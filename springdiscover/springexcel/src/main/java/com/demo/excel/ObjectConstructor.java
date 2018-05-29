package com.demo.excel;

import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-10
 **/
public interface ObjectConstructor<T> {
    T construct(List<String> data);
}
