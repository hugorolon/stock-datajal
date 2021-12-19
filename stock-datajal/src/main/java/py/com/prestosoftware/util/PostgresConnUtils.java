package py.com.prestosoftware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class PostgresConnUtils {
 
    public static Connection getPostgresConnection()
            throws ClassNotFoundException, SQLException {
        String hostName = "server";
        String dbName = "sys_agroprogreso07122021";
        String userName = "postgres";
        String password = "adminpg";
        return getPostgresConnection(hostName, dbName, userName, password);
    }
 
    public static Connection getPostgresConnection(String hostName, String dbName,
            String userName, String password) throws SQLException,
            ClassNotFoundException {
 
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        Class.forName("org.postgresql.Driver");
 
        // Cấu trúc URL Connection dành cho Oracle
        // Ví dụ: jdbc:mysql://localhost:3306/simplehr
        String connectionURL = "jdbc:postgresql://" + hostName + ":5432/" + dbName;
 
        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        conn.setAutoCommit(false);
        return conn;
    }
}
