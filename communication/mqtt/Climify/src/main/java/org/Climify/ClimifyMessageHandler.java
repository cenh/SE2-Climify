package org.Climify;

import java.io.IOException;

import org.Climify.influxDB.InfluxCommunicator;
import org.Climify.mariaDB.MariaDBCommunicator;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.SensorMeasurement;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClimifyMessageHandler extends MessageHandler {
	
	private InfluxCommunicator influx;
	private MariaDBCommunicator mariaDB;

	public ClimifyMessageHandler(String topic, MqttMessage message, InfluxCommunicator influx, MariaDBCommunicator mariaDB) {
		super(topic, message);
		this.influx = influx;
		this.mariaDB = mariaDB;
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
			String id = topic.substring(Topic.SENSORUPDATE.getTopic().length()+1);
			System.out.println("SENSORUPDATE ID = " + id);
			mariaDB.saveRaspberryPi(id, null);
			try {
				DeviceUpdate deviceUpdate = dslJson.deserialize(DeviceUpdate.class, message.getPayload(), message.getPayload().length);
				System.out.println(deviceUpdate.toString());
				mariaDB.saveDeviceUpdate(deviceUpdate, id);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
