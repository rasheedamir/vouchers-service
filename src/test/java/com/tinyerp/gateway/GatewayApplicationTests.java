package com.tinyerp.gateway;

import com.tinyerp.gateway.rest.VoucherResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
// The @SpringBootTest annotation tells Spring Boot to go and look for a main configuration class (one with @SpringBootApplication for instance), and use that to start a Spring application context.
// A nice feature of the Spring Test support is that the application context is cached in between tests, so if you have multiple methods in a test case, or multiple test cases with the same configuration, they only incur the cost of starting the application once.
@SpringBootTest
public class GatewayApplicationTests {

	@Autowired
	private VoucherResource controller;

	@Test
	public void whenSpringContextIsBootstrapped_thenNoExceptions() {
		assertThat(controller).isNotNull();
	}

}

