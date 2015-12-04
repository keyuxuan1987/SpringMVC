package com.piba.user.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piba.pojo.RestUser;

//--http://localhost:8080/rest/
@RestController
public class RestfulController {

	private Logger logger = LoggerFactory.getLogger(RestfulController.class);
	
	@RequestMapping(value = "/getUserName", method = RequestMethod.GET)
	//getUserName userName=11
	public String getUserName(@RequestParam(value = "userName") String userName) {
		logger.info(userName);
		return userName;
	}

	@RequestMapping("getRestUser")
	//invoke getRestUser?userName=11&userId=1&userAge=18
	public RestUser getRestUser(@RequestParam(value = "userName") String userName, String userId, int userAge) {
		RestUser user = new RestUser();
		user.setUserName(userName);
		user.setUserId(userId);
		user.setUserAge(userAge);
		return user;
	}

	@RequestMapping("getRestUserBody")
	/*
	getRestUserBody
	{
	"userId":"mm",
	"userName":"多多",
	"userAge":18,
	"createDate":"2015-11-12 16:34:57"
	}
	*/
	public RestUser getRestUserBody(@RequestBody String body) {
		ObjectMapper mapper = new ObjectMapper();
		RestUser user = null;

		try {
			logger.info(body);
			user = mapper.readValue(body, RestUser.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
}
