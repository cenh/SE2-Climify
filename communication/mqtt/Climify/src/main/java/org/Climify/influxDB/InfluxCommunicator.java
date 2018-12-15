package org.Climify.influxDB;

import org.MqttLib.openhab.Link;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

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
		influxDB.write(influxName, "defaultPolicy", point);
	}

	public void removeSensor(String sensorID) {
		executeQuery(InfluxQuery.removeSensor(sensorID, influxName));
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

	/**
	 *  Below section is responsible for InfluxDB part of data synchronization in case of lost connection
	 * @author KacperZyla
	 *
	 */

	/**
	 *	Gets most recent measurement time among all the sensors specified by sensors
	 */

	public String getTimeBySensors(List<String> sensors) {
		System.out.println(sensors);

		List<String> times = new ArrayList<String>();


		for(int i = 0; i < sensors.size(); i++){
			String sensor = sensors.get(i);

			QueryResult measurement = influxDB.query(InfluxQuery.getRecentTime(influxName, sensor));
			String time = "";
			String s = "";
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
		String s = sensors.get(times.indexOf(maxTime));
		System.out.println("Max time: " + maxTime + " for sensor " + s);

		return maxTime;
	}

	/**
	 *	Uses getTimeBySensors to get most recent measurement time for all RaspberryPies specified by RPis
	 */

	public Map<String, String> getTimesForRPis(List<String> RPis, List<List<String>> RPisSensors){

		Map<String, String> times = new HashMap<String, String>();
		for(int i = 0; i < RPisSensors.size(); i++){
			List<String> s = RPisSensors.get(i);
			String time = "";

			time = getTimeBySensors(s);
			if(time == ""){
				System.out.println("No measurements for RPi no " + i);
			}
			else{
				System.out.println("Max time for RPI no: " + i + " is " + time);
			}
			times.put(RPis.get(i),time);

		}
		return times;

	}

	/**
	 *	Executes query on influxDB to get the fields of the measurement
	 */

	public QueryResult getMeasurementFields(String sensor){
        QueryResult fields = influxDB.query(InfluxQuery.getMeasurementFields(influxName, sensor));
        return fields;
    }


	/**
	 * Saves measurement into influxDB in batches
	 */

	public void saveBatchMeasurements(String sensor,String category, List<List<String>> measurements){

		influxDB.enableBatch(measurements.size(), 100, TimeUnit.MILLISECONDS);
		for(int i = 0; i < measurements.size(); i++){
			String value = measurements.get(i).get(1);
			System.out.println("Value no " + i + " is: " + value);
			String time = measurements.get(i).get(0);
			System.out.println("Time no " + i + " is: " + time);
			SensorMeasurement sensorMeasurement = new SensorMeasurement(sensor, "", value, time);
			sensorMeasurement.setCategory(category);
			saveMeasurement(sensorMeasurement);
		}
		influxDB.flush();
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
