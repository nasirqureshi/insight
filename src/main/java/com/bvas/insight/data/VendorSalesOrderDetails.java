package com.bvas.insight.data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

public class VendorSalesOrderDetails {
	public String containerno;

	public Date orderdate;

	public Integer orderno;

	public BigDecimal price;
	public Integer supplierid;

	public VendorSalesOrderDetails() {
		super();
	}

	public VendorSalesOrderDetails(Integer supplierid, Integer orderno, String containerno, Date orderdate,
			BigDecimal price) {
		super();
		this.supplierid = supplierid;
		this.orderno = orderno;
		this.containerno = containerno;
		this.orderdate = orderdate;
		this.price = price;
	}

	public VendorSalesOrderDetails(String partno) {
		super();
		this.supplierid = 0;
		this.orderno = 0;
		this.containerno = "";
		this.orderdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		this.price = new BigDecimal("0.00");
	}

	public String getContainerno() {
		return containerno;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public void setContainerno(String containerno) {
		this.containerno = containerno;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}
}
