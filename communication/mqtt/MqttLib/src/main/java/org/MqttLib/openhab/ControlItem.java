package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

@CompiledJson(onUnknown = Behavior.DEFAULT)
public class ControlItem {
	public ControlType controlType;
	public String uid;
	public String channelUID;
}

enum ControlItemType {
	LINK, UNLINK
}