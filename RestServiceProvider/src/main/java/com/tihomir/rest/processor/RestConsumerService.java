package com.tihomir.rest.processor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.tihomir.rest.utils.Constants;
import com.tihomir.rest.utils.HttpObject;

/**
 * Consume Rest Services. TODO: Log entering/exiting should be done via Spring
 * aop. Some methods are also hardcoded need to make them configurable (pass service as dependency)
 *
 * @author Tihomir Raychev
 *
 */
public class RestConsumerService {

	static Logger log = Logger.getLogger(RestProvider.class.getCanonicalName());
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
	private static HttpClient httpClient;
	private HttpObject httpObject = new HttpObject();

	/**
	 * TODO long polling method for obtaining the changes from certain service
	 */
	ScheduledFuture<Object> scheduledFuture = scheduledExecutorService.schedule(new Callable<Object>() {
		public Object call() throws Exception {
			System.out.println("Executed!");
			return "Called!";
		}
	}, 5, TimeUnit.SECONDS);

	public String consumeServiceHelper(String uri, boolean encode) throws URISyntaxException {
		log.info("Entering consumeServiceHelper(String uri)");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpGet get = httpObject.fetchHttpGet(uri, encode);
			get.setHeader(Constants.HttpHelper.CONTENT_TYPE, Constants.CONTENTTYPE_JSON_WITHOUT_ENCODING);
			get.setHeader(Constants.ACCEPT, Constants.CONTENTTYPE_JSON);
			responseBody = httpClient.execute(get, responseHandler);
		} catch (ClientProtocolException e) {
			log.error(e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally {
			log.info("Exiting consumeServiceHelper(String uri)");
		}
		return responseBody;
	}

	/**
	 * Consume rest service. This method is registry for the service and it is
	 * currently hardcoded. TODO Make it configurable.
	 *
	 * @return String
	 */
	public String consumeRestService(/**TODO pass uri as parameter and remove hardcoded value..**/) {
		log.info("Entering consumeRestService()");
		String output = null;
		String uri = null;
		// set encode to true only for non-english
		// resource paths. TODO make it configurable
		// from resource path of the request
		// example:
		// http://host:port/resource-path?(encode=true), hardcoded to false for
		// now:
		boolean encode = false;
		try {
			uri = new String("http://localhost:5648/getInfo");
			output = consumeServiceHelper(uri, encode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			log.info("Exiting consumeRestService()");
		}
		return output;
	}
}
