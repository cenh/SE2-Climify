package org.Climify;

import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.Topic;
import org.eclipse.paho.client.mqttv3.MqttException;

public class ClimifyMqttController extends AsyncMqttController {
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.SENSORDATA.getTopic(), 2);
	}
}
