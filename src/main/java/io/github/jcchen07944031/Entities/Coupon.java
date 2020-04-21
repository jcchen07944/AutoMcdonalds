package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.History;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.PostContent;
import io.github.jcchen07944031.Entities.McDAPI;
import io.github.jcchen07944031.Entities.Constants;

import org.json.JSONObject;
import org.json.JSONArray;


import java.util.ArrayList;

public class Coupon {

	private Account account;
	private HttpClient httpClient;
	
	public Coupon(Account account) {
		this.account = account;
		this.httpClient = new HttpClient();
	}

	public History getLottery() {
		/* Get weather */
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_WEATHER_GET);
		String result = httpClient.post(McDAPI.McD_API_WEATHER_GET, postContent.getJson());
		try {
			JSONObject resultJson;
			if(result != "")  {
				resultJson = new JSONObject(result);
				if((int)resultJson.get("rc") != 1) {
					return null;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		/* Get lottery */
		postContent = new PostContent(Constants.POSTCONTENT.MODE_LOTTERY_GET);
		postContent.setAccessToken(account.getAccessToken());
		postContent.setDeviceUUID(account.getDeviceUUID());
		postContent.setModel(account.getModel());
		result = httpClient.post(McDAPI.McD_API_LOTTERY_GET, postContent.getJson());
		try {
			JSONObject resultJson;
			if(result != "")  {
				resultJson = new JSONObject(result);
				if((int)resultJson.get("rc") == 1) {
					System.out.println(resultJson);
					String type = resultJson.getJSONObject("results").keys().next();
					if((int)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get("object_id") != 2147483647) {
						History history = new History();
						history.setObjectID((int)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get("object_id") + "");
						history.setTitle((String)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get("title"));
						history.setEndDateTime((String)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get(type.equals("coupon") ? "redeem_end_datetime" : "expire_datetime"));
						history.setImgUrl((String)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").getJSONObject("image").get("url"));
						history.setID((int)resultJson.getJSONObject("results").getJSONObject(type).get(type + "_id") + "");
						history.setType(type);
						history.setStatus((int)resultJson.getJSONObject("results").getJSONObject(type).get("status") + "");
						return history;
					}
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<History> getCouponList() {
		try {
			ArrayList<History> historyList = new ArrayList<History>();
			
			/* Get Coupon */
			PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_COUPON_GET_LIST);
			postContent.setAccessToken(account.getAccessToken());
			postContent.setDeviceUUID(account.getDeviceUUID());
			postContent.setModel(account.getModel());
			String result = httpClient.post(McDAPI.McD_API_COUPON_GET_LIST, postContent.getJson());
			if(result != "")  {
				JSONObject resultJson = new JSONObject(result);
				if((int)resultJson.get("rc") == 1) {
					System.out.println(resultJson);
					JSONArray coupons = resultJson.getJSONObject("results").getJSONArray("coupons");
					for(int i = 0; i < coupons.length(); i++) {
						History history = new History();
						history.setObjectID((int)coupons.getJSONObject(i).getJSONObject("object_info").get("object_id") + "");
						history.setTitle((String)coupons.getJSONObject(i).getJSONObject("object_info").get("title"));
						history.setEndDateTime((String)coupons.getJSONObject(i).getJSONObject("object_info").get("redeem_end_datetime"));
						history.setImgUrl((String)coupons.getJSONObject(i).getJSONObject("object_info").getJSONObject("image").get("url"));
						history.setID((int)coupons.getJSONObject(i).get("coupon_id") + "");
						history.setType("coupon");
						history.setStatus((int)coupons.getJSONObject(i).get("status") + "");
						historyList.add(history);
					}
				}
			}
			
			/* Get Sticker */
			postContent = new PostContent(Constants.POSTCONTENT.MODE_STICKER_GET_LIST);
			postContent.setAccessToken(account.getAccessToken());
			postContent.setDeviceUUID(account.getDeviceUUID());
			postContent.setModel(account.getModel());
			result = httpClient.post(McDAPI.McD_API_STICKER_GET_LIST, postContent.getJson());
			if(result != "")  {
				JSONObject resultJson = new JSONObject(result);
				if((int)resultJson.get("rc") == 1) {
					System.out.println(resultJson);
					JSONArray stickers = resultJson.getJSONObject("results").getJSONArray("stickers");
					for(int i = 0; i < stickers.length(); i++) {
						History history = new History();
						history.setObjectID((int)stickers.getJSONObject(i).getJSONObject("object_info").get("object_id") + "");
						history.setTitle((String)stickers.getJSONObject(i).getJSONObject("object_info").get("title"));
						history.setEndDateTime((String)stickers.getJSONObject(i).getJSONObject("object_info").get("expire_datetime"));
						history.setImgUrl((String)stickers.getJSONObject(i).getJSONObject("object_info").getJSONObject("image").get("url"));
						history.setID((int)stickers.getJSONObject(i).get("sticker_id") + "");
						history.setType("sticker");
						history.setStatus((int)stickers.getJSONObject(i).get("status") + "");
						historyList.add(history);
					}
				}
			}
			return historyList;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public History redeemSticker() {
		ArrayList<History> historyList = getCouponList();
		ArrayList<History> stickerList = new ArrayList<History>();
		// Check the number of stickers.
		int cnt = 0;
		for(int i = 0; i < historyList.size(); i++) {
			if(historyList.get(i).getType().equals("sticker")) {
				stickerList.add(historyList.get(i));
				cnt++;
			}
		}
		if(cnt < 6)
			return null;
		
		PostContent postContent = new PostContent(Constants.POSTCONTENT.MODE_STICKER_REDEEM);
		postContent.setAccessToken(account.getAccessToken());
		postContent.setDeviceUUID(account.getDeviceUUID());
		postContent.setModel(account.getModel());
		for(int i = 0; i < 6; i++)
			postContent.addRedeemSticker(stickerList.get(i).getID());
		
		String result = httpClient.post(McDAPI.McD_API_STICKER_REDEEM, postContent.getJson());
		if(result != "")  {
			JSONObject resultJson = new JSONObject(result);
			if((int)resultJson.get("rc") == 1) {
				History history = new History();
				history.setObjectID((int)resultJson.getJSONObject("results").getJSONObject("coupon").getJSONObject("object_info").get("object_id") + "");
				history.setTitle((String)resultJson.getJSONObject("results").getJSONObject("coupon").getJSONObject("object_info").get("title"));
				history.setEndDateTime((String)resultJson.getJSONObject("results").getJSONObject("coupon").getJSONObject("object_info").get("redeem_end_datetime"));
				history.setImgUrl((String)resultJson.getJSONObject("results").getJSONObject("coupon").getJSONObject("object_info").getJSONObject("image").get("url"));
				history.setID((int)resultJson.getJSONObject("results").getJSONObject("coupon").get("coupon_id") + "");
				history.setType("coupon");
				history.setStatus((int)resultJson.getJSONObject("results").getJSONObject("coupon").get("status") + "");
				return history;
			}
		}
		return null;
	}
}
