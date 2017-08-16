<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
if (request.getSession().getAttribute("id") == null) {
	response.sendRedirect("Login.jsp");
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
	<div id="main_content">
		
		<form name="AddMovieToCart" action="CartBehav" method="POST">
			<input type="hidden" name="movie" value="${id}"></input> <input
				type="hidden" name="type" value="detail"></input>
			<button align="right" type="submit" 
				name="request" value="additem">Add
				to Cart</button>
		</form>

			<a href="${trailer_url}"><button type="button"
					>Preview</button></a>
					<br/>
			<b>Title:</b> ${title}
			<br/>
			<img src="${banner_url}" width="100" height="150"
				id="movie_poster" />
			<br/>
			<b>Movie ID:</b> ${id} <br /> <b>Year:</b> ${year} <br />
			<b>Director:</b> ${director} <br />
			<b>Stars:</b> ${stars} <br/>
			<b>Genres:</b> ${genres}
			<br />


	</div>

</body>
</html>