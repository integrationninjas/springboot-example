package com.integrationninjas.springbootexample.controller;

import java.util.List;
import com.integrationninjas.springbootexample.dto.UserDto;
import com.integrationninjas.springbootexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getUsers() {
		List<UserDto> usersList = userService.getUsers();
		return new ResponseEntity<>(usersList, HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
		try {
			String status = userService.createUser(userDto);
			return new ResponseEntity<>(status, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
