package com.bvas.insight.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Shoaib
 */
@Entity
@Table(name = "stockaudit")
public class StockAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "changedesc")
	private String changeDesc;

	@Column(name = "chnge")
	private Integer chnge;

	@Column(name = "dateenter")
	private Date dateenter;

	@Column(name = "olde")
	private Integer olde;

	@Column(name = "partno")
	private String partNo;

	@Id
	@Column(name = "serial")
	private Integer serial;

	public StockAudit() {
	}

	public StockAudit(Integer serial, Date dateenter, String changeDesc, String partNo, Integer chnge, Integer olde) {
		super();
		this.serial = serial;
		this.dateenter = dateenter;
		this.changeDesc = changeDesc;
		this.partNo = partNo;
		this.chnge = chnge;
		this.olde = olde;
	}

	public String getChangeDesc() {
		return changeDesc;
	}

	public Integer getChnge() {
		return chnge;
	}

	public Date getDateenter() {
		return dateenter;
	}

	public Integer getOlde() {
		return olde;
	}

	public String getPartNo() {
		return partNo;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	public void setChnge(Integer chnge) {
		this.chnge = chnge;
	}

	public void setDateenter(Date dateenter) {
		this.dateenter = dateenter;
	}

	public void setOlde(Integer olde) {
		this.olde = olde;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
