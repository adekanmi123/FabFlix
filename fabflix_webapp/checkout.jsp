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
<title>Checkout</title>
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
	<a href="TheCart.jsp"><button type="button" class="buttons_navbar">Cart</button></a>
	<a href="Main"><button type="button" class="buttons_navbar">Main</button></a>
	<a href="Logout"><button type="button" class="buttons_navbar"
			style="float: right">Logout</button></a>
	<br />
	<center>
	To test the Checkout out, use the following information: <br />
	Credit Card: 1351234512345123451 <br />
	First Name: Jackie <br />
	Last Name: Brown <br />
	Expiration Date: 2005/01/28 <br />
	</center>
	
	<div class="background">
		<c:if test="${empty cart && checkpass != '1'}">
		<% response.sendRedirect("TheCart.jsp"); %>
		</c:if>
		<c:if
			test="${empty checkpass and checkpass != '0' and checkpass != '1'}">
			<div class="main_content">
				<form name="CheckOut" action="CheckBehav" method="POST">
					<form action="CheckBehav" method="POST">
						<input type="text" placeholder="Credit Card Number" name="crenum"
							style='width: 20em' value=""
							onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode == 32 || event.charCode == 45 || event.charCode == 8 || event.charCode == 47'></input>
						<br /> <input type="text" placeholder="First Name" name="firstn"
							style='width: 20em' value=""></input> <br /> <input type="text"
							placeholder="Last Name" name="lastn" style='width: 20em' value=""></input>
						<br /> <input type="text"
							placeholder="Expiration Date (Ex. 2017/12/31)" name="exdate"
							style='width: 20em' value=""
							onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode == 32 || event.charCode == 45 || event.charCode == 8 || event.charCode == 47'></input>
						<br /> <br> <br>
						<button class="sl_button" type="submit" name="request"
							value="confirm">Confirm</button>
					</form>
			</div>
		</c:if>
		<c:if test="${checkpass == '1'}">
			<div class="main_content">
				<img src="image/success.png" style="width: 50px; height: 50px">
				<br> <br> Your transaction is complete!
				</button>

				<br> <br> <br> <br> <br> <br> <br>
				<br> <br> <br> <br> <br> <br> <br>
				<br>

			</div>



		</c:if>
		<c:if test="${checkpass == '0'}">
			<div class="main_content">
				<img src=image/error.png alt="Oops"
					style="width: 50px; height: 50px"></img> <br /> Incorrect
				Information
				<p style="color: red">${checmemo}</p>
				<form name="CheckOut" action="CheckBehav" method="POST">
					<input type="text" placeholder="Credit Card Number" name="crenum"
						style='width: 20em' value=""
						onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode == 32 || event.charCode == 45 || event.charCode == 8 || event.charCode == 47'></input>
					<br /> <input type="text" placeholder="First Name" name="firstn"
						style='width: 20em' value=""></input> <br /> <input type="text"
						placeholder="Last Name" name="lastn" style='width: 20em' value=""></input>
					<br /> <input type="text"
						placeholder="Expiration Date (Ex. 2017/12/31)" name="exdate"
						style='width: 20em' value=""
						onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode == 32 || event.charCode == 45 || event.charCode == 8 || event.charCode == 47'></input>
					<br /> <br> <br>
					<button class="sl_button" type="submit" name="request"
						value="confirm">Confirm</button>
				</form>
			</div>
		</c:if>
		<br> <br> <br> <br> <br> <br> <br>
		<br> <br> <br> <br> <br> <br> <br>
		<br>

	</div>
</body>
</html>