package se2.groupc.openhab.sse;

import java.util.HashMap;
import java.util.Map;

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
	
}
