package com.webs.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;

import com.webs.api.exception.WebsApiException;
import com.webs.api.model.App;


/**
 * @author Patrick Carroll
 */
public class WebsApiClient implements AppApi {
	private String apiPath = "http://127.0.0.1:8080/webs-api/";
	
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
		String data = apiHttpGet(getApiPath() + "apps/");
		return null;
	}

	/**
	 * Get the app corresponding to the given appId
	 */
	public App getApp(final Long appId) {
		try {
			String data = apiHttpGet(getApiPath() + "apps/" + appId + "/");
			return jsonMapper.readValue(data, App.class);
		} catch (IOException e) {
			System.out.println("exception = " + e);
			return null;
		}
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


	private String apiHttpGet(String url) {
		HttpClient client = new HttpClient();

		// XXX externalize all of this code for getting connections,
		// making requests, etc...
		try {
			GetMethod method = new GetMethod(url);
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK)
				return null;

			String body = new String(method.getResponseBody());

			method.releaseConnection();

			return extractContent(body);
		} catch (Exception e) {
			// XXX catch all of the different kinds of exceptions
			System.out.println("got exception = " + e);
		}

		return null;
	}

	// XXX it's stupid that this parses JSON but returns a String
	// representing the content of the message which has already been parsed
	private String extractContent(String jsonString) throws Exception {
		JsonNode rootNode = jsonMapper.readValue(jsonString, JsonNode.class);
		// XXX check "success" and throw exception if appropriate
		return rootNode.path("content").toString();
	}
}
