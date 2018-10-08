package se2.groupc.openhab.sse;

import java.io.IOException;
import java.util.Map;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonObject;
import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.MapConverter;
import com.dslplatform.json.StringConverter;



public abstract class Event {
	
	/**
	 * The json returned from OpenHAB while listening to events has a payload parameter with an unusual structure.
	 * The payload is a String with a json object inside with multiple dynamic values depending on the sensor.
	 * Deserialization for EventResponse & EventResponsePayload is handled manually so it is possible to create an object from the payload.
	 * @author nch
	 *
	 */
	public static class EventResponse implements JsonObject {
		public final String topic;
		public final EventResponsePayload payload;
		public final String type;
		
		EventResponse(String topic, EventResponsePayload payload, String type) {
			this.topic = topic;
			this.payload = payload;
			this.type = type;
		}

		@Override
		public void serialize(JsonWriter writer, boolean minimal) {
			// TODO Auto-generated method stub
			
		}
		
		public static final JsonReader.ReadJsonObject<EventResponse> JSON_READER = new JsonReader.ReadJsonObject<EventResponse>() {

			@Override
			public EventResponse deserialize(@SuppressWarnings("rawtypes") JsonReader reader) throws IOException {
				reader.fillName();//topic
				reader.getNextToken();//start string
				String topic = StringConverter.deserialize(reader);
				reader.getNextToken();//,
				reader.getNextToken();//start name
				reader.fillName();//payload
				reader.getNextToken();//"
				reader.getNextToken();//{
				
				EventResponsePayload payload = EventResponsePayload.JSON_READER.deserialize(reader);
				reader.getNextToken();//}
				reader.getNextToken();//"
				reader.getNextToken();// start name
				reader.fillName();//type
				reader.getNextToken();//start string
				String type = StringConverter.deserialize(reader);
				return new EventResponse(topic, payload, type);
			}
		};
	}
	
	public static class EventResponsePayload implements JsonObject {
		public String type;
		public String value;
		public Map<String, String> properties;
		
		@Override
		public void serialize(JsonWriter writer, boolean minimal) {
			//For now we serialize it back into a real object.
			//If we need to make it back into the same format this needs to be changed.
			MapConverter.serialize(properties, writer);
		}
		
		public static final JsonReader.ReadJsonObject<EventResponsePayload> JSON_READER = new JsonReader.ReadJsonObject<EventResponsePayload>() {

			@Override
			public EventResponsePayload deserialize(@SuppressWarnings("rawtypes") JsonReader reader) throws IOException {
				Map<String, String> properties = MapConverter.deserialize(reader);
				EventResponsePayload payload = new EventResponsePayload();
				payload.type = properties.get("type");
				payload.value = properties.get("value");
				payload.properties = properties;
				
				return payload;
			}
		};
	}
	
}
