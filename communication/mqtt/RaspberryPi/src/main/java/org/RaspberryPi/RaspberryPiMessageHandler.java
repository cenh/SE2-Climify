package org.RaspberryPi;

import java.io.IOException;

import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.RaspberryPi.openhab.RestCommunicator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class RaspberryPiMessageHandler extends MessageHandler {
	
	private RestCommunicator rest = new RestCommunicator();

	public RaspberryPiMessageHandler(String topic, MqttMessage message) {
		super(topic, message);
	}
	
	@Override
	public void run() {
		super.run();
		
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
