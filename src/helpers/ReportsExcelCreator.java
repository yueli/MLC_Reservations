/* @Author Victoria Chambers */

package helpers;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.DbConnect;

import java.sql.Statement;

public class ReportsExcelCreator {

      //@SuppressWarnings("deprecation")
	
      public String downloadExcel(ServletOutputStream out){
           
    	  	int nRow = 1;
            String strQuery = null;
            Connection con = null;
           
            HSSFRow row;
            HSSFCell cell;
                 
            try {
                 System.out.println("Inside Try");
                  HSSFWorkbook wb = new HSSFWorkbook();
                  HSSFSheet sheet = wb.createSheet("Banned Students");
                 
                  /* Getting connection here for mysql database */
                  Class.forName("com.mysql.jdbc.Driver").newInstance();
                  //con = DbConnect.devCredentials();
                  con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/","viccham","Hitch1991*");
                  System.out.println("Got into server");
                 
                  if(con==null)
                        return "Connection Failed";
                  /* Database Query */               
                  strQuery = "select * from banned";
                  Statement stmt=con.createStatement();
                  ResultSet rs=stmt.executeQuery(strQuery);
               
  /**              // Setting Font Style for Header Row 
                  sheet.setColumnWidth(0, 5000);
                  sheet.setColumnWidth(1, 7000);
                  sheet.setColumnWidth(3, 5000);
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
                  cell.setCellValue("UserID");
                  cell.setCellStyle(cellStyle);
                 
                  cell = row.createCell(2);
                  cell.setCellValue("AdminID");
                  cell.setCellStyle(cellStyle);
                 
                  cell = row.createCell(3);
                  cell.setCellValue("Ban Start Date");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell(4);
                  cell.setCellValue("Ban End Date");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell(5);
                  cell.setCellValue("Penalty Count");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell(6);
                  cell.setCellValue("Description");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell(7);
                  cell.setCellValue("Status");
                  cell.setCellStyle(cellStyle);
                 
                 
                  // Reading one row of table at a time and putting the values into excel cell
                  while(rs.next()){
                        row = sheet.createRow(nRow);
                        // Create a cell and put a value in it.
                        cell = row.createCell(0);
                       
                        cell.setCellValue(rs.getString(1));
                        cell = row.createCell(1);
                        cell.setCellValue(rs.getString(2));
                        cell = row.createCell(2);
                        cell.setCellValue(rs.getString(3));
                        cell = row.createCell(3);
                        cell.setCellValue(rs.getString(4));
                        cell = row.createCell(4);
                        cell.setCellValue(rs.getString(5));
                        cell = row.createCell(5);
                        cell.setCellValue(rs.getString(6));
                        cell = row.createCell(6);
                        cell.setCellValue(rs.getString(7));
                        cell = row.createCell(7);
                        nRow++;
                  }
                  wb.write(out);
                  return "File downloaded successfully";
                  System.out.println("End of ReportsExcelCreator");
            }
            catch (Exception e) {
                  return e.getMessage();
            }
      }
}*//*
                  HSSFWorkbook wb = new HSSFWorkbook();
                  HSSFSheet sheet = wb.createSheet("Excel Sheet");
                  HSSFRow rowhead = sheet.createRow((short) 0);
                  rowhead.createCell((short) 0).setCellValue("BannedID");
                  rowhead.createCell((short) 1).setCellValue("UserID");
                  rowhead.createCell((short) 2).setCellValue("AdminID");
                  rowhead.createCell((short) 3).setCellValue("Ban Start Date");
                  rowhead.createCell((short) 4).setCellValue("Ban End Date");
                  rowhead.createCell((short) 5).setCellValue("Penalty Count");
                  rowhead.createCell((short) 6).setCellValue("Description");
                  rowhead.createCell((short) 7).setCellValue("Status"); */
              
                /*  int index = 1;
                  while (rs.next()) {

                          //HSSFRow row = sheet.createRow((short) index);
                          row.createCell((short) 0).setCellValue(rs.getInt(1));
                          row.createCell((short) 1).setCellValue(rs.getInt(2));
                          row.createCell((short) 2).setCellValue(rs.getInt(3));
                          row.createCell((short) 3).setCellValue(rs.getDateTime(4));
                          row.createCell((short) 4).setCellValue(rs.getDateTime(5));
                          row.createCell((short) 5).setCellValue(rs.getInt(6));
                          //row.createCell((short) 5).setCellValue(rs.getVarChar(7));
                          row.createCell((short) 6).setCellValue(rs.getInt(8));
                          index++;
                  }*/
                 /* FileOutputStream fileOut = new FileOutputStream("c:\\bannedstudents.xls");
                  wb.write(fileOut);
                  fileOut.close();
                  System.out.println("Data is saved in excel file.");
                  rs.close();
                  con.close();*/
                  
          } catch (Exception e) {
          }
			return strQuery; //delete this
  }
}
