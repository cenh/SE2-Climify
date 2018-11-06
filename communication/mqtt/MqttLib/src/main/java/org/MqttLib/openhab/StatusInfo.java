package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class StatusInfo {
	public String status;
	public String statusDetail;
	public String description;
}
