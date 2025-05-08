package com.example.ServiceSubscription.repository;

import com.example.ServiceSubscription.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}