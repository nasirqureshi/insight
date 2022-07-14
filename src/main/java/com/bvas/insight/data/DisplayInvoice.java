package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bvas.insight.entity.Invoice;
import com.bvas.insight.utilities.InsightUtils;

public class DisplayInvoice implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal appliedamount;

	public BigDecimal balance;

	public String billAttention;

	public String customerid;

	public String customerName;

	public BigDecimal discount;

	public String discounttype;

	public List<DisplayInvoiceDetail> displayinvoicedetailslist;

	public Integer invoicenumber;

	public Long invoicetime;

	public BigDecimal invoicetotal;

	public String isdelivered;

	public String isprinted;

	public BigDecimal newapplied;

	public String notes;

	public Date orderdate;

	public String paymentterms;

	public Integer returnedinvoice;

	public String salesperson;

	public String shipattention;

	public String shipto;

	public String shipvia;
	public String status;
	public BigDecimal tax;

	public DisplayInvoice() {
		super();
	}

	public DisplayInvoice(BigDecimal appliedamount, BigDecimal balance, BigDecimal discount, String discounttype,
			Integer invoicenumber, Long invoicetime, BigDecimal invoicetotal, String isdelivered, String isprinted,
			BigDecimal newapplied, String notes, Date orderdate, Integer returnedinvoice, String salesperson,
			String shipattention, String shipto, String shipvia, String status, BigDecimal tax, String paymentterms,
			String customerid, List<DisplayInvoiceDetail> displayinvoicedetailslist) {
		super();
		this.appliedamount = appliedamount;
		this.balance = balance;
		this.discount = discount;
		this.discounttype = discounttype;
		this.invoicenumber = invoicenumber;
		this.invoicetime = System.currentTimeMillis();
		this.invoicetotal = invoicetotal;
		this.isdelivered = isdelivered;
		this.isprinted = isprinted;
		this.newapplied = newapplied;
		this.notes = notes;
		this.orderdate = orderdate;
		this.returnedinvoice = returnedinvoice;
		this.salesperson = salesperson;
		this.shipattention = shipattention;
		this.shipto = shipto;
		this.shipvia = shipvia;
		this.status = status;
		this.tax = tax;
		this.paymentterms = paymentterms;
		this.customerid = customerid;
		this.displayinvoicedetailslist = displayinvoicedetailslist;
	}

	public DisplayInvoice(Invoice invoice, List<DisplayInvoiceDetail> displayinvoicedetailslist) {

		this.appliedamount = invoice.getAppliedamount();
		this.balance = invoice.getBalance();
		this.discount = invoice.getDiscount();
		this.discounttype = invoice.getDiscounttype();
		this.invoicenumber = invoice.getInvoicenumber();
		this.invoicetime = (Long) invoice.getInvoicetime();
		this.invoicetotal = invoice.getInvoicetotal();
		this.isdelivered = invoice.getIsdelivered();
		this.isprinted = invoice.getIsprinted();
		this.newapplied = invoice.getNewapplied();
		this.notes = invoice.getNotes();
		this.orderdate = invoice.getOrderdate();
		this.returnedinvoice = invoice.getReturnedinvoice();
		this.salesperson = invoice.getSalesperson();
		this.shipattention = invoice.getShipattention();
		this.shipto = invoice.getShipto();
		this.shipvia = invoice.getShipvia();
		this.status = invoice.getStatus();
		this.tax = invoice.getTax();
		this.paymentterms = invoice.getPaymentterms();
		this.customerid = invoice.getCustomerid();
		this.displayinvoicedetailslist = displayinvoicedetailslist;
		this.billAttention = invoice.getBillattention();
	}

	public DisplayInvoice(String username) {

		@SuppressWarnings("unused")
		List<DisplayInvoiceDetail> displayinvoicedetailslistcopy = new ArrayList<DisplayInvoiceDetail>();

		@SuppressWarnings("unused")
		DisplayInvoiceDetail displayinvoicedetails = new DisplayInvoiceDetail();

		this.appliedamount = new BigDecimal("0.00");
		this.balance = new BigDecimal("0.00");
		this.discount = new BigDecimal("0.00");
		this.discounttype = "G";
		this.invoicenumber = 0;
		this.invoicetime = System.currentTimeMillis();
		this.invoicetotal = new BigDecimal("0.00");
		;
		this.isdelivered = "N";
		this.isprinted = "N";
		this.newapplied = new BigDecimal("0.00");
		this.notes = "";
		this.orderdate = InsightUtils.getCurrentSQLDate();
		this.returnedinvoice = 0;
		this.salesperson = username;
		this.shipattention = "";
		this.shipto = "";
		this.shipvia = "Deliver";
		this.status = "N";
		this.tax = new BigDecimal("0.00");
		this.paymentterms = null;
		/*
		 * displayinvoicedetails.actualprice = new BigDecimal("0.00");;
		 * displayinvoicedetails.invoicenumber = 0; displayinvoicedetails.partnumber =
		 * ""; displayinvoicedetails.quantity = 1; displayinvoicedetails.soldprice = new
		 * BigDecimal("0.00");; displayinvoicedetails.makemodelname = "";
		 * displayinvoicedetails.manufacturername = "";
		 * displayinvoicedetails.partdescription = "";
		 * displayinvoicedetails.reorderlevel = 0; displayinvoicedetails.unitsinstock =
		 * 0; displayinvoicedetails.unitsonorder = 0;
		 * displayinvoicedetailslistcopy.add(displayinvoicedetails);
		 * this.displayinvoicedetailslist = displayinvoicedetailslistcopy;
		 */
	}

	public BigDecimal getAppliedamount() {

		return appliedamount.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getBalance() {

		return balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getBillAttention() {
		return billAttention;
	}

	public String getCustomerid() {
		return customerid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public BigDecimal getDiscount() {

		return discount.setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getDiscounttype() {

		return discounttype;
	}

	public List<DisplayInvoiceDetail> getdisplayinvoicedetailslist() {

		return displayinvoicedetailslist;
	}

	public List<DisplayInvoiceDetail> getDisplayinvoicedetailslist() {

		return displayinvoicedetailslist;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public Long getInvoicetime() {

		return invoicetime;
	}

	public BigDecimal getInvoicetotal() {

		return invoicetotal.setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getIsdelivered() {

		return isdelivered;
	}

	public String getIsprinted() {

		return isprinted;
	}

	public BigDecimal getNewapplied() {

		return newapplied.setScale(2, RoundingMode.HALF_EVEN);
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

		return tax.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setAppliedamount(BigDecimal appliedamount) {

		this.appliedamount = appliedamount.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setBalance(BigDecimal balance) {

		this.balance = balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setBillAttention(String billAttention) {
		this.billAttention = billAttention;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setDiscount(BigDecimal discount) {

		this.discount = discount.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setDiscounttype(String discounttype) {

		this.discounttype = discounttype;
	}

	public void setdisplayinvoicedetailslist(List<DisplayInvoiceDetail> displayinvoicedetailslist) {

		this.displayinvoicedetailslist = displayinvoicedetailslist;
	}

	public void setDisplayinvoicedetailslist(List<DisplayInvoiceDetail> displayinvoicedetailslist) {

		this.displayinvoicedetailslist = displayinvoicedetailslist;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setInvoicetime(Long invoicetime) {

		this.invoicetime = invoicetime;
	}

	public void setInvoicetotal(BigDecimal invoicetotal) {

		this.invoicetotal = invoicetotal.setScale(2, RoundingMode.HALF_EVEN);
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

		this.tax = tax.setScale(2, RoundingMode.HALF_EVEN);
	}
}
