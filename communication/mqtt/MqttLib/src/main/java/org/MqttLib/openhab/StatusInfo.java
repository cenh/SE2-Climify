package org.MqttLib.openhab;

import com.dslplatform.json.CompiledJson;

/**
 * Describes information about the status of a Thing - Is it currently connected etc.
 * @author nch
 *
 */
@CompiledJson(onUnknown = CompiledJson.Behavior.DEFAULT)
public class StatusInfo {
	public String status;
	public String statusDetail;
	public String description;
}
