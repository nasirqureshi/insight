package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customernotes")
public class CustomerNotes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "customerid")
	private String customerid;

	@Column(name = "customernotes")
	private String customernotes;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serial;

	public CustomerNotes() {
		super();
	}

	public CustomerNotes(Integer serial, String customerid, String customernotes) {
		super();
		this.serial = serial;
		this.customerid = customerid;
		this.customernotes = customernotes;
	}

	public String getCustomerid() {

		return customerid;
	}

	public String getCustomernotes() {

		return customernotes;
	}

	public Integer getSerial() {

		return serial;
	}

	public void setCustomerid(String customerid) {

		this.customerid = customerid;
	}

	public void setCustomernotes(String customernotes) {

		this.customernotes = customernotes;
	}

	public void setSerial(Integer serial) {

		this.serial = serial;
	}
}
