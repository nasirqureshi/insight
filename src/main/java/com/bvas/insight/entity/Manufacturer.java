package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manufacturer")
public class Manufacturer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "manufacturerid")
	public Integer manufacturerid;

	@Column(name = "manufacturername")
	public String manufacturername;

	@Column(name = "manufacturershortcode")
	public String manufacturershortcode;

	public Manufacturer() {
		super();
	}

	public Manufacturer(Integer manufacturerid, String manufacturername, String manufacturershortcode) {
		super();
		this.manufacturerid = manufacturerid;
		this.manufacturername = manufacturername;
		this.manufacturershortcode = manufacturershortcode;
	}

	public Integer getManufacturerid() {

		return this.manufacturerid;
	}

	public String getManufacturername() {

		return this.manufacturername;
	}

	public String getManufacturershortcode() {

		return this.manufacturershortcode;
	}

	public void setManufacturerid(Integer manufacturerid) {

		this.manufacturerid = manufacturerid;
	}

	public void setManufacturername(String manufacturername) {

		this.manufacturername = manufacturername;
	}

	public void setManufacturershortcode(String manufacturershortcode) {

		this.manufacturershortcode = manufacturershortcode;
	}

}
