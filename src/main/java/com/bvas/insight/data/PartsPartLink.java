package com.bvas.insight.data;

import java.io.Serializable;

public class PartsPartLink implements Serializable {

	private static final long serialVersionUID = 1L;

	public String interchangeno;

	public String location;

	public String make;

	public String model;

	public String partdescription;

	public String partno;

	public String plink;

	public Integer reorderlevel;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public Integer yearfrom;

	public Integer yearto;

	public PartsPartLink() {
		super();

	}

	public PartsPartLink(String partno, String interchangeno, String plink, String make, String model,
			String partdescription, Integer yearfrom, Integer yearto, String location, Integer reorderlevel,
			Integer unitsinstock, Integer unitsonorder) {
		super();
		this.partno = partno;
		this.interchangeno = interchangeno;
		this.plink = plink;
		this.make = make;
		this.model = model;
		this.partdescription = partdescription;
		this.yearfrom = yearfrom;
		this.yearto = yearto;
		this.location = location;
		this.reorderlevel = reorderlevel;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
	}

	public String getInterchangeno() {

		return interchangeno;
	}

	public String getLocation() {

		return location;
	}

	public String getMake() {

		return make;
	}

	public String getModel() {

		return model;
	}

	public String getPartdescription() {

		return partdescription;
	}

	public String getPartno() {

		return partno;
	}

	public String getPlink() {

		return plink;
	}

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public Integer getUnitsinstock() {

		return unitsinstock;
	}

	public Integer getUnitsonorder() {

		return unitsonorder;
	}

	public Integer getYearfrom() {

		return yearfrom;
	}

	public Integer getYearto() {

		return yearto;
	}

	public void setInterchangeno(String interchangeno) {

		this.interchangeno = interchangeno;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public void setMake(String make) {

		this.make = make;
	}

	public void setModel(String model) {

		this.model = model;
	}

	public void setPartdescription(String partdescription) {

		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPlink(String plink) {

		this.plink = plink;
	}

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setUnitsinstock(Integer unitsinstock) {

		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {

		this.unitsonorder = unitsonorder;
	}

	public void setYearfrom(Integer yearfrom) {

		this.yearfrom = yearfrom;
	}

	public void setYearto(Integer yearto) {

		this.yearto = yearto;
	}
}
