package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

/**
 * Represents a Link in openHAB.
 * Describes a link between an Item & Channel.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Link {
	public String itemName;
	public String channelUID;
	
	public Link(String itemName, String channelUID) {
		this.itemName = itemName;
		this.channelUID = channelUID;
	}
}
