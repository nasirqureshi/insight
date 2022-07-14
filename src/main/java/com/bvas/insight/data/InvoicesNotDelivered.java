// $codepro.audit.disable
package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoicesNotDelivered implements Serializable {

	private static final long serialVersionUID = 1L;

	private String companyname;

	private String customerid;

	private BigDecimal discount;

	private Integer invoicenumber;

	private String invoicetime;

	private BigDecimal invoicetotal;

	private Character isdelivered;

	private String notes;

	private Character paymentterms;

	private String region;

	private String salesperson;

	private BigDecimal tax;

	public InvoicesNotDelivered() {
		super();

	}

	public InvoicesNotDelivered(String companyname, String customerid, Integer invoicenumber, String invoicetime,
			BigDecimal invoicetotal, Character isdelivered, String notes, Character paymentterms, String region,
			String salesperson, BigDecimal tax, BigDecimal discount) {
		super();
		this.companyname = companyname;
		this.customerid = customerid;
		this.invoicenumber = invoicenumber;
		this.invoicetime = invoicetime;
		this.invoicetotal = invoicetotal;
		this.isdelivered = isdelivered;
		this.notes = notes;
		this.paymentterms = paymentterms;
		this.region = region;
		this.salesperson = salesperson;
		this.tax = tax;
		this.discount = discount;
	}

	public String getCompanyname() {

		return companyname;
	}

	public String getCustomerid() {

		return customerid;
	}

	public BigDecimal getDiscount() {

		return discount;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public String getInvoicetime() {

		return invoicetime;
	}

	public BigDecimal getInvoicetotal() {

		return invoicetotal;
	}

	public Character getIsdelivered() {

		return isdelivered;
	}

	public String getNotes() {

		return notes;
	}

	public Character getPaymentterms() {

		return paymentterms;
	}

	public String getRegion() {

		return region;
	}

	public String getSalesperson() {

		return salesperson;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setCustomerid(String customerid) {

		this.customerid = customerid;
	}

	public void setDiscount(BigDecimal discount) {

		this.discount = discount;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setInvoicetime(String invoicetime) {

		this.invoicetime = invoicetime;
	}

	public void setInvoicetotal(BigDecimal invoicetotal) {

		this.invoicetotal = invoicetotal;
	}

	public void setIsdelivered(Character isdelivered) {

		this.isdelivered = isdelivered;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	public void setPaymentterms(Character paymentterms) {

		this.paymentterms = paymentterms;
	}

	public void setRegion(String region) {

		this.region = region;
	}

	public void setSalesperson(String salesperson) {

		this.salesperson = salesperson;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

}
