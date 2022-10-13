package com.usrmgmt.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.usrmgmt.bindings.ActivateUser;
import com.usrmgmt.bindings.Login;
import com.usrmgmt.bindings.User;
import com.usrmgmt.entity.UserEntity;
import com.usrmgmt.repository.UserRepository;
import com.usrmgmt.utils.EmailsUtils;

@Service
public class UserServiceImpl implements UserService {
	
     Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


	@Autowired
	private UserRepository urepo;

	@Autowired
	private EmailsUtils eutils;
    
	Random random = new Random();
	
	@Override
	public boolean saveUser(User user) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(user, entity);
		entity.setPassword(setPassword());
		entity.setActiveStatus("Inactive");
		UserEntity save = urepo.save(entity);
		String subject = "Registration Successful";
		String file = "Register-mailbody.txt";
		String mailBody = getMailBody(entity.getFullName(), entity.getPassword(), file);
		eutils.sendEmail(entity.getEmail(), subject, mailBody);
		return save.getUserId() != null;
	}

	@Override
	public boolean activeAccount(ActivateUser auser) {

		UserEntity uentity = new UserEntity();
		uentity.setEmail(auser.getEmail());
		uentity.setPassword(auser.getTemPassword());

		Example<UserEntity> of = Example.of(uentity);
		List<UserEntity> findAll = urepo.findAll(of);
		if (findAll.isEmpty()) {
			return false;
		} else {
			UserEntity userEntity = findAll.get(0);
			userEntity.setActiveStatus("Active");
			userEntity.setPassword(auser.getNewPassword());
			urepo.save(userEntity);
			return true;
		}
	}

	@Override
	public List<User> getAllUsers() {

		List<UserEntity> findAll = urepo.findAll();

		List<User> userlist = new ArrayList<>();
		for (UserEntity uentity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(uentity, user);
			userlist.add(user);
		}
		return userlist;
	}

	@Override
	public User getUserById(Integer userId) {

		Optional<UserEntity> findById = urepo.findById(userId);

		User user = new User();
		if (findById.isPresent()) {
			UserEntity userEntity = findById.get();
			BeanUtils.copyProperties(userEntity, user);
			return user;
		}
		return null;
	}

	@Override
	public boolean deleteUser(Integer userId) {
		boolean flag = false;
		try {
			urepo.deleteById(userId);
			flag = true;
		} catch (Exception e) {
			logger.error("error message is ", e);
		}
		return flag;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String status) {
		Optional<UserEntity> userentity = urepo.findById(userId);
		if (userentity.isPresent()) {
			UserEntity userentity1 = userentity.get();
			userentity1.setActiveStatus(status);
			urepo.save(userentity1);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
		UserEntity entity = urepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if (entity == null) {
			return "Invalid Credentials";
		} else {
			if (entity.getActiveStatus().equals("Active"))
				return "Login Success";
			else
				return "Account is Not Active";
		}

	}

	@Override
	public String forgotPassword(String email) {
		UserEntity entity = urepo.findByEmail(email);
		if (entity == null) {
			return "Invalid Email Id";
		}
		String file = "Password-recover-mailBody.txt";
		String subject = "Password Recovery";
		String mailBody = getMailBody(entity.getFullName(), entity.getPassword(), file);
		boolean mailSend = eutils.sendEmail(email, subject,mailBody); 
		if(mailSend) {
		return "Email sent";
		}
		return null;
	}

	private String setPassword() {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		String values = Capital_chars + Small_chars + numbers;

		// Using random method
		StringBuilder sb = new StringBuilder();
		int length = 6;
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(values.length());
			char randomChar = values.charAt(index);
			sb.append(randomChar);

		}
		return sb.toString();
	}

	private String getMailBody(String fullname, String password, String filename){
		String line = null;
		String url = "https://www.w3schools.com";
		try(FileReader reader = new FileReader(filename);
			  BufferedReader bufferedreader1 = new BufferedReader(reader)) 
		{
			
			StringBuilder builder = new StringBuilder();
			String readLine = bufferedreader1.readLine();
			while (readLine != null) {
				builder.append(readLine);
				readLine = bufferedreader1.readLine();
			}
			bufferedreader1.close();
			line = builder.toString();
			line = line.replace("{FULLNAME}", fullname);
			line = line.replace("{TEMPPASSWORD}", password);
			line = line.replace("{URL}", url);
			line = line.replace("{PWD}", password);

		} catch (Exception e) {
			logger.error("error message is ", e);
		}

		return line;
	}

}
