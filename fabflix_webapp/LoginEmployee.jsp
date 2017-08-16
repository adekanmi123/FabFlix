<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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
	<a href="reports/like-predicate"><button type="button" 
			style="float: center">Like-Predicate Report</button></a>
	<a href="reports/readme"><button type="button" 
			style="float: center">README File</button></a>
	<div class="loginemployee">
		<center>
			<p><h1>Welcome to FabFlix!</h1></p>
			<br />

			<div class="message">Login Employee</div>
			<br />
			To test the website out, use the following information: <br />
			Email: classta@email.edu <br />
			Password: classta <br />
			<form name="LoginEmployee" action="LoginEmployee" method="POST">
				<input name="employeeemail" placeholder="E-mail address" /> <input
					name="employeepassword" type="password" placeholder="Password" /> <br />
				<input type="submit" value="Login" id="login_button" />  
			    <center>
			    <a href="Login"><button type="button" 
			style="float: center">I am a Customer</button></a>
			    </center>
			</form>
		</center>

	</div>
	<div style="position: relative">
		<p style="position: fixed; bottom: 0; width: 100%; text-align: center">
			<b>Group 53</b><br/>Jennelle Lai 69498064<br/>Jennifer Lai 16913791<br/>Jinsheng Zhu 33882845</p>
	</div>
</body>
</html>