package com.scheduled.model;

import javax.persistence.Embeddable;

@Embeddable
public class Email {

	private String toaddress;

	private String[] ccaddress;

	private String subject;

	private String attachment;

	public Email() {
	}

	public Email(String toaddress, String[] ccaddress, String subject, String attachment) {
		super();
		this.toaddress = toaddress;
		this.ccaddress = ccaddress;
		this.subject = subject;
		this.attachment = attachment;
	}

	public String getToaddress() {
		return toaddress;
	}

	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}

	public String[] getCcaddress() {
		return ccaddress;
	}

	public void setCcaddress(String[] ccaddress) {
		this.ccaddress = ccaddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

}
