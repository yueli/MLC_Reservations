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
		//doGet(request, response);
		session = request.getSession();
		
		//END DATE and END TIME needs to be corrected (dummy numbers)
				int Rooms_roomID = Integer.parseInt(request.getParameter("Rooms_roomID"));
				String Building_buildingID =  request.getParameter("Building_buildingID");
				String reserveStartDate = request.getParameter("reserveStartDate");
				String reserveEndDate = request.getParameter("reserveEndDate");
				String reserveStartTime =  request.getParameter("reserveStartTime");
				String reserveEndTime = request.getParameter("reserveEndTime");
				int hourIncrement = Integer.parseInt(request.getParameter("hourIncrement"));
				//int hourIncrement = 1;
				String free = request.getParameter("free");
				
				//NEED INFO 
				String RID = "1";
				int PID = 1;
				int SID = 2;
				int AID = 0;
				String reserveName = "";
				
				session.setAttribute("rReserveID", RID);
				session.setAttribute("rPrimaryUser", PID);
				session.setAttribute("rSecondaryUser", SID);
				session.setAttribute("rAdminID", AID);
			
				session.setAttribute("rRoomID", Rooms_roomID);
				
				session.setAttribute("rStartDate", reserveStartDate);
				session.setAttribute("rEndDate", reserveEndDate);
				session.setAttribute("rReserveStartTime", reserveStartTime);
				session.setAttribute("rReserveEndTime", reserveEndTime);
				
				session.setAttribute("rHourIncrement",hourIncrement);
				session.setAttribute("rReserveName", reserveName);
				
				
				session.setAttribute("rBuildingID", Building_buildingID);
				session.setAttribute("rFree", free);
				
				
				
				//Create Form with infomration
				//Get Building Name from ID
				
				
				
				//HTML Table Confirmation
				String table ="<p>";
				table += "<form name='searchaddreservation' action=searchaddreservation method=get>";
				
				table +="Building: "+ Building_buildingID +" <br .>";
				table +="Room#: "+ Rooms_roomID +" <br .>";
				table +="Date: "+ reserveStartDate +" <br .>";;
				table +="Start Time: "+ reserveStartTime +" <br .>";
				table +="End Time: "+ reserveEndTime +" <br .>";
				table +="Hours: "+ hourIncrement +" <br .>";;
				
				table +="</p>";
				
				table += "<input class='btn btn-lg btn-red' type=submit name=submit value='Confirm'></p></form>";
				
				request.setAttribute("table", table);
				
				String url = "/user/searchconfirmation.jsp";
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
	}

}
