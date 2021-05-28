package com.homegrown.component.search.client.elasticsearch.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author youyu
 */
@Slf4j
public final class JsonUtils {

    public static final TypeReference<ArrayList<Object>> List_TYPE_REFERENCE = new TypeReference<ArrayList<Object>>() {
    };
    public static final TypeReference<HashMap<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<HashMap<String, Object>>() {
    };

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper INDENT_OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        INDENT_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        INDENT_OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        OBJECT_MAPPER.setDateFormat(new SimpleDateFormatDecorator("yyyy-MM-dd HH:mm:ss"));
        INDENT_OBJECT_MAPPER.setDateFormat(new SimpleDateFormatDecorator("yyyy-MM-dd HH:mm:ss"));

        INDENT_OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String toJson(Object o) {
        if (null != o) {
            try {
                return OBJECT_MAPPER.writeValueAsString(o);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }
    static class SimpleDateFormatDecorator extends SimpleDateFormat {
        private static ThreadLocal<SimpleDateFormat> LOCAL;

        SimpleDateFormatDecorator(String pattern) {
            LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
        }

        @Override
        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
            return LOCAL.get().format(date, toAppendTo, pos);
        }

        @Override
        public Date parse(String text, ParsePosition pos) {
            return LOCAL.get().parse(text, pos);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (null != json && json.length() > 0) {
            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (null != json && json.length() > 0) {
            try {
                return OBJECT_MAPPER.readValue(json, typeReference);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static <T> List<T> fromJsonArray(String json, Class<? extends List> arrayClass, Class<T> clazz) {
        if (null != json && json.length() > 0) {
            try {
                JavaType classType = OBJECT_MAPPER.getTypeFactory().constructParametricType(arrayClass, new Class[]{clazz});
                //noinspection unchecked
                return (List) OBJECT_MAPPER.readValue(json, classType);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static <T> T convert(Object object, Class<T> clazz) {
        if (null != object) {
            try {
                return OBJECT_MAPPER.convertValue(object, clazz);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static <T> T convert(Object object, TypeReference<T> typeReference) {
        if (null != object) {
            try {
                return OBJECT_MAPPER.convertValue(object, typeReference);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private JsonUtils() {
    }
}
