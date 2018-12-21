package org.RaspberryPi.openhab;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.MqttLib.openhab.Command;
import org.MqttLib.openhab.CreateItem;
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
	
	public String getThing(String thing) throws IOException {
		String thingUrl = restBaseUrl + "things/" + thing;
		Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(thingUrl)
            .headers(headers)
            .get()
            .build();
        
		Response response = client.newCall(request).execute();
		String responseMessage = response.body().string();
		System.out.println(responseMessage);
		return responseMessage;//response.body().string();
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
	
	public void startDiscovery(String binding) throws IOException {
		String discoveryUrl = restBaseUrl + "discovery/bindings/" + binding + "/scan";
		System.out.println(discoveryUrl);

		Headers headers = Utilities.getHeaders(HeaderType.TEXTPLAIN);

		RequestBody body = RequestBody.create(null, "");

		Request request = new Request.Builder()
				.url(discoveryUrl)
				.headers(headers)
				.post(body)
				.build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("Response from startDiscovery on binding " + binding + ": " + response.code() + " " + response.message());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String removeThing(String thingUID) throws IOException {
		String thingUrl = restBaseUrl + "things/" + thingUID;
        Headers headers = Utilities.getHeaders(HeaderType.JSON);
        
        Request request = new Request.Builder()
            .url(thingUrl)
            .headers(headers)
            .delete()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String removeItem(String itemName) throws IOException {
		String itemUrl = restBaseUrl + "items/" + itemName;
		
        Headers headers = Utilities.getHeaders(HeaderType.JSON);
        
        Request request = new Request.Builder()
            .url(itemUrl)
            .headers(headers)
            .delete()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String removeLink(String itemName, String channelUID) throws IOException {
		String linkUrl = restBaseUrl + "links/" + itemName + "/" + channelUID;
		
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(linkUrl)
            .headers(headers)
            .delete()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String getInbox() throws IOException {
		String inboxUrl = restBaseUrl + "inbox/";
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        Request request = new Request.Builder()
            .url(inboxUrl)
            .headers(headers)
            .get()
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String approveThing(String thingUID) throws IOException {
		String thingUrl = restBaseUrl + "inbox/" + thingUID + "/approve";
		
        Headers headers = Utilities.getHeaders(HeaderType.TEXTPLAIN, HeaderType.JSON);

        RequestBody body = RequestBody.create(null, "");
        
        Request request = new Request.Builder()
            .url(thingUrl)
            .headers(headers)
            .post(body)
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String addLink(String itemName, String channelUID) throws IOException {
		String linkUrl = restBaseUrl + "links/" + itemName + "/" + channelUID;
		
        Headers headers = Utilities.getHeaders(HeaderType.JSON);

        RequestBody body = RequestBody.create(null, "");
        
        Request request = new Request.Builder()
            .url(linkUrl)
            .headers(headers)
            .put(body)
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
	
	public String addItem(CreateItem createItem) throws IOException {
		String itemUrl = restBaseUrl + "items/" + createItem.name;
		
        Headers headers = Utilities.getHeaders(HeaderType.JSON);
        
        Map<String, String> values = new HashMap<String, String>();
        values.put("name", createItem.name);
        values.put("type", createItem.type);
        values.put("category", createItem.category);
        values.put("label", createItem.label);
        
        RequestBody body = RequestBody.create(null, Utilities.createJSON(values));
        
        Request request = new Request.Builder()
            .url(itemUrl)
            .headers(headers)
            .put(body)
            .build();

        Response response = client.newCall(request).execute();
        
        return response.body().string();
	}
}
