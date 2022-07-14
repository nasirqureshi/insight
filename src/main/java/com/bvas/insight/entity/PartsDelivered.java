package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partsdelivered")
public class PartsDelivered implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "invoicenumber")
	private Integer invoicenumber;

	@Column(name = "name")
	public String name;

	public PartsDelivered() {
		super();
	}

	public PartsDelivered(Integer invoicenumber, String name) {
		super();
		this.invoicenumber = invoicenumber;
		this.name = name;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public String getName() {

		return name;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setName(String name) {

		this.name = name;
	}
}
