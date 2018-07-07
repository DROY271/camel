package com.cognizant.sample.camel.cobol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CobolApplicationTests {

	@Autowired
	TestRestTemplate rest;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void testCamelRestEndpoint() {
		String payload = "{	\"name\":\"Test\", \"value\":\"Hello\"}";

		String response = rest.postForObject("/camel/apply", payload, String.class);
		
		String prefix = "\"Modified text ";
		String suffix = "\"";
		
		String expectedFormat = String.format("%1$10s%2$-70s", "Test", "Hello");
		
		assertThat(response).isEqualTo(prefix + expectedFormat + suffix);
		
	}

}
