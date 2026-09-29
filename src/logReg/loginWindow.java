package logReg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
	private String username = "banana"; //placeholder para sa database 
	private String password = "bananapotato";
	private String role = "Student";
	
	private static Connection conn;
	private JLabel prompt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UserInput input = new UserInput();
		
		Statement stmt = null;
		String url = "jdbc:mysql://localhost:3306/sampleLogin?serverTimezone=UTC";
        String user = "root";
        String password = "SQLang@246";
		
		
		
		
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
		
		
		//create table section
		System.out.println("Creating a Table");
		
		try {
			stmt = conn.createStatement();
			String createTable = "CREATE TABLE IF NOT EXISTS userCreds(userID int(3) auto_increment not null primary key,"
					+ "userName varchar(50) unique not null,"
					+ "password varchar(64) not null,"
					+ "role VARCHAR(20) not null"
					+ ");";
			stmt.executeUpdate(createTable);
		}catch(SQLException e) {
			System.out.print("Not Created");
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
				
				Salt salt = new Salt();
				
				try {
					
					
					String sql = "INSERT INTO usercreds(userName, password,role) values (?,?,?)";
					
					PreparedStatement pstate = conn.prepareStatement(sql);
					String pass = new String(passwordField.getPassword());
					
					
						
					 	pstate.setString(1, UserTextField.getText());
					    pstate.setString(2, salt.convPass(pass));
					    pstate.setString(3, role);
					
					    
					    pstate.executeUpdate();
					    System.out.println("inserted the values");
					    pstate.close();
					    
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
