package org.RaspberryPi;

import java.io.IOException;

import org.MqttLib.mqtt.MessageCallback;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Channel;
import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.ControlItem;
import org.MqttLib.openhab.ControlThing;
import org.MqttLib.openhab.ControlType;
import org.MqttLib.openhab.DeviceDiscovery;
import org.MqttLib.openhab.DidControlItem;
import org.MqttLib.openhab.DidControlThing;
import org.MqttLib.openhab.Item;
import org.MqttLib.openhab.Thing;
import org.RaspberryPi.openhab.RestCommunicator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class RaspberryPiMessageHandler extends MessageHandler {

	private RestCommunicator rest = new RestCommunicator();
	private MessageCallback messageCallback;

	public RaspberryPiMessageHandler(String topic, MqttMessage message, MessageCallback messageCallback) {
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

		if (topic.startsWith(Topic.DEVICEDISCOVERY.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				DeviceDiscovery deviceDiscovery = dslJson.deserialize(DeviceDiscovery.class, message.getPayload(), message.getPayload().length);
				rest.startDiscovery(deviceDiscovery.binding.getBinding());
				Thread.sleep(10000); //TODO: Make it a Semophore and handle it better to avoid race conditions.
				String inboxJSON = rest.getInbox();
				messageCallback.publish(Topic.INBOX.getTopic(), 2, inboxJSON.getBytes("UTF-8"));
			} catch (IOException | InterruptedException e ) {
				e.printStackTrace();
			} catch (MqttPersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (topic.startsWith(Topic.CONTROLITEM.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				ControlItem controlItem = dslJson.deserialize(ControlItem.class, message.getPayload(), message.getPayload().length);

				DidControlItem didControlItem;
				if (controlItem.controlType == ControlType.ADD) {
					rest.addItem(controlItem.createItem);
					rest.addLink(controlItem.uid, controlItem.channelUID);

					//Get Item with openHAB's automatically added StateDescription
					byte[] itemJSONBytes = rest.getItem(controlItem.uid).getBytes("UTF-8");
					Item item = dslJson.deserialize(Item.class, itemJSONBytes, itemJSONBytes.length);

					//Prepare the message
					didControlItem = new DidControlItem(controlItem.controlType, controlItem.uid, controlItem.channelUID, item);
				}
				else {
					rest.removeLink(controlItem.uid, controlItem.channelUID);
					rest.removeItem(controlItem.uid);

					//Prepare the message
					didControlItem = new DidControlItem(controlItem.controlType, controlItem.uid, controlItem.channelUID, null);
				}

				//Retrieve the payload and reset the writer prior to publishing to avoid non-resetted writer if exceptions are thrown.
				dslJson.serialize(writer, didControlItem);
				byte[] payload = writer.getByteBuffer();
				writer.reset();
				messageCallback.publish(Topic.DIDCONTROLITEM.getTopic(), 2, payload);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}

		if (topic.startsWith(Topic.CONTROLTHING.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				ControlThing controlThing = dslJson.deserialize(ControlThing.class, message.getPayload(), message.getPayload().length);

				DidControlThing didControlThing;
				if (controlThing.controlType == ControlType.ADD) {
					rest.approveThing(controlThing.uid);

					//Get the approved Thing in openHAB
					byte[] thingJSONBytes = rest.getThing(controlThing.uid).getBytes("UTF-8");
					Thing thing = dslJson.deserialize(Thing.class, thingJSONBytes, thingJSONBytes.length);

					//Prepare the message
					didControlThing = new DidControlThing(controlThing.controlType, controlThing.uid, thing);
				}
				else {
					String thingJSON = rest.getThing(controlThing.uid);
					byte[] thingBytes = thingJSON.getBytes("UTF-8");
					Thing thing = dslJson.deserialize(Thing.class, thingBytes, thingBytes.length);
					for (Channel channel: thing.channels) {
						for (String item: channel.linkedItems) {
							rest.removeLink(item, channel.uid);
							rest.removeItem(item);
						}
					}

					rest.removeThing(controlThing.uid);

					//Prepare the message
					didControlThing = new DidControlThing(controlThing.controlType, controlThing.uid, null);
				}
				
				dslJson.serialize(writer, didControlThing);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}
}
