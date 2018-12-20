package org.RaspberryPi.openhab;

import com.launchdarkly.eventsource.ConnectionErrorHandler;

/**
 * Custom error handler for ServerSentEvents.
 * @author nch
 *
 */
public class ServerSentEventConnectionErrorHandler implements ConnectionErrorHandler {

	@Override
	public Action onConnectionError(Throwable t) {
		System.out.println("error during connection = " + t.getMessage());
		return Action.PROCEED;
	}

}
