package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingSelectQuery;
import helpers.ReservationSelectQuery;
import helpers.RoomsSelectQuery;
import model.Admin;
import model.DateTimeConverter;
import model.TimeConverter;

/**
 * Servlet implementation class AdminReservationsServlet2
 */
@WebServlet({ "/AdminReservationsServlet2", "/admin-reservations" })
public class AdminReservationsServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;    
    private String url = "";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReservationsServlet2() {
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
		session = request.getSession();
		
		// check to see if there is a valid session
		//if (session != null){ // there is an active session
			
			// get the role for the currently logged in admin user.
			//Admin adminUser = (Admin) session.getAttribute("adminUser");
			//String role = adminUser.getRole();
			//int status = adminUser.getAdminStatus();
			
			// push content based off role
			//if((role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("super admin")) && status == 1){
				//------------------------------------------------//
				/*               VIEW FOR ADMIN                   */
				//------------------------------------------------//
				String msg = "";
				String table = "";
				
				//------------------------------------------------//
				/*            BUILDING INFORMATION                */
				//------------------------------------------------//
				// get session and request variables + initialization of others
				String buildings = ""; // the string that contains the HTML drop down list
				String buildingID = request.getParameter("buildingID"); // get the value from request
				String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the drop down list
				String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
				
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				// if there is no buildingID from request, then display building drop down
				if (buildingID == null){
					buildingID = "1";
					int bldg = Integer.parseInt(buildingID);
					// query building
					
					bsq.doAdminBuildingRead();
					buildings = bsq.getBuildingResults(bldg);
		
				}
				// if there is a buildingID from session, it becomes the buildingID
				// if there is a buildingID selected from drop down, it becomes the buildingID
				if (buildingIDSelect != null){
					buildingID = buildingIDSelect;
					buildings = bsq.getBuildingResults(Integer.parseInt(buildingID)); // keep value selected in drop down.
				} else if (buildingIDSession != null){
					buildingID = buildingIDSession;
				} 
				
				//------------------------------------------------//
				/*              MAKE RESERVATION                  */
				//------------------------------------------------//
				// schedule request variables
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String startTime = request.getParameter("startTime");
				String endTime = request.getParameter("endTime");
				
				// schedule session variables
				String startDateSession = (String) session.getAttribute("startDate");
				String endDateSession = (String) session.getAttribute("endDate");
				String startTimeSession = (String) session.getAttribute("startTime");
				String endTimeSession = (String) session.getAttribute("endTime");
				
				// others
				String roomID = (String) request.getParameter("roomID");
				String reserveName = request.getParameter("reserveName");

				// convert date and time to SQL format
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				startTime = tc.convertTimeTo24(startTime);
				endTime = tc.convertTimeTo24(endTime);
				
				// if there is an active session variable, 
				// it will replace the request variable (which doesn't persist).
				if (startDateSession != null){
					session.removeAttribute(startDateSession);
					//startDate = startDateSession;
				}
				if (endDateSession != null){
					session.removeAttribute(startDateSession);
					//endDate = startDateSession;
				}
				if (startTimeSession != null){
					session.removeAttribute(startTime);
					//startTime = startTimeSession;
				}
				if (endTimeSession != null){
					session.removeAttribute(endTime);
					//endTime = endTimeSession;
				}
				System.out.println("ST:" + startTime);
				System.out.println("ET:" + endTime);
				
				// query for reservation check and listing of all rooms in a building.
				ReservationSelectQuery res = new ReservationSelectQuery();
				RoomsSelectQuery rsq = new RoomsSelectQuery();
				
				// list for the room number.  Below will print all times, inclusive between start and end
				List<String> roomNumber = rsq.roomList(Integer.parseInt(buildingID));
				List<Integer> times = dtc.timeRangeList(startTime, endTime);
				
				table += "<table id='' class='mdl-data-table' cellspacing='0' width='95%'>";
				table += "<thead>";
				table += "<tr>";
				table += "<th></th>";
				table += "<th>Room #</th>";
				table += "<th>Start Date</th>";
				table += "<th>Start Time</th>";
				table += "<th>End Date</th>";
				table += "<th>End Time</th>";
				table += "</tr>";
				table += "</thead>";
				table += "<tbody>";
				// loop through each room after all times have been checked 
				int h = 1; // counter
				for (int i = 0; i < roomNumber.size(); i++){
					// loop through each time then increment room
					for (int j =0; j < times.size(); j++){
						/*
						 * Check if there is a reservation at the time.
						 * If there isn't then returned is an empty string.
						 */
						// TODO add check for end date.
						res.doReservationRead(startDate, String.valueOf(times.get(j)), roomNumber.get(i));
						String reservationCheck = res.doReservationResults2();
						if(reservationCheck.isEmpty()){
							// only put in table when the current index at the last index.
							int currentIndex = times.get(j);
							int lastIndex = (times.size() - 1);
							if(currentIndex == lastIndex){
								// testing - printing to console
								System.out.println();
								System.out.println("DATE " + startDate);
								System.out.println("END " + endDate);
								System.out.println("ROOM NUMBER " + roomNumber.get(i));
								System.out.println("TIME " + times.get(j));
								System.out.println();
								
								table += "<tr>";
								table += "<td>" + h + "</td>";
								table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
								table += "<td>" + dtc.convertDateLong(dtc.slashDateConvert(startDate)) + "</td>";
								table += "<td>" + tc.convertTimeTo12(startTime) + "</td>";
								table += "<td>" + dtc.convertDateLong(dtc.slashDateConvert(endDate)) + "</td>";
								table += "<td>" + tc.convertTimeTo12(endTime) + "</td>";
								// TODO add form that will pass hidden parameters to make a reservation
								// hidden parameters will include above + building ID, roomID, adminID, and reserve name
								table += "</tr>";
								h++;
							}
						} else {
							msg = "There were no available rooms for the date and or time lenth specified.";
						}
					}
				}
				table += "</tbody>";
				table += "</table>";
				
				// forwarding URL
				url = "admin/reservations.jsp";
				
				// set session and request variables
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildings", buildings);
				session.setAttribute("startDate", startDate);
				session.setAttribute("endDate", endDate);
				session.setAttribute("startTime", startTime);
				session.setAttribute("endTime", endTime);
				session.setAttribute("reserveName", reserveName);
				session.setAttribute("tc", tc);
				session.setAttribute("msg", msg);
				session.setAttribute("table", table);
				
				
			//} else { 
				//------------------------------------------------//
				/*                VIEW FOR CLERK                  */
				//------------------------------------------------//
				
				// forwarding URL
				//url = "admin/reservations-clerk.jsp";
				
				// set session attributes
			//}
			
		//} else { // there isn't an active session.
			//url = "admin/adminLogin.jsp";
		//}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
