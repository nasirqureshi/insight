package com.bvas.insight.data;

import java.math.BigDecimal;

public class UpdateVendorParts {

	private int excelRowNum;

	private String oemNo;

	private String partNo;

	private String plno;

	private BigDecimal sellingRate;

	private Integer supplierId;

	private String vendorPartNo;

	public int getExcelRowNum() {

		return excelRowNum;
	}

	public String getOemNo() {

		return oemNo;
	}

	public String getPartNo() {

		return partNo;
	}

	public String getPlno() {

		return plno;
	}

	public BigDecimal getSellingRate() {

		return sellingRate;
	}

	public Integer getSupplierId() {

		return supplierId;
	}

	public String getVendorPartNo() {

		return vendorPartNo;
	}

	public void setExcelRowNum(int excelRowNum) {

		this.excelRowNum = excelRowNum;
	}

	public void setOemNo(String oemNo) {

		this.oemNo = oemNo;
	}

	public void setPartNo(String partNo) {

		this.partNo = partNo;
	}

	public void setPlno(String plno) {

		this.plno = plno;
	}

	public void setSellingRate(BigDecimal sellingRate) {

		this.sellingRate = sellingRate;
	}

	public void setSupplierId(Integer supplierId) {

		this.supplierId = supplierId;
	}

	public void setVendorPartNo(String vendorPartNo) {

		this.vendorPartNo = vendorPartNo;
	}

}
