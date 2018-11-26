package org.MqttLib.openhab;

import java.util.List;

import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class StateDescription {
	public Integer minimum;
	public Integer maximum;
	public Integer step;
	public String pattern;
	public Boolean readOnly;
	public List<Option> options;
}
