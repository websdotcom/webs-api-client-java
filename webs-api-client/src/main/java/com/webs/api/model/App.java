package com.webs.api.model;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Patrick Carroll
 */
public class App implements Serializable {
	private Long id;

	private String handle;

	private String name;

	private Date releaseDate;

	private String description;

	private String category;

	private String developerName;

	private String developerUrl;


	public App() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public String getDeveloperUrl() {
		return developerUrl;
	}

	public void setDeveloperUrl(String developerUrl) {
		this.developerUrl = developerUrl;
	}
}
