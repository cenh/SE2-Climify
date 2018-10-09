package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

/**
 * Represents a Command that can be sent to an Item in OpenHAB.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.IGNORE)
public class Command {
	
	public String name;
	public String value;
	
	
	public Command() {
	}
	
	public Command(String value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public String getName() {
		return name;
	}


}
