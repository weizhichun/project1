package com.wzc.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzc.common.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
