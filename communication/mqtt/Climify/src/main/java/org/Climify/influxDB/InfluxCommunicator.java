package org.Climify.influxDB;

import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.MqttLib.openhab.SensorMeasurement;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;

import javax.management.QueryEval;

/**
 * Responsible for communicating with the Influx Database on the VM
 * @author nch
 *
 */
public class InfluxCommunicator {

	private InfluxDB influxDB;
	private String influxName = "scadb";

	public void saveMeasurement(SensorMeasurement measurement) {
		//TODO: Should check to make sure there is a category - otherwise we will end up writing to NULL field with the new structure
		Point point = Point.measurement(measurement.name)
				.time(fdate(measurement.time), TimeUnit.MILLISECONDS)
				.addField(measurement.category, measurement.value).build();
		influxDB.write("scadb", "defaultPolicy", point);
	}

	public void connect() {
		influxDB = InfluxDBFactory.connect(URL.influxDB, "admin", "groupc");
	}

	public void close() {
		influxDB.close();
	}

	public void printSensors() {
		QueryResult result = influxDB.query(InfluxQuery.getSensors(influxName));

		List<List<Object>> sensors = result.getResults().get(0).getSeries().get(0).getValues();
		System.out.println(sensors);

		List<String> times = null;


		for(int i = 0; i < sensors.size(); i++){
			String sensor = (String)sensors.get(i).get(0);
//			QueryResult [results=[Result [series=[Series [name=MainIndoorStation_Noise, tags=null, columns=[time, Noise],
//			values=[[2018-11-30T09:18:19.07Z, 35 dB]]]], error=null]], error=null]

			QueryResult measurement = influxDB.query(InfluxQuery.getRecentTime(influxName, sensor));
			String time = (String)measurement.getResults().get(0).getSeries().get(0).getValues().get(0).get(0);
			Instant instant = Instant.parse(time);
			times.add(instant.toString());
			System.out.println(instant);
		}
		String maxTime = Collections.max(times);
		System.out.println(maxTime);

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
