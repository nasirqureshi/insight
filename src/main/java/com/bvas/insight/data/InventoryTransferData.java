package com.bvas.insight.data;

public class InventoryTransferData {

	public String location;
	public String MakeModelName;
	public String manufacturername;
	public String PartDescription;
	public String PartNo;
	public Integer unitsinstock;

	public Integer unitsonorder;
	public String year;

	public String getLocation() {
		return location;
	}

	public String getMakeModelName() {
		return MakeModelName;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public String getPartDescription() {
		return PartDescription;
	}

	public String getPartNo() {
		return PartNo;
	}

	public Integer getUnitsinstock() {
		return unitsinstock;
	}

	public Integer getUnitsonorder() {
		return unitsonorder;
	}

	public String getYear() {
		return year;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMakeModelName(String makeModelName) {
		MakeModelName = makeModelName;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public void setPartDescription(String partDescription) {
		PartDescription = partDescription;
	}

	public void setPartNo(String partNo) {
		PartNo = partNo;
	}

	public void setUnitsinstock(Integer unitsinstock) {
		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {
		this.unitsonorder = unitsonorder;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
