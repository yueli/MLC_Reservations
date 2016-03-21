package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingSelectQuery;

/**
 * Servlet implementation class AdminScheduleAddServlet2
 */
@WebServlet({ "/AdminScheduleAddServlet2", "/add-schedule" })
public class AdminScheduleAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleAddServlet() {
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
	
		//this.session = request.getSession(false); 
		this.session = request.getSession();
		// if this session is not null (active/valid)
		//if(this.session != null){
			// get session and request variables + initialization of others
			String buildings = ""; // the string that contains the HTML drop down list
			String buildingID = request.getParameter("buildingID"); // get the value from 
			String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the drop down list
			String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
			
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
			
			// forward the URL
			url = "admin/schedule-add.jsp";
			
			// set session and request variables
			session.setAttribute("buildingID", buildingID);
			session.setAttribute("buildings", buildings);
			
		// if session is null (not active/valid)	
		//} else {
			
			// go back to login
			//url = "[INSERT LOGIN PAGE HERE]";
			
		//}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
