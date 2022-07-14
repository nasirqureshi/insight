package com.bvas.insight.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paymentpolicy")
public class PaymentPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "termcode")
	public String termcode;

	@Column(name = "termdescription")
	public String termdescription;

	public PaymentPolicy() {
		super();
	}

	public PaymentPolicy(String termcode, String termdescription) {
		super();
		this.termcode = termcode;
		this.termdescription = termdescription;
	}

	public String getTermcode() {
		return termcode;
	}

	public String getTermdescription() {
		return termdescription;
	}

	public void setTermcode(String termcode) {
		this.termcode = termcode;
	}

	public void setTermdescription(String termdescription) {
		this.termdescription = termdescription;
	}
}
