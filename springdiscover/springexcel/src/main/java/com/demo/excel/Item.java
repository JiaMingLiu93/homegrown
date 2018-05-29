package com.demo.excel;

import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-09
 **/
public class Item {
    private String  department;
    private String code;
    private String name;
    private String subCode;
    private String subName;
    private String operationLevel;
    private Integer numberOfYear;

    private List<OperationResult> operationResults;

    private List<RequiredMaterialAndConfirmationMessage> requiredMaterialAndConfirmationMessages;

    public static Item instanceOf(List<String> data){
        return null;
    }
}
