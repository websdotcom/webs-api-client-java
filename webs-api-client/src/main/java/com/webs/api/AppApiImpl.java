package com.webs.api;

import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import com.webs.api.http.AbstractHttpApiClientAware;
import com.webs.api.model.App;
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;


/**
 * @author Patrick Carroll
 */
public class AppApiImpl extends AbstractHttpApiClientAware implements AppApi {
	private WebsApiModelMapper<App> appMapper = new WebsApiModelMapper<App>(App.class);


	public AppApiImpl() {
	}


	// XXX paginate
	public List<App> getAllApps() {
		return httpApiClient.httpRequestMapperToList(
					new GetMethod(httpApiClient.getApiPath() + "apps/"),
					HttpStatus.SC_OK, appMapper);
	}


	public App getApp(final AppId appId) {
		return httpApiClient.httpRequestMapper(
				new GetMethod(httpApiClient.getApiPath() + "apps/" 
					+ appId.toString() + "/"), 
				HttpStatus.SC_OK, appMapper);
	}


	// XXX paginate
	public List<App> getApps(final SiteId siteId) {
		return httpApiClient.httpRequestMapperToList(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ siteId.toString() + "/apps/"), 
					HttpStatus.SC_OK, appMapper);
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
