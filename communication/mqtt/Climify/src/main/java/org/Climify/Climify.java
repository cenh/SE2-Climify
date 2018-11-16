package org.Climify;

import org.Climify.mariaDB.MariaDBCommunicator;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Climify {
	public static void main(String[] args) {
		ClimifyMqttController controller = new ClimifyMqttController();
		controller.start();
	}

	public void executeRule(String SensorID) {
		MariaDBCommunicator mb = new MariaDBCommunicator();
		List<List<String>> results = new ArrayList<List<String>>();
		results = mb.getRulesBySensorID(SensorID);
		for(List<String> result : results) {
			try {
				URL url = new URL("http://localhost:8080/api/api-execute-rule.php");
				HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod("POST");
				PrintStream ps = new PrintStream(httpCon.getOutputStream());
				ps.print("sensorID=" + result.get(0));
				ps.print("&Operator=" + result.get(1));
				ps.print("&Value=" + result.get(2));
				ps.print("&Action=" + result.get(3));
				httpCon.getInputStream();
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
