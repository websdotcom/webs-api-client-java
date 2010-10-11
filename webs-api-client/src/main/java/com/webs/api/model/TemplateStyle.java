package com.webs.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @author Patrick Carroll
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TemplateStyle implements Serializable {
	private static long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private String thumbnail;

	private String swatch;

	private String color;

	@JsonProperty("requires_flash")
	private Boolean requiresFlash;

	@JsonProperty("supports_sidebar")
	private Boolean supportsSidebar;

	@JsonProperty("supports_logos")
	private Boolean supportsLogos;

	@JsonProperty("footer_code")
	private String footerCode;

	@JsonProperty("neck_code")
	private String neckCode;


	public TemplateStyle() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSwatch() {
		return swatch;
	}

	public void setSwatch(String swatch) {
		this.swatch = swatch;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isRequiresFlash() {
		return requiresFlash != null && requiresFlash;
	}

	public void setRequiresFlash(Boolean requiresFlash) {
		this.requiresFlash = requiresFlash;
	}

	public boolean isSupportsSidebar() {
		return supportsSidebar != null && supportsSidebar;
	}

	public void setSupportsSidebar(Boolean supportsSidebar) {
		this.supportsSidebar = supportsSidebar;
	}

	public boolean isSupportsLogos() {
		return supportsLogos != null && supportsLogos;
	}

	public void setSupportsLogos(Boolean supportsLogos) {
		this.supportsLogos = supportsLogos;
	}

	public String getFooterCode() {
		return footerCode;
	}

	public void setFooterCode(String footerCode) {
		this.footerCode = footerCode;
	}

	public String getNeckCode() {
		return neckCode;
	}

	public void setNeckCode(String neckCode) {
		this.neckCode = neckCode;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
}
