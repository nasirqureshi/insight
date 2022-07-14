package com.bvas.insight.data;

import java.math.BigDecimal;

public class SalesDetailSalesPersonReport {

	private Integer returnInvCount;

	private BigDecimal returns;

	private BigDecimal sale;

	private String salesPerson;

	public Integer getReturnInvCount() {

		return returnInvCount;
	}

	public BigDecimal getReturns() {

		return returns;
	}

	public BigDecimal getSale() {

		return sale;
	}

	public String getSalesPerson() {

		return salesPerson;
	}

	public void setReturnInvCount(Integer returnInvCount) {

		this.returnInvCount = returnInvCount;
	}

	public void setReturns(BigDecimal returns) {

		this.returns = returns;
	}

	public void setSale(BigDecimal sale) {

		this.sale = sale;
	}

	public void setSalesPerson(String salesPerson) {

		this.salesPerson = salesPerson;
	}

}
