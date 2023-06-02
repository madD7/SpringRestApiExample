package com.example.practicaljava.api.server;

import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;
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
}
