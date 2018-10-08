package org.RaspberryPi.openhab;

import org.MqttLib.mqtt.BaseHandler;
import org.MqttLib.openhab.BaseEventHandler;

import com.launchdarkly.eventsource.MessageEvent;

public class ServerSentEventHandler extends BaseHandler implements BaseEventHandler {
	
	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		System.out.println("Received event: " + event + " with messageEvent: " + messageEvent.getData());
		
		//As the payload part of the event is escaped, we need to remove it if we don't want to consider payload a String.
		//Example: {"topic":"smarthome/items/TestSwitch/state","payload":"{\"type\":\"OnOff\",\"value\":\"OFF\"}","type":"ItemStateEvent"}
		String replaced = messageEvent.getData().replace("\\", "");
		byte[] data = replaced.getBytes("UTF-8");
		
		Event.EventResponse newEvent = dslJson.deserialize(Event.EventResponse.class, data, data.length);
		System.out.println("topic: " + newEvent.topic + " payload type: " + newEvent.payload.type + " payload value: " + newEvent.payload.value + " eventType: " + newEvent.type);
	}

}
