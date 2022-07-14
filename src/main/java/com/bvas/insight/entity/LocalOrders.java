package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "localorders")
public class LocalOrders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "dateentered")
	private Date dateentered;

	@Id
	@Column(name = "invoiceno")
	private Integer invoiceno;

	@Id
	@Column(name = "partno")
	private String partno;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "quantity")
	private Integer quantity;

	@Id
	@Column(name = "supplierid")
	private Integer supplierid;

	@Column(name = "vendorinvdate")
	private Date vendorinvdate;

	@Id
	@Column(name = "vendorinvno")
	private String vendorinvno;

	@Id
	@Column(name = "vendorpartno")
	private String vendorpartno;

	public LocalOrders() {
		super();
	}

	public LocalOrders(Integer invoiceno, Date dateentered, Integer supplierid, String partno, String vendorpartno,
			Integer quantity, BigDecimal price, String vendorinvno, Date vendorinvdate) {
		super();
		this.invoiceno = invoiceno;
		this.dateentered = dateentered;
		this.supplierid = supplierid;
		this.partno = partno;
		this.vendorpartno = vendorpartno;
		this.quantity = quantity;
		this.price = price;
		this.vendorinvno = vendorinvno;
		this.vendorinvdate = vendorinvdate;
	}

	public Date getDateentered() {

		return dateentered;
	}

	public Integer getInvoiceno() {

		return invoiceno;
	}

	public String getPartno() {

		return partno;
	}

	public BigDecimal getPrice() {

		return price;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public Integer getSupplierid() {

		return supplierid;
	}

	public Date getVendorinvdate() {

		return vendorinvdate;
	}

	public String getVendorinvno() {

		return vendorinvno;
	}

	public String getVendorpartno() {

		return vendorpartno;
	}

	public void setDateentered(Date dateentered) {

		this.dateentered = dateentered;
	}

	public void setInvoiceno(Integer invoiceno) {

		this.invoiceno = invoiceno;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

	public void setSupplierid(Integer supplierid) {

		this.supplierid = supplierid;
	}

	public void setVendorinvdate(Date vendorinvdate) {

		this.vendorinvdate = vendorinvdate;
	}

	public void setVendorinvno(String vendorinvno) {

		this.vendorinvno = vendorinvno;
	}

	public void setVendorpartno(String vendorpartno) {

		this.vendorpartno = vendorpartno;
	}
}
