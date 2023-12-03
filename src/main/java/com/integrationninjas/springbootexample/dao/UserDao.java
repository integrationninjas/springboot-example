package com.integrationninjas.springbootexample.dao;

import com.integrationninjas.springbootexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
