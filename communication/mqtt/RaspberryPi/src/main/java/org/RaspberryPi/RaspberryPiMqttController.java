package org.RaspberryPi;

import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.Topic;
import org.eclipse.paho.client.mqttv3.MqttException;

public class RaspberryPiMqttController extends AsyncMqttController {

	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.COMMAND.getTopic()+"/#", 2);
	}
	
	
}
