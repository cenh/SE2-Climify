package org.RaspberryPi;

import org.RaspberryPi.openhab.ServerSentEventController;

/**
 * Entry-point to the program on the Raspberry Pi.
 * Starts up the MQTT Controller as well as the ServerSentEvent Controller.
 * @author nch
 *
 */
public class RaspberryPi 
{
	public static void main(String[] args) {
		RaspberryPiMqttController controller = new RaspberryPiMqttController();
		controller.start();
		
		ServerSentEventController sseController = new ServerSentEventController(controller);
		sseController.start();
	}
}
