<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.*" %>
<%@ page import="guestbook.BlogPost" %>
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
	<%
		String blogName = request.getParameter("blogName");
	    if (blogName == null) {
	        blogName = "default";
	    }
	    pageContext.setAttribute("blogName", blogName);
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    if (user != null) {
	      pageContext.setAttribute("user", user);
	%>
			<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
			<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
	<%
	    } else {
	%>
			<p>Hello!
			<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
			to include your name with greetings you post.</p>
	<%
	    }
	%>
	
	<%
		ObjectifyService.register(BlogPost.class);
		List<BlogPost> blogPosts = ObjectifyService.ofy().load().type(BlogPost.class).list();   
		Collections.sort(blogPosts);
		// trim posts
		if (blogPosts.size() > 5) blogPosts = new ArrayList<BlogPost>(blogPosts.subList(0, 5));
	    if (blogPosts.isEmpty()) {
	        %>
	        <p>Guestbook '${fn:escapeXml(blogName)}' has no messages.</p>
	        <%
	    } else {
	        %>
	        <p>Posts in Blog '${fn:escapeXml(blogName)}'.</p>
	        <%
	        for (BlogPost blogPost : blogPosts) {
	        	pageContext.setAttribute("blogpost_title", blogPost.getTitle());
	        	pageContext.setAttribute("blogpost_content", blogPost.getContent());
	            pageContext.setAttribute("blogpost_date", blogPost.getDate());
	            
	            if (blogPost.getUser() == null) { %>
	                <p>An anonymous person wrote:</p>
	            <% } else {
	                pageContext.setAttribute("blogpost_user", blogPost.getUser()); %>
	                <p><b>${fn:escapeXml(blogpost_user.nickname)}</b> wrote:</p>
	            <% } %>
	            <blockquote>${fn:escapeXml(blogpost_title)}</blockquote>
	            <blockquote>${fn:escapeXml(blogpost_content)}</blockquote>
	            <blockquote>${fn:escapeXml(blogpost_date)}</blockquote>
	            <%
	        }
	    }
	%>
	<% if (user != null) { %>
		<a href="PostPage.jsp">Create Post</a>
	<% } %>
  </body>
</html>