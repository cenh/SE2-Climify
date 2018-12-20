package org.RaspberryPi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

/**
 * Singleton handling retrieving the Raspberry Pi's unique identifier & locationID from file.
 * The file setup.txt is expected to be there already with the format = locationID:uid
 * If it is the first time the Raspberry Pi is being run the unique identifier should be empty and it will be created and written to the end of the file.
 * @author nch
 *
 */
public class RaspberryPiSetup {
	
	private static volatile RaspberryPiSetup instance;
	private String uid;
	private int locationID;
	private String filePath = "/home/pi/files/setup.txt";
	private Boolean isNew = false;
	
	private RaspberryPiSetup() {
		if (instance != null){
            throw new RuntimeException("No reflection here buddy. Use getInstance()");
        }
		
		if (fileExist()) {
			getValuesFromFile();
			if (uid == null) {
				uid = createUID();
				isNew = true;
			}
		}
		else {
			throw new RuntimeException("The setup.txt file was not found.");
		}
	}
	
	public static RaspberryPiSetup getInstance(){
	    if(instance == null){
	        synchronized (RaspberryPiSetup.class) {
	            if(instance == null){
	                instance = new RaspberryPiSetup();
	            }
	        }
	    }
	    return instance;
	}
	
	public String getUID() {
		return uid;
	}
	
	public int getLocationID() {
		return locationID;
	}
	
	public Boolean isNew() {
		return isNew;
	}
	
	private void getValuesFromFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath));
			if (!lines.isEmpty()) {
				String line = lines.get(0);
				String[] values = line.split(":");
				locationID = Integer.parseInt(values[0]);
				if(values.length == 2) {
					uid = values[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String createUID() {
		String uid = generateUID();
		
		try {
			Files.write(Paths.get(filePath), uid.getBytes("UTF-8"), StandardOpenOption.APPEND);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return uid;
	}
	
	private String generateUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	private Boolean fileExist() {
		return Files.exists(Paths.get(filePath), LinkOption.NOFOLLOW_LINKS);
	}
	
}
