package blog;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class EmailSubscriptionServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost in EmailSubscription");
		List<Subscriber> currentSubscribers = ofy().load().type(Subscriber.class).list();
        String newEmail = req.getParameter("email");
        if (currentSubscribers.contains(newEmail)) {
        	ofy().delete().type(Subscriber.class).id(newEmail).now();
        }
        else {
        	ofy().save().entity(new Subscriber(newEmail)).now();
        }
	}
}
