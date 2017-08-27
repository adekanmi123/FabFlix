import java.io.IOException;

import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Cart;

import java.text.*;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * Servlet implementation class CheckBehav
 */
@WebServlet("/CheckBehav")
public class CheckBehav extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckBehav() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		try {
			// the following commented lines are direct connections without pooling
			boolean flag0 = false;
			//String loginUser = "mytestuser";
			//String loginpw = "mypassword";
			//String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			HttpSession session = request.getSession(false);
			if (session == null) {
				response.sendRedirect("Login.jsp"); // replace it with the login
													// page
			}
			String customer_id = session.getAttribute("id").toString();
			if (customer_id == null) {
				response.sendRedirect("Login.jsp"); // replace it with the login
													// page
			}
			
			// the following few lines are for connection pooling
            // Obtain our environment naming context

            Context initCtx = new InitialContext();
            if (initCtx == null)
                out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Master");//use master DB because of a write
            
            if (ds == null)
                out.println("ds is null.");

            Connection dbconn = ds.getConnection();
            if (dbconn == null)
                out.println("dbcon is null.");
			
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Connection dbconn = DriverManager.getConnection(loginUrl, loginUser, loginpw);
			PreparedStatement stmt;
			String input = request.getParameter("request");
			
			
			
			
			
			if (input != null) {
				if (input.equals("confirm")) {
					
					String crenum = request.getParameter("crenum");
					crenum = crenum.trim();
					if (crenum == "") {
						request.setAttribute("checkpass", "0");
						request.setAttribute("checmemo", "Empty Credit Number");
						getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response);
					}
					String fname = request.getParameter("firstn");
					fname = fname.trim();
					String lname = request.getParameter("lastn");
					lname = lname.trim();
					String time = request.getParameter("exdate");
					System.out.println(crenum);
					System.out.println(fname);
					System.out.println(lname);
					System.out.println(time);

					String sql = "SELECT * FROM creditcards WHERE id = '" + crenum.trim() + "'"
							+ " AND first_name = \"" + fname + "\" AND last_name = \"" + lname + "\""
							+ " AND expiration = \"" + time + "\"";
					stmt = dbconn.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();

					if (rs.next()) {

						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						dateFormat.setLenient(false);
						Calendar cal = Calendar.getInstance();
						System.out.println(dateFormat.format(cal.getTime()));
						java.util.Date date = dateFormat.parse(dateFormat.format(cal.getTime()));
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());

						sql = "SELECT * FROM customer_orders WHERE customer_id = '"
								+ session.getAttribute("id") + "'";
						PreparedStatement selectMovies = dbconn.prepareStatement(sql);

						ResultSet result = selectMovies
								.executeQuery();

						while (result.next()) {

							String insertString = "INSERT INTO sales (customer_id, movie_id, sale_date) VALUES (?, ?, ?)";
							java.sql.PreparedStatement insertSale = dbconn.prepareStatement(insertString);
							insertSale.setInt(1, (int) session.getAttribute("id"));
							System.out.println((int) session.getAttribute("id"));
							insertSale.setInt(2, result.getInt(1));
							System.out.println(result.getInt(1));

							insertSale.setDate(3, sqlDate);
							System.out.println(sqlDate);

							String selectString = "SELECT * FROM customer_orders " + " WHERE movie_id = "
									+ result.getInt(1) + " AND customer_id = " + (int) session.getAttribute("id");
							java.sql.PreparedStatement selectOrder = dbconn.prepareStatement(selectString);
							ResultSet orderResults = selectOrder.executeQuery();

							if (orderResults.next()) {
								for (int i = 0; i < orderResults.getInt(3); i++) {
									insertSale.executeUpdate();
								}
							}

						}
						flag0 = true;
					}

					if (input.equals("redirect")) {
						response.sendRedirect("Main"); // go to the main page,
														// please reset this
														// part
					}

				}
				if (flag0) {
					request.setAttribute("checkpass", "1");
					session.removeAttribute("cart");
					String sql = "DELETE FROM customer_orders WHERE customer_id = " + customer_id;
					stmt = dbconn.prepareStatement(sql);
					stmt.executeUpdate();
				} else {
					request.setAttribute("checkpass", "0");
				}
				request.getRequestDispatcher("checkout.jsp").forward(request, response);
			}
			
			//stmt.close();
			dbconn.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
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
