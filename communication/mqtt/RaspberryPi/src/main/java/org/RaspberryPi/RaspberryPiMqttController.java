package org.RaspberryPi;

import java.io.IOException;

import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.ItemList;
import org.RaspberryPi.openhab.RestCommunicator;
import org.RaspberryPi.openhab.SendItemsToServerTask;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Settings;

public class RaspberryPiMqttController extends AsyncMqttController implements ServerSentEventCallback {
	
	RestCommunicator rest = new RestCommunicator();
	DslJson<Object> dslJson = new DslJson<>(Settings.withRuntime().allowArrayFormat(true).includeServiceLoader());
	JsonWriter writer = dslJson.newWriter();
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.COMMAND.getTopic()+"/#", 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new RaspberryPiMessageHandler(topic, message);
	}
	
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		super.connectComplete(reconnect, serverURI);

		try {
			sendItemsToServer();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		rest.getAllThings();
		rest.getAllLinks();
	}
	
	private void sendItemsToServer() throws MqttPersistenceException, MqttException {
		try {
			String test = rest.getAllItems();
			StringBuilder itemsBuilder = new StringBuilder(test);
			itemsBuilder.insert(0, "{\"items\":");
			itemsBuilder.append('}');
			System.out.println(itemsBuilder);
			byte[] data = itemsBuilder.toString().getBytes("UTF-8");
			super.publish("topic", 2, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newMeasurement(String topic, byte[] payload) {
		try {
			super.publish(topic, 2, payload);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
