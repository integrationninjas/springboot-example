package com.integrationninjas.springbootexample.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private static final String SECRET_API_KEY = "integrationninjas";

	@GetMapping
	public Object hello() {
		Map<String, String> object = new HashMap<>();
		object.put("name", "Integration Ninjas");
		object.put("email", "integrationninjas@gmail.com");
		return object;
	}

	@GetMapping("/secret")
	public String getSecret(@RequestParam String apiKey) {
		if (apiKey.equals(SECRET_API_KEY)) {
			return "The secret data is: ...";
		} else {
			return "Unauthorized!";
		}
	}

}
