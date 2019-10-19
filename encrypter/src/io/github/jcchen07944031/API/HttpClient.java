package io.github.jcchen07944031.API;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpClient {

	private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private OkHttpClient client;
	public HttpClient() {
		client = new OkHttpClient();
	}

	public String get(String url) {
		try {
			Request request = new Request.Builder()
						.url(url)
						.get()
						.build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public String post(String url, String json) {
		try {
			RequestBody body = RequestBody.create(JSON, json);
			Request request = new Request.Builder()
						.url(url)
						.post(body)
						.build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
