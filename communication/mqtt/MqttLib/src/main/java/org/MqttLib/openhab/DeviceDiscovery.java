package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

@CompiledJson(onUnknown = Behavior.DEFAULT)
public class DeviceDiscovery {
	public Binding binding;
}

