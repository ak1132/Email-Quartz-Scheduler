package com.scheduled.model;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@Embedded
	private Email email;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String customisedMessage;

	public User() {
	}

	public User(String name, Email email, Date birthDate, String customisedMessage) {
		super();
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.customisedMessage = customisedMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCustomisedMessage() {
		return customisedMessage;
	}

	public void setCustomisedMessage(String customisedMessage) {
		this.customisedMessage = customisedMessage;
	}

}
