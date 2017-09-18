package com.scheduled.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchedulerHibernateDaoSupport implements InitializingBean {

	Log log = LogFactory.getLog(SchedulerHibernateDaoSupport.class);

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (hibernateTemplate == null) {
			throw new Exception("hibernateTemplate not set");
		}
		if (sessionFactory == null) {
			throw new Exception("sessionFactory not set");
		}

	}

}
