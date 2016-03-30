package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchConfirmResultsServlet
 */
@WebServlet({ "/SearchConfirmResultsServlet", "/searchconfrimresults" })
public class SearchConfirmResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchConfirmResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int Rooms_roomID = Integer.parseInt(request.getParameter("Rooms_roomID"));
		int Building_buildingID =  Integer.parseInt(request.getParameter("Building_buildingID"));
		String reserveStartDate = request.getParameter("reserveStartDate");
		String reserveEndDate = request.getParameter("reserveEndDate");
		String reserveStartTime =  request.getParameter("reserveStartTime");
		String reserveEndTime = request.getParameter("reserveEndTime");
		int hourIncrement = Integer.parseInt(request.getParameter("hourIncrement"));
		String free = request.getParameter("free");
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
