package com.tihomir.rest.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.tihomir.rest.processor.RestProvider;
import com.tihomir.rest.utils.Constants;

/**
 * Helper class provides methods for convertign the data between different
 * formants.
 * 
 * @author Tihomir Raychev
 *
 */
public class Convertor {

	static Logger log = Logger.getLogger(RestProvider.class.getCanonicalName());
	private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
	// Test xml!
	@SuppressWarnings("unused")
	private static final String XML_TEXT = "<note>\n" + "<to>My Love</to>\n" + "<from>Tihomir</from>\n"
			+ "<heading>Reminder</heading>\n" + "<body>Don't forget me this weekend! lul Kappa!</body>\n" + "</note>";

	/**
	 * Convert the message payload to JSON.
	 *
	 * @param message
	 * @return
	 */
	public static String xmlToJsonConvertor(MessageHelper message) {
		log.info("Entering xmlToJsonConvertor(MessageHelper message)");
		String jsonAsString = null;
		try {
			// convert payload to json:
			JSONObject xmlJSONObj = XML.toJSONObject(message.getPayload());
			jsonAsString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new RuntimeException("Unacceptable payload format" + e.getStackTrace());
		} finally {
			log.info("Exiting xmlToJsonConvertor(MessageHelper message)");
		}
		return jsonAsString;
	}

	/**
	 * Convert the message payload to XML.
	 *
	 * @param message
	 * @return
	 */
	public static String jsonToXmlConvertor(MessageHelper message) {
		log.info("Entering jsonToXmlConvertor(MessageHelper message)");
		String xmlAsString = null;
		try {
			// convert payload to xml:
			JSONObject xmlJSONObj = new JSONObject(message.getPayload());
			xmlAsString = XML.toString(xmlJSONObj);
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new RuntimeException("Unacceptable payload format" + e.getStackTrace());
		} finally {
			log.info("Exiting jsonToXmlConvertor(MessageHelper message)");
		}
		return xmlAsString;
	}

	/**
	 * Map to xml Convertor. Outside class can convert it to json as well.
	 * 
	 * @author Tihomir Raychev
	 *
	 */
	public static class HashMapToXMLConverter implements Converter {

		@SuppressWarnings("unused")
		private String mapValue = null;
		private static final String dollarReplacement = "_-";
		private static final String escapeCharReplacement = "_";

		public HashMapToXMLConverter(String value) {
			this.mapValue = value;
		}

		public static String parseHashmapToXML(MessageHelper payload, Map<String, String> customProcessorExps) {
			final String SIGNATURE = "parseHashmapToXML(XMLToHashmap reqMessage, Map<String, Object> customProcessorExps)";
			log.info("Entering " + SIGNATURE);
			try {
				XStream xStream = new XStream(new DomDriver(Constants.EncoderHelper.CONTENTTYPE_ENCODING_UTF_8,
						new XmlFriendlyNameCoder(dollarReplacement, escapeCharReplacement)));
				String wrap = null;
				StringBuilder xmlBuilder = new StringBuilder();
				if (customProcessorExps != null && customProcessorExps.size() > 0) {
					Iterator<Entry<String, String>> iterator = customProcessorExps.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String, String> entry = iterator.next();
						wrap = entry.getKey();
						xStream.alias(wrap, MessageHelper.class);
						xStream.registerConverter(new HashMapToXMLConverter(entry.getValue()));
						String xml = xStream.toXML(payload);
						xmlBuilder.append(xml);
					}
				}
				return xmlBuilder.toString();
			} catch (Exception e) {
				log.error("An exception occurred", e);
				throw new RuntimeException(e);
			} finally {
				log.info("Exiting " + SIGNATURE);

			}
		}

		/**
		 * TODO Implement all below:
		 */
		@Override
		public boolean canConvert(@SuppressWarnings("rawtypes") Class arg0) {
			// TODO
			return false;
		}

		@Override
		public void marshal(Object arg0, HierarchicalStreamWriter arg1, MarshallingContext arg2) {
			// TODO
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
			// TODO
			return null;
		}
	}
}
