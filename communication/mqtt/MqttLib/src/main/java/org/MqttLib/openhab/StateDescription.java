package org.MqttLib.openhab;

import java.util.List;

import com.dslplatform.json.CompiledJson;

/**
 * An Item can have information about what values (state) the Item accept or displays.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class StateDescription {
	public Integer minimum;
	public Integer maximum;
	public Integer step;
	public String pattern;
	public Boolean readOnly;
	public List<Option> options;
}
