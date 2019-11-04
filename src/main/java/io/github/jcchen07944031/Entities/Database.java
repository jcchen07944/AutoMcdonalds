package io.github.jcchen07944031.Entities;

import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.History;

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

public class Database {
	
	private String databaseRootDir = "/opt/tomcat/latest/webapps/database";

	public Database() {
		if(!(new File(databaseRootDir)).isDirectory())
			(new File(databaseRootDir)).mkdirs();
	}

	private void createAccount(Account account) {
		String accountDir = databaseRootDir + "/" + account.getAccount();
		if(!(new File(accountDir)).isDirectory())
			(new File(accountDir)).mkdirs();
		try {
			File file = new File(accountDir + "/AccessToken");
			file.createNewFile();
			file = new File(accountDir + "/Password");
			file.createNewFile();
			file = new File(accountDir + "/History");
			file.createNewFile();

			PrintWriter printWriter = new PrintWriter(accountDir + "/Password");
			printWriter.print(account.getPassword());
			printWriter.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateAccount(Account account) {
		String accountDir = databaseRootDir + account.getAccount();
		if(!(new File(databaseRootDir + account.getAccount()).isDirectory())) {
			createAccount(account);
		}
		try {
			PrintWriter printWriter = new PrintWriter(accountDir + "/AccessToken");
			printWriter.print(account.getAccessToken());
			printWriter.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	public ArrayList<Account> getAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();

		File database = new File(databaseRootDir);
		for(File files : database.listFiles()) {
			if(files.isDirectory()) {
				try {
					Account account = new Account();
					account.setAccount(files.getName());
					account.setPassword(Files.readString(Paths.get(databaseRootDir + account.getAccount() + "/Password"), StandardCharsets.UTF_8));
					account.setAccessToken(Files.readString(Paths.get(databaseRootDir + account.getAccount() + "/AccessToken"), StandardCharsets.UTF_8));
					accountList.add(account);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return accountList;
	}

	public void addHistory(Account account, History history) {
		
	}

	public ArrayList<History> getHistory(Account account) {
		ArrayList<History> historyList = new ArrayList<History>();
		
		return historyList;
	}

	private void saveObject(String fileName, Object object) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			FileInputStream fileInputStream = new FileInputStream(new File(fileName));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			objectOutputStream.writeInt(objectInputStream.readInt() + 1);
			objectInputStream.close();
			fileInputStream.close();

			objectOutputStream.writeObject(object);

			objectOutputStream.close();
			fileOutputStream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private ArrayList<Object> getObject(String fileName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(fileName));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			ArrayList<Object> objectList = new ArrayList<Object>();
			
			int cnt = objectInputStream.readInt();
			for(int i = 0; i < cnt; i++)
				objectList.add(objectInputStream.readObject());

			objectInputStream.close();
			fileInputStream.close();
			return objectList;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
