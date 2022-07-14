/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.bvas.insight.data;

import java.math.BigDecimal;

/**
 *
 * @author Khalid
 */
public class SaleByRouteData {
	private BigDecimal discount;
	private BigDecimal expenses;
	private BigDecimal net;
	private BigDecimal sale;
	private Integer saleMonth;
	private Integer saleYear;
	private BigDecimal tax;

	public BigDecimal getDiscount() {
		return discount;
	}

	public BigDecimal getExpenses() {
		return expenses;
	}

	public BigDecimal getNet() {
		return net;
	}

	public BigDecimal getSale() {
		return sale;
	}

	public Integer getSaleMonth() {
		return saleMonth;
	}

	public Integer getSaleYear() {
		return saleYear;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public void setExpenses(BigDecimal expenses) {
		this.expenses = expenses;
	}

	public void setNet(BigDecimal net) {
		this.net = net;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public void setSaleMonth(Integer saleMonth) {
		this.saleMonth = saleMonth;
	}

	public void setSaleYear(Integer saleYear) {
		this.saleYear = saleYear;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

}
