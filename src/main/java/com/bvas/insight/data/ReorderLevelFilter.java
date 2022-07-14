/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.bvas.insight.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author khalid
 */
public class ReorderLevelFilter {
	private boolean CAPAIncluded = false;
	private BigDecimal currentCycleDemandFactor = BigDecimal.ZERO;
	private Date currentCycleEndDate;
	private BigDecimal currentCycleSQMultiplier = BigDecimal.ZERO;
	private Date currentCycleStartDate;
	private String orderType = "T";
	private BigDecimal qtyToOrder = BigDecimal.ZERO;
	private BigDecimal targetCycleDemandFactor = BigDecimal.ZERO;
	private Date targetCycleEndDate;
	private BigDecimal targetCycleSQMultiplier = BigDecimal.ZERO;
	private Date targetCycleStartDate;

	public BigDecimal getCurrentCycleDemandFactor() {
		return currentCycleDemandFactor;
	}

	public Date getCurrentCycleEndDate() {
		return currentCycleEndDate;
	}

	public BigDecimal getCurrentCycleSQMultiplier() {
		return currentCycleSQMultiplier;
	}

	public Date getCurrentCycleStartDate() {
		return currentCycleStartDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public BigDecimal getQtyToOrder() {
		return qtyToOrder;
	}

	public BigDecimal getTargetCycleDemandFactor() {
		return targetCycleDemandFactor;
	}

	public Date getTargetCycleEndDate() {
		return targetCycleEndDate;
	}

	public BigDecimal getTargetCycleSQMultiplier() {
		return targetCycleSQMultiplier;
	}

	public Date getTargetCycleStartDate() {
		return targetCycleStartDate;
	}

	public boolean isCAPAIncluded() {
		return CAPAIncluded;
	}

	public void setCAPAIncluded(boolean CAPAIncluded) {
		this.CAPAIncluded = CAPAIncluded;
	}

	public void setCurrentCycleDemandFactor(BigDecimal currentCycleDemandFactor) {
		this.currentCycleDemandFactor = currentCycleDemandFactor;
	}

	public void setCurrentCycleEndDate(Date currentCycleEndDate) {
		this.currentCycleEndDate = currentCycleEndDate;
	}

	public void setCurrentCycleSQMultiplier(BigDecimal currentCycleSQMultiplier) {
		this.currentCycleSQMultiplier = currentCycleSQMultiplier;
	}

	public void setCurrentCycleStartDate(Date currentCycleStartDate) {
		this.currentCycleStartDate = currentCycleStartDate;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setQtyToOrder(BigDecimal qtyToOrder) {
		this.qtyToOrder = qtyToOrder;
	}

	public void setTargetCycleDemandFactor(BigDecimal targetCycleDemandFactor) {
		this.targetCycleDemandFactor = targetCycleDemandFactor;
	}

	public void setTargetCycleEndDate(Date targetCycleEndDate) {
		this.targetCycleEndDate = targetCycleEndDate;
	}

	public void setTargetCycleSQMultiplier(BigDecimal targetCycleSQMultiplier) {
		this.targetCycleSQMultiplier = targetCycleSQMultiplier;
	}

	public void setTargetCycleStartDate(Date targetCycleStartDate) {
		this.targetCycleStartDate = targetCycleStartDate;
	}

}
