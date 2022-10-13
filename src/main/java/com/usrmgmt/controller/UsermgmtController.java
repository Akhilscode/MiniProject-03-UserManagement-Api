package com.usrmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.usrmgmt.bindings.ActivateUser;
import com.usrmgmt.bindings.Login;
import com.usrmgmt.bindings.User;
import com.usrmgmt.service.UserService;

@RestController
public class UsermgmtController {

	@Autowired
	private UserService service;

	@PostMapping("/registerUser")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		boolean saveUser = service.saveUser(user);
		if (saveUser) {
			return new ResponseEntity<String>("Registration Successfull", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Registration Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/activateUser")
	public ResponseEntity<String> activateUser(@RequestBody ActivateUser activate) {
		boolean isActivate = service.activeAccount(activate);
		if (isActivate) {
			return new ResponseEntity<String>("Account activate Successfull", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Account activate Failde", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> fetchAllUsers() {
		List<User> allUsers = service.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

	@GetMapping("/getUserbyId/{UserId}")
	public ResponseEntity<User> fetchUser(@PathVariable Integer UserId) {
		User user = service.getUserById(UserId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("/DeleteUserbyId/{UserId}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer UserId) {
		boolean isDelete = service.deleteUser(UserId);
		if (isDelete) {
			return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("User Not Deleted Successfully", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/statuschange/{userId}/{status}")
	public ResponseEntity<String> changeStatus(@PathVariable Integer userId, @PathVariable String status) {
		boolean isStatusChange = service.changeAccountStatus(userId, status);
		if (isStatusChange) {
			return new ResponseEntity<String>("Status change successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Status not changed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody Login login) {
		String login1 = service.login(login);
		return new ResponseEntity<String>(login1, HttpStatus.OK);
	}

	@GetMapping("/forgotPassword/{email}")
	public ResponseEntity<String> forgotPassword(@PathVariable String email) {
		String forgotPassword = service.forgotPassword(email);
		return new ResponseEntity<String>(forgotPassword, HttpStatus.OK);
	}

}
