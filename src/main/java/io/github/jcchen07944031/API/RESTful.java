package io.github.jcchen07944031.API;

import io.github.jcchen07944031.Entities.Database;
import io.github.jcchen07944031.Entities.Account;

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
		JSONObject messageJson = new JSONObject(message);
		Account account = new Account((String)messageJson.get("username"), (String)messageJson.get("password"));
		if(account.login()){
			account.setKeyword((String)messageJson.get("keyword"));
			Database database = new Database();
			database.updateAccount(account);
			return true;
		}
		return false;
	}
}
