package io.github.jcchen07944031.Entities;

public class McDAPI {
	
	public static final String McD_API_BASE_URL = "https://api.mcddaily.com.tw";
	public static final String McD_API_BASE_URL_1 = "https://api1.mcddailyapp.com";

	public static final String McD_API_LOGIN = McD_API_BASE_URL + "/login_by_mobile";
	public static final String McD_API_VERIFY_ACCESS_TOKEN = McD_API_BASE_URL + "/verify_member_access_token";

	public static final String McD_API_WEATHER_GET = McD_API_BASE_URL_1 + "/weather/get_status";
	public static final String McD_API_LOTTERY_GET = McD_API_BASE_URL_1 + "/lottery/get_item";

	public static final String McD_API_COUPON_GET_LIST = McD_API_BASE_URL_1 + "/coupon/get_list";
	public static final String McD_API_STICKER_GET_LIST = McD_API_BASE_URL_1 + "/sticker/get_list";
}
