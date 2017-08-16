<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%
	if (request.getSession(false) != null) {
		if (request.getSession().getAttribute("id") != null) {
			response.sendRedirect("Main");
		}
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
	<br />
	<a href="reports/like-predicate"><button type="button" 
			style="float: center">Like-Predicate Report</button></a>
	<a href="reports/readme"><button type="button" 
			style="float: center">README File</button></a>
	<div class="loginapp">
		<center>
			<p><h1>Welcome to FabFlix!</h1></p>
			<br />

			<div class="message">Login App</div><br />
			To test the website out, use the following information: <br />
			Email: green@ics.com <br />
			Password: green <br />
			<br />
			<form action="LoginApp" method="GET">
				<input name="email" placeholder="E-mail address" /> <input
					name="password" type="password" placeholder="Password" /> <br />
				<input type="submit" value="Login" id="login_button" />  
			</form>
		
		</center>

	</div>
	<div style="position: relative">
		<p style="position: fixed; bottom: 0; width: 100%; text-align: center">
			<b>Group 53</b><br/>Jennelle Lai 69498064<br/>Jennifer Lai 16913791<br/>Jinsheng Zhu 33882845</p>
	</div>
</body>
</html>