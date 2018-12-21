package org.MqttLib.openhab;

import java.util.List;
import com.dslplatform.json.CompiledJson;

/**
 * Represents a Thing from openHAB.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Thing {
	public String label;
	public String bridgeUID;
	public String UID;
	public String thingTypeUID;
	public List<Channel> channels;
	public StatusInfo statusInfo;
}
