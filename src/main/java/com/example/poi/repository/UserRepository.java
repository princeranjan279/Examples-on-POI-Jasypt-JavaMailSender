package com.example.poi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.poi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
