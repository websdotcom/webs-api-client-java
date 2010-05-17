package com.webs.api.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @author Patrick Carroll
 */
public class Sidebar implements Serializable {
	private Long id;

	private String title;

	private boolean onHomePage;

	private boolean onAllPages;


	public Sidebar() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isOnHomePage() {
		return onHomePage;
	}

	public void setOnHomePage(boolean onHomePage) {
		this.onHomePage = onHomePage;
	}

	public boolean isOnAllPages() {
		return onAllPages;
	}

	public void setOnAllPage(boolean onAllPages) {
		this.onAllPages = onAllPages;
	}


	@Override
	public String toString() {
		return getTitle();
	}
}
