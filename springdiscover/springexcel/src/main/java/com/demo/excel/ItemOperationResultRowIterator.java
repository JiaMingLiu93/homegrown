package com.demo.excel;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author jam
 * @description
 * @create 2018-05-09
 **/
public class ItemOperationResultRowIterator implements Iterator<List<String>> {
    private List<String> data;
    private int position;
    private Consumer<List<String>> consumer;


    public ItemOperationResultRowIterator(List<String> data){
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
        consumer.accept(data);
        //logic
        if (position == 1){
            position++;
            return data.subList(0,7);
        }
        if (position == 2){
            position++;
            return data.subList(7,9);
        }
        if (position == 3){
            position++;
            return data.subList(9,14);
        }
        if (position == 4){
            position++;
            return data.subList(14,20);
        }
        return Lists.newArrayList();
    }
}
