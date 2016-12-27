package com.tihomir.rest.utils;

/**
 * Helper class providing constants values.
 * @author Tihomir Raychev
 *
 */
public class Constants {

	public static final char SINGLE_QUOTATION = '\'';
	public static final char OPENING_BRACE = '(';
	public static final char CLOSING_BRACE = ')';
	public static final String URL_ENCODED_SPACE = "%20";
	public static final String SINGLE_QUOTATION_MARK = "'";
	public static final String URL_ENCODED_SINGLE_QUOTATION_MARK = "%27";
	public static final String AND = "&";
	public static final String AND_TOKEN = "\\&";
	public static final String QUESTION_MARK = "?";
	public static final String QUESTION_MARK_TOKEN = "\\?";
	public static final String CLOSING_BRACKET = "}";
	public static final String OPENING_BRACKET = "{";
	public static final String SPACE = " ";

	public static final String ACCEPT = "ACCEPT";
	public static final String ATOM = "atom";
	public static final String JSON = "json";
	public static final String CONTENTTYPE_ATOM_XML = "application/atom+xml; charset=utf-8";
	public static final String CONTENTTYPE_XML = "application/xml; charset=utf-8";
	public static final String CONTENTTYPE_JSON = "application/json; charset=utf-8";
	public static final String CONTENTTYPE_XML_WITHOUT_ENCODING = "application/xml";
	public static final String CONTENTTYPE_JSON_WITHOUT_ENCODING = "application/json";
	public static final String CONTENTTYPE_ATOM_XML_WITHOUT_ENCODING = "application/atom+xml";
	public static final String CONTENTTYPE_ENCODING_NONE = "none";

	public static interface EncoderHelper {
		public static final String CONTENTTYPE_ENCODING_UTF_8 = "utf-8";
	}

	public static interface HttpHelper {

		public static final String CONTENT_TYPE = "Content-Type";
		public static final String BATCH_CONTENT_TYPE_VALUE = "multipart/mixed;boundary=batch";
		public static final String DISABLE_CSRF_HEADER = "X-Requested-With";
		public static final String DISABLE_CSRF_HEADER_VALUE = "XMLHttpRequest";
		public static final String ACCEPT_HEADER = "Accept";
		public static final String AUTHORIZATION_HEADER = "Authorization";
		public static final String BASIC_AUTH_HEADER = "Basic ";
		public static final String HTTP_CONSTANT = "http://";
		public static final String HTTPS_CONSTANT = "https://";
		public static final String STATUS_CODE = "statusCode";
		public static final String STATUS_INFO = "statusInfo";
		public static final String CONTENT_ID = "contentId";
		public static final String HEADERS = "headers";
		public static final String BODY = "body";
		public static final String URI = "uri";
	}

	public static final String CLOSING_TAG = "}";
	public static final String OPEN_TAG = "{";
	public static final String RESULT_TAG = " \"d\" :";
	public static final String OPEN_JSONARRAY_TAG = "[";
	public static final String CLOSING_JSONARRAY_TAG = "]";
}
