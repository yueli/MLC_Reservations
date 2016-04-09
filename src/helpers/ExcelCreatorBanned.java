/* @Author Victoria Chambers */

package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;


import model.DbConnect;

import java.sql.Statement;

public class ExcelCreatorBanned {

          public String downloadExcel( ) { //ServletOutputStream out){
        	  
           
        	System.out.println("At the very top");
    	  	int nRow = 1;
            String strQuery = null;
            Connection con = null;
                         
            try {
                                  
                  // Getting connection here for mySQL database 
                  Class.forName("com.mysql.jdbc.Driver").newInstance();
                  //con = DbConnect.devCredentials();
                  con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/","Root","");
                  System.out.println("Got into server");
                 
               if(con==null)
                        return "Connection Failed";
                  // Database Query               
                  strQuery = "select * from banned";
                  Statement stmt=con.createStatement();
                  ResultSet rs=stmt.executeQuery(strQuery);
                  
                  File file = new File("C:\\Temp\\BannedStudents.txt");
                  
                  //Get Titles
                //  public static String newline = System.getProperty("line.separator");
               //   System.out.println("BannedID" + " " + "UserID" + " " + "AdminID" + " " + "Ban Start Date" + " " + 
                //  "Ban End Date" + " " + "Penalty Count" + " " + "Description" + " " + "Status");
                  
                  //String query = "SELECT * FROM Banned";
                  
                  /** stmt = conn.createStatement();
                  rs = stmt.executeQuery(query);
                  while (rs.next()) {
                  int BannedId = rs.getInt(1);
                  int UserID = rs.getInt(2); */
 /** UNCOMMENT HERE - BRIAN - ADDED COMMENT SO THAT THERE WONT BE AN ERROR WHEN RUNNIN ON SERVER                        
                  String Status = rs.getString(8);
                  System.out.println(BannedId + "\t" + UserID + "\t" + AdminID + "\t" + BanStartDate + "\t" + 
                  BanEndDate + "\t" + PenaltyCount + "\t" + Description + "\t" + Status);   
                   
                  FileWriter fstream = new FileWriter(file);
                  BufferedWriter out = new BufferedWriter(fstream);
                   
                   out.write(rs.getInt(1));
                   out.write(rs.getInt(2));
                   out.write(rs.getString(4));
                   
                   out.close();  
                   
                  } */
                 
          } catch (Exception e) {
        	  System.err.println("Got an exception!");
              System.err.println(e.getMessage());
          }
			return strQuery;// <---- DELETE THIS - I ADDED THIS RETURN SO THAT CLASS WONT CAUSE AN ERROR - BRIAN 
     }
}
