package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class LocalDetailsPartsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	public String companyname;

	public Date dateentered;

	public Integer invoiceno;

	public String partno;

	public BigDecimal price;

	public Integer quantity;

	public LocalDetailsPartsHistory() {
		super();
	}

	public LocalDetailsPartsHistory(Date dateentered, Integer invoiceno, String partno, BigDecimal price,
			Integer quantity, String companyname) {
		super();
		this.dateentered = dateentered;
		this.invoiceno = invoiceno;
		this.partno = partno;
		this.price = price;
		this.quantity = quantity;
		this.companyname = companyname;
	}

	public String getCompanyname() {

		return companyname;
	}

	public Date getDateentered() {

		return dateentered;
	}

	public Integer getInvoiceno() {

		return invoiceno;
	}

	public String getPartno() {

		return partno;
	}

	public BigDecimal getPrice() {

		return price;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setDateentered(Date dateentered) {

		this.dateentered = dateentered;
	}

	public void setInvoiceno(Integer invoiceno) {

		this.invoiceno = invoiceno;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

}
