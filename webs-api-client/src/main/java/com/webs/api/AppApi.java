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
	 */
	public List<App> getAllApps();

	/**
	 * Get the app corresponding to the given appId
	 *
	 * @param app	The unique identifier for this app.  Either an id 
	 * converted to a string, or an app handle (i.e., "photos", "calendar")
	 *
	 * @return The app that was found.  If none are found, returns null
	 *
	 */
	public App getApp(final AppId appId);

	/**
	 * Get all currently installed apps on the given site
	 *
	 * @param site	The unique identifier for this site.  Either an id 
	 * converted to a string, or a valid username
	 *
	 */
	public List<App> getApps(final SiteId siteId); 

	/**
	 * Install the given app on the given site
	 *
	 * @param app	The unique identifier for this app.  Either an id 
	 * converted to a string, or an app handle (i.e., "photos", "calendar")
	 *
	 * @param site	The unique identifier for this site.  Either an id 
	 * converted to a string, or a valid username
	 *
	 */
	public void installApp(final AppId appId, final SiteId siteId);

	/**
	 * Install the given app on the given site
	 *
	 * @param app	The unique identifier for this app.  Either an id 
	 * converted to a string, or an app handle (i.e., "photos", "calendar")
	 *
	 * @param site	The unique identifier for this site.  Either an id 
	 * converted to a string, or a valid username
	 *
	 */
	public void uninstallApp(final AppId appId, final SiteId siteId);
}
