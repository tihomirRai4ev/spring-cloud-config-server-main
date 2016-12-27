package com.tihomir.rest.processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * This class is access point to some restfull services TODO make factory
 * Configurable[@Configurable]?
 * 
 * @author Tihomir Raychev
 *
 */
@RefreshScope
@RestController
public class RestProvider {

	static Logger log = Logger.getLogger(RestProvider.class.getCanonicalName());
	private Base64Encoder encode = new Base64Encoder();
	private RestConsumerService consumer = new RestConsumerService();
	private final Gson gson = new Gson();
	private final JsonParser parser = new JsonParser();

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
		log.info("Entering consumeService()");
		String payload = null;
		String jsonResponse = null;
		try {
			payload = consumer.consumeRestService();
			jsonResponse = this.parser.parse(payload).toString();
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new RuntimeException(e);
		} finally {
			log.info("Exiting consumeService()");
		}
		return payload == null ? "default" : jsonResponse;
	}

	/**
	 * Should return stuff from my gitHub
	 */
	@RequestMapping("/getInfo")
	public String requestInfo() throws UnsupportedEncodingException {
		StringBuilder helper = null;
		String jsonResponse = null;
		try {
			log.info("Entering requestInfo()");
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
			helper.append("Properties read from my gitHub account: ").append("rate " + rate)
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
	 * 
	 * @return
	 */
	@RequestMapping("/getRate")
	public String getRate() {
		log.info("inside getRate()");
		return rate;
	}
}
