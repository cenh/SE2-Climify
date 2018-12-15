package org.Climify.influxDB;

import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.MqttLib.openhab.SensorMeasurement;
import org.influxdb.InfluxDB;

/**
 * Responsible for communicating with the Influx Database on the VM
 * @author nch
 *
 */
public class InfluxCommunicator {

	private InfluxDB influxDB;
	private String dbName = "scadb";

	public void saveMeasurement(SensorMeasurement measurement) {
		//TODO: Should check to make sure there is a category - otherwise we will end up writing to NULL field with the new structure
		Point point = Point.measurement(measurement.name)
				.time(fdate(measurement.time), TimeUnit.MILLISECONDS)
				.addField(measurement.category, measurement.value).build();
		influxDB.write(dbName, "defaultPolicy", point);
	}
	
	public void removeSensor(String sensorID) {
		executeQuery(InfluxQuery.removeSensor(sensorID, dbName));
	}
	
	private void executeQuery(Query query) {
		influxDB.query(query);
	}

	public void connect() {
		influxDB = InfluxDBFactory.connect(URL.influxDB, "admin", "groupc");
	}

	public void close() {
		influxDB.close();
	}

	//Taken from the Msc. Project 
	private Long fdate(String time)
	{
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			return  format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return System.currentTimeMillis();
		}

	}
}
