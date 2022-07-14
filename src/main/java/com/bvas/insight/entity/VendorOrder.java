package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendororder")
public class VendorOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ArrivedDate")
	public Date arriveddate;

	@Column(name = "ContainerNo")
	public String containerno;

	@Column(name = "deliveredDate")
	public Date delivereddate;

	@Column(name = "Discount")
	public BigDecimal discount;

	@Column(name = "EstimatedArrivalDate")
	public Date estimatedarrivaldate;

	@Column(name = "InventoryDoneDate")
	public Date inventorydonedate;

	@Column(name = "isfinal", columnDefinition = "char(1)")
	public String isfinal;

	@Column(name = "OrderDate")
	public Date orderdate;

	@Id
	@Column(name = "OrderNo")
	public Integer orderno;

	@Column(name = "OrderStatus")
	public String orderstatus;

	@Column(name = "OrderTotal")
	public BigDecimal ordertotal;

	@Column(name = "OverheadAmountsTotal")
	public BigDecimal overheadamountstotal;

	@Column(name = "PaymentDate")
	public Date paymentdate;

	@Column(name = "PaymentTerms")
	public String paymentterms;

	@Column(name = "PricesDoneDate")
	public Date pricesdonedate;

	@Column(name = "StickerCharges")
	public BigDecimal stickercharges;

	@Column(name = "SupplierID")
	public Integer supplierid;

	@Column(name = "SupplInvNo")
	public String supplinvno;

	@Column(name = "TotalItems")
	public Integer totalitems;

	@Column(name = "UnitsOrderDoneDate")
	public Date unitsorderdonedate;

	@Column(name = "updatedinventory", columnDefinition = "char(1)")
	public String updatedinventory;

	@Column(name = "updatedprices", columnDefinition = "char(1)")
	public String updatedprices;

	public VendorOrder() {
		super();
	}

	public VendorOrder(Integer orderno, Integer supplierid, Date orderdate, Date delivereddate, Date arriveddate,
			String containerno, String supplinvno, String orderstatus, Integer totalitems, BigDecimal ordertotal,
			BigDecimal discount, BigDecimal overheadamountstotal, BigDecimal stickercharges, Date unitsorderdonedate,
			Date pricesdonedate, Date inventorydonedate, String paymentterms, Date paymentdate, String updatedinventory,
			String updatedprices, String isfinal, Date estimatedarrivaldate) {
		super();
		this.orderno = orderno;
		this.supplierid = supplierid;
		this.orderdate = orderdate;
		this.delivereddate = delivereddate;
		this.arriveddate = arriveddate;
		this.containerno = containerno;
		this.supplinvno = supplinvno;
		this.orderstatus = orderstatus;
		this.totalitems = totalitems;
		this.ordertotal = ordertotal;
		this.discount = discount;
		this.overheadamountstotal = overheadamountstotal;
		this.stickercharges = stickercharges;
		this.unitsorderdonedate = unitsorderdonedate;
		this.pricesdonedate = pricesdonedate;
		this.inventorydonedate = inventorydonedate;
		this.paymentterms = paymentterms;
		this.paymentdate = paymentdate;
		this.updatedinventory = updatedinventory;
		this.updatedprices = updatedprices;
		this.isfinal = isfinal;
		this.estimatedarrivaldate = estimatedarrivaldate;
	}

	public Date getArriveddate() {

		return arriveddate;
	}

	public String getContainerno() {

		return containerno;
	}

	public Date getDelivereddate() {

		return delivereddate;
	}

	public BigDecimal getDiscount() {

		return discount;
	}

	public Date getEstimatedarrivaldate() {

		return estimatedarrivaldate;
	}

	public Date getInventorydonedate() {

		return inventorydonedate;
	}

	public String getIsfinal() {

		return isfinal;
	}

	public Date getOrderdate() {

		return orderdate;
	}

	public Integer getOrderno() {

		return orderno;
	}

	public String getOrderstatus() {

		return orderstatus;
	}

	public BigDecimal getOrdertotal() {

		return ordertotal;
	}

	public BigDecimal getOverheadamountstotal() {

		return overheadamountstotal;
	}

	public Date getPaymentdate() {

		return paymentdate;
	}

	public String getPaymentterms() {

		return paymentterms;
	}

	public Date getPricesdonedate() {

		return pricesdonedate;
	}

	public BigDecimal getStickercharges() {

		return stickercharges;
	}

	public Integer getSupplierid() {

		return supplierid;
	}

	public String getSupplinvno() {

		return supplinvno;
	}

	public Integer getTotalitems() {

		return totalitems;
	}

	public Date getUnitsorderdonedate() {

		return unitsorderdonedate;
	}

	public String getUpdatedinventory() {

		return updatedinventory;
	}

	public String getUpdatedprices() {

		return updatedprices;
	}

	public void setArriveddate(Date arriveddate) {

		this.arriveddate = arriveddate;
	}

	public void setContainerno(String containerno) {

		this.containerno = containerno;
	}

	public void setDelivereddate(Date delivereddate) {

		this.delivereddate = delivereddate;
	}

	public void setDiscount(BigDecimal discount) {

		this.discount = discount;
	}

	public void setEstimatedarrivaldate(Date estimatedarrivaldate) {

		this.estimatedarrivaldate = estimatedarrivaldate;
	}

	public void setInventorydonedate(Date inventorydonedate) {

		this.inventorydonedate = inventorydonedate;
	}

	public void setIsfinal(String isfinal) {

		this.isfinal = isfinal;
	}

	public void setOrderdate(Date orderdate) {

		this.orderdate = orderdate;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
	}

	public void setOrderstatus(String orderstatus) {

		this.orderstatus = orderstatus;
	}

	public void setOrdertotal(BigDecimal ordertotal) {

		this.ordertotal = ordertotal;
	}

	public void setOverheadamountstotal(BigDecimal overheadamountstotal) {

		this.overheadamountstotal = overheadamountstotal;
	}

	public void setPaymentdate(Date paymentdate) {

		this.paymentdate = paymentdate;
	}

	public void setPaymentterms(String paymentterms) {

		this.paymentterms = paymentterms;
	}

	public void setPricesdonedate(Date pricesdonedate) {

		this.pricesdonedate = pricesdonedate;
	}

	public void setStickercharges(BigDecimal stickercharges) {

		this.stickercharges = stickercharges;
	}

	public void setSupplierid(Integer supplierid) {

		this.supplierid = supplierid;
	}

	public void setSupplinvno(String supplinvno) {

		this.supplinvno = supplinvno;
	}

	public void setTotalitems(Integer totalitems) {

		this.totalitems = totalitems;
	}

	public void setUnitsorderdonedate(Date unitsorderdonedate) {

		this.unitsorderdonedate = unitsorderdonedate;
	}

	public void setUpdatedinventory(String updatedinventory) {

		this.updatedinventory = updatedinventory;
	}

	public void setUpdatedprices(String updatedprices) {

		this.updatedprices = updatedprices;
	}
}
