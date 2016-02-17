//http://1-dot-ofy-blog-1208.appspot.com/ofyguestbook.jsp
package blog;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class OfySignBlogServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
        ofy().save().entity(post).now();   // synchronous
        //String guestbookName = req.getParameter("guestbookName");
        resp.sendRedirect("/ofyblog.jsp");
    }

}