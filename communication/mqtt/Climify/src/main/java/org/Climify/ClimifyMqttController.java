package org.Climify;

import org.Climify.influxDB.InfluxCommunicator;
import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClimifyMqttController extends AsyncMqttController {
	
	private InfluxCommunicator influx = new InfluxCommunicator();
	
	@Override
	public void start() {
		super.start();
		
		influx.connect();
	}
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.SENSORDATA.getTopic()+"/#", 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new ClimifyMessageHandler(topic, message, influx);
	}
}
