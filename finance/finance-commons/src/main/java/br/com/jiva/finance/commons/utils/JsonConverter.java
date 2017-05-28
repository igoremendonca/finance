package br.com.jiva.finance.commons.utils;


import br.com.jiva.finance.commons.enuns.ExceptionCode;
import br.com.jiva.finance.commons.exception.FinanceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FinanceException(ExceptionCode.INVALID_OBJECT, "Invalid");
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new FinanceException(ExceptionCode.INVALID_OBJECT, "Invalid");
        }
    }

    public static <K,V> Map<String, Object> convertObjectToMap(Object object, Class<K> key, Class<V> value) {
        return objectMapper.convertValue(object, new TypeReference<Map<K, V>>(){});
    }

    public static <V> List<V> convertObjectToList(String json, Class<V> value) {
        try {
            final JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, value);
            return objectMapper.readValue(json, listType);
        }catch (Exception e){
            throw new FinanceException(ExceptionCode.INVALID_OBJECT, "Invalid");
        }
    }
}
