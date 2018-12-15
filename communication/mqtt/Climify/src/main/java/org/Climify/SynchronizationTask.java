package org.Climify;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.Climify.influxDB.InfluxCommunicator;
import org.Climify.mariaDB.MariaDBCommunicator;
import org.MqttLib.mqtt.BaseHandler;
import org.MqttLib.mqtt.MessageCallback;
import org.MqttLib.mqtt.Topic;
import org.MqttLib.openhab.Synchronize;

public class SynchronizationTask extends BaseHandler implements Runnable {
	private MessageCallback messageCallback;
	private MariaDBCommunicator mariaDB;
	private InfluxCommunicator influx;
	
	public SynchronizationTask(MessageCallback messageCallback, MariaDBCommunicator mariaDB, InfluxCommunicator influx) {
		this.messageCallback = messageCallback;
		this.mariaDB = mariaDB;
		this.influx = influx;
	}

	@Override
	public void run() {
		List<String> RPis = mariaDB.getRaspberryPies();
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
				messageCallback.publish(Topic.SYNCHRONIZE.getTopic()+"/"+pair.getKey(),2,payload);

				it.remove(); // avoids a ConcurrentModificationException

			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
