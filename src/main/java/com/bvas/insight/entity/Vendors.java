package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendors implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "Comments")
	public String comments;

	@Column(name = "CompanyName")
	public String companyname;

	@Column(name = "Companytype")
	public String companytype;

	@Column(name = "ContactName")
	public String contactname;

	@Column(name = "ContactTitle")
	public String contacttitle;

	@Column(name = "eMail")
	public String email;

	@Column(name = "HomePage")
	public String homepage;

	@Id
	@Column(name = "SupplierID")
	public Integer supplierid;

	public Vendors() {
		super();
	}

	public Vendors(Integer supplierid, String companyname, String contactname, String contacttitle, String homepage,
			String email, String comments, String companytype) {
		super();
		this.supplierid = supplierid;
		this.companyname = companyname;
		this.contactname = contactname;
		this.contacttitle = contacttitle;
		this.homepage = homepage;
		this.email = email;
		this.comments = comments;
		this.companytype = companytype;
	}

	public String getComments() {

		return comments;
	}

	public String getCompanyname() {

		return companyname;
	}

	public String getCompanytype() {

		return companytype;
	}

	public String getContactname() {

		return contactname;
	}

	public String getContacttitle() {

		return contacttitle;
	}

	public String getEmail() {

		return email;
	}

	public String getHomepage() {

		return homepage;
	}

	public Integer getSupplierid() {

		return supplierid;
	}

	public void setComments(String comments) {

		this.comments = comments;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setCompanytype(String companytype) {

		this.companytype = companytype;
	}

	public void setContactname(String contactname) {

		this.contactname = contactname;
	}

	public void setContacttitle(String contacttitle) {

		this.contacttitle = contacttitle;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public void setHomepage(String homepage) {

		this.homepage = homepage;
	}

	public void setSupplierid(Integer supplierid) {

		this.supplierid = supplierid;
	}
}
