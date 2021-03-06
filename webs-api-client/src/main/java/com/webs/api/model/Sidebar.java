package com.webs.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @author Patrick Carroll
 */
public class Sidebar implements Serializable {
	private static long serialVersionUID = 1L;

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
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
}
