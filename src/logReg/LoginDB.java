package logReg;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.*;


public class LoginDB {

	public static void main(String[] args) {
		UserInput input = new UserInput();
		Connection conn =  null;
		Statement stmt = null;
		
		
		
		
		
		//step one register JDBC
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC not registered");
			return;
		}
		System.out.println("JDBC registered");
		
		//connects it to databeyssssss
		
		System.out.println("Connecting to the dotabose hehehe.....");
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/samplelogin?serverTimezone=UTC","root","SQLang@246");
		}catch(SQLException e) {
			System.out.println("MYSql Database di konektado");
		}
		if(conn != null) {
			System.out.println("Success beybee");
		}
		
		
		//create table section
		System.out.println("Creating a Table");
		
		try {
			stmt = conn.createStatement();
			String createTable = "CREATE TABLE IF NOT EXISTS userCreds(userID int(3) auto_increment not null primary key,"
					+ "userName varchar(50) unique not null,"
					+ "password varchar(50) not null"
					+ ");";
			stmt.executeUpdate(createTable);
		}catch(SQLException e) {
			System.out.print("Not Created");
		}
		
		
		try {
				
			
//				String sql = "INSERT INTO userinfo(userName, passWord) values (?,?)";
//				
//				PreparedStatement pstate = conn.prepareStatement(sql);
				
//				 	pstate.setString(1, fName);
//				    pstate.setString(2, lName);
//				    pstate.setString(3, mInitial);
//				    pstate.setInt(4, age);
				
				    
//				    pstate.executeUpdate();
				   
//				    pstate.close();
		}catch(Exception e) {
			System.out.println("hindi na insert Koven");
			e.printStackTrace();
			
		}
		
		
	}
	

}
