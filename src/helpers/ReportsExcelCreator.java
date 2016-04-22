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

import model.Banned;
import model.DateTimeConverter;
import model.DbConnect;
import model.User;


/**
 * 
 * @author Brian Olaogun & Victoria Chambers
 * This Class is used to generate the reports for admins.
 *
 */
public class ReportsExcelCreator {
  
  /**
   * This method is used to download reports on banned students  
   * @param out Output stream for sending binary data to the client.
   * @return Excel formatted result set that is used in conjuction with Java file writer.
   */
  public String downloadBannedList(ServletOutputStream out){
    
      int nRow = 1;
        String strQuery = null;
        Connection connection = null;
       
        HSSFRow row;
        HSSFCell cell;
             
        try {
              HSSFWorkbook wb = new HSSFWorkbook();
              HSSFSheet sheet = wb.createSheet("Banned Students");
             
              // Getting connection here for mySQL database 
              Class.forName("com.mysql.jdbc.Driver").newInstance();
              connection = DbConnect.devCredentials();
             
              if(connection == null){
                return "Connection Failed";
              }
              // Database Query  
              strQuery = "SELECT Banned.bannedID, "
              		+ "User.myID, "
              		+ "Banned.banStart, "
              		+ "Banned.banEnd, "
              		+ "Banned.penaltyCount, "
              		+ "Banned.description, "
              		+ "Banned.status "
              		+ "FROM tomcatdb.Banned, tomcatdb.User "
              		+ "WHERE Banned.User_userID = User.userID "
              		+ "AND status = '1' "
              		+ "AND penaltyCount > 1 "
              		+ "AND (banEnd IS NULL OR banEnd = '')";
              PreparedStatement ps = connection.prepareStatement(strQuery);
              ResultSet rs = ps.executeQuery();
           
              // Setting Font Style for Header Row 
              sheet.setColumnWidth(0, 5000);
              sheet.setColumnWidth(1, 6000);
              sheet.setColumnWidth(2, 7000);
              sheet.setColumnWidth(3, 7000);
              sheet.setColumnWidth(4, 5000);
              sheet.setColumnWidth(5, 7000);
              sheet.setColumnWidth(6, 5000);
              sheet.setColumnWidth(7, 5000);
             
              // Creating the Font Style here 
              HSSFFont boldFont = wb.createFont();
              boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              boldFont.setColor(HSSFFont.COLOR_RED);
              HSSFCellStyle cellStyle = wb.createCellStyle();
              cellStyle.setFont(boldFont);
             
             
              // Create a row for header
              row = sheet.createRow(0);
             
              cell = row.createCell(0);
              cell.setCellValue("Banned Id");
              cell.setCellStyle(cellStyle);
             
              cell = row.createCell(1);
              cell.setCellValue("Student MyID");
              cell.setCellStyle(cellStyle);
              
              /*cell = row.createCell(2);
              cell.setCellValue("Admin MyID");
              cell.setCellStyle(cellStyle);*/
             
              cell = row.createCell(2);
              cell.setCellValue("Ban Start Date");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(3);
              cell.setCellValue("Ban End Date");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(4);
              cell.setCellValue("Penalty Count");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(5);
              cell.setCellValue("Description");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(6);
              cell.setCellValue("Status");
              cell.setCellStyle(cellStyle);
             
             
              // Reading one row of table at a time and putting the values into excel cell
              while(rs.next()){
            	DateTimeConverter dtc = new DateTimeConverter();
            	
                
            	User user = new User();
        	  	Banned banned = new Banned();
                banned.setBanID(rs.getInt(1));
                user.setMyID(rs.getString(2));
        	  	banned.setBanStart(rs.getString(3));
                banned.setBanEnd(rs.getString(4));
                banned.setPenaltyCount(rs.getInt(5));
                banned.setDescription(rs.getString(6));
        	  	banned.setStatus(rs.getInt(7));
        	  	
        	  	// below will show a string status instead of a number
                String stringStatus = "";
                int status = banned.getStatus();
                if(status == 1){
                	stringStatus = "Active";
                } else {
                	stringStatus = "Not Active";
                }
                
                // If ban end date is not null or empty, 
                // parse the end date from date time.
                // This was needed to get the excel file to work.
                String banEnd = banned.getBanEnd();
                if(banEnd != null && !banEnd.isEmpty()){
                	banEnd = dtc.parseDate(banEnd);
                }
                
        	  	row = sheet.createRow(nRow);
                // Create a cell and put a value in it.
                cell = row.createCell(0);
               
                cell.setCellValue(banned.getBanID());
                cell = row.createCell(1);
                cell.setCellValue(user.getMyID());
                cell = row.createCell(2);
                cell.setCellValue(dtc.parseDate(banned.getBanStart()));
                cell = row.createCell(3);
                cell.setCellValue(banEnd);
                cell = row.createCell(4);
                cell.setCellValue(banned.getPenaltyCount());
                cell = row.createCell(5);
                cell.setCellValue(banned.getDescription());
                cell = row.createCell(6);
                cell.setCellValue(stringStatus);
                cell = row.createCell(7);
                nRow++;
              }
             
              wb.write(out);
              out.close();
              System.out.println("End of ReportsExcelCreator");
              return "File downloaded successfully";
              
        }
        catch (Exception e) {
              return e.getMessage();
        }
    }
}
