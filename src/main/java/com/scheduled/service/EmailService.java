package com.scheduled.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.hibernate.utils.HibernateUtils;
import com.scheduled.model.Email;
import com.scheduled.model.User;

public class EmailService implements InitializingBean {

	static Logger log = Logger.getLogger(EmailService.class);

	private JavaMailSenderImpl mailSender = null;

	private VelocityEngine velocityEngine;

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
		updateBirthDate(user);
		log.info(user.getName() + "'s Birthday has been updated to " + user.getBirthDate());

	}

	public void updateBirthDate(User user) {
		Date currentBdate = user.getBirthDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentBdate);
		calendar.add(Calendar.YEAR, 1); // Add a year to currentBirthdate

		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		user.setBirthDate(calendar.getTime());
		session.save(user);
		tx.commit();
		session.close();
	}

	public void afterPropertiesSet() throws Exception {
		if (mailSender == null)
			throw new Exception("mailSender not set");
		if (velocityEngine == null)
			throw new Exception("velocityEngine not set");
	}
}
