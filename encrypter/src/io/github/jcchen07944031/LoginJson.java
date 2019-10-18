package io.github.jcchen07944031;

import io.github.jcchen07944031.SourceInfo;

import java.util.Date;
import java.text.SimpleDateFormat;

public class LoginJson {
	SourceInfo sourceInfo;
	String account;
	String password;
	String orderNo;
	String mask;

	public LoginJson(String account, String password) {
		this.account = account;
		this.password = password;
		this.sourceInfo = new SourceInfo();
		setMask();
	}
		
	public void setMask() {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date mDate = new Date(System.currentTimeMillis());
		sourceInfo.setDate(mSimpleDateFormat.format(mDate));
		mSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.orderNo = sourceInfo.deviceUUID + mSimpleDateFormat.format(mDate) + "000";

		String data = sourceInfo.versionRelease + sourceInfo.model + sourceInfo.deviceUUID + sourceInfo.date + sourceInfo.appVersion + this.account + this.password + this.orderNo + sourceInfo.platform;
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

	public String convertToJson() {
		return "{" + "\"account\": " + "\"" + this.account + "\"," +
				"\"password\": " + "\"" + this.password + "\"," +
				"\"OrderNo\": " + "\"" + this.orderNo + "\"," +
				"\"mask\": " + "\"" + this.mask + "\"," +
				"\"source_info\": " + this.sourceInfo.convertToJson() + "}";
	}
}
