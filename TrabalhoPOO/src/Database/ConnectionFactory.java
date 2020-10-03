package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/conveniencia";
    private static String USER = "root";
    private static String PASSWORD = "root";

    public static Connection getConnection(){
        Connection connect = null;
        try{
            Class.forName(DRIVER);
            connect =  DriverManager.getConnection(URL, USER, PASSWORD);
            connect.setAutoCommit(false);
        }catch (ClassNotFoundException | SQLException ex){
           throw new RuntimeException("Error na conexão", ex);
        }
        return connect;
    }
}
