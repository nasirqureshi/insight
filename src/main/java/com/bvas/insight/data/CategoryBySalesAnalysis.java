package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bvas.insight.entity.PartsMonthlySales;

public class CategoryBySalesAnalysis implements Serializable, Comparable<Object> {

	private static final long serialVersionUID = 1L;

	public BigDecimal buyingprice;

	public String keystonenumber;

	public Integer m1;

	public Integer m12;

	public Integer m3;

	public Integer m6;

	public String makemodelname;

	public String manufacturername;

	public String ordertype;

	public String partdescription;

	public String partno;

	@ManyToOne(targetEntity = PartsMonthlySales.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "partno")
	public List<PartsMonthlySales> partsmonthlysaleslist;

	public BigDecimal percent;

	public Integer reorderlevel;

	public BigDecimal sellingprice;

	public BigDecimal totalsold;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public List<String> vendorpriceslist;

	public Integer yearfrom;

	public Integer yearto;

	public CategoryBySalesAnalysis() {
		super();
	}

	public CategoryBySalesAnalysis(BigDecimal buyingprice, String makemodelname, String manufacturername,
			String ordertype, Integer yearfrom, Integer yearto, String partdescription, String keystonenumber,
			String partno, BigDecimal percent, Integer reorderlevel, BigDecimal sellingprice, BigDecimal totalsold,
			Integer unitsinstock, Integer unitsonorder, Integer m1, Integer m3, Integer m6, Integer m12,
			List<String> vendorpriceslist) {
		super();
		this.buyingprice = buyingprice;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.ordertype = ordertype;
		this.yearfrom = yearfrom;
		this.yearto = yearto;
		this.partdescription = partdescription;
		this.keystonenumber = keystonenumber;
		this.partno = partno;
		this.percent = percent;
		this.reorderlevel = reorderlevel;
		this.sellingprice = sellingprice;
		this.totalsold = totalsold;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.vendorpriceslist = vendorpriceslist;
		this.m1 = m1;
		this.m3 = m3;
		this.m6 = m6;
		this.m12 = m12;

		for (int i = 0; i <= 4; i++) {
			vendorpriceslist.add("");
		}
	}

	@Override
	public int compareTo(Object o) {

		int comparesold = ((CategoryBySalesAnalysis) o).getTotalsold().intValue();
		return comparesold - this.totalsold.intValue();
	}

	public BigDecimal getBuyingprice() {

		return buyingprice;
	}

	public String getKeystonenumber() {

		return keystonenumber;
	}

	public Integer getM1() {
		return m1;
	}

	public Integer getM12() {
		return m12;
	}

	public Integer getM3() {
		return m3;
	}

	public Integer getM6() {
		return m6;
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

	public List<PartsMonthlySales> getPartsmonthlysaleslist() {
		return partsmonthlysaleslist;
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

	public void setM1(Integer m1) {
		this.m1 = m1;
	}

	public void setM12(Integer m12) {
		this.m12 = m12;
	}

	public void setM3(Integer m3) {
		this.m3 = m3;
	}

	public void setM6(Integer m6) {
		this.m6 = m6;
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

	public void setPartsmonthlysaleslist(List<PartsMonthlySales> partsmonthlysaleslist) {
		this.partsmonthlysaleslist = partsmonthlysaleslist;
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

	public void setYearfrom(Integer yearfrom) {

		this.yearfrom = yearfrom;
	}

	public void setYearto(Integer yearto) {

		this.yearto = yearto;
	}

}
