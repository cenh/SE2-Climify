package se2.groupc.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageHandler implements Runnable {

	private String topic;
	private MqttMessage message;
	
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
