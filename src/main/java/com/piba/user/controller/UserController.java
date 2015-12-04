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
import com.piba.common.SHA1Util;
import com.piba.common.ShortMsg;
import com.piba.common.TokenHandler;
import com.piba.pojo.User;
import com.piba.user.service.IUserService;

//@Controller
//@RequestMapping("/user")
@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 薪资用户
	 * /1.0/user/add
	 * 
	 * @param body
	 * 
	 {
		"userName":"多多",
		"userPwd":"123456",
		"age":18,
		"niceName":"duoduo"
	 }
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.ADD, method = RequestMethod.POST)
	public String add(@RequestBody String body) {
		String code = ShortMsg.ERROR;
		User user = null;
		if(!StringUtils.isEmpty(body)){
			try {
				ObjectMapper mapper = new ObjectMapper();
				user = mapper.readValue(body, User.class);
				if (user != null) {
					String sign = TokenHandler.getInstance()
							.customToken(new Object[] { user.getUserName(), user.getNiceName(), user.getAge() });

					List<User> list = userService.queryForList("from User where sign = ? ", new Object[] { sign });
					if (list != null && !list.isEmpty()) {
						code = ShortMsg.EXISTS;
					} else {
						user.setSign(sign);
						String userPwd = user.getUserPwd();
						if(!StringUtils.isEmpty(userPwd)){
							userPwd = "111111";
						}
						user.setUserPwd(SHA1Util.getInstance().Digest(userPwd));
						userService.saveUser(user);
						code = ShortMsg.SUCCESS;
					}
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
	 * 根据ID删除用户
	 * /1.0/user/delete/xxx
	 * @param id
	 * xxx
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.DELETE + "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Integer id) {
		String code = ShortMsg.ERROR;
		userService.deleteUserById(id);
		code = ShortMsg.SUCCESS;
		MessageBean mb = new MessageBean(code, ShortMsg.getValue(code));
		String result = getJson(mb);
		return result;
	}
	
	/**
	 * 根据ID查询用户
	 * /1.0/user/findById/xxx
	 * @param id
	 * xxx
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.FINDBYID + "/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable Integer id) {
		String result = null;
		String code = ShortMsg.ERROR;
		User user = userService.findUserById(id);
		code = ShortMsg.SUCCESS;
		try {
			ObjectMapper mapper = new ObjectMapper();
			MessageBean mb = new MessageBean(code, ShortMsg.getValue(code));
			user.setMb(mb);
			result = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新用户
	 * /1.0/user/put
	 * @param body
	 * 
	 {
	 	"id":1,
		"userName":"多多",
		"userPwd":"123456",
		"age":18,
		"niceName":"duoduo"
	 }
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.PUT, method = RequestMethod.PUT)
	public String put(@RequestBody String body) {
		String code = ShortMsg.ERROR;
		User user = null;
		if(!StringUtils.isEmpty(body)){
			try {
				ObjectMapper mapper = new ObjectMapper();
				user = mapper.readValue(body, User.class);
				if(user != null && user.getId() != null){
					userService.updateUser(user);
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
	 * 获取所有用户
	 * /1.0/user/getList
	 * @return
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.GETLIST, method = RequestMethod.GET)
	public String getList() {
		String result = ShortMsg.ERROR;
		List<User> list = userService.queryForList("from User", new Object[]{});
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
	

	/**
	 * 获取用户tickId
	 * /1.0/user/getTicketId
	 * xxx
	 * @return ticketId
	 * 
	 */
	@RequestMapping(value = RestfulContants.USER_REQUEST_MAPPING + RestfulContants.TICKETID, method = RequestMethod.GET)
	public String getTicketId() {
		Integer ticketId = userService.getTicketId();
		String result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(ticketId);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 *  Function:  getJson;
	 *  Author :  ASUS,    Version : 1.0,    First complete date : 2015年12月1日 下午3:22:23;
	 *  Description :传入对象获取JSON值
	 *  Param and Desciption :
	 *  Return:
	 *  History:
	 *    1. Date:2015年12月1日 下午3:22:23
	 *       Author:kelvin
	 *       Version:
	 *       Modification:
	 *
	 */
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