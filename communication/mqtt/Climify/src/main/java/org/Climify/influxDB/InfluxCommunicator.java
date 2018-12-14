package org.Climify.influxDB;

import org.MqttLib.openhab.Link;
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

	public String getTimeBySensors(List<String> sensors) {
		System.out.println(sensors);

		List<String> times = new ArrayList<String>();


		for(int i = 0; i < sensors.size(); i++){
			String sensor = sensors.get(i);

			QueryResult measurement = influxDB.query(InfluxQuery.getRecentTime(influxName, sensor));
			String time = "";
			try {
				time = (String)measurement.getResults().get(0).getSeries().get(0).getValues().get(0).get(0);

			}catch (NullPointerException e){
				System.out.println("No measurements for sensor no " + i);
				time = "";
			}
			if(time != "") {
				Instant instant = Instant.parse(time);
				times.add(instant.toString());
				System.out.println(instant);
			}

		}
		if(times.isEmpty()) return "";
		
		String maxTime = Collections.max(times);
		System.out.println("Max time :" + maxTime);

		return maxTime;
	}

	public List<String> getTimesForRPis(List<List<String>> RPisSensors){

		List<String> times = new ArrayList<String>();
		for(int i = 0; i < RPisSensors.size(); i++){
			List<String> s = RPisSensors.get(i);
			String time = "";

			time = getTimeBySensors(s);
			if(time == ""){
				System.out.println("No measurements for RPi no " + i);
			}
			else{
				times.add(time);
				System.out.println("Max time for RPI no: " + i + " is " + time);
			}

		}
		return times;

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
