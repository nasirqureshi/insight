package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordercounter")
public class OrderCounter {

	@Column(name = "branch")
	public String branch;

	@Column(name = "orderno")
	public Integer orderno;

	@Column(name = "ordertype")
	public String ordertype;

	@Id
	@Column(name = "serialno")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer serialno;

	public OrderCounter() {
		super();
	}

	public OrderCounter(Integer serialno, String ordertype, Integer orderno, String branch) {
		super();
		this.serialno = serialno;
		this.ordertype = ordertype;
		this.orderno = orderno;
		this.branch = branch;
	}

	public String getBranch() {

		return branch;
	}

	public Integer getOrderno() {

		return orderno;
	}

	public String getOrdertype() {

		return ordertype;
	}

	public Integer getSerialno() {

		return serialno;
	}

	public void setBranch(String branch) {

		this.branch = branch;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
	}

	public void setOrdertype(String ordertype) {

		this.ordertype = ordertype;
	}

	public void setSerialno(Integer serialno) {

		this.serialno = serialno;
	}
}
