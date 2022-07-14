package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendorordereditems")
public class VendorOrderedItems implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "noorder")
	private Integer noorder;

	@Id
	@Column(name = "orderno")
	private Integer orderno;

	@Column(name = "partno")
	private String partno;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "vendorpartno")
	private String vendorpartno;

	public VendorOrderedItems() {
		super();
	}

	public VendorOrderedItems(Integer orderno, Integer noorder, String partno, String vendorpartno, Integer quantity,
			BigDecimal price) {
		super();
		this.orderno = orderno;
		this.noorder = noorder;
		this.partno = partno;
		this.vendorpartno = vendorpartno;
		this.quantity = quantity;
		this.price = price;
	}

	public Integer getNoorder() {

		return noorder;
	}

	public Integer getOrderno() {

		return orderno;
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

	public String getVendorpartno() {

		return vendorpartno;
	}

	public void setNoorder(Integer noorder) {

		this.noorder = noorder;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
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

	public void setVendorpartno(String vendorpartno) {

		this.vendorpartno = vendorpartno;
	}
}
