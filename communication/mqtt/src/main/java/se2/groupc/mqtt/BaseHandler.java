package se2.groupc.mqtt;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Settings;

import se2.groupc.openhab.sse.RestCommunicator;

public class BaseHandler {
	protected DslJson<Object> dslJson = new DslJson<>(Settings.withRuntime().allowArrayFormat(true).includeServiceLoader());
	protected JsonWriter writer = dslJson.newWriter();
	protected RestCommunicator rest = new RestCommunicator();
}
