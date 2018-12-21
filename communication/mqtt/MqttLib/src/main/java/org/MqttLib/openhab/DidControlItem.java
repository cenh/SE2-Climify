package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

/**
 * Confirmation message when an Item has been linked/unlinked.
 * @author nch
 *
 */
@CompiledJson(onUnknown = Behavior.DEFAULT)
public class DidControlItem {
	public ControlType controlType;
	public String uid;
	public String channelUID;
	public Item item;
	
	public DidControlItem(ControlType controlType, String uid, String channelUID, Item item) {
		this.controlType = controlType;
		this.uid = uid;
		this.channelUID = channelUID;
		this.item = item;
	}
}
