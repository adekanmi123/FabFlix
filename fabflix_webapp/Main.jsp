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
<title>Main</title>
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

	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="javascript/jquery-1.4.2.min.js"></script>
    <script src="javascript/jquery.autocomplete.js"></script>

<script>
    jQuery(function(){
        $("#autoTitle").autocomplete("Auto.jsp");
    });
</script>








</head>
<body>

	<%@ include file="/topic.jsp"%>
	<br />
	<a href="TheCart.jsp"><button type="button" >Cart</button></a>
	<a href="Main"><button type="button" >Main</button></a>
	<a href="Logout"><button type="button" 
			style="float: right">Logout</button></a>
	<a href="reports/like-predicate"><button type="button" 
			style="float: center">Like-Predicate Report</button></a>
	<a href="reports/readme"><button type="button" 
			style="float: center">README File</button></a>
	<br />



<center>
<br/>
<br/>
<b>Search Auto-completion</b>
<br/><br/>
<form name="SearchByAuto" action="MovieList" method="GET">
					<input type="text" id="autoTitle" name="autoTitle" class="input_text" placeholder="Title..."/>
					<input type="hidden" value="true" name="auto" /> 
					<br/>
					<input type="submit" style="width: 20%;" value="autoSearch" name="autoSearch"  />
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
</center>




	<div class="main">
		<div id="darkbannerwrap"></div>
		<div class="search_option">
			<form name="browse_genre" action="Main" method="POST">
				<center>
					<p>
						<b>Browse by Genre</b>
					</p>
					-  
					<c:forEach var="genre" begin="1" end="96" items="${genres}">
						<a href="MovieList?genre=${genre}"> ${genre} </a>
  			-  
  	</c:forEach>
				</center>
			</form>

			<br /> <br />

		</div>

		<div class="search_option">
			<form name="browse_title" action="Main" method="POST">
				<center>
					<p>
						<b>Browse by Title</b>
					</p>
					 
					<c:set var="alphabet"
						value="0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z" />
					-  
					<c:forTokens var="letter" items="${alphabet}" delims=",">
    <a href="MovieList?title=${letter}"> ${letter} </a>
  		-
  </c:forTokens>
				</center>
			</form>
		</div>

		<br /> <br />

		<div class="Search">
			<center>
				<p>
					<b>Search By Attribute</b>
				</p>
				<form name="SearchByAttribute" action="MovieList" method="GET">
					<input type="text" style="width: 20%;" value="" name="title"
						placeholder="Title" /> <br /> <input type="text"
						style="width: 20%;" value="" name="year" placeholder="Year" /> <br />
					<input type="text" style="width: 20%;" value="" name="director"
						placeholder="Director" /> <br /> <input type="text"
						style="width: 20%;" value="" name="first_name"
						placeholder="Star's First Name" /> <br /> <input type="text"
						style="width: 20%;" value="" name="last_name"
						placeholder="Star's Last Name" /> <br /> <input type="checkbox"
						name="substringMatching" value="true" />Substring Matching<input type="checkbox"
						name="fuzzysearch" value="true" />Fuzzy Search<br> <br />
					<input type="hidden" value="true" name="search" /> 
					<input type="submit" style="width: 20%;" value="Search" name="Search"  />
				</form>
			</center>


		</div>
	</div>
</body>


</html>