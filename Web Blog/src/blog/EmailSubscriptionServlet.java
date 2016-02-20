package blog;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.googlecode.objectify.Objectify;

public class EmailSubscriptionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean subscribeAction = Boolean.valueOf(req.getParameter("sub"));
        String email = req.getParameter("email");
		Objectify ofy = ofy();
		HttpSession session = req.getSession(true);
        
        if(subscribeAction) {
        	ofy.save().entity(new Subscriber(email)).now();
        	String message = "You have successfully subscribed!"
        			+ "\nAll posts from the past 24 hours"
        			+ "\nwill be emailed to you at 5 pm, every day";
        	session.setAttribute("message", message);
        }
        else {
        	ofy.delete().type(Subscriber.class).id(email).now();
        	String message = "You have successfully unsubscribed!";
        	session.setAttribute("message", message);
        }
        resp.sendRedirect("/homepage");
        //req.getRequestDispatcher("/homepage").forward(req, resp);
        
	}
}
