package com.integrationninjas.springbootexample.dto;

import lombok.*;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
