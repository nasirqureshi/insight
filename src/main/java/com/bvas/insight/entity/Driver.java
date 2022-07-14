package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {

	@Column(name = "active")
	public Integer active;

	@Column(name = "drivername")
	public String drivername;

	@Id
	@Column(name = "serial")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer serial;

	public Driver() {
		super();
	}

	public Driver(Integer serial, String drivername, Integer active) {
		super();
		this.serial = serial;
		this.drivername = drivername;
		this.active = active;
	}

	public Integer getActive() {

		return active;
	}

	public String getDrivername() {

		return drivername;
	}

	public Integer getSerial() {

		return serial;
	}

	public void setActive(Integer active) {

		this.active = active;
	}

	public void setDrivername(String drivername) {

		this.drivername = drivername;
	}

	public void setSerial(Integer serial) {

		this.serial = serial;
	}
}
