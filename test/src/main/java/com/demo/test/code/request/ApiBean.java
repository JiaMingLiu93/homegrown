package com.demo.test.code.request;

import com.demo.test.code.utils.JsonUtil;

import java.io.Serializable;

/**
 * @author youyu
 */
public class ApiBean implements Serializable {
    public ApiBean() {
    }

    public String toString() {
        return JsonUtil.getIndentNonEmptyJsonString(this);
    }
}
