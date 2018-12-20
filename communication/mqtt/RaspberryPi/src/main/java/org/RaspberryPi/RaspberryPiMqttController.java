package org.RaspberryPi;

import java.io.IOException;
import java.util.List;

import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.Item;
import org.MqttLib.openhab.Link;
import org.MqttLib.openhab.NewClient;
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
	
	private RestCommunicator rest = new RestCommunicator();
	private DslJson<Object> dslJson = new DslJson<>(Settings.withRuntime().allowArrayFormat(true).includeServiceLoader());
	private JsonWriter writer = dslJson.newWriter();
	private InfluxCommunicator influx = new InfluxCommunicator();
	private String uid = RaspberryPiSetup.getInstance().getUID();
	private int locationID = RaspberryPiSetup.getInstance().getLocationID();
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.COMMAND.getTopic()+"/"+uid, 2);
		super.subscribe(Topic.SYNCHRONIZE.getTopic()+"/"+uid, 2);
		super.subscribe(Topic.COMMAND.getTopic()+"/"+uid, 2);
		super.subscribe(Topic.CONTROLTHING.getTopic()+"/"+uid, 2);
		super.subscribe(Topic.CONTROLITEM.getTopic()+"/"+uid, 2);
		super.subscribe(Topic.DEVICEDISCOVERY.getTopic()+"/"+uid, 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new RaspberryPiMessageHandler(topic, message, this, influx);
	}
	
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		super.connectComplete(reconnect, serverURI);

		try {
			if (RaspberryPiSetup.getInstance().isNew()) {
				sendNewClientUpdate();
			}
			influx.connect();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	private void sendNewClientUpdate() throws MqttPersistenceException, MqttException {
		try {
			NewClient newClient = new NewClient(uid, locationID);
			System.out.println("Preparing to send new client update");
			System.out.println("UID = " + uid + " locationID = " + locationID);
			dslJson.serialize(writer, newClient);
			byte[] payload = writer.getByteBuffer();
			writer.reset();
			super.publish(Topic.NEWCLIENT.getTopic()+"/"+uid, 2, payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void sendDeviceUpdate() throws MqttPersistenceException, MqttException {
		try {
			byte[] itemsData = rest.getAllItems().getBytes("UTF-8");
			byte[] thingsData = rest.getAllThings().getBytes("UTF-8");
			byte[] linksData = rest.getAllLinks().getBytes("UTF-8");
			
			List<Item> items = dslJson.deserializeList(Item.class, itemsData, itemsData.length);
			List<Thing> things = dslJson.deserializeList(Thing.class, thingsData, thingsData.length);
			List<Link> links = dslJson.deserializeList(Link.class, linksData, linksData.length);
			
			DeviceUpdate deviceUpdate = new DeviceUpdate(things, items, links);
			deviceUpdate.removeControllers();
			dslJson.serialize(writer, deviceUpdate);
			byte[] payload = writer.getByteBuffer();
			writer.reset();
			super.publish(Topic.SENSORUPDATE.getTopic()+"/"+uid, 2, payload);
			
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
