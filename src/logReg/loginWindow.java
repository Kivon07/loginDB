package logReg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class loginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField UserTextField;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	private String role = "Student";
	
	private static Connection conn;
	private JLabel prompt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		Statement stmt = null;
		final String url = "jdbc:mysql://localhost:3306/";
		final String DATABASE = "enrollDB";
        final String user = "root";
        final String password = "SQLang@246"; // ur pass here
		final Salt salt = new Salt();
		
		
		
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
			conn = DriverManager.getConnection(url, user, password);
		}catch(SQLException e) {
			System.out.println("MYSql Database di konektado");
		}
		if(conn != null) {
			System.out.println("Success beybee");
		}
		
		try {
			stmt = conn.createStatement();
			
			String createDatabase = "Create DATABASE IF NOT EXISTS " + DATABASE;
			
			
			stmt.executeUpdate(createDatabase);
		}catch(SQLException e) {
			System.out.println("failed creating database");
		}
		
		
		try {
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(url + DATABASE, user, password);
		}catch(SQLException e) {
			System.out.println("Connected to" + DATABASE);
		}
		

		
		
			
		try{
			
			
			
			
			String sql = "INSERT INTO usercreds(userName, password,role) values (?,?,?)";
			
			PreparedStatement pstate = conn.prepareStatement(sql);
		
			
			
				
			 	pstate.setString(1, "root");
			    pstate.setString(2, salt.convPass("adminHW"));
			    pstate.setString(3, "admin");
			
			    
			    pstate.executeUpdate();
			    System.out.println("inserted the values");
			    pstate.close();
		}catch(SQLIntegrityConstraintViolationException e1){
			System.out.println("error");
			
		}catch(SQLException e3){
			System.out.println();
		}
		
		
		
	
		
		System.out.println("Created zy table");
		//-----------------------------------
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginWindow frame = new loginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		prompt = new JLabel("");
		prompt.setForeground(new Color(255, 128, 64));
		prompt.setBounds(121, 119, 385, 26);
		contentPane.add(prompt);
		
		JLabel lblNewLabel = new JLabel("Admissions Office");
		lblNewLabel.setFont(new Font("Poppins", Font.BOLD, 24));
		lblNewLabel.setBounds(31, 26, 241, 81);
		contentPane.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(230, 230, 230));
		passwordField.setBounds(121, 220, 385, 34);
		contentPane.add(passwordField);
		
		UserTextField = new JTextField();
		UserTextField.setBackground(new Color(230, 230, 230));
		UserTextField.setBounds(121, 155, 385, 34);
		contentPane.add(UserTextField);
		UserTextField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(45, 165, 44, 12);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(45, 230, 44, 12);
		contentPane.add(lblNewLabel_2);
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//test method here
				 convertHash hash = new convertHash();
				 
				 String username = UserTextField.getText();
				 String pass = new String(passwordField.getPassword());
				
				 
				 
				 try {
					 
					 boolean checked;
					 
					 if(username.isEmpty() || pass.isEmpty()) {
						 
						 System.out.println("Please fill in the forms");
						 
					 }else {
						 
						 Salt salt = new Salt();
						 
						 String sql = "select password from usercreds where userName = ?";
							
							PreparedStatement pstate = conn.prepareStatement(sql);
							
							 	pstate.setString(1, UserTextField.getText());
							
							    
							    ResultSet rs = pstate.executeQuery()
;								
							    if(rs.next()) {
							    	String storedHash = rs.getString("password");
							    	String inputPass = new String(passwordField.getPassword());
							    	
							    	boolean valid = salt.passChecker(inputPass, storedHash);
							    	
							    	if(valid) {
							    		System.out.println("Correct Pass");
							    	}
							    	else {
							    		System.out.println("Wrong pass");
							    	}
							    }
							    
							    
							    pstate.close();
						
					 }
				 }catch(Exception e1) {
					 e1.printStackTrace();				

				 }
				 
				 
				
				
				
				//--------------------------------------
				
				//sign in checker
				
			}
		});
		btnSignIn.setBounds(121, 274, 149, 64);
		contentPane.add(btnSignIn);
		
		JLabel lblNewLabel_3 = new JLabel("No Account? Enroll now");
		lblNewLabel_3.setBounds(280, 262, 112, 89);
		contentPane.add(lblNewLabel_3);
		
		JButton btnSignUp = new JButton("Enroll");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				//enroll method or class here
				
				
				//test adding of account 
				//tong line of code nato ang dapat panghuli sa enrollment process
				
				
				
				String username = UserTextField.getText();
				String pass = new String(passwordField.getPassword());
				
				Salt salt = new Salt();
				
				try {
					
					if(pass.isBlank() || username.isBlank()) {
						prompt.setText("Please fill the fields");
					}
					else {
						String sql = "INSERT INTO usercreds(userName, password,role) values (?,?,?)";
						
						PreparedStatement pstate = conn.prepareStatement(sql);
					
						 
							
						 	pstate.setString(1, username);
						    pstate.setString(2, salt.convPass(pass));
						    pstate.setString(3, role);
						
						    
						    pstate.executeUpdate();
						    System.out.println("inserted the values");
						    pstate.close();
					}
					
					
					    
				}catch(SQLIntegrityConstraintViolationException e1) {
					prompt.setText("User already Exists");
					System.out.println("credentials already exists");
				}catch(Exception e1) {
					
						System.out.println("Other Errors");
						e1.printStackTrace();
				}
				
				//--------
				
					
				
			}
		});
		btnSignUp.setBounds(394, 274, 149, 64);
		contentPane.add(btnSignUp);

	}
}




// yung Salt class ang ginamit ko para i convert yung pass to hash
// ginamit koden yun to compare yun nainput na pass to the stored hash sa database
// use the role variable to determine kung admin access or student ang lalabas na frame
// mostly admin access naman sa pagkakaalam ko yun syst