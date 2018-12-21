package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

/**
 * Message that should be sent when requesting to Link/Unlink an Item to a Channel.
 * @author nch
 *
 */
@CompiledJson(onUnknown = Behavior.DEFAULT)
public class ControlItem {
	public ControlType controlType;
	public String uid;
	public String channelUID;
	
	//When linking an item further information should be specified
	public CreateItem createItem;
	
}