package com.webs.api;

import com.webs.api.model.Site;


/**
 * Interface of all site-related API calls.
 *
 * @author Patrick Carroll
 */
public interface SiteApi {
	/**
	 * Looks up a {@link Site} object given the unique ID of that site
	 *
	 * @param siteId	The unique ID of the site
	 *
	 * @return The site or null if not found
	 */
	public Site getSite(final Long siteId);

	/**
	 * Looks up a {@link Site} object given the username of that site.
	 *
	 * @param username	The username associated with the site 
	 *
	 * @return The site or null if not found
	 */
	public Site getSite(final String username);

	/**
	 * Update the given site object.  The supplied site object must have
	 * either the handle or id properties set
	 *
	 * @param site	a non-null object which has either the id or handle
	 * properties set
	 */
	public void updateSite(final Site site);
}
