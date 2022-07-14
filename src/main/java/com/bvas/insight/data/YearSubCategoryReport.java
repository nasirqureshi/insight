package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

public class YearSubCategoryReport implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal cnt;

	public BigDecimal margin;

	public BigDecimal ourprice;

	public BigDecimal prcnt;

	public BigDecimal salesprice;

	public String subcategory;

	public String subcategorycode;

	public Integer yr;

	public YearSubCategoryReport() {
		super();
	}

	public YearSubCategoryReport(BigDecimal cnt, BigDecimal margin, BigDecimal ourprice, BigDecimal prcnt,
			BigDecimal salesprice, String subcategory, String subcategorycode, Integer yr) {
		super();
		this.cnt = cnt;
		this.margin = margin;
		this.ourprice = ourprice;
		this.prcnt = prcnt;
		this.salesprice = salesprice;
		this.subcategory = subcategory;
		this.subcategorycode = subcategorycode;
		this.yr = yr;
	}

	public BigDecimal getCnt() {
		return cnt;
	}

	public BigDecimal getMargin() {
		return margin;
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

	public String getSubcategorycode() {
		return subcategorycode;
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

	public void setSubcategorycode(String subcategorycode) {
		this.subcategorycode = subcategorycode;
	}

	public void setYr(Integer yr) {
		this.yr = yr;
	}

}
