package blog;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity

public class BlogPost implements Comparable<BlogPost> {

    @Id Long id;
    User user;
    String title;
    String content;
    Date date;
    
    BlogPost() {}

    public BlogPost(User user, String content) {
        this.user = user;
        this.content = content;
        date = new Date();
    }
    
    public BlogPost(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        date = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	/*
	 * http://stackoverflow.com/a/13475390 for string format
	 */
	public String toString() {
		if (user != null) {
			String out = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:MM");
			out += user.getEmail() + " " + 
					sdf.format(date) + " " +
					String.format("%1$"+ 15 + "s", content); // truncated to 15 chars
			return out;
		}
		else {
			String out = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:MM");
			out += "Anonymous" + " " + 
					sdf.format(date) + " " +
					String.format("%1$"+ 15 + "s", content); // truncated to 15 chars
			return out;
		}
	}

	@Override
    public int compareTo(BlogPost other) {
        if (date.after(other.date)) {
            return -1;
        } else if (date.before(other.date)) {
            return 1;
        }
        return 0;
    }
}