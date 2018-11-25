package org.MqttLib.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public interface MessageCallback {

    public void publish(String topic, int qos, byte[] payload) throws MqttPersistenceException, MqttException;
}
