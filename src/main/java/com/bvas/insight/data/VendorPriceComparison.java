package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class VendorPriceComparison implements Serializable {

	private static final long serialVersionUID = 1L;

	public String makemodelname;

	public String manufacturername;

	public String partdescription;

	public String partno;

	public Integer reorderlevel;

	public BigDecimal totalsold;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public List<String> vendorpriceslist;

	public VendorPriceComparison() {
		super();
	}

	public VendorPriceComparison(String partno, String makemodelname, String manufacturername, String partdescription,
			Integer reorderlevel, BigDecimal totalsold, Integer unitsinstock, Integer unitsonorder) {
		super();
		this.partno = partno;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.partdescription = partdescription;
		this.reorderlevel = reorderlevel;
		this.totalsold = totalsold;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		for (int i = 0; i <= 4; i++) {
			vendorpriceslist.add("");
		}
	}

	public VendorPriceComparison(String partno, String makemodelname, String manufacturername, String partdescription,
			Integer reorderlevel, BigDecimal totalsold, Integer unitsinstock, Integer unitsonorder,
			List<String> vendorpriceslist) {
		super();
		this.partno = partno;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.partdescription = partdescription;
		this.reorderlevel = reorderlevel;
		this.totalsold = totalsold;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.vendorpriceslist = vendorpriceslist;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public String getManufacturername() {

		return manufacturername;
	}

	public String getPartdescription() {

		return partdescription;
	}

	public String getPartno() {

		return partno;
	}

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public BigDecimal getTotalsold() {

		return totalsold;
	}

	public Integer getUnitsinstock() {

		return unitsinstock;
	}

	public Integer getUnitsonorder() {

		return unitsonorder;
	}

	public List<String> getVendorpriceslist() {

		return vendorpriceslist;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {

		this.manufacturername = manufacturername;
	}

	public void setPartdescription(String partdescription) {

		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setTotalsold(BigDecimal totalsold) {

		this.totalsold = totalsold;
	}

	public void setUnitsinstock(Integer unitsinstock) {

		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {

		this.unitsonorder = unitsonorder;
	}

	public void setVendorpriceslist(List<String> vendorpriceslist) {

		this.vendorpriceslist = vendorpriceslist;
	}

}
