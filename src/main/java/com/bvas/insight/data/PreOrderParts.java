package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreOrderParts implements Serializable {

	private static final long serialVersionUID = 1L;

	public String capa;

	public String itemdesc1;

	public String itemdesc2;

	public String oemno;

	public String partno;

	public String plno;

	public BigDecimal price = BigDecimal.valueOf(2);

	public Integer quantity;

	public String vendorpartno;

	public PreOrderParts() {
		super();
	}

	public PreOrderParts(String partno, String vendorpartno, String itemdesc1, String itemdesc2, String capa,
			String plno, String oemno, Integer quantity, BigDecimal price) {
		super();
		this.partno = partno;
		this.vendorpartno = vendorpartno;
		this.itemdesc1 = itemdesc1;
		this.itemdesc2 = itemdesc2;
		this.capa = capa;
		this.plno = plno;
		this.oemno = oemno;
		this.quantity = quantity;
		this.price = price;
	}

	public String getCapa() {

		return capa;
	}

	public String getItemdesc1() {

		return itemdesc1;
	}

	public String getItemdesc2() {

		return itemdesc2;
	}

	public String getOemno() {

		return oemno;
	}

	public String getPartno() {

		return partno;
	}

	public String getPlno() {

		return plno;
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

	public void setCapa(String capa) {

		this.capa = capa;
	}

	public void setItemdesc1(String itemdesc1) {

		this.itemdesc1 = itemdesc1;
	}

	public void setItemdesc2(String itemdesc2) {

		this.itemdesc2 = itemdesc2;
	}

	public void setOemno(String oemno) {

		this.oemno = oemno;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPlno(String plno) {

		this.plno = plno;
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
