package com.demo.excel;

import java.util.Iterator;
import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-11
 **/
public abstract class RowIterator implements Iterator<List<String>> {

    private List<String> data;
    private int position;


    public RowIterator(List<String> data){
        this.data = data;
        this.position = 1;
    }
    @Override
    public boolean hasNext() {
        if (data.size()< getSpecifiedDataSize()){
            //log.warn
            return false;
        }
        if (position < getIterateCounts()){
            return true;
        }else {
            return false;
        }
    }

    protected abstract int getIterateCounts();

    protected abstract int getSpecifiedDataSize();

    @Override
    public List<String> next() {
        return null;
    }
}
