package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.CompiledJson.Behavior;

/**
 * Represents a discovered Thing in the Inbox of openHAB.
 * @author nch
 *
 */
@CompiledJson(onUnknown = Behavior.DEFAULT)
public class InboxDevice {
	public String bridgeUID;
	public String flag;
	public String label;
	public String thingUID;
	public String thingTypeUID;
}
