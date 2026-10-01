package logReg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/samplelogin"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "SQLang@246";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL Driver not found: " + e.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
}