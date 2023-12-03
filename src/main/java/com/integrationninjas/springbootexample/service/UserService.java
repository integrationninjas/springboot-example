package com.integrationninjas.springbootexample.service;

import com.integrationninjas.springbootexample.dto.UserDto;
import com.integrationninjas.springbootexample.entity.User;

import java.util.List;

public interface UserService {

    String createUser(UserDto userDto);

    List<UserDto> getUsers();
}
