package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoicedetailsarch")
public class InvoiceDetailsArch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "actualprice")
	private BigDecimal actualprice;

	@Id
	@Column(name = "invoicenumber")
	private Integer invoicenumber;

	@Id
	@Column(name = "partnumber")
	private String partnumber;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "soldprice")
	private BigDecimal soldprice;

	public InvoiceDetailsArch() {
		super();
	}

	public InvoiceDetailsArch(Integer invoicenumber, String partnumber, Integer quantity, BigDecimal soldprice,
			BigDecimal actualprice) {
		super();
		this.invoicenumber = invoicenumber;
		this.partnumber = partnumber;
		this.quantity = quantity;
		this.soldprice = soldprice;
		this.actualprice = actualprice;
	}

	public BigDecimal getActualprice() {

		return actualprice;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public String getPartnumber() {

		return partnumber;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public BigDecimal getSoldprice() {

		return soldprice;
	}

	public void setActualprice(BigDecimal actualprice) {

		this.actualprice = actualprice;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setPartnumber(String partnumber) {

		this.partnumber = partnumber;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

	public void setSoldprice(BigDecimal soldprice) {

		this.soldprice = soldprice;
	}
}
