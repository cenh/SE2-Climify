package org.Climify;

public class Climify {
	public static void main(String[] args) {
		ClimifyMqttController controller = new ClimifyMqttController();
		controller.start();
	}
}
