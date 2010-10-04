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

import com.webs.api.exception.UsageErrorApiException;
import com.webs.api.http.AbstractHttpApiClientAware;
import com.webs.api.model.Site;
import com.webs.api.model.id.SiteId;


/**
 * @author Patrick Carroll
 */
public class SiteApiImpl extends AbstractHttpApiClientAware implements SiteApi {
	public SiteApiImpl() {
	}


	public Site getSite(final SiteId siteId) {
		try {
			String data = httpApiClient.httpRequest(
					new GetMethod(httpApiClient.getApiPath() + "sites/" 
						+ siteId.toString()));
			return jsonMapper.readValue(data, Site.class);
		} catch (IOException e) {
			log.fatal("Error converting JSON to Site: " + e);
			return null;
		}
	}

	public void updateSite(final Site site) {
		String identifier;
		if (site.getId() != null) 
			identifier = site.getId().toString();
		else if (site.getUsername() != null) 
			identifier = site.getUsername().toString();
		else 
			throw new UsageErrorApiException("updateSite requires either site.id or site.username to be set");

		try {
			PutMethod put = new PutMethod(httpApiClient.getApiPath() 
					+ "sites/" + identifier);
			put.setRequestBody(jsonMapper.writeValueAsString(site));
			httpApiClient.httpRequest(put, HttpStatus.SC_NO_CONTENT);
		} catch (IOException e) {
			log.fatal("Error converting JSON to WebsID: " + e);
		}
	}
}
