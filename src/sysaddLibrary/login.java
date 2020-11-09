package sysaddLibrary;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class login {

	private JFrame frmLibraryLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frmLibraryLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmLibraryLogin = new JFrame();
		frmLibraryLogin.setTitle("Library Logging System");
		frmLibraryLogin.setBounds(100, 100, 450, 300);
		frmLibraryLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryLogin.getContentPane().setLayout(null);
		
		JLabel lblPleaseTapYour = new JLabel("Please tap your ID card");
		lblPleaseTapYour.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPleaseTapYour.setBounds(104, 11, 207, 41);
		frmLibraryLogin.getContentPane().add(lblPleaseTapYour);
		
		JLabel successLbl = new JLabel("");
		successLbl.setHorizontalAlignment(SwingConstants.CENTER);
		successLbl.setBounds(116, 88, 170, 28);
		frmLibraryLogin.getContentPane().add(successLbl);
		
		JLabel welcomeLbl = new JLabel("");
		welcomeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		welcomeLbl.setBounds(153, 63, 95, 28);
		frmLibraryLogin.getContentPane().add(welcomeLbl);
		
		JLabel nameLbl = new JLabel("");
		nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nameLbl.setBounds(104, 114, 195, 14);
		frmLibraryLogin.getContentPane().add(nameLbl);
		
		Button button = new Button("RFID READER");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fName = "Lorenz";
				String lName = "Durante";
				String email = "lcdurante@student.apc.edu.ph";
				String usType = "Student";
				int studentId = 2018100195;
				String studentName = fName + lName;
				welcomeLbl.setText("Good day!");
				successLbl.setText(Integer.toString(studentId));
				nameLbl.setText(lName +", "+ fName);
				
				try {
					//logs information to login/out database
			        Class.forName("com.mysql.cj.jdbc.Driver");
			        System.out.println("Loaded driver");
			        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library");
			        Statement s=con.createStatement();
					s.execute("INSERT INTO LOGIN(studentID, FNAME, LNAME, EMAIL, USTYPE) VALUES('" + studentId + "','" + fName + "','" + lName+ "','"+ email +"', '"+usType+"')");
					
					
					
					int delay = 2500; //milliseconds, Clears the labels for the next user
				      ActionListener taskPerformer = new ActionListener() {
				          public void actionPerformed(ActionEvent evt) {
				        	  welcomeLbl.setText("");
				        	  successLbl.setText("");
				        	  nameLbl.setText("");
				        	  
				            }
				        };
				        javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
				        tick.setRepeats(false);
				        tick.start();
				        
			        System.out.println("Connected to MySQL");
			        return;	
			    
			 } 
			 catch (Exception ex) {
				 
			        ex.printStackTrace();
			 }

				
			}
			
		});
		
		button.setBounds(10, 160, 414, 91);
		frmLibraryLogin.getContentPane().add(button);
	}
}
