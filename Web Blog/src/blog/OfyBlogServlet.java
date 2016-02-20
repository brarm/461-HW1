//http://1-dot-ofy-blog-1208.appspot.com/ofyguestbook.jsp
package blog;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyBlogServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String blogName = "";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ObjectifyService.register(blog.BlogPost.class);
		ObjectifyService.register(blog.Subscriber.class);
		blogName = config.getInitParameter("blogName");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		this.doGet(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String posts = "";
		if(!req.getParameterMap().isEmpty())
			posts = req.getParameter("posts");
					
		if(userService.isUserLoggedIn()) {
			req.setAttribute("logged_in", true);
			req.setAttribute("user", user);
			req.setAttribute("email", user.getEmail());
			String logoutURL =  userService.createLogoutURL(req.getRequestURI());
			req.setAttribute("logoutURL", logoutURL);
			
			List<Subscriber> currentSubscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();	    
		    Subscriber sub = new Subscriber(user.getEmail());
		    if(currentSubscribers.contains(sub))
		    	req.setAttribute("subscribed", true);
		    else
		    	req.setAttribute("subscribed", false);
		} else {
			req.setAttribute("logged_in", false);
			String loginURL =  userService.createLoginURL(req.getRequestURI());
			req.setAttribute("loginURL", loginURL);
		}
		
		String blogName = this.blogName;
		req.setAttribute("blogName", blogName);
		
		List<BlogPost> blogPosts = ObjectifyService.ofy().load().type(BlogPost.class).list();
		Collections.sort(blogPosts);
		/* truncation if input parameter specified and <=10 in case of manual user nav */
		/* truncation by default and all posts if user requests */
		if(posts.isEmpty() || posts.equalsIgnoreCase("some")) {
			if(blogPosts.size() > 5) 
				blogPosts = blogPosts.subList(0, 5);
			req.setAttribute("truncated", true);
		} else if (posts.equalsIgnoreCase("all")) {
			req.setAttribute("truncated", false);
		}
		req.setAttribute("blogPosts", blogPosts);
		req.getRequestDispatcher("/ofyblog.jsp").forward(req, resp);
	}
}