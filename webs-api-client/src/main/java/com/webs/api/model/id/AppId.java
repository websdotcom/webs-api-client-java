package com.webs.api.model.id;

import java.io.Serializable;


/**
 * Interface representing a unique identifier corresponding to an App object.
 * This consists of either an ID or an app handle (i.e., "photos", 
 * "calendar", etc...)
 *
 * @author Patrick Carroll
 */
public class AppId implements Serializable {
	private Long id;

	private String handle;

	public AppId(Long id) {
		setId(id);
	}

	public AppId(String handle) {
		setHandle(handle);
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}


	@Override
	public String toString() {
		if (getId() != null)
			return getId().toString();
		else if (getHandle() != null)
			return getHandle().toString();

		return null;
	}
}
