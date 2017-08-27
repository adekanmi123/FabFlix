import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

//import session.SessionCart;
import objects.*;
import java.util.Random;
@WebServlet("/LoginEmployee")
public class LoginEmployee extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpSession session;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("LoginEmployee.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out2 = response.getWriter();
		
		response.setContentType("text/html");

		String email = request.getParameter("employeeemail");
		String password = request.getParameter("employeepassword");
		System.out.println(email + " " + password);

		try {
			PrintWriter out = response.getWriter();
            /*
			 String gRecaptchaResponse =
			 request.getParameter("g-recaptcha-response");
			 System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
			 // Verify CAPTCHA.
			 boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			 if (!valid) {
			 // errorString = "Captcha invalid!";
			 out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" +
			 "</TITLE></HEAD>\n<BODY>"
			 + "<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
			 return;
			 }
			 */
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
				pool_string = "jdbc/EmpDB";
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

	            Connection dbcon = ds.getConnection();
	            if (dbcon == null)
	                out2.println("dbcon is null.");

			String sql = "SELECT * FROM employees WHERE email = ? AND password = ?";

			PreparedStatement find_id = dbcon.prepareStatement(sql);
			find_id.setString(1, email);
			find_id.setString(2, password);

			ResultSet result = find_id.executeQuery();

			if (result.next()) {
				session = request.getSession(true);
				session.setAttribute("employeeemail", result.getString("email"));

				response.sendRedirect("_dashboard");
			} else {
				response.sendRedirect("InvalidLogin.jsp");
			}

			find_id.close();
			result.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
