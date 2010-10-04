package com.webs.api;

import static junit.framework.Assert.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webs.api.exception.UsageErrorApiException;
import com.webs.api.http.HttpApiClient;
import com.webs.api.model.Site;
import com.webs.api.model.id.SiteId;


/**
 * @author Patrick Carroll
 */
public class SiteApiTest {
	private static final String TEST_JSON_SITE = "{\"id\": 1, \"username\": \"patrick\", \"email_address\": \"patrick@webs.com\", \"premium\": false, \"url\": \"http://patrick.webs.com\", \"vertical\": \"business\", \"title\": \"My Site\", \"footer\": \"footer\", \"description\": \"Site Description\", \"sidebars_enabled\": true, \"keywords\": [\"asdf\", \"foo\"], \"advanced\": true}";

	private SiteApiImpl client;

	private HttpApiClient mockHttpApiClient;


	@Before
	public void setUp() {
		client = new SiteApiImpl();
		mockHttpApiClient = createMock(HttpApiClient.class);

		client.setHttpApiClient(mockHttpApiClient);
	}


	@Test
	public void getSite() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn(TEST_JSON_SITE);
		replay(mockHttpApiClient);

		Site site = client.getSite(new SiteId("patrick"));
		assertNotNull(site);
		assertEquals(new Long(1), site.getId());
		assertEquals("patrick", site.getUsername());
		assertEquals("patrick@webs.com", site.getEmailAddress());
		assertFalse(site.isPremium());
		assertEquals("http://patrick.webs.com", site.getUrl());
		assertEquals("business", site.getVertical());
		assertEquals("My Site", site.getTitle());
		assertEquals("footer", site.getFooter());
		assertEquals("Site Description", site.getDescription());
		assertTrue(site.isSidebarsEnabled());
		assertFalse(site.getKeywords().isEmpty());
		assertTrue(site.isAdvanced());

		verify(mockHttpApiClient);
	}

	@Test
	public void updateSite() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((PutMethod)notNull(), eq(HttpStatus.SC_NO_CONTENT))).andReturn(TEST_JSON_SITE);
		replay(mockHttpApiClient);

		Site site = new Site();
		site.setId(1L);

		client.updateSite(site);

		verify(mockHttpApiClient);
	}

	@Test(expected = UsageErrorApiException.class)
	public void updateSite_noId() {
		client.updateSite(new Site());
	}
}
