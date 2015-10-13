package com.github.netoht.mobile.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.netoht.mobile.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
}