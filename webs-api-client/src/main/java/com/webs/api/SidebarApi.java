package com.webs.api;

import java.util.List;

import com.webs.api.model.Sidebar;


/**
 * Interface which specifies all sidebar-related calls to the Webs API
 *
 * @author Patrick Carroll
 */
public interface SidebarApi {
	/**
	 * Get all sidebars for the given site
	 */
	public List<Sidebar> getSidebars(final Long siteId);

	/**
	 * Get all sidebars for the given site
	 */
	public List<Sidebar> getSidebars(final String username);

	/**
	 * Get the sidebar corresponding to the given sidebarId
	 */
	public Sidebar getSidebar(final Long sidebarId, final Long siteId);

	/**
	 * Get the sidebar corresponding to the given sidebarId
	 */
	public Sidebar getSidebar(final Long sidebarId, final String username);

	/**
	 * Install the given sidebar on the given site
	 */
	public void installSidebar(final Long sidebarId, final Long siteId);

	/**
	 * Install the given sidebar on the given site
	 */
	public void installSidebar(final Long sidebarId, final String username);

	/**
	 * Install the given app on the given site
	 */
	public void removeSidebar(final Long sidebarId, final Long siteId);

	/**
	 * Install the given app on the given site
	 */
	public void removeSidebar(final Long sidebarId, final String username);
}
