package com.piba.user.service;

import java.util.List;

import com.piba.pojo.User;

public interface IUserService {

	public void saveUser(User t);

	public void deleteUser(User t);
	
	public void deleteUserById(Integer id);

	public User findUserById(Integer id);

	public void updateUser(User t);

	public List<User> queryForList(String hqlString, Object... values);
	
	public Integer getTicketId();

}