package org.RaspberryPi;

import java.io.IOException;

import com.dslplatform.json.DslJson;

import org.MqttLib.mqtt.MessageCallback;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.DidSynchronize;
import org.MqttLib.openhab.Synchronize;
import org.RaspberryPi.InfluxDB.InfluxCommunicator;
import org.RaspberryPi.openhab.RestCommunicator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class RaspberryPiMessageHandler extends MessageHandler {
	
	private RestCommunicator rest = new RestCommunicator();
	private InfluxCommunicator influx;
	private MessageCallback messageCallback;

	public RaspberryPiMessageHandler(String topic, MqttMessage message, MessageCallback messageCallback, InfluxCommunicator influxCommunicator) {
		super(topic, message);
		this.messageCallback = messageCallback;
	}
	
	@Override
	public void run() {
		super.run();
	
		if (topic.startsWith(Topic.COMMAND.getTopic())) {
			try {
				System.out.println("Inside topic " + topic); 
				Command command = dslJson.deserialize(Command.class, message.getPayload(), message.getPayload().length);
				rest.sendCommandToItem(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (topic.startsWith(Topic.SYNCHRONIZE.getTopic())) {

			try {
				Synchronize synchronize = dslJson.deserialize(Synchronize.class, message.getPayload(), message.getPayload().length);
				DidSynchronize didSynchronize = new DidSynchronize(influx.getMeasurementsSince(synchronize.timeOfLastMeasurement));
				dslJson.serialize(writer, didSynchronize);
				byte[] payload = writer.getByteBuffer();
				writer.reset();
				messageCallback.publish(Topic.DIDSYNCHRONIZE.getTopic()+"/testID", 2, payload);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}
}
