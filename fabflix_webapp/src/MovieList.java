import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import objects.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	java.sql.Connection dbcon;
	
	// Time an event in a program to nanosecond precision
	long startTime;
	long endTime;
	long elapsedTime;
	long TJstart;
	long TJend;
	long TJelapsed;
	long TJtotal = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		FileOutputStream write1 = new FileOutputStream("/home/ubuntu/TSlog.txt", true);
		
		PrintStream print1 = new PrintStream(write1);
		
		FileOutputStream write2 = new FileOutputStream("/home/ubuntu/TJlog.txt", true);
		
		PrintStream print2 = new PrintStream(write2);

		try {
			
			startTime = System.nanoTime();
			
			// the following commented lines are direct connections without
			// pooling
			// String loginUser = "mytestuser";
			// String loginPasswd = "mypassword";
			// String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

			// Class.forName("com.mysql.jdbc.Driver").newInstance();

			// dbcon = DriverManager.getConnection(loginUrl, loginUser,
			// loginPasswd);
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

			String title = "";
			String year = "";
			String director = "";
			String first_name = "";
			String last_name = "";
			String genre = "";
			int page;
			int limit;
			String order = "ascendingByTitle";

			int starDoesNotAlreadyExist = 1;
			int genreDoesNotAlreadyExist = 1;
			int genreExists = 1;
			int starExists = 1;
			ArrayList<Movie> movies;
			HashMap<Integer, Movie> movieHashMap = new HashMap<Integer, Movie>();
			String searchMoviesStatement = "";
			PreparedStatement searchString;
			ResultSet rs;
			String orderBy = "";
			Boolean substringMatching = false;
			Boolean bi_fuzzysearch = false;
			Boolean search = false;
			int pageOffset;

			if (request.getParameter("title") != null) {
				title = request.getParameter("title");
			}

			if (request.getParameter("year") != null) {
				year = request.getParameter("year");
			}

			if (request.getParameter("director") != null) {
				director = request.getParameter("director");
			}

			if (request.getParameter("first_name") != null) {
				first_name = request.getParameter("first_name");
			}

			if (request.getParameter("last_name") != null) {
				last_name = request.getParameter("last_name");
			}
			if (request.getParameter("genre") != null) {
				genre = request.getParameter("genre");
			}
			if (request.getParameter("page") != null) {
				page = Integer.parseInt((String) request.getParameter("page"));
			} else {
				page = 0;
			}

			request.setAttribute("page", page);
			if (request.getParameter("limit") != null) {
				limit = Integer.parseInt((String) request.getParameter("limit"));
			} else {
				limit = 5;
			}

			request.setAttribute("limit", limit);
			if (request.getParameter("order") != null) {
				order = request.getParameter("order");
			}

			switch (order) {
			case "descendingByTitle":
				orderBy = " ORDER BY m.title DESC";
				break;
			case "ascendingByYear":
				orderBy = " ORDER BY m.year ASC";
				break;
			case "descendingByYear":
				orderBy = " ORDER BY m.year DESC";
				break;
			default:
				orderBy = " ORDER BY m.title ASC";
			}

			if (request.getParameter("search") != null) {
				search = true;
				if ((request.getParameter("substringMatching") == null) == false) {
					substringMatching = true;
				} else {
					substringMatching = false;
				}
				if (request.getParameter("fuzzysearch") != null) {
					bi_fuzzysearch = true;
				} else {
					bi_fuzzysearch = false;
				}
			}

			String query = "";
			if (request.getParameter("autoTitle") != null) {
				query = request.getParameter("autoTitle");
			}

			String fixed = query.replaceAll("-", "");
			fixed = query.replaceAll("\'", "");
			System.out.println(fixed);

			String[] splited = fixed.split("\\s+");

			String bool = "";
			for (int i = 0; i < splited.length; i++) {
				bool += "+" + splited[i] + "* ";
			}

			bool = bool.trim();

			Boolean autoSearch = false;

			if (request.getParameter("auto") != null) {
				autoSearch = true;
				System.out.println("autoSearch true!");
			}

			String searchMoviesTitles;

			String fuzzysearch = "";

			if (search == true) {

				if (substringMatching == false && !bi_fuzzysearch) {
					String where = "";

					if (title != null && title.isEmpty() == false) {
						where = where + " AND m.title = \"" + title + "\"";
					}
					if (director != null && director.isEmpty() == false) {
						where = where + " AND m.director = \"" + director + "\"";
					}

					if (genre != null && genre.isEmpty() == false) {
						where = where + " AND g.name = \"" + genre + "\"";
					}

					if (first_name != null && first_name.isEmpty() == false) {
						where = where + " AND s.first_name = \"" + first_name + "\"";
					}
					if (last_name != null && last_name.isEmpty() == false) {
						where = where + " AND s.last_name = \"" + last_name + "\"";
					}

					if (year != null && year.isEmpty() == false) {
						where = where + " AND m.year = \"" + year + "\"";
					}

					TJstart = System.nanoTime();
					
					searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
							+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id"
							+ where + orderBy;

				} else if (substringMatching && bi_fuzzysearch) {

					if (title != null && !title.trim().isEmpty()) {

						fuzzysearch = fuzzysearch + " AND (m.title LIKE \"%" + title.trim()
								+ "%\" OR edth(LCASE(m.title),LCASE('" + title.trim() + "'),3)) ";
					}
					if (director != null && !director.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND (m.director LIKE \"%" + director.trim()
								+ "%\" OR edth(LCASE(m.director),LCASE('" + director.trim() + "'),3)) ";
					}

					if (first_name != null && !first_name.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND (s.first_name LIKE \"%" + first_name.trim()
								+ "%\" OR edth(LCASE(s.first_name),LCASE('" + first_name.trim() + "'),3)) ";
					}
					if (last_name != null && !last_name.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND (s.last_name LIKE \"%" + last_name.trim()
								+ "%\" OR edth(LCASE(s.last_name),LCASE('" + last_name.trim() + "'),3)) ";
					}

					TJstart = System.nanoTime();
					
					searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
							+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id AND g.name LIKE \"%"
							+ genre.trim() + "%\" AND m.year LIKE \"%" + year.trim() + "%\"" + fuzzysearch

							+ orderBy;
				} else if (!substringMatching && bi_fuzzysearch) {

					if (title != null && !title.trim().isEmpty()) {

						fuzzysearch = fuzzysearch + " AND  edth(LCASE(m.title),LCASE('" + title.trim() + "'),3) ";
					}
					if (director != null && !director.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND  edth(LCASE(m.director),LCASE('" + director.trim() + "'),3) ";
					}

					if (first_name != null && !first_name.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND  edth(LCASE(s.first_name),LCASE('" + first_name.trim()
								+ "'),3) ";
					}
					if (last_name != null && !last_name.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND  edth(LCASE(s.last_name),LCASE('" + last_name.trim()
								+ "'),3) ";
					}
					if (year != null && !year.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND m.year = \"" + year.trim() + "\"";
					}

					if (genre != null && !genre.trim().isEmpty()) {
						fuzzysearch = fuzzysearch + " AND g.name = \"" + genre.trim() + "\"";
					}
					
					TJstart = System.nanoTime();
					
					searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
							+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id "
							+ fuzzysearch

							+ orderBy;

				} else if (substringMatching && !bi_fuzzysearch) {
					
					TJstart = System.nanoTime();
					
					searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
							+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id"
							+ " AND m.title LIKE \"%" + title + "%\" AND m.director LIKE \"%" + director
							+ "%\" AND g.name LIKE \"%" + genre + "%\" AND s.first_name LIKE \"%" + first_name
							+ "%\" AND s.last_name LIKE \"%" + last_name + "%\" AND m.year LIKE \"%" + year + "%\""
							+ orderBy;

				}

			} else if (title != null && title.isEmpty() == false) {
				
				TJstart = System.nanoTime();

				searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
						+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
						+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
						+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id"
						+ " AND m.title LIKE \"" + title + "%\"" + orderBy;
			}

			else if (genre != null && genre.isEmpty() == false) {
				
				TJstart = System.nanoTime();

				searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
						+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
						+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
						+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id"
						+ " AND g.name = \"" + genre + "\"" + orderBy;
			} else if (autoSearch == true) {
				
				TJstart = System.nanoTime();
				
				searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
						+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
						+ " FROM ft, movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
						+ " WHERE (MATCH (ft.title) AGAINST ('" + bool + "' IN BOOLEAN MODE) AND"
						+ " ft.titleID = m.id AND m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id) ";

			}

			System.out.println(searchMoviesStatement);

			searchString = dbcon.prepareStatement(searchMoviesStatement);
			rs = searchString.executeQuery();
			
			TJend = System.nanoTime();
			TJelapsed = TJend - TJstart; // elapsed time in nano seconds. Note: print the values in nano seconds
			TJtotal += TJelapsed;

			int num = 0;

			if (autoSearch == true && !rs.next()) {
				
				TJstart = System.nanoTime();
				
				searchMoviesStatement = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url,"
						+ " s.id, s.first_name, s.last_name, s.dob, s.photo_url, g.id, g.name"
						+ " FROM ft, movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
						+ " WHERE ft.title=\"" + query + "\" AND"
						+ " ft.titleID = m.id AND m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id";
//				searchString = dbcon.prepareStatement(searchMoviesStatement);
//				rs = searchString.executeQuery();
				
				TJend = System.nanoTime();
				TJelapsed = TJend - TJstart;
				TJtotal += TJelapsed;
				
			} 
//			else if (autoSearch == true) {
//				searchString = dbcon.prepareStatement(searchMoviesStatement);
//				rs = searchString.executeQuery();
//			}
				
			searchString = dbcon.prepareStatement(searchMoviesStatement);
			rs = searchString.executeQuery();
				
			TJend = System.nanoTime();
			TJelapsed = TJend - TJstart;
			TJtotal += TJelapsed;

			while (rs.next()) {
				int sid = rs.getInt("s.id");
				String sfirstname = rs.getString("s.first_name");
				String slastname = rs.getString("s.last_name");
				Date sdob = rs.getDate("s.dob");
				String sphotourl = rs.getString("s.photo_url");

				Star movieStar = new Star(sid, sfirstname, slastname, sdob, sphotourl);

				int gid = rs.getInt("g.id");
				String gname = rs.getString("g.name");

				Genre movieGenre = new Genre(gid, gname);

				int mid = rs.getInt("m.id");

				if (movieHashMap.containsKey(mid)) {
					Movie movie = movieHashMap.get(mid);

					for (int i = 0; i < movie.getStars().size(); i++) {
						if (movie.getStars().get(i).getId() == movieStar.getId()) {
							starDoesNotAlreadyExist = 0;
						}
					}

					for (int i = 0; i < movie.getGenres().size(); i++) {
						if (movie.getGenres().get(i).getId() == movieGenre.getId()) {
							genreDoesNotAlreadyExist = 0;

						}
					}

					if (starDoesNotAlreadyExist == 1) {
						movie.addStar(movieStar);
						System.out.println("star added");
					}

					if (genreDoesNotAlreadyExist == 1) {
						movie.addGenre(movieGenre);
					}

					TJstart = System.nanoTime();
					
					String searchGenres = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, s.id, s.first_name, s.last_name, s.dob, s.photo_url,g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id AND m.id = ?";

					PreparedStatement searchMoreGenres = dbcon.prepareStatement(searchGenres);
					searchMoreGenres.setInt(1, mid);
					ResultSet genresResult = searchMoreGenres.executeQuery();

					TJend = System.nanoTime();
					TJelapsed = TJend - TJstart;
					TJtotal += TJelapsed;
					
					System.out.println(searchGenres);

					while (genresResult.next()) {

						int genreid = genresResult.getInt("g.id");
						String genrename = genresResult.getString("g.name");
						System.out.println(genreid + genrename);

						Genre moreGenre = new Genre(genreid, genrename);

						for (int i = 0; i < movie.getGenres().size(); i++) {
							if (movie.getGenres().get(i).getId() == moreGenre.getId()) {
								genreExists = 0;
							}
						}

						if (genreExists == 1) {
							movie.addGenre(moreGenre);
						}

						genreExists = 1;
					}

					TJstart = System.nanoTime();
					
					String searchStars = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, s.id, s.first_name, s.last_name, s.dob, s.photo_url,g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id AND m.id = ?";

					PreparedStatement searchMoreStars = dbcon.prepareStatement(searchStars);
					searchMoreStars.setInt(1, mid);
					ResultSet starsResult = searchMoreStars.executeQuery();

					TJend = System.nanoTime();
					TJelapsed = TJend - TJstart;
					TJtotal += TJelapsed;
					
					while (starsResult.next()) {

						int starid = starsResult.getInt("s.id");
						String starfirstname = starsResult.getString("s.first_name");
						String starlastname = starsResult.getString("s.last_name");
						Date stardob = starsResult.getDate("s.dob");
						String starphotourl = starsResult.getString("s.photo_url");

						Star moreStar = new Star(starid, starfirstname, starlastname, stardob, starphotourl);

						for (int i = 0; i < movie.getStars().size(); i++) {
							if (movie.getStars().get(i).getId() == moreStar.getId()) {
								starExists = 0;
							}
						}

						if (starExists == 1) {
							movie.addStar(moreStar);
						}

						starExists = 1;
					}

					movieHashMap.put(movie.getId(), movie);
					starDoesNotAlreadyExist = 1;
					genreDoesNotAlreadyExist = 1;
				} else {
					ArrayList<Genre> genres = new ArrayList<Genre>();
					ArrayList<Star> stars = new ArrayList<Star>();

					genres.add(movieGenre);
					stars.add(movieStar);

					String mtitle = rs.getString("m.title");
					int myear = rs.getInt("m.year");
					String mdirector = rs.getString("m.director");
					String mbannerurl = rs.getString("m.banner_url");
					String mtrailerurl = rs.getString("m.trailer_url");

					Movie movie = new Movie(mid, mtitle, myear, mdirector, mbannerurl, mtrailerurl, genres, stars);

					TJstart = System.nanoTime();
					
					String searchGenres = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, s.id, s.first_name, s.last_name, s.dob, s.photo_url,g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id AND m.id = ?";

					PreparedStatement searchMoreGenres = dbcon.prepareStatement(searchGenres);
					searchMoreGenres.setInt(1, mid);
					ResultSet genresResult = searchMoreGenres.executeQuery();

					TJend = System.nanoTime();
					TJelapsed = TJend - TJstart;
					TJtotal += TJelapsed;
					
					System.out.println(searchGenres);

					while (genresResult.next()) {

						int genreid = genresResult.getInt("g.id");
						String genrename = genresResult.getString("g.name");
						System.out.println(genreid + genrename);

						Genre moreGenre = new Genre(genreid, genrename);

						for (int i = 0; i < movie.getGenres().size(); i++) {
							if (movie.getGenres().get(i).getId() == moreGenre.getId()) {
								genreExists = 0;
							}
						}

						if (genreExists == 1) {
							movie.addGenre(moreGenre);
						}

						genreExists = 1;
					}
					
					TJstart = System.nanoTime();

					String searchStars = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url, s.id, s.first_name, s.last_name, s.dob, s.photo_url,g.id, g.name"
							+ " FROM movies m, stars s, genres g, stars_in_movies sim, genres_in_movies gim"
							+ " WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.id = gim.movie_id AND g.id = gim.genre_id AND m.id = ?";

					PreparedStatement searchMoreStars = dbcon.prepareStatement(searchStars);
					searchMoreStars.setInt(1, mid);
					ResultSet starsResult = searchMoreStars.executeQuery();

					TJend = System.nanoTime();
					TJelapsed = TJend - TJstart;
					TJtotal += TJelapsed;
					
					while (starsResult.next()) {

						int starid = starsResult.getInt("s.id");
						String starfirstname = starsResult.getString("s.first_name");
						String starlastname = starsResult.getString("s.last_name");
						Date stardob = starsResult.getDate("s.dob");
						String starphotourl = starsResult.getString("s.photo_url");

						Star moreStar = new Star(starid, starfirstname, starlastname, stardob, starphotourl);

						for (int i = 0; i < movie.getStars().size(); i++) {
							if (movie.getStars().get(i).getId() == moreStar.getId()) {
								starExists = 0;
							}
						}

						if (starExists == 1) {
							movie.addStar(moreStar);
						}

						starExists = 1;
					}

					movieHashMap.put(mid, movie);
				}

				num++;
			}
			
			endTime = System.nanoTime();
			elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
			print1.println(elapsedTime);
			print2.println(TJtotal);
			TJtotal = 0;
			write1.close();
			write2.close();

			movies = new ArrayList<Movie>(movieHashMap.values());

			switch (order) {
			case "descendingByTitle":
				Collections.sort(movies, Movie.orderByTitleDescending());
				break;
			case "ascendingByYear":
				Collections.sort(movies, Movie.orderByYearAscending());
				break;
			case "descendingByYear":
				Collections.sort(movies, Movie.orderByYearDescending());
				break;
			default:
				Collections.sort(movies, Movie.orderByTitleAscending());
			}

			request.setAttribute("order", order);

			ArrayList<Movie> showMovies = new ArrayList<Movie>();
			pageOffset = page * limit;

			for (int i = pageOffset; (i < (pageOffset + limit) && i < movies.size()); i++) {
				if (movies.size() != 0) {
					showMovies.add(movies.get(i));
				}
			}

			request.setAttribute("movies", showMovies);

			request.setAttribute("totalPages", (int) Math.ceil(movies.size() / limit));

			if (search) {
				if (substringMatching == false && !bi_fuzzysearch) {
					request.setAttribute("parameters", "title=" + title + "&year=" + year + "&director=" + director
							+ "&first_name=" + first_name + "&last_name=" + last_name + "&search=" + search);
				} else if (substringMatching && bi_fuzzysearch) {
					request.setAttribute("parameters",
							"title=" + title + "&year=" + year + "&director=" + director + "&first_name=" + first_name
									+ "&last_name=" + last_name + "&substringMatching=" + substringMatching
									+ "&fuzzysearch=" + bi_fuzzysearch + "&search=" + search);
				} else if (!substringMatching && bi_fuzzysearch) {
					request.setAttribute("parameters",
							"title=" + title + "&year=" + year + "&director=" + director + "&first_name=" + first_name
									+ "&last_name=" + last_name + "&fuzzysearch=" + bi_fuzzysearch + "&search="
									+ search);
				} else if (substringMatching && !bi_fuzzysearch) {
					request.setAttribute("parameters", "title=" + title + "&year=" + year + "&director=" + director
							+ "&first_name=" + first_name + "&last_name=" + last_name + "&search=" + search);
				} else {
					request.setAttribute("parameters",
							"title=" + title + "&year=" + year + "&director=" + director + "&first_name=" + first_name
									+ "&last_name=" + last_name + "&substringMatching=" + substringMatching + "&search="
									+ search);
				}
			} else if (title != null && title.isEmpty() == false) {
				request.setAttribute("parameters", "title=" + title);
			} else if (genre != null && genre.isEmpty() == false) {
				request.setAttribute("parameters", "genre=" + genre);
			} else if (autoSearch == true) {
				request.setAttribute("parameters", "autoTitle=" + query + "&auto=true&autoSearch=autoSearch");
			}

			request.getRequestDispatcher("MovieList.jsp").forward(request, response);

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