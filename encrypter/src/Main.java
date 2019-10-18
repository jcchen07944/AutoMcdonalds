//import tw.com.mcddaily.jni.Encrypt;
import io.github.jcchen07944031.Encrypt;
import io.github.jcchen07944031.LoginJson;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Main {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static void main(String[] argv) {
		Login("12345678", "abcdefgh");
		//System.out.println(Encrypt.encode(null, "9Android SDK built for x86_64e43b4f1d26bcae412019/10/02 00:00:002.2.112345678901234567890e43b4f1d26bcae4120191002000000000Android"));
		//System.out.println(Encrypt.encode(context.get, (new String("12345")).toString()));
	}

	private static String Login(String account, String password) {
		LoginJson loginJson = new LoginJson(account, password);
		System.out.println(loginJson.convertToJson());

		try {
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, loginJson.convertToJson());
			Request request = new Request.Builder()
						.url("https://api.mcddaily.com.tw/login_by_mobile")
						.post(body)
						.build();
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return "";
	}
}
