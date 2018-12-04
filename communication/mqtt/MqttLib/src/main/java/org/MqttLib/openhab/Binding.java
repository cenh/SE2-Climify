package org.MqttLib.openhab;

public enum Binding {
	ZWAVE("zwave");
	
	private final String binding;
	
	private Binding(String binding) {
		this.binding = binding;
	}
	
	public String getBinding() {
		return binding;
	}
}
