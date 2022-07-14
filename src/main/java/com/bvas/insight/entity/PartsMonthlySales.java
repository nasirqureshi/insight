package com.bvas.insight.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partsmonthlysales")
public class PartsMonthlySales implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "monthval")
	private String monthval;

	@Id
	@Column(name = "partno")
	private String partno;

	@Column(name = "salescount")
	private Integer salescount;

	@Id
	@Column(name = "yearmonth")
	private Integer yearmonth;

	public PartsMonthlySales() {
		super();
	}

	public PartsMonthlySales(Integer yearmonth, String partno, Integer salescount, String monthval) {
		super();
		this.yearmonth = yearmonth;
		this.partno = partno;
		this.salescount = salescount;
		this.monthval = monthval;
	}

	public String getMonthval() {
		return monthval;
	}

	public String getPartno() {
		return partno;
	}

	public Integer getSalescount() {
		return salescount;
	}

	public Integer getYearmonth() {
		return yearmonth;
	}

	public void setMonthval(String monthval) {
		this.monthval = monthval;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setSalescount(Integer salescount) {
		this.salescount = salescount;
	}

	public void setYearmonth(Integer yearmonth) {
		this.yearmonth = yearmonth;
	}

}
