package blog;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;

public class BlogPostServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public BlogPostServlet() {
		super();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("----- doGet in BlogPost -----");
		RequestDispatcher rd = req.getRequestDispatcher("/blogpost.jsp");
		rd.forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doPost in blogpost!!!!!!");
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        BlogPost post = new BlogPost();
        if(title == null) {
        	post = new BlogPost(user, content);
        } else { 
        	post = new BlogPost(user, title, content);
        }

        Objectify ofy = ofy();
        ofy.save().entity(post).now();
        
        System.out.println("redirecting to homepage from blogpost!!!!!!");
        req.getRequestDispatcher("/homepage").forward(req, resp);
    }
}
