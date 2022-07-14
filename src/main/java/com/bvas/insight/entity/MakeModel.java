package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "makemodel")
public class MakeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "interchangemodel")
	public String interchangemodel;

	@Id
	@Column(name = "makemodelcode")
	public String makemodelcode;

	@Column(name = "makemodelname")
	public String makemodelname;

	@Column(name = "manufacturerid")
	public Integer manufacturerid;

	@Column(name = "manufacturername")
	public String manufacturername;

	public MakeModel() {
		super();
	}

	public MakeModel(String interchangemodel, String makemodelcode, String makemodelname, Integer manufacturerid,
			String manufacturername) {
		super();
		this.interchangemodel = interchangemodel;
		this.makemodelcode = makemodelcode;
		this.makemodelname = makemodelname;
		this.manufacturerid = manufacturerid;
		this.manufacturername = manufacturername;
	}

	public String getInterchangemodel() {

		return interchangemodel;
	}

	public String getMakemodelcode() {

		return makemodelcode;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public Integer getManufacturerid() {

		return manufacturerid;
	}

	public String getManufacturername() {

		return manufacturername;
	}

	public void setInterchangemodel(String interchangemodel) {

		this.interchangemodel = interchangemodel;
	}

	public void setMakemodelcode(String makemodelcode) {

		this.makemodelcode = makemodelcode;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setManufacturerid(Integer manufacturerid) {

		this.manufacturerid = manufacturerid;
	}

	public void setManufacturername(String manufacturername) {

		this.manufacturername = manufacturername;
	}

}
