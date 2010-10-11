package com.webs.api;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import com.webs.api.http.AbstractHttpApiClientAware;
import com.webs.api.exception.UsageErrorApiException;
import com.webs.api.model.SiteSubscription;
import com.webs.api.model.WebsID;
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;


/**
 * @author Patrick Carroll
 */
public class MemberApiImpl extends AbstractHttpApiClientAware implements MemberApi {
	private WebsApiModelMapper<WebsID> websIDMapper = new WebsApiModelMapper<WebsID>(WebsID.class);

	private WebsApiModelMapper<SiteSubscription> siteSubscriptionMapper = new WebsApiModelMapper<SiteSubscription>(SiteSubscription.class);


	public MemberApiImpl() {
	}


	// XXX paginate
	public List<WebsID> getMembers(final SiteId siteId) {
		return httpApiClient.httpRequestMapperToList(
				new GetMethod(httpApiClient.getApiPath() + "sites/" 
					+ siteId.toString() + "/members/"),
				HttpStatus.SC_OK, websIDMapper);
	}

	public WebsID joinSite(final WebsID member, final SiteId siteId) {
		// XXX
		return null;
	}

	public WebsID getMember(final WebsIDId websIDId, final SiteId siteId) {
		return httpApiClient.httpRequestMapper(
				new GetMethod(httpApiClient.getApiPath() + "sites/" 
					+ siteId.toString() + "/members/" + websIDId.toString() 
					+ "/"), HttpStatus.SC_OK, websIDMapper);
	}

	public void updateMember(final WebsID member, final SiteId siteId) {
		PutMethod put = new PutMethod(httpApiClient.getApiPath() 
				+ "sites/" + siteId.toString() + "/members/" 
				+ member.getId() + "/");

		try {
			put.setRequestBody(jsonMapper.writeValueAsString(member));

		} catch (IOException e) {
			throw new UsageErrorApiException("Error converting object to JSON");
		}

		httpApiClient.httpRequest(put, HttpStatus.SC_NO_CONTENT);
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
		return httpApiClient.httpRequestMapper(
					new GetMethod(httpApiClient.getApiPath() + "websid/"
						+ websIDId.toString() + "/sites/" 
						+ siteId.toString() + "/"),
					HttpStatus.SC_OK, siteSubscriptionMapper);
	}

	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId) {
		return getSiteSubscriptions(websIDId, null);
	}

	// XXX paginate
	public List<SiteSubscription> getSiteSubscriptions(final WebsIDId websIDId, final String permission) {
		GetMethod get = new GetMethod(httpApiClient.getApiPath() 
				+ "websid/" + websIDId.toString() + "/sites/");

		if (permission != null)
			get.setQueryString(new NameValuePair[] { new NameValuePair("permission", permission), });

		return httpApiClient.httpRequestMapperToList(get, HttpStatus.SC_OK, 
				siteSubscriptionMapper);
	}
}
