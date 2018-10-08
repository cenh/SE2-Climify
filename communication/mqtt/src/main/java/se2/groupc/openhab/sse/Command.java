package se2.groupc.openhab.sse;

/**
 * Represents a Command that can be sent to an Item in OpenHAB.
 * @author nch
 *
 */
public class Command {
	
	private String value;
	
	public Command(String value) {
		this.value = value;
	}
	
	public Command(Float value) {
		this.value = value.toString();
	}
	
	public String getValue() {
		return value;
	}

}
