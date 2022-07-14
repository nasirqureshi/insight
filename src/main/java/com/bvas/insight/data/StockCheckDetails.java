package com.bvas.insight.data;

import java.math.BigDecimal;

public class StockCheckDetails {

	public String comments;

	public String companyname;

	public BigDecimal sellingrate;

	public String vendorpartno;

	public String getComments() {

		return comments;
	}

	public String getCompanyname() {

		return companyname;
	}

	public BigDecimal getSellingrate() {

		return sellingrate;
	}

	public String getVendorpartno() {

		return vendorpartno;
	}

	public void setComments(String comments) {

		this.comments = comments;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setSellingrate(BigDecimal sellingrate) {

		this.sellingrate = sellingrate;
	}

	public void setVendorpartno(String vendorpartno) {

		this.vendorpartno = vendorpartno;
	}
}
