
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ReportsExcelCreatorAdminAdmin;
import model.Admin;
import model.DbConnect;

@WebServlet(description = "Download Reports for Admin", urlPatterns = { "/DownloadReportsAdmin" })
/**
 * This servlet will download the actual file when the download button is clicked on
 * downloadreports.jsp
 * @author Victoria Chambers
 *
 */
public class ReportsDownloadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private HttpSession session; 
    private String url;

    public ReportsDownloadFileServlet() {
    
    }
   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession(false);
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session

			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				// get the role for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
					
					String adminList = request.getParameter("adminList"); // value of hidden input in adminForm
					
					// if the download admin button is clicked, download file
					if(adminList != null && !adminList.isEmpty()){
						String strMessage = "";
			            try {	
							response.reset();
							response.setContentType("application/vnd.ms-excel");
							response.setHeader("Content-Disposition", "attachment;filename=BannedStudents.xls");
							ReportsExcelCreatorAdmin rec = new ReportsExcelCreatorAdmin();
							  
							//Calling method download admin list 
							strMessage = rec.downloadAdminList(response.getOutputStream());
							request.setAttribute("Message",strMessage);
							
							// the url to forward
							url = "admin/downloadreports.jsp";
							
							// since outputting to response, a return is needed
							return;
			
			            } catch (Exception e) {
			            	e.getMessage();
			            }
					
					}
				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					
					// forwarding URL
					url = "AdminViewReservations";
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
					//------------------------------------------------//
					// if a new session is created with no user object passed
					// user will need to login again
					session.invalidate();
					
					response.sendRedirect(DbConnect.urlRedirect());
					return;
				}
					
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}
}
