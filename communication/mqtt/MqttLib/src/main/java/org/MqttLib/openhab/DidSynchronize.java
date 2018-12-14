package org.MqttLib.openhab;

import java.util.List;
import java.util.Map;
import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class DidSynchronize {

	public Map<String, List<List<Object>>>  measurements;

	public  DidSynchronize(Map<String, List<List<Object>>> measurements){
		this.measurements = measurements;
	}


}
