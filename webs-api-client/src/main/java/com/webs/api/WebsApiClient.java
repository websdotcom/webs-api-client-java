package com.webs.api;

import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.webs.api.model.App;


/**
 * @author Patrick Carroll
 */
public class WebsApiClient implements AppApi {
	private String apiPath = "http://local.members.webs.com:8080/";
	
	private ObjectMapper jsonMapper = new ObjectMapper();


	public WebsApiClient() {
		// XXX can use a SerializationConfig object to configure the 
		// date format
	}

	public WebsApiClient(String apiPath) {
		super();
		setApiPath(apiPath);
	}


	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public String getApiPath() {
		return apiPath;
	}


	/**
	 * Get all publicly available apps
	 */
	public List<App> getAllApps() {
		return null;
	}

	/**
	 * Get the app corresponding to the given appId
	 */
	public App getApp(final Long appId) {
		return null;
	}

	/**
	 * Get all currently installed apps on the given site
	 */
	public List<App> getApps(final Long siteId) {
		return null;
	}

	/**
	 * Install the given app on the given site
	 */
	public void installApp(final Long appId, final Long siteId) {
	}

	/**
	 * Install the given app on the given site
	 */
	public void uninstallApp(final Long appId, final Long siteId) {
	}
}
