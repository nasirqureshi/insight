package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class InventoryPartsTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal actualprice;

	public String capa;

	public BigDecimal costprice;

	public String dpinumber;

	public String keystonenumber;

	public String makemodelname;

	public String manufacturername;

	public String ordertype;

	public String partdescription;

	public String partno;

	public BigDecimal percent;

	public BigInteger quantitytoorder;

	public Integer reorderlevel;

	public Integer safetyquantity;

	public BigInteger sales1yearback;

	public BigInteger sales2yearback;

	public BigInteger sales3yearback;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public String year;

	public InventoryPartsTransfer() {
		super();
	}

	public InventoryPartsTransfer(String partno, String manufacturername, String makemodelname, String year,
			String capa, String ordertype, String partdescription, Integer unitsinstock, Integer unitsonorder,
			Integer reorderlevel, Integer safetyquantity, String dpinumber, String keystonenumber,
			BigDecimal actualprice, BigDecimal costprice, BigDecimal percent, BigInteger quantitytoorder) {
		super();
		this.partno = partno.trim();
		this.manufacturername = manufacturername.trim();
		this.makemodelname = makemodelname.trim();
		this.year = year.trim();
		this.capa = capa.trim();
		this.ordertype = ordertype.trim();
		this.partdescription = partdescription.trim();
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.reorderlevel = reorderlevel;
		this.safetyquantity = safetyquantity;
		this.quantitytoorder = quantitytoorder;
		this.dpinumber = dpinumber.trim();
		this.keystonenumber = keystonenumber.trim();
		this.actualprice = actualprice;
		this.costprice = costprice;
		this.percent = percent;
		this.sales1yearback = new BigInteger("0");
		this.sales1yearback = new BigInteger("0");
		this.sales1yearback = new BigInteger("0");

	}

	public BigDecimal getActualprice() {
		return actualprice;
	}

	public String getCapa() {
		return capa;
	}

	public BigDecimal getCostprice() {
		return costprice;
	}

	public String getDpinumber() {
		return dpinumber;
	}

	public String getKeystonenumber() {
		return keystonenumber;
	}

	public String getMakemodelname() {
		return makemodelname;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public String getPartdescription() {
		return partdescription;
	}

	public String getPartno() {
		return partno;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public BigInteger getQuantitytoorder() {
		return quantitytoorder;
	}

	public Integer getReorderlevel() {
		return reorderlevel;
	}

	public Integer getSafetyquantity() {
		return safetyquantity;
	}

	public BigInteger getSales1yearback() {
		return sales1yearback;
	}

	public BigInteger getSales2yearback() {
		return sales2yearback;
	}

	public BigInteger getSales3yearback() {
		return sales3yearback;
	}

	public Integer getUnitsinstock() {
		return unitsinstock;
	}

	public Integer getUnitsonorder() {
		return unitsonorder;
	}

	public String getYear() {
		return year;
	}

	public void setActualprice(BigDecimal actualprice) {
		this.actualprice = actualprice;
	}

	public void setCapa(String capa) {
		this.capa = capa;
	}

	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}

	public void setDpinumber(String dpinumber) {
		this.dpinumber = dpinumber;
	}

	public void setKeystonenumber(String keystonenumber) {
		this.keystonenumber = keystonenumber;
	}

	public void setMakemodelname(String makemodelname) {
		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public void setPartdescription(String partdescription) {
		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public void setQuantitytoorder(BigInteger quantitytoorder) {
		this.quantitytoorder = quantitytoorder;
	}

	public void setReorderlevel(Integer reorderlevel) {
		this.reorderlevel = reorderlevel;
	}

	public void setSafetyquantity(Integer safetyquantity) {
		this.safetyquantity = safetyquantity;
	}

	public void setSales1yearback(BigInteger sales1yearback) {
		this.sales1yearback = sales1yearback;
	}

	public void setSales2yearback(BigInteger sales2yearback) {
		this.sales2yearback = sales2yearback;
	}

	public void setSales3yearback(BigInteger sales3yearback) {
		this.sales3yearback = sales3yearback;
	}

	public void setUnitsinstock(Integer unitsinstock) {
		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {
		this.unitsonorder = unitsonorder;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
