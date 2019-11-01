package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.PostContent;
import io.github.jcchen07944031.Entities.McDAPI;
import io.github.jcchen07944031.Entities.Constants;

import org.json.JSONObject;

public class Coupon {

	private Account account;
	private HttpClient httpClient;
	
	public Coupon(Account account) {
		this.account = account;
		this.httpClient = new HttpClient();
	}

	public int getLottery() {
		/* Get weather */
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_WEATHER_GET);
		String result = httpClient.post(McDAPI.McD_API_WEATHER_GET, postContent.getJson());
		try {
			JSONObject resultJson;
			if(result != "")  {
				resultJson = new JSONObject(result);
				if((int)resultJson.get("rc") != 1) {
					return -2;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		/* Get lottery */
		postContent = new PostContent(Constants.POSTCONTENT.MODE_LOTTERY_GET);
		postContent.setAccessToken(account.getAccessToken());
		result = httpClient.post(McDAPI.McD_API_LOTTERY_GET, postContent.getJson());
		try {
			JSONObject resultJson;
			if(result != "")  {
				resultJson = new JSONObject(result);
				if((int)resultJson.get("rc") == 1) {
					System.out.println(resultJson);
					return 1;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}
}
