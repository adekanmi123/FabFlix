import javax.servlet.*;

import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.text.*;
import javax.servlet.http.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * Servlet implementation class AddStar
 */
@WebServlet("/AddStar")
public class AddStar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddStar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out2 = response.getWriter();

		try {
			// the following commented lines are direct connections without pooling
			//String user = "mytestuser";
			//String pass = "mypassword";
			//String url = "jdbc:mysql://localhost:3306/moviedb";

			//Class.forName("com.mysql.jdbc.Driver").newInstance();

			//Connection dbcon = DriverManager.getConnection(url, user, pass);

			// the following few lines are for connection pooling
            // Obtain our environment naming context

            Context initCtx = new InitialContext();
            if (initCtx == null)
                out2.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out2.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Master");
            
            if (ds == null)
                out2.println("ds is null.");

            Connection dbcon = ds.getConnection();
            if (dbcon == null)
                out2.println("dbcon is null.");

			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String firstName = request.getParameter("first_name");
			String lastName = request.getParameter("last_name");
			String DOB = request.getParameter("dob");
			String photoURL = request.getParameter("photo_url");
			java.sql.Date sqlDate;
			HttpSession session = request.getSession(false);

			if (DOB.equals("")) {

				String selectString = "SELECT * FROM stars WHERE first_name = " + "\"" + firstName + "\"" + " AND last_name = "
						+ "\"" + lastName + "\"" + " AND dob IS " + "NULL" + ";";
				
				System.out.println(selectString);

				try (PreparedStatement selectStar = dbcon.prepareStatement(selectString)) {
					ResultSet result = selectStar.executeQuery();

					if (result.next()) {
						out.println("ERROR: STAR ALREADY EXISTS");
						return;
					}

				}

				String insertString = "INSERT INTO stars (first_name, last_name, dob, photo_url) VALUES (?, ?, ?, ?)";
				try (PreparedStatement insertStar = dbcon.prepareStatement(insertString)) {

					insertStar.setString(1, firstName);

					insertStar.setString(2, lastName);

					insertStar.setDate(3, null);

					if (photoURL.equals("")) {
						insertStar.setString(4, null);
					} else {
						insertStar.setString(4, photoURL);
					}

					insertStar.executeUpdate();

					out.println(firstName + " " + lastName + " was successfully added." + "<br>");
					PreparedStatement selectID = dbcon.prepareStatement("SELECT * FROM stars ORDER BY id DESC LIMIT 1");
					ResultSet printID = selectID.executeQuery();
					printID.next();
					out.println(firstName + " " + lastName + "'s ID is " + printID.getInt(1));
				} catch (SQLException e) {
					out.println("ERROR: " + firstName + " " + lastName + " was not added to the database.");
				}
			} else {

				String selectString = "SELECT * FROM stars WHERE first_name = " + "\"" + firstName + "\"" + " AND last_name = "
						+ "\"" + lastName + "\"" + " AND dob = " + "\"" + DOB + "\"" + ";";

				try (PreparedStatement selectStar = dbcon.prepareStatement(selectString)) {
					ResultSet result = selectStar.executeQuery();

					if (result.next()) {
						out.println("ERROR: STAR ALREADY EXISTS");
						return;
					}

				}

				try {
					DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
					formatter.setLenient(false);
					java.util.Date date = formatter.parse(DOB);
					sqlDate = new java.sql.Date(date.getTime());
				} catch (ParseException e) {
					out.println(
							"ERROR: Invalid date. Please enter date in yyyy/MM/dd format and make sure it is a valid date.");
					return;
				}

				String insertString = "INSERT INTO stars (first_name, last_name, dob, photo_url) VALUES (?, ?, ?, ?)";
				try (PreparedStatement insertStar = dbcon.prepareStatement(insertString)) {

					insertStar.setString(1, firstName);

					insertStar.setString(2, lastName);

					insertStar.setDate(3, sqlDate);

					if (photoURL.equals("")) {
						insertStar.setString(4, null);
					} else {
						insertStar.setString(4, photoURL);
					}

					insertStar.executeUpdate();

					out.println(firstName + " " + lastName + " was successfully added." + "<br>");
					PreparedStatement selectID = dbcon.prepareStatement("SELECT * FROM stars ORDER BY id DESC LIMIT 1");
					ResultSet printID = selectID.executeQuery();
					printID.next();
					out.println(firstName + " " + lastName + "'s ID is " + printID.getInt(1));
					
					insertStar.close();
					selectID.close();
					printID.close();
					
				} catch (SQLException e) {
					out.println("ERROR: " + firstName + " " + lastName + " was not added to the database.");
				}
			}
			
			dbcon.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
