package org.MqttLib.openhab;


import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Synchronize {
	public String timeOfLastMeasurement;

	public Synchronize(String time){
		this.timeOfLastMeasurement = time;
	}
}
