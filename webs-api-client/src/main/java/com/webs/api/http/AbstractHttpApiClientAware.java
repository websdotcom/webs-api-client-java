package com.webs.api.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Convenience class for any implementation class which would like to have
 * access to an HttpApiClient object.
 *
 * @author Patrick Carroll
 */
public class AbstractHttpApiClientAware implements HttpApiClientAware {
	protected static final Log log = LogFactory.getLog(AbstractHttpApiClientAware.class);

	protected HttpApiClient httpApiClient;

	protected ObjectMapper jsonMapper = new ObjectMapper();

	public void setHttpApiClient(HttpApiClient httpApiClient) {
		this.httpApiClient = httpApiClient;
	}
}
