package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import helpers.BuildingListUpdateQuery;
import model.Building;

/**
 * Servlet implementation class BuildingListUpdateServlet
 */
@WebServlet({ "/BuildingListBuildingUpdateServlet", "/updatingbuilding" })
public class BuildingListBuildingUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListBuildingUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//Get current building
		 Building building = (Building) session.getAttribute("building");
		
		
		 building.setBuildingName(request.getParameter("buildingName"));
		 building.setBuildingStatus(Integer.parseInt(request.getParameter("buildingStatus")));
		 building.setBuildingCalName(request.getParameter("buildingCalName"));
		 building.setBuildingCalUrl(request.getParameter("buildingCalUrl"));
		 
		 BuildingListUpdateQuery bluq = new BuildingListUpdateQuery();
		 bluq.doUpdate(building);
		 
		 
		String url = "buildinglist";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
