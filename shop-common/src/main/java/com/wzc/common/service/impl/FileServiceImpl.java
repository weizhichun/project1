package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.FileRecord;
import com.wzc.common.mapper.FileRecordMapper;
import com.wzc.common.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRecordMapper fileRecordMapper;

    private static final Set<String> ALLOWED_IMAGE_TYPES = new HashSet<>(Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"
    ));

    private static final Map<String, String> MAGIC_NUMBERS = new HashMap<>();

    static {
        MAGIC_NUMBERS.put("FFD8FF", "image/jpeg");
        MAGIC_NUMBERS.put("89504E47", "image/png");
        MAGIC_NUMBERS.put("47494638", "image/gif");
        MAGIC_NUMBERS.put("424D", "image/bmp");
        MAGIC_NUMBERS.put("52494646", "image/webp");
    }

    @PostConstruct
    public void init() {
        File uploadDir = new File(CommonConstant.UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @Override
    public Map<String, Object> upload(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("success", false);
            result.put("msg", "文件为空");
            return result;
        }

        if (file.getSize() > CommonConstant.MAX_FILE_SIZE) {
            result.put("success", false);
            result.put("msg", "文件大小超过10MB限制");
            return result;
        }

        String contentType = file.getContentType();
        if (!ALLOWED_IMAGE_TYPES.contains(contentType) || !validateMagicNumber(file)) {
            result.put("success", false);
            result.put("msg", "文件类型不允许或文件内容不安全");
            return result;
        }

        try {
            String md5 = calculateMd5(file.getBytes());

            LambdaQueryWrapper<FileRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FileRecord::getMd5, md5);
            FileRecord existingRecord = fileRecordMapper.selectOne(wrapper);

            if (existingRecord != null) {
                result.put("success", true);
                result.put("msg", "文件已存在（去重）");
                result.put("url", "/" + existingRecord.getFilePath().replace("\\", "/"));
                result.put("pcUrl", "/" + existingRecord.getFilePath().replace("\\", "/").replace(".", "_pc."));
                result.put("mobileUrl", "/" + existingRecord.getFilePath().replace("\\", "/").replace(".", "_mobile."));
                result.put("fileName", existingRecord.getStoredName());
                result.put("deduplicated", true);
                return result;
            }

            String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String fileDir = CommonConstant.UPLOAD_DIR + File.separator + dateDir;
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }
            String storedName = UUID.randomUUID().toString().replace("-", "") + extension;
            String relativePath = dateDir + File.separator + storedName;
            String fullPath = CommonConstant.UPLOAD_DIR + File.separator + relativePath;

            File destFile = new File(fullPath);
            file.transferTo(destFile);

            String pcName = storedName.replace(extension, "_pc" + extension);
            String mobileName = storedName.replace(extension, "_mobile" + extension);
            String pcPath = fileDir + File.separator + pcName;
            String mobilePath = fileDir + File.separator + mobileName;

            Thumbnails.of(destFile)
                    .size(CommonConstant.PC_WIDTH, CommonConstant.PC_HEIGHT)
                    .keepAspectRatio(true)
                    .toFile(new File(pcPath));

            Thumbnails.of(destFile)
                    .size(CommonConstant.MOBILE_WIDTH, CommonConstant.MOBILE_HEIGHT)
                    .keepAspectRatio(true)
                    .toFile(new File(mobilePath));

            FileRecord record = new FileRecord();
            record.setOriginalName(originalName);
            record.setStoredName(storedName);
            record.setFilePath(relativePath);
            record.setFileSize(file.getSize());
            record.setFileType(contentType);
            record.setMd5(md5);
            fileRecordMapper.insert(record);

            result.put("success", true);
            result.put("msg", "上传成功");
            result.put("url", "/" + relativePath.replace("\\", "/"));
            result.put("pcUrl", "/" + relativePath.replace("\\", "/").replace(extension, "_pc" + extension));
            result.put("mobileUrl", "/" + relativePath.replace("\\", "/").replace(extension, "_mobile" + extension));
            result.put("fileName", storedName);
            result.put("deduplicated", false);

            log.info("文件上传成功: {} -> {}", originalName, relativePath);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            result.put("success", false);
            result.put("msg", "文件上传失败: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Map<String, Object> uploadEditorImage(MultipartFile file) {
        Map<String, Object> uploadResult = upload(file);
        Map<String, Object> editorResult = new HashMap<>();

        if (Boolean.TRUE.equals(uploadResult.get("success"))) {
            editorResult.put("errno", 0);
            editorResult.put("message", "上传成功");
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("url", (String) uploadResult.get("url"));
            dataMap.put("alt", (String) uploadResult.get("fileName"));
            dataMap.put("href", (String) uploadResult.get("url"));
            editorResult.put("data", dataMap);
        } else {
            editorResult.put("errno", 1);
            editorResult.put("message", uploadResult.get("msg"));
        }

        return editorResult;
    }

    private boolean validateMagicNumber(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[8];
            int read = is.read(header, 0, 8);
            if (read < 4) {
                return false;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < read; i++) {
                sb.append(String.format("%02X", header[i]));
            }
            String hexHeader = sb.toString();

            for (Map.Entry<String, String> entry : MAGIC_NUMBERS.entrySet()) {
                if (hexHeader.startsWith(entry.getKey())) {
                    return entry.getValue().equals(file.getContentType());
                }
            }

            return false;
        } catch (Exception e) {
            log.error("文件魔数校验失败", e);
            return false;
        }
    }

    private String calculateMd5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("MD5计算失败", e);
            return "";
        }
    }
}