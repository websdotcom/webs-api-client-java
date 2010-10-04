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
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;


/**
 * @author Patrick Carroll
 */
public class MemberApiImpl extends AbstractHttpApiClientAware implements MemberApi {
	public MemberApiImpl() {
	}


	public List<WebsID> getMembers(final SiteId siteId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ siteId.toString() + "/members/"));
			return jsonMapper.readValue(data, new TypeReference<List<WebsID>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to List<App>: " + e);
			return null;
		}
	}

	public WebsID joinSite(final WebsID member, final SiteId siteId) {
		// XXX
		return null;
	}

	public WebsID getMember(final WebsIDId websIDId, final SiteId siteId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ siteId.toString() + "/members/" 
						+ websIDId.toString() + "/"));
			return jsonMapper.readValue(data, WebsID.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
			return null;
		}
	}

	public void updateMember(final WebsID member, final SiteId siteId) {
		try {
			PutMethod put = new PutMethod(httpApiClient.getApiPath() 
					+ "sites/" + siteId.toString() + "/members/" 
					+ member.getId() + "/");
			put.setRequestBody(jsonMapper.writeValueAsString(member));

			httpApiClient.httpRequest(put, HttpStatus.SC_NO_CONTENT);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
		}
	}

	public void updateMemberStatus(final WebsIDId websIDId, final SiteId siteId, final String status) {
		WebsID member = getMember(websIDId, siteId);
		member.setStatus(status);
		updateMember(member, siteId);
	}

	public void clearMemberStatus(final WebsIDId websIDId, final SiteId siteId) {
		updateMemberStatus(websIDId, siteId, null);
	}

	public void leaveSite(final WebsIDId websIDId, final SiteId siteId) {
		// XXX
		return;
	}

	public List<WebsID> getFriends(final WebsIDId websIDId, final SiteId siteId) {
		// XXX
		return null;
	}

	public SiteSubscription getSiteSubscription(final WebsIDId websIDId, final SiteId siteId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "websid/"
						+ websIDId.toString() + "/sites/" 
						+ siteId.toString() + "/"));
			return jsonMapper.readValue(data, SiteSubscription.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
			return null;
		}
	}

	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId) {
		return getSiteSubscriptions(websIDId, null);
	}

	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId, final String permission) {
		try {
			GetMethod get = new GetMethod(httpApiClient.getApiPath() 
					+ "websid/" + websIDId.toString() + "/sites/");

			if (permission != null)
				get.setQueryString(new NameValuePair[] { new NameValuePair("permission", permission), });

			String data = httpApiClient.httpRequest(get);

			return jsonMapper.readValue(data, new TypeReference<List<SiteSubscription>>() { });
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
			return null;
		}
	}
}
