package com.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.scheduled.model.Email;
import com.scheduled.model.User;

public class HibernateUtils {

	public static Session getCurrentSession() {
		SessionFactory sessionFactory = HibernateUtils.buildAnnotatedSessionFactory(User.class, Email.class);
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	public static SessionFactory buildSessionFactory(String configFileName) {
		Configuration configuration = new Configuration().configure(configFileName);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static SessionFactory buildSessionFactory(Class<?>... persistentClasses) {
		Configuration configuration = new Configuration();
		for (Class<?> persistentClass : persistentClasses)
			configuration.addClass(persistentClass);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static SessionFactory buildAnnotatedSessionFactory(Class<?>... persistentClasses) {
		Configuration configuration = new Configuration();
		for (Class<?> persistentClass : persistentClasses)
			configuration.addAnnotatedClass(persistentClass);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

}