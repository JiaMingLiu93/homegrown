package com.homegrown.db.mysql.jdbc;

import java.sql.*;

/**
 * @author youyu
 */
public class JdbcTest {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?characterEncoding=utf8", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from user");
            while(rs.next()){
                System.out.println(rs.getObject(1)+" "+rs.getObject(2));
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
