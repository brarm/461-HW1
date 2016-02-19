package blog;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailCronJobServlet extends HttpServlet {

	private static final Logger _logger = Logger.getLogger(EmailCronJobServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
        List<BlogPost> blogPosts = ObjectifyService.ofy().load().type(BlogPost.class).list();
    	Collections.sort(blogPosts);
    	
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, -1);
    	Date cutoffDate = cal.getTime();
    	
        String msgBody = "Activity in the past 24 hrs: \n\n";
    	for (int i = 0; i < blogPosts.size(); i += 1) {
    		BlogPost post = blogPosts.get(i);
    		if (post.getDate().after(cutoffDate)) {
    			msgBody += post.getUser().getNickname() + " posted: \n";
    			msgBody += post.getTitle() + "\n";
    			msgBody += post.getContent() + "\n";
    			msgBody += "On " + post.getDate();
    			msgBody += "\n\n\n";
    		}
    	}
    	
		try {
			_logger.info("Cron Job has been executed");
			
			List<Subscriber> subscribers = ofy().load().type(Subscriber.class).list();
			
			subscribers.add(new Subscriber("yannigeorg@gmail.com"));
			
			for (Subscriber s : subscribers) {
				String subscriberEmail = s.getEmailAddress();
				
		        Properties props = new Properties();
		        Session session = Session.getDefaultInstance(props, null);

		        try {
		            Message msg = new MimeMessage(session);
		            msg.setFrom(new InternetAddress("Admin@web-blog-1226.appspotmail.com", "Web Blog"));
		            msg.addRecipient(Message.RecipientType.TO,
		                             new InternetAddress(subscriberEmail, "Mr. User"));
		            msg.setSubject("Daily Activity Digest: Blog");
		            msg.setText(msgBody);
		            Transport.send(msg);

		        } catch (AddressException e) {
		        } catch (MessagingException e) {
		        }
			}

		}
		catch (Exception ex) {
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

