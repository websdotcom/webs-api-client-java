package com.webs.api.pagination;

import java.io.Serializable;
import java.util.List;


/**
 * @author Patrick Carroll
 */
public abstract class Page<T> implements Serializable {
	protected String baseUrl = "?";

	abstract public boolean isFirstPage();
	abstract public boolean isLastPage();
	abstract public boolean hasNextPage();
	abstract public boolean isNextPage();
	abstract public boolean hasPreviousPage();
	abstract public boolean isPreviousPage();

	abstract public List<T> getElements();
	abstract public int getPageNum();
	abstract public int getPageSize();

	abstract public int getNextPageNum();
	abstract public int getPreviousPageNum();
	abstract public int getLastPageNum();
	abstract public long getTotalSize();

	abstract public long getStartingIndex();
	abstract public long getEndingIndex();

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public static int calculateLastPageNum(long totalSize, int pageSize) {
		int lastPage = (int)Math.ceil((double)totalSize/pageSize);

		// if totalSize == 0, lastPage will be 0 when it should be 1
		if (lastPage == 0) lastPage = 1;

		return lastPage;
	}
}
