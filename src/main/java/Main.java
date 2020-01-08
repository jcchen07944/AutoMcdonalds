//import tw.com.mcddaily.jni.Encrypt;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.API.RESTful;
import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.Coupon;
import io.github.jcchen07944031.Entities.Database;
import io.github.jcchen07944031.Entities.History;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Thread;

import org.json.JSONObject;

public class Main {

	public static void main(String[] argv) {
		Database database = new Database();
		Random rand = new Random();

		//Account account = new Account("", "");
		//database.updateAccount(account);
		//account = new Account("", "");
		//database.updateAccount(account);
		//String testResult = "{\"rc\":1,\"rm\":\"恭喜您！限時獨家優惠券到手\",\"results\":{\"coupon\":{\"coupon_id\":1084495794,\"object_info\":{\"image\":{\"width\":1080,\"url\":\"https://mcdapp1.azureedge.net/P_G181.jpg\",\"height\":1920},\"title\":\"買任一超值全餐送蘋果派_G181\",\"redeem_end_datetime\":\"2019/11/09 23:59:59\",\"object_id\":302},\"type\":\"coupon\",\"status\":1}}}";

		/*String testResult = "{\"rc\":1,\"rm\":\"歡樂貼入手！集滿 6 張可兌換優惠券！\",\"results\":{\"sticker\":{\"sticker_id\":519452120,\"object_info\":{\"image\":{\"width\":300,\"url\":\"https://mcdapp1.azureedge.net/sticker_01.png\",\"height\":300},\"title\":\"歡樂貼 (0)\",\"expire_datetime\":\"2019/12/31 23:59:59\",\"object_id\":18},\"obtain_datetime\":\"2019/11/08 02:26:06\",\"type\":\"sticker\",\"status\":1}}}";
		JSONObject resultJson = new JSONObject(testResult);

		String type = resultJson.getJSONObject("results").keys().next();
		History history = new History();
		history.setObjectID((int)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get("object_id") + "");
		history.setTitle((String)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get("title"));
		history.setEndDateTime((String)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").get(type.equals("coupon") ? "redeem_end_datetime" : "expire_datetime"));
		history.setImgUrl((String)resultJson.getJSONObject("results").getJSONObject(type).getJSONObject("object_info").getJSONObject("image").get("url"));
		history.setID((int)resultJson.getJSONObject("results").getJSONObject(type).get(type + "_id") + "");
		history.setType((String)resultJson.getJSONObject("results").getJSONObject(type).get("type"));
		history.setStatus((int)resultJson.getJSONObject("results").getJSONObject(type).get("status") + "");*/

		ArrayList<Account> accountList = database.getAccounts();
		for(int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			//account = accountList.get(i);
			if(account.login()) {
				database.updateAccount(account);
				Coupon coupon = new Coupon(account);
				database.saveHistory(account, coupon.getLottery());
				database.updateHistory(account, coupon.getCouponList());
				try {
					//Thread.sleep(rand.nextInt(120000));
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
