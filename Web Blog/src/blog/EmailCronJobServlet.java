package blog;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

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
	
		try {
			_logger.info("Cron Job has been executed");
			
			//Put your logic here
			//BEGIN
			
			List<Subscriber> subscribers = ofy().load().type(Subscriber.class).list();
			
			subscribers.add(new Subscriber("yanni.georghiades@utexas.edu"));
			
			for (Subscriber s : subscribers) {
				String subscriberEmail = s.getEmailAddress();
				
		        Properties props = new Properties();
		        Session session = Session.getDefaultInstance(props, null);

		        String msgBody = "please work";

		        try {
		            Message msg = new MimeMessage(session);
		            msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
		            msg.addRecipient(Message.RecipientType.TO,
		                             new InternetAddress(subscriberEmail, "Mr. User"));
		            msg.setSubject("Your Example.com account has been activated");
		            msg.setText(msgBody);
		            Transport.send(msg);

		        } catch (AddressException e) {
		            // ...
		        } catch (MessagingException e) {
		            // ...
		        }
			}
			
			
			
			//END
		}
		catch (Exception ex) {
			//Log any exceptions in your Cron Job
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

