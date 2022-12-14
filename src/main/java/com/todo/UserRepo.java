package com.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todo.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

	@Query("SELECT u FROM Users u WHERE u.email = ?1")
	Users findByEmail(String email);
	
}
