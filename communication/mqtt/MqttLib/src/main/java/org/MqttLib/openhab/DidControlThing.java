package org.MqttLib.openhab;

/**
 * Confirmation message when a thing has been removed/approved
 * @author nch
 *
 */
public class DidControlThing extends ControlThing {
	//Thing will be null if the thing is removed.
	public Thing thing;
	
	public DidControlThing(ControlType controlType, String uid, Thing thing) {
		this.controlType = controlType;
		this.uid = uid;
		this.thing = thing;
	}
}
