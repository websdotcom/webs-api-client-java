package com.webs.api;

import java.util.List;

import com.webs.api.http.HttpApiClient;
import com.webs.api.http.HttpApiClientAware;
import com.webs.api.model.App;
import com.webs.api.model.Sidebar;
import com.webs.api.model.Site;
import com.webs.api.model.SiteSubscription;
import com.webs.api.model.WebsID;
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;


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
	private AppApi appApi;

	private MemberApi memberApi;

	private SiteApi siteApi;
	
	private HttpApiClient httpApiClient;


	/**
	 * Create an API client without an OAuth access token
	 */
	public WebsApiClient() {
		this.httpApiClient = new HttpApiClient();

		this.appApi = new AppApiImpl();
		((HttpApiClientAware)appApi).setHttpApiClient(httpApiClient);

		this.memberApi = new MemberApiImpl();
		this.siteApi = new SiteApiImpl();
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
		httpApiClient.setApiPath(apiPath);
	}

	/**
	 * Get the location of the API server where requests are made.
	 */
	public String getApiPath() {
		return httpApiClient.getApiPath();
	}

	/**
	 * Set the OAuth access token which will be sent to the Webs.com API
	 * server
	 */
	public void setAccessToken(String accessToken) {
		httpApiClient.setAccessToken(accessToken);
	}

	/**
	 * Get the OAuth access token that is actively being used.
	 */
	public String getAccessToken() {
		return httpApiClient.getAccessToken();
	}

	/**
	 * Get the URL used for requesting OAuth authorization
	 */
	public String getOAuthAuthorizationUrl() {
		return "https://api.members.webs.com/oauth/authorize";
	}


	/**
	 * Get all publicly available apps
	 *
	 * @return	A list of all publicly available apps on Webs.com
	 */
	public List<App> getAllApps() {
		return appApi.getAllApps();
	}

	/**
	 * Get the app corresponding to the given appId
	 *
	 * @param appId	The id of the corresponding app
	 *
	 * @return The app that was found or null if none are found
	 */
	public App getApp(final AppId appId) {
		return appApi.getApp(appId);
	}

	/**
	 * Get all publicly installed apps on the site corresponding to siteId
	 *
	 * @param siteId	The unique id corresponding to the site
	 *
	 * @return A list of all publicly available apps
	 */
	public List<App> getApps(final SiteId siteId) {
		return appApi.getApps(siteId);
	}

	public void installApp(final AppId appId, final SiteId siteId) {
		appApi.installApp(appId, siteId);
	}

	public void uninstallApp(final AppId appId, final SiteId siteId) {
		appApi.uninstallApp(appId, siteId);
	}


	// member api methods
	public List<WebsID> getMembers(final SiteId siteId) {
		return getMembers(siteId);
	}

	public WebsID joinSite(final WebsID member, final SiteId siteId) {
		return memberApi.joinSite(member, siteId);
	}

	public WebsID getMember(final WebsIDId websIDId, final SiteId siteId) {
		return memberApi.getMember(websIDId, siteId);
	}

	public void updateMember(final WebsID member, final SiteId siteId) {
		memberApi.updateMember(member, siteId);
	}

	public void updateMemberStatus(final WebsIDId websIDId, final SiteId siteId, final String status) {
		memberApi.updateMemberStatus(websIDId, siteId, status);
	}

	public void clearMemberStatus(final WebsIDId websIDId, final SiteId siteId) {
		updateMemberStatus(websIDId, siteId, null);
	}

	public void leaveSite(final WebsIDId websIDId, final SiteId siteId) {
		memberApi.leaveSite(websIDId, siteId);
	}

	public List<WebsID> getFriends(final WebsIDId websIDId, final SiteId siteId) {
		return memberApi.getFriends(websIDId, siteId);
	}

	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId) {
		return memberApi.getSiteSubscriptions(websIDId);
	}


	// site api methods
	public Site getSite(final SiteId siteId) {
		return siteApi.getSite(siteId);
	}

	public void updateSite(final Site site) {
		siteApi.updateSite(site);
	}
}
