package com.webs.api;

import java.util.List;

import com.webs.api.model.WebsID;
import com.webs.api.model.SiteSubscription;


/**
 * Interface which specifies all app-related calls to the Webs API
 *
 * @author Patrick Carroll
 */
public interface MemberApi {
	public List<WebsID> getMembers(final Long siteId);

	public List<WebsID> getMembers(final String username);

	public WebsID joinSite(final WebsID member, final Long siteId);

	public WebsID getMember(final Long memberId, final Long siteId);

	public void updateMember(final WebsID member, final Long siteId);

	public void updateMemberStatus(final Long memberId, final Long siteId, final String status); 

	public void clearMemberStatus(final Long memberId, final Long siteId); 

	public void leaveSite(final Long memberId, final Long siteId);

	public List<WebsID> getFriends(final Long memberId, final Long siteId);

	// XXX request friendship

	// XXX get feeds

	// XXX profile tabs
	
	// XXX should take a query parameter to filter by permission level
	public List<SiteSubscription> getSiteSubscriptions(final String emailAddress);

	public List<SiteSubscription> getSiteSubscriptions(final Long websId);
}
