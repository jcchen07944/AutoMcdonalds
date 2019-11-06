package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.PostContent;
import io.github.jcchen07944031.Entities.McDAPI;
import io.github.jcchen07944031.Entities.Constants;

import org.json.JSONObject;

public class Account {

	private HttpClient httpClient;
	private String username;
	private String password;
	private String accessToken;
	private String keyword;

	public Account() {
		this.httpClient = new HttpClient();
		this.username = "";
		this.password = "";
		this.accessToken = "";
		this.keyword = "";
	}
	public Account(String username, String password) {
		this.httpClient = new HttpClient();
		this.username = username;
		this.password = password;
		this.accessToken = "";
		this.keyword = "";
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setKeyword(String keyword)  {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public boolean login() {
		if(verifyAccessToken())
			return true;

		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_LOGIN);
		postContent.setUsername(username);
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
					return true;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
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

