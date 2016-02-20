<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="blog.BlogPost" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>YnM Web Blog</title>

    <!-- Bootstrap -->
    <link href="stylesheets/css/bootstrap.min.css" rel="stylesheet">
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
  
    <div class="container-fluid" style="max-width:750px">
    
	    <div class="jumbotron">
			<div class="blog-header">
				<h1 class="blog-title">New Post</h1>
			<p class="lead blog-description">Write a new post here</p>
		</div>
    	
	    <form class="form-horizontal" role="form" action="/blogpost" method="POST">
	    	<div class="form-group row">
	    		<div class="text-center">
	    			<h1></h1>
	    		</div>
	    	</div>
	    	<div class="form-group row">
		      <label class="control-label col-sm-2" for="text">Title:</label>
		      <div class="col-sm-10">
		        <input type="text" class="form-control" name="title" placeholder="Enter title">
		      </div>
		    </div>
		    <div class="form-group row">
		      <label class="control-label col-sm-2" for="text">Content:</label>
		      <div class="col-sm-10"> 
		        <input type="text" class="form-control" name="content" placeholder="Enter content" required>
		      </div>
		    </div>
		    <div class="form-group row"> 
		      <div class="col-sm-offset-2 col-sm-10 text-center">
		        <button type="submit" class="btn btn-primary">Post to Blog</button>
		        <form action="/home" method="GET">
		        	<button type="button" class="btn btn-default">Cancel</button>
		        </form>
		      </div>
		    </div>
	    </form>
    </div>
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="stylesheets/js/bootstrap.min.js"></script>
  </body>
</html>