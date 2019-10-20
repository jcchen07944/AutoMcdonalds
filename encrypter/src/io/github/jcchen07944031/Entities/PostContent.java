package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.API.Encrypt;
import io.github.jcchen07944031.Entities.Constants;

import java.util.Date;
import java.text.SimpleDateFormat;

public class PostContent {
	private Constants.POSTCONTENT MODE;

	private String account;
	private String password;
	private String orderNo;
	private String mask;
	private String accessToken;

	private class SourceInfo {
		final String deviceUUID = "e43b4f1d26bcae41";
		final String appVersion = "2.2.1";
		final String versionRelease = "9";
		final String model = "Pixel 3";
		final String platform = "Android";
		String date;

		public SourceInfo() {
	
		}
	
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getJson() {
			return "{" + "\"app_version\": " + "\"" + appVersion + "\"," + 
					"\"device_time\": " + "\"" + date + "\"," +
					"\"device_uuid\": " + "\"" + deviceUUID + "\"," +
					"\"model_id\": " + "\"" + model + "\"," +
					"\"os_version\": " + "\"" + versionRelease + "\"," +
					"\"platform\": " + "\"" + platform + "\"}";
		}
	}
	private SourceInfo sourceInfo;

	public PostContent(Constants.POSTCONTENT MODE) {
		this.sourceInfo = new SourceInfo();
		this.MODE = MODE;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
		
	public void setMask() {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date mDate = new Date(System.currentTimeMillis());
		sourceInfo.setDate(mSimpleDateFormat.format(mDate));
		mSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.orderNo = sourceInfo.deviceUUID + mSimpleDateFormat.format(mDate) + "000";

		String data = sourceInfo.versionRelease + sourceInfo.model + sourceInfo.deviceUUID + sourceInfo.date + sourceInfo.appVersion;
		if(MODE == Constants.POSTCONTENT.MODE_LOGIN) {
			data += this.account + this.password;
		}
		else if(MODE == Constants.POSTCONTENT.MODE_VERIFY_ACCESS_TOKEN) {
			data += this.accessToken;
		}
		else if(MODE == Constants.POSTCONTENT.MODE_LOTTERY_GET) {
			return;
		}
		else if(MODE == Constants.POSTCONTENT.MODE_WEATHER_GET) {
			return;
		}
		data += this.orderNo + sourceInfo.platform;
		
		mask = convertSymbolsToUnicode(Encrypt.encode(null, data));
	}

	private String convertSymbolsToUnicode(String mask) {
		StringBuffer unicode = new StringBuffer();

    		for (int i = 0; i < mask.length(); i++) {
        		char c = mask.charAt(i);
			if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
				String str = Integer.toHexString(c);
				for(int j = str.length(); j < 4; j++) str = "0" + str;
        			unicode.append("\\u" + str);
			}
			else
				unicode.append(c);
    		}
		return unicode.toString();
	}

	public String getJson() {
		setMask();
		String retJson = "";

		if(MODE == Constants.POSTCONTENT.MODE_LOTTERY_GET) {
			return "{" + "\"access_token\": " + "\"" + this.accessToken + "\"," + "\"source_info\": " + this.sourceInfo.getJson() + "}";
		}

		if(MODE == Constants.POSTCONTENT.MODE_WEATHER_GET) {
			return "{" + "\"city\": " + "\"" + "2306179" + "\"" + "}";
		}

		if(MODE == Constants.POSTCONTENT.MODE_LOGIN) {
			retJson += "\"account\": " + "\"" + this.account + "\"," + "\"password\": " + "\"" + this.password + "\",";
		}

		retJson += "\"OrderNo\": " + "\"" + this.orderNo + "\",";

		if(MODE == Constants.POSTCONTENT.MODE_VERIFY_ACCESS_TOKEN) {
			retJson += "\"access_token\": " + "\"" + this.accessToken + "\",";
		}

		return "{" + retJson + "\"mask\": " + "\"" + this.mask + "\"," + "\"source_info\": " + this.sourceInfo.getJson() + "}";
	}
}
