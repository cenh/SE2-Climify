package org.MqttLib.mqtt;

public enum Topic {
	NEWCLIENT("newClient"), 
	NEWSENSOR("newSensor"), 
	SENSORDATA("sensorData"),
	COMMAND("commandse2"),
	SENSORUPDATE("sensorUpdate"),
	CONTROLTHING("deviceControl/Thing"),
	CONTROLITEM("deviceControl/Item"),
	INBOX("inbox"),
	DEVICEDISCOVERY("deviceDiscovery");
	
	private final String topic;
	
	private Topic(String topic) {
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}
}
