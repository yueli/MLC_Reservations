package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.ReportsExcelCreator;

public class ReportsExcelDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsExcelDownloadServlet() {
    }

    protected void doGet(HttpServletRequest request,
       HttpServletResponse response)
       throws ServletException, IOException {
    	doPost(request, response);
      }

    protected void doPost(HttpServletRequest request,
       HttpServletResponse response)
       throws ServletException, IOException {
            String strMessage="";
            try
            {
                  response.reset();
                  response.setContentType("application/vnd.ms-excel");
                  response.setHeader("Content-Disposition",
                   "attachment;filename=BannedStudents.xls");
                  ReportsExcelCreator objEDH=new ReportsExcelCreator();
                  /* Calling method downloadExcel */
                  strMessage=objEDH.downloadExcel(response.getOutputStream());
                  request.setAttribute("Message",strMessage);
            }catch (Exception e)
            {
                  e.getMessage();
            }
      }
}