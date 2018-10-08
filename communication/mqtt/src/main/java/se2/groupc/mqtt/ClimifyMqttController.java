package se2.groupc.mqtt;

public class ClimifyMqttController extends AsyncMqttController {

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		super.connectComplete(reconnect, serverURI);;
		
		// We subscribe to topics even when reconnecting as there could be edge-cases where topics are not persisted in a non-clean session
		subscribeToTopics();
	}
	
	private void subscribeToTopics() {
		//super.subscribe(topic, qos);
	}
}
