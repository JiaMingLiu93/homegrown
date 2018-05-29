package com.demo.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemConstructor implements ObjectConstructor<Item> {
    private List<String> data;

    @Override
    public Item construct(List<String> data) {
        try {
            List<String> itemInfo = data.subList(0, 9);
            if (itemInfo.get(3).equals(" ")) {
                Item item = Item.class.newInstance();
                Field[] fields = Item.class.getDeclaredFields();
                for (int i=0;i<itemInfo.size();i++) {
                    fields[i].setAccessible(true);
                    fields[i].set(item,itemInfo.get(i));
                }
            }else {
                Item item = Item.class.newInstance();
                Field[] fields = Item.class.getDeclaredFields();
                for (int i=0;i<itemInfo.size();i++) {
                    fields[i].setAccessible(true);
                    fields[i].set(item,itemInfo.get(i));
                    fields[i].getName().equals(" 子项");
                }

            }
            List<String> strings = data.subList(10, 14);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
//        if (position == 2){
//            position++;
//            return data.subList(7,9);
//        }
//        if (position == 3){
//            position++;
//            return data.subList(9,14);
//        }
//        if (position == 4){
//            position++;
//            return data.subList(14,20);
//        }

}

