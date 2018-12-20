package com.demo.test.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author jam
 * @description
 * @create 2018-12-03
 **/
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final RequestHandler requestHandler;

    public ClientHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.clientSocket = clientSocket;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try (Scanner input = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                String request = input.nextLine();
                if ("quit".equals(request)) {
                    break;
                }
                System.out.println(String.format("From %s : %s", clientSocket.getRemoteSocketAddress(), request));
                String response = requestHandler.handle(request);
                clientSocket.getOutputStream().write(response.getBytes());
            }
        }catch (IOException e){
            System.out.println("Caught exception: "+e);
            throw new RuntimeException(e);
        }
    }

}
