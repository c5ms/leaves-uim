package com.leaves.queue.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 平台默认序列化工具,当前使用fastjson实现
 */
@Component
public class JacksonSerializer implements Serializer {
    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        this.objectMapper = new ObjectMapper();
        //允许未转义的控制字符
        this.objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
        // 允许单引号
        this.objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 禁用遇到未知属性抛出异常
        this.objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        // 禁用遇到未知属性抛出异常
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 视空字符传为null
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
    }

    @Override
    public byte[] serialize(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("object serialize to json failure.", e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> tClass) {
        try {
            if (data.length == 0) {
                return null;
            }
            return objectMapper.readValue(data, tClass);
        } catch (Exception e) {
            throw new RuntimeException("json serialize to object failure.", e);
        }
    }

    @Override
    public String obj2Json(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("object serialize to json failure.", e);
        }
    }

    @Override
    public <T> T json2Obj(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException("json serialize to object failure.", e);
        }
    }
}
