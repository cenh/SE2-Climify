package se2.groupc.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

public class ClimifyMqttController extends AsyncMqttController {
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.SENSORDATA.getTopic(), 2);
	}
}
