package com.webs.api;

import java.util.List;

import static junit.framework.Assert.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webs.api.http.HttpApiClient;
import com.webs.api.model.SiteSubscription;
import com.webs.api.model.WebsID;
import com.webs.api.model.id.SiteId;
import com.webs.api.model.id.WebsIDId;


/**
 * @author Patrick Carroll
 */
public class MemberApiTest {
	private static final String TEST_JSON_MEMBER = "{\"id\": 1, \"email_address\": \"patrick@webs.com\", \"display_name\": \"Patrick Carroll\", \"gender\": \"male\", \"about_me\": \"I like stuff\", \"status\": \"Doing stuff\"}";

	private static final String TEST_JSON_SITE_SUBSCRIPTION = "{\"id\": 1, \"site_id\": 1, \"username\": \"patrick\", \"site_title\": \"Site title\", \"url\": \"http://patrick.webs.com/\", \"permission\": \"member\", \"subscription_private\": false}";

	private HttpApiClient mockHttpApiClient;

	private MemberApiImpl client;

	@Before
	public void setUp() {
		mockHttpApiClient = createMock(HttpApiClient.class);

		client = new MemberApiImpl();
		client.setHttpApiClient(mockHttpApiClient);
	}


	@Test
	public void getMembers() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn("[" + TEST_JSON_MEMBER + "]");
		replay(mockHttpApiClient);

		List<WebsID> members = client.getMembers(new SiteId("patrick"));

		assertNotNull(members);
		assertFalse(members.isEmpty());

		verify(mockHttpApiClient);
	}

	@Test 
	public void joinSite() {
		// XXX
	}

	// XXX test creation_date and last_login
	@Test
	public void getMember() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn(TEST_JSON_MEMBER);
		replay(mockHttpApiClient);

		WebsID member = client.getMember(new WebsIDId("patrick@webs.com"), new SiteId("patrick"));
		assertNotNull(member);
		assertEquals(new Long(1), member.getId());
		assertEquals("patrick@webs.com", member.getEmailAddress());
		assertEquals("Patrick Carroll", member.getDisplayName());
		assertEquals("male", member.getGender());
		assertEquals("I like stuff", member.getAboutMe());
		assertEquals("Doing stuff", member.getStatus());

		verify(mockHttpApiClient);
	}

	@Test
	public void updateMember() {
		WebsID member = new WebsID();
		member.setId(1L);
		member.setEmailAddress("patrick@webs.com");

		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((PutMethod)notNull(), eq(HttpStatus.SC_NO_CONTENT))).andReturn(TEST_JSON_MEMBER);
		replay(mockHttpApiClient);

		client.updateMember(member, new SiteId("patrick"));

		verify(mockHttpApiClient);
	}

	@Test
	public void updateMemberStatus() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn(TEST_JSON_MEMBER);
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((PutMethod)notNull(), eq(HttpStatus.SC_NO_CONTENT))).andReturn(TEST_JSON_MEMBER);
		replay(mockHttpApiClient);

		client.updateMemberStatus(new WebsIDId("patrick@webs.com"), new SiteId("patrick"), "status");

		verify(mockHttpApiClient);
	}

	@Test
	public void clearMemberStatus() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn(TEST_JSON_MEMBER);
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((PutMethod)notNull(), eq(HttpStatus.SC_NO_CONTENT))).andReturn(TEST_JSON_MEMBER);
		replay(mockHttpApiClient);

		client.clearMemberStatus(new WebsIDId("patrick@webs.com"), new SiteId("patrick"));

		verify(mockHttpApiClient);
	}

	@Test
	public void leaveSite() {
		// XXX
	}

	@Test
	public void getFriends() {
		// XXX
	}

	@Test
	public void getSiteSubscription() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn(TEST_JSON_SITE_SUBSCRIPTION);
		replay(mockHttpApiClient);

		// XXX test creation_date and last_login also
		SiteSubscription sub = client.getSiteSubscription(new WebsIDId("patrick@webs.com"), new SiteId("patrick"));
		assertNotNull(sub);
		assertEquals(new Long(1), sub.getId());
		assertEquals(new Long(1), sub.getSiteId());
		assertEquals("patrick", sub.getUsername());
		assertEquals("Site title", sub.getSiteTitle());
		assertEquals("http://patrick.webs.com/", sub.getUrl());
		assertEquals("member", sub.getPermission());
		assertFalse(sub.isSubscriptionPrivate());

		verify(mockHttpApiClient);
	}

	@Test
	public void getSiteSubscriptions() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn("[" + TEST_JSON_SITE_SUBSCRIPTION + "]");
		replay(mockHttpApiClient);

		List<SiteSubscription> subs = client.getSiteSubscriptions(new WebsIDId("patrick@webs.com"));
		assertNotNull(subs);
		assertFalse(subs.isEmpty());

		verify(mockHttpApiClient);
	}

	@Test
	public void getSiteSubscriptions_permission() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn("[" + TEST_JSON_SITE_SUBSCRIPTION + "]");
		replay(mockHttpApiClient);

		// XXX verify the permission somehow
		List<SiteSubscription> subs = client.getSiteSubscriptions(new WebsIDId("patrick@webs.com"), "member");
		assertNotNull(subs);
		assertFalse(subs.isEmpty());

		verify(mockHttpApiClient);
	}
}
