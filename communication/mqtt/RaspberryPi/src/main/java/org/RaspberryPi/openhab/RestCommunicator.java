package org.RaspberryPi.openhab;

import java.io.IOException;

import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.HeaderType;
import org.MqttLib.openhab.Utilities;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Responsible for communicating with OpenHAB using the REST API.
 * @author nch
 * 
 */

public class RestCommunicator {

	private String restBaseUrl = "http://localhost:8080/rest/";
	private OkHttpClient client = new OkHttpClient();

	public void sendCommandToItem(Command command) {
		String commandUrl = restBaseUrl + "items/" + command.getName();
		System.out.println(commandUrl);

		Headers headers = Utilities.getHeaders(HeaderType.JSON, HeaderType.TEXTPLAIN);

		RequestBody body = RequestBody.create(null, command.getValue());

		Request request = new Request.Builder()
				.url(commandUrl)
				.headers(headers)
				.post(body)
				.build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("Response from command to item: " + response.code() + " " + response.message());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getItem(String item) throws IOException {
		String commandUrl = restBaseUrl + "items/" + item;
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(commandUrl)
            .headers(headers)
            .get()
            .build();
        
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	
	public String getAllItems() throws IOException {
        String commandUrl = restBaseUrl + "items/" + "?recursive=false&fields=name%2C%20category%2C%20stateDescription";
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(commandUrl)
            .headers(headers)
            .get()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
    }
	
	public String getAllThings() throws IOException {
		String commandUrl = restBaseUrl + "things/";
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(commandUrl)
            .headers(headers)
            .get()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String getAllLinks() throws IOException {
		String commandUrl = restBaseUrl + "links/";
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(commandUrl)
            .headers(headers)
            .get()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
}
