package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bvas.insight.utilities.InsightUtils;

@Entity
@Table(name = "address")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "addr1")
	private String addr1;

	@Column(name = "addr2")
	private String addr2;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "datecreated")
	private Date datecreated;

	@Column(name = "ext")
	private String ext;

	@Column(name = "fax")
	private String fax;

	@Id
	@Column(name = "id")
	private String id;

	@Id
	@Column(name = "invoicenumber")
	private Integer invoicenumber;

	@Column(name = "phone")
	private String phone;

	@Column(name = "postalcode")
	private String postalcode;

	@Column(name = "region")
	private String region;

	@Column(name = "state")
	private String state;

	@Id
	@Column(name = "type")
	private String type;

	@Id
	@Column(name = "who")
	private String who;

	public Address() {
		super();
	}

	public Address(String type) {
		super();
		this.id = "0";
		this.type = type;
		this.who = "";
		this.invoicenumber = 0;
		this.datecreated = InsightUtils.getCurrentSQLDate();
		this.addr1 = "";
		this.addr2 = "";
		this.city = "";
		this.state = "";
		this.postalcode = "";
		this.country = "";
		this.phone = "";
		this.ext = "";
		this.fax = "";
		this.region = "";
	}

	public Address(String id, String type, String who, Integer invoicenumber, Date datecreated, String addr1,
			String addr2, String city, String state, String postalcode, String country, String phone, String ext,
			String fax, String region) {
		super();
		this.id = id;
		this.type = type;
		this.who = who;
		this.invoicenumber = invoicenumber;
		this.datecreated = datecreated;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.city = city;
		this.state = state;
		this.postalcode = postalcode;
		this.country = country;
		this.phone = phone;
		this.ext = ext;
		this.fax = fax;
		this.region = region;
	}

	public String getAddr1() {

		return addr1;
	}

	public String getAddr2() {

		return addr2;
	}

	public String getCity() {

		return city;
	}

	public String getCountry() {

		return country;
	}

	public Date getDatecreated() {

		return datecreated;
	}

	public String getExt() {

		return ext;
	}

	public String getFax() {

		return fax;
	}

	public String getId() {

		return id;
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public String getPhone() {

		return phone;
	}

	public String getPostalcode() {

		return postalcode;
	}

	public String getRegion() {

		return region;
	}

	public String getState() {

		return state;
	}

	public String getType() {

		return type;
	}

	public String getWho() {

		return who;
	}

	public void setAddr1(String addr1) {

		this.addr1 = addr1;
	}

	public void setAddr2(String addr2) {

		this.addr2 = addr2;
	}

	public void setCity(String city) {

		this.city = city;
	}

	public void setCountry(String country) {

		this.country = country;
	}

	public void setDatecreated(Date datecreated) {

		this.datecreated = datecreated;
	}

	public void setExt(String ext) {

		this.ext = ext;
	}

	public void setFax(String fax) {

		this.fax = fax;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	public void setPostalcode(String postalcode) {

		this.postalcode = postalcode;
	}

	public void setRegion(String region) {

		this.region = region;
	}

	public void setState(String state) {

		this.state = state;
	}

	public void setType(String type) {

		this.type = type;
	}

	public void setWho(String who) {

		this.who = who;
	}
}
