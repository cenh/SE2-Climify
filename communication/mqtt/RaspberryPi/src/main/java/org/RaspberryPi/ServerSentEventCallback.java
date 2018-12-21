package org.RaspberryPi;

/**
 * Callback to publish messages from the ServerSentEventHandler.
 * @author nch
 *
 */
public interface ServerSentEventCallback {

	void newMeasurement(String topic, byte[] payload);
}
