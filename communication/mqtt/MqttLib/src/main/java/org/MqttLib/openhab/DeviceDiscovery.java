package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

/**
 * Message that should be sent when requesting the Raspberry Pi to search for new devices on a specific binding.
 * @author nch
 *
 */
@CompiledJson(onUnknown = Behavior.DEFAULT)
public class DeviceDiscovery {
	public Binding binding;
}

