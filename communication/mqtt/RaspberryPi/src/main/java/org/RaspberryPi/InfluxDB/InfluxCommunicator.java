package org.RaspberryPi.InfluxDB;

import org.MqttLib.openhab.Link;
import org.MqttLib.openhab.Synchronize;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.lang.reflect.Array;
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
	private String influxName = "openhab_db";

	public void connect() {
		influxDB = InfluxDBFactory.connect(URL.influxDB, "admin", "groupc");
	}

	public void close() {
		influxDB.close();
	}

	public Map<String, List<List<String>>> getMeasurementsSince(String time) {
		Map<String, List<List<String>>> measurements = new HashMap<>();
		
		Long timeInMillis = fdate(time);

		QueryResult result = influxDB.query(InfluxQuery.getSensors(influxName));

		List<List<Object>> sensors = result.getResults().get(0).getSeries().get(0).getValues();
		for(int i = 0; i < sensors.size(); i++) {
			String sensor = (String)sensors.get(i).get(0);

			QueryResult values =  influxDB.query(InfluxQuery.getMeasurementsSince(influxName, sensor, timeInMillis));

			List<List<Object>> vals = values.getResults().get(0).getSeries().get(0).getValues();
			List<List<String>> strings = new ArrayList<>();
			for (List<Object> list: vals) {
				List<String> temp = new ArrayList<>();
				for (Object object: list) {
					temp.add(object.toString());
				}
				
				strings.add(temp);
			}
			measurements.put(sensor, strings);

		}
		return measurements;
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
