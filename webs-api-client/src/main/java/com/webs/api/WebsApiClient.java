package com.webs.api;

import java.util.List;

import com.webs.api.http.HttpApiClient;
import com.webs.api.http.HttpApiClientApacheCommonsImpl;
import com.webs.api.http.HttpApiClientAware;
import com.webs.api.model.App;
import com.webs.api.model.Sidebar;
import com.webs.api.model.Site;
import com.webs.api.model.SiteSubscription;
import com.webs.api.model.Template;
import com.webs.api.model.TemplateStyle;
import com.webs.api.model.WebsID;
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;
import com.webs.api.pagination.Page;


/**
 * Simple API client for use with the Webs.com REST API.  Using JSON as the
 * data format, this class is responsible for marshalling domain-specific
 * objects to/from JSON in communication with the API server.  Most methods
 * which modify data will need an OAuth access token passed through the
 * constructor.
 *  
 * @author Patrick Carroll
 */
public class WebsApiClient implements AppApi, MemberApi, SiteApi, TemplateApi {
	private AppApi appApi;

	private MemberApi memberApi;

	private SiteApi siteApi;

	private TemplateApi templateApi;
	
	private HttpApiClient httpApiClient;


	/**
	 * Create an API client without an OAuth access token
	 */
	public WebsApiClient() {
		this.httpApiClient = new HttpApiClientApacheCommonsImpl();

		this.appApi = new AppApiImpl();
		((HttpApiClientAware)appApi).setHttpApiClient(httpApiClient);

		this.memberApi = new MemberApiImpl();
		((HttpApiClientAware)memberApi).setHttpApiClient(httpApiClient);

		this.siteApi = new SiteApiImpl();
		((HttpApiClientAware)siteApi).setHttpApiClient(httpApiClient);

		this.templateApi = new TemplateApiImpl();
		((HttpApiClientAware)templateApi).setHttpApiClient(httpApiClient);
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


	// AppApi implementations
	public List<App> getAllApps() {
		return appApi.getAllApps();
	}

	public App getApp(final AppId appId) {
		return appApi.getApp(appId);
	}

	public List<App> getApps(final SiteId siteId) {
		return appApi.getApps(siteId);
	}

	public void installApp(final AppId appId, final SiteId siteId) {
		appApi.installApp(appId, siteId);
	}

	public void uninstallApp(final AppId appId, final SiteId siteId) {
		appApi.uninstallApp(appId, siteId);
	}


	// MemberApi implementations
	public WebsID getMember(final WebsIDId websIDId, final SiteId siteId) {
		return memberApi.getMember(websIDId, siteId);
	}

	public List<WebsID> getMembers(final SiteId siteId) {
		return getMembers(siteId);
	}

	public WebsID joinSite(final WebsID member, final SiteId siteId) {
		return memberApi.joinSite(member, siteId);
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

	public SiteSubscription getSiteSubscription(final WebsIDId websIDId, final SiteId siteId) {
		return memberApi.getSiteSubscription(websIDId, siteId);
	}

	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId) {
		return memberApi.getSiteSubscriptions(websIDId);
	}

	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId, final String permission) {
		return memberApi.getSiteSubscriptions(websIDId, permission);
	}
	

	// SiteApi implementations
	public Site getSite(final SiteId siteId) {
		return siteApi.getSite(siteId);
	}

	public void updateSite(final Site site) {
		siteApi.updateSite(site);
	}


	// TemplateApi implementation
	public Template getTemplate(final Long templateId) {
		return templateApi.getTemplate(templateId);
	}

	public TemplateStyle getTemplateStyle(final Long templateId, final Long templateStyleId) {
		return templateApi.getTemplateStyle(templateId, templateStyleId);
	}

	public List<Template> getTemplates() {
		return templateApi.getTemplates();
	}

	public Page<Template> getTemplates(int page, int pageSize) {
		return templateApi.getTemplates(page, pageSize);
	}

	public List<TemplateStyle> getTemplateStyles(final Long templateId) {
		return templateApi.getTemplateStyles(templateId);
	}

	public Page<TemplateStyle> getTemplateStyles(final Long templateId, int page, int pageSize) {
		return templateApi.getTemplateStyles(templateId, page, pageSize);
	}
}
