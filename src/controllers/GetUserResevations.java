package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetUserResevations
 */
@WebServlet("/GetUserResevations")
public class GetUserResevations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserResevations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		String message = " ";
		String table = "";
		
		//get our current session
		session = request.getSession();
		User user = (User) session.getAttribute("user");
			
		ListUserReservationsQuery lurq = new ListUserReservationsQuery();
		
		System.out.println("View Servlet: just set up database connection");
		
		//see how many records the student has, and if none, set error message, and if has at least one, 
		//put reservations found in a table

		table = lurq.ListUserReservations(user.getUserRecordID());
	}

}
