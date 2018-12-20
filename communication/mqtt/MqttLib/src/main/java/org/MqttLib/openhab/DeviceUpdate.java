package org.MqttLib.openhab;

import java.util.List;

import com.dslplatform.json.CompiledJson;

/**
 * Message to update Climify with all approved devices on a Raspberry Pi and everything connected to them.
 * @author nch
 *
 */
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
	
	/**
	 * We dont wan't to send important Things to skoleklima like the Netatmo API and the Z-wave Serial Controller to avoid users accidently removing them.
	 * There is unfortunately not a way to distinguish such Things from regular devices and currently they will be hardcoded.
	 */
	public void removeControllers() {
		String netatmo = "Netatmo API";
		String zwaveController = "Z-Wave Serial Controller";
		
		things.removeIf(p -> p.label.equals(netatmo) || p.label.equals(zwaveController));
	}
}
