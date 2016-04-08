/* @author: Ginger Nix
 * 
 * This controller is called when someone checks into a room by scanning the QR code. They
 * have to login through CAS and if authenticated they are checked into a reserved room.
 * The code checks to see if the building exists, the room exists, the user is banned, and if they actually 
 * are on the reservation. If they pass all these checks they are sent to a QR success jsp page.
 * 
 */

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminUserHelper;
import helpers.ReservationQuery;
import helpers.UserHelper;
import model.Admin;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class QRLoginController
 */
@WebServlet("/QRLoginController")
public class QRLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private HttpSession session; 
    private String url;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QRLoginController() {
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

    	//get our current session
        session = request.getSession();
        String building = (String) session.getAttribute("building");
        String room = (String) session.getAttribute("room");
        		
		User user = (User) session.getAttribute("user");		

        session.removeAttribute("message");
    	String message = "";
    	       
        System.out.println("GQLoginController resv building = " + building);
        System.out.println("GQLoginController resv room = " + room);
        System.out.println("GQLoginController user my id = " + user.getMyID());
					 
			// check to see if this user was banned after making this 
        	// reservation but before checking into it
 
			UserHelper uh = new UserHelper();
			
			// we get the user object w/ MyID, fname, and lname
			// so get their user table record number to do checks below
						
			//int recordID = uh.getRecordID(user.getMyID());
			
			user.setUserRecordID(uh.getRecordID(user.getMyID()));
				   	
			if(uh.alreadyBanned(user.getUserRecordID())) {
				
				// since they have already been banned, send them to a page telling them 
				
				System.out.println("QR Login Controller already banned! " + user.getMyID() + " " + user.getUserFirstName() + " " + user.getUserLastName());				
	
				message = "This user " + user.getUserFirstName() + " " 
				+ user.getUserLastName()+ " has been banned and can not check into this room";
				
				session.setAttribute("message", message);
				session.setAttribute("user", user);
				url="user/bannedUser.jsp";
				
			}else{ // not banned - check if in user table

				boolean inTable = uh.inUserTable(user.getMyID());
			
				if (inTable){ // if in the user table update the last login filed
					
					uh.updateLastLogin(user.getMyID());	
					System.out.println("QR Login Controller in table " + user.getUserFirstName() + user.getUserLastName());	
				
				}else{ //not in user table so insert
					uh.insertUserTable(user.getMyID(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail());
					System.out.println("QR Login Controller NOT in user table recd id" + user.getMyID() + " " + user.getUserFirstName() + " " + user.getUserLastName() + " " + user.getUserEmail());	
				}
							
				// We now need to get the record id's of the user, building, and room to check for a reservation
				
				//since we know they are now in the table, get the user record id 
				int userRecordID = uh.getRecordID(user.getMyID());
				user.setUserRecordID(userRecordID);
				System.out.println("QR Login Controller get user Record ID =  " + userRecordID);			
	
				//create the reservation object
				Reservation reservation = new Reservation();		
				
				// get the building id for query for this building name
				ReservationQuery resvQuery = new ReservationQuery();
				int buildingID = 0;
				buildingID = resvQuery.getBuildingID(building);
				
				if (buildingID == 0){
					// ERROR there is no building record with this building QR name
					
					
					System.out.println("QR Login Controller building qr name doesn't exist " + building);
					
					
					url="user/qrError.jsp"; 
					message = "The building name " + building + " on the QR code doesn't exist. Alert the admins.";
					
					
					
				}else{
					// the building QR name is good and we have the building record ID :)
					
					// get the room id for query using room number and building id
					int roomID = 0;
					roomID = resvQuery.getRoomID(room, buildingID);
					
					System.out.println("QR Login Controller room qr # " + room + " has recd id = " + roomID);

					// check if room id = 0, and if so send to error page w/ message
					
					if (roomID == 0){ //have an error						
						
						System.out.println("QR Login Controller room qr name doesn't exist " + room);
						
						url="user/qrError.jsp"; 
						message = "The room number " + room + " for building " + building + " on the QR code doesn't exist. Alert the admins.";
						

						
					}else{ // found room record
						
						// check to see if there is a free reservation with this person as primary or secondary at this current date
						// and time in this building in this room
						int reservationID = 0;
						reservationID = resvQuery.getUserReservation(userRecordID, buildingID, roomID);
						
						if (reservationID == 0){ // didn't find reservation 
							
							System.out.println("QR Login Controller reservation not found ");
							
							url="user/qrError.jsp"; 
							message = "A reservation for room " + room + " in the building " + building + " was not found. "
									+ "This may be caused by: "
									+ "<ul>"
									+ "<li>this is the wrong building or room</li>"
									+ "<li>a reservation was not made for this room under this name</li>"
									+ "<li>you are too late to check in - you must check in within ten minutes past the reservation time</li>"
									+ "</ul>"
									+ "<p>See the FAQ for more information.</p>";
							
						
						}else{ 
							
							System.out.println("QR Login Controller reservation found - checking in ");
							
							if (resvQuery.checkInUser(reservationID, userRecordID)) { //if successfully checked in user
								
								url = "user/qrCheckInSuccess.jsp";
								
								message = "Successfully checked into the " + building + ", room " + room
										+ " for " + user.getUserFirstName() + " " + user.getUserLastName() + ". ";
								message += "<br /><br />";
								message	+= "<form action='https://cas.uga.edu/cas/logout'>";
								message += "<input type='submit' value='Log Out'>";
								message += "</form>";
								message += "<br /><br />";
								
							
							}else{
								
								url = "user/qrError.jsp";	
								message = "Error checking in. Contact administrators.";

								
							} //end if/else check them into the rooom
						
						} // end if/else reservation found
			        
					} // end if/else QR room # exists					
					
				} // end if/else building QR name exists
		     
			}//end if not banned user
			
			session.setAttribute("user", user);
			session.setAttribute("message", message);
	
			//forward our request along
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
        
    } // end do post

}
