package com.demo.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-09
 **/
public class ExcelRead {
    public static void main(String[] args) {
        try {
            File file = ResourceUtils.getFile("classpath:test.xlsx");
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook sheets = new XSSFWorkbook(fileInputStream);
            Sheet sheet = sheets.getSheetAt(0);
            HashMap<Integer, List<String>> data = Maps.newHashMap();
            int i = 0;
            for(Row row : sheet){
                data.put(i, Lists.newArrayList());
                for(Cell cell : row){
                    switch (cell.getCellTypeEnum()) {
                        case STRING: data.get(i).add(cell.getStringCellValue()); break;
                        case NUMERIC: data.get(i).add(cell.getNumericCellValue()+""); break;
                        case BOOLEAN: data.get(i).add(cell.getBooleanCellValue()+""); break;
                        case FORMULA: data.get(i).add(cell.getCellFormula()); break;
                        default: data.get(i).add(" ");
                    }
                }
                i++;
            }
//            data.forEach((key, value) -> value.forEach(System.out::println));
            ArrayList<ObjectConstructor> constructors = Lists.newArrayList();
            constructors.add(new ItemConstructor());
            final Item[] preItem = new Item[1];
            data.entrySet().stream().skip(3).forEach(entry-> {
                RowOfSheet row = new RowOfSheet(entry.getValue().subList(1,entry.getValue().size()));
                int k = 1;
                Item item = null;
                OperationResult operationResult;
                for (List<String> element : row){
                    if (k==1){
                        item = Item.instanceOf(element);
                        preItem[0] = item;
                    }
                    if (k==2){
                        operationResult = OperationResult.instanceOf(element);
                        if (operationResult != null){
                            if (item != null){
                                //item.add
                                return;
                            }
                            //preItem[0].add
                        }
                    }

                    element.forEach(System.out::println);
                    Object construct = constructors.get(k).construct(element);
                    k++;
                }

            });

            int itemCount = 1;
            final Item[] item = new Item[1];
            data.entrySet().stream().skip(3).forEach(entry->{
                List<String> row = entry.getValue();
                Item rowItem = null;
                OperationResult operationResult = null;
                RequiredMaterial requiredMaterial = null;
                ConfirmationMessageOfSourceDepartment confirmationMessageOfSourceDepartment = null;
                for(int j=1;j<row.size();j++){
                    if (j<7){
                        if (!row.get(j).equals(" ")){
                            if (rowItem!=null){
                                //continue fill
                            }else {
                                rowItem = new Item();
                                item[0] = rowItem;
                            }
                        }
                    }else if (j<9){
                        if (operationResult == null){
                            operationResult = new OperationResult();
                        }
                        //fill
                    }else if (j<14){
                        //add operationResult
                        Item item1 = item[0];
                        //item1.add(operationResult)

                        requiredMaterial = new RequiredMaterial();
                        //fill

                    }else if (j<19){
                        // add requiredMaterial
                        confirmationMessageOfSourceDepartment = new ConfirmationMessageOfSourceDepartment();
                        //fill
                    }else {
                        //remark
                    }
                }
            });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


    }
}
