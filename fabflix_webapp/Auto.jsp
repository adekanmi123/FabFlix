<%@page import = "javax.servlet.http.*"%>
<%@page import = "javax.servlet.*" %>
<%@page import = "java.sql.*" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.sql.DataSource"%>
<%
Connection con = null;
Statement stmt = null;
ResultSet rs = null;
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

con = ds.getConnection();
if (con == null)
    out.println("dbcon is null.");

Class.forName("com.mysql.jdbc.Driver");
//stmt = con.createStatement();

String query = request.getParameter("q");
response.setHeader("Content-Type", "text/html");


String[] splited = query.split("\\s+");

String bool = "";
for (int i = 0; i < splited.length; i++) {
		bool += "+" + splited[i] + "* ";
}

bool = bool.trim();

System.out.println(bool);

String getTitles = "SELECT title FROM ft WHERE MATCH (title) AGAINST ('" + bool + "' IN BOOLEAN MODE)";
stmt = con.prepareStatement(getTitles);	
System.out.println(getTitles);

rs = stmt.executeQuery(getTitles);

while(rs.next())
{
	out.println(rs.getString("title"));
    /* movieTitles.add(rs.getString("title")); */
}

/* out.println(movieTitles);  */
    
%>