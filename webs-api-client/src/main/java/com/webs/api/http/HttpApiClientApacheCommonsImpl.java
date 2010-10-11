package com.webs.api.http;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.webs.api.WebsApiModelMapper;
import com.webs.api.exception.HttpApiException;
import com.webs.api.exception.UsageErrorApiException;


/**
 * @author Patrick Carroll
 */
public class HttpApiClientApacheCommonsImpl implements HttpApiClient {
	private static final Log log = LogFactory.getLog(HttpApiClient.class);

	private static final int CONNECTION_TIMEOUT = 5000;

	private static final int SOCKET_TIMEOUT = 10000;

	private ObjectMapper jsonMapper = new ObjectMapper();

	private String apiPath = "https://api.webs.com/";

	private String accessToken = null;


	public HttpApiClientApacheCommonsImpl() {
	}

	public HttpApiClientApacheCommonsImpl(String apiPath) {
		setApiPath(apiPath);
	}


	@Override
	public String getApiPath() {
		return apiPath;
	}

	@Override
	public void setApiPath(String apiPath) {
		if (!apiPath.startsWith("https://"))
			log.warn("Attempting to use non-SSL API path.");

		if (!apiPath.endsWith("/"))
			apiPath = apiPath + "/";

		this.apiPath = apiPath;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	@Override
	public String httpRequest(HttpMethod method) {
		return httpRequest(method, HttpStatus.SC_OK);
	}

	@Override
	public String httpRequest(HttpMethod method, int expectedStatus) {
		HttpConnectionManager connectionManager = new SimpleHttpConnectionManager(true);
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(CONNECTION_TIMEOUT);
		params.setSoTimeout(SOCKET_TIMEOUT);
		connectionManager.setParams(params);

		HttpClient client = new HttpClient(connectionManager);
		method.addRequestHeader("Accept", "application/json");
		method.addRequestHeader("Content-Type", "application/json");

		if (accessToken != null) 
			method.addRequestHeader("Authorization", 
					"Token token=\"" + accessToken + "\"");

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != expectedStatus)
				throw new HttpApiException("" + statusCode);

			if (statusCode == HttpStatus.SC_NO_CONTENT)
				return null;

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

	@Override
	public <T> T httpRequestMapper(HttpMethod method, int expectedStatus, WebsApiModelMapper<T> mapper) {
		HttpConnectionManager connectionManager = new SimpleHttpConnectionManager(true);
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(CONNECTION_TIMEOUT);
		params.setSoTimeout(SOCKET_TIMEOUT);
		connectionManager.setParams(params);

		HttpClient client = new HttpClient(connectionManager);
		method.addRequestHeader("Accept", "application/json");
		method.addRequestHeader("Content-Type", "application/json");

		if (accessToken != null) 
			method.addRequestHeader("Authorization", 
					"Token token=\"" + accessToken + "\"");

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != expectedStatus)
				throw new HttpApiException("" + statusCode);

			if (statusCode == HttpStatus.SC_NO_CONTENT)
				return null;

			InputStream stream = method.getResponseBodyAsStream();

			String data = extractContent(stream);
			if (mapper != null) {
				// XXX call the Page one if it's a paged result
				try {
					return mapper.mapModel(data);
				} catch (IOException e) {
					log.fatal("Unable to map object: " + e);
					return null;
				}
			} else {
				return null;
			}
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
	
	@Override
	public String extractContent(InputStream json) throws IOException {
		JsonNode rootNode = jsonMapper.readValue(json, JsonNode.class);
		if (rootNode.get("success") != null && !rootNode.path("success").getBooleanValue()) 
			throw new UsageErrorApiException(rootNode.path("message").getValueAsText());

		return rootNode.toString();
	}
}
