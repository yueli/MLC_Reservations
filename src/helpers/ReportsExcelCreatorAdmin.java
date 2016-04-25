package helpers;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.Admin;
import model.DateTimeConverter;
import model.DbConnect;
import model.User;


/**
 * 
 * @author Victoria Chambers
 * @contributor Brian Olaogun
 * This Class is used to generate the reports for admins.
 *
 */
public class ReportsExcelCreatorAdmin {
  
  /**
   * This method is used to download reports on admin students  
   * @param out Output stream for sending binary data to the client.
   * @return Excel formatted result set that is used in conjuction with Java file writer.
   */
  public String downloadAdminList(ServletOutputStream out){
    
      int nRow = 1;
        String strQuery = null;
        Connection connection = null;
       
        HSSFRow row;
        HSSFCell cell;
             
        try {
              HSSFWorkbook wb = new HSSFWorkbook();
              HSSFSheet sheet = wb.createSheet("Admin Stats");
             
              // Getting connection here for mySQL database 
              Class.forName("com.mysql.jdbc.Driver").newInstance();
              connection = DbConnect.devCredentials();
             
              if(connection == null){
                return "Connection Failed";
              }
              // Database Query  
              strQuery = "SELECT "
              		+ "Admin.AdminMyID, "
              		+ "Admin.fname, "
              		+ "Admin.lname, "
              		+ "Admin.role, "
              		+ "Admin.adminStatus "
              		+ "FROM tomcatdb.Admin ";
              	
              PreparedStatement ps = connection.prepareStatement(strQuery);
              ResultSet rs = ps.executeQuery();
           
              // Setting Font Style for Header Row 
              sheet.setColumnWidth(0, 5000);
              sheet.setColumnWidth(1, 7000);
              sheet.setColumnWidth(2, 7000);
              sheet.setColumnWidth(3, 7000);
              sheet.setColumnWidth(4, 5000);
             
              // Creating the Font Style here 
              HSSFFont boldFont = wb.createFont();
              boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              boldFont.setColor(HSSFFont.COLOR_RED);
              HSSFCellStyle cellStyle = wb.createCellStyle();
              cellStyle.setFont(boldFont);
             
             
              // Create a row for header
              row = sheet.createRow(0);
             
              cell = row.createCell(0);
              cell.setCellValue("Admin MyID");
              cell.setCellStyle(cellStyle);
             
              cell = row.createCell(1);
              cell.setCellValue("First Name");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(2);
              cell.setCellValue("Last Name");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(3);
              cell.setCellValue("Role");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(4);
              cell.setCellValue("Admin Status");
              cell.setCellStyle(cellStyle);
              
             
              // Reading one row of table at a time and putting the values into excel cell
              while(rs.next()){
            	
        	  	Admin admin = new Admin();
                admin.setAdminMyID(rs.getString(1));
        	  	admin.setFname(rs.getString(2));
                admin.setLname(rs.getString(3));
                admin.setRole(rs.getString(4));
                admin.setAdminStatus(rs.getInt(5));           
                
                // convert role text that easier for display
                AdminUserHelper auh = new AdminUserHelper();   
                String adminRole = auh.convertAdminRole(admin.getRole());
                
                // convert status to words
                String adminStatus = "";
                int status = admin.getAdminStatus();
                if(status == 1){
                	adminStatus = "Active";
                } else {
                	adminStatus = "Not Active";
                }
                
        	  	row = sheet.createRow(nRow);
                // Create a cell and put a value in it.
                cell = row.createCell(0);
               
                cell.setCellValue(admin.getAdminMyID());
                cell = row.createCell(1);
                cell.setCellValue(admin.getFname());
                cell = row.createCell(2);
                cell.setCellValue(admin.getLname());
                cell = row.createCell(3);
                cell.setCellValue(adminRole);
                cell = row.createCell(4);
                cell.setCellValue(adminStatus);
                cell = row.createCell(5);
                nRow++;
              }
             
              wb.write(out);
              out.close();
              System.out.println("End of ReportsExcelCreatorAdmin");
              connection.close();
              return "File downloaded successfully";
              
        }
        catch (Exception e) {
              return e.getMessage();
        }
    }
}
