import java.io.*;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Random;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * Servlet implementation class ProvideMeta
 */
@WebServlet("/Metadata")
public class Metadata extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection dbcon;

	/**
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 * @see HttpServlet#HttpServlet()
	 */
	public Metadata() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		super();
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
			response.setContentType("text/html");
			HttpSession session = request.getSession(false);

			if (request.getParameter("showMetadata") != null) {

				// the following commented lines are direct connections without pooling
				//String user = "mytestuser";
				//String pass = "mypassword";
				//String url = "jdbc:mysql://localhost:3306/moviedb";

				//Class.forName("com.mysql.jdbc.Driver").newInstance();

				//Connection dbcon = DriverManager.getConnection(url, user, pass);
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

				DatabaseMetaData metadata = dbcon.getMetaData();
				ResultSet tables = metadata.getTables(null, null, "%", null);

				PrintWriter out = response.getWriter();
				
				while (tables.next()) {
					String tableName = tables.getString(3);
					out.println("Table: " + tableName + "<br>");

					String sql = "select * from " + tableName;
					Statement statement = dbcon.createStatement();

					ResultSet result = statement.executeQuery(sql);
					ResultSetMetaData rsmd = result.getMetaData();

					out.println("There are " + rsmd.getColumnCount() + " columns." + "<br>");
					ResultSet cols = metadata.getColumns(null, null, tableName, null);

					while (cols.next()) {
						out.print("Column ");
						out.print(cols.getString("COLUMN_NAME"));
						out.print(" has type ");
						out.print(cols.getString("TYPE_NAME"));
						out.println("." + "<br>");
					}
					out.println("<br>");
				}
			}
		
			dbcon.close();
		} catch (Exception e) {
			System.out.println("ERROR: Could not print metadata.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
