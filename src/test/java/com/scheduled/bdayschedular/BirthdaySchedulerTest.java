package com.scheduled.bdayschedular;

import java.util.Date;

import org.junit.Test;

import com.scheduled.dao.SchedulerHibernateDaoSupport;
import com.scheduled.model.Email;
import com.scheduled.model.User;

public class BirthdaySchedulerTest extends SchedulerHibernateDaoSupport {

	@Test
	public void testBirthdayEmailSending() {

		// Dummy test for adding objects to Hibernate
		Email email = new Email();
		email.setAttachment("D:/abc.jpg"); // Store the path of the attachment
											// you want to send to your friend
		email.setCcaddress(new String[] {});
		email.setSubject("");
		email.setToaddress("");

		User user = new User();
		user.setBirthDate(new Date());
		user.setCustomisedMessage("");
		user.setEmail(email);
		user.setName("");

		getHibernateTemplate().saveOrUpdate(user);
		getHibernateTemplate().flush();
	}
}
