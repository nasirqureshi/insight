package com.bvas.insight.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "categorycode")
	public String categorycode;

	@Column(name = "categoryname")
	public String categoryname;

	public Category() {
		super();
	}

	public Category(String categorycode, String categoryname) {
		super();
		this.categorycode = categorycode;
		this.categoryname = categoryname;
	}

	public String getCategorycode() {

		return categorycode;
	}

	public String getCategoryname() {

		return categoryname;
	}

	public void setCategorycode(String categorycode) {

		this.categorycode = categorycode;
	}

	public void setCategoryname(String categoryname) {

		this.categoryname = categoryname;
	}

}
