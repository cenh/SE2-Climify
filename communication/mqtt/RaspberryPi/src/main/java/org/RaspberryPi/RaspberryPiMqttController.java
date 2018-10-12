package org.RaspberryPi;

import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.RaspberryPi.openhab.RestCommunicator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class RaspberryPiMqttController extends AsyncMqttController implements ServerSentEventCallback {
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.COMMAND.getTopic()+"/#", 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new RaspberryPiMessageHandler(topic, message);
	}
	
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		super.connectComplete(reconnect, serverURI);
		
		RestCommunicator rest = new RestCommunicator();
		rest.sendSensorsToServer();
	}

	@Override
	public void newMeasurement(String topic, byte[] payload) {
		try {
			super.publish(topic, 2, payload);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
