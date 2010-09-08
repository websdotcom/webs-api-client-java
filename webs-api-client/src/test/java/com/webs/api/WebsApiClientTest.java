package com.webs.api;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

import com.webs.api.model.Site;
import com.webs.api.model.SiteSubscription;
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;


/**
 * @author Patrick Carroll
 */
public class WebsApiClientTest {
	private WebsApiClient client;

	@Before
	public void setUp() {
		client = new WebsApiClient();
		client.setApiPath("http://127.0.0.1:8080/com-webs-api/");
	}


	@Test
	public void getSite() {
		Site site = client.getSite(new SiteId(31515461L));
		System.out.println("site=" + site);
	}

	@Test
	public void getSite_username() {
		Site site = client.getSite(new SiteId("podcast55"));
		System.out.println("site=" + site);
	}

	@Test
	public void getSiteSubscriptions() {
		List<SiteSubscription> subscriptions = client.getSiteSubscriptions(new WebsIDId("patrick@webs.com"));
		System.out.println("subscriptions= " + subscriptions);
	}
}
