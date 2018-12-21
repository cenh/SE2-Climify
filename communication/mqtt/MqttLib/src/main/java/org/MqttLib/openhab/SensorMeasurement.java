package org.MqttLib.openhab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


import com.dslplatform.json.CompiledJson;

/**
 * Represents a measurement from a Sensor/Actuator.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.IGNORE)
public class SensorMeasurement {
	public String name;
	public String type;
	public String value;
	public String time;
	public String category;
	
	public SensorMeasurement() {
		
	}
	
	public SensorMeasurement(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.time = getTime();
	}

	public SensorMeasurement(String name, String type, String value, String time){
		this.name = name;
		this.type = type;
		this.value = value;
		this.time = time;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	private String getTime() {
	    SimpleDateFormat format = new SimpleDateFormat(
	    		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	    format.setTimeZone(TimeZone.getTimeZone("UTC"));
	    Date date = new Date();  
	    return format.format(date);
	}
}
