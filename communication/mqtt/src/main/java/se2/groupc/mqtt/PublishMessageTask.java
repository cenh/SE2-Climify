package se2.groupc.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class PublishMessageTask implements Runnable {
	
	private MqttAsyncClient client;
	private MqttMessage message;
	private String topic;
	private IMqttActionListener actionListener;
	
	public PublishMessageTask(MqttAsyncClient client, MqttMessage message, String topic, IMqttActionListener actionListener) {
		this.client = client;
		this.message = message;
		this.topic = topic;
		this.actionListener = actionListener;
	}

	@Override
	public void run() {
		try {
			client.publish(topic, message, null, actionListener);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
