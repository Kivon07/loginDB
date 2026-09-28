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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private String username = "banana";
	private String password = "bananapotato";
	private String role = "Student";
	private static Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UserInput input = new UserInput();
		
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
		setBounds(100, 100, 582, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
				
				 String username = UserTextField.getText();
				 String pass = new String(passwordField.getPassword());
				 
				 
				 if(username.isEmpty() || pass.isEmpty()) {
					 System.out.println("Please fill in the forms");
				 }else {
					 System.out.println(username + " " + pass);
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
				
				//test adding of account 
				passHash hash = new passHash();
				
				try {
					
					
					String sql = "INSERT INTO usercreds(userName, password,role) values (?,?,?)";
					
					PreparedStatement pstate = conn.prepareStatement(sql);
					
					 	pstate.setString(1, username);
					    pstate.setString(2, hash.encryptString(password));
					    pstate.setString(3, role);
					
					    
					    pstate.executeUpdate();
					    System.out.println("inserted the values");
					    pstate.close();
					    
				}catch(SQLIntegrityConstraintViolationException e1) {
					System.out.println("credentials already exists");
				}catch(Exception e1) {
					
						System.out.println("Other Errors");
						e1.printStackTrace();
				}
				
				//--------
				
					
				//enroll method or class here
			}
		});
		btnSignUp.setBounds(394, 274, 149, 64);
		contentPane.add(btnSignUp);

	}
}
