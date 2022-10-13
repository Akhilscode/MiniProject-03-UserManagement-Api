package com.usrmgmt.service;

import java.util.List;

import com.usrmgmt.bindings.ActivateUser;
import com.usrmgmt.bindings.Login;
import com.usrmgmt.bindings.User;
import com.usrmgmt.entity.UserEntity;

public interface UserService {
	
	public boolean saveUser(User user);
	
	public boolean activeAccount(ActivateUser auser);
	
	public List<User> getAllUsers();
	
	public User getUserById(Integer userId);
	
	public boolean deleteUser(Integer userId);
	
	public boolean changeAccountStatus(Integer userId, String status);
	
	public String login(Login login);
	
	public String forgotPassword(String email);
 
}
