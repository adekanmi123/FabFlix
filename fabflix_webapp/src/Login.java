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

@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpSession session;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			PrintWriter out = response.getWriter();

//			 String gRecaptchaResponse =
//			 request.getParameter("g-recaptcha-response");
//			 System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
//			 // Verify CAPTCHA.
//			 boolean valid = VerifyUtils.verify(gRecaptchaResponse);
//			 if (!valid) {
//			 // errorString = "Captcha invalid!";
//			 out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" +
//			 "</TITLE></HEAD>\n<BODY>"
//			 + "<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
//			 return;
//			 }

			// the following few lines are for connection pooling
            // Obtain our environment naming context

            Context initCtx = new InitialContext();
            if (initCtx == null)
                out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Master"); //use master db because of update in full-title table
			
         // the following commented lines are direct connections without pooling
			//String user = "mytestuser";
			//String pass = "mypassword";
			//String url = "jdbc:mysql://localhost:3306/moviedb";

			//Class.forName("com.mysql.jdbc.Driver").newInstance();

			//Connection dbcon = DriverManager.getConnection(url, user, pass);
			
            if (ds == null)
                out.println("ds is null.");

            Connection dbcon = ds.getConnection();
            if (dbcon == null)
                out.println("dbcon is null.");

			
			
			// insert movies into mysql full-text table for auto-completion search
            String movieSQL = "INSERT INTO ft (titleID, title) SELECT  movies.id,movies.title  FROM movies WHERE NOT EXISTS (SELECT 1 FROM ft WHERE ft.titleID = movies.id)";
            PreparedStatement ftInsert = dbcon.prepareStatement(movieSQL);
        	ftInsert.executeUpdate();
            //end
        	dbcon.close();
        	ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            if (ds == null)
                out.println("ds is null.");
            dbcon = ds.getConnection();
            if (dbcon == null)
                out.println("dbcon is null.");


			String sql = "SELECT * FROM customers WHERE email = ? AND password = ?";

			PreparedStatement find_id = dbcon.prepareStatement(sql);
			find_id.setString(1, email);
			find_id.setString(2, password);

			System.out.println(find_id);

			ResultSet result = find_id.executeQuery();

			System.out.println(result);
			
			
			
			if (result.next()) {
				
				System.out.println("next");
				session = request.getSession(true);
				session.setAttribute("id", result.getInt("id"));

				Cart cart = new Cart();
				String moreSQL = "SELECT m.banner_url, m.id,m.title,co.quantity FROM customers c, customer_orders co, movies m WHERE c.id = co.customer_id AND co.movie_id = m.id AND customer_id ="
						+ session.getAttribute("id");
				PreparedStatement stmt = dbcon.prepareStatement(moreSQL);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					cart.addItemtoCart(new Movie(rs.getString(1), rs.getInt(2), rs.getString(3)), rs.getInt(4));
					while (rs.next()) {
						cart.addItemtoCart(new Movie(rs.getString(1), rs.getInt(2), rs.getString(3)), rs.getInt(4));
					}
					session.setAttribute("cart", cart);
				}
				rs.close();

				response.sendRedirect("Main");
			} else {
				response.sendRedirect("InvalidLogin.jsp");
			}


			ftInsert.close();
			find_id.close();
			result.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
