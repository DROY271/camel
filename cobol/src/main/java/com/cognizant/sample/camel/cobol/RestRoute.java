package com.cognizant.sample.camel.cobol;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		restConfiguration().bindingMode(RestBindingMode.json);
		
		rest("/apply").post().type(RestInput.class) // Defines a REST service /apply and binds to type RestInput using json format.
			.consumes("application/json")
			.produces("application/json")
			.to("direct:uw-application"); // Sends the java object to route "direct:uw-application" 
		


	}

}
