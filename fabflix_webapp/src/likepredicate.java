

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class likepredicate
 */
@WebServlet("/reports/like-predicate")
public class likepredicate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public likepredicate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
		out.println(docType + "<HTML>\n" + "<BODY BGCOLOR=\"#FDF5E6\">\n" + "<body>" + "Group Number 53" + "<br />" +
				"We used the LIKE predicate for when the user ticks the box \"Substring Matching\" within \"MovieList.java\"." + "<br /> <br /> <br />" +


				"Here are the ways that we used LIKE:" + "<br /> <br /> <br />" +


					"1) Browse By Title" + "<br />" +
							"- Java SQL Statement in MovieList.java on lines 154 to 160." + "<br />" +
							"- For example, when the user selects the letter \"A\" to browse by title, that SQL statement turns into:" + "<br />" +
								"WHERE m.title LIKE \"A%\";" + "<br />" +
							  "  This should show all movies that begin with the letter 'A', and then could be followed by any other characters. " + "<br /> <br />" +

					"2) Browse By Attribute" + "<br />" +
							"- Java SQL Statement in MovieList.java on line 144 to 151." + "<br />" +
							"- The user can type in a text for any other the attributes to browse by attribute and any movie that contains that text in its name will be shown." + "<br />" +
							"- The phrase \"COLLATE UTF8_GENERAL_CI\" right before the LIKE means that the matching between the user's text and the attributes are case-insensitive." + "<br />" +
							"- For example, if the user types in \"ad\" for the attribute \"Star's first name\", then the SQL statement turns into: " + "<br />" +
							"  WHERE s.first_name LIKE \"%ad%\";" + "<br />" +
							"  This should show all movies that featured stars whose first names have \"ad\" in them." + "<br />" +
							"- Another example, if the user types in \"t_m\" for the attribute \"Star's first name\", then the SQL Statement turns into:" + "<br />" +
								"  WHERE s.first_name LIKE \"%t_m%\";" + "<br />" +
							"  This should show all movies that features stars whose first names could start with any characters, then contain a 't', and then any character following " + "<br />" +
							  "  after that 't', and then an 'm' following that character, and then followed by any characters.");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
