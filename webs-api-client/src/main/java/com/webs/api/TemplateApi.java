package com.webs.api;

import java.util.List;

import com.webs.api.model.Template;
import com.webs.api.model.TemplateStyle;
import com.webs.api.pagination.Page;


/**
 * @author Patrick Carroll
 */
public interface TemplateApi {
	public Template getTemplate(final Long templateId);

	public TemplateStyle getTemplateStyle(final Long templateId, final Long templateStyleId);

	public List<Template> getTemplates();

	public Page<Template> getTemplates(int page, int pageSize);

	public List<TemplateStyle> getTemplateStyles(final Long templateId);

	public Page<TemplateStyle> getTemplateStyles(final Long templateId, int page, int pageSize);
}
