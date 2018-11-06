package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Channel {
	public String uid;
	public String id;
	public String channelTypeUID;
	public String itemType;
	public String kind;
	public String label;
	public String description;
}
