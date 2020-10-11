package com.demo.test.code.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author youyu
 */
public class MyClassLoader extends ClassLoader{
    private String path;
    private String className;
    public MyClassLoader(ClassLoader parent,String classPath,String className) {
        super(parent);
        this.path = classPath;
        this.className = className;
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        try {
            if(!className.equals(name))
                return super.loadClass(name);

            URL myUrl = new URL("file:"+path);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(name,
                    classData, 0, classData.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
