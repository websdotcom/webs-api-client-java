package com.webs.api;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jackson.type.TypeReference;

import com.webs.api.http.AbstractHttpApiClientAware;
import com.webs.api.model.App;


/**
 * @author Patrick Carroll
 */
public class AppApiImpl extends AbstractHttpApiClientAware implements AppApi {
	public AppApiImpl() {
	}


	public List<App> getAllApps() {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "apps/"));
			return jsonMapper.readValue(data, new TypeReference<List<App>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	public App getApp(final Long appId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "apps/" 
						+ appId + "/"));
			return jsonMapper.readValue(data, App.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to App: " + e);
			return null;
		}
	}

	public List<App> getApps(final Long siteId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ siteId + "/apps/"));
			return jsonMapper.readValue(data, new TypeReference<List<App>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	public void installApp(final Long appId, final Long siteId) {
		PostMethod post = new PostMethod(httpApiClient.getApiPath() 
				+ "sites/" + siteId + "/apps/");

		NameValuePair[] data = {
			new NameValuePair("id", appId.toString()),
		};

		post.setRequestBody(data);

		httpApiClient.httpRequest(post, HttpStatus.SC_CREATED);
	}

	public void uninstallApp(final Long appId, final Long siteId) {
		httpApiClient.httpRequest(
				new DeleteMethod(httpApiClient.getApiPath() + "sites/" + siteId + "/apps/" + appId));
	}
}
