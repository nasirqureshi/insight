package com.bvas.insight.data;

import java.math.BigDecimal;
import java.util.List;

public class InvCategoryBySalesAnalysis {

	public BigDecimal buyingprice;

	public String keystonenumber;

	public String makemodelname;

	public String manufacturername;

	public String monthval;

	public String ordertype;

	public String partdescription;

	public String partno;

	public BigDecimal percent;

	public Integer reorderlevel;

	public Integer salescount;

	public BigDecimal sellingprice;

	public Integer totalsold;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public List<String> vendorpriceslist;

	public Integer yearfrom;

	// from join table
	public Integer yearmonth;

	public Integer yearto;

	public BigDecimal getBuyingprice() {

		return buyingprice;
	}

	public String getKeystonenumber() {

		return keystonenumber;
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

	public BigDecimal getPercent() {

		return percent;
	}

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public BigDecimal getSellingprice() {

		return sellingprice;
	}

	public Integer getTotalsold() {

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

	public Integer getYearfrom() {

		return yearfrom;
	}

	public Integer getYearto() {

		return yearto;
	}

	public void setBuyingprice(BigDecimal buyingprice) {

		this.buyingprice = buyingprice;
	}

	public void setKeystonenumber(String keystonenumber) {

		this.keystonenumber = keystonenumber;
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

	public void setPercent(BigDecimal percent) {

		this.percent = percent;
	}

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setSellingprice(BigDecimal sellingprice) {

		this.sellingprice = sellingprice;
	}

	public void setTotalsold(Integer totalsold) {

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

	public void setYearfrom(Integer yearfrom) {

		this.yearfrom = yearfrom;
	}

	public void setYearto(Integer yearto) {

		this.yearto = yearto;
	}

}
