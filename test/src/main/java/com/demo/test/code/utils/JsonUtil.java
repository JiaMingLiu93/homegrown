package com.demo.test.code.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Objects;

/**
 * @author youyu
 */
public class JsonUtil {
    private static final ObjectMapper INDENT_OBJECT_MAPPER_NON_EMPTY = new ObjectMapper();
    private static final ObjectMapper INDENT_OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper NON_INDENT_OBJECT_MAPPER = new ObjectMapper();

    public JsonUtil() {
    }

    public static String getIndentNonEmptyJsonString(Object o) {
        if (Objects.isNull(o)) {
            return "";
        } else {
            try {
                return INDENT_OBJECT_MAPPER.writeValueAsString(o);
            } catch (Exception var2) {
                return doToStringException(o, var2);
            }
        }
    }

    public static String getIndentJsonString(Object o) {
        if (Objects.isNull(o)) {
            return "";
        } else {
            try {
                return INDENT_OBJECT_MAPPER.writeValueAsString(o);
            } catch (Exception var2) {
                return doToStringException(o, var2);
            }
        }
    }

    public static String getNonIndentJsonString(Object o) {
        if (Objects.isNull(o)) {
            return "";
        } else {
            try {
                return NON_INDENT_OBJECT_MAPPER.writeValueAsString(o);
            } catch (Exception var2) {
                return doToStringException(o, var2);
            }
        }
    }

    public static <T> T getObject(String jsonSting, Class<T> beanClass) {
        if (Objects.nonNull(jsonSting) && Objects.nonNull(beanClass)) {
            try {
                return INDENT_OBJECT_MAPPER.readValue(jsonSting, beanClass);
            } catch (IOException var3) {
                return null;
            }
        } else {
            return null;
        }
    }

    private static String doToStringException(Object o, Exception e) {
        return "[Object toString() cause Jackson Serialization Failed] [" + o.getClass().getName() + "] " + e.getMessage();
    }

    static {
        INDENT_OBJECT_MAPPER_NON_EMPTY.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        INDENT_OBJECT_MAPPER_NON_EMPTY.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        INDENT_OBJECT_MAPPER_NON_EMPTY.enable(SerializationFeature.INDENT_OUTPUT);
        INDENT_OBJECT_MAPPER_NON_EMPTY.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        INDENT_OBJECT_MAPPER_NON_EMPTY.registerModule(new GuavaModule());
        INDENT_OBJECT_MAPPER.registerModule(new JavaTimeModule());
        INDENT_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        INDENT_OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        INDENT_OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        NON_INDENT_OBJECT_MAPPER.registerModule(new JavaTimeModule());
        NON_INDENT_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        NON_INDENT_OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
