package com.ge.cab.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ge.cab.model.User;
import com.ge.cab.repositories.UserRepository;

@Service
public class UserService {
	public final static Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	public List<User> fetchAllUsers(String applicationName){
		List<User> users = new ArrayList<>();
		userRepository.findByApplicationName(applicationName).forEach(users::add);
		logger.info("In getAllusers function"+users);
		return users;
	}

	public String addNewUser(User user) throws NullPointerException,NoSuchFieldError{

		User existingUser= userRepository.findBySsoAndApplicationName(user.getSso(),user.getApplicationName());
		if(!userRepository.exists(user.getSso()))
		{
			logger.info("In adduser function"+user);
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
			user.setIsPasswordUpdated(true);
			userRepository.save(user);
			return "You have been registered successfully.";
		}
		else if(existingUser.getContactNo()==0)
		{
			userRepository.save(user);
			return "You have been registered successfully.";
		}
		else{
			logger.info("user already exists");
			return "This sso is already registered";
		}
	}

	public void updateUserDetails(Long sso,User user){
		if(userRepository.exists(sso))
		{
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
			user.setIsPasswordUpdated(true);
			userRepository.save(user);
		}
		logger.info("user update function"+user);
	}

	public String userAuthentication(User input) {
		long s=input.getSso();
		String pwd=input.getPassword();
		User id1=userRepository.findBySsoAndApplicationName(s,input.getApplicationName());
		if(id1!=null)
		{
			if(id1.getPassword().equals(pwd))
				return "{ \"response\": \"success\" "+ "}";
			else
				return "{ \"response\": \"Wrong username or password.\" "+ "}";
		}
		else
			return "{ \"response\": \"Please Register\" "+ "}";
	}

	public String userLogin(User input) {
		logger.info("In login function of userservice");
			User user=userRepository.findBySsoAndApplicationName(input.getSso(),input.getApplicationName());
			if(user!=null)
				return userLogin(user,input);		
		return "{ \"response\": \"Please Register\" }";
	}

	private String userLogin(User user, User input) {
		logger.info("In userlogin function of userservice");
		logger.info("logged in with Secure password ");
		if(user.getIsPasswordUpdated()){
		if (BCrypt.checkpw(input.getPassword(), user.getPassword()))
			return "{ \"entity\": \"user\", \"response\": \"success\"}";
		else
			return "{ \"entity\": \"user\", \"response\": \"Wrong username or password.\"}";
		}
		else{
		if(user.getPassword().equals(input.getPassword()))
			{
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
			user.setIsPasswordUpdated(true);
			userRepository.save(user);
			return "{ \"entity\": \"user\", \"response\": \"success\"}";
			}
		else
			return "{ \"entity\": \"user\", \"response\": \"Wrong username or password.\" }";
		}
	}


	public User getSingleUserBySsoAndApplicationName(long sso,String applicationName) {
		User user=userRepository.findBySsoAndApplicationName(sso,applicationName);
		logger.info("get single user function"+user);
		return user;
	}
}