//import tw.com.mcddaily.jni.Encrypt;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.API.RESTful;
import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.Coupon;
import io.github.jcchen07944031.Entities.Database;

import java.util.ArrayList;


public class Main {

	public static void main(String[] argv) {
		Database database = new Database();
		//database.deleteAccount(new Account("0902389735", "jack841008"));
		ArrayList<Account> accountList = database.getAccounts();
		for(int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			if(account.login()) {
				database.updateAccount(account);
				Coupon coupon = new Coupon(account);
				coupon.getLottery();
			}
		}
	}
}
