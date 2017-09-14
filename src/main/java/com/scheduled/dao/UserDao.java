package com.scheduled.dao;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.scheduled.model.User;

@Repository
public class UserDao extends SchedulerHibernateDaoSupport {

	public void updateBirthDate(User user) {
		Date currentBdate = user.getBirthDate();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentBdate);
		calendar.add(Calendar.YEAR, 1); // Add a year to currentBirthdate

		user.setBirthDate(calendar.getTime());

		getHibernateTemplate().saveOrUpdate(user);
		getHibernateTemplate().flush();
	}

}
