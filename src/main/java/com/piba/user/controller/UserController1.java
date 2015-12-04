package com.piba.user.controller;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piba.common.MessageBean;
import com.piba.common.RestfulContants;
import com.piba.common.ShortMsg;
import com.piba.pojo.User;
import com.piba.user.service.IUserService;

@RestController
public class UserController1 {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService UserService;
	
	/**
	 * 薪资用户
	 * /1.0/User/add
	 * 
	 * @param body
	 * 
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.ADD, method = RequestMethod.POST)
	public String add(@RequestBody String body) {
		String code = ShortMsg.ERROR;
		User User = null;
		if(!StringUtils.isEmpty(body)){
			try {
				ObjectMapper mapper = new ObjectMapper();
				User = mapper.readValue(body, User.class);
				if (User != null) {
					UserService.saveUser(User);
					code = ShortMsg.SUCCESS;
				}
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}		
		MessageBean mb = new MessageBean(code, ShortMsg.getValue(code));
		String result = getJson(mb);
		return result;
	}

	/**
	 * 根据ID删除User
	 * /1.0/User/delete/xxx
	 * @param id
	 * xxx
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.DELETE + "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Integer id) {
		String code = ShortMsg.ERROR;
		UserService.deleteUserById(id);
		code = ShortMsg.SUCCESS;
		MessageBean mb = new MessageBean(code, ShortMsg.getValue(code));
		String result = getJson(mb);
		return result;
	}
	
	/**
	 * 根据ID查询User
	 * /1.0/User/findById/xxx
	 * @param id
	 * xxx
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.FINDBYID + "/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable Integer id) {
		String result = null;
		String code = ShortMsg.ERROR;
		User User = UserService.findUserById(id);
		code = ShortMsg.SUCCESS;
		try {
			ObjectMapper mapper = new ObjectMapper();
			MessageBean mb = new MessageBean(code, ShortMsg.getValue(code));
			User.setMb(mb);
			result = mapper.writeValueAsString(User);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新User
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.PUT, method = RequestMethod.PUT)
	public String put(@RequestBody String body) {
		String code = ShortMsg.ERROR;
		User User = null;
		if(!StringUtils.isEmpty(body)){
			try {
				ObjectMapper mapper = new ObjectMapper();
				User = mapper.readValue(body, User.class);
				if(User != null && User.getId() != null){
					UserService.updateUser(User);
					code = ShortMsg.SUCCESS;
				}
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		MessageBean mb = new MessageBean(code, ShortMsg.getValue(code));
		String result = getJson(mb);
		return result;
	}

	/**
	 * 获取所有User
	 * /1.0/User/getList
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.GETLIST, method = RequestMethod.GET)
	public String getList() {
		String result = ShortMsg.ERROR;
		List<User> list = UserService.queryForList("from User", new Object[]{});
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(list);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if(ShortMsg.ERROR.equals(result)){
			MessageBean mb = new MessageBean(result, ShortMsg.getValue(result));
			String ret = getJson(mb);
			return ret;
		}
		return result;
	}
	

	private String getJson(Object val) {
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			result = mapper.writeValueAsString(val);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

}