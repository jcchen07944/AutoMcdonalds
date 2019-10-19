//import tw.com.mcddaily.jni.Encrypt;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.Account;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Main {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static void main(String[] argv) {
		Account account = new Account("", "");
		account.login();
		account.verifyAccessToken();
		
		//System.out.println(Encrypt.encode(null, "9Android SDK built for x86_64e43b4f1d26bcae412019/10/02 00:00:002.2.112345678901234567890e43b4f1d26bcae4120191002000000000Android"));
		//System.out.println(Encrypt.encode(context.get, (new String("12345")).toString()));
	}

	
}
