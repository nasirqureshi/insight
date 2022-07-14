package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContainerOrderDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public String capa;

	public String description1;

	public String description2;

	public String ordertype;

	public String partno;

	public BigDecimal price;

	public Integer quantity;

	public Integer reorderlevel;

	public BigDecimal totalcost;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public String vendorpartno;

	public ContainerOrderDetails() {
		super();

	}

	public ContainerOrderDetails(String partno, String vendorpartno, String description1, String description2,
			Integer unitsinstock, Integer unitsonorder, Integer reorderlevel, BigDecimal price, Integer quantity,
			BigDecimal totalcost, String ordertype, String capa) {
		super();
		this.partno = partno;
		this.vendorpartno = vendorpartno;
		this.description1 = description1;
		this.description2 = description2;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.reorderlevel = reorderlevel;
		this.price = price;
		this.quantity = quantity;
		this.totalcost = totalcost;
		this.ordertype = ordertype;
		this.capa = capa;
	}

	public String getCapa() {

		return capa;
	}

	public String getDescription1() {

		return description1;
	}

	public String getDescription2() {

		return description2;
	}

	public String getOrdertype() {

		return ordertype;
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

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public BigDecimal getTotalcost() {

		return totalcost;
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

	public void setCapa(String capa) {

		this.capa = capa;
	}

	public void setDescription1(String description1) {

		this.description1 = description1;
	}

	public void setDescription2(String description2) {

		this.description2 = description2;
	}

	public void setOrdertype(String ordertype) {

		this.ordertype = ordertype;
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

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setTotalcost(BigDecimal totalcost) {

		this.totalcost = totalcost;
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

}
