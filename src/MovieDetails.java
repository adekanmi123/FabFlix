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

@WebServlet("/MovieDetails")
public class MovieDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	java.sql.Connection dbcon;

	public MovieDetails() {
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

			int starDoesNotAlreadyExist = 1;
			int genreDoesNotAlreadyExist = 1;
			
			PreparedStatement searchString;
			ResultSet rs;
			
			String movieID = request.getParameter("movieid");

			String findMovieStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
					+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url," + " g.id, g.name"
					+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
					+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id"
					+ " AND m.id=?";

			System.out.println(findMovieStatement);

			searchString = dbcon.prepareStatement(findMovieStatement);
			searchString.setString(1, movieID);
			rs = searchString.executeQuery();
			
			HashMap<Integer, Movie> movieHashMap = new HashMap<Integer, Movie>();

			while (rs.next()) {
				int sid = rs.getInt("s.id");
				String sfirstname = rs.getString("s.first_name");
				String slastname = rs.getString("s.last_name");
				Date sdob = rs.getDate("s.dob");
				String sphotourl = rs.getString("s.photo_url");
				
				Star star = new Star(sid, sfirstname, slastname, sdob, sphotourl);
				
				
				int gid = rs.getInt("g.id");
				String gname = rs.getString("g.name");
				
				Genre genre = new Genre(gid, gname);

				int mid = rs.getInt("m.id");
				
				if (movieHashMap.containsKey(mid)) {
					Movie movie = movieHashMap.get(mid);
					
					for (int i = 0; i < movie.getStars().size(); i++) {
						if (movie.getStars().get(i).getId() == sid) {
							starDoesNotAlreadyExist = 0;
						}
					}

					for (int i = 0; i < movie.getGenres().size(); i++) {
						if (movie.getGenres().get(i).getId() == genre.getId()) {
							genreDoesNotAlreadyExist = 0;
						}
					}

					if (starDoesNotAlreadyExist == 1) {
						movie.addStar(star);
					}

					if (genreDoesNotAlreadyExist == 1) {
						movie.addGenre(genre);
					}
					
					movieHashMap.put(mid, movie);
					
					starDoesNotAlreadyExist = 1;
					genreDoesNotAlreadyExist = 1;
				} else {
					ArrayList<Genre> genres = new ArrayList<Genre>();
					ArrayList<Star> stars = new ArrayList<Star>();

					genres.add(genre);
					stars.add(star);

					mid = rs.getInt("m.id");
					String mtitle = rs.getString("m.title");
					int myear = rs.getInt("m.year");
					String mdirector = rs.getString("m.director");
					String mbannerurl = rs.getString("m.banner_url");
					String mtrailerurl = rs.getString("m.trailer_url");

					Movie movie = new Movie(mid, mtitle, myear, mdirector, mbannerurl, mtrailerurl, genres, stars);

					movieHashMap.put(mid, movie);
				}
			}

			ArrayList<Movie> movies = new ArrayList<Movie>(movieHashMap.values());

			Movie movie = movies.get(0);

			request.setAttribute("movie", movie);
			request.setAttribute("stars", movie.getStars());
			request.setAttribute("genres", movie.getGenres());
			request.getRequestDispatcher("MovieDetails.jsp").forward(request, response);

			searchString.close();
			rs.close();
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
