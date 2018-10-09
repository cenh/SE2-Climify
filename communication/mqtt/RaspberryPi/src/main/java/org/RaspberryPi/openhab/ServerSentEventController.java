package org.RaspberryPi.openhab;

import java.net.URI;

import org.MqttLib.openhab.HeaderType;
import org.MqttLib.openhab.Utilities;
import org.RaspberryPi.ServerSentEventCallback;

import com.launchdarkly.eventsource.EventSource;

import okhttp3.Headers;

public class ServerSentEventController {
	
	ServerSentEventCallback callback;
	
	public ServerSentEventController() {
		
	}
	
	public ServerSentEventController(ServerSentEventCallback callback) {
		this.callback = callback;
	}
	
	public void start() {
		ServerSentEventHandler eventHandler = new ServerSentEventHandler();
		eventHandler.setCallback(callback);
		URI url = URI.create("http://localhost:8080/rest/events");
		EventSource.Builder builder = new EventSource.Builder(eventHandler, url);

		builder.connectionErrorHandler(new ServerSentEventConnectionErrorHandler());
		
		//Here we should configure the builder so it will establish a streaming connection with OpenHab using their Rest API
		//https://github.com/appzer/openhab/wiki/REST-API  @ServerPush
		
		Headers headers = Utilities.getHeaders(HeaderType.JSON, HeaderType.STREAMING);
		builder.headers(headers);		
		
		try {
			EventSource eventSource = builder.build();
			System.out.println(eventSource.getState());
			eventSource.start();
			System.out.println(eventSource.getState());
		} catch (Exception e) {
			System.out.println("Error setting up the eventSource: " + e.getMessage());
		}
	}

}
