package com.demo.test.bio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author jam
 * @description
 * @create 2018-12-04
 **/
public class BioTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket clientSocket = new Socket("localhost", 8888);
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        try (Scanner input = new Scanner(System.in)) {

            while (true){
                if (input.hasNext()){
                    outputStream.write(input.nextLine().getBytes());
                    outputStream.flush();
                }
                byte b[] = new byte[1024];
                if(inputStream.read(b) > 0) {
                    System.out.println(new String(b));
                }
            }

        }

    }
}
