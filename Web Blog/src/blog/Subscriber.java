package blog;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@ Entity
public class Subscriber {
	@Id String emailAddress;
	
	Subscriber() {}
	
	public Subscriber(String email) {
		emailAddress = email;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String email) {
		emailAddress = email;
	}
	
//	public boolean equals(Object obj) {
//		if(obj == null)
//			return false;
//		Subscriber sub = (Subscriber)obj;
//		if(this.email == sub)
//			return true;
//		
//		if (email.equals(emailAddress)) {
//			return true;
//		}
//		return false;
//	}
	
	public boolean equals(String email) {
		if (email.equals(this.emailAddress)) {
			return true;
		}
		return false;
	}
	
}
