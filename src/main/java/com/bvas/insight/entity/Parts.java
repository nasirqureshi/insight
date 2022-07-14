package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "parts")
public class Parts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "actualprice")
	public BigDecimal actualprice;

	@Transient
	private BigDecimal calculatedprice;

	@Column(name = "capa")
	public String capa;

	@Column(name = "costprice")
	public BigDecimal costprice;

	@Column(name = "dpinumber")
	public String dpinumber;

	@Column(name = "interchangeno")
	public String interchangeno;

	@Column(name = "keystonenumber")
	public String keystonenumber;

	@Column(name = "listprice")
	public BigDecimal listprice;

	@Column(name = "location")
	public String location;

	@Column(name = "makemodelcode")
	public String makemodelcode;

	@Column(name = "makemodelname")
	public String makemodelname;

	@Column(name = "manufacturername")
	public String manufacturername;

	@Column(name = "oemnumber")
	public String oemnumber;

	@Column(name = "orderno")
	public Integer orderno;

	@Column(name = "ordertype")
	public String ordertype;

	@Column(name = "partdescription")
	public String partdescription;

	@Id
	@Column(name = "partno")
	public String partno;

	@Column(name = "pricelock")
	public String pricelock;

	@Column(name = "reorderlevel")
	public Integer reorderlevel;

	@Column(name = "returncount")
	public Integer returncount;

	@Column(name = "safetyquantity")
	public Integer safetyquantity;

	@Column(name = "sortcode")
	public String sortcode;

	@Column(name = "subcategory")
	public String subcategory;

	@Column(name = "supplierid")
	public Integer supplierid;

	@Column(name = "unitsinstock")
	public Integer unitsinstock;

	@Column(name = "unitsonorder")
	public Integer unitsonorder;

	@Column(name = "wholesaleprice")
	public BigDecimal wholesaleprice;

	@Column(name = "year")
	public String year;

	@Column(name = "yearfrom")
	public Integer yearfrom;

	@Column(name = "yearto")
	public Integer yearto;

	public Parts() {
		super();
	}

	public Parts(BigDecimal actualprice, BigDecimal calculatedprice, String capa, BigDecimal costprice,
			String dpinumber, String interchangeno, String keystonenumber, BigDecimal listprice, String location,
			String makemodelcode, String makemodelname, String manufacturername, String oemnumber, Integer orderno,
			String partdescription, String partno, Integer reorderlevel, Integer safetyquantity, String subcategory,
			Integer supplierid, Integer unitsinstock, Integer unitsonorder, String year, Integer yearfrom,
			Integer yearto, String ordertype, BigDecimal wholesaleprice, String sortcode, String pricelock,
			Integer returncount) {
		super();
		this.actualprice = actualprice;
		this.calculatedprice = calculatedprice;
		this.capa = capa;
		this.costprice = costprice;
		this.dpinumber = dpinumber;
		this.interchangeno = interchangeno;
		this.keystonenumber = keystonenumber;
		this.listprice = listprice;
		this.location = location;
		this.makemodelcode = makemodelcode;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.oemnumber = oemnumber;
		this.orderno = orderno;
		this.partdescription = partdescription;
		this.partno = partno;
		this.reorderlevel = reorderlevel;
		this.safetyquantity = safetyquantity;
		this.subcategory = subcategory;
		this.supplierid = supplierid;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.year = year;
		this.yearfrom = yearfrom;
		this.yearto = yearto;
		this.ordertype = ordertype;
		this.wholesaleprice = wholesaleprice;
		this.sortcode = sortcode;
		this.pricelock = pricelock;
		this.returncount = returncount;
	}

	public Parts(String partno) {
		super();
		this.actualprice = new BigDecimal("0.00");
		this.calculatedprice = new BigDecimal("0.00");
		this.capa = "N";
		this.costprice = new BigDecimal("0.00");
		this.dpinumber = "";
		this.interchangeno = "";
		this.keystonenumber = "";
		this.listprice = new BigDecimal("0.00");
		this.location = "";
		this.makemodelcode = "";
		this.makemodelname = "";
		this.manufacturername = "";
		this.oemnumber = "";
		this.orderno = 0;
		this.partdescription = "";
		this.partno = partno;
		this.reorderlevel = 0;
		this.safetyquantity = 0;
		this.subcategory = "";
		this.supplierid = 0;
		this.unitsinstock = 0;
		this.unitsonorder = 0;
		this.year = "";
		this.yearfrom = 0000;
		this.yearto = 0000;
		this.ordertype = "N";
		this.wholesaleprice = new BigDecimal("0.00");
		this.sortcode = "000";
		this.pricelock = "N";
		this.returncount = 0;
	}

	public BigDecimal getActualprice() {
		return actualprice;
	}

	public BigDecimal getCalculatedprice() {
		return calculatedprice;
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

	public String getInterchangeno() {
		return interchangeno;
	}

	public String getKeystonenumber() {
		return keystonenumber;
	}

	public BigDecimal getListprice() {
		return listprice;
	}

	public String getLocation() {
		return location;
	}

	public String getMakemodelcode() {
		return makemodelcode;
	}

	public String getMakemodelname() {
		return makemodelname;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public String getOemnumber() {
		return oemnumber;
	}

	public Integer getOrderno() {
		return orderno;
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

	public String getPricelock() {
		return pricelock;
	}

	public Integer getReorderlevel() {
		return reorderlevel;
	}

	public Integer getReturncount() {
		return returncount;
	}

	public Integer getSafetyquantity() {
		return safetyquantity;
	}

	public String getSortcode() {
		return sortcode;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public Integer getUnitsinstock() {
		return unitsinstock;
	}

	public Integer getUnitsonorder() {
		return unitsonorder;
	}

	public BigDecimal getWholesaleprice() {
		return wholesaleprice;
	}

	public String getYear() {
		return year;
	}

	public Integer getYearfrom() {
		return yearfrom;
	}

	public Integer getYearto() {
		return yearto;
	}

	public void setActualprice(BigDecimal actualprice) {
		this.actualprice = actualprice;
	}

	public void setCalculatedprice(BigDecimal calculatedprice) {
		this.calculatedprice = calculatedprice;
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

	public void setInterchangeno(String interchangeno) {
		this.interchangeno = interchangeno;
	}

	public void setKeystonenumber(String keystonenumber) {
		this.keystonenumber = keystonenumber;
	}

	public void setListprice(BigDecimal listprice) {
		this.listprice = listprice;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMakemodelcode(String makemodelcode) {
		this.makemodelcode = makemodelcode;
	}

	public void setMakemodelname(String makemodelname) {
		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public void setOemnumber(String oemnumber) {
		this.oemnumber = oemnumber;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
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

	public void setPricelock(String pricelock) {
		this.pricelock = pricelock;
	}

	public void setReorderlevel(Integer reorderlevel) {
		this.reorderlevel = reorderlevel;
	}

	public void setReturncount(Integer returncount) {
		this.returncount = returncount;
	}

	public void setSafetyquantity(Integer safetyquantity) {
		this.safetyquantity = safetyquantity;
	}

	public void setSortcode(String sortcode) {
		this.sortcode = sortcode;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}

	public void setUnitsinstock(Integer unitsinstock) {
		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {
		this.unitsonorder = unitsonorder;
	}

	public void setWholesaleprice(BigDecimal wholesaleprice) {
		this.wholesaleprice = wholesaleprice;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setYearfrom(Integer yearfrom) {
		this.yearfrom = yearfrom;
	}

	public void setYearto(Integer yearto) {
		this.yearto = yearto;
	}

}
