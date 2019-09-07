package mybatis.test.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author jam
 * @date 2019/4/12 11:47 AM
 */
public class HsqldbAccessTest {
    public static void main(String[] args) {
        Connection con = null;

        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:mem:hsql://localhost/testdb", "SA", "");
            if (con!= null){
                System.out.println("Connection created successfully");

            }else{
                System.out.println("Problem with creating connection");
            }

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
