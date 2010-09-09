package com.webs.api.http;


/**
 * Interface to be used by any class which needs access to an HttpApiClient
 * object.  By abstracting out the HttpApiClient code, we're able to split
 * up the class hierarchy, increase code reuse and allow for unit tests.
 *
 * @see com.webs.api.http.HttpApiClient
 *
 * @author Patrick Carroll
 */
public interface HttpApiClientAware {
	/**
	 * Set the httpApiClient that will be used by the implementation
	 */
	public void setHttpApiClient(HttpApiClient httpClient);
}
