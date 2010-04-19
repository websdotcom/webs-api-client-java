package com.webs.api;

import java.util.List;

import com.webs.api.model.App;


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
	 */
	public App getApp(final Long appId);

	/**
	 * Get all currently installed apps on the given site
	 */
	public List<App> getApps(final Long siteId); 

	/**
	 * Install the given app on the given site
	 */
	public void installApp(final Long appId, final Long siteId);

	/**
	 * Install the given app on the given site
	 */
	public void uninstallApp(final Long appId, final Long siteId);
}
