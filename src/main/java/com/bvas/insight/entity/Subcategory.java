package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subcategory")
public class Subcategory {

	@Column(name = "categorycode", columnDefinition = "char(1)")
	public String categorycode;

	@Id
	@Column(name = "subcategorycode")
	public String subcategorycode;

	@Column(name = "subcategoryname")
	public String subcategoryname;

	public Subcategory() {
		super();
	}

	public Subcategory(String subcategorycode, String subcategoryname, String categorycode) {
		super();
		this.subcategorycode = subcategorycode;
		this.subcategoryname = subcategoryname;
		this.categorycode = categorycode;
	}

	public String getCategorycode() {

		return categorycode;
	}

	public String getSubcategorycode() {

		return subcategorycode;
	}

	public String getSubcategoryname() {

		return subcategoryname;
	}

	public void setCategorycode(String categorycode) {

		this.categorycode = categorycode;
	}

	public void setSubcategorycode(String subcategorycode) {

		this.subcategorycode = subcategorycode;
	}

	public void setSubcategoryname(String subcategoryname) {

		this.subcategoryname = subcategoryname;
	}
}
