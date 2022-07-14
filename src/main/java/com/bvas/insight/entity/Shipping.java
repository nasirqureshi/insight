package com.bvas.insight.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shipping")
public class Shipping {

	@Id
	@Column(name = "serialno")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer serialno;

	@Column(name = "shiptype")
	public String shiptype;

	public Shipping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shipping(Integer serialno, String shiptype) {
		super();
		this.serialno = serialno;
		this.shiptype = shiptype;
	}

	public Integer getSerialno() {
		return serialno;
	}

	public String getShiptype() {
		return shiptype;
	}

	public void setSerialno(Integer serialno) {
		this.serialno = serialno;
	}

	public void setShiptype(String shiptype) {
		this.shiptype = shiptype;
	}

}
