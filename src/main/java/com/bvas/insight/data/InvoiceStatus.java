package com.bvas.insight.data;

public class InvoiceStatus {

	String invoicemessage = "";

	String invoicemode = "";

	public InvoiceStatus() {
		super();
		this.invoicemode = "proceed";
		this.invoicemessage = "";
	}

	public InvoiceStatus(String invoicemode, String invoicemessage) {
		super();
		this.invoicemode = invoicemode;
		this.invoicemessage = invoicemessage;
	}

	public String getInvoicemessage() {

		return invoicemessage;
	}

	public String getInvoicemode() {

		return invoicemode;
	}

	public void setInvoicemessage(String invoicemessage) {

		this.invoicemessage = invoicemessage;
	}

	public void setInvoicemode(String invoicemode) {

		this.invoicemode = invoicemode;
	}
}
