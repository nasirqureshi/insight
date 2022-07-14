package com.bvas.insight.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bouncedchecks")
public class BouncedChecks implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "Balance")
	private BigDecimal balance;

	@NotNull(message = "Bounced Amount cannot be empty")
	@Column(name = "BouncedAmount")
	private BigDecimal bouncedamount;

	@Column(name = "CheckDate")
	private Date checkdate;

	@Id
	@Column(name = "CheckId")
	private Integer checkid;

	@Column(name = "CheckNo")
	private String checkno;

	@Transient
	private String companyname;

	@Transient
	private BigDecimal creditbalance;

	@NotNull(message = "Customer Id cannot be empty")
	@Column(name = "CustomerId")
	private String customerid;

	@Column(name = "EnteredDate")
	private Date entereddate;

	@Column(name = "IsCleared")
	private String iscleared;

	@Column(name = "PaidAmount")
	private BigDecimal paidamount;

	@Transient
	private String paymentdetails;

	public BouncedChecks() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BouncedChecks(BigDecimal balance, BigDecimal bouncedamount, Date checkdate, Integer checkid, String checkno,
			String companyname, BigDecimal creditbalance, String customerid, Date entereddate, String iscleared,
			BigDecimal paidamount, String paymentdetails) {
		super();
		this.balance = balance;
		this.bouncedamount = bouncedamount;
		this.checkdate = checkdate;
		this.checkid = checkid;
		this.checkno = checkno;
		this.companyname = companyname;
		this.creditbalance = creditbalance;
		this.customerid = customerid;
		this.entereddate = entereddate;
		this.iscleared = iscleared;
		this.paidamount = paidamount;
		this.paymentdetails = paymentdetails;
	}

	public BigDecimal getBalance() {

		return balance;
	}

	public BigDecimal getBouncedamount() {

		return bouncedamount;
	}

	public Date getCheckdate() {

		return checkdate;
	}

	public Integer getCheckid() {

		return checkid;
	}

	public String getCheckno() {

		return checkno;
	}

	public String getCompanyname() {

		return companyname;
	}

	public BigDecimal getCreditbalance() {

		return creditbalance;
	}

	public String getCustomerid() {

		return customerid;
	}

	public Date getEntereddate() {

		return entereddate;
	}

	public String getIscleared() {

		return iscleared;
	}

	public BigDecimal getPaidamount() {

		return paidamount;
	}

	public String getPaymentdetails() {

		return paymentdetails;
	}

	public void setBalance(BigDecimal balance) {

		this.balance = balance;
	}

	public void setBouncedamount(BigDecimal bouncedamount) {

		this.bouncedamount = bouncedamount;
	}

	public void setCheckdate(Date checkdate) {

		this.checkdate = checkdate;
	}

	public void setCheckid(Integer checkid) {

		this.checkid = checkid;
	}

	public void setCheckno(String checkno) {

		this.checkno = checkno;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setCreditbalance(BigDecimal creditbalance) {

		this.creditbalance = creditbalance;
	}

	public void setCustomerid(String customerid) {

		this.customerid = customerid;
	}

	public void setEntereddate(Date entereddate) {

		this.entereddate = entereddate;
	}

	public void setIscleared(String iscleared) {

		this.iscleared = iscleared;
	}

	public void setPaidamount(BigDecimal paidamount) {

		this.paidamount = paidamount;
	}

	public void setPaymentdetails(String paymentdetails) {

		this.paymentdetails = paymentdetails;
	}

}
