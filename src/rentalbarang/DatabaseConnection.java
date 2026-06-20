package rentalbarang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    public static Connection connect() {
        Connection conn = null;
        try {
            // Path pointing to where your database file will live
            // 'hobbyhustle.db' will be created automatically in your project folder
            String url = "jdbc:mysql://localhost:3306/hobbyhustle";
            
            String user ="root";
            String password="";
            
            // Establish the connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to Hobby Hustle has been established!");
            
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}