package com.demo.test.format;

import org.springframework.util.StringUtils;

/**
 * @author jam
 * @description
 * @create 2018-11-02
 **/
public class FormatTest {
    public static void main(String[] args) {
        String msg = "%s,hello,%s";
        String format = String.format(msg, "jam", "i am coming");
        System.out.println(format);

        String space = " hello, jam";
        System.out.println(space);
        System.out.println(StringUtils.trimAllWhitespace(space));
    }
}
