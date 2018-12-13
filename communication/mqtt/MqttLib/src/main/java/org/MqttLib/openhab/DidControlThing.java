package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

/**
 * Confirmation message when a thing has been removed/approved
 * @author nch
 *
 */
@CompiledJson(onUnknown = Behavior.DEFAULT)
public class DidControlThing {
	public ControlType controlType;
	public String uid; 
	
	//Thing will be null if the thing is removed.
	public Thing thing;
	
	public DidControlThing(ControlType controlType, String uid, Thing thing) {
		this.controlType = controlType;
		this.uid = uid;
		this.thing = thing;
	}
}
