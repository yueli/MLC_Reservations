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
        	  
        	String outFileName = "BannedStudents.csv";
        	System.out.println("At the very top");
    	  	int nRow = 1;
            String strQuery = null;
            Connection con = null;
            System.out.println("Before try");
                         
            try {
                                  
            	System.out.println("In Try"); 
            	// Getting connection here for mySQL database 
                  Class.forName("com.mysql.jdbc.Driver").newInstance();
                  con = DbConnect.devCredentials();
                  System.out.println(con);
                  /*con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/","Root","");
                  System.out.println("Got into server");*/
                 
               if(con==null)
                        return "Connection Failed";
                  // Database Query               
                  strQuery = "select * from banned";
                  Statement stmt=con.createStatement();
                  ResultSet rs=stmt.executeQuery(strQuery);
                  
                  
                  
                  File file = new File(outFileName);
                  FileWriter fstream = new FileWriter(file);
                  BufferedWriter out = new BufferedWriter(fstream);
                
                  //Get Titles
                  String titleLine = "BannedID" + "," + "UserID" + "," + "AdminID" + "," + "Ban Start Date" + "," + 
                		   "Ban End Date" + "," + "Penalty Count" + "," + "Description" + "," + "Status" + "\n";
                  out.write(titleLine);
                  
                  // stmt = conn.createStatement();
                  //rs = stmt.executeQuery(query);
                  
                  while (rs.next()) {
                	  int BannedId = rs.getInt(1);
                	  int UserID = rs.getInt(2); 
                	  int AdminID = rs.getInt(3); 
                	  String BanStartDate = rs.getString(4); 
                	  String BanEndDate = rs.getString(5); 
                	  int PenaltyCount = rs.getInt(6); 
                	  String Description = rs.getString(7);                 
                	  String Status = rs.getString(8);
                  
                	  String outline = BannedId + "," + UserID + "," + AdminID + "," + BanStartDate + "," + 
                          BanEndDate + "," + PenaltyCount + "," + Description + "," + Status + "\n";
                	  
                	  out.write(outline);

                  } //end of while
                  out.close();
                 
          } catch (Exception e) {
        	  System.err.println("Got an exception!");
              System.err.println(e.getMessage());
          }
			return outFileName;// <---- DELETE THIS - I ADDED THIS RETURN SO THAT CLASS WONT CAUSE AN ERROR - BRIAN 
     }
}
