package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.math.BigDecimal;

public class CostOfGoodsTotalReport {

	public BigDecimal totaldiscount;

	public BigDecimal totalgross;

	public Integer totalitems;

	public BigDecimal totalmargin;

	public BigDecimal totalourprice;

	public BigDecimal totalpercent;

	public BigDecimal totalsoldprice;

	public BigDecimal getTotaldiscount() {

		return totaldiscount;
	}

	public BigDecimal getTotalgross() {
		return totalgross;
	}

	public Integer getTotalitems() {

		return totalitems;
	}

	public BigDecimal getTotalmargin() {

		return totalmargin;
	}

	public BigDecimal getTotalourprice() {

		return totalourprice;
	}

	public BigDecimal getTotalpercent() {

		return totalpercent;
	}

	public BigDecimal getTotalsoldprice() {

		return totalsoldprice;
	}

	public void setTotaldiscount(BigDecimal totaldiscount) {

		this.totaldiscount = totaldiscount;
	}

	public void setTotalgross(BigDecimal totalgross) {
		this.totalgross = totalgross;
	}

	public void setTotalitems(Integer totalitems) {

		this.totalitems = totalitems;
	}

	public void setTotalmargin(BigDecimal totalmargin) {

		this.totalmargin = totalmargin;
	}

	public void setTotalourprice(BigDecimal totalourprice) {

		this.totalourprice = totalourprice;
	}

	public void setTotalpercent(BigDecimal totalpercent) {

		this.totalpercent = totalpercent;
	}

	public void setTotalsoldprice(BigDecimal totalsoldprice) {

		this.totalsoldprice = totalsoldprice;
	}
}
