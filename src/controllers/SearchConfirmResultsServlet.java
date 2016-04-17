package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingListQuery;
import model.Building;

/**
 * Servlet implementation class SearchConfirmResultsServlet
 */
@WebServlet({ "/SearchConfirmResultsServlet", "/searchconfirmresults" })
public class SearchConfirmResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchConfirmResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//session = request.getSession();
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		

		
		
		//END DATE and END TIME needs to be corrected (dummy numbers)
				int Rooms_roomID = Integer.parseInt(request.getParameter("Rooms_roomID"));
				int Building_buildingID =   Integer.parseInt(request.getParameter("Building_buildingID"));
				String reserveStartDate = request.getParameter("reserveStartDate");
				String reserveEndDate = request.getParameter("reserveEndDate");
				String reserveStartTime =  request.getParameter("reserveStartTime");
				String reserveEndTime = request.getParameter("reserveEndTime");
				int hourIncrement = Integer.parseInt(request.getParameter("hourIncrement"));
				String free = request.getParameter("free");
				
				
				
				
			
				String reserveName = "";
				
			
			
				
				//Create Form with infomration
				//Get Building Name from ID
				
				
				
				//HTML Table Confirmation
				String table ="<p>";
				table += "<form name='searchaddreservation' action=searchaddreservation method=get>";
				
				
				//table += "<input type='hidden' name='Rooms_roomID' value='"+ Rooms_roomID +"'>";
				//table += "<input type='hidden' name='Rooms_roomID' value='"+ Rooms_roomID +"'>";
				//table += "<input type='hidden' name='Rooms_roomID' value='"+ Rooms_roomID +"'>";
				
				table += "<input type='hidden' name='Rooms_roomID' value='"+ Rooms_roomID +"'>";
				table += "<input type='hidden' name='reserveStartDate' value='"+ reserveStartDate +"'>";
				table += "<input type='hidden' name='reserveEndDate' value='"+reserveEndDate +"'>";
				table += "<input type='hidden' name='reserveStartTime' value='"+ reserveStartTime +"'>";
				table += "<input type='hidden' name='reserveEndTime' value='"+reserveEndTime+"'>";
				table += "<input type='hidden' name='hourIncrement' value='"+ hourIncrement +"'>";
				table += "<input type='hidden' name='reserveName' value='"+ reserveName +"'>";
				table += "<input type='hidden' name='Building_buildingID' value='"+ Building_buildingID +"'>";
				table += "<input type='hidden' name='free' value='"+free+"'>";
				
				//Get Building Name
				BuildingListQuery blq = new BuildingListQuery();
				blq.doRead();
				String buildingName = blq.getBuildingName(Building_buildingID);
				
				//Get Room Name/Number
				blq.doReadRooms(Building_buildingID);
				String roomNumber = blq.getRoomName(Rooms_roomID);
				
				
				table +="Building Name: "+ buildingName +" <br .>";
				table +="Room#: "+ roomNumber +" <br .>";
				table +="Date: "+ reserveStartDate +" <br .>";
				table +="Start Time: "+ reserveStartTime +" <br .>";
				table +="End Time: "+ reserveEndTime +" <br .>";
				table +="Hours: "+ hourIncrement +" <br .>";
				
				table +="</p>";
				
				table += "<input class='btn btn-lg btn-red' type=submit name=submit value='Confirm'></p></form>";
				
				request.setAttribute("table", table);
				
				String url = "/user/searchconfirmation.jsp";
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
	}

}
