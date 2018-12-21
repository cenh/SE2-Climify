package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

/**
 * The StateDescription of Items can contain a List of Options which describes which values its state can be set to.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Option {
	public String label;
	public String value;
}
