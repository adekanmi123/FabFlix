import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import objects.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

@WebServlet("/StarDetails")
public class StarDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	java.sql.Connection dbcon;

	public StarDetails() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		try {
			// the following commented lines are direct connections without pooling
			//String loginUser = "mytestuser";
			//String loginPasswd = "mypassword";
			//String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

			//Class.forName("com.mysql.jdbc.Driver").newInstance();

			//dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Random rand = new Random();
			int n = rand.nextInt(1);
			String pool_string;
			if(n == 0)
			{
				pool_string = "jdbc/Master";
				
			}
			else
			{
				pool_string = "jdbc/TestDB";
			}
			Context initCtx = new InitialContext();
	        if (initCtx == null)
	            out.println("initCtx is NULL");

	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
	        if (envCtx == null)
	            out.println("envCtx is NULL");

	        // Look up our data source
	        DataSource ds = (DataSource) envCtx.lookup(pool_string);
	        
	        if (ds == null)
	            out.println("ds is null.");

	        dbcon = ds.getConnection();
	        if (dbcon == null)
	            out.println("dbcon is null.");
			
			String sqlStatement = "SELECT s.id, s.first_name, s.last_name, s.dob, s.photo_url, "
					+ " m.id, m.title, m.year"
					+ " FROM movies m, stars s, stars_in_movies sim"
					+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND s.id = ?";
			PreparedStatement preparedStatement = dbcon.prepareStatement(sqlStatement);
			preparedStatement.setString(1, request.getParameter("starid"));
			ResultSet moviesStarred = preparedStatement.executeQuery();
			
			ArrayList<Movie> movieList = null;
			Star star = null;
			
			if (moviesStarred.next()) {
				int sid = moviesStarred.getInt("s.id");
				String sfirstname = moviesStarred.getString("s.first_name");
				String slastname = moviesStarred.getString("s.last_name");
				Date sdob = moviesStarred.getDate("s.dob");
				String sphotourl = moviesStarred.getString("s.photo_url");
				
				int mid = moviesStarred.getInt("m.id");
				String mtitle = moviesStarred.getString("m.title");
				int myear = moviesStarred.getInt("m.year");
				
				star = new Star(sid, sfirstname, slastname, sdob, sphotourl);
				star.addMovie(new Movie(mid, mtitle, myear, "", "", ""));
				
				
				while (moviesStarred.next()) {
					
					mid = moviesStarred.getInt("m.id");
					mtitle = moviesStarred.getString("m.title");
					myear = moviesStarred.getInt("m.year");
					
					star.addMovie(new Movie(mid, mtitle, myear, "", "", ""));
				}
				
			}
			
			request.setAttribute("star", star);
			movieList = star.getMovies();
			
			request.setAttribute("movies", movieList);
			
			request.getRequestDispatcher("StarDetails.jsp").forward(request, response);

			preparedStatement.close();
			moviesStarred.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
