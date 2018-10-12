package org.RaspberryPi;

public interface ServerSentEventCallback {

	void newMeasurement(String topic, byte[] payload);
}
