package io.github.jcchen07944031;

public class SourceInfo {
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

	public String convertToJson() {
		return "{" + "\"app_version\": " + "\"" + appVersion + "\"," + 
				"\"device_time\": " + "\"" + date + "\"," +
				"\"device_uuid\": " + "\"" + deviceUUID + "\"," +
				"\"model_id\": " + "\"" + model + "\"," +
				"\"os_version\": " + "\"" + versionRelease + "\"," +
				"\"platform\": " + "\"" + platform + "\"}";
	}
}
