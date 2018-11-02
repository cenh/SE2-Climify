package org.RaspberryPi.openhab;

import java.io.IOException;

public class SendItemsToServerTask implements Runnable {

	RestCommunicator rest = new RestCommunicator();

	@Override
	public void run() {
		try {
			String test = rest.getAllItems();
			StringBuilder itemsBuilder = new StringBuilder(test);
			itemsBuilder.setCharAt(0, '{');
			itemsBuilder.insert(1, "items:");
			itemsBuilder.setCharAt(itemsBuilder.length() - 1, '}');
			System.out.println(itemsBuilder);
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
