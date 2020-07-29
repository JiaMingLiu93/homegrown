package com.demo.middleware.canal.utils;

import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;

import static java.sql.Types.*;
import static java.sql.Types.TIMESTAMP;

/**
 * Maybe can refer type converter of spring and convert kinds of type
 * with extending.
 * @see ConversionService
 * @author youyu
 */
public final class TypeConverter {
    private static final String DATE_PREFIX = "1970-01-01 ";
    private static final String TIME_PREFIX = " 00:00:00";

    public static Object convertSqlTypeValue(int sqlType, String value){
        if (null == value) {
            return null;
        }
        switch (sqlType) {
            case BOOLEAN:
                if (value.isEmpty()) {
                    return null;
                }
                return Boolean.parseBoolean(value);
            case TINYINT:
            case SMALLINT:
            case INTEGER:
                if (value.isEmpty()) {
                    return null;
                }
                return Integer.parseInt(value);
            case BIT:
            case BIGINT:
                if (value.isEmpty()) {
                    return null;
                }
                return Long.parseLong(value);
            case FLOAT:
            case REAL:
                if (value.isEmpty()) {
                    return null;
                }
                return Float.parseFloat(value);
            case DOUBLE:
                if (value.isEmpty()) {
                    return null;
                }
                return Double.parseDouble(value);
            case NUMERIC:
            case DECIMAL:
                if (value.isEmpty()) {
                    return null;
                }
                return new BigDecimal(value);
            case DATE:
                if (value.isEmpty()) {
                    return null;
                }
                //格式为 yyyy-MM-dd
                return value + TIME_PREFIX;
            case TIME:
                if (value.isEmpty()) {
                    return null;
                }
                return DATE_PREFIX + ignorePrecision(value);
            case TIMESTAMP:
                if (value.isEmpty()) {
                    return null;
                }
                if (value.startsWith("0000")) {
                    return "1970-01-01 00:00:00";
                }
                return ignorePrecision(value);
            default:
                return value;
        }
    }

    private static String ignorePrecision(String value) {
        int i = value.lastIndexOf(".");
        return i == -1 ? value : value.substring(0, i);
    }
}
