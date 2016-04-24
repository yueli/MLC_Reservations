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

import model.Schedule;
import model.TimeConverter;
import model.DateTimeConverter;
import model.DbConnect;
import model.Building;


/**
 * 
 * @author Victoria Chambers
 * This Class is used to generate the reports for admins.
 *
 */
public class ReportsExcelCreatorSchedule {
  
  /**
   * This method is used to download reports on schedule students  
   * @param out Output stream for sending binary data to the client.
   * @return Excel formatted result set that is used in conjuction with Java file writer.
   */
  public String downloadSchedule(ServletOutputStream out){
    
      int nRow = 1;
        String strQuery = null;
        Connection connection = null;
       
        HSSFRow row;
        HSSFCell cell;
             
        try {
              HSSFWorkbook wb = new HSSFWorkbook();
              HSSFSheet sheet = wb.createSheet("Schedule Students");
             
              // Getting connection here for mySQL database 
              Class.forName("com.mysql.jdbc.Driver").newInstance();
              connection = DbConnect.devCredentials();
             
              if(connection == null){
                return "Connection Failed";
              }
              // Database Query  
              strQuery = "SELECT Schedule.scheduleID, "
              		+ "Schedule.allDayEvent, "
              		+ "Schedule.startDate, "
              		+ "Schedule.endDate, "
              		+ "Schedule.startTime, "
              		+ "Schedule.endTime, "
              		+ "Schedule.summary, "
              		+ "Schedule.createdBy, "
              		+ "Building.buildingID "
              		+ "FROM tomcatdb.Schedule, tomcatdb.Building "
              		+ "WHERE Schedule.Building_buildingID = Building.buildingID ";
              
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
              sheet.setColumnWidth(8, 5000);
              
              // Creating the Font Style here 
              HSSFFont boldFont = wb.createFont();
              boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              boldFont.setColor(HSSFFont.COLOR_RED);
              HSSFCellStyle cellStyle = wb.createCellStyle();
              cellStyle.setFont(boldFont);
             
             
              // Create a row for header
              row = sheet.createRow(0);
             
              cell = row.createCell(0);
              cell.setCellValue("Schedule Id");
              cell.setCellStyle(cellStyle);
             
              cell = row.createCell(1);
              cell.setCellValue("All Day Event");
              cell.setCellStyle(cellStyle);
             
              cell = row.createCell(2);
              cell.setCellValue("Start Date");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(3);
              cell.setCellValue("End Date");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(4);
              cell.setCellValue("Start Time");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(5);
              cell.setCellValue("End Time");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(6);
              cell.setCellValue("Summary");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(7);
              cell.setCellValue("Created By");
              cell.setCellStyle(cellStyle);
              
              cell = row.createCell(8);
              cell.setCellValue("Building ID");
              cell.setCellStyle(cellStyle);
             
             
              // Reading one row of table at a time and putting the values into excel cell
              while(rs.next()){
            	TimeConverter tc = new TimeConverter();
            	DateTimeConverter dtc = new DateTimeConverter();
            	
        	  	Schedule schedule = new Schedule();
                schedule.setScheduleID(rs.getInt(1));
                schedule.setAllDayEvent(rs.getInt(2));
        	  	schedule.setStartDate(rs.getString(3));
                schedule.setEndDate(rs.getString(4));
                schedule.setStartTime(rs.getString(5));
                schedule.setEndTime(rs.getString(6));
        	  	schedule.setSummary(rs.getString(7));
        	  	schedule.setCreatedBy(rs.getString(8));
        	  	schedule.setBuildingID(rs.getInt(9));
                
        	  	row = sheet.createRow(nRow);
                // Create a cell and put a value in it.
                cell = row.createCell(0);
               
                cell.setCellValue(schedule.getScheduleID());
                cell = row.createCell(1);
                cell.setCellValue(schedule.getAllDayEvent());
                cell = row.createCell(2);
                cell.setCellValue(dtc.convertDateLong(schedule.getStartDate()));
                cell = row.createCell(3);
                cell.setCellValue(dtc.convertDateLong(schedule.getEndDate()));
                cell = row.createCell(4);
                cell.setCellValue(tc.convertTimeTo12(schedule.getStartTime()));
                cell = row.createCell(5);
                cell.setCellValue(tc.convertTimeTo12(schedule.getEndTime()));
                cell = row.createCell(6);
                cell.setCellValue(schedule.getSummary());
                cell = row.createCell(7);
                cell.setCellValue(schedule.getCreatedBy());
                cell = row.createCell(8);
                cell.setCellValue(schedule.getBuildingID());
                cell = row.createCell(9);
                nRow++;
              }
             
              wb.write(out);
              out.close();
              System.out.println("End of ReportsExcelCreatorSchedule");
              return "File downloaded successfully";
              
        }
        catch (Exception e) {
              return e.getMessage();
        }
    }
}
