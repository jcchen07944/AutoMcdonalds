//import tw.com.mcddaily.jni.Encrypt;
import io.github.jcchen07944031.API.HttpClient;
import io.github.jcchen07944031.API.RESTful;
import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.Coupon;

public class Main {

	public static void main(String[] argv) {
<<<<<<< HEAD
<<<<<<< HEAD:Backend/src/main/java/Main.java
<<<<<<< HEAD:Backend/src/Main.java
<<<<<<< HEAD
		Account account = new Account("", "");
		account.login();
		account.verifyAccessToken();
		Coupon coupon = new Coupon(account);
		coupon.getLottery();
		
		//System.out.println(Encrypt.encode(null, "9Android SDK built for x86_64e43b4f1d26bcae412019/10/02 00:00:002.2.112345678901234567890e43b4f1d26bcae4120191002000000000Android"));
		//System.out.println(Encrypt.encode(context.get, (new String("12345")).toString()));
=======
=======
		JettyHttpContainerFactory.createServer(URI.create("http://localhost:4067/"), new RestApplication());
=======
>>>>>>> c2aac7f... Using Tomcat to deploy website.:src/main/java/Main.java
/*
>>>>>>> 6740c3e... Using gradle.:Backend/src/main/java/Main.java
=======

>>>>>>> af1535a... Testing mysql.
		Database database = new Database();
		database.updateAccount(new Account("", ""));
		/*
		ArrayList<Account> accountList = database.getAccounts();
		for(int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			account.login();
			database.updateAccount(account);

			Coupon coupon = new Coupon(account);
			coupon.getLottery();
<<<<<<< HEAD:Backend/src/Main.java
		}
>>>>>>> e6e60f3... Remove unuse code.
=======
		}*/
>>>>>>> 6740c3e... Using gradle.:Backend/src/main/java/Main.java
	}

	
}
