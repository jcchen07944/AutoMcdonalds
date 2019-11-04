package io.github.jcchen07944031.API;

import java.util.*;
import javax.ws.rs.*;

@Path("/api")
public class RESTful {
	
	public RESTful() {
		
	}

	@GET
	public String test() {
		return "TEST";
	}
}
