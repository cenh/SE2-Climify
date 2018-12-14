package org.Climify;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Settings;
import org.Climify.influxDB.InfluxCommunicator;
import org.Climify.mariaDB.MariaDBCommunicator;
import org.MqttLib.mqtt.AsyncMqttController;
import org.MqttLib.mqtt.MessageHandler;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Synchronize;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClimifyMqttController extends AsyncMqttController {
	
	private InfluxCommunicator influx = new InfluxCommunicator();
	private MariaDBCommunicator mariaDB = new MariaDBCommunicator();

	private DslJson<Object> dslJson = new DslJson<>(Settings.withRuntime().allowArrayFormat(true).includeServiceLoader());
	private JsonWriter writer = dslJson.newWriter();
	
	@Override
	public void start() {
		super.start();
		
		influx.connect();
		try {
			mariaDB.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void subscribeToTopics() throws MqttException {
		super.subscribe(Topic.SENSORDATA.getTopic()+"/#", 2);
		super.subscribe(Topic.SENSORUPDATE.getTopic()+"/#", 2);
	}
	
	@Override
	protected MessageHandler getMessageHandler(String topic, MqttMessage message) {
		return new ClimifyMessageHandler(topic, message, influx, mariaDB, this);
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI){
		super.connectComplete(reconnect,serverURI);

		List<String> RPis = mariaDB.getRaspberryPis();
		List<List<String>> RPiSensors = mariaDB.getSensorsForRaspberryPis();
		Map<String, String> RPisTimes =  influx.getTimesForRPis(RPis, RPiSensors);

		Iterator it = RPisTimes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());

			Synchronize sync = new Synchronize((String)pair.getValue());
			byte[] payload;
			try {
				dslJson.serialize(writer, sync);
				payload = writer.getByteBuffer();
				writer.reset();
				super.publish(Topic.SYNCHRONIZE.getTopic()+"/"+pair.getKey(),2,payload);

				it.remove(); // avoids a ConcurrentModificationException

			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
