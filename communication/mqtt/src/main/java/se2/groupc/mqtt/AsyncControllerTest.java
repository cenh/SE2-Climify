package se2.groupc.mqtt;

public abstract class AsyncControllerTest {

	public static void main(String[] args) {
		AsyncMqttController controller = new AsyncMqttController();
		controller.start();
		
	}

}
