package org.RaspberryPi;

import java.io.IOException;

import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.ControlItem;
import org.MqttLib.openhab.ControlThing;
import org.MqttLib.openhab.ControlType;
import org.MqttLib.openhab.DeviceDiscovery;
import org.RaspberryPi.openhab.RestCommunicator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class RaspberryPiMessageHandler extends MessageHandler {
	
	private RestCommunicator rest = new RestCommunicator();

	public RaspberryPiMessageHandler(String topic, MqttMessage message) {
		super(topic, message);
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
				
			} catch (IOException | InterruptedException e ) {
				e.printStackTrace();
			}
		}
		
		if (topic.startsWith(Topic.CONTROLITEM.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				ControlItem controlItem = dslJson.deserialize(ControlItem.class, message.getPayload(), message.getPayload().length);
				if (controlItem.controlType == ControlType.ADD) {
					//add link
					rest.addLink(controlItem.uid, controlItem.channelUID);
				}
				else {
					rest.removeLink(controlItem.uid, controlItem.channelUID);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (topic.startsWith(Topic.CONTROLTHING.getTopic())) {
			try {
				System.out.println("Inside topic " + topic);
				ControlThing controlThing = dslJson.deserialize(ControlThing.class, message.getPayload(), message.getPayload().length);
				if (controlThing.controlType == ControlType.ADD) {
					rest.approveThing(controlThing.uid);
				}
				else {
					rest.removeThing(controlThing.uid);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
