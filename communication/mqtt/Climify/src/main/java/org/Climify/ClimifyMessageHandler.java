package org.Climify;

import java.io.IOException;

import org.Climify.influxDB.InfluxCommunicator;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.ItemList;
import org.MqttLib.openhab.SensorMeasurement;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClimifyMessageHandler extends MessageHandler {
	
	private InfluxCommunicator influx;

	public ClimifyMessageHandler(String topic, MqttMessage message, InfluxCommunicator influx) {
		super(topic, message);
		this.influx = influx;
	}
	
	@Override
	public void run() {
		super.run();
		
		if (topic.startsWith(Topic.SENSORDATA.getTopic())) {
			try {
				System.out.println("Inside topic " + topic); 
				SensorMeasurement measurement = dslJson.deserialize(SensorMeasurement.class, message.getPayload(), message.getPayload().length);
				influx.saveMeasurement(measurement);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (topic.startsWith(Topic.SENSORUPDATE.getTopic())) {
			ItemList items;
			try {
				items = dslJson.deserialize(ItemList.class, message.getPayload(), message.getPayload().length);
				System.out.println(items.items.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Save/Delete stuff from MariaDB
			
		}
	}

}
