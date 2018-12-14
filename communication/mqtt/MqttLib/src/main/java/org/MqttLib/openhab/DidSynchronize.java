package org.MqttLib.openhab;

import java.util.Map;
import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class DidSynchronize {
	public Map<String, String> measurements;
}
