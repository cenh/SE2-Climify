package org.Climify;

import java.sql.SQLException;

import org.Climify.influxDB.InfluxCommunicator;
import org.Climify.mariaDB.MariaDBCommunicator;
import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * The MQTT Controller for Climify.
 * @author nch
 *
 */
public class ClimifyMqttController extends AsyncMqttController {
	
	private InfluxCommunicator influx = new InfluxCommunicator();
	private MariaDBCommunicator mariaDB = new MariaDBCommunicator();

	@Override
	public void start() {
		
		influx.connect();
		try {
			mariaDB.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		super.start();
	}
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.NEWCLIENT.getTopic()+"/#", 2);
		super.subscribe(Topic.SENSORDATA.getTopic()+"/#", 2);
		super.subscribe(Topic.SENSORUPDATE.getTopic()+"/#", 2);
		super.subscribe(Topic.INBOX.getTopic()+"/#", 2);
		super.subscribe(Topic.DIDCONTROLITEM.getTopic()+"/#", 2);
		super.subscribe(Topic.DIDCONTROLTHING.getTopic()+"/#", 2);
		super.subscribe(Topic.DIDSYNCHRONIZE.getTopic()+"/#", 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new ClimifyMessageHandler(topic, message, influx, mariaDB, this);
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI){
		super.connectComplete(reconnect,serverURI);

		executeTask(new SynchronizationTask(this, mariaDB, influx));
	}
}
