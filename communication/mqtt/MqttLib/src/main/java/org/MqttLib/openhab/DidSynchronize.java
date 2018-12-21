package org.MqttLib.openhab;

import java.util.List;
import java.util.Map;
import com.dslplatform.json.CompiledJson;

/**
 * Confirmation message with synchronization data from the Raspberry Pi.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class DidSynchronize {
	public Map<String, List<List<String>>>  measurements;

	public  DidSynchronize(Map<String, List<List<String>>> measurements){
		this.measurements = measurements;
	}
}
