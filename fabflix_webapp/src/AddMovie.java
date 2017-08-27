import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddMovie() {
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
		HttpSession session = request.getSession(false);

		PrintWriter out = response.getWriter();
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
                out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Master");
            
            if (ds == null)
                out.println("ds is null.");

            Connection dbcon = ds.getConnection();
            if (dbcon == null)
                out.println("dbcon is null.");
			
			String dob = request.getParameter("dob");

			
			
			if (dob == "") {
				
				CallableStatement addMovies = dbcon.prepareCall("{call add_movie_without_dob(?,?,?,?,?,?,?,?,?,?)}");
				
				addMovies.registerOutParameter(10, Types.INTEGER);
				
				
				String first_name = request.getParameter("first_name");
				String last_name = request.getParameter("last_name");

				String photo_url = request.getParameter("photo_url");
				String title = request.getParameter("title");
				String year = request.getParameter("year");
				String director = request.getParameter("director");
				String banner_url = request.getParameter("banner_url");
				String trailer_url = request.getParameter("trailer_url");
				String genre = request.getParameter("genre");

				int intYear = 0;

				try {
					intYear = Integer.parseInt(year);
				} catch (Exception e) {
					out.println(
							"ERROR: Invalid year. Please enter in the year for the movie and make sure it is an integer.");
					return;
				}

				
				addMovies.setString(1, first_name);
				addMovies.setString(2, last_name);
				if (photo_url.equals("")) {
					addMovies.setString(3, null);
				} else {
					addMovies.setString(3, photo_url);
				}
				addMovies.setString(4, title);
				addMovies.setInt(5, intYear);
				addMovies.setString(6, director);
				if (banner_url.equals("")) {
					addMovies.setString(7, null);
				} else {
					addMovies.setString(7, banner_url);
				}
				if (trailer_url.equals("")) {
					addMovies.setString(8, null);
				} else {
					addMovies.setString(8, trailer_url);
				}
				addMovies.setString(9, genre);
				
				addMovies.setInt(10, 0);
				
				
				addMovies.executeUpdate();
				
				
				int numUpdated = addMovies.getInt(10);
				

				System.out.println(numUpdated);
				
				if (numUpdated > 0) {
					out.println("Successfully Updated " + numUpdated + " tables");
				}
				else {
					out.println(numUpdated + " updates. Data already exists.");
				}
			} else {

				
				CallableStatement addMovies = dbcon.prepareCall("{call add_movie(?,?,?,?,?,?,?,?,?,?,?)}");
				
				addMovies.registerOutParameter(11, Types.INTEGER);
				
				
				
				String first_name = request.getParameter("first_name");
				String last_name = request.getParameter("last_name");

				String photo_url = request.getParameter("photo_url");
				String title = request.getParameter("title");
				String year = request.getParameter("year");
				String director = request.getParameter("director");
				String banner_url = request.getParameter("banner_url");
				String trailer_url = request.getParameter("trailer_url");
				String genre = request.getParameter("genre");

				int intYear = 0;

				try {
					intYear = Integer.parseInt(year);
				} catch (Exception e) {
					out.println(
							"ERROR: Invalid Year. Please enter in the year for the movie and make sure it is an integer.");
					return;
				}

				
				
				addMovies.setString(1, first_name);
				addMovies.setString(2, last_name);
				addMovies.setString(3, dob);
				if (photo_url.equals("")) {
					addMovies.setString(4, null);
				} else {
					addMovies.setString(4, photo_url);
				}
				addMovies.setString(5, title);
				addMovies.setInt(6, intYear);
				addMovies.setString(7, director);
				if (banner_url.equals("")) {
					addMovies.setString(8, null);
				} else {
					addMovies.setString(8, banner_url);
				}
				if (trailer_url.equals("")) {
					addMovies.setString(9, null);
				} else {
					addMovies.setString(9, trailer_url);
				}
				addMovies.setString(10, genre);

				
				addMovies.setInt(11, 0);
				
				addMovies.executeUpdate();
				
				int numUpdated = addMovies.getInt(11);

				System.out.println(numUpdated);
				
				if (numUpdated > 0) {
					out.println("Successfully Updated " + numUpdated + " tables");
				}
				else {
					out.println(numUpdated + " updates. Data already exists.");
				}
			}
			
			dbcon.close();
			
		} catch (Exception e) {
			out.println("ERROR: Please make sure that the DOB is in yyyy/MM/dd format");
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
