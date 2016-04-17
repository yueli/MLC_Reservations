/* @Author Victoria Chambers */

package controllers;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ExcelCreatorBanned;

/**
 * Servlet implementation class ReportsServlet
 */
@WebServlet({ "/ReportsServlet", "/Reports" })
public class ReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private String url;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsServlet() {
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
		
		ExcelCreatorBanned ecb = new ExcelCreatorBanned();
		ecb.downloadExcel();
	
		url = "admin/downloadreports.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	private Object getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
