package org.MqttLib.openhab;

import java.util.List;

import com.dslplatform.json.CompiledJson;

//@CompiledJson(formats = CompiledJson.Format.OBJECT)
public class ItemList {
	public List<Item> items;
	
	/*
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Item item: items) {
			builder.append(item.toString());
		}
		
		return builder.toString();
	}
	*/
}
