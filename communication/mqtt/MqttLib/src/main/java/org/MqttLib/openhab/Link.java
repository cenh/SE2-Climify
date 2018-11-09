package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Link {
	public String itemName;
	public String channelUID;
}
