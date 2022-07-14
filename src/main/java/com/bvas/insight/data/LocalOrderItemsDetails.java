package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.math.BigDecimal;
import java.math.BigInteger;

public class LocalOrderItemsDetails {

	public String capa;

	public String makemodelname;

	public String manufacturername;

	public String ordertype;

	public String partdescription;

	public String partno;

	public BigDecimal price;

	public BigInteger quantity;

	public Integer reorderlevel;

	public BigDecimal totalprice;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public String vendorpartno;

	public String year;

	public LocalOrderItemsDetails() {
		super();

	}

	public LocalOrderItemsDetails(String capa, String makemodelname, String manufacturername, String partdescription,
			String partno, BigDecimal price, BigInteger quantity, Integer reorderlevel, BigDecimal totalprice,
			Integer unitsinstock, Integer unitsonorder, String vendorpartno, String year, String ordertype) {
		super();
		this.capa = capa;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.partdescription = partdescription;
		this.partno = partno;
		this.price = price;
		this.quantity = quantity;
		this.reorderlevel = reorderlevel;
		this.totalprice = totalprice;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.vendorpartno = vendorpartno;
		this.year = year;
		this.ordertype = ordertype;
	}

	public String getCapa() {

		return capa;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public String getManufacturername() {

		return manufacturername;
	}

	public String getOrdertype() {

		return ordertype;
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

	public BigInteger getQuantity() {

		return quantity;
	}

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public BigDecimal getTotalprice() {

		return totalprice;
	}

	public Integer getUnitsinstock() {

		return unitsinstock;
	}

	public Integer getUnitsonorder() {

		return unitsonorder;
	}

	public String getVendorpartno() {

		return vendorpartno;
	}

	public String getYear() {

		return year;
	}

	public void setCapa(String capa) {

		this.capa = capa;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {

		this.manufacturername = manufacturername;
	}

	public void setOrdertype(String ordertype) {

		this.ordertype = ordertype;
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

	public void setQuantity(BigInteger quantity) {

		this.quantity = quantity;
	}

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setTotalprice(BigDecimal totalprice) {

		this.totalprice = totalprice;
	}

	public void setUnitsinstock(Integer unitsinstock) {

		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {

		this.unitsonorder = unitsonorder;
	}

	public void setVendorpartno(String vendorpartno) {

		this.vendorpartno = vendorpartno;
	}

	public void setYear(String year) {

		this.year = year;
	}

}
