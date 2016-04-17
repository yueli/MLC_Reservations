package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingListQuery;
import helpers.ReservationInsertQuery;
import model.Building;
import model.DbConnect;
import model.Email;
import model.Reservation;
import model.TimeConverter;
import model.User;

/**
 * Servlet implementation class SearchAddReservation
 */
@WebServlet({ "/SearchAddReservation", "/searchaddreservation" })
public class SearchAddReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAddReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("Resevation Set");
		

		int tempPUSER = 1;
		int tempSUSER = 2;

		int Rooms_roomID = Integer.parseInt(request.getParameter("Rooms_roomID"));
		String reserveStartDate =  request.getParameter("reserveStartDate");
		String reserveEndDate = request.getParameter("reserveEndDate");
		String reserveStartTime = request.getParameter("reserveStartTime");
		String reserveEndTime = request.getParameter("reserveEndTime");
		int hourIncrement =  Integer.parseInt(request.getParameter("hourIncrement"));
		int Building_buildingID =  Integer.parseInt(request.getParameter("Building_buildingID"));
		String free = request.getParameter("free");
		
		//Create Reservation Object
		System.out.println("Resevation Set");
		
		Building building = new Building();
		building.setBuildingID(Building_buildingID);

		
		
		Reservation reservation = new Reservation(tempPUSER, tempSUSER, Rooms_roomID, reserveStartDate, reserveEndDate, reserveStartTime , TimeConverter.subtractOneSecondToTime(reserveEndTime), hourIncrement, Building_buildingID, free);
		
		//Get Building Name
		BuildingListQuery blq = new BuildingListQuery();
		blq.doRead();
		String buildingName = blq.getBuildingName(Building_buildingID);
		
		//Get Room Name/Number
		blq.doReadRooms(Building_buildingID);
		String roomNumber = blq.getRoomName(Rooms_roomID);
		
		
		
		String table = "";
		table += "Reservation Succesful! <br />";
		table += "Building Name: "+buildingName+"<br />";
		table += "Room Number: "+roomNumber+"<br />";
		table += "Date: "+reserveStartDate+"<br />";
		table += "Time: "+ reserveStartTime+"<br />";
		table += "<br />";
		
		
		
		//Insert Data
		ReservationInsertQuery riq = new ReservationInsertQuery();
		riq.doReservationInsert(reservation);
		System.out.println("We just inserted");
		
		//Send Email
		
		// class used to send email
		/*
		 * RONNIE, in order to send an email, you need to get the primary user email from the session object
		 * and get the secondary users email from the myID
		 * 
		 * User primaryUser = (User) session.getAttribute("user");
		 * String primaryEmail = primaryUser.getUserEmail();
		 * 
		 * To get the secondary's info, you will need their myID
		 * 
		 * 1st check to see if the secondary's myID is in the user table
		 * 
		 * UserHelper uh = new UserHelper();
		 * If the user is not in a table, display a message similar to the one below and have them enter another myID 
		 * if(!uh.inUserTable(secondaryMyID)){
				msg = "Please have " + secondaryMyID + " login once into the application. <br>"
						+ "Logging in once serves as a form of user registration.<br> Once " + secondaryMyID 
						+ " has logged in once, you can add them to any future reservation. ";
			}
		 * User secondaryUser = uh.getUserInfo(secondaryMyID);
		 * String secondaryEmail = secondaryUser.getUserEmail();
		 * 
		 * Also do a check to make sure that the person logged in doesn't use their myID as the secondary user.
		 * 
		 * Also for email to work, you need the room number
		 */
		
		/*Email email = new Email();
		email.setWebsiteURL(DbConnect.urlRedirect());
		email.sendMail(primaryEmail, secondaryEmail, reserveStartDate, reserveStartTime, reserveEndTime, buildingName, roomNumber, email.getWebsiteURL()); */
		
		//Redirect to home page? with message?
		
		
		request.setAttribute("table", table);
		
		String url = "/user/searchsuccess.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
