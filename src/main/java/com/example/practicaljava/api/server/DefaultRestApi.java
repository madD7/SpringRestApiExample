package com.example.practicaljava.api.server;

import java.time.LocalTime;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class DefaultRestApi {

	/* Using @RequestMapping means any action can be used, viz. Get, Post, Put, etc */
	
	@GetMapping(value="/HelloWorld")	// or - @RequestMapping(value="/api/welcome")
	public String welcome() {
		return "Hello World!";
	}
	
	@GetMapping(value="/getServerTime")
	public String getServerTime() {		
		return LocalTime.now().toString();
		
	}
	
	/* HTTP Header is key-value pairs that are displayed in the request or response at HTTP message.
	 * HTTP headers allow the client and the server to pass additional information with the request or the response.
	 * An HTTP header consists of its case-insensitive name followed by a separator, usually colon (:)
	 * 
	 * Below method is used to demonstrate how to retrive header using annotation.
	 * We will retive 2 headers. 
	 * 1. Standard Http request header, contains browser information.
	 * 2. Custom header. if not marked required = false, the api will result to error from browser. 
	 * 						Coz it expects key:value in header, which can be supplied using postman.  */
	@GetMapping(value = "/headerByAnnotation")
	public String headerByAnnotation(@RequestHeader(name="User-agent") String stdHeader,
										@RequestHeader(name="PracticalJava", required = false) String customHeader) {
		return "User-agent: " + stdHeader + ", Custom Header: " + customHeader ;
	}
	
	/* Second way of getting HTTP headers using class ServerHttpRequest which 
	 * represents server-side http request. In Spring, we can get that 
	 * ServerHttpRequest by passing it as method parameter. 
	 * Lets create a new method : headerByRequest that returns String. */
	@GetMapping(value = "/headerByRequest")
	public String headerByRequest(ServerHttpRequest request) {
		return "User-agent: " + request.getHeaders().getValuesAsList("User-agent") 
								+ ", PracticalJava: "
								+ request.getHeaders().getValuesAsList("PracticalJava");
	}
}
