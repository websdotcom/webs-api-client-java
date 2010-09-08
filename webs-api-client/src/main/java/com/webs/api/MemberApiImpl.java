package com.webs.api;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jackson.type.TypeReference;

import com.webs.api.http.AbstractHttpApiClientAware;
import com.webs.api.model.SiteSubscription;
import com.webs.api.model.WebsID;


/**
 * @author Patrick Carroll
 */
public class MemberApiImpl extends AbstractHttpApiClientAware implements MemberApi {
	public MemberApiImpl() {
	}


	public List<WebsID> getMembers(final Long siteId) {
		return getMembers(siteId.toString());
	}

	public List<WebsID> getMembers(final String username) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ username + "/members/"));
			return jsonMapper.readValue(data, new TypeReference<List<WebsID>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	public WebsID joinSite(final WebsID member, final Long siteId) {
		return null;
	}

	public WebsID getMember(final Long memberId, final Long siteId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ siteId + "/members/" + memberId + "/"));
			return jsonMapper.readValue(data, WebsID.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
			return null;
		}
	}

	public void updateMember(final WebsID member, final Long siteId) {
		try {
			PutMethod put = new PutMethod(httpApiClient.getApiPath() 
					+ "sites/" + siteId + "/members/" + member.getId() + "/");
			put.setRequestBody(jsonMapper.writeValueAsString(member));

			httpApiClient.httpRequest(put, HttpStatus.SC_NO_CONTENT);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
		}
	}

	public void updateMemberStatus(final Long memberId, final Long siteId, final String status) {
		WebsID member = getMember(memberId, siteId);
		member.setStatus(status);
		updateMember(member, siteId);
	}

	public void clearMemberStatus(final Long memberId, final Long siteId) {
		updateMemberStatus(memberId, siteId, null);
	}

	public void leaveSite(final Long memberId, final Long siteId) {
		// XXX
		return;
	}

	public List<WebsID> getFriends(final Long memberId, final Long siteId) {
		// XXX
		return null;
	}

	public List<SiteSubscription> getSiteSubscriptions(final String emailAddress) {
		// XXX
		return null;
	}

	public List<SiteSubscription> getSiteSubscriptions(final Long websId) {
		// XXX
		return null;
	}
}
