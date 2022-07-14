package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthSubCategoryReport implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal cnt;

	public BigDecimal margin;

	public Integer mnth;

	public BigDecimal ourprice;

	public BigDecimal prcnt;

	public BigDecimal salesprice;

	public String subcategory;

	public Integer yr;

	public MonthSubCategoryReport() {
		super();
	}

	public MonthSubCategoryReport(Integer yr, Integer mnth, String subcategory, BigDecimal cnt, BigDecimal ourprice,
			BigDecimal salesprice, BigDecimal margin, BigDecimal prcnt) {
		super();
		this.yr = yr;
		this.mnth = mnth;
		this.subcategory = subcategory;
		this.cnt = cnt;
		this.ourprice = ourprice;
		this.salesprice = salesprice;
		this.margin = margin;
		this.prcnt = prcnt;
	}

	public BigDecimal getCnt() {

		return cnt;
	}

	public BigDecimal getMargin() {

		return margin;
	}

	public Integer getMnth() {

		return mnth;
	}

	public BigDecimal getOurprice() {

		return ourprice;
	}

	public BigDecimal getPrcnt() {

		return prcnt;
	}

	public BigDecimal getSalesprice() {

		return salesprice;
	}

	public String getSubcategory() {

		return subcategory;
	}

	public Integer getYr() {

		return yr;
	}

	public void setCnt(BigDecimal cnt) {

		this.cnt = cnt;
	}

	public void setMargin(BigDecimal margin) {

		this.margin = margin;
	}

	public void setMnth(Integer mnth) {

		this.mnth = mnth;
	}

	public void setOurprice(BigDecimal ourprice) {

		this.ourprice = ourprice;
	}

	public void setPrcnt(BigDecimal prcnt) {

		this.prcnt = prcnt;
	}

	public void setSalesprice(BigDecimal salesprice) {

		this.salesprice = salesprice;
	}

	public void setSubcategory(String subcategory) {

		this.subcategory = subcategory;
	}

	public void setYr(Integer yr) {

		this.yr = yr;
	}
}
