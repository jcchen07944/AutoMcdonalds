//import tw.com.mcddaily.jni.Encrypt;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.Coupon;

public class Main {

	public static void main(String[] argv) {
<<<<<<< HEAD
		Account account = new Account("", "");
		account.login();
		account.verifyAccessToken();
		Coupon coupon = new Coupon(account);
		coupon.getLottery();
		
		//System.out.println(Encrypt.encode(null, "9Android SDK built for x86_64e43b4f1d26bcae412019/10/02 00:00:002.2.112345678901234567890e43b4f1d26bcae4120191002000000000Android"));
		//System.out.println(Encrypt.encode(context.get, (new String("12345")).toString()));
=======
		Database database = new Database();
		
		ArrayList<Account> accountList = database.getAccounts();
		for(int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			account.login();
			database.updateAccount(account);

			Coupon coupon = new Coupon(account);
			coupon.getLottery();
		}
>>>>>>> e6e60f3... Remove unuse code.
	}

	
}
