package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.PostContent;
import io.github.jcchen07944031.Entities.McDAPI;
import io.github.jcchen07944031.Entities.Constants;

import org.json.JSONObject;

public class Account {

	private HttpClient httpClient;
	private String account;
	private String password;
	private String accessToken;

<<<<<<< HEAD
=======
	public Account() {
		this.httpClient = new HttpClient();
		this.account = "";
		this.password = "";
		this.accessToken = "";
	}

>>>>>>> 909c112... Register post test ok.
	public Account(String account, String password) {
		this.httpClient = new HttpClient();
		this.account = account;
		this.password = password;
		this.accessToken = "";
	}

	public String getAccessToken() {
		return accessToken;
	}

<<<<<<< HEAD
	public String login() {
=======
	public boolean login() {
		if(verifyAccessToken())
			return true;

>>>>>>> 4655834... Adjust login function.
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_LOGIN);
		postContent.setAccount(account);
		postContent.setPassword(password);
		String result = httpClient.post(McDAPI.McD_API_LOGIN, postContent.getJson());
		try {
			JSONObject resultJson;
			if(result != "")  {
				resultJson = new JSONObject(result);
				if(((String)resultJson.get("rc")).equals("1")) {
					accessToken = (String)resultJson
								.getJSONObject("results")
								.getJSONObject("member_info")
								.get("access_token");
<<<<<<< HEAD
					System.out.println(accessToken);
=======
					return true;
>>>>>>> 4655834... Adjust login function.
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
<<<<<<< HEAD
		return "";
=======
		return false;
>>>>>>> 4655834... Adjust login function.
	}

	public String logout() {
		return "";
	}

	public boolean verifyAccessToken() {
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_VERIFY_ACCESS_TOKEN);
		postContent.setAccessToken(accessToken);
		String result = httpClient.post(McDAPI.McD_API_VERIFY_ACCESS_TOKEN, postContent.getJson());
		try {
			JSONObject resultJson;
			if(result != "")  {
				resultJson = new JSONObject(result);
				if(((String)resultJson.get("rc")).equals("1")) {
					return true;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}

