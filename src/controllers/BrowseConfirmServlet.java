package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Building;
import model.DateTimeConverter;
import model.Email;
import model.Reservation;
import model.User;

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
		String currentDate = (String) session.getAttribute("currentDate");
		String buildingName = (String) session.getAttribute("buildingName");
		int buildingID = (Integer) session.getAttribute("buildingID");
		User user = (User) session.getAttribute("user");
		String hourIncrement = (String) request.getParameter("userIncrementSelected");
		String secondaryEmail = (String) request.getParameter("secondary");
		
		// error/validation message and forwarding url
		String url = "";
		String msg = "";
		// get roomID from roomNumber
		
		
		// verify inputed email
		if(!Email.isEmail(secondaryEmail)){
			msg = "Please enter a valid email";
			url = "user/reservation.jsp";
		}
		//-----------------------//
		// make a reservation
		//-----------------------//
		String free = "N";
		String endTime = DateTimeConverter.addTime(startTime, hourIncrement);
		String primaryUser = Integer.toString(user.getUserRecordID());
		// Reservation reservation = new Reservation(primaryUser, secondaryUser, roomsID, currentDate, currentDate, startTime, endTime, hourIncrement, buildingID, free);
		
		// send confirmation email
		Email email = new Email();
		String primaryEmail = user.getUserEmail();
		email.sendMail(primaryEmail, secondaryEmail, currentDate, startTime, endTime, buildingName, roomNumber);
		
		
		
		// set session attributes
		session.setAttribute("startTime", startTime);
		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("building", buildingName);
		session.setAttribute("hourIncrement", hourIncrement);
		session.setAttribute("secondaryEmail", secondaryEmail);
		session.setAttribute("msg", msg);
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
