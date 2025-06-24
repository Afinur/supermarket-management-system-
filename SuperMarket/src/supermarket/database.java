package supermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    public static Connection connectionDb() {
        try {
            // Load the new MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection URL
            String url = "jdbc:mysql://localhost:3306/market?useSSL=false&serverTimezone=UTC";
            String user = "root";       // change if needed
            String password = "";       // change if needed

            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }

        return null;
    }
}
