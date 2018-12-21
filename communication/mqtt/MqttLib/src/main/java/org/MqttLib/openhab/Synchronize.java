package org.MqttLib.openhab;


import com.dslplatform.json.CompiledJson;

/**
 * Message sent to the Raspberry Pis to request a synchronization of measurements when connection is reestablished.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Synchronize {
	public String timeOfLastMeasurement;

	public Synchronize(String timeOfLastMeasurement){
		this.timeOfLastMeasurement = timeOfLastMeasurement;
	}
}
