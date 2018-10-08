package se2.groupc.openhab.sse;

import java.io.IOException;

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

	public void sendCommandToItem(String item, Command command) {
		String commandUrl = restBaseUrl + "items/" + item;
		System.out.println(commandUrl);

		Headers headers = Utilities.getHeaders(HeaderType.JSON, HeaderType.TEXTPLAIN);

		RequestBody body = RequestBody.create(null, command.getValue());

		Request request = new Request.Builder()
				.url(commandUrl)
				.headers(headers)
				.post(body)
				.build();

		System.out.println("Request: " + request.toString());
		System.out.println("Headers: " + request.headers().toString());

		try {
			Response response = client.newCall(request).execute();
			System.out.println("Response from command to item: " + response.code() + " " + response.message());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
