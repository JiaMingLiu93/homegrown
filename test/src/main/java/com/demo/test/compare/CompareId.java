package com.demo.test.compare;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author youyu
 */
public class CompareId {
    public static void main(String[] args) throws IOException {
//        compareOneFile();
        compareTwoFiles();
    }

    private static void compareOneFile() throws IOException {
        File file = new File("/Users/jam/dumps/asia-dev-contact-user-0412/query_result.csv");
        BufferedReader textFile = new BufferedReader(new FileReader(file));
        String lineDta = "";

        ArrayList<String> lines = Lists.newArrayList();

        //第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
        while ((lineDta = textFile.readLine()) != null){
            lines.add(lineDta);
        }
        textFile.close();

        for (int i = 1; i<119590; i++){
            if (!lines.contains(Integer.toString(i))){
                System.out.println(i);
            }
        }
    }

    private static List<String> getLines(File file) throws IOException {

        BufferedReader textFile = new BufferedReader(new FileReader(file));
        String lineDta = "";

        ArrayList<String> lines = Lists.newArrayList();

        //第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
        while ((lineDta = textFile.readLine()) != null){
            lines.add(lineDta);
        }
        textFile.close();

        return lines;
    }

    public static void compareTwoFiles() throws IOException {
        File db = new File("/Users/jam/dumps/asia-dev-contact-user-0412/query_result.csv");

        File file = new File("/Users/jam/company/亚士/数据迁移相关/0322/联系人/联系人0322.xlsx");
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet("联系人");
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        System.out.println(physicalNumberOfRows);

        List<String> values = Lists.newArrayList();



        for (int i=1;i<physicalNumberOfRows;i++){
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);

            values.add(BigDecimal.valueOf(cell.getNumericCellValue()).toBigInteger().toString());
        }

//        System.out.println(values);

        HashSet<String> strings = Sets.newHashSet(values);

        System.out.println(values.size());
        System.out.println(strings.size());

        List<String> lines = getLines(db);

        strings.removeAll(lines);
        System.out.println(strings);
    }
}
