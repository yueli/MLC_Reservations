package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.BuildingListAddQuery;
import model.Building;

/**
 * Servlet implementation class BuildingListAddServlet
 */
@WebServlet({ "/BuildingListAddServlet", "/addbuilding" })
public class BuildingListAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println("i got to the servlet");
		//Get Data from Form
		
		
		BuildingListAddQuery blaq = new  BuildingListAddQuery();
	
		
		//WORKING HERE...

		Building building = new Building();
		
		///Get buildingID
	
		int buildingID = blaq.getLastBuildingID();

		
		String buildingName = request.getParameter("buildingName");
		int buildingStatus =  Integer.parseInt(request.getParameter("buildingStatus"));
		String buildingCalName = request.getParameter("buildingCalName");
		String buildingCalUrl = request.getParameter("buildingCalUrl");
		//Set Admin PROBLEM
	
		
		//AddNewBuilding
		//building.setBuildingID(buildingID);
		//building.setBuildingName(buildingName);
		//building.setBuildingStatus(buildingStatus);
		//building.setBuildingCalName(buildingCalName);
		//building.setBuildingCalUrl(buildingCalUrl);
		//building.setAdmin(buildingAdmin);
		
		try {
			blaq.addBuildingList(buildingID, buildingName, buildingStatus, buildingCalName, buildingCalUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
		String url = "/buildinglist";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
