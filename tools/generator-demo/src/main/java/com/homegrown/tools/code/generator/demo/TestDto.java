package com.homegrown.tools.code.generator.demo;

import java.lang.management.ManagementFactory;

/**
 * @author youyu
 */
public class TestDto {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        System.out.println(path);
        String s = ManagementFactory.
                getRuntimeMXBean().
                getInputArguments().toString();
        System.out.println(s);
//        [-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=61475:/Applications/IntelliJ IDEA CE.app/Contents/bin, -Dfile.encoding=UTF-8]

    }
}
