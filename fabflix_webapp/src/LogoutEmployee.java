import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import objects.*;
//import session.SessionCart;

@WebServlet("/LogoutEmployee")
public class LogoutEmployee extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public LogoutEmployee() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("employeeemail") != null)
		{
			
			session.setAttribute("employeeemail", null);

			response.sendRedirect("LoginEmployee");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
