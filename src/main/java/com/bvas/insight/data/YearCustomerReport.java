package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

public class YearCustomerReport implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal cnt;

	public String companyname;

	public BigDecimal margin;

	public BigDecimal ourprice;

	public BigDecimal prcnt;

	public BigDecimal salesprice;

	public Integer yr;

	public YearCustomerReport() {
		super();
	}

	public YearCustomerReport(Integer yr, String companyname, BigDecimal cnt, BigDecimal ourprice,
			BigDecimal salesprice, BigDecimal margin, BigDecimal prcnt) {
		super();
		this.yr = yr;
		this.companyname = companyname;
		this.cnt = cnt;
		this.ourprice = ourprice;
		this.salesprice = salesprice;
		this.margin = margin;
		this.prcnt = prcnt;
	}

	public BigDecimal getCnt() {

		return cnt;
	}

	public String getCompanyname() {

		return companyname;
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

	public Integer getYr() {

		return yr;
	}

	public void setCnt(BigDecimal cnt) {

		this.cnt = cnt;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
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

	public void setYr(Integer yr) {

		this.yr = yr;
	}
}
