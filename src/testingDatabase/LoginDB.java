package testingDatabase;


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
		Statement state = null;
		
		input.setfName(JOptionPane.showInputDialog("Input First Name"));
		String fName = input.getfName();
		
		input.setlName(JOptionPane.showInputDialog("Input Last Name"));
		String lName = input.getlName();
		
		input.setmInit(JOptionPane.showInputDialog("Input Middle Initiial")); 
		String mInitial = input.getmInit();
		
		input.setAge(Integer.parseInt(JOptionPane.showInputDialog("Input Age: "))); 
		int age = input.getAge();
		
		
		
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
				
			
				String sql = "INSERT INTO userinfo(firstName, lastName, middle_initial, age) values (?,?,?,?)";
				
				PreparedStatement pstate = conn.prepareStatement(sql);
				
				 	pstate.setString(1, fName);
				    pstate.setString(2, lName);
				    pstate.setString(3, mInitial);
				    pstate.setInt(4, age);
				
				    
				    pstate.executeUpdate();
				    System.out.println("Data inserted successfully into table!");
				    pstate.close();
		}catch(SQLException e) {
			System.out.println("hindi na insert Koven");
			e.printStackTrace();
			
		}
		
		System.out.println("Created zy table");
	}
	

}
