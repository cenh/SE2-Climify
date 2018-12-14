package org.Climify;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.Climify.influxDB.InfluxCommunicator;
import org.Climify.influxDB.InfluxQuery;
import org.Climify.mariaDB.MariaDBCommunicator;
import org.MqttLib.mqtt.MessageCallback;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.DidSynchronize;
import org.MqttLib.openhab.SensorMeasurement;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

public class ClimifyMessageHandler extends MessageHandler {
	
	private InfluxCommunicator influx;
	private String influxName = "scadb";
	private MariaDBCommunicator mariaDB;
	private MessageCallback msgCall;


	public ClimifyMessageHandler(String topic, MqttMessage message, InfluxCommunicator influx, MariaDBCommunicator mariaDB, MessageCallback msgCall) {
		super(topic, message);
		this.influx = influx;
		this.mariaDB = mariaDB;
		this.msgCall = msgCall;
	}
	
	@Override
	public void run() {
		super.run();
		
		if (topic.startsWith(Topic.SENSORDATA.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				SensorMeasurement measurement = dslJson.deserialize(SensorMeasurement.class, message.getPayload(), message.getPayload().length);
//				influx.saveMeasurement(measurement);
//				executeRule(measurement.name, measurement.value);


				//getCategory test
				QueryResult QR = influx.getMeasurementFields("readTemperature");

				List<List<Object>> fields = QR.getResults().get(0).getSeries().get(0).getValues();

				String category = fields.get(0).get(0).toString();

				System.out.println(category);

			} catch (Exception e) {
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
		
		if (topic.startsWith(Topic.DIDSYNCHRONIZE.getTopic())) {
			String id = topic.substring(Topic.SENSORUPDATE.getTopic().length()+1);
			System.out.println("DIDSYNCHRONIZE ID = " + id);
			
			try {
				DidSynchronize didSynchronize = dslJson.deserialize(DidSynchronize.class, message.getPayload(), message.getPayload().length);
				System.out.println(didSynchronize.toString());

				Iterator it = didSynchronize.measurements.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();

					QueryResult QR = influx.getMeasurementFields((String)pair.getKey());

					List<List<Object>> fields = QR.getResults().get(0).getSeries().get(0).getValues();

					String category = fields.get(0).get(0).toString();

					influx.saveBatchMeasurements((String)pair.getKey(), category, (List<List<Object>>)pair.getValue());

					it.remove(); // avoids a ConcurrentModificationException
				}

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
				ps.print("SensorID=" + result.get(0));
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

	private void executeRule(String SensorID, String SensorValue) throws java.io.IOException,
            org.eclipse.paho.client.mqttv3.MqttPersistenceException, org.eclipse.paho.client.mqttv3.MqttException{
	    System.out.println("Looking for rules on sensor: " + SensorID);
		List<List<String>> results = new ArrayList<List<String>>();
		results = this.mariaDB.getRulesBySensorID(SensorID);
		String Operator = "";
		String Value = "";
		String Action = "";
		String ActuatorID = "";
		for(List<String> result : results) {
		    System.out.println("Running rule on sensor: " + result.get(0));
			Operator = result.get(1);
			Value = result.get(2);
			Action = result.get(3);
			ActuatorID = result.get(4);
            System.out.println("Extracted Variables: Operator =  "
                    + Operator + " Value = " + Value + " Action = " + Action + " ActuatorID = " + ActuatorID);

			Command com  = new Command(Action, ActuatorID);
			dslJson.serialize(writer, com);

			byte[] payload = writer.getByteBuffer();
			System.out.println("Created a Command:  " + payload);
			writer.reset();

			switch (Operator){
                    case ("GREATER"):
                        if (Float.parseFloat(SensorValue) > Float.parseFloat(Value)){
                            System.out.println("Sending the Command");
                            msgCall.publish(Topic.COMMAND.getTopic(), 2, payload);
                        }
                        break;
                    case ("LESS"):
                        if (Float.parseFloat(SensorValue) < Float.parseFloat(Value)){
                            System.out.println("Sending the Command");
                            msgCall.publish(Topic.COMMAND.getTopic(), 2, payload);
                        }
                        break;
                    case ("EQUAL"):
                        if (Float.parseFloat(SensorValue) == Float.parseFloat(Value)){
                            System.out.println("Sending the Command");
                            msgCall.publish(Topic.COMMAND.getTopic(), 2, payload);
                        }
                        break;
                }
            }

		}
}


//	URL url = new URL("http://localhost:80/rules/skoleklima/api/api-rule-execute.php");
//				HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
//				httpCon.setDoOutput(true);
//				httpCon.setRequestMethod("POST");
//				PrintStream ps = new PrintStream(httpCon.getOutputStream());
//				ps.print("SensorID=" + result.get(0));
//				ps.print("&Operator=" + result.get(1));
//				ps.print("&Value=" + result.get(2));
//				ps.print("&Action=" + result.get(3));
//				httpCon.getInputStream();
//				ps.close();