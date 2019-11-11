package io.github.jcchen07944031.API;

import io.github.jcchen07944031.Entities.Database;
import io.github.jcchen07944031.Entities.Account;
import io.github.jcchen07944031.Entities.History;

import java.util.*;
import javax.ws.rs.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import org.json.JSONObject;

@Path("/api")
public class RESTful {
	
	public RESTful() {
		
	}

	@GET
	public String helloAPI() {
		return "helloAPI";
	}

	@POST
	@Path("/register")
	public boolean register(String message) {
		Database database = new Database();
		JSONObject messageJson = new JSONObject(message);
		Account account = database.findAccountByUsername((String)messageJson.get("username"));
		if(account == null)
			account = new Account((String)messageJson.get("username"), (String)messageJson.get("password"));
		
		if(account.login()){
			account.setKeyword((String)messageJson.get("keyword"));
			database.updateAccount(account);
			return true;
		}
		return false;
	}

	@POST
	@Path("/cancel")
	public boolean cancel(String message) {
		Database database = new Database();
		JSONObject messageJson = new JSONObject(message);
		Account account = database.findAccountByUsername((String)messageJson.get("username"));
		if(account == null)
			return false;

		database.deleteAccount(account);
		return true;
	}

	@POST
	@Path("/getHistory")
	public String getHistory(String message) {
		Database database = new Database();
		JSONObject messageJson = new JSONObject(message);
		ArrayList<Account> accountList = database.findAccountByKeyword((String)messageJson.get("keyword"));

		String retJson = "[";
		for(int i = 0; i < accountList.size(); i++) {
			if(i != 0)
				retJson += ",";
			retJson += "{\"username\":\"" + accountList.get(i).getUsername() + "\", \"history\":[";
			ArrayList<History> historyList = database.getHistory(accountList.get(i));

			if(historyList != null)
				for(int j = 0; j < historyList.size(); j++) {
					if(j != 0)
						retJson += ",";
					retJson += historyList.get(j).getJson();
				}
			retJson += "]}";
		}
		retJson += "]";

		return retJson;
	}
}
