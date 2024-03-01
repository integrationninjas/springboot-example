package com.integrationninjas.springbootexample.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping
	public Object hello() {
		Map<String, String> object = new HashMap<>();
		object.put("name", "Integration Ninjas");
		object.put("email", "integrationninjas@gmail.com");
		return object;
	}

	@GetMapping("/readfile")
	public String readFile(@RequestParam String fileName) {
		StringBuilder content = new StringBuilder();
		try {
			Files.lines(Paths.get(fileName))
					.forEach(line -> content.append(line).append("\n"));
			// Files.lines() does not require explicit closing, but this comment illustrates the vulnerability.
		} catch (IOException e) {
			return "Error reading file: " + e.getMessage();
		}
		return "File content: " + content.toString();
	}

}
