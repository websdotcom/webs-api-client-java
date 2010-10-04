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
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;


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

	public App getApp(final AppId appId) {
		String app = appId.toString();

		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "apps/" 
						+ app + "/"));
			return jsonMapper.readValue(data, App.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to App: " + e);
			return null;
		}
	}

	public List<App> getApps(final SiteId siteId) {
		String site = siteId.toString();

		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ site + "/apps/"));
			return jsonMapper.readValue(data, new TypeReference<List<App>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	public void installApp(final AppId appId, final SiteId siteId) {
		String app = appId.toString();

		PostMethod post = new PostMethod(httpApiClient.getApiPath() 
				+ "sites/" + siteId.toString() + "/apps/");

		String key = "id";

		try {
			Long.parseLong(app);
		} catch (NumberFormatException e) {
			key = "handle";
		}

		NameValuePair[] data = { new NameValuePair(key, app), };

		post.setRequestBody(data);

		httpApiClient.httpRequest(post, HttpStatus.SC_CREATED);
	}

	public void uninstallApp(final AppId appId, final SiteId siteId) {
		httpApiClient.httpRequest(
				new DeleteMethod(httpApiClient.getApiPath() + "sites/" 
					+ siteId.toString() + "/apps/" + appId.toString()));
	}
}
