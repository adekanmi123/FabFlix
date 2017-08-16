<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.util.ArrayList"%>
<%@ include file="/topic.jsp"%>
<%
	if (request.getSession().getAttribute("id") == null) {
		response.sendRedirect("Login.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<br />

			<form action="MovieListApp" method="GET">
				<input name="title" placeholder="Movie Title" /> 
				<input type="submit" value="search" />  
			</form>
		
		</center>

	</div>
	<div style="position: relative">
		<p style="position: fixed; bottom: 0; width: 100%; text-align: center">
			<b>Group 53</b><br/>Jennelle Lai 69498064<br/>Jennifer Lai 16913791<br/>Jinsheng Zhu 33882845</p>
	</div>

</body>
</html>




