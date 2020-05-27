# AutoMcdonalds  
Make mcddaily run automatically on pc.   
http://jcchen.csie.org:9010 (For testing only, do not have auto draw lottery feature and not always online.)

## Newest encryption algorithm (since version 2.2.1) 
``` java
InputData = Build.VERSION.RELEASE + Build.MODEL + DeviceUUID + yyyy/MM/dd HH:mm:ss + appVersion + account + password + OrderNo + "Android"  
PreProcessData = "McD" + InputData + "App"  
DESEncrypt = DES(MD5(PreProcessData), key, iv)  
Base64Encode = base64_encode(DESEncrypt)  
Encrypt.encode = Base64Encode[0:4] + MD5(PreProcessData) + Base64Encode[len-4:len]  
``` 

## Documentation

*  Account
``` java
/* Variable (Using Get & Set) */
String username;
String password;
String accessToken;
String keyword; // Use to link accounts(If two accounts set the same keyword, means these accounts belong to one person.)
String deviceUUID; // Provide randomly
String model; // Provide randomly

/* Account constructor */
Account account = new Account(); // Empty account.
Account account = new Account(username, password); // Account with username & password.

/* Login */
boolean state = account.login(); // TRUE if login success, FALSE if failed.
String accessToken = account.getAccessToken(); // After login, can get accessToken.

/* Verify Access Token */
account.setAccessToken(accessToken);
boolean state = account.verifyAccessToken(); // TRUE if accessToken is valid, FALSE if not.
```

*  Coupon
``` java
/* Coupon constructor */
Coupon coupon = new Coupon(account); // Before using coupon, need to add an Account and login.

/* Draw lottery */
History history = coupon.getLottery(); // If success, will return a Histroy object, else, return NULL.

/* Get user coupon list */
ArrayList<History> historyList = coupon.getCouponList(); // Including coupons and stickers.

/* Redeem stickers */
History history = coupon.redeemSticker(); // If redeem success, return a History object, else, return NULL.
```

*  Database  
*Note that it use MySQL as database, need to construct MySQL tables first by yourself.*
``` java
/* Database constructor */
Database database = new Database();

/* Add or update Account */
database.updateAccount(account);

/* Delete account */
database.deleteAccount(account);

/* Get all accounts */
ArrayList<Account> accountList = database.getAccounts();

/* Find account by username */
Account account = database.findAccountByUsername(username);

/* Get accounts list that belong to same keyword */
ArrayList<Account> accountList = database.findAccountByKeyword(keyword);

/* Save a History to an Account */
database.saveHistory(account, history);

/* Replace all History in an Account */
database.updateHistory(account, historyList); // Will first delete all history in this account and replace by historyList.

/* Delete all History in an Account */
database.deleteHistory(account);

/* Get all History of an Account */
ArrayList<History> historyList = database.getHistory(account);
```

*  History
``` java
/* Variable (Using Get & Set) */
String ID;
String objectID;
String endDateTime;
String title;
String imgUrl;
String type;
String status;
String timeStamp;

/* Get string in json type */
String historyJson = history.getJson();
```
