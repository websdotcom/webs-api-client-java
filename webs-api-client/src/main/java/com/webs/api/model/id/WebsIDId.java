package com.webs.api.model.id;

import java.io.Serializable;


/**
 * Interface representing a unique identifier corresponding to an Member 
 * object. This consists of either an ID or an email address
 *
 * @author Patrick Carroll
 */
public class WebsIDId implements Serializable {
	private Long id;

	private String emailAddress;

	public WebsIDId(Long id) {
		setId(id);
	}

	public WebsIDId(String emailAddress) {
		setEmailAddress(emailAddress);
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	@Override
	public String toString() {
		if (getId() != null)
			return getId().toString();
		else if (getEmailAddress() != null)
			return getEmailAddress().toString();

		return null;
	}
}
