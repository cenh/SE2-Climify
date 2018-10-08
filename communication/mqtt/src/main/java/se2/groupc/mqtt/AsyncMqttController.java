package se2.groupc.mqtt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * This class implements the core logic to asynchronously connect to a broker, receive & publish messages.
 * A Threadpool is used to dispatch the work of processing received messages to available threads.
 * @author nch
 *
 */
public class AsyncMqttController implements MqttCallbackExtended {

	private int numberOfThreads;
	private String brokerUrl;
	private int port;
	private String protocol;
	private String broker;
	private String clientId;
	private Boolean cleanSession;
	private Boolean automaticReconnect;
	private MqttAsyncClient client;
	private MqttClientPersistence persistence;
	private MqttConnectOptions connectOptions;
	
	private ExecutorService threadPool;

	public AsyncMqttController() {
		numberOfThreads = 4;
		brokerUrl = "tcp://iot.eclipse.org:1883";//"tcp://se2-webapp03.compute.dtu.dk:1883";
		clientId = MqttAsyncClient.generateClientId();
		cleanSession = false;
		automaticReconnect = true;
		persistence = new MemoryPersistence();
		
		threadPool = Executors.newFixedThreadPool(numberOfThreads);
		connectOptions = new MqttConnectOptions();
		connectOptions.setAutomaticReconnect(automaticReconnect);
		connectOptions.setCleanSession(cleanSession);	
	}

	public void start() {
		try {
			client = new MqttAsyncClient(brokerUrl, clientId, persistence);
			client.setCallback(this);
			startConnection();
		} catch (MqttException e) {
			System.out.println("Unable to set up client: " + e.toString() + "exception = " + e.getMessage());
		}

	}

	//Throws if there was an issue with the parameters for connecting to the broker.
	public void startConnection() throws MqttException {
		System.out.println("Initiating connection to " + client.getServerURI() + " with clientId: " + client.getClientId());

		IMqttActionListener connectionListener = new IMqttActionListener() {

			@Override
			public void onSuccess(IMqttToken asyncActionToken) {
				//We don't care about onSuccess as we already get notified by connectComplete
			}

			@Override
			public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
				//Reconnect?....
			}
		};

		client.connect(null, connectionListener);
	} 


	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost by: " + cause.getMessage());
		// Notify relevant workers

	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		System.out.println("Connection established at: " + serverURI);
		// We subscribe to topics even when reconnecting as there could be edge-cases where topics are not persisted in a non-clean session
		testData();
	}


	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Message arrived with topic: " + topic + " and message: " + message.toString());
		threadPool.execute(new MessageHandler(topic, message));
	}



	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Message delivered with id: " + token.getMessageId());
	}

	
	protected void publish(String topic, int qos, byte[] payload) throws MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage(payload);
		message.setQos(qos);
		
		IMqttActionListener messageListener = new IMqttActionListener() {
			
			@Override
			public void onSuccess(IMqttToken asyncActionToken) {
				System.out.println("success message " + asyncActionToken.getMessageId());
			}
			
			@Override
			public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
				System.out.println("failure message " + asyncActionToken.getMessageId() + " cause: " + exception.getMessage());
			}
		};
		
		threadPool.execute(new PublishMessageTask(client, message, topic, messageListener));
	}
	
	protected void subscribe(String topic, int qos) throws MqttException {
		client.subscribe(topic, qos);
	}
	
	
	private void testData() {
		try {
			//subscribe("testse2", 2);
			
			subscribe("niclastest", 2);
			publish("niclastest", 2, "Testingstuff".getBytes());
			publish("niclastest", 1, "Testingstuff1".getBytes());
			publish("niclastest", 0, "Testingstuff2".getBytes());
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

