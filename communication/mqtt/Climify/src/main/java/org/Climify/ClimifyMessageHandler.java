package org.Climify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.Climify.influxDB.InfluxCommunicator;
import org.Climify.mariaDB.MariaDBCommunicator;
import org.MqttLib.mqtt.MessageCallback;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.ControlType;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.DidSynchronize;
import org.MqttLib.openhab.DidControlItem;
import org.MqttLib.openhab.DidControlThing;
import org.MqttLib.openhab.InboxDevice;
import org.MqttLib.openhab.NewClient;
import org.MqttLib.openhab.SensorMeasurement;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Handles the MQTT Messages received on Climify.
 * @author nch
 *
 */
public class ClimifyMessageHandler extends MessageHandler {

	private InfluxCommunicator influx;
	private MariaDBCommunicator mariaDB;
	private MessageCallback messageCallback;


	public ClimifyMessageHandler(String topic, MqttMessage message, InfluxCommunicator influx, MariaDBCommunicator mariaDB, MessageCallback messageCallback) {
		super(topic, message);
		this.influx = influx;
		this.mariaDB = mariaDB;
		this.messageCallback = messageCallback;
	}

	@Override
	public void run() {
		super.run();
		
		if (topic.startsWith(Topic.SENSORDATA.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				SensorMeasurement measurement = dslJson.deserialize(SensorMeasurement.class, message.getPayload(), message.getPayload().length);
				influx.saveMeasurement(measurement);
				executeRule(measurement.name, measurement.value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (topic.startsWith(Topic.NEWCLIENT.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				NewClient newClient = dslJson.deserialize(NewClient.class, message.getPayload(), message.getPayload().length);
				mariaDB.saveRaspberryPi(newClient.uid, newClient.locationID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (topic.startsWith(Topic.SENSORUPDATE.getTopic())) {
			String raspberryPiUID = topic.substring(Topic.SENSORUPDATE.getTopic().length() + 1);
			System.out.println("SENSORUPDATE ID = " + raspberryPiUID);
			mariaDB.saveRaspberryPi(raspberryPiUID, null);
			try {
				DeviceUpdate deviceUpdate = dslJson.deserialize(DeviceUpdate.class, message.getPayload(), message.getPayload().length);
				System.out.println(deviceUpdate.toString());
				mariaDB.saveDeviceUpdate(deviceUpdate, raspberryPiUID);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (topic.startsWith(Topic.INBOX.getTopic())) {
			System.out.println("Got sent new inbox!");
			String raspberryPiUID = topic.substring(Topic.INBOX.getTopic().length() + 1);
			try {
				List<InboxDevice> inbox = dslJson.deserializeList(InboxDevice.class, message.getPayload(), message.getPayload().length);
				System.out.println(inbox.toString());
				mariaDB.clearInbox(raspberryPiUID);
				mariaDB.saveInbox(inbox, raspberryPiUID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (topic.startsWith(Topic.DIDCONTROLITEM.getTopic())) {
			System.out.println("Got sent control item confirmation!");
			String raspberryPiUID = topic.substring(Topic.DIDCONTROLITEM.getTopic().length()+1);
			try {
				DidControlItem didControlItem = dslJson.deserialize(DidControlItem.class, message.getPayload(), message.getPayload().length);
				mariaDB.handleControlItem(didControlItem, raspberryPiUID);

				if (didControlItem.controlType == ControlType.REMOVE) {
					influx.removeSensor(didControlItem.uid);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			/**
			 *	Saves the batch data points sent by RaspberryPi in case of lost connection
			 * @author KacperZyla
			 */

			if (topic.startsWith(Topic.DIDSYNCHRONIZE.getTopic())) {
				String id = topic.substring(Topic.DIDSYNCHRONIZE.getTopic().length() + 1);
				System.out.println("DIDSYNCHRONIZE ID = " + id);

				try {
					DidSynchronize didSynchronize = dslJson.deserialize(DidSynchronize.class, message.getPayload(), message.getPayload().length);
					System.out.println(didSynchronize.toString());

					System.out.println("DidSynchronize object deserialized");

					Iterator it = didSynchronize.measurements.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry) it.next();

						String category = mariaDB.getSensorCategory((String) pair.getKey());

						System.out.println("Saving Measurements for " + pair.getKey());

						influx.saveBatchMeasurements((String) pair.getKey(), category, (List<List<String>>) pair.getValue());

						it.remove(); // avoids a ConcurrentModificationException
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}


		if (topic.startsWith(Topic.DIDCONTROLTHING.getTopic())) {
			System.out.println("Got sent control thing confirmation!");
			String raspberryPiUID = topic.substring(Topic.DIDCONTROLTHING.getTopic().length()+1);
			try {
				DidControlThing didControlThing = dslJson.deserialize(DidControlThing.class, message.getPayload(), message.getPayload().length);

				if (didControlThing.controlType == ControlType.REMOVE) {
					//All items associated with the thing we are about to remove
					List<String> itemNames = mariaDB.getItemNamesFromThing(didControlThing.uid);

					for (String itemName: itemNames) {
						influx.removeSensor(itemName);
					}
				}

				mariaDB.handleControlThing(didControlThing, raspberryPiUID);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 *	For every new measurement fetches the corresponding rules from MariaDB and executes them if necessary
	 * @author Christian Hansen and KacperZyla
	 */

	private void executeRule(String SensorID, String SensorValue) throws java.io.IOException,
            org.eclipse.paho.client.mqttv3.MqttPersistenceException, org.eclipse.paho.client.mqttv3.MqttException{
	    System.out.println("Looking for rules on sensor: " + SensorID);
		List<List<String>> results = new ArrayList<List<String>>();
		results = mariaDB.getRulesBySensorID(SensorID);
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
                            messageCallback.publish(Topic.COMMAND.getTopic(), 2, payload);
                        }

                        break;
                    case ("LESS"):
                        if (Float.parseFloat(SensorValue) < Float.parseFloat(Value)){
                            System.out.println("Sending the Command");
                            messageCallback.publish(Topic.COMMAND.getTopic(), 2, payload);
                        }
                        break;
                    case ("EQUAL"):
                        if (Float.parseFloat(SensorValue) == Float.parseFloat(Value)){
                            System.out.println("Sending the Command");
                            messageCallback.publish(Topic.COMMAND.getTopic(), 2, payload);
                        }
                        break;
                }
            }

		}
}
