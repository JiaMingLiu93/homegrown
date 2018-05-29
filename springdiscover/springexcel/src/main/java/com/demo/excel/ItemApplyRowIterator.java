package com.demo.excel;

import java.util.Iterator;
import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-11
 **/
public class ItemApplyRowIterator implements Iterator<List<String>> {
    private List<String> data;
    private int position;


    public ItemApplyRowIterator(List<String> data){
        this.data = data;
        this.position = 1;
    }
    @Override
    public boolean hasNext() {
        if (data.size()<20){
            //log.warn
            return false;
        }
        if (position <5){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<String> next() {
        return null;
    }
}
