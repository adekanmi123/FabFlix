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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Movie Details</title>
<style>
.background {
	background-color: #f2f3f4;
	position: static;
	padding: 100px;
	overflow: hidden;
	width: 100%;
	height: 100%;
	margin: 0;
}

.main_content {
	width: auto%;
	background-color: #f2f3f4;
	border-right: 1px solid #f2f3f4;
	height: auto;
	margin: 0 0;
	padding: 10px;
	position: relative;
	text-align: center;
}

html body {
	margin: 0;
	padding: 0;
}

.sl_button {
	width: 20%;
	padding: 12px 0px;
	margin: 0 0;
	background-color: tomato;
	color: white;
	font-family: cursive;
	box-sizing: border-box;
	border: 3px solid #ccc;
	-webkit-transition: 0.2s;
	transition: 0.2s;
	outline: none;
	font-weight: bold
}

.sl_button:hover {
	background-color: Darkred;
	color: white;
	border: 3px solid Darkred;
}

.hl_button {
	background: none;
	color: inherit;
	border: none;
	padding: 0;
	vertical-align: top;
	font: inherit;
	color: black;
	font-size: 40px;
	font-weight: bold;
	/*border is optional*/;
	cursor: pointer;
	-webkit-transition-duration: 0.4s; /* Safari */
	transition-duration: 0.4s;
}

.hl_button:hover {
	color: red;
}

input[type=text] {
	width: 20%;
	padding: 5px 5px;
	margin: 3px 0;
	font-size: 25px;
	box-sizing: border-box;
	border: 3px solid #f2f3f4;
	-webkit-transition: 0.5s;
	transition: 0.5s;
	outline: none;
	border-radius: 15px
}

input[type=text]:focus {
	border: 3px solid #555;
}
</style>
</head>
<body>

	<%@ include file="/topic.jsp"%>
	<br />

	<a href="TheCart.jsp"><button type="button" >Cart</button></a>
	<a href="Main"><button type="button" >Main</button></a>
	<a href="Logout"><button type="button" 
			style="float: right">Logout</button></a>

	<br />

	<div id="main_content">

		
		<form name="AddMovieToCart" action="CartBehav" method="POST">
			<input type="hidden" name="movie" value="${movie.id}"></input> <input
				type="hidden" name="type" value="detail"></input>
			<button align="right" type="submit" 
				name="request" value="additem" style="float: right;">Add
				to Cart</button>
		</form>
		
		<br />

		<p align="center">
			<a href="${movie.trailer_url}"><button type="button"
					>Preview</button></a>
		</p>


		<p style="text-align: center">
			<b>Title:</b> ${movie.title}
		</p>

		<p style="text-align: center">
			<img src="${movie.banner_url}" width="200" height="250"
				id="movie_poster" />
		</p>

		<center>
			<b>Movie ID:</b> ${movie.id} <br /> <b>Year:</b> ${movie.year} <br />
			<b>Director:</b> ${movie.director} <br /> <b>Stars:</b>
			<c:forEach items="${stars}" var="star"> <a href="StarDetails?starid=${star.id}">${star.first_name}
					${star.last_name}</a>, </c:forEach>
			<br /> <b>Genres:</b>
			<c:forEach items="${genres}" var="genre">
						${genre.name}, 
					</c:forEach>
			<br />
		</center>

	</div>

</body>
</html>