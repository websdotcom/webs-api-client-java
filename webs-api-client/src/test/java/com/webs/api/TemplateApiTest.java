package com.webs.api;

import static junit.framework.Assert.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webs.api.http.HttpApiClient;
import com.webs.api.http.HttpApiClientApacheCommonsImpl;
import com.webs.api.model.Template;


/**
 * @author Patrick Carroll
 */
public class TemplateApiTest {
	private static final String TEST_JSON_TEMPLATE = "{\"id\": 1, \"name\": \"Template\", \"rating\": 5.0}";

	private TemplateApi client;

	private HttpApiClient mockHttpApiClient;


	@Before
	public void setUp() {
		client = new TemplateApiImpl();
		mockHttpApiClient = createMock(HttpApiClient.class);

		((TemplateApiImpl)client).setHttpApiClient(mockHttpApiClient);
	}


	@Test
	public void getTemplate() {
		//Template template = client.getTemplate(277L);
	}
}
