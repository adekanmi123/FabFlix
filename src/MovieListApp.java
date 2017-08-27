import java.io.IOException;

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

@WebServlet("/MovieListApp")
public class MovieListApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	java.sql.Connection dbcon;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out2 = response.getWriter();
		
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
	            out2.println("initCtx is NULL");

	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
	        if (envCtx == null)
	            out2.println("envCtx is NULL");

	        // Look up our data source
	        DataSource ds = (DataSource) envCtx.lookup(pool_string);
	        
	        if (ds == null)
	            out2.println("ds is null.");

	        dbcon = ds.getConnection();
	        if (dbcon == null)
	            out2.println("dbcon is null.");
			
			String title = "";

			String searchMoviesStatement = "";
			PreparedStatement searchString;
			ResultSet rs;
			
			
			if (request.getParameter("title") != null) {
				title = request.getParameter("title");
			}
			
			if (request.getParameter("title") == null) {
				request.getRequestDispatcher("MovieListApp.jsp").forward(request, response);
				return;
			}
            
			if (title != null && title.isEmpty() == false) {
                
                String[] splited = title.split("\\s+");
                
                String bool = "";
                for (int i = 0; i < splited.length; i++) {
                    bool += "+" + splited[i] + "* ";
                }
                
                bool = bool.trim();


                searchMoviesStatement = "SELECT title FROM ft WHERE MATCH (title) AGAINST ('" + bool + "' IN BOOLEAN MODE)" + " ORDER BY title ASC";
			}
			
			searchString = dbcon.prepareStatement(searchMoviesStatement);
			
			System.out.println(searchString);
			rs = searchString.executeQuery();
			
			
			String movies = "";
			
			
			while (rs.next()) {
				movies = movies + rs.getString("title") + "; ";
			}
			
			PrintWriter out = response.getWriter();
			out.println(movies);


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
