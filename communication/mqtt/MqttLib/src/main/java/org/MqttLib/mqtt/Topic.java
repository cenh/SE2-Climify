package org.MqttLib.mqtt;

/**
 * Topics used for the MQTT communication.
 * @author nch
 *
 */
public enum Topic {
	NEWCLIENT("newClient"), 
	NEWSENSOR("newSensor"), 
	SENSORDATA("sensorData"),
	COMMAND("commandse2"),
	SENSORUPDATE("sensorUpdate"),
	CONTROLTHING("deviceControl/Thing"),
	CONTROLITEM("deviceControl/Item"),
	INBOX("inbox"),
	DEVICEDISCOVERY("deviceDiscovery"),

	//Confirmation topics
	DIDCONTROLTHING("didDeviceControl/Thing"),
	DIDCONTROLITEM("didDeviceControl/Item"),

    SYNCHRONIZE("sync"),
    DIDSYNCHRONIZE("didSync");
	
	private final String topic;
	
	private Topic(String topic) {
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}
}
