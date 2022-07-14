package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

public class VendorSalesHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public Integer ch12m;

	public Integer ch1m;

	public Integer ch2m;

	public Integer ch3m;
	public String containerno;
	public String makemodelname;
	public String manufacturername;
	public Integer order;
	public Date orderdate;
	public Integer orderno;
	public String partdescription;
	public String partno;
	public BigDecimal price;
	public Integer stock;
	public Integer supplierid;

	public VendorSalesHelper() {
		super();
	}

	public VendorSalesHelper(String partno) {
		super();
		this.partno = partno;
		this.manufacturername = "";
		this.makemodelname = "";
		this.partdescription = "";
		this.ch1m = 0;
		this.ch2m = 0;
		this.ch3m = 0;
		this.ch12m = 0;
		this.stock = 0;
		this.order = 0;
		this.supplierid = 0;
		this.orderno = 0;
		this.containerno = "";
		this.orderdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		this.price = new BigDecimal("0.00");
	}

	public VendorSalesHelper(String partno, String manufacturername, String makemodelname, String partdescription,
			Integer ch1m, Integer ch2m, Integer ch3m, Integer ch12m, Integer stock, Integer order, Integer supplierid,
			Integer orderno, String containerno, Date orderdate, BigDecimal price) {
		super();
		this.partno = partno;
		this.manufacturername = manufacturername;
		this.makemodelname = makemodelname;
		this.partdescription = partdescription;
		this.ch1m = ch1m;
		this.ch2m = ch2m;
		this.ch3m = ch3m;
		this.ch12m = ch12m;
		this.stock = stock;
		this.order = order;
		this.supplierid = supplierid;
		this.orderno = orderno;
		this.containerno = containerno;
		this.orderdate = orderdate;
		this.price = price;
	}

	public Integer getCh12m() {
		return ch12m;
	}

	public Integer getCh1m() {
		return ch1m;
	}

	public Integer getCh2m() {
		return ch2m;
	}

	public Integer getCh3m() {
		return ch3m;
	}

	public String getContainerno() {
		return containerno;
	}

	public String getMakemodelname() {
		return makemodelname;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public Integer getOrder() {
		return order;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public String getPartdescription() {
		return partdescription;
	}

	public String getPartno() {
		return partno;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public void setCh12m(Integer ch12m) {
		this.ch12m = ch12m;
	}

	public void setCh1m(Integer ch1m) {
		this.ch1m = ch1m;
	}

	public void setCh2m(Integer ch2m) {
		this.ch2m = ch2m;
	}

	public void setCh3m(Integer ch3m) {
		this.ch3m = ch3m;
	}

	public void setContainerno(String containerno) {
		this.containerno = containerno;
	}

	public void setMakemodelname(String makemodelname) {
		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public void setPartdescription(String partdescription) {
		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}

}
