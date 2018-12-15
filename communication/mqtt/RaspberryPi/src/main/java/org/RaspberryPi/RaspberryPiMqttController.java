package org.RaspberryPi;

import java.io.IOException;
import java.util.List;

import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.Item;
import org.MqttLib.openhab.Link;
import org.MqttLib.openhab.Thing;
import org.RaspberryPi.InfluxDB.InfluxCommunicator;
import org.RaspberryPi.openhab.RestCommunicator;
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
	InfluxCommunicator influx = new InfluxCommunicator();
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.COMMAND.getTopic()+"/testID", 2);
		super.subscribe(Topic.SYNCHRONIZE.getTopic()+"/testID", 2);
		super.subscribe(Topic.COMMAND.getTopic()+"/#", 2);
		super.subscribe(Topic.CONTROLTHING.getTopic()+"/#", 2);
		super.subscribe(Topic.CONTROLITEM.getTopic()+"/#", 2);
		super.subscribe(Topic.DEVICEDISCOVERY.getTopic()+"/#", 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new RaspberryPiMessageHandler(topic, message, this, influx);
	}
	
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		super.connectComplete(reconnect, serverURI);

		try {
			sendDeviceUpdate();
			influx.connect();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	private void sendDeviceUpdate() throws MqttPersistenceException, MqttException {
		try {
			//TODO: Could be parallelized
			byte[] itemsData = rest.getAllItems().getBytes("UTF-8");
			byte[] thingsData = rest.getAllThings().getBytes("UTF-8");
			byte[] linksData = rest.getAllLinks().getBytes("UTF-8");
			
			List<Item> items = dslJson.deserializeList(Item.class, itemsData, itemsData.length);
			List<Thing> things = dslJson.deserializeList(Thing.class, thingsData, thingsData.length);
			List<Link> links = dslJson.deserializeList(Link.class, linksData, linksData.length);
			
			DeviceUpdate deviceUpdate = new DeviceUpdate(things, items, links);
			deviceUpdate.removeControllers();
			dslJson.serialize(writer, deviceUpdate);
			
			super.publish(Topic.SENSORUPDATE.getTopic()+"/"+"testID", 2, writer.getByteBuffer());
			
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
