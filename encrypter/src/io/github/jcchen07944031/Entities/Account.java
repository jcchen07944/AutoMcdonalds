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

	public Account(String account, String password) {
		httpClient = new HttpClient();
		this.account = account;
		this.password = password;
	}

	public String login() {
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
					System.out.println(accessToken);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
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

