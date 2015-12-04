package com.piba.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.piba.dao.impl.BaseDao;
import com.piba.pojo.User;
import com.piba.user.dao.IUserDao;

@Repository
public class UserDaoImpl extends BaseDao<User> implements IUserDao {

	public UserDaoImpl() {
		super(User.class);
	}

}