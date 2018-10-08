package org.RaspberryPi;

import org.RaspberryPi.openhab.ServerSentEventController;

public class RaspberryPi 
{
	public static void main(String[] args) {
		RaspberryPiMqttController controller = new RaspberryPiMqttController();
		controller.start();
		
		ServerSentEventController sseController = new ServerSentEventController();
		sseController.start();
	}
}
