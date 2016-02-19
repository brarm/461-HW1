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
	
	String blogName = "";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ObjectifyService.register(blog.BlogPost.class);
		ObjectifyService.register(blog.Subscriber.class);
		blogName = config.getInitParameter("blogName");
		System.out.println("----- init done -----");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("do get in ofyBlog!!!!!");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
					
		if(userService.isUserLoggedIn()) {
			req.setAttribute("user", user);
			req.setAttribute("email", user.getEmail());
		}
		
		String blogName = this.blogName;
		req.setAttribute("blogName", blogName);
		
		List<BlogPost> blogPosts = ObjectifyService.ofy().load().type(BlogPost.class).list();
		Collections.sort(blogPosts);
		req.setAttribute("blogPosts", blogPosts);
		
		List<Subscriber> currentSubscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();	    
	    req.setAttribute("currentSubscribers", currentSubscribers);
	    System.out.println("forwarding to /ofyblog.jsp from BlogServlet doGet");
		req.getRequestDispatcher("/ofyblog.jsp").forward(req, resp);
		System.out.println("after forward to /ofyblog.jsp from BlogServlet doGet\n\n");
	}
}