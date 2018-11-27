package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Option {
	public String label;
	public String value;
}
