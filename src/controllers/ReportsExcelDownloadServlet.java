/* @Author Victoria Chambers */

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

<<<<<<< Updated upstream
import helpers.ReportsExcelCreator;
=======
@WebServlet(
		description = "Download Reports", 
		urlPatterns = { 
				"/DownloadReports"
		})
>>>>>>> Stashed changes

public class ReportsExcelDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private HttpSession session; 
	private String url;

    public ReportsExcelDownloadServlet() {
    }

    protected void doGet(HttpServletRequest request,
       HttpServletResponse response)
       throws ServletException, IOException {
<<<<<<< Updated upstream
    	doPost(request, response);
=======
    	System.out.println("Above doPost");
    	url = "user/confirmCancellation.jsp";	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
 //   	doPost(request,response);
>>>>>>> Stashed changes
      }

/**    protected void doPost(HttpServletRequest request,
       HttpServletResponse response)
       throws ServletException, IOException {
            String strMessage="";
            try
            {
                  response.reset();
                  response.setContentType("application/vnd.ms-excel");
                  response.setHeader("Content-Disposition",
                   "attachment;filename=BannedStudents.xls");
                  ReportsExcelCreator objDownload = new ReportsExcelCreator();
                  /* Calling method downloadExcel 
                  strMessage = objDownload.downloadExcel(response.getOutputStream());
                  request.setAttribute("Message",strMessage);
            }catch (Exception e)
            {
                  e.getMessage();
            }
      }*/
}