<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.util.ArrayList"%>
<%@ include file="/topic.jsp"%>
<%
/* 	if (request.getSession().getAttribute("id") == null) {
		response.sendRedirect("Login.jsp");
	} */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Movie List</title>
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


 	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.js"></script>
	 <script type="text/javascript" src="javascript/jquery.qtip.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/jquery.qtip.min.css" /> 



</head>
<body>

	<%@ include file="/topic.jsp"%>
	<br />

	<a href="TheCart.jsp"><button type="button" >Cart</button></a>
	<a href="Main"><button type="button" >Main</button></a>
	<a href="Logout"><button type="button" 
			style="float: right">Logout</button></a>

	<br />
	<div id="MovieList">
		<p style="text-align: center">
			<b>Order By:</b> • <a
				href="MovieList?${parameters}&order=ascendingByTitle&page=${page}&limit=${limit}">Ascending By Title</a> • <a
				href="MovieList?${parameters}&order=descendingByTitle&page=${page}&limit=${limit}">Descending By Title</a> • <a
				href="MovieList?${parameters}&order=ascendingByYear&page=${page}&limit=${limit}">Ascending By Year</a> • <a
				href="MovieList?${parameters}&order=descendingByYear&page=${page}&limit=${limit}">Descending By Year</a> •
		</p>

		<br />

		<c:forEach items="${movies}" var="movie">
			<img src="${movie.banner_url}" width="200" height="250"
				style="float: left" />



			<form name="AddMovieToCart" action="CartBehav" method="POST">
				<input type="hidden" name="movie" value="${movie.id}"></input> <input
					type="hidden" name="type" value="list"></input>
				<button type="submit" name="request"
					value="additem" style="float: right">Add to Cart</button>
			</form>

			<a href="${movie.trailer_url}"><button type="button"
					>Preview</button></a>
			<br />
			<br />
			<b>Title: </b>
		
			<u class="movieID" id="${movie.id}"><a href="MovieDetails?movieid=${movie.id}">${movie.title}</a></u>

			
			
			<br />
			<br />
			<b>Movie ID:</b> ${movie.id} <br />
			<br />
			<b>Year: </b>${movie.year} <br />
			<br />
			<b>Director: </b>${movie.director} <br />
			<br />
			<b>Genre: </b>
			<c:forEach items="${movie.genres}" var="genre">
				${genre.name},
			</c:forEach>
			<br />
			<br />
			<b>Stars: </b>
			<c:forEach items="${movie.stars}" var="star">
				<a href="StarDetails?starid=${star.id}">${star.first_name}
					${star.last_name}</a>
					, 
			</c:forEach>
			<br />
			<br />
			<br />
			<br />
			
			<script type="text/javascript">
		
		$('u').qtip({
			content: {
				text: function(event, api) {
                    $.ajax({
                    	url: 'MoviePopup', // Use href attribute as URL
                         type: 'GET',
                        data: {id :$(this).attr('id')},
                        /* dataType: 'text/html'   */ 
                    })
                     .then(function(content) {
                        // Set the tooltip content upon successful retrieval
                        api.set('content.text', content);
                    }, function(xhr, status, error) {
                        // Upon failure... set the tooltip content to error
                        api.set('content.text', status + ': ' + error);
                    }); 
        
                    return 'Loading...'; // Set some initial text
                }
				/* text: $(
				
				'<img src="${movie.banner_url}" width="50" height="100"/>' +
				'<form name="AddMovieToCart" action="CartBehav" method="POST"><input type="hidden" name="movie" value="${movie.id}"></input> <input type="hidden" name="type" value="list"></input> <button type="submit" name="request" value="additem" style="float: right">Add to Cart</button></form>'

				), */
			},
			show: {
				event: 'mouseover'
			},
			position: {
				adjust: {
	                 screen: true
	             },
            	my: 'right'
            },
            hide: {
            	fixed: true,
            	//delay:500, 
        		//event: 'click mouseleave'
        	}
		})
		</script>
			
			
		</c:forEach>

				<center>
				<c:if test="${page > 0}">
						<a href="MovieList?${parameters}&order=${order}&page=${page-1}&limit=${limit}">
							<button>Previous Page</button>
						</a>
					</c:if>
					<c:if test="${page < totalPages}">
						<a href="MovieList?${parameters}&order=${order}&page=${page+1}&limit=${limit}">
							<button>Next Page</button>
						</a>
					</c:if>
					</center>
					
					<p style="text-align:center">
						<b> Results Per Page: <b></b> •
						<!-- 10, 25, 50, 100  -->
						<a href="MovieList?${parameters}&order=${order}&page=0&limit=5">5</a> •
						<a href="MovieList?${parameters}&order=${order}&page=0&limit=10">10</a> •
						<a href="MovieList?${parameters}&order=${order}&page=0&limit=25">25</a> •
						<a href="MovieList?${parameters}&order=${order}&page=0&limit=50">50</a> •
						<a href="MovieList?${parameters}&order=${order}&page=0&limit=100">100</a> •
					</p>
	</div>

</body>
</html>




