package org.MqttLib.openhab;

import java.util.List;

import com.dslplatform.json.CompiledJson;

@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class DeviceUpdate {
	private List<Thing> things;
	private List<Item> items;
	private List<Link> links;
	
	@CompiledJson
	public DeviceUpdate(List<Thing> things, List<Item> items, List<Link> links) {
		this.things = things;
		this.items = items;
		this.links = links;
	}
	
	public List<Thing> getThings() {
		return things;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public List<Link> getLinks() {
		return links;
	}
}
