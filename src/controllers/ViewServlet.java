/* MLC View Servlet */

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.tomcat.jni.User;
import model.User;

import helpers.ReservationSelectQuery;
import helpers.UserHelper;
import helpers.ListUserReservationsQuery;


/**
 * Servlet implementation class ViewServlet
 */
@WebServlet(
		description = "lists the reservations for a student", 
		urlPatterns = { 
				"/ViewServlet", 
				"/View"
		})
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

	//private String MyID;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String message = " ";
		String table = "";
		
		//get our current session
		session = request.getSession();
		User user = (User) session.getAttribute("user");
			
		UserHelper uh = new UserHelper();
		System.out.println("View Servlet: just set up database connection");
		
		//see how many records the student has, and if none, set error message, and if has at least one, put reservations found in a table
		
		//get list of student's reservations
		//uh.listUserReservations(user.getUserRecordID());  

		//CREATE the methods getNumberRecords and listUserReservations
		int numRecords = uh.getNumberRecords(user.getUserRecordID());
		
		if (numRecords==0)	{
			message="No records returned";
		}
		else {
			table = uh.listUserReservations(user.getUserRecordID());
		}
		
		//forward our request along
		request.setAttribute("user", user);
		url = "user/view.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
