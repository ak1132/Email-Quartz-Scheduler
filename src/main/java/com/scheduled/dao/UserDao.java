package com.scheduled.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scheduled.model.User;

@Repository
public class UserDao extends SchedulerHibernateDaoSupport {

	Log log = LogFactory.getLog(UserDao.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBirthDate(User user) {

		Date currentBdate = user.getBirthDate();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentBdate);
		calendar.add(Calendar.YEAR, 1); // Add a year to currentBirthdate

		user.setBirthDate(calendar.getTime());

		getHibernateTemplate().saveOrUpdate(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	public List<User> getUsersBornToday(final Date today) {

		List<User> usersBornToday = getHibernateTemplate().execute(new HibernateCallback<List<User>>() {

			@Override
			public List<User> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from User where birthDate= :bdate");
				query.setParameter("bdate", today);
				return query.getResultList();
			}
		});
		return usersBornToday;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveUser(User user) {
		getHibernateTemplate().saveOrUpdate(user);
	}

}
