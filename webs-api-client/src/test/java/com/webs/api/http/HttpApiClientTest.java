package com.webs.api.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static junit.framework.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webs.api.exception.UsageErrorApiException;


/**
 * @author Patrick Carroll
 */
public class HttpApiClientTest {
	private HttpApiClient httpApiClient;

	@Before
	public void setUp() {
		httpApiClient = new HttpApiClientApacheCommonsImpl();
	}


	@Test
	public void setApiPath_noTrailingSlash() {
		httpApiClient.setApiPath("https://api.webs.com");
		assertEquals("https://api.webs.com/", httpApiClient.getApiPath());
	}

	@Test
	public void httpRequest() {
		// XXX is this even testable without allowing an outgoing http request?
	}

	@Test
	public void extractContent() throws IOException {
		String content = "{\"foo\":false}";
		assertEquals(content, httpApiClient.extractContent(new ByteArrayInputStream(content.getBytes())));
	}

	@Test(expected=UsageErrorApiException.class)
	public void extractContent_error() throws IOException {
		httpApiClient.extractContent(new ByteArrayInputStream("{\"success\": false, \"message\": \"foo\"}".getBytes()));
	}
}
