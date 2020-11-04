package com.demo.middleware.rocketMq.polling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 客户端使用HttpURLConnection来和服务端建立连接，不停的拉取数据
 * @author youyu
 * @date 2020/5/21 2:50 PM
 */
public class Client {

    public static void main(String[] args) {
        while (true) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://localhost:8080");
                connection = (HttpURLConnection) url.openConnection();
                //只要在10秒内，服务端有数据返回，就会读取到数据
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.connect();
                if (200 == connection.getResponseCode()) {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                        StringBuffer result = new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        System.out.println("时间:" + new Date().toString() + "result =  " + result);
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}
