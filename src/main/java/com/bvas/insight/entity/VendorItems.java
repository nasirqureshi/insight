package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendoritems")
public class VendorItems implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "itemdesc1")
	private String itemdesc1;

	@Column(name = "itemdesc2")
	private String itemdesc2;

	@Column(name = "itemsize")
	private BigDecimal itemsize;

	@Column(name = "itemsizeunits")
	private String itemsizeunits;

	@Column(name = "noofpieces")
	private Integer noofpieces;

	@Column(name = "oemno")
	private String oemno;

	@Id
	@Column(name = "partno")
	private String partno;

	@Column(name = "plno")
	private String plno;

	@Column(name = "sellingrate")
	private BigDecimal sellingrate;

	@Id
	@Column(name = "supplierid")
	private Integer supplierid;

	@Id
	@Column(name = "vendorpartno")
	private String vendorpartno;

	public VendorItems() {
		super();
	}

	public VendorItems(Integer supplierid, String partno, String vendorpartno, String itemdesc1, String itemdesc2,
			String plno, String oemno, BigDecimal sellingrate, Integer noofpieces, BigDecimal itemsize,
			String itemsizeunits) {
		super();
		this.supplierid = supplierid;
		this.partno = partno;
		this.vendorpartno = vendorpartno;
		this.itemdesc1 = itemdesc1;
		this.itemdesc2 = itemdesc2;
		this.plno = plno;
		this.oemno = oemno;
		this.sellingrate = sellingrate;
		this.noofpieces = noofpieces;
		this.itemsize = itemsize;
		this.itemsizeunits = itemsizeunits;
	}

	public String getItemdesc1() {

		return itemdesc1;
	}

	public String getItemdesc2() {

		return itemdesc2;
	}

	public BigDecimal getItemsize() {

		return itemsize;
	}

	public String getItemsizeunits() {

		return itemsizeunits;
	}

	public Integer getNoofpieces() {

		return noofpieces;
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

	public BigDecimal getSellingrate() {

		return sellingrate;
	}

	public Integer getSupplierid() {

		return supplierid;
	}

	public String getVendorpartno() {

		return vendorpartno;
	}

	public void setItemdesc1(String itemdesc1) {

		this.itemdesc1 = itemdesc1;
	}

	public void setItemdesc2(String itemdesc2) {

		this.itemdesc2 = itemdesc2;
	}

	public void setItemsize(BigDecimal itemsize) {

		this.itemsize = itemsize;
	}

	public void setItemsizeunits(String itemsizeunits) {

		this.itemsizeunits = itemsizeunits;
	}

	public void setNoofpieces(Integer noofpieces) {

		this.noofpieces = noofpieces;
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

	public void setSellingrate(BigDecimal sellingrate) {

		this.sellingrate = sellingrate;
	}

	public void setSupplierid(Integer supplierid) {

		this.supplierid = supplierid;
	}

	public void setVendorpartno(String vendorpartno) {

		this.vendorpartno = vendorpartno;
	}
}
