package com.webs.api.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Generic object for holding the results of a query which may be restricted
 * to a smaller subset
 * 
 * @author Patrick Carroll
 */
public class GenericPage<T> extends Page<T> implements Serializable {
	private static final long serialVersionUID = -2371661668491354748L;

	private List<T> elements;
	private int pageNum, pageSize;
	private int lastPageNum; // the total number of pages available
	private long totalSize; // the total number of result elements available

	public GenericPage() {
		this.elements = new ArrayList<T>();
		this.pageNum = 1;
		this.pageSize = 0;
		this.totalSize = 0;
		this.lastPageNum = 1;
	}

	public GenericPage(List<T> elements, int pageNum, int pageSize, long totalSize) {
		this.elements = elements;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.lastPageNum = calculateLastPageNum(totalSize, pageSize);
	}

	@Override
	public long getStartingIndex() {
		return (totalSize <= 0 ? 0 : (pageNum - 1) * pageSize)+1; 
	}

	@Override
	public long getEndingIndex() {
		long endingIdx = getStartingIndex() - 1 + pageSize;
		return totalSize <= endingIdx ? totalSize : endingIdx;
	}

	@Override
	public boolean isFirstPage() {
		return (pageNum == 1);
	}

	@Override
	public boolean isLastPage() {
		return (lastPageNum == pageNum);
	}

	@Override
	public boolean hasNextPage() {
		return (pageNum < lastPageNum);
	}

	@Override
	public boolean isNextPage() {
		return hasNextPage();
	}

	@Override
	public boolean hasPreviousPage() {
		return (pageNum > 1 && pageNum <= lastPageNum);
	}

	@Override
	public boolean isPreviousPage() {
		return hasPreviousPage();
	}

	@Override
	public List<T> getElements() {
		return this.elements;
	}

	@Override
	public int getPageNum() {
		return pageNum;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getNextPageNum() {
		return (hasNextPage() ? pageNum + 1 : pageNum);
	}

	@Override
	public int getPreviousPageNum() {
		return (hasPreviousPage() ? pageNum - 1: pageNum);
	}

	@Override
	public int getLastPageNum() {
		return lastPageNum;
	}

	@Override
	public long getTotalSize() {
		return totalSize;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
