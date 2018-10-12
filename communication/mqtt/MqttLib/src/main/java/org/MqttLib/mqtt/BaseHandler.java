package org.MqttLib.mqtt;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Settings;

public class BaseHandler {
	protected DslJson<Object> dslJson = new DslJson<>(Settings.withRuntime().allowArrayFormat(true).includeServiceLoader());
	protected JsonWriter writer = dslJson.newWriter();
}
