// $codepro.audit.disable
package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

public class SubCatDrillDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal actualprice;

	public BigDecimal cnt;

	public BigDecimal costprice;

	public BigDecimal margin;

	public String ordertype;

	public BigDecimal ourprice;

	public BigDecimal partmargin;

	public String partno;

	public BigDecimal partprcnt;

	public BigDecimal prcnt;

	public BigDecimal salesprice;

	public SubCatDrillDetails() {
		super();
	}

	public SubCatDrillDetails(String partno, String ordertype, BigDecimal cnt, BigDecimal actualprice,
			BigDecimal costprice, BigDecimal partmargin, BigDecimal partprcnt, BigDecimal ourprice,
			BigDecimal salesprice, BigDecimal margin, BigDecimal prcnt) {
		super();
		this.partno = partno;
		this.ordertype = ordertype;
		this.cnt = cnt;
		this.actualprice = actualprice;
		this.costprice = costprice;
		this.partmargin = partmargin;
		this.partprcnt = partprcnt;
		this.ourprice = ourprice;
		this.salesprice = salesprice;
		this.margin = margin;
		this.prcnt = prcnt;
	}

	public BigDecimal getActualprice() {

		return actualprice;
	}

	public BigDecimal getCnt() {

		return cnt;
	}

	public BigDecimal getCostprice() {

		return costprice;
	}

	public BigDecimal getMargin() {

		return margin;
	}

	public String getOrdertype() {

		return ordertype;
	}

	public BigDecimal getOurprice() {

		return ourprice;
	}

	public BigDecimal getPartmargin() {

		return partmargin;
	}

	public String getPartno() {

		return partno;
	}

	public BigDecimal getPartprcnt() {

		return partprcnt;
	}

	public BigDecimal getPrcnt() {

		return prcnt;
	}

	public BigDecimal getSalesprice() {

		return salesprice;
	}

	public void setActualprice(BigDecimal actualprice) {

		this.actualprice = actualprice;
	}

	public void setCnt(BigDecimal cnt) {

		this.cnt = cnt;
	}

	public void setCostprice(BigDecimal costprice) {

		this.costprice = costprice;
	}

	public void setMargin(BigDecimal margin) {

		this.margin = margin;
	}

	public void setOrdertype(String ordertype) {

		this.ordertype = ordertype;
	}

	public void setOurprice(BigDecimal ourprice) {

		this.ourprice = ourprice;
	}

	public void setPartmargin(BigDecimal partmargin) {

		this.partmargin = partmargin;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPartprcnt(BigDecimal partprcnt) {

		this.partprcnt = partprcnt;
	}

	public void setPrcnt(BigDecimal prcnt) {

		this.prcnt = prcnt;
	}

	public void setSalesprice(BigDecimal salesprice) {

		this.salesprice = salesprice;
	}
}
