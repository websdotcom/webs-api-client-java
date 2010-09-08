package com.webs.api.http;

/**
 * Interface to be used by any class which needs access to an HttpApiClient
 * object.  By abstracting out the HttpApiClient code, we're able to split
 * up the class hierarchy, increase code reuse and allow for unit tests.
 *
 * {@see HttpApiClient}
 *
 * @author Patrick Carroll
 */
public interface HttpApiClientAware {
	public void setHttpApiClient(HttpApiClient httpClient);
}
