package org.MqttLib.openhab;

import com.launchdarkly.eventsource.EventHandler;

/**
 * Custom Event Handler for ServerSentEvents.
 * @author nch
 *
 */
public interface BaseEventHandler extends EventHandler {
	
	@Override
	default void onOpen() throws Exception {
		System.out.println("EventHandler opened");
	}
	
	@Override
	default void onClosed() throws Exception {
		System.out.println("EventHandler closed");
	}
	
	@Override
	default void onComment(String comment) throws Exception {
		System.out.println("Comment received: " + comment);
	}
	
	@Override
	default void onError(Throwable t) {
		System.out.println("EventHandler error: " + t.getMessage());
		t.printStackTrace();
	}

}
