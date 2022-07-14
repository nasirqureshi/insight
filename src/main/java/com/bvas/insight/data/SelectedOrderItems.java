package com.bvas.insight.data;

public class SelectedOrderItems {

	public Integer orderno;
	public String partno;
	public Float price;
	public Integer quantity;
	public Integer supplierid;

	public SelectedOrderItems() {
		super();

		// TODO Auto-generated constructor stub
	}

	public SelectedOrderItems(String partno, Integer supplierid, Integer orderno, Float price, Integer quantity) {
		super();
		this.partno = partno;
		this.supplierid = supplierid;
		this.orderno = orderno;
		this.price = price;
		this.quantity = quantity;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public String getPartno() {
		return partno;
	}

	public Float getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}
}
