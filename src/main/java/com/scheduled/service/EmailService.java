package com.scheduled.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.scheduled.dao.UserDao;
import com.scheduled.model.Email;
import com.scheduled.model.User;

public class EmailService implements InitializingBean {

	Log log = LogFactory.getLog(EmailService.class);

	private JavaMailSenderImpl mailSender;

	private VelocityEngine velocityEngine;

	@Autowired
	private UserDao userDao;

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public MimeMessageHelper createNewMimeMessageHelper(boolean hasAttachments, String email, String subject,
			String body) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, hasAttachments, "UTF-8");
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(body, true);
		return helper;
	}

	public FileSystemResource getResource(String filepath) throws IOException {
		FileSystemResource res = new FileSystemResource(new File(filepath));
		return res;
	}

	public void sendEmail(final User user) throws MessagingException, IOException {
		log.info("Sending Email...");
		final VelocityContext context = new VelocityContext();
		final Template t = Velocity.getTemplate("./src/main/resources/templates/message.vm");
		final Email userEmail = user.getEmail();

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				FileSystemResource fsr = getResource(userEmail.getAttachment());
				message.setTo(userEmail.getToaddress());
				message.setSubject(userEmail.getSubject());
				message.addAttachment(fsr.getFilename(), fsr);
				context.put("user", user.getName());
				StringWriter stringWriter = new StringWriter();
				t.merge(context, stringWriter);
				message.setText(stringWriter.toString(), true);
			}
		};

		mailSender.send(preparator);
		log.info("Email Sent to " + userEmail.getToaddress());
		userDao.updateBirthDate(user);
		log.info(user.getName() + "'s Birthday has been updated to " + user.getBirthDate());

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (mailSender == null)
			throw new Exception("mailSender not set");
		if (velocityEngine == null)
			throw new Exception("velocityEngine not set");
	}
}
