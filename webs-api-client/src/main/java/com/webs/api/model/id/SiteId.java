package com.webs.api.model.id;

import java.io.Serializable;


/**
 * Interface representing a unique identifier corresponding to a Site object.
 * This consists of either an ID or a username.
 *
 * @author Patrick Carroll
 */
public class SiteId implements Serializable {
	private Long id;

	private String username;

	public SiteId(Long id) {
		setId(id);
	}

	public SiteId(String username) {
		setUsername(username);
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		if (getId() != null)
			return getId().toString();
		else if (getUsername() != null)
			return getUsername().toString();

		return null;
	}
}
