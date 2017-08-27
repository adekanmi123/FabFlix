import javax.servlet.annotation.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import objects.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/*
 * Servlet implementation class CartBehav
 */
@WebServlet("/CartBehav")
public class CartBehav extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartBehav() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * if
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		try {
			// the following commented lines are direct connections without pooling
			//String loginUser = "mytestuser";
			//String loginpw = "mypassword";
			//String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			HttpSession session = request.getSession(false);
			if (session == null) {
				response.sendRedirect("Login.jsp"); // change it to login jsp
			}
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Connection dbconn = DriverManager.getConnection(loginUrl, loginUser, loginpw);
			
			// the following few lines are for connection pooling
            // Obtain our environment naming context

            Context initCtx = new InitialContext();
            if (initCtx == null)
                out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Master");
            
            if (ds == null)
                out.println("ds is null.");

            Connection dbconn = ds.getConnection();
            if (dbconn == null)
                out.println("dbcon is null.");
			
			PreparedStatement stmt;
			Cart carta = (Cart) session.getAttribute("cart");
			String movie_id = request.getParameter("movie");
			System.out.println(movie_id);
			String customer_id = session.getAttribute("id").toString();
			if (customer_id == null) {
				response.sendRedirect("Login.jsp"); // change it to login jsp
			}

			if (carta == null) {
				carta = new Cart();
				String sql = "SELECT m.banner_url, m.id,m.title,co.quantity FROM customers c, customer_orders co, movies m WHERE c.id = co.customer_id AND co.movie_id = m.id AND customer_id ="
						+ customer_id;
				stmt = dbconn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
						
				if (rs.next()) {
					carta.addItemtoCart(new Movie(rs.getString(1), rs.getInt(2), rs.getString(3)), rs.getInt(4));
					while (rs.next()) {
						carta.addItemtoCart(new Movie(rs.getString(1), rs.getInt(2), rs.getString(3)), rs.getInt(4));
					}
					session.setAttribute("cart", carta);
				}
				rs.close();
			}

			String input = request.getParameter("request");
			String quantity = request.getParameter("quantity");
			Movie movie = null;
			Movie movieadd = null;
			if (input != null && !input.equals("")) {
				if (movie_id != null) {
					String sql = "SELECT m.banner_url, m.id,m.title,co.quantity FROM customers c, customer_orders co, movies m WHERE c.id = co.customer_id AND co.movie_id = m.id AND customer_id ="
							+ customer_id + " AND movie_id =" + movie_id;
					stmt = dbconn.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
							
							
					if (rs.next()) {
						movie = new Movie(rs.getString(1), rs.getInt(2), rs.getString(3));
					}
					rs = stmt.executeQuery(
							"SELECT movies.banner_url,movies.id,movies.title FROM movies WHERE id = " + movie_id);
					if (rs.next()) {
						movieadd = new Movie(rs.getString(1), rs.getInt(2), rs.getString(3));
					}
					rs.close();
				}
				if (input.equals("update")) {
					if (movie != null && quantity != null && !quantity.equals("0")) {
						carta.updateQua(movie, Integer.parseInt(quantity));
						String sql = "UPDATE customer_orders SET quantity = " + Integer.parseInt(quantity)
						+ " WHERE customer_id = " + customer_id + " AND movie_id = " + movie_id;
						stmt = dbconn.prepareStatement(sql);
						stmt.executeUpdate();
					}
					if (quantity.equals("0")) {
						input = "delete";
					}

				}
				if (input.equals("delete")) {
					if (movie != null) {
						carta.removeMov(movie);
						String sql = "DELETE FROM customer_orders WHERE customer_id = " + customer_id
								+ " AND movie_id = " + movie_id;
						stmt = dbconn.prepareStatement(sql);
						stmt.executeUpdate();
					}

				}
				if (input.equals("delete_all")) {
					if (carta != null) {
						carta.removeALL();
						String sql = "DELETE FROM customer_orders WHERE customer_id = " + customer_id;
						stmt = dbconn.prepareStatement(sql);
						stmt.executeUpdate();
					}
				}
				if (input.equals("additem")) {
					if (movieadd != null) {
						if (carta == null) {
							carta = new Cart();
						}
						carta.addItemtoCart(movieadd, 1);
						String sql = "SELECT m.id FROM customers c, customer_orders co, movies m WHERE c.id = co.customer_id AND co.movie_id = m.id AND customer_id ="
								+ customer_id + " AND movie_id =" + movie_id;
						stmt = dbconn.prepareStatement(sql);
						ResultSet rs = stmt.executeQuery();
								
								
						if (rs.next()) {
							sql = "UPDATE customer_orders SET quantity = quantity + 1 WHERE customer_id = "
									+ customer_id + " AND movie_id = " + movie_id;
							stmt = dbconn.prepareStatement(sql);
							stmt.executeUpdate();
						} else {
							sql = "INSERT INTO customer_orders VALUES (" + movie_id + ", " + customer_id + ",1 )";
							stmt = dbconn.prepareStatement(sql);
							stmt.executeUpdate();
						}
						rs.close();
						stmt.close();
						session.setAttribute("cart", carta);
					}
				}
			}
			if (carta.isempty()) {
				session.removeAttribute("cart");
			}
			//stmt.close();
			dbconn.close();
			if (input.equals("update") || input.equals("delete") || input.equals("delete_all")) {
				response.sendRedirect("TheCart.jsp");

			} else {

				response.sendRedirect("AddedToCart.jsp");
			}

		} catch (Exception ex) {
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
