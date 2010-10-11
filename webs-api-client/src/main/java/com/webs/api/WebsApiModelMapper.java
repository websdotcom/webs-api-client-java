package com.webs.api;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.ParameterizedType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.webs.api.pagination.GenericPage;
import com.webs.api.pagination.Page;


/**
 * @author Patrick Carroll
 */
public class WebsApiModelMapper<T> {
	protected ObjectMapper jsonMapper = new ObjectMapper();

	protected Class<T> type;


	public WebsApiModelMapper() {
		// XXX
	}

	public WebsApiModelMapper(Class<T> type) {
		this.type = type;
	}


	public T mapModel(String data) throws IOException {
		return jsonMapper.readValue(data, type);
	}

	public List<T> mapModelToList(String data, int page, int pageSize, long totalSize) throws IOException {
		return jsonMapper.readValue(data, new TypeReference<List<T>>() { });
	}

	public Page<T> mapModelToPage(String data, int page, int pageSize, long totalSize) throws IOException {
		List<T> results = jsonMapper.readValue(data, new TypeReference<List<T>>() { });
		return new GenericPage<T>(results, page, pageSize, totalSize);
	}
}
