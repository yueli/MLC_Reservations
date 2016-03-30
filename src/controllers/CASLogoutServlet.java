package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CASLogoutServlet
 */
@WebServlet({ "/CASLogoutServlet", "/Logout" })
public class CASLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CASLogoutServlet() {
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
		this.session = request.getSession(false);
		logout(this.session);
		
	}
	
	public String logout(HttpSession session){
		this.session.invalidate();
		return "redirect:https://cas.dev.uga.edu/cas/logout";
	}
}
