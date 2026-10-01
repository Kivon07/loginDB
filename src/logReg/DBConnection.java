package logReg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/";

    private static final String DATABASE = "enrollDB";

    private static final String USER = "root";

    private static final String PASSWORD = "SQLang@246";


    public static Connection getConnection() {

        Connection conn = null;

        try {

            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("JDBC registered.");


            // -----------------------------------------
            // Connect to MySQL server
            // -----------------------------------------
            conn = DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
            );

            System.out.println("Connected to MySQL server.");


            // -----------------------------------------
            // Create database if it doesn't exist
            // -----------------------------------------
            Statement stmt = conn.createStatement();

            String createDatabase =
                "CREATE DATABASE IF NOT EXISTS " + DATABASE;

            stmt.executeUpdate(createDatabase);

            System.out.println(
                "Database " + DATABASE + " is ready."
            );

            stmt.close();


            // -----------------------------------------
            // Close server connection
            // -----------------------------------------
            conn.close();


            // -----------------------------------------
            // Connect to enrollDB
            // -----------------------------------------
            conn = DriverManager.getConnection(
                URL + DATABASE,
                USER,
                PASSWORD
            );

            System.out.println(
                "Connected to " + DATABASE
            );


            // -----------------------------------------
            // Create usercreds table
            // -----------------------------------------
            Statement tableStmt = conn.createStatement();

            String createTable =
                "CREATE TABLE IF NOT EXISTS usercreds (" +
                "userID INT AUTO_INCREMENT PRIMARY KEY, " +
                "userName VARCHAR(50) UNIQUE NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "role VARCHAR(20) NOT NULL" +
                ")";

            tableStmt.executeUpdate(createTable);

            System.out.println(
                "usercreds table is ready."
            );

            tableStmt.close();


        } catch (ClassNotFoundException e) {

            JOptionPane.showMessageDialog(
                null,
                "MySQL Driver not found: " + e.getMessage(),
                "Driver Error",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                null,
                "Database Error: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }


        return conn;
    }
}