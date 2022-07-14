package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesAnalysisHistoryCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal cntperiod1;

	public BigDecimal cntperiod2;

	public BigDecimal cntperiod3;

	public String durationfromperiod1;

	public String durationfromperiod2;

	public String durationfromperiod3;

	public String durationtoperiod1;

	public String durationtoperiod2;

	public String durationtoperiod3;

	public BigDecimal marginperiod1;

	public BigDecimal marginperiod2;

	public BigDecimal marginperiod3;

	public BigDecimal ourpriceperiod1;

	public BigDecimal ourpriceperiod2;

	public BigDecimal ourpriceperiod3;

	public BigDecimal prcntperiod1;

	public BigDecimal prcntperiod2;

	public BigDecimal prcntperiod3;

	public BigDecimal salespriceperiod1;

	public BigDecimal salespriceperiod2;

	public BigDecimal salespriceperiod3;

	public String subcategory;

	public SalesAnalysisHistoryCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalesAnalysisHistoryCategory(Integer i) {
		super();
		this.subcategory = "";
		this.cntperiod1 = new BigDecimal("0.00");
		this.marginperiod1 = new BigDecimal("0.00");
		this.ourpriceperiod1 = new BigDecimal("0.00");
		this.prcntperiod1 = new BigDecimal("0.00");
		this.salespriceperiod1 = new BigDecimal("0.00");
		this.durationfromperiod1 = "";
		this.durationtoperiod1 = "";
		this.cntperiod2 = new BigDecimal("0.00");
		this.marginperiod2 = new BigDecimal("0.00");
		this.ourpriceperiod2 = new BigDecimal("0.00");
		this.prcntperiod2 = new BigDecimal("0.00");
		this.salespriceperiod2 = new BigDecimal("0.00");
		this.durationfromperiod2 = "";
		this.durationtoperiod2 = "";
		this.cntperiod3 = new BigDecimal("0.00");
		this.marginperiod3 = new BigDecimal("0.00");
		this.ourpriceperiod3 = new BigDecimal("0.00");
		this.prcntperiod3 = new BigDecimal("0.00");
		;
		this.salespriceperiod3 = new BigDecimal("0.00");
		this.durationfromperiod3 = "";
		this.durationtoperiod3 = "";
	}

	public SalesAnalysisHistoryCategory(String subcategory, BigDecimal cntperiod1, BigDecimal marginperiod1,
			BigDecimal ourpriceperiod1, BigDecimal prcntperiod1, BigDecimal salespriceperiod1,
			String durationfromperiod1, String durationtoperiod1, BigDecimal cntperiod2, BigDecimal marginperiod2,
			BigDecimal ourpriceperiod2, BigDecimal prcntperiod2, BigDecimal salespriceperiod2,
			String durationfromperiod2, String durationtoperiod2, BigDecimal cntperiod3, BigDecimal marginperiod3,
			BigDecimal ourpriceperiod3, BigDecimal prcntperiod3, BigDecimal salespriceperiod3,
			String durationfromperiod3, String durationtoperiod3) {
		super();
		this.subcategory = subcategory;
		this.cntperiod1 = cntperiod1;
		this.marginperiod1 = marginperiod1;
		this.ourpriceperiod1 = ourpriceperiod1;
		this.prcntperiod1 = prcntperiod1;
		this.salespriceperiod1 = salespriceperiod1;
		this.durationfromperiod1 = durationfromperiod1;
		this.durationtoperiod1 = durationtoperiod1;
		this.cntperiod2 = cntperiod2;
		this.marginperiod2 = marginperiod2;
		this.ourpriceperiod2 = ourpriceperiod2;
		this.prcntperiod2 = prcntperiod2;
		this.salespriceperiod2 = salespriceperiod2;
		this.durationfromperiod2 = durationfromperiod2;
		this.durationtoperiod2 = durationtoperiod2;
		this.cntperiod3 = cntperiod3;
		this.marginperiod3 = marginperiod3;
		this.ourpriceperiod3 = ourpriceperiod3;
		this.prcntperiod3 = prcntperiod3;
		this.salespriceperiod3 = salespriceperiod3;
		this.durationfromperiod3 = durationfromperiod3;
		this.durationtoperiod3 = durationtoperiod3;
	}

	public BigDecimal getCntperiod1() {
		return cntperiod1;
	}

	public BigDecimal getCntperiod2() {
		return cntperiod2;
	}

	public BigDecimal getCntperiod3() {
		return cntperiod3;
	}

	public String getDurationfromperiod1() {
		return durationfromperiod1;
	}

	public String getDurationfromperiod2() {
		return durationfromperiod2;
	}

	public String getDurationfromperiod3() {
		return durationfromperiod3;
	}

	public String getDurationtoperiod1() {
		return durationtoperiod1;
	}

	public String getDurationtoperiod2() {
		return durationtoperiod2;
	}

	public String getDurationtoperiod3() {
		return durationtoperiod3;
	}

	public BigDecimal getMarginperiod1() {
		return marginperiod1;
	}

	public BigDecimal getMarginperiod2() {
		return marginperiod2;
	}

	public BigDecimal getMarginperiod3() {
		return marginperiod3;
	}

	public BigDecimal getOurpriceperiod1() {
		return ourpriceperiod1;
	}

	public BigDecimal getOurpriceperiod2() {
		return ourpriceperiod2;
	}

	public BigDecimal getOurpriceperiod3() {
		return ourpriceperiod3;
	}

	public BigDecimal getPrcntperiod1() {
		return prcntperiod1;
	}

	public BigDecimal getPrcntperiod2() {
		return prcntperiod2;
	}

	public BigDecimal getPrcntperiod3() {
		return prcntperiod3;
	}

	public BigDecimal getSalespriceperiod1() {
		return salespriceperiod1;
	}

	public BigDecimal getSalespriceperiod2() {
		return salespriceperiod2;
	}

	public BigDecimal getSalespriceperiod3() {
		return salespriceperiod3;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setCntperiod1(BigDecimal cntperiod1) {
		this.cntperiod1 = cntperiod1;
	}

	public void setCntperiod2(BigDecimal cntperiod2) {
		this.cntperiod2 = cntperiod2;
	}

	public void setCntperiod3(BigDecimal cntperiod3) {
		this.cntperiod3 = cntperiod3;
	}

	public void setDurationfromperiod1(String durationfromperiod1) {
		this.durationfromperiod1 = durationfromperiod1;
	}

	public void setDurationfromperiod2(String durationfromperiod2) {
		this.durationfromperiod2 = durationfromperiod2;
	}

	public void setDurationfromperiod3(String durationfromperiod3) {
		this.durationfromperiod3 = durationfromperiod3;
	}

	public void setDurationtoperiod1(String durationtoperiod1) {
		this.durationtoperiod1 = durationtoperiod1;
	}

	public void setDurationtoperiod2(String durationtoperiod2) {
		this.durationtoperiod2 = durationtoperiod2;
	}

	public void setDurationtoperiod3(String durationtoperiod3) {
		this.durationtoperiod3 = durationtoperiod3;
	}

	public void setMarginperiod1(BigDecimal marginperiod1) {
		this.marginperiod1 = marginperiod1;
	}

	public void setMarginperiod2(BigDecimal marginperiod2) {
		this.marginperiod2 = marginperiod2;
	}

	public void setMarginperiod3(BigDecimal marginperiod3) {
		this.marginperiod3 = marginperiod3;
	}

	public void setOurpriceperiod1(BigDecimal ourpriceperiod1) {
		this.ourpriceperiod1 = ourpriceperiod1;
	}

	public void setOurpriceperiod2(BigDecimal ourpriceperiod2) {
		this.ourpriceperiod2 = ourpriceperiod2;
	}

	public void setOurpriceperiod3(BigDecimal ourpriceperiod3) {
		this.ourpriceperiod3 = ourpriceperiod3;
	}

	public void setPrcntperiod1(BigDecimal prcntperiod1) {
		this.prcntperiod1 = prcntperiod1;
	}

	public void setPrcntperiod2(BigDecimal prcntperiod2) {
		this.prcntperiod2 = prcntperiod2;
	}

	public void setPrcntperiod3(BigDecimal prcntperiod3) {
		this.prcntperiod3 = prcntperiod3;
	}

	public void setSalespriceperiod1(BigDecimal salespriceperiod1) {
		this.salespriceperiod1 = salespriceperiod1;
	}

	public void setSalespriceperiod2(BigDecimal salespriceperiod2) {
		this.salespriceperiod2 = salespriceperiod2;
	}

	public void setSalespriceperiod3(BigDecimal salespriceperiod3) {
		this.salespriceperiod3 = salespriceperiod3;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

}
