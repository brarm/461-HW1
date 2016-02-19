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
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Subscriber))
			return false;
		Subscriber sub = (Subscriber)obj;
		if(this == sub) return true;
		return this.emailAddress.equals(sub.emailAddress); 
	}
	
}
