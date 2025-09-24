package com.smartsolution.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartsolution.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}