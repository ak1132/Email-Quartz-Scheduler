package com.scheduled.job;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.scheduled.model.User;
import com.scheduled.service.EmailService;
import com.scheduled.service.UserService;

public class BirthdayJob extends QuartzJobBean implements InitializingBean {

	Log log = LogFactory.getLog(BirthdayJob.class);

	private EmailService emailService;

	@Autowired
	private UserService userService;

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
		try {
			List<User> users = userService.getUserBornToday();

			for (User user : users) {
				emailService.sendEmail(user);
			}

		} catch (MessagingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (emailService == null)
			throw new Exception("Email Service not initiliazed");
	}

}
