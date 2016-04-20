package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BanGetUserInfoQuery;
import helpers.BanUserQuery;
import model.Admin;
import model.Banned;
import model.User;


//**By Ronnie Xu~****/
/**
 * Servlet implementation class banUserServlet
 */
@WebServlet(
		description = "Ban User", 
		urlPatterns = { 
				"/banUserServlet",  "/ban"
			
		})
public class BanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private HttpSession session;  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.session = request.getSession(false);
		
		
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
		
		String table ="";
		BanGetUserInfoQuery bguiq = new BanGetUserInfoQuery();
		User user = new User();
		//Get data from form
		int studentID = Integer.parseInt(request.getParameter("userID"));
		user = bguiq.userData(studentID);
		
		//Check if student is already banned or not
		
		boolean bannedAlready = bguiq.isUserBannedAlready(studentID);
		
	
		if(bannedAlready == true){
			table ="";
			table += user.getUserFirstName()+ " is already banned in the system. <br />";
			table += "Reason for ban: "+ bguiq.banDescription + ".";
			
		}
		//User is not banned
		else{
			table = "";
			table += "First Name: "+ user.getUserFirstName()+" <br />";
			table += "Last Name: "+ user.getUserLastName()+" <br />";
			table += "E-mail: "+ user.getUserEmail() + "<br />";
			
			//Hide ban inputs
			//Create Form for confirmation
			// java.util.Date today = new java.util.Date();
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.format(date);
			
			table += "<form name='ban' action=bansuccess method=post>";
			table += "<input type='hidden' name='userID' value='"+ user.getUserRecordID() +"'>";
//			table += "<input type='hidden' name='adminID' value='"+ loggedInAdminUser.getAdminID() +"'>";
			//ISSUE
			table += "<input type='hidden' name='adminID' value='1'>";
			table += "<input type='hidden' name='banStart' value='"+sdf.format(date) +"'>";
			table += "<input type='hidden' name='banEnd' value='1111-11-11 11:11:11'>";
			table += "<input type='hidden' name='penaltyCount' value='2'>";
			table += "Reason for ban<br /><input type='text' name='description' value=''>";
			table += "<input type='hidden' name='status' value='1'><br />";
			table += "<input class='btn btn-lg btn-red' type=submit name=submit value='Confirm Ban'></p></form>";
			table += "</table>";
		}
		
			request.setAttribute("table", table);
			
			String url = "/admin/banconfirm.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			
		
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
