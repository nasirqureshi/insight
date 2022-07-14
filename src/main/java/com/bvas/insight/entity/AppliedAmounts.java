package com.bvas.insight.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appliedamounts")
public class AppliedAmounts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "AppliedAmount")
	private BigDecimal appliedAmount;

	@Column(name = "AppliedDate")
	private Date appliedDate;

	@Id
	@Column(name = "InvoiceNumber")
	private String invoiceNumber;

	@Column(name = "PaymentTime")
	private String paymentTime;

	@Id
	@Column(name = "PaymentType")
	private String paymentType;

	@Column(name = "UserName")
	private String userName;

	public BigDecimal getAppliedAmount() {

		return appliedAmount;
	}

	public Date getAppliedDate() {

		return appliedDate;
	}

	public String getInvoiceNumber() {

		return invoiceNumber;
	}

	public String getPaymentTime() {

		return paymentTime;
	}

	public String getPaymentType() {

		return paymentType;
	}

	public String getUserName() {

		return userName;
	}

	public void setAppliedAmount(BigDecimal appliedAmount) {

		this.appliedAmount = appliedAmount;
	}

	public void setAppliedDate(Date appliedDate) {

		this.appliedDate = appliedDate;
	}

	public void setInvoiceNumber(String invoiceNumber) {

		this.invoiceNumber = invoiceNumber;
	}

	public void setPaymentTime(String paymentTime) {

		this.paymentTime = paymentTime;
	}

	public void setPaymentType(String paymentType) {

		this.paymentType = paymentType;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}
}
