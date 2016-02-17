package controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DateTimeConverter;

/**
 * Servlet implementation class BrowseConfirmServlet
 */
@WebServlet({ "/BrowseConfirmServlet", "/BrowseConfirm" })
public class BrowseConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseConfirmServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String startTime = (String) session.getAttribute("startTime");
		String roomNumber = (String) session.getAttribute("roomNumber");
		String building = (String) session.getAttribute("building");
		String hourIncrement = (String) request.getParameter("incrementSel");
		String secondaryEmail = (String) request.getParameter("secondary");
		
		// get roomID from roomNumber
		
		// get buildingID from buildingName
		
		
		String free = "N";
		// get current date and time
		DateTimeConverter dtc = new DateTimeConverter();
		String currentDate = dtc.parseDate(dtc.datetimeStamp());
		
	}

}
