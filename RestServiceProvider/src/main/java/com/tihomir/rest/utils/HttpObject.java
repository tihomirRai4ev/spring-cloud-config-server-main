package com.tihomir.rest.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.log4j.Logger;

import com.tihomir.rest.processor.RestProvider;

/**
 * Http class handling http methods.
 *
 * @author Tihomir Raychev
 *
 */
public class HttpObject {
	static Logger log = Logger.getLogger(RestProvider.class.getCanonicalName());

	public HttpGet fetchHttpGet(String serviceURL, boolean encode) throws URISyntaxException {
		log.info("Entering fetchHttpGet(String serviceURL, boolean encode)");
		HttpGet requestGet = null;
		try {
			if (encode) {
				requestGet = new HttpGet(encode(serviceURL));
			} else {
				requestGet = new HttpGet(serviceURL);
			}
		} finally {
			log.info("Entering fetchHttpGet(String serviceURL, boolean encode)");
		}

		return requestGet;
	}

	public HttpPost fetchHttpPost(String serviceURL, boolean encode) throws URISyntaxException {
		HttpPost requestPost = null;
		try {
			if (encode) {
				requestPost = new HttpPost(encode(serviceURL));
			} else {
				requestPost = new HttpPost(serviceURL);
			}
		} finally {
			log.info("Entering fetchHttpGet(String serviceURL, boolean encode)");
		}

		return requestPost;
	}

	public HttpPut fetchHttpPut(String serviceURL) throws URISyntaxException {
		HttpPut requestPut = new HttpPut(encode(serviceURL));
		return requestPut;
	}

	public HttpDelete fetchHttpDelete(String serviceURL) throws URISyntaxException {
		HttpDelete requestDelete = new HttpDelete(encode(serviceURL));
		return requestDelete;
	}

	public HttpHead fetchHttpHead(String serviceURL) throws URISyntaxException {
		HttpHead requestHead = new HttpHead(encode(serviceURL));
		return requestHead;
	}

	private String encode(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String relativePath = uri.getPath() + (uri.getQuery() == null ? "" : ("?" + uri.getQuery()));
		relativePath = relativePath.replaceAll(Constants.SPACE, Constants.URL_ENCODED_SPACE)
				.replaceAll(Constants.SINGLE_QUOTATION_MARK, Constants.URL_ENCODED_SINGLE_QUOTATION_MARK);
		return relativePath;
	}
}
