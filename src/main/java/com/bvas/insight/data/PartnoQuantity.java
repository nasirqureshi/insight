package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

public class PartnoQuantity implements Serializable {

	private static final long serialVersionUID = 1L;

	public String partno;

	public Double price;

	public Integer quantity;

	public PartnoQuantity() {
		super();
	}

	public PartnoQuantity(String partno, Integer quantity, Double price) {
		super();
		this.partno = partno;
		this.quantity = quantity;
		this.price = price;
	}

	public String getPartno() {

		return partno;
	}

	public Double getPrice() {

		return price;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}
}
