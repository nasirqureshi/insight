package com.bvas.insight.data;

public class Label {

	public String interchangeno = "";

	public String keystonenumber = "";

	public String location = "";

	public String locationCH = "";

	public String locationGR = "";

	public String locationMP = "";

	public String makemodelname = "";

	public String oemnumber = "";

	public String partdescription = "";

	public String partno = "";

	public String year = "";

	public Label() {
		super();
	}

	public Label(String interchangeno, String keystonenumber, String makemodelname, String oemnumber,
			String partdescription, String partno, String year, String location, String locationCH, String locationGR,
			String locationMP) {
		super();
		this.interchangeno = interchangeno;
		this.keystonenumber = keystonenumber;
		this.makemodelname = makemodelname;
		this.oemnumber = oemnumber;
		this.partdescription = partdescription;
		this.partno = partno;
		this.year = year;
		this.location = location;
		this.locationCH = locationCH;
		this.locationGR = locationGR;
		this.locationMP = locationMP;
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

	public String getLocationCH() {
		return locationCH;
	}

	public String getLocationGR() {
		return locationGR;
	}

	public String getLocationMP() {
		return locationMP;
	}

	public String getMakemodelname() {
		return makemodelname;
	}

	public String getOemnumber() {
		return oemnumber;
	}

	public String getPartdescription() {
		return partdescription;
	}

	public String getPartno() {
		return partno;
	}

	public String getYear() {
		return year;
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

	public void setLocationCH(String locationCH) {
		this.locationCH = locationCH;
	}

	public void setLocationGR(String locationGR) {
		this.locationGR = locationGR;
	}

	public void setLocationMP(String locationMP) {
		this.locationMP = locationMP;
	}

	public void setMakemodelname(String makemodelname) {
		this.makemodelname = makemodelname;
	}

	public void setOemnumber(String oemnumber) {
		this.oemnumber = oemnumber;
	}

	public void setPartdescription(String partdescription) {
		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
