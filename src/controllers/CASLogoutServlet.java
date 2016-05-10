package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CASLogoutServlet.  
 * This servlet will clear the cache, invalidate the session, and redirect to the CAS logout page.
 * There are also standalone methods to clear the browser cache on to be used on a selected servlet.
 * @author Brian Olaogun
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
		this.session.invalidate();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies
        
        // CAS logout URL
		response.sendRedirect("https://cas.dev.uga.edu/cas/logout");
		//response.sendRedirect("https://cas.stage.uga.edu/cas/logout");
		//response.sendRedirect("https://cas.uga.edu/cas/logout");
		
	}
	
	/**
	 * This method will clear browser cache and prevent the browser from going back to previous page.
	 * @param request HTTP Request
	 * @param response HTTP Response
	 * @throws IOException Java Exception
	 * @throws ServletException Servlet Exception
	 */
	public static void clearCache(HttpServletRequest request, HttpServletResponse response) 
		      throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
	}

}
