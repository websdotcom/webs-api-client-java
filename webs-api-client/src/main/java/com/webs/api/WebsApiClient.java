package com.webs.api;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.webs.api.exception.HttpApiException;
import com.webs.api.exception.UsageErrorApiException;
import com.webs.api.exception.WebsApiException;
import com.webs.api.model.App;
import com.webs.api.model.Sidebar;
import com.webs.api.model.WebsID;


/**
 * @author Patrick Carroll
 */
public class WebsApiClient implements AppApi {
	private static final Log log = LogFactory.getLog(WebsApiClient.class);

	private static final int CONNECTION_TIMEOUT = 5000;

	private static final int SOCKET_TIMEOUT = 10000;

	private String apiPath = "http://127.0.0.1:8080/webs-api/";
	
	private ObjectMapper jsonMapper = new ObjectMapper();

	private String accessToken = null;


	public WebsApiClient() {
		// XXX can use a SerializationConfig object to configure the 
		// date format
	}

	public WebsApiClient(String accessToken) {
		super();
		setAccessToken(accessToken);
	}


	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getOAuthAuthorizationUrl() {
		return "https://api.members.webs.com/oauth/authorize";
	}


	/**
	 * Get all publicly available apps
	 */
	public List<App> getAllApps() {
		try {
			String data = httpRequest(new GetMethod(getApiPath() + "apps/"));
			return jsonMapper.readValue(data, new TypeReference<List<App>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	/**
	 * Get the app corresponding to the given appId
	 */
	public App getApp(final Long appId) {
		try {
			String data = httpRequest(new GetMethod(getApiPath() + "apps/" + appId + "/"));
			return jsonMapper.readValue(data, App.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to App: " + e);
			return null;
		}
	}

	/**
	 * Get all currently installed apps on the given site
	 */
	public List<App> getApps(final Long siteId) {
		try {
			String data = httpRequest(new GetMethod(getApiPath() + "sites/" + siteId + "/apps/"));
			return jsonMapper.readValue(data, new TypeReference<List<App>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	/**
	 * Install the given app on the given site
	 */
	public void installApp(final Long appId, final Long siteId) {
		PostMethod post = new PostMethod(apiPath + "site/" + siteId + "/apps/");
		NameValuePair[] data = {
			new NameValuePair("id", appId.toString()),
		};
		post.setRequestBody(data);

		httpRequest(post, HttpStatus.SC_CREATED);
	}

	/**
	 * Install the given app on the given site
	 */
	public void uninstallApp(final Long appId, final Long siteId) {
		httpRequest(new DeleteMethod(apiPath + "site/" + siteId + "/apps/" + appId));
	}


	protected String httpRequest(HttpMethod method) {
		return httpRequest(method, HttpStatus.SC_OK);
	}

	protected String httpRequest(HttpMethod method, int expectedStatus) {
		HttpConnectionManager connectionManager = new SimpleHttpConnectionManager(true);
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(CONNECTION_TIMEOUT);
		params.setSoTimeout(SOCKET_TIMEOUT);
		connectionManager.setParams(params);

		HttpClient client = new HttpClient(connectionManager);
		method.addRequestHeader("Accept", "application/json");

		if (accessToken != null) 
			method.addRequestHeader("Authorization", 
					"Token token=\"" + accessToken + "\"");

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != expectedStatus)
				throw new HttpApiException("" + statusCode);

			InputStream stream = method.getResponseBodyAsStream();

			return extractContent(stream);
		} catch (HttpException e) {
			log.fatal("HTTP error: " + e.getMessage());
		} catch (IOException e) {
			log.fatal("Connection error: " + e.getMessage());
		} finally {
			method.releaseConnection();
		}

		// shouldn't get here
		throw new HttpApiException("No content received from server");
	}

	// XXX it's stupid that this parses JSON but returns a String
	// representing the content of the message which has already been parsed
	protected String extractContent(InputStream json) throws IOException {
		JsonNode rootNode = jsonMapper.readValue(json, JsonNode.class);
		if (!rootNode.path("success").getBooleanValue()) 
			throw new UsageErrorApiException(rootNode.path("message").getValueAsText());

		return rootNode.path("content").toString();
	}
}
