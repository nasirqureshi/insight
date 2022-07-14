package com.bvas.insight.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partschanges")
public class PartsChanges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "modifiedby")
	public String modifiedby;

	@Column(name = "modifieddate")
	public Date modifieddate;

	@Id
	@Column(name = "modifiedorder")
	public Integer modifiedorder;

	@Id
	@Column(name = "partno")
	public String partno;

	@Column(name = "remarks")
	public String remarks;

	public PartsChanges() {
		super();
	}

	public PartsChanges(String partno, String modifiedby, Date modifieddate, Integer modifiedorder, String remarks) {
		super();
		this.partno = partno;
		this.modifiedby = modifiedby;
		this.modifieddate = modifieddate;
		this.modifiedorder = modifiedorder;
		this.remarks = remarks;
	}

	public String getModifiedby() {

		return modifiedby;
	}

	public Date getModifieddate() {

		return modifieddate;
	}

	public Integer getModifiedorder() {

		return modifiedorder;
	}

	public String getPartno() {

		return partno;
	}

	public String getRemarks() {

		return remarks;
	}

	public void setModifiedby(String modifiedby) {

		this.modifiedby = modifiedby;
	}

	public void setModifieddate(Date modifieddate) {

		this.modifieddate = modifieddate;
	}

	public void setModifiedorder(Integer modifiedorder) {

		this.modifiedorder = modifiedorder;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}
}
