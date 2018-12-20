package com.demo.test.bio;

import org.apache.tools.ant.taskdefs.Sleep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author jam
 * @description
 * @create 2018-12-03
 **/
public class BIOServer {
    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(8888)){
            System.out.println("BIOServer has started,listening on port:"+serverSocket.getLocalSocketAddress());
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection from " + clientSocket.getRemoteSocketAddress());
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                Scanner scanner = new Scanner(System.in);
                while (true){
                    byte b[] = new byte[1024];
                    if(inputStream.read(b) > 0) {
                        System.out.println(new String(b));
                    }
                    if (scanner.hasNext()){
                        outputStream.write(scanner.nextLine().getBytes());
                        outputStream.flush();
                    }
                }
//                try (Scanner input = new Scanner(clientSocket.getInputStream())) {
//                    while (true) {
//                        String request = input.nextLine();
//                        if ("quit".equals(request)) {
//                            break;
//                        }
//                        System.out.println(String.format("From %s : %s", clientSocket.getRemoteSocketAddress(), request));
//                        String response = "From BIOServer Hello " + request + ".\n";
//                        clientSocket.getOutputStream().write(response.getBytes());
//                    }
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
