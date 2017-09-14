package com.scheduled.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate4.HibernateCallback;
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

	private static Date trim(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersBornToday() {

		List<User> usersBornToday = getHibernateTemplate().execute(new HibernateCallback<List<User>>() {

			@Override
			public List<User> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from User where birthDate= :bdate");
				query.setParameter("bdate", trim(new Date()));
				return query.getResultList();
			}
		});
		return usersBornToday;
	}

}
