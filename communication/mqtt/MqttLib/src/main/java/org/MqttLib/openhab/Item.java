package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

/**
 * Represents an Item in openHAB.
 * Describes a Sensor/Actuator.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class Item {
	public String name;
	public String category;
	public StateDescription stateDescription;
	
	public Item() {
		
	}
	
	public Item(String name, String category) {
		this.name = name;
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "name: " + name + " category: " + category;
	}
	
}