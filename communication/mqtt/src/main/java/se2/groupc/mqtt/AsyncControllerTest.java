package se2.groupc.mqtt;

import se2.groupc.openhab.sse.ServerSentEventController;

public abstract class AsyncControllerTest {

	public static void main(String[] args) {
		RaspberryPiMqttController controller = new RaspberryPiMqttController();
		controller.start();
		
		ServerSentEventController sseController = new ServerSentEventController();
		sseController.start();
	}

}
