package com.scheduled.bdayschedular;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scheduled.model.Email;
import com.scheduled.model.User;
import com.scheduled.service.UserService;

@Configuration
@EnableAspectJAutoProxy
public class AppClient {
	public static void main(String[] args) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

		UserService userService = ctx.getBean(UserService.class);
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

		userService.saveUserDetails(user);

	}
}
