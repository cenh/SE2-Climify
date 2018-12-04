package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

@CompiledJson(onUnknown = Behavior.DEFAULT)
public class InboxDevice {
	public String bridgeUID;
	public String flag;
	public String label;
	public String serialNumber;
	public String id;
	public Integer firmwareVersion;
	public String modelId;
	public String vendor;
	public String thingUID;
	public String thingTypeUID;
}
