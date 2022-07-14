package com.bvas.insight.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "localvendors")
public class LocalVendors implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "comments")
	private String comments;

	@Column(name = "companyname")
	private String companyname;

	@Column(name = "contactname")
	private String contactname;

	@Column(name = "contacttitle")
	private String contacttitle;

	@Column(name = "email")
	private String email;

	@Column(name = "homepage")
	private String homepage;

	@Column(name = "shortcode")
	private String shortcode;

	@Id
	@Column(name = "supplierid")
	private Integer supplierid;

	public LocalVendors() {
		super();

	}

	public LocalVendors(String comments, String companyname, String contactname, String contacttitle, String email,
			String homepage, Integer supplierid, String shortcode) {
		super();
		this.comments = comments;
		this.companyname = companyname;
		this.contactname = contactname;
		this.contacttitle = contacttitle;
		this.email = email;
		this.homepage = homepage;
		this.supplierid = supplierid;
		this.shortcode = shortcode;
	}

	public String getComments() {

		return comments;
	}

	public String getCompanyname() {

		return companyname;
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

	public String getShortcode() {

		return shortcode;
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

	public void setShortcode(String shortcode) {

		this.shortcode = shortcode;
	}

	public void setSupplierid(Integer supplierid) {

		this.supplierid = supplierid;
	}

}
