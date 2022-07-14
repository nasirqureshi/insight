package com.bvas.insight.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "writeoff")
public class Writeoff implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "Amount")
	private BigDecimal amount;

	@Transient
	private String companyName;

	@Id
	@Column(name = "InvoiceNo")
	private Integer invoiceNo;

	@Column(name = "Notes")
	private String notes;

	@Transient
	private Date orderDate;

	@Id
	@Column(name = "WriteOffDate")
	private Date writeOffDate;

	public BigDecimal getAmount() {

		return amount;
	}

	public String getCompanyName() {

		return companyName;
	}

	public Integer getInvoiceNo() {

		return invoiceNo;
	}

	public String getNotes() {

		return notes;
	}

	public Date getOrderDate() {

		return orderDate;
	}

	public Date getWriteOffDate() {

		return writeOffDate;
	}

	public void setAmount(BigDecimal amount) {

		this.amount = amount;
	}

	public void setCompanyName(String companyName) {

		this.companyName = companyName;
	}

	public void setInvoiceNo(Integer invoiceNo) {

		this.invoiceNo = invoiceNo;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	public void setOrderDate(Date orderDate) {

		this.orderDate = orderDate;
	}

	public void setWriteOffDate(Date writeOffDate) {

		this.writeOffDate = writeOffDate;
	}

}
