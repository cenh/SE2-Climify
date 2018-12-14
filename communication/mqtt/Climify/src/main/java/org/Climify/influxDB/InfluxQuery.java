package org.Climify.influxDB;

import org.influxdb.dto.Query;

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

	public static Query getMeasurementFields(String db, String sensor){
		Query query = new Query("SHOW FIELD KEYS ON" +  db +  " FROM "+ sensor, db);
		return query;
	}

}
