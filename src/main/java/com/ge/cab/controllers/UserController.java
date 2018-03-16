package com.ge.cab.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.cab.JsonResponse;
import com.ge.cab.model.User;
/*import com.ge.cab.repositories.SessionRepository;*/
import com.ge.cab.repositories.UserRepository;
/*import com.ge.cab.services.SessionService;*/
import com.ge.cab.services.UserService;

@RestController
public class UserController {
	public final static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private  UserService userservice;
	@Autowired
	private UserRepository UserRepository;
/*	@Autowired
	private SessionService sessionService;*/

	@RequestMapping(method= RequestMethod.GET,value="/getAllUsers/{applicationName}")
	public ResponseEntity<?> getAllUsers(@PathVariable String applicationName){
		logger.info("Fetching details of all users");
		List<User> list=null;
		try {
			list=userservice.fetchAllUsers(applicationName);
		}	
		catch(Exception e) {
			logger.error("Exception occured while getting User details.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("No entries found!"),HttpStatus.NOT_FOUND);
		}
		logger.info("Returned the list of user details");
		return new ResponseEntity<>(list,HttpStatus.OK);		
	}

	@RequestMapping(method= RequestMethod.GET,value="/getSingleUser/{Sso}/{applicationName}")
	public ResponseEntity<?> getSingleUser(@PathVariable long Sso,@PathVariable String applicationName){
		logger.info("Fetching details of a user.");
		User result;
		try {
			result=userservice.getSingleUserBySsoAndApplicationName(Sso,applicationName);
			if(result.getSso()==0)
				return new ResponseEntity<>(new JsonResponse().convertToJson("User does not exist.Please register!"),HttpStatus.BAD_REQUEST);
		}catch (java.lang.NullPointerException e) {
			logger.error("User does not exist.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("User does not exist.Please register!"),HttpStatus.BAD_REQUEST);
		}	
		catch(Exception e) {
			logger.error("Exception occured while getting User details.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("No entry found for User!"),HttpStatus.NOT_FOUND);
		}
		logger.info("Returned User details.");
		return new ResponseEntity<>(result,HttpStatus.OK);		
	}

	@RequestMapping(method= RequestMethod.POST,value="/login")
	public ResponseEntity<?> Login(@RequestBody User input){
		logger.info("Authenticating the user with SSO:"+input.getSso());
		logger.info("logged in with Secure password ");
		String str="";
		try{
			str=userservice.userLogin(input);
		}catch (HttpMessageNotReadableException e) 
		{
			logger.error("Could not authenticate. Required request body is missing");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate. Required request body is missing"),HttpStatus.BAD_REQUEST);
		}catch (java.lang.NullPointerException e) {
			logger.error("Could not authenticate. Required request body is incomplete");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate. Required request body is incomplete"),HttpStatus.BAD_REQUEST);
		}catch (DataAccessException e) {
			logger.error("Username does not exist.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate."),HttpStatus.BAD_REQUEST);
		}
		if(str==null)
		{
			logger.error("Could not authenticate. Insufficient data sent.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate Admin. Insufficient data sent."),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(str,HttpStatus.OK);			
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateUser")
	public ResponseEntity<?> updateUser(@RequestBody User user){
		logger.info("Updating user details whose SSO is:"+user.getSso());
		try{
			userservice.updateUserDetails(user.getSso(), user);
		}catch(DataAccessException e){
			logger.error("Could not update user details.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not update user details"), HttpStatus.BAD_REQUEST);
		}
		logger.info("User details updated successfully.");
		return new ResponseEntity<>(new JsonResponse().convertToJson("User details updated successfully."), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public ResponseEntity<?> addUser(@RequestBody User user){
		logger.info("Adding new user with following details:");
		logger.info("SSO:"+user.getSso());
		String str=null;
		try{
			str=userservice.addNewUser(user);	
		}catch(NullPointerException e){
			logger.error("Input data is incomplete. Could not add user.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Input data is incomplete. Could not add user."), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Exception occured while getting User details.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Exception occured while getting User details."),HttpStatus.NOT_FOUND);
		}
		logger.info(str);
		return new ResponseEntity<>(new JsonResponse().convertToJson(str), HttpStatus.OK);
	}
}