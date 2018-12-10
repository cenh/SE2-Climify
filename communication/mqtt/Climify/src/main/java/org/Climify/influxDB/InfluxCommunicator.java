package org.Climify.influxDB;

import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
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

//		QueryResult [results=[Result [series=[Series [name=measurements, tags=null, columns=[name],
//		values=[[MainIndoorStation_Noise], [ZWaveNode4LC13LivingConnectZThermostat_SetpointHeating], [readBattery],
//		[readCO2], [readHumidity], [readOutdoorTemperature], [readTemperature], [setTemperature]]]], error=null]], error=null]

		System.out.println(result.getResults().get(0).getSeries().get(0).getValues());


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
