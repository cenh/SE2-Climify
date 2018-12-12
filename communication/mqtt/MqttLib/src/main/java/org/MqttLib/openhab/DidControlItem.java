package org.MqttLib.openhab;

public class DidControlItem extends ControlItem {
	public Item item;
	
	public DidControlItem(ControlType controlType, String uid, String channelUID, Item item) {
		this.controlType = controlType;
		this.uid = uid;
		this.channelUID = channelUID;
		this.item = item;
	}
}
