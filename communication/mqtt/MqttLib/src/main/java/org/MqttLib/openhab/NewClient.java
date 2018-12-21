package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

/**
 * Message sent when a new Raspberry Pi is running for the first time.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class NewClient {
	public String uid;
	public int locationID;
	
	public NewClient(String uid, int locationID) {
		this.uid = uid;
		this.locationID = locationID;
	}
}
