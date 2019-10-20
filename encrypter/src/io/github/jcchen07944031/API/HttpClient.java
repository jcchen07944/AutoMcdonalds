package io.github.jcchen07944031.API;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpClient {

	private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private String cookie = "";

	private OkHttpClient client;
	public HttpClient() {
		client = new OkHttpClient();
	}

	public String get(String url) {
		try {
			Request.Builder builder = new Request.Builder();
			if(!cookie.equals(""))
				builder.addHeader("Cookie", cookie);
			Request request = builder.url(url)
						.get()
						.build();
			Response response = client.newCall(request).execute();
			if(!response.headers("Set-Cookie").isEmpty())
				cookie = response.headers("Set-Cookie").get(0).split(";")[0];
			return response.body().string();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public String post(String url, String json) {
		try {
			RequestBody body = RequestBody.create(JSON, json);
			Request.Builder builder = new Request.Builder();
			if(!cookie.equals(""))
				builder.addHeader("Cookie", cookie);
			Request request = builder.url(url)
						.post(body)
						.build();
			Response response = client.newCall(request).execute();
			if(!response.headers("Set-Cookie").isEmpty())
				cookie = response.headers("Set-Cookie").get(0).split(";")[0];
			System.out.println(cookie);
			return response.body().string();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
