package org.MqttLib.mqtt;

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
		port = 1883;
		broker = "iot.eclipse.org"; // se2-webapp03.compute.dtu.dk
		protocol = "tcp";
		numberOfThreads = 4;
		brokerUrl = protocol + "://" + broker + ":" + port;
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
		cause.printStackTrace();
		// Notify relevant workers

	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		System.out.println("Connection established at: " + serverURI);
		// We subscribe to topics even when reconnecting as there could be edge-cases where topics are not persisted in a non-clean session
		try {
			subscribeToTopics();
		} catch (MqttException e) {
			System.out.println("Failed to subscribe to topics.");
			//TODO Maybe create a timer and retry subscribing soon.
			e.printStackTrace();
		}
	}


	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Message arrived with topic: " + topic + " and message: " + message.toString());
		threadPool.execute(getMessageHandler(topic, message));
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

	/**
	 * 
	 * @param topic the topic to subscribe to, which can include wildcards.
	 * @param qos the maximum quality of service at which to subscribe. Messages published at a lower quality of service will be received at the published QoS. Messages published at a higher quality of service will be received using the QoS specified on the subscribe.
	 * @throws MqttException
	 */
	protected void subscribe(String topic, int qos) throws MqttException {
		client.subscribe(topic, qos);
	}

	/**
	 * Override this to configure the topics to initially subscribe to.
	 * @throws MqttException
	 */
	protected void subscribeToTopics() throws MqttException { 
	}

	/**
	 * Override this if you want to return your own MessageHandler.
	 * @param topic The topic the message arrived on.
	 * @param message An MQTT message holds the application payload and options specifying how the message is to be delivered The message includes a "payload" (the body of the message) represented as a byte[].
	 * @return MessageHandler
	 */
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new MessageHandler(topic, message);		
	}
	
	/**
	 * Dispatches the task off to the threadpool.
	 * @param runnable
	 */
	protected void executeTask(Runnable runnable) {
		threadPool.execute(runnable);
	}

}

