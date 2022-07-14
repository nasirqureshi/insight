package com.bvas.insight.data;

import java.math.BigDecimal;

public class SalesDetailReport {

	private Integer count;

	private BigDecimal discount;

	private BigDecimal gross;

	private BigDecimal net;

	// For region Returns tab only
	private String region;

	private BigDecimal returned;

	private BigDecimal sale;

	private BigDecimal tax;

	private String year;

	public Integer getCount() {

		return count;
	}

	public BigDecimal getDiscount() {

		return discount == null ? BigDecimal.ZERO : discount;
	}

	public BigDecimal getGross() {

		gross = getSale().add(getTax());
		return gross == null ? BigDecimal.ZERO : gross;
	}

	public BigDecimal getNet() {

		net = getSale().subtract(getDiscount());
		return net == null ? BigDecimal.ZERO : net;
	}

	public String getRegion() {

		return region;
	}

	public BigDecimal getReturned() {

		return returned == null ? BigDecimal.ZERO : returned;
	}

	public BigDecimal getSale() {

		return sale == null ? BigDecimal.ZERO : sale;
	}

	public BigDecimal getTax() {

		return tax == null ? BigDecimal.ZERO : tax;
	}

	public String getYear() {

		return year;
	}

	public void setCount(Integer count) {

		this.count = count;
	}

	public void setDiscount(BigDecimal discount) {

		this.discount = discount;
	}

	public void setGross(BigDecimal gross) {

		this.gross = gross;
	}

	public void setNet(BigDecimal net) {

		this.net = net;
	}

	public void setRegion(String region) {

		this.region = region;
	}

	public void setReturned(BigDecimal returned) {

		this.returned = returned;
	}

	public void setSale(BigDecimal sale) {

		this.sale = sale;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

	public void setYear(String year) {

		this.year = year;
	}

}
