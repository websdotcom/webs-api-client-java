package com.webs.api.exception;

/**
 * @author Patrick Carroll
 */
public class WebsApiException extends RuntimeException {
	public WebsApiException() {
		super();
	}

	public WebsApiException(String message) {
		super(message);
	}
}
