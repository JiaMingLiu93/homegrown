package com.homegrown.coding.javac.token.self;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.util.Context;

import javax.tools.JavaFileObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author jam
 * @description
 * @create 2019-07-31
 **/
public class JavaFileReader {
    public void read(String[] files){
        if (files.length == 0){
            return;
        }

        for (String file:files){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] b = new byte[100];
                int a=0;
                StringBuffer sb = new StringBuffer();
                while ((a=fileInputStream.read(b))!=-1){
                    sb.append(new String(b,0,a));
                }
                System.out.println(sb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read2(String[] files){
        Context context = new Context();
        JavacFileManager.preRegister(context);
        JavacFileManager javacFileManager = new JavacFileManager(context,true,null);

        Iterable<? extends JavaFileObject> iterable = javacFileManager.getJavaFileObjectsFromStrings(Arrays.asList(files));
        for (JavaFileObject javaFileObject : iterable){
            System.out.println(javaFileObject);
        }
    }

    public static void main(String[] args) {
        new JavaFileReader().read2(new String[]{"/Users/jam/study/code/homegrown/coding/javac/src/main/java/com/homegrown/coding/javac/token/Token.java"});
    }
}
