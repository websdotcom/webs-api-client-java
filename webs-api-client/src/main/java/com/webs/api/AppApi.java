package com.webs.api;

import java.util.List;

import com.webs.api.model.App;
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;


/**
 * Interface which specifies all app-related calls to the Webs API
 *
 * @author Patrick Carroll
 */
public interface AppApi {
	/**
	 * Get all publicly available apps
	 *
	 * @return	A list of all publicly available apps on Webs.com
	 */
	public List<App> getAllApps();

	/**
	 * Get the app corresponding to the given appId
	 *
	 * @param appId	The id of the corresponding app
	 *
	 * @return The app that was found or null if none are found
	 */
	public App getApp(final AppId appId);

	/**
	 * Get all publicly installed apps on the site corresponding to siteId
	 *
	 * @param siteId	The unique id corresponding to the site
	 *
	 * @return A list of all publicly available apps
	 */
	public List<App> getApps(final SiteId siteId); 

	/**
	 * Install the given app on a site.  This method requires a valid OAuth 
	 * token with site owner permissions
	 *
	 * @param appId		The unique id corresponding to the app to be 
	 * 					installed
	 * @param siteId	The unique id corresponding to the site
	 */
	public void installApp(final AppId appId, final SiteId siteId);

	/**
	 * Uninstall the given app from a site.  This method requires a valid 
	 * OAuth token with site owner permissions
	 *
	 * @param appId		The unique id corresponding to the app to be 
	 * 					uninstalled
	 * @param siteId	The unique id corresponding to the site
	 */
	public void uninstallApp(final AppId appId, final SiteId siteId);
}
