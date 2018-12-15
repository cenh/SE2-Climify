package org.MqttLib.openhab;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;

public class Utilities {
	
	/**
	 * Convenience function to easily construct the headers needed when sending a request using OkHttp3
	 * @param headerTypes Any amount of needed HeaderType.
	 * @return Returns Headers for use with OkHttp3
	 */
	public static Headers getHeaders(HeaderType... headerTypes) {
		Map<String, String> headersToAdd = new HashMap<String, String>();
		
		for (HeaderType header: headerTypes) {
			headersToAdd.put(header.getType(), header.getValue());
		}
		
		return Headers.of(headersToAdd);
	}
	
	/**
	 * Convenience function to construct valid JSON from a set of key-value pairs.
	 * @param Map<String, String> key-value pairs.
	 * @return JSON-formatted String
	 */
	public static String createJSON(Map<String, String> strings) {
		String json = "{";
		Set<String> keys = strings.keySet();
		
		int i = 0;
		for (String key: keys) {
			json = json + '"' + key + "\":\"" + strings.get(key) + '"';
			
			if (++i < keys.size()) {
				json = json + ',';
			}
		}
		
		json = json + '}';
		
		return json;
	}
	
}
