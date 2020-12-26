package com.homegrown.basic.design.pattern.create.singleton.usage.example1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author youyu
 */
public class Logger {

    private FileWriter writer;

    private static final Logger instance = new Logger();

    private Logger() {

        File file = new File("/Users/jam/company/code/homegrown/basic/design-pattern/src/main/resources/log.txt");
        try {
            this.writer = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance(){
        return instance;
    }

    public void log(String msg){
        try {
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
