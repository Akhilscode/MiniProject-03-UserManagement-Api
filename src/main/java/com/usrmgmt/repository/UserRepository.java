package com.usrmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usrmgmt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findByEmailAndPassword(String email, String password);
	
	public UserEntity findByEmail(String email);

}
