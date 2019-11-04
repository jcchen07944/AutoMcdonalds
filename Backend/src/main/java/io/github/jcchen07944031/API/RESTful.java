package io.github.jcchen07944031.API;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {
	
	public RestApplication() {
		this.packages("io.github.jcchen07944031.API");
		
	}
}
