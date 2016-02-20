//http://1-dot-ofy-blog-1208.appspot.com/ofyguestbook.jsp
package blog;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
		System.out.println("----- init done -----");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		this.doGet(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("do get in ofyBlog!!!!!");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
					
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
		req.setAttribute("blogPosts", blogPosts);	
		
	    System.out.println("forwarding to /ofyblog.jsp from BlogServlet doGet");
		req.getRequestDispatcher("/ofyblog.jsp").forward(req, resp);
		System.out.println("after forward to /ofyblog.jsp from BlogServlet doGet\n\n");
	}
}