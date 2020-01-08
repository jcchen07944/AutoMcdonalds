package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.History;
import io.github.jcchen07944031.Entities.Constants;

import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Database {
	
	private String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/AutoMcdonalds?user=" + Constants.MYSQL_USERNAME + "&password=" + Constants.MYSQL_PASSWORD + "&characterEncoding=utf-8";

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;


	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(mysqlUrl);
			statement = connection.createStatement();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	public void updateAccount(Account account) {
		String username = AESEncrypt(account.getUsername(), Constants.DATABASE_USERNAME_AES_KEY);
		String password = AESEncrypt(account.getPassword(), Constants.DATABASE_PASSWORD_AES_KEY);
		String accessToken = AESEncrypt(account.getAccessToken(), Constants.DATABASE_ACCESSTOKEN_AES_KEY);
		String keyword = AESEncrypt(account.getKeyword(), Constants.DATABASE_KEYWORD_AES_KEY);
		String deviceUUID = account.getDeviceUUID();
		String model = account.getModel();
		String sql = "INSERT INTO account(`username`, `password`, `access_token`, `keyword`, `device_uuid`, `model`) " +
				"VALUES ('" + username + "', '" + password + "', '" + accessToken + "', '" + keyword + "', '" + deviceUUID + "', '" + model + "') " + 
				"ON DUPLICATE KEY UPDATE password = '" + password + "', access_token = '" + accessToken + "', keyword = '" + keyword + "', device_uuid = '" + deviceUUID + "', model = '" + model + "'";

		try {
			statement.execute(sql);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	public void deleteAccount(Account account) {
		String username = AESEncrypt(account.getUsername(), Constants.DATABASE_USERNAME_AES_KEY);
		String sqlDelete = "DELETE FROM account WHERE username='" + username + "'";
		deleteHistory(account);
		try {
			statement.execute(sqlDelete);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<Account> getAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();

		String sql = "SELECT * FROM account";
		try {
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				Account account = new Account();
				account.setUsername(AESDecrypt(resultSet.getString("username"), Constants.DATABASE_USERNAME_AES_KEY));
				account.setPassword(AESDecrypt(resultSet.getString("password"), Constants.DATABASE_PASSWORD_AES_KEY));
				account.setAccessToken(AESDecrypt(resultSet.getString("access_token"), Constants.DATABASE_ACCESSTOKEN_AES_KEY));
				account.setKeyword(AESDecrypt(resultSet.getString("keyword"), Constants.DATABASE_KEYWORD_AES_KEY));
				account.setDeviceUUID(resultSet.getString("device_uuid"));
				account.setModel(resultSet.getString("model"));
				accountList.add(account);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return accountList;
	}

	public Account findAccountByUsername(String username) {
		username = AESEncrypt(username, Constants.DATABASE_USERNAME_AES_KEY);
		String sql = "SELECT * FROM account WHERE username='" + username + "'";
		try {
			resultSet = statement.executeQuery(sql);
			if(!resultSet.next())
				return null;
			Account account = new Account();
			account.setUsername(AESDecrypt(resultSet.getString("username"), Constants.DATABASE_USERNAME_AES_KEY));
			account.setPassword(AESDecrypt(resultSet.getString("password"), Constants.DATABASE_PASSWORD_AES_KEY));
			account.setAccessToken(AESDecrypt(resultSet.getString("access_token"), Constants.DATABASE_ACCESSTOKEN_AES_KEY));
			account.setKeyword(AESDecrypt(resultSet.getString("keyword"), Constants.DATABASE_KEYWORD_AES_KEY));
			account.setDeviceUUID(resultSet.getString("device_uuid"));
			account.setModel(resultSet.getString("model"));
			return account;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public ArrayList<Account> findAccountByKeyword(String keyword) {
		keyword = AESEncrypt(keyword, Constants.DATABASE_KEYWORD_AES_KEY);
		String sql = "SELECT * FROM account WHERE keyword='" + keyword + "'";
		
		try {
			resultSet = statement.executeQuery(sql);
			ArrayList<Account> accountList = new ArrayList<Account>();
			while(resultSet.next()) {
				Account account = new Account();
				account.setUsername(AESDecrypt(resultSet.getString("username"), Constants.DATABASE_USERNAME_AES_KEY));
				account.setPassword(AESDecrypt(resultSet.getString("password"), Constants.DATABASE_PASSWORD_AES_KEY));
				account.setAccessToken(AESDecrypt(resultSet.getString("access_token"), Constants.DATABASE_ACCESSTOKEN_AES_KEY));
				account.setKeyword(AESDecrypt(resultSet.getString("keyword"), Constants.DATABASE_KEYWORD_AES_KEY));
				account.setDeviceUUID(resultSet.getString("device_uuid"));
				account.setModel(resultSet.getString("model"));
				accountList.add(account);
			}
			return accountList;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	
	public void saveHistory(Account account, History history) {
		if(history == null)
			return;
		String username = AESEncrypt(account.getUsername(), Constants.DATABASE_USERNAME_AES_KEY);

		String sqlUpdate = "INSERT INTO statistics(`object_id`, `title`, `image_url`, `count`) " +
					"VALUES ('" + history.getObjectID() + "', '" + history.getTitle() + "', '" + history.getImgUrl() + "', " + 1 + ") " + 
					"ON DUPLICATE KEY UPDATE image_url = '" + history.getImgUrl() + "', count = count + 1";

		try {
			statement.execute(sqlUpdate);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateHistory(Account account, ArrayList<History> historyList) {
		if(historyList == null)
			return;
		
		String username = AESEncrypt(account.getUsername(), Constants.DATABASE_USERNAME_AES_KEY);
		String sqlCreate = "CREATE TABLE IF NOT EXISTS `" + username + "` (" +
				"`id` VARCHAR(15), " + 
				"`object_id` VARCHAR(15), " +
				"`type` VARCHAR(50), " +
				"`title` VARCHAR(100), " +
				"`image_url` VARCHAR(100), " +
				"`end_datetime` VARCHAR(50), " +
				"`status` VARCHAR(15), " +
				"`time_stamp` TIMESTAMP, " +
				"PRIMARY KEY (`id`)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
		
		try {
			deleteHistory(account);
			statement.execute(sqlCreate);
			for(int i = 0; i < historyList.size(); i++) {
				History history = historyList.get(i);
				String sqlInsert = "INSERT INTO `" + username + "`(`id`, `object_id`, `type`, `title`, `image_url`, `end_datetime`, `status`) " +
						"VALUES ('" + history.getID() + "', '" + history.getObjectID() + "', '" + history.getType() + "', '" + 
							history.getTitle() + "', '" + history.getImgUrl() + "', '" + history.getEndDateTime() + "', '" + history.getStatus() + "') " +
						"ON DUPLICATE KEY UPDATE id = '" + history.getID() + "'";
				statement.execute(sqlInsert);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void deleteHistory(Account account) {
		String username = AESEncrypt(account.getUsername(), Constants.DATABASE_USERNAME_AES_KEY);
		String sqlDrop = "DROP TABLE IF EXISTS `" + username + "`";
		
		try {
			statement.execute(sqlDrop);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<History> getHistory(Account account) {
		ArrayList<History> historyList = new ArrayList<History>();
		String username = AESEncrypt(account.getUsername(), Constants.DATABASE_USERNAME_AES_KEY);
		String sql = "SELECT * FROM `" + username + "`";
		try {
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				History history = new History();
				history.setID(resultSet.getString("id"));
				history.setObjectID(resultSet.getString("object_id"));
				history.setType(resultSet.getString("type"));
				history.setTitle(resultSet.getString("title"));
				history.setImgUrl(resultSet.getString("image_url"));
				history.setEndDateTime(resultSet.getString("end_datetime"));
				history.setStatus(resultSet.getString("status"));
				history.setTimeStamp(resultSet.getString("time_stamp"));
				historyList.add(history);
			}
			return historyList;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private String AESEncrypt(String str, String key) {
		if(str.equals(""))
			return "";

		try {
			Cipher mCipher = Cipher.getInstance("AES");
			SecretKeySpec mSecretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec);
			return Base64Encode(mCipher.doFinal(str.getBytes()));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	private String AESDecrypt(String str, String key) {
		if(str.equals(""))
			return "";

		try {
			Cipher mCipher = Cipher.getInstance("AES");
			SecretKeySpec mSecretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec);
			return new String(mCipher.doFinal(Base64Decode(str)));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	private String Base64Encode(byte[] dataBytes) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(dataBytes);
	}

	private byte[] Base64Decode(String data) {
		Base64.Decoder decoder = Base64.getDecoder();
		return decoder.decode(data);
	}
}
