package com.example.practicaljava.api.server;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;


//This annotation will make spring auto configure web client for testing. 
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DefaultRestApiTest {

//	we need to autowire a WebTestClient variable. Annotation @SpringBootTest 
//	will do all configurations behind the screen, so we just need this code
	@Autowired
	private WebTestClient webTestClient; 
	
	@Test
	void testWelcome() {
		webTestClient.get()
						.uri("/api/HelloWorld")
						.exchange()
						.expectStatus()
						.is2xxSuccessful()
						.expectBody(String.class)
						.value(IsEqualIgnoringCase.equalToIgnoringCase("Hello World!"));
						
		// fail("Not yet implemented");
	}

	@Test
	void testGetServerTime() {
		webTestClient.get()
						.uri("/api/getServerTime")
						.exchange()
						.expectStatus()
						.is2xxSuccessful()
						.expectBody(String.class)
						.value(v -> isCorrectTime(v));
		//fail("Not yet implemented");
	}

	private Object isCorrectTime(String v) {
		var inTime = LocalTime.parse(v);
		var now = LocalTime.now();
		var before30Sec = now.minusSeconds(30);
		
		assertTrue(inTime.isAfter(before30Sec) && inTime.isBefore(now));
		return inTime ;
	}

	@Test
	void testHeaderByAnnotation() {
		var headerOne = "SpringBoot Test";
		var headerTwo = "SpringBoot Test on PracticalJava";
		
		webTestClient.get().uri("/api/headerByAnnotation")
							.header("User-agent", headerOne)
							.header("PracticalJava", headerTwo)
							.exchange()
							.expectBody(String.class)
							.value(v -> {
								assertTrue(v.contains(headerOne));
								assertTrue(v.contains(headerTwo));
							});
		//fail("Not yet implemented");
	}

//	@Test
	void testHeaderByRequest() {
		fail("Not yet implemented");
	}

}
