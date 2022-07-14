package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partslink")
public class PartsLink implements Serializable {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "cert")
	public String cert;

	@Id
	@Column(name = "ID")
	public Integer id;

	@Column(name = "make")
	public String make;

	@Column(name = "MLONG")
	public String mlong;

	@Column(name = "model")
	public String model;

	@Column(name = "mvariables")
	public String mvariables;

	@Column(name = "notes")
	public String notes;

	@Column(name = "oem")
	public String oem;

	@Column(name = "oeprice")
	public BigDecimal oeprice;

	@Column(name = "oldprice")
	public BigDecimal oldprice;

	@Column(name = "partno")
	public String partno;

	@Column(name = "plink")
	public String plink;

	@Column(name = "PNAME")
	public String pname;

	@Column(name = "pricedate")
	public Date pricedate;

	@Column(name = "y1")
	public Integer y1;

	@Column(name = "y2")
	public Integer y2;

	public PartsLink() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PartsLink(Integer id, String cert, String mlong, String make, String model, String notes, String oem,
			BigDecimal oeprice, BigDecimal oldprice, String pname, String mvariables, String plink, String partno,
			Date pricedate, Integer y1, Integer y2) {
		super();
		this.id = id;
		this.cert = cert;
		this.mlong = mlong;
		this.make = make;
		this.model = model;
		this.notes = notes;
		this.oem = oem;
		this.oeprice = oeprice;
		this.oldprice = oldprice;
		this.pname = pname;
		this.mvariables = mvariables;
		this.plink = plink;
		this.partno = partno;
		this.pricedate = pricedate;
		this.y1 = y1;
		this.y2 = y2;
	}

	public String getCert() {
		return cert;
	}

	public Integer getId() {
		return id;
	}

	public String getMake() {
		return make;
	}

	public String getMlong() {
		return mlong;
	}

	public String getModel() {
		return model;
	}

	public String getMvariables() {
		return mvariables;
	}

	public String getNotes() {
		return notes;
	}

	public String getOem() {
		return oem;
	}

	public BigDecimal getOeprice() {
		return oeprice;
	}

	public BigDecimal getOldprice() {
		return oldprice;
	}

	public String getPartno() {
		return partno;
	}

	public String getPlink() {
		return plink;
	}

	public String getPname() {
		return pname;
	}

	public Date getPricedate() {
		return pricedate;
	}

	public Integer getY1() {
		return y1;
	}

	public Integer getY2() {
		return y2;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setMlong(String mlong) {
		this.mlong = mlong;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setMvariables(String mvariables) {
		this.mvariables = mvariables;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public void setOeprice(BigDecimal oeprice) {
		this.oeprice = oeprice;
	}

	public void setOldprice(BigDecimal oldprice) {
		this.oldprice = oldprice;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setPlink(String plink) {
		this.plink = plink;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public void setPricedate(Date pricedate) {
		this.pricedate = pricedate;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}

	public void setY2(Integer y2) {
		this.y2 = y2;
	}

}
