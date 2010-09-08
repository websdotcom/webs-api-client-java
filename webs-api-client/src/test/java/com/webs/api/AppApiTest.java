package com.webs.api;

import java.util.List;

import static junit.framework.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webs.api.model.App;
import com.webs.api.model.id.AppId;
import com.webs.api.model.id.SiteId;


/**
 * @author Patrick Carroll
 */
public class AppApiTest {
	private Long TEST_APP_ID = 2L; // XXX

	private Long TEST_SITE_ID = 1L; // XXX

	private AppApi client;


	@Before
	public void setUp() {
		client = new WebsApiClient();
	}


	@Test
	public void getAllApps() {
		List<App> apps = client.getAllApps();
		assertNotNull(apps);
		assertFalse(apps.isEmpty());
	}

	@Test
	public void getApp() {
		App app = client.getApp(new AppId(TEST_APP_ID));
		assertNotNull(app);
		assertEquals("photos", app.getHandle());
	}

	@Test
	public void getApps() {
		List<App> apps = client.getApps(new SiteId(TEST_SITE_ID));
	}

	@Test
	public void installApp() {

	}

	@Test
	public void uninstallApp() {

	}
}
