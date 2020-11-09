package sysaddLibrary;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class test {

	private JFrame frmGenerateReport;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frmGenerateReport.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGenerateReport = new JFrame();
		frmGenerateReport.setTitle("Generate Report");
		frmGenerateReport.setBounds(100, 100, 351, 224);
		frmGenerateReport.getContentPane().setLayout(null);
		
		JLabel successLbl = new JLabel("");
		successLbl.setHorizontalAlignment(SwingConstants.CENTER);
		successLbl.setBounds(127, 113, 74, 35);
		frmGenerateReport.getContentPane().add(successLbl);
		
		JButton attBtn = new JButton("Attendance Log");
		attBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generate.generateAttendance();
				successLbl.setText("Success!");
				
				int delay = 2500; //milliseconds, Clears the labels for the next user
			      ActionListener taskPerformer = new ActionListener() {
			          public void actionPerformed(ActionEvent evt) {
			        	  successLbl.setText("");
			        	  
			            }
			        };
			        javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
			        tick.setRepeats(false);
			        tick.start();
			}
		});
		attBtn.setBounds(21, 24, 139, 70);
		frmGenerateReport.getContentPane().add(attBtn);
		
		JButton bookBtn = new JButton("Book Log");
		bookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generate.generateBookReport();
				successLbl.setText("Success!");
				
				int delay = 2500; //milliseconds, Clears the labels for the next user
			      ActionListener taskPerformer = new ActionListener() {
			          public void actionPerformed(ActionEvent evt) {
			        	  successLbl.setText("");
			        	  
			            }
			        };
			        javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
			        tick.setRepeats(false);
			        tick.start();
			}
		});
		
		
		bookBtn.setBounds(170, 24, 139, 70);
		frmGenerateReport.getContentPane().add(bookBtn);
		
		
	}
}
