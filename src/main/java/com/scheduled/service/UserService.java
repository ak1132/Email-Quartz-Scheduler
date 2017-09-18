package com.scheduled.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduled.dao.UserDao;
import com.scheduled.model.User;

@Service
public class UserService implements InitializingBean {

	@Autowired
	private UserDao userDao;

	public void saveUserDetails(User user) {
		userDao.saveUser(user);
	}

	public List<User> getUserBornToday() {
		return userDao.getUsersBornToday(trim(new Date()));
	}

	public void updateUserBirthDay(User user) {
		userDao.updateBirthDate(user);
	}

	private static Date trim(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (userDao == null) {
			throw new Exception("userDao not set");
		}
	}

}
