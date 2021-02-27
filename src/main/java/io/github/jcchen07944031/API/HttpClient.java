package io.github.jcchen07944031.API;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

	private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private String cookie = "";

	private OkHttpClient client;
	public HttpClient() {
		client = new OkHttpClient();
	}

	public String get(String url) {
		int reconnectCount = 0;
		do {
			try {
				if(reconnectCount > 0)
					Thread.sleep(30000);
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
		} while(reconnectCount++ < 5);
		return "";
	}

	public String post(String url, String json) {
		int reconnectCount = 0;
		do {
			try {
				RequestBody body = RequestBody.create(JSON, json);
				Request.Builder builder = new Request.Builder();
				if(!cookie.equals(""))
					builder.addHeader("Cookie", cookie);
				Request request = builder.url(url)
							.header("User-Agent", "okhttp/3.12.1")
							.post(body)
							.build();
				Response response = client.newCall(request).execute();
				if(!response.headers("Set-Cookie").isEmpty())
					cookie = response.headers("Set-Cookie").get(0).split(";")[0];
				return response.body().string();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} while(reconnectCount++ < 5);
		return "";
	}
}
