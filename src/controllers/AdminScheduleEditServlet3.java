package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminScheduleSelectQuery;
import helpers.AdminUpdateQuery;
import helpers.BuildingSelectQuery;
import model.Admin;
import model.DateTimeConverter;
import model.DbConnect;
import model.Schedule;
import model.TimeConverter;

/**
 * This servlet is used in Admin Building Hours Edit.  This servlet will
 * verify the user inputted values for for editing hours and run the query to 
 * update the database.
 * Servlet implementation class AdminScheduleEditServlet3
 * @author Brian Olaogun
 */
@WebServlet({ "/AdminScheduleEditServlet3", "/schedule-confirm" })
public class AdminScheduleEditServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleEditServlet3() {
        super();
        // TODO Auto-generated constructor stub
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
					
					//------------------------------------------------//
					/*              EDIT SCHEDULE CONT.               */
					//------------------------------------------------//
					
					session.removeAttribute("scheduleHeader");
					
					String scheduleID = (String) session.getAttribute("scheduleID");
					String buildingName = (String) session.getAttribute("buildingName");
					String buildingID = (String) session.getAttribute("buildingID");
					String startTime = (String) session.getAttribute("startTime");
					String endTime = (String) session.getAttribute("endTime");
					String startDate = (String) session.getAttribute("startDate");
					String endDate = (String) session.getAttribute("endDate");
					String summary = (String) session.getAttribute("summary");
					String createdBy = (String) session.getAttribute("createdBy");
					
					
					// edited values from schedule-edit.jsp
					//String startDateEdit  = request.getParameter("startDateEdit");
					String startTimeEdit  = request.getParameter("startTimeEdit");
					String endTimeEdit  = request.getParameter("endTimeEdit");
					String summaryEdit  = request.getParameter("summaryEdit");
					
					// Others
					TimeConverter tc = new TimeConverter();
					DateTimeConverter dtc = new DateTimeConverter();
					String msg = "";
					String newSchedule = "";

					
					if(startDate == null || startDate.isEmpty()){
						
						msg = "Please enter a date. A date is required.";
						url = "admin/schedule-edit.jsp";
						
					} else if (startTimeEdit == null || startTimeEdit.isEmpty() || endTimeEdit == null || endTimeEdit.isEmpty()){
						
						msg = "Please enter a start time <b>and</b> end time.";
						url = "admin/schedule-edit.jsp";
						
					} else if (summaryEdit == null || summaryEdit.isEmpty()){
						
						msg = "Please enter a summary.  A summary is required.";
						url = "admin/schedule-edit.jsp";
						
					} else {
						
						// get scheduleID from session
						scheduleID = (String) session.getAttribute("scheduleID");
						
						// place variables from jsp (user altered) into standard variables
						// the start and end date have to be the same
						//startDate = startDateEdit;
						endDate = startDate;
						
						// convert from 24-hour time
						startTimeEdit = tc.convertTimeTo24(startTimeEdit);
						startTime = startTimeEdit;
					
						// convert to 24-hour time
						endTimeEdit = tc.convertTimeTo24(endTimeEdit);
						endTime = endTimeEdit;
						
						summary = summaryEdit;
						createdBy = "admin"; // notate that admin made a change in database
						
						/* 
						 * Add the 59 seconds from the time selector.
						 * Added a custom time of 11:59pm and can't add the seconds to it
						 * so doing it in java.
						 */
						if(endTime.equals("23:59:00")){
							endTime = "23:59:59";
						}
						
						BuildingSelectQuery bsq = new BuildingSelectQuery();
						
						// get building name
						buildingName = bsq.getBuildingNameFromID(Integer.parseInt(buildingID));
						msg = "Successfully edited hours for " + dtc.convertDateLong(startDate) + ".";
						
						// Update Schedule edits into database
						int scheduleInt = Integer.parseInt(scheduleID);
						Schedule schedule = new Schedule(scheduleInt, startDate, endDate,
								startTime, endTime, summary, createdBy);
						AdminUpdateQuery suq = new AdminUpdateQuery();
						suq.doScheduleUpdate(schedule);
						
						// list hours and have the edit hour only listed in the table
						AdminScheduleSelectQuery ssq = new AdminScheduleSelectQuery();
						ssq.doRead(buildingID, dtc.convertToSlashed(endDate), dtc.convertToSlashed(startDate));
						newSchedule = ssq.listSchedule();
						
						url = "admin/schedule.jsp";
					
					}
					
					this.session.setAttribute("msg", msg);
					this.session.setAttribute("tc", tc);
					this.session.setAttribute("scheduleID", scheduleID);
					this.session.setAttribute("buildingName", buildingName);
					this.session.setAttribute("buildingID", buildingID);
					this.session.setAttribute("startTime", startTime);
					this.session.setAttribute("endTime", endTime);
					this.session.setAttribute("startDate", startDate);
					this.session.setAttribute("endDate", endDate);
					this.session.setAttribute("summary", summary);
					this.session.setAttribute("createdBy", createdBy);
					this.session.setAttribute("schedule", newSchedule);
					
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
