package com.bvas.insight.data;

import java.util.Date;

/**
 *
 * @author Shoaib
 */
public class StockAuditData {
	private String changeDesc;

	private Integer chnge;

	private Date dateenter;

	private Integer olde;

	private String partNo;

	private Integer serial;

	public StockAuditData() {
		super();
	}

	public StockAuditData(Integer serial, Date dateenter, String changeDesc, String partNo, Integer chnge,
			Integer olde) {
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
