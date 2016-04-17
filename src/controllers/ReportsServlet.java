/* @Author Victoria Chambers */

package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
		//try{

	
		url = "admin/downloadreports.jsp";
		
		String action = request.getParameter("action");
		if ("bannedreport".equalsIgnoreCase(action)){
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=BannedStudents.csv");
			//ServletContext ctx = getServletContext();
			PrintWriter out = response.getWriter();
			//out.println("Banned List Button Test</br>");
			ExcelCreatorBanned ecb = new ExcelCreatorBanned();
			File bannedreport = ecb.downloadExcel();
			//out.println("data:application/octet-stream;base64," + toByteValue(bannedreport));
			//out.println("data:application/octet-stream;base64,");
			
			//Make Byte Buffer and write printwriter out to browser
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		//System.out.println(request);
		//}
		
		
	/**	catch {
			null = System.out.println("No Information");
		} */
	
	}

	private Object getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** private String toByteValue(File file)
			throws IOException {

		byte[] bytes = loadFile(file);
		
		String encodedString = new String(bytes);

		return encodedString;
	}

	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	} */

}
