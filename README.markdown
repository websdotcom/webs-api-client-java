# webs-api-client-java: A Java-based client for the Webs.com REST API #

## Usage ##

### Anonymous access without an OAuth access token.

	import com.webs.api.WebsApiClient;

	...

	WebsApiClient websApi = new WebsApiClient();
	List<App> apps = websApi.getAllApps();

### Access using an OAuth access token as provided through the Webs.com platform via the *fw_sig_access_token* parameter. ###

	import com.webs.api.WebsApiClient;
	import com.webs.api.model.Site;
	import com.webs.api.model.id.SiteId;

	...

	WebsApiClient websApi = new WebsApiClient(accessToken);

	Site site = websApi.getSite(new SiteId("haroon"));

## Documentation ##
The Javadoc-generated documentation can be found at [http://websdotcom.github.com/webs-api-client-java/apidocs/](http://websdotcom.github.com/webs-api-client-java/apidocs/)

## Maintainers ##

* Patrick Carroll ([github.com/patrickwebs](http://github.com/patrickwebs))

## License ##

Apache License, Version 2.0
