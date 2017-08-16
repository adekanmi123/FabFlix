<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%
	if (request.getSession().getAttribute("employeeemail") == null) {
		response.sendRedirect("LoginEmployee");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src='https://www.google.com/recaptcha/api.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Login</title>
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
	<a href="LogoutEmployee"><button type="button" style="float: right">Logout</button></a>
	<a href="reports/like-predicate"><button type="button"
			style="float: center">Like-Predicate Report</button></a>
	<a href="reports/readme"><button type="button"
			style="float: center">README File</button></a>

	<center>
		<form action="AddStar" method="get">
			<b>Add Star</b> <br>
			<table>
				<tr>
					<input type="text" placeholder="First Name" name="first_name"
						style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Last Name" name="last_name"
						required style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Date of Birth" name="dob"
						style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Photo URL" name="photo_url"
						style='width: 20em' />
					</input>
					<br>
				</tr>
			</table>
			<input type="submit" value="Add Star" class="submit" />
		</form>

	</center>
	<br>
	<br>

	<center>

		<form action="AddMovie" method="get">
			<b>Add Movie</b> <br>
			<table>
				<tr>
					<input type="text" placeholder="Star's First Name"
						name="first_name" style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Star's Last Name" name="last_name"
						required style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Star's Date of Birth" name="dob"
						style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Star's Photo URL" name="photo_url"
						style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Movie's Title" name="title"
						required style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Movie's Year" name="year" required
						style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Movie's Director" name="director"
						required style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Movie's Banner URL"
						name="banner_url" style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Movie's Trailer URL"
						name="trailer_url" style='width: 20em' />
					</input>
					<br>
				</tr>
				<tr>
					<input type="text" placeholder="Movie's Genre" name="genre"
						required style='width: 20em' />
					</input>
					<br>
				</tr>
			</table>
			<input type="submit" value="Add Movie" class="submit" />
		</form>

	</center>
	<br>
	<br>


	</center>

	<form action="Metadata" method="get">
		<center>
			<b>Show Database Metadata</b> <br> <br> <input
				type="submit" name="showMetadata" value="Show Metadata"
				class="submit" />
		</center>
	</form>


</body>
</html>