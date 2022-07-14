package com.bvas.insight.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "branch")
public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "branchcode")
	public String branchcode;

	@Column(name = "branchname")
	public String branchname;

	@Column(name = "tax")
	public BigDecimal tax;

	public Branch() {
		super();
	}

	public Branch(String branchcode, String branchname, BigDecimal tax) {
		super();
		this.branchcode = branchcode;
		this.branchname = branchname;
		this.tax = tax;
	}

	public String getBranchcode() {

		return branchcode;
	}

	public String getBranchname() {

		return branchname;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setBranchcode(String branchcode) {

		this.branchcode = branchcode;
	}

	public void setBranchname(String branchname) {

		this.branchname = branchname;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

}
