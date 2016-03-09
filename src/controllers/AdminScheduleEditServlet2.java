package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminScheduleEditServlet2
 */
@WebServlet({ "/AdminScheduleEditServlet2", "/schedule-edit" })
public class AdminScheduleEditServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleEditServlet2() {
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
		this.session = request.getSession();
		String url = "";
		String msg = "";
		
		// values passed from schedule.jsp/AdminScheduleSelectQuery
		String scheduleID = request.getParameter("scheduleID");
		String buildingName = request.getParameter("buildingName");
		String buildingID = request.getParameter("buildingID");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String summary = request.getParameter("summary");
		String createdBy = request.getParameter("createdBy");
		
		// edited values from schedule-edit.jsp
		String startDateEdit  = request.getParameter("startDateEdit");
		String startTimeEdit  = request.getParameter("startTimeEdit");
		String endTimeEdit  = request.getParameter("endTimeEdit");
		String summaryEdit  = request.getParameter("summaryEdit");
		
		
		if (buildingName != null &&
				buildingID != null &&
				startTime != null &&
				endTime != null &&
				startDate != null &&
				endDate != null &&
				summary != null &&
				createdBy != null &&
				scheduleID != null){
			
			if (startDateEdit != null){
				// the start and end date have to be the same
				startDate = startDateEdit;
				endDate = startDateEdit;
			}
			if (startTimeEdit != null){
				startTime = startTimeEdit;
				createdBy = "admin"; // notate that admin made a change in database
			}
			if (endTimeEdit != null){
				endTime = startTimeEdit;
				createdBy = "admin"; // notate that admin made a change in database
			}
			if (summaryEdit != null){
				summary = summaryEdit;
				createdBy = "admin"; // notate that admin made a change in database
			}
	
			this.session.setAttribute("msg", msg);
			this.session.setAttribute("scheduleID", scheduleID);
			this.session.setAttribute("buildingName", buildingName);
			this.session.setAttribute("buildingID", buildingID);
			this.session.setAttribute("startTime", startTime);
			this.session.setAttribute("endTime", endTime);
			this.session.setAttribute("startDate", startDate);
			this.session.setAttribute("endDate", endDate);
			this.session.setAttribute("summary", summary);
			this.session.setAttribute("createdBy", createdBy);
			url = "admin/schedule-edit.jsp";
		} else {
			// send back to schedule list
			url = "Schedule";
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
