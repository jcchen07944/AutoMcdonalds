package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.PostContent;
import io.github.jcchen07944031.Entities.McDAPI;
import io.github.jcchen07944031.Entities.Constants;

import org.json.JSONObject;

import java.util.Random;
import java.util.UUID;

public class Account {

	private HttpClient httpClient;
	private String username;
	private String password;
	private String accessToken;
	private String keyword;

	private String deviceUUID;
	private String model;

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

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public boolean login() {
		if(verifyAccessToken())
			return true;

		setDeviceUUID(getRandomUUID());
		setModel(getRandomModel());
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_LOGIN);
		postContent.setUsername(username);
		postContent.setPassword(password);
		postContent.setDeviceUUID(deviceUUID);
		postContent.setModel(model);
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
		if(accessToken.equals(""))
			return false;
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_VERIFY_ACCESS_TOKEN);
		postContent.setAccessToken(accessToken);
		postContent.setDeviceUUID(deviceUUID);
		postContent.setModel(model);
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

	private String getRandomUUID() {
		UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		String str = uid.randomUUID() + "";
		return str.split("-")[3] + str.split("-")[4];
	}

	private String getRandomModel() {
		Random random = new Random();
		String[] models = {"Pixel 3", "Samsung s10", "HTC U-3u", "Sony Z5", "Zenfone 5z", "HTC U-1u", "Pixel 3 XL"};
		return models[random.nextInt(7)];
	}
}

