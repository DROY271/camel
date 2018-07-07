package com.cognizant.sample.camel.cobol;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class LegacyRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		DataFormat format = new BindyFixedLengthDataFormat(RestInput.class);
		
		from("direct:uw-application")
			.marshal(format) // To Fixed length for RestInput.class
			.log("After conversion:${body}")
			.inOut("jms:queue:timed-request?replyTo=reply-queue&exchangePattern=InOut"); // Send to JMS queue and expect response.

	}

}
