import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import objects.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

@WebServlet("/MoviePopup")
public class MoviePopup extends HttpServlet{

	private static final long serialVersionUID = 1L;

	java.sql.Connection dbcon;

	public MoviePopup() {
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
			
			System.out.println("Inside popup");
			String id = request.getParameter("id");
			System.out.println(id);
			
			
			String sql = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
					+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url," + " g.id, g.name"
					+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
					+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id"
					+ " AND m.id=?";
			System.out.println(sql);
			PreparedStatement findDeets = dbcon.prepareStatement(sql);
			findDeets.setString(1, id);
			ResultSet rs = findDeets.executeQuery();
			
			String preview = "";
			String title = "";
			String img = "";
			String dir = "";
			String year = "";
			String stars = "";
			String genres = "";
			
			sql = "SELECT DISTINCT s.first_name, s.last_name FROM stars s, stars_in_movies sim WHERE sim.movie_id= ? AND sim.star_id = s.id";
			PreparedStatement findStars = dbcon.prepareStatement(sql);
			findStars.setString(1, id);
			ResultSet results = findStars.executeQuery();
			
			while (results.next()) {
				stars += results.getString("s.first_name") + " " + results.getString("s.last_name") + ", ";
			}
			
			sql = "SELECT DISTINCT g.name FROM genres g, genres_in_movies gim WHERE gim.movie_id= ? AND gim.genre_id = g.id";
			PreparedStatement findGenres = dbcon.prepareStatement(sql);
			findGenres.setString(1, id);
			ResultSet res = findGenres.executeQuery();
			
			while (res.next()) {
				genres += res.getString("g.name") + ", ";
			}
			
			
			
			while (rs.next()){
				preview = rs.getString("m.trailer_url");
				title = rs.getString("m.title");
				img = rs.getString("m.banner_url");
				dir = rs.getString("m.director");
				year = rs.getString("m.year");
			}
//			
//			String html = "<form name=\"AddMovieToCart\" action=\"CartBehav\" method=\"POST\">" +
//			"<input type=\"hidden\" name=\"movie\" value=\"" + id + "\"></input>" +
//			"<input type=\"hidden\" name=\"type\" value=\"detail\"></input>" +
//			"<button align=\"right\" type=\"submit\" name=\"request\" value=\"additem\">Add to Cart</button></form>" +
//			"<br /><a href=\"" + preview + "\"><button type=\"button\">Preview</button></a>";
//			//String html = id;
////			System.out.println(html);
////		
//			response.setContentType("text/html");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out = response.getWriter();
//			out.print(html);
			
			request.setAttribute("id", id);
			request.setAttribute("trailer_url", preview);
			request.setAttribute("banner_url", img);
			request.setAttribute("title", title);
			request.setAttribute("director", dir);
			request.setAttribute("year", year);
			request.setAttribute("stars", stars);
			request.setAttribute("genres", genres);
			request.getRequestDispatcher("MoviePopup.jsp").forward(request, response);
			
			findDeets.close();
			findStars.close();
			findGenres.close();
			results.close();
			rs.close();
			res.close();
			dbcon.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
