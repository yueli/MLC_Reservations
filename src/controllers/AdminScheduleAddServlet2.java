package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminScheduleInsertQuery;
import helpers.AdminScheduleUpdateQuery;
import helpers.BuildingSelectQuery;
import model.Admin;
import model.DateTimeConverter;
import model.Schedule;
import model.TimeConverter;

/**
 * Servlet implementation class AdminScheduleAddServlet
 */
@WebServlet({ "/AdminScheduleAddServlet2", "/new-schedule" })
public class AdminScheduleAddServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleAddServlet2() {
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
		this.session = request.getSession(false); 
		
		// if this session is not null (active/valid)
		if(this.session != null){
			// get admin user from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
			String role = loggedInAdminUser.getRole();
			int status = loggedInAdminUser.getAdminStatus();
			
			// push content based off role
			if((role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("super admin")) && status == 1){
			
				// TODO Looping in multiple dates.
		
				// get session and request variables + initialization of others
				String buildings = ""; // the string that contains the HTML drop down list
				String buildingID = request.getParameter("buildingID"); // get the value from 
				String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the drop down list
				String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
				
				// schedule request variables
				int scheduleID; // get the schedule ID if there is already a matching entry.
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String startTime = request.getParameter("startTime");
				String endTime = request.getParameter("endTime");
				String summary = request.getParameter("summary");
				String createdBy = "admin";
				int allDayEvent = 0;
				
				// schedule session variables
				//String scheduleIDSession = ""; // get the schedule ID if there is already a matching entry.
				String startDateSession = (String) session.getAttribute("startDate");
				String endDateSession = (String) session.getAttribute("endDate");
				String startTimeSession = (String) session.getAttribute("startTime");
				String endTimeSession = (String) session.getAttribute("endTime");
				String summarySession = (String) session.getAttribute("summary");
				// others
				String msg = "";
				String yesButton = "";
				String noButton = "";
				String update = request.getParameter("update");
				
				// convert date and time to SQL format
				DateTimeConverter dtc = new DateTimeConverter();
				
				if (startDate != null && !startDate.isEmpty()){
					startDate = dtc.slashDateConvert(startDate);
				} else {
					startDate = dtc.parseDate(dtc.datetimeStamp());
				}
				if (endDate != null && !endDate.isEmpty()){
					endDate =  dtc.slashDateConvert(endDate);
				}
				
				// convert time to SQL format
				TimeConverter tc = new TimeConverter();
				
				// if there is an active session variable, 
				// it will replace the request variable (which doesn't persist).
				if (startDateSession != null){
					startDate = startDateSession;
				}
				if (endDateSession != null){
					endDate = startDateSession;
				}
				if (startTimeSession != null){
					startTime = startTimeSession;
				}
				if (endTimeSession != null){
					endTime = endTimeSession;
				}
				if (summarySession != null){
					summary = summarySession;
				}
				
				//------------------------------------------------//
				/*               SCHEDULE UPDATE                  */
				//------------------------------------------------//
				/*
				 * If the schedule entry already exist, the user will see a prompt asking 
				 * if they want to update the entry or not
				 * 
				 * if the user says yes, the schedule will be updated
				 */
				if (update != null && !update.isEmpty()){
					if(update.equalsIgnoreCase("yes")){
						// get schedule ID from request
						scheduleID = Integer.parseInt(request.getParameter("scheduleID"));
						
						// query to update entry instead
						AdminScheduleUpdateQuery suq = new AdminScheduleUpdateQuery();
						Schedule schedule = new Schedule(scheduleID, startDate, endDate, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy);
						suq.doScheduleUpdate(schedule);
						
						msg = "Schedule Updated!";
						
						session.removeAttribute("buildingID");
						session.removeAttribute("startDate");
						session.removeAttribute("endDate");
						session.removeAttribute("startTime");
						session.removeAttribute("endTime");
						session.removeAttribute("summary");
						session.removeAttribute("yesButton");
						session.removeAttribute("noButton");
						
						session.setAttribute("msg", msg);
						
						
						url = "add-schedule";
						
					} else if (update.equalsIgnoreCase("no")){
						msg = "Schedule was not updated.";
						
						session.removeAttribute("buildingID");
						session.removeAttribute("startDate");
						session.removeAttribute("endDate");
						session.removeAttribute("startTime");
						session.removeAttribute("endTime");
						session.removeAttribute("summary");
						session.removeAttribute("yesButton");
						session.removeAttribute("noButton");
						
						session.setAttribute("msg", msg);
						
						url = "add-schedule";
					}
				} else {
				//------------------------------------------------//
				/*            BUILDING INFORMATION                */
				//------------------------------------------------//
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				// if there is no buildingID from request, then display building drop down
				if (buildingID == null || buildingID.equals("0")){
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
				/*               SCHEDULE INSERT                  */
				//------------------------------------------------//
				int buildingIDInt = Integer.parseInt(buildingID);
				Schedule schedule = new Schedule(startDate, endDate, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy, allDayEvent, buildingIDInt);
				AdminScheduleInsertQuery siq = new AdminScheduleInsertQuery();
				String check = siq.scheduleInsertCheck(schedule);
				if (check.equalsIgnoreCase("false")){
					// if false, no entries in schedule table exist that
					// match parameters user entered
					msg = "Successfully added schedule!";
					siq.doScheduleInsert(schedule);
					url = "schedule";
				} else {
					// create a button that says yes or no
					yesButton = "<form name='doUpdate' action='new-schedule' method='post'>";
					yesButton += "<input name ='update' type='hidden' value='yes'>";
					yesButton += "<input name ='scheduleID' type='hidden' value='" + check + "'>";
					yesButton += "<input type='submit' name='Yes' value='Yes'>";
					yesButton += "</form>";
					
					noButton = "<form name='dontUpdate' action='new-schedule' method='post'>";
					noButton += "<input name ='update' type='hidden' value='no'>";
					noButton += "<input type='submit' name='No' value='No'>";
					noButton += "</form>";
					
					msg = "Entry for " + dtc.convertDateLong(startDate) + " exists, would you like to update the entry? ";
					
					url = "admin/schedule-add.jsp";
				}
				
				// set session and request variables
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildings", buildings);
				session.setAttribute("startDate", startDate);
				session.setAttribute("endDate", endDate);
				session.setAttribute("startTime", startTime);
				session.setAttribute("endTime", endTime);
				session.setAttribute("summary", summary);
				session.setAttribute("msg", msg);
				session.setAttribute("yesButton", yesButton);
				session.setAttribute("noButton", noButton);
				session.setAttribute("tc", tc);
				}
			} else { 
				//------------------------------------------------//
				/*                VIEW FOR CLERK                  */
				//------------------------------------------------//
				
				// forwarding URL
				url = "AdminViewReservations";
				
				// set session attributes
			}
			
		} else { // there isn't an active session.
			//------------------------------------------------//
			/*           VIEW FOR INVALID SESSION             */
			//------------------------------------------------//
			url = "http://ebus.terry.uga.edu:8080/MLC_Reservations";
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	

}
