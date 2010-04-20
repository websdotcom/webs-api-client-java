package com.webs.api;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.webs.api.exception.HttpApiException;
import com.webs.api.exception.UsageErrorApiException;
import com.webs.api.exception.WebsApiException;
import com.webs.api.model.App;


/**
 * @author Patrick Carroll
 */
public class WebsApiClient implements AppApi {
	private static final Log log = LogFactory.getLog(WebsApiClient.class);

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
		try {
			String data = apiHttpGet(getApiPath() + "apps/");
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
			String data = apiHttpGet(getApiPath() + "apps/" + appId + "/");
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
			String data = apiHttpGet(getApiPath() + "sites/" + siteId + "/apps/");
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
	}

	/**
	 * Install the given app on the given site
	 */
	public void uninstallApp(final Long appId, final Long siteId) {
	}


	protected String apiHttpGet(String url) {
		HttpClient client = new HttpClient();

		GetMethod method = new GetMethod(url);
		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK)
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
