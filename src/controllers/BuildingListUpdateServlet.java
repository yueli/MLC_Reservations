package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingListSelectQuery;
import model.Building;
//**By Ronnie Xu~****/
/**
 * Servlet implementation class BuildingListUpdateServlet
 */
@WebServlet({ "/BuildingListUpdateServlet", "/updatebuilding" })
public class BuildingListUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListUpdateServlet() {
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
		
		int buildingID = Integer.parseInt(request.getParameter("buildingID"));
		
		BuildingListSelectQuery blsq = new BuildingListSelectQuery();
		
		blsq.doRead(buildingID);
		
		Building building = new Building();
		building = blsq.getBuilding();
		
		
		session.setAttribute("building", building);

		
		String url = "/admin/buildingupdate.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
