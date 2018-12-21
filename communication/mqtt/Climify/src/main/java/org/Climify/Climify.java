package org.Climify;

/**
 * Entry-point for Climify. 
 * Starts the MQTT Controller.
 * @author nch
 *
 */
public class Climify {
	public static void main(String[] args) {
		ClimifyMqttController controller = new ClimifyMqttController();
		controller.start();
	}
}
