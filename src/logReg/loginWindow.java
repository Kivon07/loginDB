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
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class loginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField UserTextField;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UserInput input = new UserInput();
		Connection conn =  null;
		Statement state = null;
		
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
				
			
				String sql = "INSERT INTO userlogininfo(firstName, lastName, middle_initial, age) values (?,?,?,?)";
				
				PreparedStatement pstate = conn.prepareStatement(sql);
				
//				 	pstate.setString(1, fName);
//				    pstate.setString(2, lName);
//				    pstate.setString(3, mInitial);
//				    pstate.setInt(4, age);
				
				    
				    pstate.executeUpdate();
				    System.out.println("Data inserted successfully into table!");
				    pstate.close();
		}catch(SQLException e) {
			System.out.println("hindi na insert Koven");
			e.printStackTrace();
			
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
		
		JButton btnSignIn = new JButton("SignIn");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//method here
			}
		});
		btnSignIn.setBounds(121, 274, 149, 64);
		contentPane.add(btnSignIn);
		
		JLabel lblNewLabel_3 = new JLabel("No Account? Enroll now");
		lblNewLabel_3.setBounds(280, 262, 112, 89);
		contentPane.add(lblNewLabel_3);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//method here
			}
		});
		btnSignUp.setBounds(394, 274, 149, 64);
		contentPane.add(btnSignUp);

	}
}
