package org.RaspberryPi.InfluxDB;

import org.influxdb.dto.Query;


/**
 * Class holding influxDB queries for RaspberryPi
 * @author nch and KacperZyla
 */

public abstract class InfluxQuery {
	
	public static String createRetentionPolicy(String name, String db, String duration) {
		String query = "CREATE RETENTION POLICY " + name + 
					   " ON " + db + 
					   " DURATION " + duration + 
					   " REPLICATION 1" + 
					   " DEFAULT"; 
		return query;
	}

	public static Query getSensors(String db){
		Query query =  new Query("SHOW Measurements", db);
		return query;
	}

	public  static Query getRecentTime(String db, String sensor){
		Query query = new Query("SELECT * FROM " + sensor + " ORDER BY time DESC LIMIT 1", db);
		return query;
	}
	
	public static Query getMeasurementsSince(String db, String sensor, Long time) {
		Query query = new Query("SELECT * FROM " + sensor + " WHERE time > " + time, db);
		return query;
	}
	
	public static Query removeSensor(String sensorID, String dbName) {
		Query query = new Query("DROP MEASUREMENT " + sensorID, dbName, true);
		return query;
	}

}
