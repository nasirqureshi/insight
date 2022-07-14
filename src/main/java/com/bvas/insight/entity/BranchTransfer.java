package com.bvas.insight.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "branchtransfer")
public class BranchTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "orderno")
	public Integer orderno;

	@Column(name = "removedate")
	public Date removedate;

	@Id
	@GeneratedValue
	@Column(name = "serial")
	public Integer serial;

	@Column(name = "transfercode")
	public String transfercode;

	@Column(name = "type")
	public String type;

	public BranchTransfer() {
		super();
	}

	public BranchTransfer(Integer serial, Integer orderno, String transfercode, Date removedate, String type) {
		super();
		this.serial = serial;
		this.orderno = orderno;
		this.transfercode = transfercode;
		this.removedate = removedate;
		this.type = type;
	}

	public Integer getOrderno() {

		return orderno;
	}

	public Date getRemovedate() {

		return removedate;
	}

	public Integer getSerial() {

		return serial;
	}

	public String getTransfercode() {

		return transfercode;
	}

	public String getType() {

		return type;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
	}

	public void setRemovedate(Date removedate) {

		this.removedate = removedate;
	}

	public void setSerial(Integer serial) {

		this.serial = serial;
	}

	public void setTransfercode(String transfercode) {

		this.transfercode = transfercode;
	}

	public void setType(String type) {

		this.type = type;
	}

}
