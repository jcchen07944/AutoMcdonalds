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
	
	private String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/AutoMcdonalds?user=" + Constants.MYSQL_USERNAME + "&password=" + Constants.MYSQL_PASSWORD;

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
		String sql = "INSERT INTO account(`username`, `password`, `access_token`, `keyword`) " +
				"VALUES ('" + username + "', '" + password + "', '" + accessToken + "', '" + keyword + "') " + 
				"ON DUPLICATE KEY UPDATE password = '" + password + "', access_token = '" + accessToken + "', keyword = '" + keyword + "'";

		try {
			statement.execute(sql);
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
			return account;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void addHistory(Account account, History history) {
		
	}

	public ArrayList<History> getHistory(Account account) {
		ArrayList<History> historyList = new ArrayList<History>();
		
		return historyList;
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
