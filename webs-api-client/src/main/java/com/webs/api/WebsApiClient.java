package com.webs.api;

import java.util.List;

import com.webs.api.http.HttpApiClient;
import com.webs.api.http.HttpApiClientAware;
import com.webs.api.model.App;
import com.webs.api.model.Sidebar;
import com.webs.api.model.Site;
import com.webs.api.model.SiteSubscription;
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


	// app api methods
	public List<App> getAllApps() {
		return appApi.getAllApps();
	}

	public App getApp(final Long appId) {
		return appApi.getApp(appId);
	}

	public List<App> getApps(final Long siteId) {
		return appApi.getApps(siteId);
	}

	public void installApp(final Long appId, final Long siteId) {
		appApi.installApp(appId, siteId);
	}

	public void uninstallApp(final Long appId, final Long siteId) {
		appApi.uninstallApp(appId, siteId);
	}


	// member api methods
	public List<WebsID> getMembers(final Long siteId) {
		return getMembers(siteId.toString());
	}

	public List<WebsID> getMembers(final String username) {
		return memberApi.getMembers(username);
	}

	public WebsID joinSite(final WebsID member, final Long siteId) {
		return memberApi.joinSite(member, siteId);
	}

	public WebsID getMember(final Long memberId, final Long siteId) {
		return memberApi.getMember(memberId, siteId);
	}

	public void updateMember(final WebsID member, final Long siteId) {
		memberApi.updateMember(member, siteId);
	}

	public void updateMemberStatus(final Long memberId, final Long siteId, final String status) {
		memberApi.updateMemberStatus(memberId, siteId, status);
	}

	public void clearMemberStatus(final Long memberId, final Long siteId) {
		updateMemberStatus(memberId, siteId, null);
	}

	public void leaveSite(final Long memberId, final Long siteId) {
		memberApi.leaveSite(memberId, siteId);
	}

	public List<WebsID> getFriends(final Long memberId, final Long siteId) {
		return memberApi.getFriends(memberId, siteId);
	}

	public List<SiteSubscription> getSiteSubscriptions(final String emailAddress) {
		return memberApi.getSiteSubscriptions(emailAddress);
	}

	public List<SiteSubscription> getSiteSubscriptions(final Long websId) {
		return memberApi.getSiteSubscriptions(websId);
	}


	// site api methods
	public Site getSite(final Long siteId) {
		return siteApi.getSite(siteId);
	}

	public Site getSite(final String username) {
		return siteApi.getSite(username);
	}

	public void updateSite(final Site site) {
		siteApi.updateSite(site);
	}
}
