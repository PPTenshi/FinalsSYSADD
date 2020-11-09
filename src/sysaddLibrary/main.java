package sysaddLibrary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
 
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import sysaddLibrary.main.ex;

import java.awt.Color;
import java.awt.SystemColor;
 
public class main {
     
    public static class ex{
        public static int days=0;
            }
 
    public static void main(String[] args) {
         
        admin_menu();
    	//database();
        //create();
    	//login();
    }

	public static void login() {
     
    JFrame f=new JFrame("Login");//creating instance of JFrame  
    JLabel l1,l2;  
    l1=new JLabel("Email");  //Create label Username
    l1.setBounds(30,15, 100,30); //x axis, y axis, width, height 
     
    l2=new JLabel("Password");  //Create label Password
    l2.setBounds(30,50, 100,30);    
     
    JTextField F_user = new JTextField(); //Create text field for username
    F_user.setBounds(110, 15, 200, 30);
         
    JPasswordField F_pass=new JPasswordField(); //Create text field for password
    F_pass.setBounds(110, 50, 200, 30);
       
    JButton login_but=new JButton("Login");//creating instance of JButton for Login Button
    login_but.setBounds(130,90,80,25);//Dimensions for button
    login_but.addActionListener(new ActionListener() {  //Perform action
         
        public void actionPerformed(ActionEvent e){ 
 
        String username = F_user.getText(); //Store username entered by the user in the variable "username"
        String password = F_pass.getText(); //Store password entered by the user in the variable "password"
         
        if(username.equals("")) //If username is null
        {
            JOptionPane.showMessageDialog(null,"Please enter Email"); //Display dialog box with the message
        } 
        else if(password.equals("")) //If password is null
        {
            JOptionPane.showMessageDialog(null,"Please enter password"); //Display dialog box with the message
        }
        else { //If both the fields are present then to login the user, check wether the user exists already
            //System.out.println("Login connect");
            Connection connection=connect();  //Connect to the database
            try
            {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
            	    ResultSet.CONCUR_READ_ONLY);
              stmt.executeUpdate("USE LIBRARY"); //Use the database with the name "Library"
              String st = ("SELECT * FROM USERS WHERE EMAIL='"+username+"' AND PASSWORD='"+password+"'"); //Retreive username and passwords from users
              ResultSet rs = stmt.executeQuery(st); //Execute query
              if(rs.next()==false) { //Move pointer below
                  System.out.print("No user");  
                  JOptionPane.showMessageDialog(null,"Wrong Username/Password!"); //Display Message
 
              }
              else {
                  f.dispose();
                rs.beforeFirst();  //Move the pointer above
                while(rs.next())
                {
                  String admin = rs.getString("ADMIN"); //user is admin
                  //System.out.println(admin);
                  String UID = rs.getString("IDNUM"); //Get user ID of the user
                  if(admin.equals("1")) { //If boolean value 1
                      admin_menu(); //redirect to admin menu
                  }
                  else{
                	  JOptionPane.showMessageDialog(null,"Error: Contact Administrator!"); //Display Message //redirect to user menu for that user ID
                  }
              }
              }
            }
            catch (Exception ex) {
                 ex.printStackTrace();
        }
        }
    }               
    });
 
     
    f.getContentPane().add(F_pass); //add password
    f.getContentPane().add(login_but);//adding button in JFrame  
    f.getContentPane().add(F_user);  //add user
    f.getContentPane().add(l1);  // add label1 i.e. for username
    f.getContentPane().add(l2); // add label2 i.e. for password
    f.setSize(400,180);//400 width and 500 height  
    f.getContentPane().setLayout(null);//using no layout managers  
    f.setVisible(true);//making the frame visible 
    f.setLocationRelativeTo(null);
     
}
	
	public static Connection connect()
	{
	try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.println("Loaded driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library");
	        System.out.println("Connected to MySQL");
	        return con;
	 } 
	 catch (Exception ex) {
	        ex.printStackTrace();
	 }
	return null;
	}
	
	public static void database() {
	    try {
	    Connection connection=connect();
	    ResultSet resultSet = connection.getMetaData().getCatalogs();
	    //iterate each catalog in the ResultSet
	        while (resultSet.next()) {
	          // Get the database name, which is at position 1
	          String databaseName = resultSet.getString(1);
	          if(databaseName.equals("library")) {
	              //System.out.print("yes");
	              Statement stmt = connection.createStatement();
	              //Drop database if it pre-exists to reset the complete database
	              String sql = "DROP DATABASE library";
	              stmt.executeUpdate(sql);
	          }
	        }
	          Statement stmt = connection.createStatement();
	           
	          String sql = "CREATE DATABASE LIBRARY"; //Create Database
	          stmt.executeUpdate(sql); 
	          stmt.executeUpdate("USE LIBRARY"); //Use Database
	          //Create Users Table
	          String sql1 = "CREATE TABLE USERS(IDNUM INT NOT NULL AUTO_INCREMENT PRIMARY KEY, EMAIL VARCHAR(30), PASSWORD VARCHAR(30), ADMIN BOOLEAN)";
	          stmt.executeUpdate(sql1);
	          //Insert into users table
	          stmt.executeUpdate("INSERT INTO USERS(EMAIL, PASSWORD, ADMIN) VALUES('admin','admin',TRUE)");
	          //Create Books table
	          stmt.executeUpdate("CREATE TABLE BOOKS(BACCNUM INT NOT NULL AUTO_INCREMENT PRIMARY KEY, BTITLE VARCHAR(50), BAUTH VARCHAR(50), GENRE VARCHAR(50), PUBLISHER VARCHAR(50), PUBYEAR INT(4), ISSUED_DATE VARCHAR(20), RETURN_DATE VARCHAR(20), PERIOD INT, STATUS VARCHAR(20), UID INT)");
	          //Create Issued Table
	          stmt.executeUpdate("CREATE TABLE ISSUED(ISSUEID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, UID INT, BACCNUM INT, ISSUED_DATE VARCHAR(20), RETURN_DATE VARCHAR(20), PERIOD INT)");
	          //Insert into books table
	          stmt.executeUpdate("INSERT INTO BOOKS(BTITLE, BAUTH, GENRE, PUBLISHER, PUBYEAR, STATUS) VALUES ('War and Peace', 'Mike Stint', 'Fiction', 'Publeash Inc.', 2002, 'Available'),  ('The Sost Lymbol', 'Ban Drown', 'Nonfiction', 'Penguin Inc.', 2010, 'Available'), ('Jomeo and Ruliet', 'Shilliam Wakespeare', 'Mystery', 'Microsoft Publishing.', 2022, 'Available'),('1489', 'Borge Borwell', 'Comedy', 'Mystery Inc.', 1489, 'Available'), ('Tarey Fails', 'Princess Aurora', 'Horror', 'The Printer Inc', 2012, 'Available'),('Bambee', 'Salt Widney', 'Mythology', 'Jack Sparrow Publishers', 1985, 'Available'), ('Mankey', 'Shmibid Attenmibid', 'Educational', 'SF Publishing', 1521, 'Available')");
	          //login
	          stmt.executeUpdate("CREATE TABLE LOGIN(studentID INT NOT NULL, FNAME VARCHAR(50), LNAME VARCHAR(50), EMAIL VARCHAR(50), USTYPE VARCHAR(50), DATE datetime default now())");
	    resultSet.close();
	    
	    }
	     catch (Exception ex) {
	         ex.printStackTrace();
             admin_menu();
	     }
	}
	
	public static void admin_menu() {
	     
	    JFrame f=new JFrame("APC RFID LIBRARY SYSTEM");
	    f.getContentPane().setBackground(new Color(100, 149, 237));
	    f.getContentPane().setForeground(Color.WHITE);
	    f.setTitle("APC RFID LIBRARY SYSTEM - Librarian");
	     
	    JButton database_but=new JButton("Database Reset");
	    database_but.setBackground(Color.PINK);
	    database_but.setBounds(10,11,141,49);
	    database_but.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	             
	            database();
	            JOptionPane.showMessageDialog(null,"Database Reset!");
	             
	        }
	    });

	   
	    Connection connection = main.connect(); //connect database
	    String sql="SELECT BACCNUM as 'Accession No.', BTITLE as 'Title', BAUTH as 'Author', GENRE as 'Genre', PUBLISHER as 'Publisher', PUBYEAR as 'Year Published', ISSUED_DATE as 'Issued Date', RETURN_DATE as 'Return Date', PERIOD as 'Period', STATUS as 'Status' FROM BOOKS"; //select all books 

        try {
            Statement stmt = connection.createStatement();
             stmt.executeUpdate("USE LIBRARY"); 
            stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            JTable book_list= new JTable(); 
            book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
            //mention scroll bar
            JScrollPane scrollPane = new JScrollPane(book_list);
            scrollPane.setBounds(10, 66, 577, 228);
            f.getContentPane().add(scrollPane);
            f.getContentPane().setLayout(null);
            
            
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
             JOptionPane.showMessageDialog(null, e1);
        }
	     
        
        JButton btnNewButton = new JButton("Generate Report");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
                generate.generateWindow();
        	}
        });
        btnNewButton.setBounds(332, 11, 136, 49);
        f.getContentPane().add(btnNewButton);
        f.setSize(622, 432); //set size for frame
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        
	     
	    JButton add_book=new JButton("Add Book"); //creating instance of JButton for adding books
	    add_book.setBounds(478,11,109,49); 
	     
	    add_book.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	                //set frame wot enter book details
	        	JFrame g = new JFrame("Enter Book Details");
	    		g.setTitle("Add Book to Library");
	            //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            // set labels
	            JLabel l1,l2,l3;  
	            l1=new JLabel("Book Title");  //lebel 1 for book name
	            l1.setBounds(30,15, 100,30); 
	             
	             
	            l2=new JLabel("Author");  //label 2 for genre
	            l2.setBounds(30,53, 100,30); 
	             
	            l3=new JLabel("Genre");  //label 2 for price
	            l3.setBounds(30,90, 100,30); 
	            
	            JLabel lblPublisher = new JLabel("Publisher");
	            lblPublisher.setBounds(30, 131, 100, 30);
	            g.getContentPane().add(lblPublisher);

	            JLabel lblYearPublished = new JLabel("Year Published");
	            lblYearPublished.setBounds(30, 172, 60, 30);
	            g.getContentPane().add(lblYearPublished);
	            
	            //set text field for book name
	            JTextField F_btitle = new JTextField();
	            F_btitle.setBounds(110, 15, 200, 30);
	            
	            //set text field for genre 
	            JTextField F_bauth=new JTextField();
	            F_bauth.setBounds(110, 53, 200, 30);
	            //set text field for price
	            JTextField F_genre=new JTextField();
	            F_genre.setBounds(110, 90, 200, 30);
	                     
	            JTextField publ = new JTextField();
	            publ.setBounds(110, 131, 200, 30);
	            
	            JTextField yearPub = new JTextField();
	            yearPub.setBounds(110, 172, 200, 30);
	            
	            
	            JButton create_but=new JButton("Add Book");//creating instance of JButton to submit details  
	            create_but.setBounds(120,213,80,25);//x axis, y axis, width, height 
	            create_but.addActionListener(new ActionListener() {
	                 
	                public void actionPerformed(ActionEvent e){
	                // assign the book name, genre, price
	                String bname = F_btitle.getText();
	                String auth = F_bauth.getText();
	                String genre = F_genre.getText();
	                String pub = publ.getText();
	                String yrPub = yearPub.getText();
	                Connection connection = main.connect();
	                 
	                try {
	                Statement stmt = connection.createStatement();
	                 stmt.executeUpdate("USE LIBRARY");
	                 stmt.executeUpdate("INSERT INTO BOOKS(BTITLE, BAUTH, GENRE, PUBLISHER, PUBYEAR, STATUS) VALUES ('"+bname+"','"+auth+"','"+genre+"','"+pub+"', '"+yrPub+"','Available')");
	                 JOptionPane.showMessageDialog(null,"Book added!");
	                 //f.dispose();
	                 main.admin_menu();
	                 g.dispose();
	                 
	                  
	                }
	                 
	                catch (SQLException e1) {
	                    // TODO Auto-generated catch block
	                     JOptionPane.showMessageDialog(null, e1);
	                }   
	                 
	                }
	                 
	            });
	                             
	                g.getContentPane().add(l3);
	                g.getContentPane().add(create_but);
	                g.getContentPane().add(l1);
	                g.getContentPane().add(l2);
	                g.getContentPane().add(F_btitle);
	                g.getContentPane().add(F_bauth);
	                g.getContentPane().add(F_genre);
	                g.setSize(350,292);//400 width and 500 height  
	                g.getContentPane().setLayout(null);//using no layout managers  
	                g.getContentPane().add(publ);
	                g.getContentPane().add(yearPub);
	                g.setVisible(true);//making the frame visible 
	                g.setLocationRelativeTo(null);
	                
	                
	                
	                
	                
	                         
	    }	
	    });
	     
	     
	    JButton issue_book=new JButton("Borrow"); //creating instance of JButton to issue books
	    issue_book.setBackground(Color.GREEN);
	    issue_book.setBounds(280,305,307,66); 
	     
	    issue_book.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	                //enter details

	   		 JFrame g = new JFrame("Enter Details");
	   		 g.setTitle("Borrow Book");
	            //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            //create labels
	            JLabel l1,l2,l3,l4;  
	            l1=new JLabel("Book Accession No.");  // Label 1 for Book ID
	            l1.setBounds(30,15, 138,30); 
	             
	            l2=new JLabel("Personal ID No.");  //Label 2 for user ID
	            l2.setBounds(30,53, 100,30); 
	             
	            l3=new JLabel("Period(days)");  //Label 3 for period
	            l3.setBounds(30,90, 100,30); 
	             
	            l4=new JLabel("Issued Date(DD-MM-YYYY)");  //Label 4 for issue date
	            l4.setBounds(30,127, 150,30); 
	             
	            JTextField F_bid = new JTextField();
	            F_bid.setBounds(193, 15, 200, 30);
	             
	             
	            JTextField F_uid=new JTextField();
	            F_uid.setBounds(193, 53, 200, 30);
	             
	            JTextField F_period=new JTextField();
	            F_period.setBounds(193, 90, 200, 30);
	             
	            JTextField F_issue=new JTextField();
	            F_issue.setBounds(193, 127, 200, 30);   

	             
	            JButton create_but=new JButton("Submit");//creating instance of JButton  
	            create_but.setBounds(153,168,80,25);//x axis, y axis, width, height 
	            create_but.addActionListener(new ActionListener() {
	                 
	                public void actionPerformed(ActionEvent e){
	                 
	                String uid = F_uid.getText();
	                String bid = F_bid.getText();
	                String period = F_period.getText();
	                String issued_date = F_issue.getText();

	                int period_int = Integer.parseInt(period);
	                 
	                Connection connection = connect(); try {
	                Statement stmt = connection.createStatement();
	                 stmt.executeUpdate("USE LIBRARY");
	                 stmt.executeUpdate("INSERT INTO ISSUED(UID,BACCNUM,ISSUED_DATE,PERIOD) VALUES ('"+uid+"','"+bid+"','"+issued_date+"','"+period_int+"')");
	                 stmt.executeUpdate("UPDATE BOOKS SET ISSUED_DATE='"+issued_date+"' WHERE BACCNUM="+bid);
	                 stmt.executeUpdate("UPDATE BOOKS SET PERIOD='"+period_int+"' WHERE BACCNUM="+bid);
	                 stmt.executeUpdate("UPDATE BOOKS SET STATUS='On Loan' WHERE BACCNUM="+bid);
	                 stmt.executeUpdate("UPDATE BOOKS SET UID='"+uid+"' WHERE BACCNUM="+bid);
	                 JOptionPane.showMessageDialog(null,"Book Issued!");
	                 f.dispose();
	                 admin_menu();
	                 g.dispose();
	                  
	                }
	                 
	                catch (SQLException e1) {
	                    // TODO Auto-generated catch block
	                     JOptionPane.showMessageDialog(null, e1);
	                }   
	                 
	                }
	                 
	            });
	                 
	             
	                g.getContentPane().add(l3);
	                g.getContentPane().add(l4);
	                g.getContentPane().add(create_but);
	                g.getContentPane().add(l1);
	                g.getContentPane().add(l2);
	                g.getContentPane().add(F_uid);
	                g.getContentPane().add(F_bid);
	                g.getContentPane().add(F_period);
	                g.getContentPane().add(F_issue);
	                g.setSize(444,240);//400 width and 500 height  
	                g.getContentPane().setLayout(null);//using no layout managers  
	                g.setVisible(true);//making the frame visible 
	                g.setLocationRelativeTo(null);
	             
	             
	   }
	    });
	     
	     
	    JButton return_book=new JButton("Return"); //creating instance of JButton to return books
	    return_book.setBackground(Color.GREEN);
	    return_book.setBounds(10,305,254,66); 
	     
	    return_book.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	                 
	        	JFrame g = new JFrame("Enter Details");
	    		g.setTitle("Return Books");
	            //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            //set labels 
	            JLabel l1,l2,l3,l4;  
	            l1=new JLabel("Book Accession No.");  //Label 1 for Issue ID
	            l1.setBounds(30,15, 150,30); 
	            
	             
	            l4=new JLabel("Return Date(DD-MM-YYYY)");  
	            l4.setBounds(30,50, 150,30); 
	             
	            JTextField F_bid = new JTextField();
	            F_bid.setBounds(152, 15, 157, 30);
	             
	             
	            JTextField F_return=new JTextField();
	            F_return.setBounds(179, 50, 130, 30);
	         

	            JButton create_but=new JButton("Return");//creating instance of JButton to mention return date and calculcate fine
	            create_but.setBounds(123,91,80,25);//x axis, y axis, width, height 
	            create_but.addActionListener(new ActionListener() {
	                 
	                public void actionPerformed(ActionEvent e){                 
	                 
	                String bid = F_bid.getText();
	                String return_date = F_return.getText();
	                 
	                Connection connection = connect();
	                 
	                try {
	                Statement stmt = connection.createStatement();
	                 stmt.executeUpdate("USE LIBRARY");
	                 //Intialize date1 with NULL value
	                 String date1=null;
	                 String date2=return_date; //Intialize date2 with return date
	                 
	                 //select issue date
	                 ResultSet rs = stmt.executeQuery("SELECT ISSUED_DATE FROM ISSUED WHERE BACCNUM="+bid);
	                 while (rs.next()) {
	                     date1 = rs.getString(1);
	                      
	                   }
	                  
	                 try {
	                        Date date_1=new SimpleDateFormat("dd-MM-yyyy").parse(date1);
	                        Date date_2=new SimpleDateFormat("dd-MM-yyyy").parse(date2);
	                        //subtract the dates and store in diff
	                        long diff = date_2.getTime() - date_1.getTime();
	                        //Convert diff from milliseconds to days
	                        ex.days=(int)(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	                         
	                         
	                    } catch (ParseException e1) {
	                        // TODO Auto-generated catch block
	                        e1.printStackTrace();
	                    }
	                  
	                 
	                 //update return date
	                 stmt.executeUpdate("UPDATE ISSUED SET RETURN_DATE='"+return_date+"' WHERE BACCNUM="+bid);
	                 stmt.executeUpdate("UPDATE BOOKS SET RETURN_DATE='"+return_date+"' WHERE BACCNUM="+bid);
	                 stmt.executeUpdate("UPDATE BOOKS SET STATUS='Available' WHERE BACCNUM="+bid);
	                 f.dispose();
	                 admin_menu();
	                 g.dispose();
	                 JOptionPane.showMessageDialog(null,"Book Returned!");
	                  
	                }
	                         
	                 
	                catch (SQLException e1) {
	                    // TODO Auto-generated catch block
	                     JOptionPane.showMessageDialog(null, e1);
	                }   
	                 
	                }
	                 
	            }); 
	                g.getContentPane().add(l4);
	                g.getContentPane().add(create_but);
	                g.getContentPane().add(l1);
	                g.getContentPane().add(F_bid);
	                g.getContentPane().add(F_return);
	                g.setSize(347,176);//400 width and 500 height  
	                g.getContentPane().setLayout(null);//using no layout managers  
	                g.setVisible(true);//making the frame visible 
	                g.setLocationRelativeTo(null);              
	    }

	    });
	     
	    f.getContentPane().add(database_but);
	    f.getContentPane().add(return_book);
	    f.getContentPane().add(issue_book);
	    f.getContentPane().add(add_book); 
	    f.getContentPane().setLayout(null);
	    f.setVisible(true);
	    f.setLocationRelativeTo(null);
	     
	    }
	}
