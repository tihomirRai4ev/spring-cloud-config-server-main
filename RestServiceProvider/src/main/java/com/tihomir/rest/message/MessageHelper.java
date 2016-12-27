package com.tihomir.rest.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.log4j.Logger;

import com.tihomir.rest.processor.RestProvider;

/**
 * This is the class which exposes the message logic.
 *
 * @author Tihomir Raychev
 *
 */
public class MessageHelper {

	static Logger log = Logger.getLogger(RestProvider.class.getCanonicalName());
	private HashMap<String, Object> headerProperties = new HashMap<String, Object>();
	private String payload;
	private Collection<Header> headers = new ArrayList<Header>();

	public Object getHeader(String key) {
		return headerProperties.get(key);
	}

	public Collection<Header> getHeaders() {
		return headers;
	}

	public void setSessionHeader(Collection<Header> sessionHeader) {
		if (sessionHeader != null && !sessionHeader.isEmpty()) {
			this.headers.addAll(sessionHeader);
		}
	}

	public <T> T getHeader(String key, Class<T> type) {
		if (headerProperties.get(key) == null) {
			return null;
		}
		return (T) headerProperties.get(key);

	}

	public void setHeaders(HashMap<String, Object> headerProperties) {

		headerProperties.putAll(headerProperties);
	}

	public void setHeader(String key, Object value) {
		headerProperties.put(key, value);
	}

	public boolean hasHeaders() {
		return headerProperties.isEmpty();
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String body) {
		this.payload = body;
	}

}
