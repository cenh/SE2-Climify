package org.Climify;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
				executeRule(measurement.name);
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

	private void executeRule(String SensorID) {
	    System.out.println("Looking for rules on sensor: " + SensorID);
		List<List<String>> results = new ArrayList<List<String>>();
		results = this.mariaDB.getRulesBySensorID(SensorID);
		for(List<String> result : results) {
		    System.out.println("Running rule on sensor: " + result.get(0));
			try {
				URL url = new URL("http://localhost:80/rules/skoleklima/api/api-rule-execute.php");
				HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod("POST");
				PrintStream ps = new PrintStream(httpCon.getOutputStream());
				ps.print("?sensorID=" + result.get(0));
				ps.print("&Operator=" + result.get(1));
				ps.print("&Value=" + result.get(2));
				ps.print("&Action=" + result.get(3));
				httpCon.getInputStream();
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
