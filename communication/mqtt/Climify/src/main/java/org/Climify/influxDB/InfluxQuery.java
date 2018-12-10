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
		Query query =  new Query("SELECT * FROM MEASUREMENTS", db);
		return query;
	}

}
