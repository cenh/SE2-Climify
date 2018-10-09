package org.MqttLib.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MessageHandler extends BaseHandler implements Runnable {

	protected String topic;
	protected MqttMessage message;
	
	public MessageHandler(String topic, MqttMessage message) {
		this.topic = topic;
		this.message = message;
	}
	
	@Override
	public void run() {
		//Processing the message
		System.out.println("Message: " + message + "on topic: " + topic + " has been processed.");
		
		
		
		
	}

}
