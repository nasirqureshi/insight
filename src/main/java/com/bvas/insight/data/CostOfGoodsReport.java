package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

public class CostOfGoodsReport implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal discount;

	public Integer invoicenumber;

	public BigDecimal invoicetotal;

	public BigDecimal margin;

	public BigDecimal ourprice;

	public BigDecimal soldprice;

	public BigDecimal tax;

	public CostOfGoodsReport() {
		super();
	}

	public CostOfGoodsReport(Integer invoicenumber, BigDecimal invoicetotal, BigDecimal discount, BigDecimal ourprice,
			BigDecimal soldprice, BigDecimal margin, BigDecimal tax) {
		super();
		this.invoicenumber = invoicenumber;
		this.invoicetotal = invoicetotal;
		this.discount = discount;
		this.ourprice = ourprice;
		this.soldprice = soldprice;
		this.margin = margin;
		this.tax = tax;
	}

	public BigDecimal getDiscount() {

		return discount;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public BigDecimal getInvoicetotal() {

		return invoicetotal;
	}

	public BigDecimal getMargin() {

		return margin;
	}

	public BigDecimal getOurprice() {

		return ourprice;
	}

	public BigDecimal getSoldprice() {

		return soldprice;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setDiscount(BigDecimal discount) {

		this.discount = discount;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setInvoicetotal(BigDecimal invoicetotal) {

		this.invoicetotal = invoicetotal;
	}

	public void setMargin(BigDecimal margin) {

		this.margin = margin;
	}

	public void setOurprice(BigDecimal ourprice) {

		this.ourprice = ourprice;
	}

	public void setSoldprice(BigDecimal soldprice) {

		this.soldprice = soldprice;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
}
