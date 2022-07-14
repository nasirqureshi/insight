package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class FutureOrderDetailsPartsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	public String companyname;

	public Date estimatedarrivaldate;

	public Integer orderno;

	public String partno;

	public BigDecimal price;

	public Integer quantity;

	public FutureOrderDetailsPartsHistory() {
		super();
	}

	public FutureOrderDetailsPartsHistory(String partno, Integer orderno, Date estimatedarrivaldate, Integer quantity,
			String companyname, BigDecimal price) {
		super();
		this.partno = partno;
		this.orderno = orderno;
		this.estimatedarrivaldate = estimatedarrivaldate;
		this.quantity = quantity;
		this.companyname = companyname;
		this.price = price;
	}

	public String getcompanyname() {

		return companyname;
	}

	public Date getEstimatedarrivaldate() {

		return estimatedarrivaldate;
	}

	public Integer getOrderno() {

		return orderno;
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

	public void setcompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setEstimatedarrivaldate(Date estimatedarrivaldate) {

		this.estimatedarrivaldate = estimatedarrivaldate;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
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
