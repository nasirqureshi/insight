package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartsPartHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal actualprice;

	public BigDecimal costprice;

	public String interchangeno;

	public String keystonenumber;

	public String location;

	public String make;

	public String model;

	public String partdescription;

	public String partno;

	public Integer reorderlevel;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public Integer yearfrom;

	public Integer yearto;

	public PartsPartHistory() {
		super();
	}

	public PartsPartHistory(String make, String model, BigDecimal actualprice, BigDecimal costprice,
			String interchangeno, String keystonenumber, String location, String partdescription, String partno,
			Integer reorderlevel, Integer unitsinstock, Integer unitsonorder, Integer yearfrom, Integer yearto) {
		super();
		this.make = make;
		this.model = model;
		this.actualprice = actualprice;
		this.costprice = costprice;
		this.interchangeno = interchangeno;
		this.keystonenumber = keystonenumber;
		this.location = location;
		this.partdescription = partdescription;
		this.partno = partno;
		this.reorderlevel = reorderlevel;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.yearfrom = yearfrom;
		this.yearto = yearto;
	}

	public BigDecimal getActualprice() {

		return actualprice;
	}

	public BigDecimal getCostprice() {

		return costprice;
	}

	public String getInterchangeno() {

		return interchangeno;
	}

	public String getKeystonenumber() {

		return keystonenumber;
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

	public void setActualprice(BigDecimal actualprice) {

		this.actualprice = actualprice;
	}

	public void setCostprice(BigDecimal costprice) {

		this.costprice = costprice;
	}

	public void setInterchangeno(String interchangeno) {

		this.interchangeno = interchangeno;
	}

	public void setKeystonenumber(String keystonenumber) {

		this.keystonenumber = keystonenumber;
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
