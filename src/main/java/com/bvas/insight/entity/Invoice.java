package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "appliedamount")
	private BigDecimal appliedamount;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "billattention")
	private String billattention;

	@Column(name = "customerid")
	private String customerid;

	@Column(name = "datenewapplied")
	private Date datenewapplied;

	@Column(name = "discount")
	private BigDecimal discount;

	@Column(name = "discounttype", columnDefinition = "char(1)")
	private String discounttype;

	@Column(name = "history", columnDefinition = "char(1)")
	private String history;

	@Id
	@Column(name = "invoicenumber")
	private Integer invoicenumber;

	@Column(name = "invoicetime")
	private Long invoicetime;

	@Column(name = "invoicetotal")
	private BigDecimal invoicetotal;

	@Column(name = "isdelivered", columnDefinition = "char(1)")
	private String isdelivered;

	@Column(name = "isprinted", columnDefinition = "char(1)")
	private String isprinted;

	@Column(name = "newapplied")
	private BigDecimal newapplied;

	@Column(name = "notes")
	private String notes;

	@Column(name = "orderdate")
	private Date orderdate;

	@Column(name = "paymentterms", columnDefinition = "char(1)")
	private String paymentterms;

	@Column(name = "returnedinvoice")
	private Integer returnedinvoice;

	@Column(name = "salesperson")
	private String salesperson;

	@Column(name = "shipattention")
	private String shipattention;

	@Column(name = "shipto")
	private String shipto;

	@Column(name = "shipvia")
	private String shipvia;

	@Column(name = "status", columnDefinition = "char(1)")
	private String status;

	@Column(name = "tax")
	private BigDecimal tax;

	public Invoice() {
		super();
	}

	public Invoice(BigDecimal appliedamount, BigDecimal balance, String billattention, String customerid,
			Date datenewapplied, BigDecimal discount, String discounttype, String history, Integer invoicenumber,
			Long invoicetime, BigDecimal invoicetotal, String isdelivered, String isprinted, BigDecimal newapplied,
			String notes, Date orderdate, String paymentterms, Integer returnedinvoice, String salesperson,
			String shipattention, String shipto, String shipvia, String status, BigDecimal tax) {
		super();
		this.appliedamount = appliedamount;
		this.balance = balance;
		this.billattention = billattention;
		this.customerid = customerid;
		this.datenewapplied = datenewapplied;
		this.discount = discount;
		this.discounttype = discounttype;
		this.history = history;
		this.invoicenumber = invoicenumber;
		this.invoicetime = invoicetime;
		this.invoicetotal = invoicetotal;
		this.isdelivered = isdelivered;
		this.isprinted = isprinted;
		this.newapplied = newapplied;
		this.notes = notes;
		this.orderdate = orderdate;
		this.paymentterms = paymentterms;
		this.returnedinvoice = returnedinvoice;
		this.salesperson = salesperson;
		this.shipattention = shipattention;
		this.shipto = shipto;
		this.shipvia = shipvia;
		this.status = status;
		this.tax = tax;
	}

	public BigDecimal getAppliedamount() {

		return appliedamount;
	}

	public BigDecimal getBalance() {

		return balance;
	}

	public String getBillattention() {

		return billattention;
	}

	public String getCustomerid() {

		return customerid;
	}

	public Date getDatenewapplied() {

		return datenewapplied;
	}

	public BigDecimal getDiscount() {

		return discount;
	}

	public String getDiscounttype() {

		return discounttype;
	}

	public String getHistory() {

		return history;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public Number getInvoicetime() {

		return invoicetime;
	}

	public BigDecimal getInvoicetotal() {

		return invoicetotal;
	}

	public String getIsdelivered() {

		return isdelivered;
	}

	public String getIsprinted() {

		return isprinted;
	}

	public BigDecimal getNewapplied() {

		return newapplied;
	}

	public String getNotes() {

		return notes;
	}

	public Date getOrderdate() {

		return orderdate;
	}

	public String getPaymentterms() {

		return paymentterms;
	}

	public Integer getReturnedinvoice() {

		return returnedinvoice;
	}

	public String getSalesperson() {

		return salesperson;
	}

	public String getShipattention() {

		return shipattention;
	}

	public String getShipto() {

		return shipto;
	}

	public String getShipvia() {

		return shipvia;
	}

	public String getStatus() {

		return status;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setAppliedamount(BigDecimal appliedamount) {

		this.appliedamount = appliedamount;
	}

	public void setBalance(BigDecimal balance) {

		this.balance = balance;
	}

	public void setBillattention(String billattention) {

		this.billattention = billattention;
	}

	public void setCustomerid(String customerid) {

		this.customerid = customerid;
	}

	public void setDatenewapplied(Date datenewapplied) {

		this.datenewapplied = datenewapplied;
	}

	public void setDiscount(BigDecimal discount) {

		this.discount = discount;
	}

	public void setDiscounttype(String discounttype) {

		this.discounttype = discounttype;
	}

	public void setHistory(String history) {

		this.history = history;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setInvoicetime(Long invoicetime) {

		this.invoicetime = invoicetime;
	}

	public void setInvoicetotal(BigDecimal invoicetotal) {

		this.invoicetotal = invoicetotal;
	}

	public void setIsdelivered(String isdelivered) {

		this.isdelivered = isdelivered;
	}

	public void setIsprinted(String isprinted) {

		this.isprinted = isprinted;
	}

	public void setNewapplied(BigDecimal newapplied) {

		this.newapplied = newapplied;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	public void setOrderdate(Date orderdate) {

		this.orderdate = orderdate;
	}

	public void setPaymentterms(String paymentterms) {

		this.paymentterms = paymentterms;
	}

	public void setReturnedinvoice(Integer returnedinvoice) {

		this.returnedinvoice = returnedinvoice;
	}

	public void setSalesperson(String salesperson) {

		this.salesperson = salesperson;
	}

	public void setShipattention(String shipattention) {

		this.shipattention = shipattention;
	}

	public void setShipto(String shipto) {

		this.shipto = shipto;
	}

	public void setShipvia(String shipvia) {

		this.shipvia = shipvia;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}
}
