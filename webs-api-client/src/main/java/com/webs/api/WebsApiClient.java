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
import com.webs.api.model.Site;
import com.webs.api.model.WebsID;


/**
 * Simple API client for use with the Webs.com REST API.  Using JSON as the
 * data format, this class is responsible for marshalling domain-specific
 * objects to/from JSON in communication with the API server.  Most methods
 * which modify data will need an OAuth access token passed through the
 * constructor.
 *  
 * @author Patrick Carroll
 */
public class WebsApiClient implements AppApi, MemberApi, SiteApi {
	private static final Log log = LogFactory.getLog(WebsApiClient.class);

	private static final int CONNECTION_TIMEOUT = 5000;

	private static final int SOCKET_TIMEOUT = 10000;

	private String apiPath = "http://api1/"; // XXX need DNS for this
	
	private ObjectMapper jsonMapper = new ObjectMapper();

	private String accessToken = null;


	/**
	 * Create an API client without an OAuth access token
	 */
	public WebsApiClient() {
		// XXX can use a SerializationConfig object to configure the 
		// date format
	}

	/**
	 * Create an API client with the given OAuth access token
	 *
	 * @param accessToken	The access token provided by the Webs.com platform
	 */
	public WebsApiClient(String accessToken) {
		super();
		setAccessToken(accessToken);
	}


	/**
	 * Set the location of the API server.  This location should be an
	 * HTTPS compatible URL (i.e., start with <tt>https://</tt>.
	 */
	public void setApiPath(String apiPath) {
		if (!apiPath.startsWith("https://"))
			log.warn("Attempting to use non-SSL API path.");

		if (!apiPath.endsWith("/"))
			apiPath = apiPath + "/";

		this.apiPath = apiPath;
	}

	/**
	 * Get the location of the API server where requests are made.
	 */
	public String getApiPath() {
		return apiPath;
	}

	/**
	 * Set the OAuth access token which will be sent to the Webs.com API
	 * server
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Get the OAuth access token that is actively being used.
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Get the URL used for requesting OAuth authorization
	 */
	public String getOAuthAuthorizationUrl() {
		return "https://api.members.webs.com/oauth/authorize";
	}


	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
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
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	public void installApp(final Long appId, final Long siteId) {
		PostMethod post = new PostMethod(apiPath + "sites/" + siteId + "/apps/");
		NameValuePair[] data = {
			new NameValuePair("id", appId.toString()),
		};
		post.setRequestBody(data);

		httpRequest(post, HttpStatus.SC_CREATED);
	}

	/**
	 * {@inheritDoc}
	 */
	public void uninstallApp(final Long appId, final Long siteId) {
		httpRequest(new DeleteMethod(apiPath + "sites/" + siteId + "/apps/" + appId));
	}



	public List<WebsID> getMembers(final Long siteId) {
		try {
			String data = httpRequest(new GetMethod(getApiPath() + "sites/" + siteId + "/members/"));
			return jsonMapper.readValue(data, new TypeReference<List<WebsID>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	public WebsID joinSite(final WebsID member, final Long siteId) {
		return null;
	}

	public WebsID getMember(final Long memberId, final Long siteId) {
		try {
			String data = httpRequest(new GetMethod(getApiPath() + "sites/" + siteId + "/members/" + memberId + "/"));
			return jsonMapper.readValue(data, WebsID.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
			return null;
		}
	}

	public void updateMember(final WebsID member, final Long siteId) {
		try {
			PutMethod put = new PutMethod(apiPath + "sites/" + siteId + "/members/" + member.getId() + "/");
			put.setRequestBody(jsonMapper.writeValueAsString(member));

			httpRequest(put, HttpStatus.SC_NO_CONTENT);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
		}
	}

	public void updateMemberStatus(final Long memberId, final Long siteId, final String status) {
		WebsID member = getMember(memberId, siteId);
		member.setStatus(status);
		updateMember(member, siteId);
	}

	public void clearMemberStatus(final Long memberId, final Long siteId) {
		updateMemberStatus(memberId, siteId, null);
	}

	public void leaveSite(final Long memberId, final Long siteId) {
		return;
	}

	public List<WebsID> getFriends(final Long memberId, final Long siteId) {
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	public Site getSite(final Long siteId) {
		return getSite(siteId.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public Site getSite(final String username) {
		try {
			String data = httpRequest(new GetMethod(getApiPath() + "sites/" + username));
			return jsonMapper.readValue(data, Site.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to Site: " + e);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateSite(final Site site) {
		String identifier;
		if (site.getId() != null) {
			identifier = site.getId().toString();
		} else if (site.getUsername() != null) {
			identifier = site.getUsername().toString();
		} else {
			log.fatal("updateSite requires either site.id or site.username to be set");
			return;
		}

		try {
			PutMethod put = new PutMethod(apiPath + "sites/" + identifier);
			put.setRequestBody(jsonMapper.writeValueAsString(site));
			httpRequest(put, HttpStatus.SC_NO_CONTENT);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
		}
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

	protected String extractContent(InputStream json) throws IOException {
		JsonNode rootNode = jsonMapper.readValue(json, JsonNode.class);
		if (rootNode.get("success") != null && !rootNode.path("success").getBooleanValue()) 
			throw new UsageErrorApiException(rootNode.path("message").getValueAsText());

		return rootNode.toString();
	}
}
