package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.ReservationInsertQuery;

/**
 * Servlet implementation class SearchAddReservation
 */
@WebServlet({ "/SearchAddReservation", "/searchaddreservation" })
public class SearchAddReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAddReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		//Insert Data
		ReservationInsertQuery riq = new ReservationInsertQuery();
		
		
		
		int Rooms_roomID = Integer.parseInt(request.getParameter("rRooms_roomID"));
		String Building_buildingID =  request.getParameter("rBuilding_buildingID");
		String reserveStartDate = request.getParameter("rReserveStartDate");
		String reserveEndDate = request.getParameter("rReserveEndDate");
		String reserveStartTime =  request.getParameter("rReserveStartTime");
		String reserveEndTime = request.getParameter("rReserveEndTime");
		int hourIncrement = Integer.parseInt(request.getParameter("rhourIncrement"));
		//int hourIncrement = 1;
		String free = request.getParameter("free");
		/*
		 
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
		 */
		
	}

}
