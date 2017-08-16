<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="objects.*"%>
<%
if (request.getSession().getAttribute("id") == null) {
		response.sendRedirect("Login.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Cart</title>


<style>
input[type=text]:focus {
	border: 3px solid #555;
}

img {
	white-space: pre
}

.delete {
	position: relative;
	top: 35px;
	right: 35px;
	color: #f1f1f1;
	font-size: 40px;
	font-weight: bold;
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

.header_content {
	font-size: 50px;
	color: tomato;
	font-family: Arial;
	font-weight: bold;
	padding: 0px;
	margin: 0px;
}

.text_content {
	color: tomato;
	font-size: 35px;
	font-family: cursive;
	padding: 0px;
	margin: 0px;
}

.background {
	background-color: #f2f3f4;
	position: static;
	padding: 100px;
	overflow: hidden;
	width: 100%;
	height: 100%;
	margin: 0;
}

html body {
	margin: 0;
	padding: 0;
}

.main_content {
	float: left;
	width: 80%;
	background-color: #ffffff;
	position: static;
	left: -200px;
	border-right: 1px solid #f2f3ff;
	text-align: center;
}

.main_table {
	align: center;
	width: 100%;
	border-bottom: 3px solid #DCDCDC;
}

input[type=text] {
	width: 15%;
	padding: 12px 20px;
	margin: 8px 0;
	box-sizing: border-box;
	border: 3px solid #ccc;
	-webkit-transition: 0.5s;
	transition: 0.5s;
	outline: none;
}

.hl_button_0 {
	color: tomato;
	font-size: 35px;
	font-family: cursive;
	padding: 0px;
	margin: 0px;
	background: none;
	border: none;
	padding: 0;
	align: center;
	font: inherit;
	/*border is optional*/;
	cursor: pointer;
}

.sl_button {
	width: 20%;
	padding: 12px 0px;
	margin: 0 0;
	background-color: #A9A9A9;
	color: black;
	font-family: cursive;
	box-sizing: border-box;
	border: 3px solid #ccc;
	-webkit-transition: 0.2s;
	transition: 0.2s;
	outline: none;
	font-weight: bold
}

.sl_button:hover {
	background-color: tomato;
	color: white;
	border: 3px solid tomato;
}

.sidebar_right {
	width: 20%;
	float: right;
	display: block;
	padding: 20px 0 20px 0;
	overflow: hidden;
	background-color: #f2f3f4;
	border-radius: 5px;
	position: fixed;
	text-align: center;
	top: 30%;
	left: 76%;
}

.s_button {
	background: tomato;
	border: 2px solid #f56c6c;
	color: #FFFAFA;
	width: 80%;
	align: center;
	padding: 10px 10px 10px 10px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	-webkit-border-radius: 5;
	-moz-border-radius: 5;
	border-radius: 5px;
	font-size: 14px;
	font-family: cursive;
	font-weight: bold;
	position: relative;
}

.s_button:hover {
	background: DarkRed;
	border: 2px solid DarkRed;
}
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

	<div class="background">

		<div class="main_content" id="main_content">

			<c:if test="${not empty cart}">
				<c:forEach items="${cart.items}" var="Item">
					<table class="main_table">
						<tr>
							<th rowspan="4" colspan="2">
								<p>
									<img src="${Item.movie.photo_url}" title="banner not found"
										alt="banner not found" height="300" width="200" />
								</p>
							</th>
							<th colspan="3">
								<h3 style="font-size: 20px">${Item.movie.title}</h3>
								<p>&times;${Item.quantity}</p>
							</th>
							<th style="vertical-align: top">
								<form action="CartBehav" method="POST">
									<input type="hidden" name="movie" value="${Item.movie.id}"></input>
									<button class="hl_button" type="submit" name="request"
										value="delete">&times;</button>
								</form>
							</th>
						</tr>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
						<tr>
							<th colspan="2"></th>
							<th></th>
							<th></th>
						</tr>
						<tr>
							<th colspan="2">
								<form action="CartBehav" method="POST">
									<input type="text" name="quantity" value="${Item.quantity}"
										onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode == 8'></input>
									<input type="hidden" name="movie" value="${Item.movie.id}"></input>
									<button class="sl_button" type="submit" name="request"
										value="update">UPDATE</button>
								</form>
							</th>
							<th></th>
							<th></th>
							<th></th>
						</tr>

					</table>
				</c:forEach>
			</c:if>
			<c:if test="${empty cart}">
				<br>
				<br>
				<br>
				<br>

				<script type="text/javascript">
					var div = document.getElementById('main_content');
					div.style.backgroundColor = '#f2f3f4';
				</script>


				<p class="header_content" style="text-align: center">Oops!</p>
				<p class="text_content" style="text-align: center">There is no
					item in the shopping cart.....</p>
				<br>
			</c:if>
		</div>
		<br>
		<div class="sidebar_right">

















			<form action="checkout.jsp" method="POST">
				<button class="s_button" type="submit" name="request"
					value="checkout">CHECK OUT</button>
			</form>
			<br> <br>
			<form action="CartBehav" method="POST">
				<button class="s_button" type="submit" name="request"
					value="delete_all">Empty the Cart</button>
			</form>

		</div>
		<br style="clear: both;" /> <br> <br> <br> <br> <br>
		<br> <br> <br> <br> <br> <br> <br>
		<br> <br>
	</div>


</body>
</html>