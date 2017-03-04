package com.scheduled.job;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hibernate.utils.HibernateUtils;
import com.scheduled.model.User;
import com.scheduled.service.EmailService;

public class BirthdayJob extends QuartzJobBean implements InitializingBean {

	Logger log = Logger.getLogger(BirthdayJob.class);

	private EmailService emailService;

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public static Date trim(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	@SuppressWarnings("unchecked")
	private List<User> getUsersBornToday() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where birthDate= :bdate");
		query.setParameter("bdate", trim(new Date()));
		List<User> usersBornToday = query.getResultList();
		tx.commit();
		session.close();
		return usersBornToday;
	}

	@Override
	protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
		try {
			List<User> users = getUsersBornToday();

			for (User user : users) {

				emailService.sendEmail(user);
			}

		} catch (MessagingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}

	}

	public void afterPropertiesSet() throws Exception {
		if (emailService == null)
			throw new Exception("Email Service not initiliazed");
	}

}
