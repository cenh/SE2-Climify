package org.MqttLib.mqtt;

import java.io.IOException;

import org.MqttLib.openhab.Command;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MessageHandler extends BaseHandler implements Runnable {

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
		
		if (topic.startsWith(Topic.COMMAND.getTopic())) {
			try {
				System.out.println("Inside topic " + topic); 
				Command command = dslJson.deserialize(Command.class, message.getPayload(), message.getPayload().length);
				rest.sendCommandToItem(command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
