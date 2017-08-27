
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class readme
 */
@WebServlet("/reports/readme")
public class readme extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public readme() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
		out.println(docType + "<HTML>\n" + "<BODY BGCOLOR=\"#FDF5E6\">\n" + "<body>" + "Group Number 53" + "<br />"
				+ "Jennelle Lai 69498064" + "<br />" + "Jennifer Lai 16913791" + "<br />" + "Jinsheng Zhu 33882845"
				+ "<br /> <br /> <br />" +

				"Follow the instructions below to run our FabFlix locally in your machine:" + "<br /><br />"

				+ "********************************************* BEGIN **************************************************"
				+ "<br /> <br />" +

				"Put the project2_53.war file into the webapps folder of your Tomcat directory" + "<br />"
				+ "Then run: '/path_to_your_tomcat_directory/bin/startup.sh'" + "<br /> <br />" +

				"Tomcat should have started! Now you should be able to go to 'localhost::8080/project2_53' and FabFlix should be running" + "<br /> <br /> <br />"
				
				
				+ "********************************************* END **************************************************"
				+ "<br/> <br /> <br />" +

				"If you want to access our FabFlix in our AWS instance, enter in the URL: " + "<br />" +

				"http://54.149.198.42:8080/project2_53/" + "<br /> <br /> <br />" +

				"You should be in!" + "<br /> <br /> <br />" +

				"------------------ For Your Information ---------------" + "<br /> <br/>" +

				"database name: moviedb" + "<br />" + "db username: mytestuser" + "<br />" + "db password: mypassword");

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
