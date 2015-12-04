package com.piba.user.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piba.pojo.User;
import com.piba.user.dao.IUserDao;
import com.piba.user.service.IUserService;

@Transactional
@Service
@SuppressWarnings("all")
public class UserServiceImpl implements IUserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserDao userDao;

	@Override
	public void saveUser(User t) {
		t.setCreatedAt(new Date());
		userDao.save(t);
	}

	@Override
	public void deleteUser(User t) {
		userDao.delete(t);
	}

	@Override
	public void deleteUserById(Integer id) {
		User user = findUserById(id);
		if (user != null) {
			deleteUser(user);
		} else {
			logger.info("Not found User");
		}
	}

	@Override
	public User findUserById(Integer id) {
		return userDao.query(id);
	}

	@Override
	public void updateUser(User t) {
		User dbUser = findUserById(t.getId());
		dbUser.setUpdatedAt(new Date());
		userDao.update(dbUser);
	}

	@Override
	public List<User> queryForList(String hqlString, Object... values) {
		return userDao.queryForList(hqlString, values);
	}

	@Override
	public Integer getTicketId() {
		Integer ticketId = (Integer) userDao.queryForSql("SELECT MAX(ID) FROM USER", new Object[] {});
		if (ticketId == null) {
			ticketId = 1;
		} else {
			ticketId += 1;
		}
		return ticketId;
	}

}