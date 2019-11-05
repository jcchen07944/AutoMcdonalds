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

<<<<<<< HEAD
=======
	public Account() {
		this.httpClient = new HttpClient();
		this.username = "";
		this.password = "";
		this.accessToken = "";
	}

<<<<<<< HEAD
>>>>>>> 909c112... Register post test ok.
	public Account(String account, String password) {
=======
	public Account(String username, String password) {
>>>>>>> 7e8ee15... Modify confusing variable name.
		this.httpClient = new HttpClient();
		this.username = username;
		this.password = password;
		this.accessToken = "";
	}

<<<<<<< HEAD
=======
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

>>>>>>> 7e8ee15... Modify confusing variable name.
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

