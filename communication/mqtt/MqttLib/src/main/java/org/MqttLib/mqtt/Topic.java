package org.MqttLib.mqtt;

public enum Topic {
	NEWCLIENT("newClient"), 
	NEWSENSOR("newSensor"), 
	SENSORDATA("sensorData"),
	COMMAND("commandse2");
	
	private final String topic;
	
	private Topic(String topic) {
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}
}
