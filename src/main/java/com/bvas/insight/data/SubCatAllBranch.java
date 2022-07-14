package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubCatAllBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal chicagoquantity;

	public BigDecimal grandrapidsquantity;

	public String makemodelname;

	public String manufacturername;

	public BigDecimal melrosequantity;

	public String partdescription;

	public String partno;

	public BigDecimal total;

	public SubCatAllBranch() {
		super();
	}

	public SubCatAllBranch(BigDecimal melrosequantity, BigDecimal chicagoquantity, BigDecimal grandrapidsquantity,
			String makemodelname, String manufacturername, String partdescription, String partno, BigDecimal total) {
		super();
		this.melrosequantity = melrosequantity;
		this.chicagoquantity = chicagoquantity;
		this.grandrapidsquantity = grandrapidsquantity;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;

		this.partdescription = partdescription;
		this.partno = partno;
		this.total = total;
	}

	public BigDecimal getChicagoquantity() {

		return chicagoquantity;
	}

	public BigDecimal getGrandrapidsquantity() {

		return grandrapidsquantity;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public String getManufacturername() {

		return manufacturername;
	}

	public BigDecimal getMelrosequantity() {

		return melrosequantity;
	}

	public String getPartdescription() {

		return partdescription;
	}

	public String getPartno() {

		return partno;
	}

	public BigDecimal getTotal() {

		return total;
	}

	public void setChicagoquantity(BigDecimal chicagoquantity) {

		this.chicagoquantity = chicagoquantity;
	}

	public void setGrandrapidsquantity(BigDecimal grandrapidsquantity) {

		this.grandrapidsquantity = grandrapidsquantity;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {

		this.manufacturername = manufacturername;
	}

	public void setMelrosequantity(BigDecimal melrosequantity) {

		this.melrosequantity = melrosequantity;
	}

	public void setPartdescription(String partdescription) {

		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setTotal(BigDecimal total) {

		this.total = total;
	}

}
