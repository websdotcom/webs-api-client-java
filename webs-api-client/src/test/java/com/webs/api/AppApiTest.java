package com.webs.api;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webs.api.WebsApiModelMapper;
import com.webs.api.http.HttpApiClient;
import com.webs.api.model.App;
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;


/**
 * @author Patrick Carroll
 */
public class AppApiTest {
	private static final String TEST_JSON_APP = "{\"id\": 1, \"handle\": \"photos\", \"name\": \"Photos App\", \"description\": \"Photos app\", \"category\": \"pictures\", \"developer_name\": \"Patrick Carroll\", \"developer_url\": \"http://webs.com\"}";

	private AppApi client;

	private HttpApiClient mockHttpApiClient;


	@Before
	public void setUp() {
		client = new AppApiImpl();
		mockHttpApiClient = createMock(HttpApiClient.class);

		((AppApiImpl)client).setHttpApiClient(mockHttpApiClient);
	}


	@Test
	public void getAllApps() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequestMapperToList((GetMethod)notNull(), (Integer)notNull(), (WebsApiModelMapper)notNull())).andReturn(new ArrayList<App>());
		replay(mockHttpApiClient);

		List<App> apps = client.getAllApps();
		assertNotNull(apps);
		assertTrue(apps.isEmpty());

		verify(mockHttpApiClient);
	}

	@Test
	public void getApp() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn(TEST_JSON_APP);
		replay(mockHttpApiClient);

		App app = client.getApp(new AppId("photos"));
		assertNotNull(app);
		assertEquals(new Long(1), app.getId());
		assertEquals("photos", app.getHandle());
		assertEquals("Photos App", app.getName());
		assertEquals("Photos app", app.getDescription());
		assertEquals("pictures", app.getCategory());
		assertEquals("Patrick Carroll", app.getDeveloperName());
		assertEquals("http://webs.com", app.getDeveloperUrl());
		// XXX test release_date

		verify(mockHttpApiClient);
	}

	@Test
	public void getApps() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((GetMethod)notNull())).andReturn("[" + TEST_JSON_APP + "]");
		replay(mockHttpApiClient);

		List<App> apps = client.getApps(new SiteId("patrick"));
		assertNotNull(apps);
		assertFalse(apps.isEmpty());

		verify(mockHttpApiClient);
	}

	@Test
	public void installApp() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		// XXX would be nice to be able to inspect the PostMethod object
		expect(mockHttpApiClient.httpRequest((PostMethod)notNull(), eq(HttpStatus.SC_CREATED))).andReturn(null);
		replay(mockHttpApiClient);

		client.installApp(new AppId("photos"), new SiteId("patrick"));

		verify(mockHttpApiClient);
	}

	@Test
	public void uninstallApp() {
		expect(mockHttpApiClient.getApiPath()).andReturn("https://api.webs.com/");
		expect(mockHttpApiClient.httpRequest((DeleteMethod)notNull())).andReturn(null);
		replay(mockHttpApiClient);

		client.uninstallApp(new AppId("photos"), new SiteId("patrick"));

		verify(mockHttpApiClient);
	}
}
