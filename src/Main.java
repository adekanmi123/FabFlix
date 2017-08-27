import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/Main")
public class Main extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	Connection connection;
    public Main() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
    {
    	super();
    	// the following commented lines are direct connections without pooling
    	//String user = "mytestuser";
    	//String pass = "mypassword";
    	//String url = "jdbc:mysql://localhost:3306/moviedb";
    	

    	//Class.forName("com.mysql.jdbc.Driver").newInstance();

    	//connection = DriverManager.getConnection(url, user, pass);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		try 
		{
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

            connection = ds.getConnection();
            if (connection == null)
                out.println("dbcon is null.");
			
			ArrayList<String> genres = new ArrayList<String>();
			
			String sql = "SELECT name FROM genres ORDER BY name ASC";
			
			ResultSet result = connection.createStatement().executeQuery(sql);
			
			while(result.next())
			{
				genres.add(result.getString(1));
			}
			
			request.setAttribute("genres", genres);
			request.getRequestDispatcher("Main.jsp").forward(request, response);
			
			connection.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if (request.getParameter("browse_genre") != null) 
		{
			response.sendRedirect("MovieList?" + "genre=" + request.getParameter("browse_genre"));
		}
		else if (request.getParameter("browse_title") != null) 
		{
			response.sendRedirect("MovieList?" + "title=" + request.getParameter("browse_title"));
		}
	}
}
