package com.webs.api;

import java.util.List;

import com.webs.api.model.SiteSubscription;
import com.webs.api.model.WebsID;
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;


/**
 * Interface which specifies all app-related calls to the Webs API
 *
 * @author Patrick Carroll
 */
public interface MemberApi {
	/**
	 * Get a member's information for a particular site
	 *
	 * @param websIDId	The identifier of the member
	 * @param siteId	The identifier of the site
	 *
	 * @return The member that was found or null if it was not found
	 */
	public WebsID getMember(final WebsIDId websIDId, final SiteId siteId);

	/**
	 * Gets a list of all members on a given site
	 *
	 * @param siteId	The identifier of the site
	 *
	 * @return A list of all members of the site
	 */
	public List<WebsID> getMembers(final SiteId siteId);

	public WebsID joinSite(final WebsID member, final SiteId siteId);

	/**
	 * Update the member's information on the given site
	 *
	 * @param member	The member with information to be set.  This object
	 * 					must have either the id or emailAddress set.
	 * @param siteId	The siteId of the site on which the information
	 * 					will be updated
	 */
	public void updateMember(final WebsID member, final SiteId siteId);

	/**
	 * Update the member's status on the given site
	 *
	 * @param websIDId	The unique identifier for the member
	 * @param siteId	The siteId of the site on which the information
	 * 					will be updated
	 * @param status	The new status to be set
	 */
	public void updateMemberStatus(final WebsIDId websIDId, final SiteId siteId, final String status); 


	/**
	 * Clear a member's status on the given site
	 *
	 * @param websIDId	The unique identifier for the member
	 * @param siteId	The siteId of the site on which the information
	 * 					will be updated
	 */
	public void clearMemberStatus(final WebsIDId websIDId, final SiteId siteId); 

	public void leaveSite(final WebsIDId websIDId, final SiteId siteId);

	/**
	 * Get a list of all of a member's friends
	 *
	 * @param websIDId	The unique identifier for the member
	 * @param siteId	The siteId of the site 
	 *
	 * 	@return The list of all members who are friends with the given member
	 */
	public List<WebsID> getFriends(final WebsIDId websIDId, final SiteId siteId);

	// XXX request friendship

	// XXX get feeds

	// XXX profile tabs
	
	/**
	 * Get a site subscription
	 *
	 * @param websIDId	The ID of the member
	 * @param siteId	The id of the site to get the subscription from
	 */
	public SiteSubscription getSiteSubscription(final WebsIDId websIDId, final SiteId siteId);

	/**
	 * Get a list of sites the given WebsID is subscribed to
	 *
	 * @param websIDId	The ID of the member
	 *
	 * @return A list of all of the sites where the given WebsID is a member.
	 * If a valid OAuth token is passed with the appropriate permissions,
	 * private subscriptions will also be returned.
	 */
	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId);

	/**
	 * Get a list of sites the given WebsID is subscribed to and filter by
	 * permission
	 *
	 * @param websIDId		The ID of the member
	 * @param permission	Only subscriptions with this permission will be
	 * returned
	 *
	 * @return A list of all of the sites where the given WebsID is a member.
	 * If a valid OAuth token is passed with the appropriate permissions,
	 * private subscriptions will also be returned.
	 */
	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId, final String permission);
}
