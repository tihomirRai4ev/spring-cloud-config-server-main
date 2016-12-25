package com.vmware.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
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
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

/**
 * This class is access point to some restfull services
 * TODO make factory Configurable[@Configurable]?
 * @author Tihomir
 *
 */
@RefreshScope
@RestController
public class RestProvider {

	static Logger log = Logger.getLogger(RestProvider.class.getCanonicalName());
	Base64Encoder encode = new Base64Encoder();

	@Value("${rate:default}")
	private String rate;

	@Value("${tollstop:default}")
	private String tollstop;

	@Value("${tollstart:default}")
	private String tollstart;

	@Value("${lanecount:default}")
	private String lanecount;

	@RequestMapping("/getConsumedService")
	public String consumeService() {
		log.info("inside consumeService()");
		String payload = null;
		payload = PollingService.consumeRestService();
		return payload == null ? "default" : payload;
	}

	/**
	 * Should return stuff from my gitHub
	 */
	@RequestMapping("/getInfo")
	public String requestInfo() throws UnsupportedEncodingException {
		StringBuilder helper = null;
		String jsonResponse = null;
		Gson gson = new Gson();
		try {
			log.info("inside requestInfo()");
			helper = new StringBuilder();
			helper.append("Properties read from my gitHub account: ").append("rate " + rate)
					.append("lanecount " + lanecount);
			helper.append(" " + tollstart + " " + tollstop);
		} finally {
			log.info("Exiting requestInfo()");
		}
		jsonResponse = gson.toJson(helper.toString());
		return jsonResponse;
	}

	/**
	 * Should return stuff from my gitHub - encoded!
	 */
	@RequestMapping("/getInfoEncoded")
	public String requestInfoEncoded() {
		StringBuilder helper = null;
		String result = null;
		try {
			log.info("inside getInfoEncoded()");
			helper = new StringBuilder();
			helper.append("Properties read from my gitHub account: ")
			        .append("rate " + rate)
					.append("lanecount " + lanecount);
			helper.append(" " + tollstart + " " + tollstop);
			result = URLEncoder.encode(helper.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			log.info("Exiting getInfoEncoded()");
		}
		return result;
	}

	/**
	 * returns the rates from gitHub test account
	 * @return
	 */
	@RequestMapping("/getRate")
	public String getRate() {
		log.info("inside getRate()");
		return rate;
	}
	
	public static class PollingService {
		private ScheduledExecutorService scheduledExecutorService = 
				Executors.newScheduledThreadPool(5);
		private static HttpGet get;
		private static HttpClient httpClient;

		ScheduledFuture<Object> scheduledFuture = scheduledExecutorService.
				schedule(new Callable<Object>() {
			public Object call() throws Exception {
				System.out.println("Executed!");
				return "Called!";
			}
		}, 5, TimeUnit.SECONDS);

		// TODO check
		public static String consumeServiceHelper(URI uri) {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody;
			try {
				/*
				 * Init http client and http method.
				 */
				httpClient = HttpClientBuilder.create().build();
				get = new HttpGet(uri);
				// hardcoded..
				get.setHeader("Content-Type", "application/json");
				get.setHeader("Accept", "application/json");
				responseBody = httpClient.execute(get, responseHandler);
			} catch (ClientProtocolException e) {
				log.error(e);
				throw new RuntimeException(e);
			} catch (IOException e) {
				log.error(e);
				throw new RuntimeException(e);
			}
			return responseBody;
		}

		/**
		 * Consume rest service from produced ones.
		 * 
		 * @return
		 */
		public static String consumeRestService(/** TODO String uri **/) {
			String output = null;
			URI uri = null;
			try {
				// URL url = new URL(uri);
				uri = new URI("http://localhost:5648/getInfo");
				output = consumeServiceHelper(uri);

			} catch (Exception e) {
				log.error(e.getMessage());
				throw new RuntimeException(e);
			}
			return output;
		}
	}
}
