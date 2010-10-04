package com.webs.api.http;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpMethod;


/**
 * @author Patrick Carroll
 */
public interface HttpApiClient {
	public String getApiPath(); 

	public void setApiPath(String apiPath); 

	public String getAccessToken(); 

	public void setAccessToken(String accessToken); 

	public String httpRequest(HttpMethod method); 

	public String httpRequest(HttpMethod method, int expectedStatus); 

	public String extractContent(InputStream json) throws IOException; 
}
