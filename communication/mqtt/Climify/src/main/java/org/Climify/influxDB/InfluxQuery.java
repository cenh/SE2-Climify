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
	
	public static Query removeSensor(String sensorID, String dbName) {
		Query query = new Query("DROP MEASUREMENT " + sensorID, dbName, true);
		return query;
	}
}
