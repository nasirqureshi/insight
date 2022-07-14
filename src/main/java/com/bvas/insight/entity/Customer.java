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
@Table(name = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "accountopenedby")
	public String accountopenedby;

	@Column(name = "accountreferredby")
	public String accountreferredby;

	@Column(name = "active")
	private Integer active;

	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "cell")
	private String cell;

	@Column(name = "companyname")
	private String companyname;

	@Column(name = "contactname")
	private String contactname;

	@Column(name = "contacttitle")
	private String contacttitle;

	@Column(name = "creditbalance")
	private BigDecimal creditbalance;

	@Column(name = "creditlimit")
	private BigDecimal creditlimit;

	@Id
	@Column(name = "customerid")
	private String customerid;

	@Column(name = "customerlevel")
	private Integer customerlevel;

	// @Column(name = "fax")
	// private String fax;

	@Column(name = "emailaddress")
	private String emailaddress;

	@Column(name = "fax")
	private String fax;

	@Column(name = "notes")
	private String notes;

	@Column(name = "openaccountdate")
	private Date openaccountdate;

	@Column(name = "paymentterms", columnDefinition = "char(1)")
	private String paymentterms;

	@Column(name = "ph")
	private String ph;

	@Column(name = "rte")
	private String rte;

	@Column(name = "st")
	private String st;

	@Column(name = "taxid", columnDefinition = "char(1)")
	private String taxid;

	@Column(name = "taxidexpireon")
	private Date taxidexpireon;

	@Column(name = "taxidnumber")
	private String taxidnumber;

	@Column(name = "terms")
	private String terms;

	@Column(name = "town")
	private String town;

	@Column(name = "zip")
	private String zip;

	public Customer() {
		super();
	}

	public Customer(String customerid) {
		super();
		this.customerid = customerid;
		this.companyname = "";
		this.contactname = "";
		this.contacttitle = "";
		this.terms = "";
		this.taxid = "N";
		this.taxidnumber = "";
		this.notes = "";
		this.paymentterms = "";
		this.creditbalance = new BigDecimal("0.00");
		this.creditlimit = new BigDecimal("0.00");
		this.customerlevel = 0;
		this.address1 = "";
		this.address2 = "";
		this.town = "";
		this.st = "";
		this.rte = "";
		this.ph = "";
		this.zip = "";
		this.emailaddress = "";
		this.openaccountdate = new Date();
		this.taxidexpireon = new Date();
		this.accountopenedby = "";
		this.accountreferredby = "";
		this.active = 1;
		this.fax = "";
		this.cell = "";

	}

	public Customer(String address1, String address2, String companyname, String contactname, String contacttitle,
			BigDecimal creditbalance, BigDecimal creditlimit, String customerid, Integer customerlevel, String notes,
			String paymentterms, String ph, String fax, String rte, String st, String taxid, String taxidnumber,
			String terms, String town, String zip, String emailaddress, Date openaccountdate, Date taxidexpireon,
			String accountreferredby, String accountopenedby, Integer active, String cell) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.companyname = companyname;
		this.contactname = contactname;
		this.contacttitle = contacttitle;
		this.creditbalance = creditbalance;
		this.creditlimit = creditlimit;
		this.customerid = customerid;
		this.customerlevel = customerlevel;
		this.notes = notes;
		this.paymentterms = paymentterms;
		this.ph = ph;
		this.fax = fax;
		this.rte = rte;
		this.st = st;
		this.taxid = taxid;
		this.taxidnumber = taxidnumber;
		this.terms = terms;
		this.town = town;
		this.zip = zip;
		this.emailaddress = emailaddress;
		this.openaccountdate = openaccountdate;
		this.taxidexpireon = taxidexpireon;
		this.accountreferredby = accountreferredby;
		this.accountopenedby = accountopenedby;
		this.active = active;
		this.cell = cell;
	}

	public String getAccountopenedby() {
		return accountopenedby;
	}

	public String getAccountreferredby() {
		return accountreferredby;
	}

	public Integer getActive() {
		return active;
	}

	public String getAddress1() {

		return address1;
	}

	public String getAddress2() {

		return address2;
	}

	public String getCell() {
		return cell;
	}

	public String getCompanyname() {

		return companyname;
	}

	public String getContactname() {

		return contactname;
	}

	public String getContacttitle() {

		return contacttitle;
	}

	public BigDecimal getCreditbalance() {

		return creditbalance;
	}

	public BigDecimal getCreditlimit() {

		return creditlimit;
	}

	public String getCustomerid() {

		return customerid;
	}

	public Integer getCustomerlevel() {

		return customerlevel;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public String getFax() {
		return fax;
	}

	public String getNotes() {

		return notes;
	}

	public Date getOpenaccountdate() {
		return openaccountdate;
	}

	public String getPaymentterms() {

		return paymentterms;
	}

	public String getPh() {

		return ph;
	}

	public String getRte() {

		return rte;
	}

	public String getSt() {

		return st;
	}

	public String getTaxid() {

		return taxid;
	}

	public Date getTaxidexpireon() {
		return taxidexpireon;
	}

	public String getTaxidnumber() {

		return taxidnumber;
	}

	public String getTerms() {

		return terms;
	}

	public String getTown() {

		return town;
	}

	public String getZip() {

		return zip;
	}

	public void setAccountopenedby(String accountopenedby) {
		this.accountopenedby = accountopenedby;
	}

	public void setAccountreferredby(String accountreferredby) {
		this.accountreferredby = accountreferredby;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public void setAddress1(String address1) {

		this.address1 = address1;
	}

	public void setAddress2(String address2) {

		this.address2 = address2;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setContactname(String contactname) {

		this.contactname = contactname;
	}

	public void setContacttitle(String contacttitle) {

		this.contacttitle = contacttitle;
	}

	public void setCreditbalance(BigDecimal creditbalance) {

		this.creditbalance = creditbalance;
	}

	public void setCreditlimit(BigDecimal creditlimit) {

		this.creditlimit = creditlimit;
	}

	public void setCustomerid(String customerid) {

		this.customerid = customerid;
	}

	public void setCustomerlevel(Integer customerlevel) {

		this.customerlevel = customerlevel;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	public void setOpenaccountdate(Date openaccountdate) {
		this.openaccountdate = openaccountdate;
	}

	public void setPaymentterms(String paymentterms) {

		this.paymentterms = paymentterms;
	}

	public void setPh(String ph) {

		this.ph = ph;
	}

	public void setRte(String rte) {

		this.rte = rte;
	}

	public void setSt(String st) {

		this.st = st;
	}

	public void setTaxid(String taxid) {

		this.taxid = taxid;
	}

	public void setTaxidexpireon(Date taxidexpireon) {
		this.taxidexpireon = taxidexpireon;
	}

	public void setTaxidnumber(String taxidnumber) {

		this.taxidnumber = taxidnumber;
	}

	public void setTerms(String terms) {

		this.terms = terms;
	}

	public void setTown(String town) {

		this.town = town;
	}

	public void setZip(String zip) {

		this.zip = zip;
	}

}
