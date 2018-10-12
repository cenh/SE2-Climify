package org.MqttLib.openhab;

public enum HeaderType {
	JSON ("Accept", "application/json"), 
	XML ("Accept", "application/xml"), 
	JAVASCRIPT ("Accept", "application/x-javascript"), 
	STREAMING ("X-Atmosphere-Transport", "streaming"), 
	TEXTPLAIN ("Content-Type", "text/plain");
	
	private final String type;
	private final String value;
	
	private HeaderType(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() { 
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
}