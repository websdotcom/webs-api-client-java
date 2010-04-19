package com.webs.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;


/**
 * @author Patrick Carroll
 */
public class WebsApiClientTest {
	private WebsApiClient client;

	@Before
	public void setUp() {
		client = new WebsApiClient();
	}


	@Test
	public void testApp() {
		assertTrue(true);
	}
}
