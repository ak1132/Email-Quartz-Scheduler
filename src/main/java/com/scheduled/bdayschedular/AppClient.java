package com.scheduled.bdayschedular;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hibernate.utils.HibernateUtils;
import com.scheduled.model.Email;
import com.scheduled.model.User;

@ComponentScan(basePackages = { "com.scheduled.*" })
public class AppClient {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

		Session session = HibernateUtils.getCurrentSession();

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

		Transaction tx = session.beginTransaction();

		session.save(user);
		tx.commit();
		session.close();
	}
}
