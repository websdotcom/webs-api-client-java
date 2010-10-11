package com.webs.api.http;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpMethod;

import com.webs.api.WebsApiModelMapper;
import com.webs.api.pagination.Page;


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

	public <T> T httpRequestMapper(HttpMethod method, int expectedStatus, WebsApiModelMapper<T> mapper); 

	public <T> List<T> httpRequestMapperToList(HttpMethod method, int expectedStatus, WebsApiModelMapper<T> mapper); 

	public <T> Page<T> httpRequestMapperToPage(HttpMethod method, int expectedStatus, WebsApiModelMapper<T> mapper); 

	public String extractContent(InputStream json) throws IOException; 
}
