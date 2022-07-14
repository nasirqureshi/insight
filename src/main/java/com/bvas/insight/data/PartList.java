package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.util.Comparator;

public class PartList implements Comparable<PartList> {

	public static Comparator<PartList> PartListComparator = new Comparator<PartList>() {

		@Override
		public int compare(PartList pl1, PartList pl2) {

			Integer noorder1 = pl1.getNoorder();
			Integer noorder2 = pl2.getNoorder();

			// ascending
			// order
			return noorder1.compareTo(noorder2);
		}
	};

	public String location;

	public String makemodelname;

	public Integer noorder;

	public String partdescription;

	public String partno;

	public Integer quantity;

	public Integer reorderlevel;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public String year;

	public PartList() {
		super();
	}

	public PartList(String location, String makemodelname, Integer noorder, String partdescription, String partno,
			Integer quantity, String year, Integer unitsinstock, Integer unitsonorder, Integer reorderlevel) {
		super();
		this.location = location;
		this.makemodelname = makemodelname;
		this.noorder = noorder;
		this.partdescription = partdescription;
		this.partno = partno;
		this.quantity = quantity;
		this.year = year;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.reorderlevel = reorderlevel;
	}

	@Override
	public int compareTo(PartList o) {

		int noorder = o.getNoorder();

		// ascending order
		return this.noorder - noorder;
	}

	public String getLocation() {

		return location;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public Integer getNoorder() {

		return noorder;
	}

	public String getPartdescription() {

		return partdescription;
	}

	public String getPartno() {

		return partno;
	}

	public Integer getQuantity() {

		return quantity;
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

	public String getYear() {

		return year;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setNoorder(Integer noorder) {

		this.noorder = noorder;
	}

	public void setPartdescription(String partdescription) {

		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
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

	public void setYear(String year) {

		this.year = year;
	}

}
