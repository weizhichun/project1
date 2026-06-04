package com.wzc.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wzc.common.config.AiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class AiClient {
    private final AiConfig aiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiClient(AiConfig aiConfig) {
        this.aiConfig = aiConfig;
        // 配置超时
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10秒连接超时
        factory.setReadTimeout(30000);    // 30秒读取超时
        this.restTemplate = new RestTemplate(factory);
    }

    public String chat(String message) {
        // 模拟响应（当API不可用时）
        boolean useMock = "your-api-key-here".equals(aiConfig.getApiKey()) || 
                         aiConfig.getApiKey() == null || 
                         aiConfig.getApiKey().isEmpty();
        
        if (useMock) {
            return getMockResponse(message);
        }

        String url = aiConfig.getBaseUrl() + "/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + aiConfig.getApiKey());

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", aiConfig.getModel());

        ArrayNode messages = objectMapper.createArrayNode();
        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", message);
        messages.add(userMessage);

        requestBody.set("messages", messages);

        try {
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
            String response = restTemplate.postForObject(url, entity, String.class);
            JsonNode responseJson = objectMapper.readTree(response);
            JsonNode choices = responseJson.get("choices");
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode choice = choices.get(0);
                JsonNode messageObj = choice.get("message");
                return messageObj.get("content").asText();
            }
        } catch (ResourceAccessException e) {
            log.warn("AI服务连接超时或不可访问，使用模拟响应", e);
            return getMockResponse(message);
        } catch (Exception e) {
            log.error("AI调用失败", e);
            return getMockResponse(message);
        }
        return getMockResponse(message);
    }

    /**
     * 模拟AI响应
     */
    private String getMockResponse(String message) {
        if (message.contains("商品") || message.contains("产品")) {
            return "您可以在我们的商城首页查看各种热销商品。如果有任何问题，随时告诉我！";
        } else if (message.contains("订单") || message.contains("发货")) {
            return "您可以在个人中心查看您的订单状态。如有疑问，请联系客服！";
        } else if (message.contains("你好") || message.contains("您好")) {
            return "您好！我是智能客服，有什么可以帮助您的吗？";
        } else {
            return "感谢您的咨询！如有任何问题，欢迎继续联系我们。";
        }
    }
}
