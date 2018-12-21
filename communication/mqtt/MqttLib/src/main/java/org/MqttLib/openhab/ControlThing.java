package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

/**
 * Message that should be sent when wanting to remove/approve a Thing on the Raspberry Pi.
 * @author nch
 *
 */
@CompiledJson(onUnknown = Behavior.DEFAULT)
public class ControlThing {
	public ControlType controlType;
	public String uid; 
}