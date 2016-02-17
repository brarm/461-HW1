<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="blog.BlogPost" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body> 
	<form action="/ofysign" method="post">
		<p>Title (optional)</p>
		<div><textarea name="title" rows="3" cols="60"></textarea></div>
		<p>Content</p>
	    <div><textarea name="content" rows="3" cols="60"></textarea></div>
	    <div><input type="submit" value="Post to Blog" /></div>
	</form>
</body>

</html> 