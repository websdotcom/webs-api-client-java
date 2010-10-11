package com.webs.api;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.webs.api.http.AbstractHttpApiClientAware;
import com.webs.api.model.Template;
import com.webs.api.model.TemplateStyle;
import com.webs.api.pagination.Page;


/**
 * @author Patrick Carroll
 */
public class TemplateApiImpl extends AbstractHttpApiClientAware implements TemplateApi {
	private WebsApiModelMapper<Template> templateMapper = new WebsApiModelMapper<Template>(Template.class);

	private WebsApiModelMapper<TemplateStyle> templateStyleMapper = new WebsApiModelMapper<TemplateStyle>(TemplateStyle.class);


	public TemplateApiImpl() {
	}


	public Template getTemplate(final Long templateId) {
		Template template = httpApiClient.httpRequestMapper(
				new GetMethod(httpApiClient.getApiPath() + "templates/" 
					+ templateId),
				HttpStatus.SC_OK, templateMapper);
		
		template.setStyles(getTemplateStyles(templateId));

		return template;
	}

	public TemplateStyle getTemplateStyle(final Long templateId, final Long templateStyleId) {
		return httpApiClient.httpRequestMapper(
				new GetMethod(httpApiClient.getApiPath() + "templates/" 
					+ templateId + "/styles/" + templateStyleId),
				HttpStatus.SC_OK, templateStyleMapper);
	}

	public List<Template> getTemplates() {
		return httpApiClient.httpRequestMapperToList(
					new GetMethod(httpApiClient.getApiPath() + "templates/"),
					HttpStatus.SC_OK, templateMapper);
	}

	public Page<Template> getTemplates(int page, int pageSize) {
		return null; // XXX
	}

	public List<TemplateStyle> getTemplateStyles(final Long templateId) {
		return httpApiClient.httpRequestMapperToList(
				new GetMethod(httpApiClient.getApiPath() + "templates/"
					+ templateId.toString() + "/styles/"), HttpStatus.SC_OK,
				templateStyleMapper);
	}

	public Page<TemplateStyle> getTemplateStyles(final Long templateId, int page, int pageSize) {
		return null;

	}
}

