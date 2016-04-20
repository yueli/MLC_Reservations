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

import helpers.AdminScheduleInsertQuery;
import helpers.AdminUpdateQuery;
import helpers.BuildingSelectQuery;
import model.Admin;
import model.DateTimeConverter;
import model.DbConnect;
import model.Schedule;
import model.TimeConverter;

/**
 * Servlet implementation class AdminScheduleAddServlet2.  This servlet will allow admins to add building hours.
 * @author Brian Olaogun
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

			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				
				// get the role for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
					
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
					
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
					
					// convert date and time to SQL format
					DateTimeConverter dtc = new DateTimeConverter();
					/*
					if (startDate != null && !startDate.isEmpty()){
						// if start date isn't null, convert to SQL format
						startDate = dtc.slashDateConvert(startDate);
					} else {
						// if start date is null, start date is the current date
						startDate = dtc.parseDate(dtc.datetimeStamp());
					}
					if (endDate != null && !endDate.isEmpty()){
						// if end date isn't null, convert to SQL format
						endDate =  dtc.slashDateConvert(endDate);
					} else {
						endDate = startDate;
					}*/
					if(startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()){
						if(DateTimeConverter.isAfter(startDate, endDate) == true){
							msg = "Please enter a <b>start date</b> that's less than the <b>end date</b>.";
							session.setAttribute("msg", msg);
							url = "admin/schedule-add.jsp";
						} else {
							// convert time to SQL format
							TimeConverter tc = new TimeConverter();
							
							// if there is an active session variable, 
							// it will replace the request variable (which doesn't persist).
							/*if (startDateSession != null){
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
							}*/
							System.out.println();
							System.out.println("Start Date in Schedule Add 2: " + startDate);
							System.out.println("End Date in Schedule Add 2: " + endDate);
							System.out.println();
							
							//------------------------------------------------//
							/*            BUILDING INFORMATION                */
							//------------------------------------------------//
							BuildingSelectQuery bsq = new BuildingSelectQuery();
							// if there is no buildingID from request, then display building drop down
							if (buildingID == null){
								buildingID = Integer.toString(bsq.getFirstBuildingID());
								System.out.println("BuildingID in Schedule add 2: getFirstBuilding: " + buildingID);
								int bldg = Integer.parseInt(buildingID);
								// query building
								
								bsq.doAdminBuildingRead();
								buildings = "<h3>Select a Building: </h3>" + bsq.getBuildingResults(bldg);
			
							}
							// if there is a buildingID from session, it becomes the buildingID
							// if there is a buildingID selected from drop down, it becomes the buildingID
							if (buildingIDSelect != null){
								buildingID = buildingIDSelect;
								buildings = bsq.getBuildingResults(Integer.parseInt(buildingID)); // keep value selected in drop down.
							} else if (buildingIDSession != null){
								buildingID = buildingIDSession;
							} 
							System.out.println("BuildingID in Schedule add 2: " + buildingID);
							
							//------------------------------------------------//
							/*               SCHEDULE INSERT                  */
							//------------------------------------------------//
							
							// date range
							List<String> dates = dtc.dateRangeList(startDate, endDate);
							for(int i=0; i < dates.size(); i++){
								String date = dates.get(i);
							    System.out.println("Print of Date range in Schedule Add 2:  " + date);
							    
							    int buildingIDInt = Integer.parseInt(buildingID);
							    System.out.println("START TIME IN ASAS2 " + startTime + " END TIME " + endTime);
							    System.out.println("*********");
							    System.out.println();
							    System.out.println("END TIME TO 24 " + tc.convertTimeTo24(endTime));
							    System.out.println();
							    System.out.println("*********");
							    Schedule schedule;
							    if(tc.convertTimeTo24(endTime).equals("00:00:00")){
									schedule = new Schedule(date, date, tc.convertTimeTo24(startTime), TimeConverter.subtractOneSecondToTime(tc.convertTimeTo24(endTime)), summary, createdBy, allDayEvent, buildingIDInt);

							    } else {
									schedule = new Schedule(date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy, allDayEvent, buildingIDInt);

							    }
							    
								//Schedule schedule = new Schedule(date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy, allDayEvent, buildingIDInt);
								
								// class used to check schedule entries and add schedule entries
								AdminScheduleInsertQuery siq = new AdminScheduleInsertQuery();
								
								// check entries before inserting
								String check = siq.scheduleInsertCheck(schedule);
								
								if (check.equalsIgnoreCase("false")){
									// if false, no entries in schedule table exist that
									// match parameters user entered
									msg = "Successfully added schedule for dates " + dtc.convertDateLong(dtc.slashDateConvert(startDate)) + " - " + dtc.convertDateLong(dtc.slashDateConvert(endDate)) + "!";
									siq.doScheduleInsert(schedule);
									
									session.removeAttribute("buildingID");
									session.removeAttribute("startDate");
									session.removeAttribute("endDate");
									session.removeAttribute("startTime");
									session.removeAttribute("endTime");
									session.removeAttribute("summary");
									
									url = "admin/schedule-add.jsp";
								} else {
									// entry in schedule table exist, get schedule ID and do an update
									scheduleID = Integer.parseInt(check);
									Schedule updateSchedule;
									if(tc.convertTimeTo24(endTime).equals("00:00:00")){
										updateSchedule = new Schedule(scheduleID, date, date, tc.convertTimeTo24(startTime), TimeConverter.subtractOneSecondToTime(tc.convertTimeTo24(endTime)), summary, createdBy);

									} else {
										updateSchedule = new Schedule(scheduleID, date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy);
									}
									//Schedule updateSchedule = new Schedule(scheduleID, date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy);
									AdminUpdateQuery suq = new AdminUpdateQuery();
									
									msg += "Successfully updated schedule for " + dtc.convertDateLong(date)+ ".<br>";
									suq.doScheduleUpdate(updateSchedule);
									
									session.removeAttribute("buildingID");
									session.removeAttribute("startDate");
									session.removeAttribute("endDate");
									session.removeAttribute("startTime");
									session.removeAttribute("endTime");
									session.removeAttribute("summary");
									
									session.setAttribute("msg", msg);
									url = "admin/schedule-add.jsp";
									
								}
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
							session.setAttribute("tc", tc);
						}
					} else if (startDate != null && !startDate.isEmpty()){
						// if start date isn't null, convert to SQL format
						startDate = dtc.slashDateConvert(startDate);
					} else if (endDate != null && !endDate.isEmpty()){
						// if end date isn't null, convert to SQL format
						endDate =  dtc.slashDateConvert(endDate);
					} else if (startDate == null || startDate.isEmpty()){
						// if start date is null, start date is the current date
						startDate = dtc.parseDate(dtc.datetimeStamp());
					} else if (endDate == null || endDate.isEmpty()){
						endDate = startDate;
					} else {
					// convert time to SQL format
					TimeConverter tc = new TimeConverter();
					
					// if there is an active session variable, 
					// it will replace the request variable (which doesn't persist).
					/*if (startDateSession != null){
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
					}*/
					System.out.println();
					System.out.println("Start Date in Schedule Add 2: " + startDate);
					System.out.println("End Date in Schedule Add 2: " + endDate);
					System.out.println();
					
					//------------------------------------------------//
					/*            BUILDING INFORMATION                */
					//------------------------------------------------//
					BuildingSelectQuery bsq = new BuildingSelectQuery();
					// if there is no buildingID from request, then display building drop down
					if (buildingID == null){
						buildingID = Integer.toString(bsq.getFirstBuildingID());
						System.out.println("BuildingID in Schedule add 2: getFirstBuilding: " + buildingID);
						int bldg = Integer.parseInt(buildingID);
						// query building
						
						bsq.doAdminBuildingRead();
						buildings = "<h3>Select a Building: </h3>" + bsq.getBuildingResults(bldg);
	
					}
					// if there is a buildingID from session, it becomes the buildingID
					// if there is a buildingID selected from drop down, it becomes the buildingID
					if (buildingIDSelect != null){
						buildingID = buildingIDSelect;
						buildings = bsq.getBuildingResults(Integer.parseInt(buildingID)); // keep value selected in drop down.
					} else if (buildingIDSession != null){
						buildingID = buildingIDSession;
					} 
					System.out.println("BuildingID in Schedule add 2: " + buildingID);
					
					//------------------------------------------------//
					/*               SCHEDULE INSERT                  */
					//------------------------------------------------//
					
					// date range
					List<String> dates = dtc.dateRangeList(startDate, endDate);
					for(int i=0; i < dates.size(); i++){
						String date = dates.get(i);
					    System.out.println("Print of Date range in Schedule Add 2:  " + date);
					    
					    int buildingIDInt = Integer.parseInt(buildingID);
					    System.out.println("START TIME IN ASAS2 " + startTime + " END TIME " + endTime);
					    System.out.println("*********");
					    System.out.println();
					    System.out.println("END TIME TO 24 " + tc.convertTimeTo24(endTime));
					    System.out.println();
					    System.out.println("*********");
					    Schedule schedule;
					    if(tc.convertTimeTo24(endTime).equals("00:00:00")){
							schedule = new Schedule(date, date, tc.convertTimeTo24(startTime), TimeConverter.subtractOneSecondToTime(tc.convertTimeTo24(endTime)), summary, createdBy, allDayEvent, buildingIDInt);

					    } else {
							schedule = new Schedule(date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy, allDayEvent, buildingIDInt);

					    }
					    
						//Schedule schedule = new Schedule(date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy, allDayEvent, buildingIDInt);
						
						// class used to check schedule entries and add schedule entries
						AdminScheduleInsertQuery siq = new AdminScheduleInsertQuery();
						
						// check entries before inserting
						String check = siq.scheduleInsertCheck(schedule);
						
						if (check.equalsIgnoreCase("false")){
							// if false, no entries in schedule table exist that
							// match parameters user entered
							msg = "Successfully added schedule for dates " + dtc.convertDateLong(dtc.slashDateConvert(startDate)) + " - " + dtc.convertDateLong(dtc.slashDateConvert(endDate)) + "!";							
							siq.doScheduleInsert(schedule);
							
							session.removeAttribute("buildingID");
							session.removeAttribute("startDate");
							session.removeAttribute("endDate");
							session.removeAttribute("startTime");
							session.removeAttribute("endTime");
							session.removeAttribute("summary");
							
							url = "admin/schedule-add.jsp";
						} else {
							// entry in schedule table exist, get schedule ID and do an update
							scheduleID = Integer.parseInt(check);
							Schedule updateSchedule;
							if(tc.convertTimeTo24(endTime).equals("00:00:00")){
								updateSchedule = new Schedule(scheduleID, date, date, tc.convertTimeTo24(startTime), TimeConverter.subtractOneSecondToTime(tc.convertTimeTo24(endTime)), summary, createdBy);

							} else {
								updateSchedule = new Schedule(scheduleID, date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy);
							}
							//Schedule updateSchedule = new Schedule(scheduleID, date, date, tc.convertTimeTo24(startTime), tc.convertTimeTo24(endTime), summary, createdBy);
							AdminUpdateQuery suq = new AdminUpdateQuery();
							
							msg += "Successfully updated schedule for " + dtc.convertDateLong(date)+ ".<br>";
							suq.doScheduleUpdate(updateSchedule);
							
							session.removeAttribute("buildingID");
							session.removeAttribute("startDate");
							session.removeAttribute("endDate");
							session.removeAttribute("startTime");
							session.removeAttribute("endTime");
							session.removeAttribute("summary");
							
							session.setAttribute("msg", msg);
							url = "admin/schedule-add.jsp";
							
						}
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
					session.setAttribute("tc", tc);
					}
				} else if (role.equalsIgnoreCase("C") && status == 1){ 
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
				CASLogoutServlet.clearCache(request, response);
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			CASLogoutServlet.clearCache(request, response);
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
	
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	

}
