package com.bvas.insight.data;

public class StockData {

	public String partno;

	public Integer reorderlevel;

	public Integer returncount;

	public Integer safetyquantity;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public String getPartno() {

		return partno;
	}

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public Integer getReturncount() {
		return returncount;
	}

	public Integer getSafetyquantity() {
		return safetyquantity;
	}

	public Integer getUnitsinstock() {

		return unitsinstock;
	}

	public Integer getUnitsonorder() {

		return unitsonorder;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setReturncount(Integer returncount) {
		this.returncount = returncount;
	}

	public void setSafetyquantity(Integer safetyquantity) {
		this.safetyquantity = safetyquantity;
	}

	public void setUnitsinstock(Integer unitsinstock) {

		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {

		this.unitsonorder = unitsonorder;
	}
}
