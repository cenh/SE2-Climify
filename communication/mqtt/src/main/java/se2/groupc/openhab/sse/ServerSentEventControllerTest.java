package se2.groupc.openhab.sse;

public class ServerSentEventControllerTest {

	public static void main(String[] args) {
		ServerSentEventController sseController = new ServerSentEventController();
		sseController.start();
		
		RestCommunicator rest = new RestCommunicator();
		rest.sendCommandToItem("TestSwitch", new Command("OFF"));
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
