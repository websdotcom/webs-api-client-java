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
	public List<WebsID> getMembers(final SiteId siteId);

	public WebsID joinSite(final WebsID member, final SiteId siteId);

	public WebsID getMember(final WebsIDId websIDId, final SiteId siteId);

	public void updateMember(final WebsID member, final SiteId siteId);

	public void updateMemberStatus(final WebsIDId websIDId, final SiteId siteId, final String status); 

	public void clearMemberStatus(final WebsIDId websIDId, final SiteId siteId); 

	public void leaveSite(final WebsIDId websIDId, final SiteId siteId);

	public List<WebsID> getFriends(final WebsIDId websIDId, final SiteId siteId);

	// XXX request friendship

	// XXX get feeds

	// XXX profile tabs
	
	// XXX should take a query parameter to filter by permission level
	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId);
}
