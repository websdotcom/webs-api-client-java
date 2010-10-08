package com.webs.api.pagination;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import static org.junit.Assert.*;


/**
 * @author Patrick Carroll
 */
public class GenericPageTest {
	@Test
	public void testIsFirstPage() {
		Page<Integer> page = new GenericPage<Integer>(testData(10), 1, 2, 10);
		assertTrue(page.isFirstPage());

		page = new GenericPage<Integer>(testData(10), 2, 2, 10);
		assertFalse(page.isFirstPage());

		// test with an empty list
		page = new GenericPage<Integer>(testData(0), 1, 2, 0);
		assertTrue(page.isFirstPage());
	}

	@Test
	public void testIsLastPage() {
		Page<Integer> page = new GenericPage<Integer>(testData(10), 1, 2, 10);
		assertFalse(page.isLastPage());

		page = new GenericPage<Integer>(testData(10), 5, 2, 10);
		assertTrue(page.isLastPage());

		// test with an empty list
		page = new GenericPage<Integer>(testData(0), 1, 2, 0);
		assertTrue(page.isLastPage());
	}

	@Test
	public void testHasNextPage() {
		Page<Integer> page = new GenericPage<Integer>(testData(10), 1, 2, 10);
		assertTrue(page.hasNextPage());
		
		page = new GenericPage<Integer>(testData(10), 3, 2, 10);
		assertTrue(page.hasNextPage());

		page = new GenericPage<Integer>(testData(10), 5, 2, 10);
		assertFalse(page.hasNextPage());

		// test with an empty list
		page = new GenericPage<Integer>(testData(0), 1, 2, 0);
		assertFalse(page.hasNextPage());
	}

	@Test
	public void testHasPreviousPage() {
		Page<Integer> page = new GenericPage<Integer>(testData(10), 1, 2, 10);
		assertFalse(page.hasPreviousPage());
		
		page = new GenericPage<Integer>(testData(10), 3, 2, 10);
		assertTrue(page.hasPreviousPage());

		page = new GenericPage<Integer>(testData(10), 5, 2, 10);
		assertTrue(page.hasPreviousPage());

		// test with an empty list
		page = new GenericPage<Integer>(testData(0), 1, 2, 0);
		assertFalse(page.hasPreviousPage());
	}

	@Test
	public void testGetNextPageNum() {
		Page<Integer> page = new GenericPage<Integer>(testData(10), 1, 2, 10);
		assertEquals(2, page.getNextPageNum());
		
		page = new GenericPage<Integer>(testData(10), 3, 2, 10);
		assertEquals(4, page.getNextPageNum());

		page = new GenericPage<Integer>(testData(10), 5, 2, 10);
		assertEquals(5, page.getNextPageNum());

		// test with an empty list
		page = new GenericPage<Integer>(testData(0), 1, 2, 0);
		assertEquals(1, page.getNextPageNum());
	}

	@Test
	public void testGetPreviousPageNum() {
		Page<Integer> page = new GenericPage<Integer>(testData(10), 1, 2, 10);
		assertEquals(1, page.getPreviousPageNum());
		
		page = new GenericPage<Integer>(testData(10), 3, 2, 10);
		assertEquals(2, page.getPreviousPageNum());

		page = new GenericPage<Integer>(testData(10), 5, 2, 10);
		assertEquals(4, page.getPreviousPageNum());

		// test with an empty list
		page = new GenericPage<Integer>(testData(0), 1, 2, 0);
		assertEquals(1, page.getNextPageNum());
	}


	private List<Integer> testData(int max) {
		List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < max; i++)
			data.add(i);
		return data;
	}
}
