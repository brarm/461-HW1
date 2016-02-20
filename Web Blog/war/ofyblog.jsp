<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="blog.BlogPost" %>
<%@ page import="blog.Subscriber" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<title>Bootstrap 101 Template</title>
	<!-- Bootstrap -->
	<link href="stylesheets/css/bootstrap.min.css" rel="stylesheet">
	<link href="stylesheets/blog.css" rel="stylesheet">
	<!-- css other -->
	<link href="stylesheets/main.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body>

	<div class="container">
		<div class="jumbotron">
			<div class="blog-header">
	      	<h1 class="blog-title">${blogName}</h1>
	   		<p class="lead blog-description">This is a web blog. What did you expect?</p>
		   </div>

		   <c:choose>
		   	<c:when test="${logged_in}">
		   		<h3>Hello, ${user.nickname}!</h3>
		   		
		   		<form class="form-inline" action="/post" method="get">
		   			<div class="form-group">
	   					<input type="submit" class="btn btn-primary" role="button"Create Post</button>
	   				</div>
   				</form>
   				<form class="form-inline">
	   				<div class="form-group">
	   					<a href="<%= request.getAttribute("logoutURL") %>" class="btn btn-primary" role="button">Sign Out</button>
	   				</div>
				</form>
		   	</c:when>
		   	<c:otherwise>
		   		<h3>Hello!</h3>
		   		<h4>You must be signed in to post</h4>
		   		<p><a href="<%= request.getAttribute("loginURL") %>" class="btn btn-success" role="button">Sign in</a>
		   	</c:otherwise>
		   </c:choose>
	   </div>
	
	   <c:choose>
			<c:when test="${empty blogPosts}">
				<p></p>
			</c:when>
			<c:otherwise>
				<c:forEach var="blogPost" items="${blogPosts}">
					<c:set var="blogpost_title" value="${blogPost.title}"/>
					<c:set var="blogpost_content" value="${blogPost.content}"/>
					<c:set var="blogpost_date" value="${blogPost.date}"/>
					<c:set var="blogpost_user" value="${blogPost.user}"/>

					<c:choose>
						<c:when test="${blogpost_user == null}">
							<c:set var="blogpost_user_string" value="an anonymous person"/>
						</c:when>
						<c:otherwise>
							<% System.out.println("user is " + pageContext.getAttribute("blogpost_user")); %>
							<c:set var="blogpost_user_string" value="<b>${blogpost_user}<b>"/>
						</c:otherwise>
					</c:choose>

					<div class="row">
        				<div class="col-sm-8 blog-main">
							<div class="blog-post">
								<h2 class="blog-post-title">${blogpost_title}</h2>
								<p class="blog-post-meta">${blogpost_date} by <a href="#">${blogpost_user_string}</a></p>
								<p>${blogpost_content}</p>
							</div>
						</div>
					</div>

				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>