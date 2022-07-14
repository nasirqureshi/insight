package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bvas.insight.entity.Parts;

public class DisplayInvoiceDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BigDecimal actualprice;

	public Integer invoicenumber;

	public BigDecimal listprice;

	public String location;

	public String makemodelname;

	public String manufacturername;

	public String partdescription;

	public String partnumber;

	public Integer quantity;

	public Integer reorderlevel;

	public BigDecimal soldprice;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public String year;

	public DisplayInvoiceDetail() {
		super();
	}

	public DisplayInvoiceDetail(BigDecimal actualprice, Integer invoicenumber, String partnumber, Integer quantity,
			BigDecimal soldprice, String makemodelname, String manufacturername, String partdescription,
			Integer reorderlevel, Integer unitsinstock, Integer unitsonorder, String year, BigDecimal listprice,
			String location) {
		super();
		this.actualprice = actualprice;
		this.invoicenumber = invoicenumber;
		this.partnumber = partnumber;
		this.quantity = quantity;
		this.soldprice = soldprice;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.partdescription = partdescription;
		this.reorderlevel = reorderlevel;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.year = year;
		this.listprice = listprice;
		this.location = location;
	}

	public DisplayInvoiceDetail(Parts part) {
		this.actualprice = part.getActualprice();
		this.invoicenumber = 0;
		this.partnumber = part.getPartno();
		this.quantity = 1;
		this.soldprice = part.getCostprice();
		this.makemodelname = part.getMakemodelname();
		this.manufacturername = part.getManufacturername();
		this.partdescription = part.getPartdescription();
		this.reorderlevel = part.getReorderlevel();
		this.unitsinstock = part.getUnitsinstock();
		this.unitsonorder = part.getUnitsonorder();
		this.year = part.getYear();
		this.listprice = part.getListprice();
		this.location = part.getLocation();
	}

	public DisplayInvoiceDetail(String string) {
		super();
		this.actualprice = new BigDecimal("0.00");
		;
		this.invoicenumber = 0;
		this.partnumber = "";
		this.quantity = 1;
		this.soldprice = new BigDecimal("0.00");
		this.makemodelname = "";
		this.manufacturername = "";
		this.partdescription = "";
		this.reorderlevel = 0;
		this.unitsinstock = 0;
		this.unitsonorder = 0;
		this.year = "00-00";
		this.listprice = new BigDecimal("0.00");
		this.location = "";
	}

	public BigDecimal getActualprice() {

		return actualprice.setScale(2, RoundingMode.HALF_EVEN);
	}

	public Integer getInvoicenumber() {

		return invoicenumber;
	}

	public BigDecimal getListprice() {

		return listprice.setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getLocation() {

		return location;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public String getManufacturername() {

		return manufacturername;
	}

	public String getPartdescription() {

		return partdescription;
	}

	public String getPartnumber() {

		return partnumber;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public Integer getReorderlevel() {

		return reorderlevel;
	}

	public BigDecimal getSoldprice() {

		return soldprice.setScale(2, RoundingMode.HALF_EVEN);
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

		this.actualprice = actualprice.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setInvoicenumber(Integer invoicenumber) {

		this.invoicenumber = invoicenumber;
	}

	public void setListprice(BigDecimal listprice) {

		this.listprice = listprice.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {

		this.manufacturername = manufacturername;
	}

	public void setPartdescription(String partdescription) {

		this.partdescription = partdescription;
	}

	public void setPartnumber(String partnumber) {

		this.partnumber = partnumber;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

	public void setReorderlevel(Integer reorderlevel) {

		this.reorderlevel = reorderlevel;
	}

	public void setSoldprice(BigDecimal soldprice) {

		this.soldprice = soldprice.setScale(2, RoundingMode.HALF_EVEN);
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
