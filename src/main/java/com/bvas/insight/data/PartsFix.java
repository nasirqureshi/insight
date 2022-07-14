package com.bvas.insight.data;

import java.io.Serializable;

public class PartsFix implements Serializable {

	private static final long serialVersionUID = 1L;

	public String category;

	public String dpinumber;

	public String interchangeno;

	public String makemodelcode;

	public String makemodelname;

	public String oemnumber;

	public String othersidepartno;

	public String partdescription;

	public String partno;

	public String partslinknumber;

	public String subcategory;

	public String year;

	public String yearfrom;

	public String yearto;

	public PartsFix() {
		super();
	}

	public PartsFix(String makemodelcode, String partslinknumber, String oemnumber, String dpinumber,
			String makemodelname, String partno, String interchangeno, String othersidepartno, String partdescription,
			String yearfrom, String yearto, String year, String category, String subcategory) {
		super();
		this.makemodelcode = makemodelcode;
		this.partslinknumber = partslinknumber;
		this.oemnumber = oemnumber;
		this.dpinumber = dpinumber;
		this.makemodelname = makemodelname;
		this.partno = partno;
		this.interchangeno = interchangeno;
		this.othersidepartno = othersidepartno;
		this.partdescription = partdescription;
		this.yearfrom = yearfrom;
		this.yearto = yearto;
		this.year = year;
		this.category = category;
		this.subcategory = subcategory;
	}

	public String getCategory() {

		return this.category;
	}

	public String getDpinumber() {

		return this.dpinumber;
	}

	public String getInterchangeno() {

		return this.interchangeno;
	}

	public String getMakemodelcode() {

		return this.makemodelcode;
	}

	public String getMakemodelname() {

		return this.makemodelname;
	}

	public String getOemnumber() {

		return this.oemnumber;
	}

	public String getOthersidepartno() {

		return this.othersidepartno;
	}

	public String getPartdescription() {

		return this.partdescription;
	}

	public String getPartno() {

		return this.partno;
	}

	public String getPartslinknumber() {

		return this.partslinknumber;
	}

	public String getSubcategory() {

		return this.subcategory;
	}

	public String getYear() {

		return this.year;
	}

	public String getYearfrom() {

		return this.yearfrom;
	}

	public String getYearto() {

		return this.yearto;
	}

	public void setCategory(String category) {

		this.category = category;
	}

	public void setDpinumber(String dpinumber) {

		this.dpinumber = dpinumber;
	}

	public void setInterchangeno(String interchangeno) {

		this.interchangeno = interchangeno;
	}

	public void setMakemodelcode(String makemodelcode) {

		this.makemodelcode = makemodelcode;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setOemnumber(String oemnumber) {

		this.oemnumber = oemnumber;
	}

	public void setOthersidepartno(String othersidepartno) {

		this.othersidepartno = othersidepartno;
	}

	public void setPartdescription(String partdescription) {

		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPartslinknumber(String partslinknumber) {

		this.partslinknumber = partslinknumber;
	}

	public void setSubcategory(String subcategory) {

		this.subcategory = subcategory;
	}

	public void setYear(String year) {

		this.year = year;
	}

	public void setYearfrom(String yearfrom) {

		this.yearfrom = yearfrom;
	}

	public void setYearto(String yearto) {

		this.yearto = yearto;
	}

}
