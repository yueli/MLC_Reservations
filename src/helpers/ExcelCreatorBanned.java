/* @Author Victoria Chambers */

package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;

import com.mysql.jdbc.Blob;

import model.DbConnect;

import java.sql.Statement;

public class ExcelCreatorBanned {

          public String downloadExcel( ) throws IOException { //ServletOutputStream out){
        	 String data = ""; 
        	//String outFileName = "BannedStudents.csv";
        	//File file = new File(outFileName);
        
        	//String home = System.getProperty("user.home");
        	//File file = new File(home + "/Downloads" + outFileName);
        	
  /**      	if (!file.exists()){
        		file.createNewFile();
        	} */
        	
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
                        return null;
                  // Database Query               
                  strQuery = "select * from Banned into OUTFILE BannedStudents.csv;";
                  Statement stmt = con.createStatement();
                  ResultSet rs = stmt.executeQuery(strQuery);
                                                                     
                  //FileWriter fstream = new FileWriter(file.getAbsoluteFile());
                  //BufferedWriter out = new BufferedWriter(fstream);
                  
                //Get Titles
                  String titleLine = "BannedID" + "," + "UserID" + "," + "AdminID" + "," + "Ban Start Date" + "," + 
                		   "Ban End Date" + "," + "Penalty Count" + "," + "Description" + "," + "Status" + "\n";
                  //out.write(titleLine);
                  data += titleLine;
                  
                  while(rs.next()){
	                 /* out.write(Integer.toString(rs.getInt("BannedID")) + ", ");
	                  out.write(Integer.toString(rs.getInt("UserID")) + ", ");
	                  out.write(Integer.toString(rs.getInt("AdminID")) + ", ");
	                  out.write(rs.getString("BanStartDate") + ", ");
	                  out.write(rs.getString("BanEndDate") + ", ");
	                  out.write(Integer.toString(rs.getInt("PenaltyCount")) + ", ");
	                  out.write(rs.getString("Description") + ", ");
	                  out.write(rs.getString("Status") + ", ");
	                  out.newLine();*/
                	  
                	  data += Integer.toString(rs.getInt("BannedID")) + ", ";
                	  data += Integer.toString(rs.getInt("UserID")) + ", ";
                	  data += Integer.toString(rs.getInt("AdminID")) + ", ";
                	  data += rs.getString("BanStartDate") + ", ";
                	  data += rs.getString("BanEndDate") + ", ";
                	  data += Integer.toString(rs.getInt("PenaltyCount")) + ", ";
                	  data += rs.getString("Description") + ", ";
                	  data += rs.getString("Status") + ", ";
                	  data += "/n";
                	  
                	  
                  }
                
                  
                  
                  //stmt = conn.createStatement();
                  
                  
               /**   while (rs.next()) {
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
                  } //end of while */
                  //out.close();
                 
          } catch (Exception e) {
        	  System.err.println("Got an exception!");
              System.err.println(e.getMessage());
          }
          
			return rs;// <---- DELETE THIS - I ADDED THIS RETURN SO THAT CLASS WONT CAUSE AN ERROR - BRIAN 
			//return file; // I commented out line above and put this in instead because I was geting an error - GINGER
			
			//return data;// <---- DELETE THIS - I ADDED THIS RETURN SO THAT CLASS WONT CAUSE AN ERROR - BRIAN 

     }
          
          /** private String toByteValue(File file)
			throws IOException {
		byte[] bytes = loadFile(file);
		
		String encodedString = new String(bytes);
		return encodedString;
	}
	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
	    is.close();
	    return bytes;
	} */
          
}
