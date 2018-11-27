package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

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