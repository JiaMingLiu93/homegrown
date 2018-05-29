package com.demo.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowOfSheet implements Iterable<List<String>>{
    private List<String> data;
    @Override
    public Iterator<List<String>> iterator() {
        return new ItemOperationResultRowIterator(data);
    }
}
