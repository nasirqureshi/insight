package com.bvas.insight.data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author khalid
 */
public class ReorderLevelDataConsolidated {

	private BigDecimal adjustedReorder;
	private BigDecimal currentCycleDemandFactor; //
	private Date currentCycleEndDate;
	private BigDecimal currentCycleMonth1GrowthRate;
	private BigDecimal currentCycleMonth1Sale;
	private Integer currentCycleMonth1TotalDays;
	private BigDecimal currentCycleMonth2GrowthRate;
	private BigDecimal currentCycleMonth2Sale;
	private Integer currentCycleMonth2TotalDays;
	private BigDecimal currentCycleReorder;
	private BigDecimal currentCycleSQMultiplier; //
	private Date currentCycleStartDate;
	private BigDecimal excessQty;
	private Integer instockNow;
	private Integer inTransitNow;
	private Date lastOrderDate; //
	private BigDecimal minimumQtyToSurviveNext45Days;
	private Integer onorderNow;
	// private String orderType;
	private String partNo;
	private Integer qtyOrderedAfterSnapshot; //
	// derived
	private BigDecimal qtyToOrder;
	private BigDecimal reorderCurrentCycleMonth1Daily;
	private BigDecimal reorderCurrentCycleMonth2Daily;
	private BigDecimal reorderTargetCycleMonth1Daily;
	private BigDecimal reorderTargetCycleMonth2Daily;
	private BigDecimal safetyQty;
	private Date snapShotDate;
	private Integer snapshotInstock;
	private Integer snapshotOnorder;
	private BigDecimal targetCycleDemandFactor; //
	private Date targetCycleEndDate;
	private BigDecimal targetCycleMonth1GrowthRate;
	private BigDecimal targetCycleMonth1Sale;
	private Integer targetCycleMonth1TotalDays;
	private BigDecimal targetCycleMonth2GrowthRate;
	private BigDecimal targetCycleMonth2Sale;
	private Integer targetCycleMonth2TotalDays;
	private BigDecimal targetCycleReorder;
	private BigDecimal targetCycleSQMultiplier; //
	private Date targetCycleStartDate;

	public BigDecimal getAdjustedReorder() {
		return adjustedReorder;
	}

	public BigDecimal getCurrentCycleDemandFactor() {
		return currentCycleDemandFactor;
	}

	public Date getCurrentCycleEndDate() {
		return currentCycleEndDate;
	}

	public BigDecimal getCurrentCycleMonth1GrowthRate() {
		return currentCycleMonth1GrowthRate;
	}

	public BigDecimal getCurrentCycleMonth1Sale() {
		return currentCycleMonth1Sale;
	}

	public Integer getCurrentCycleMonth1TotalDays() {
		if (currentCycleMonth1TotalDays == 0) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(getCurrentCycleStartDate());
			return c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return currentCycleMonth1TotalDays;
	}

	public BigDecimal getCurrentCycleMonth2GrowthRate() {
		return currentCycleMonth2GrowthRate;
	}

	public BigDecimal getCurrentCycleMonth2Sale() {
		return currentCycleMonth2Sale;
	}

	public Integer getCurrentCycleMonth2TotalDays() {
		if (currentCycleMonth2TotalDays == 0) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(getCurrentCycleEndDate());
			return c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return currentCycleMonth2TotalDays;
	}

	public BigDecimal getCurrentCycleReorder() {
		return currentCycleReorder;
	}

	public BigDecimal getCurrentCycleSQMultiplier() {
		return currentCycleSQMultiplier;
	}

	public Date getCurrentCycleStartDate() {
		return currentCycleStartDate;
	}

	public BigDecimal getExcessQty() {
		return excessQty;
	}

	public Integer getInstockNow() {
		return instockNow;
	}

	public Integer getInTransitNow() {
		return inTransitNow;
	}

	public Date getLastOrderDate() {
		return lastOrderDate;
	}

	public BigDecimal getMinimumQtyToSurviveNext45Days() {
		return minimumQtyToSurviveNext45Days;
	}

	public Integer getOnorderNow() {
		return onorderNow;
	}

	public String getPartNo() {
		return partNo;
	}

	public Integer getQtyOrderedAfterSnapshot() {
		return qtyOrderedAfterSnapshot;
	}

	public BigDecimal getQtyToOrder() {
		return qtyToOrder;
	}

	public BigDecimal getReorderCurrentCycleMonth1Daily() {
		return reorderCurrentCycleMonth1Daily;
	}

	public BigDecimal getReorderCurrentCycleMonth2Daily() {
		return reorderCurrentCycleMonth2Daily;
	}

	public BigDecimal getReorderTargetCycleMonth1Daily() {
		return reorderTargetCycleMonth1Daily;
	}

	public BigDecimal getReorderTargetCycleMonth2Daily() {
		return reorderTargetCycleMonth2Daily;
	}

	public BigDecimal getSafetyQty() {
		return safetyQty;
	}

	public Date getSnapShotDate() {
		return snapShotDate;
	}

	public Integer getSnapshotInstock() {
		return snapshotInstock;
	}

	public Integer getSnapshotOnorder() {
		return snapshotOnorder;
	}

	public BigDecimal getTargetCycleDemandFactor() {
		return targetCycleDemandFactor;
	}

	public Date getTargetCycleEndDate() {
		return targetCycleEndDate;
	}

	public BigDecimal getTargetCycleMonth1GrowthRate() {
		return targetCycleMonth1GrowthRate;
	}

	public BigDecimal getTargetCycleMonth1Sale() {
		return targetCycleMonth1Sale;
	}

	public Integer getTargetCycleMonth1TotalDays() {
		if (targetCycleMonth1TotalDays == 0) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(getTargetCycleStartDate());
			return c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return targetCycleMonth1TotalDays;
	}

	public BigDecimal getTargetCycleMonth2GrowthRate() {
		return targetCycleMonth2GrowthRate;
	}

	public BigDecimal getTargetCycleMonth2Sale() {
		return targetCycleMonth2Sale;
	}

	public Integer getTargetCycleMonth2TotalDays() {
		if (targetCycleMonth2TotalDays == 0) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(getTargetCycleEndDate());
			return c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return targetCycleMonth2TotalDays;
	}

	public BigDecimal getTargetCycleReorder() {
		return targetCycleReorder;
	}

	public BigDecimal getTargetCycleSQMultiplier() {
		return targetCycleSQMultiplier;
	}

	public Date getTargetCycleStartDate() {
		return targetCycleStartDate;
	}

	public void mergeData(ReorderLevelData data) {
		this.setCurrentCycleDemandFactor(data.getCurrentCycleDemandFactor());
		this.setCurrentCycleEndDate(data.getCurrentCycleEndDate());
		if (this.getCurrentCycleMonth1GrowthRate() == null) {
			this.setCurrentCycleMonth1GrowthRate(data.getCurrentCycleMonth1GrowthRate());
		} else {
			this.setCurrentCycleMonth1GrowthRate(
					this.getCurrentCycleMonth1GrowthRate().add(data.getCurrentCycleMonth1GrowthRate()));
		}
		if (this.getCurrentCycleMonth1Sale() == null) {
			this.setCurrentCycleMonth1Sale(data.getCurrentCycleMonth1Sale());
		} else {
			this.setCurrentCycleMonth1Sale(this.getCurrentCycleMonth1Sale().add(data.getCurrentCycleMonth1Sale()));
		}

		if (this.currentCycleMonth1TotalDays == null || this.currentCycleMonth1TotalDays == 0) {
			this.setCurrentCycleMonth1TotalDays(data.getCurrentCycleMonth1TotalDays());
		}
		if (this.getCurrentCycleMonth2GrowthRate() == null) {
			this.setCurrentCycleMonth2GrowthRate(data.getCurrentCycleMonth2GrowthRate());
		} else {
			this.setCurrentCycleMonth2GrowthRate(
					this.getCurrentCycleMonth2GrowthRate().add(data.getCurrentCycleMonth2GrowthRate()));
		}
		if (this.getCurrentCycleMonth2Sale() == null) {
			this.setCurrentCycleMonth2Sale(data.getCurrentCycleMonth2Sale());
		} else {
			this.setCurrentCycleMonth2Sale(this.getCurrentCycleMonth2Sale().add(data.getCurrentCycleMonth2Sale()));
		}

		if (this.currentCycleMonth2TotalDays == null || this.currentCycleMonth2TotalDays == 0) {
			this.setCurrentCycleMonth2TotalDays(data.getCurrentCycleMonth2TotalDays());
		}
		this.setCurrentCycleSQMultiplier(data.getCurrentCycleSQMultiplier());
		this.setCurrentCycleStartDate(data.getCurrentCycleStartDate());

		if (this.getLastOrderDate() == null && data.getLastOrderDate() != null) {
			this.setLastOrderDate((Date) data.getLastOrderDate().clone());
		} else if (this.getLastOrderDate() != null && data.getLastOrderDate() != null
				&& data.getLastOrderDate().after(this.getLastOrderDate())) {
			this.setLastOrderDate(data.getLastOrderDate());
		}
		// this.setLocation(this.getLocation());//
		this.setPartNo(data.getPartNo());//
		if (this.getQtyOrderedAfterSnapshot() == null) {
			this.setQtyOrderedAfterSnapshot(data.getQtyOrderedAfterSnapshot());
		} else {
			this.setQtyOrderedAfterSnapshot(this.getQtyOrderedAfterSnapshot() + data.getQtyOrderedAfterSnapshot());
		}
		if (this.getSafetyQty() == null) {
			this.setSafetyQty(data.getSafetyQty());
		} else {
			this.setSafetyQty(this.getSafetyQty().add(data.getSafetyQty()));
		}

		this.setSnapShotDate(data.getSnapShotDate());
		if (this.getSnapshotInstock() == null) {
			this.setSnapshotInstock(data.getSnapshotInstock());
		} else {
			this.setSnapshotInstock(this.getSnapshotInstock() + data.getSnapshotInstock());
		}
		if (this.getSnapshotOnorder() == null) {
			this.setSnapshotOnorder(data.getSnapshotOnorder());
		} else {
			this.setSnapshotOnorder(this.getSnapshotOnorder() + data.getSnapshotOnorder());
		}
		this.setTargetCycleDemandFactor(data.getTargetCycleDemandFactor());
		this.setTargetCycleEndDate(data.getTargetCycleEndDate());
		if (this.getTargetCycleMonth1GrowthRate() == null) {
			this.setTargetCycleMonth1GrowthRate(data.getTargetCycleMonth1GrowthRate());
		} else {
			this.setTargetCycleMonth1GrowthRate(
					this.getTargetCycleMonth1GrowthRate().add(data.getTargetCycleMonth1GrowthRate()));
		}
		if (this.getTargetCycleMonth1Sale() == null) {
			this.setTargetCycleMonth1Sale(data.getTargetCycleMonth1Sale());
		} else {
			this.setTargetCycleMonth1Sale(this.getTargetCycleMonth1Sale().add(data.getTargetCycleMonth1Sale()));
		}

		if (this.targetCycleMonth1TotalDays == null || this.targetCycleMonth1TotalDays == 0) {
			this.setTargetCycleMonth1TotalDays(data.getTargetCycleMonth1TotalDays());
		}
		if (this.getTargetCycleMonth2GrowthRate() == null) {
			this.setTargetCycleMonth2GrowthRate(data.getTargetCycleMonth2GrowthRate());
		} else {
			this.setTargetCycleMonth2GrowthRate(
					this.getTargetCycleMonth2GrowthRate().add(data.getTargetCycleMonth2GrowthRate()));
		}
		if (this.getTargetCycleMonth2Sale() == null) {
			this.setTargetCycleMonth2Sale(data.getTargetCycleMonth2Sale());
		} else {
			this.setTargetCycleMonth2Sale(this.getTargetCycleMonth2Sale().add(data.getTargetCycleMonth2Sale()));
		}

		if (this.targetCycleMonth2TotalDays == null || this.targetCycleMonth2TotalDays == 0) {
			this.setTargetCycleMonth2TotalDays(data.getTargetCycleMonth2TotalDays());
		}
		this.setTargetCycleSQMultiplier(data.getTargetCycleSQMultiplier());
		this.setTargetCycleStartDate(data.getTargetCycleStartDate());

		if (this.getInstockNow() == null) {
			this.setInstockNow(data.getInstockNow());
		} else {
			this.setInstockNow(this.getInstockNow() + data.getInstockNow());
		}

		if (this.getOnorderNow() == null) {
			this.setOnorderNow(data.getOnorderNow());
		} else {
			this.setOnorderNow(this.getOnorderNow() + data.getOnorderNow());
		}
		if (this.getInTransitNow() == null) {
			this.setInTransitNow(data.getInTransitNow());
		} else {
			this.setInTransitNow(this.getInTransitNow() + data.getInTransitNow());
		}
		// derived
		if (this.getQtyToOrder() == null) {
			this.setQtyToOrder(data.getQtyToOrder());
		} else {
			this.setQtyToOrder(this.getQtyToOrder().add(data.getQtyToOrder()));
		}
		if (this.getReorderCurrentCycleMonth1Daily() == null) {
			this.setReorderCurrentCycleMonth1Daily(data.getReorderCurrentCycleMonth1Daily());
		} else {
			this.setReorderCurrentCycleMonth1Daily(
					this.getReorderCurrentCycleMonth1Daily().add(data.getReorderCurrentCycleMonth1Daily()));
		}
		if (this.getReorderCurrentCycleMonth2Daily() == null) {
			this.setReorderCurrentCycleMonth2Daily(data.getReorderCurrentCycleMonth2Daily());
		} else {
			this.setReorderCurrentCycleMonth2Daily(
					this.getReorderCurrentCycleMonth2Daily().add(data.getReorderCurrentCycleMonth2Daily()));
		}
		if (this.getReorderTargetCycleMonth1Daily() == null) {
			this.setReorderTargetCycleMonth1Daily(data.getReorderTargetCycleMonth1Daily());
		} else {
			this.setReorderTargetCycleMonth1Daily(
					this.getReorderTargetCycleMonth1Daily().add(data.getReorderTargetCycleMonth1Daily()));
		}
		if (this.getReorderTargetCycleMonth2Daily() == null) {
			this.setReorderTargetCycleMonth2Daily(data.getReorderTargetCycleMonth2Daily());
		} else {
			this.setReorderTargetCycleMonth2Daily(
					this.getReorderTargetCycleMonth2Daily().add(data.getReorderTargetCycleMonth2Daily()));
		}
		if (this.getMinimumQtyToSurviveNext45Days() == null) {
			this.setMinimumQtyToSurviveNext45Days(data.getMinimumQtyToSurviveNext45Days());
		} else {
			this.setMinimumQtyToSurviveNext45Days(
					this.getMinimumQtyToSurviveNext45Days().add(data.getMinimumQtyToSurviveNext45Days()));
		}
		if (this.getCurrentCycleReorder() == null) {
			this.setCurrentCycleReorder(data.getCurrentCycleReorder());
		} else {
			this.setCurrentCycleReorder(this.getCurrentCycleReorder().add(data.getCurrentCycleReorder()));
		}
		if (this.getTargetCycleReorder() == null) {
			this.setTargetCycleReorder(data.getTargetCycleReorder());
		} else {
			this.setTargetCycleReorder(this.getTargetCycleReorder().add(data.getTargetCycleReorder()));
		}
		if (this.getExcessQty() == null) {
			this.setExcessQty(data.getExcessQty());
		} else {
			this.setExcessQty(this.getExcessQty().add(data.getExcessQty()));
		}
		if (this.getAdjustedReorder() == null) {
			this.setAdjustedReorder(data.getAdjustedReorder());
		} else {
			this.setAdjustedReorder(this.getAdjustedReorder().add(data.getAdjustedReorder()));
		}
	}

	public void setAdjustedReorder(BigDecimal adjustedReorder) {
		this.adjustedReorder = adjustedReorder;
	}

	public void setCurrentCycleDemandFactor(BigDecimal currentCycleDemandFactor) {
		this.currentCycleDemandFactor = currentCycleDemandFactor;
	}

	public void setCurrentCycleEndDate(Date currentCycleEndDate) {
		this.currentCycleEndDate = currentCycleEndDate;
	}

	public void setCurrentCycleMonth1GrowthRate(BigDecimal currentCycleMonth1GrowthRate) {
		this.currentCycleMonth1GrowthRate = currentCycleMonth1GrowthRate;
	}

	public void setCurrentCycleMonth1Sale(BigDecimal currentCycleMonth1Sale) {
		this.currentCycleMonth1Sale = currentCycleMonth1Sale;
	}

	public void setCurrentCycleMonth1TotalDays(Integer currentCycleMonth1TotalDays) {
		this.currentCycleMonth1TotalDays = currentCycleMonth1TotalDays;
	}

	public void setCurrentCycleMonth2GrowthRate(BigDecimal currentCycleMonth2GrowthRate) {
		this.currentCycleMonth2GrowthRate = currentCycleMonth2GrowthRate;
	}

	public void setCurrentCycleMonth2Sale(BigDecimal currentCycleMonth2Sale) {
		this.currentCycleMonth2Sale = currentCycleMonth2Sale;
	}

	public void setCurrentCycleMonth2TotalDays(Integer currentCycleMonth2TotalDays) {
		this.currentCycleMonth2TotalDays = currentCycleMonth2TotalDays;
	}

	public void setCurrentCycleReorder(BigDecimal currentCycleReorder) {
		this.currentCycleReorder = currentCycleReorder;
	}

	public void setCurrentCycleSQMultiplier(BigDecimal currentCycleSQMultiplier) {
		this.currentCycleSQMultiplier = currentCycleSQMultiplier;
	}

	public void setCurrentCycleStartDate(Date currentCycleStartDate) {
		this.currentCycleStartDate = currentCycleStartDate;
	}

	public void setExcessQty(BigDecimal excessQty) {
		this.excessQty = excessQty;
	}

	public void setInstockNow(Integer instockNow) {
		this.instockNow = instockNow;
	}

	public void setInTransitNow(Integer inTransitNow) {
		this.inTransitNow = inTransitNow;
	}

	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public void setMinimumQtyToSurviveNext45Days(BigDecimal minimumQtyToSurviveNext45Days) {
		this.minimumQtyToSurviveNext45Days = minimumQtyToSurviveNext45Days;
	}

	public void setOnorderNow(Integer onorderNow) {
		this.onorderNow = onorderNow;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public void setQtyOrderedAfterSnapshot(Integer qtyOrderedAfterSnapshot) {
		this.qtyOrderedAfterSnapshot = qtyOrderedAfterSnapshot;
	}

	public void setQtyToOrder(BigDecimal qtyToOrder) {
		this.qtyToOrder = qtyToOrder;
	}

	public void setReorderCurrentCycleMonth1Daily(BigDecimal reorderCurrentCycleMonth1Daily) {
		this.reorderCurrentCycleMonth1Daily = reorderCurrentCycleMonth1Daily;
	}

	public void setReorderCurrentCycleMonth2Daily(BigDecimal reorderCurrentCycleMonth2Daily) {
		this.reorderCurrentCycleMonth2Daily = reorderCurrentCycleMonth2Daily;
	}

	public void setReorderTargetCycleMonth1Daily(BigDecimal reorderTargetCycleMonth1Daily) {
		this.reorderTargetCycleMonth1Daily = reorderTargetCycleMonth1Daily;
	}

	public void setReorderTargetCycleMonth2Daily(BigDecimal reorderTargetCycleMonth2Daily) {
		this.reorderTargetCycleMonth2Daily = reorderTargetCycleMonth2Daily;
	}

	public void setSafetyQty(BigDecimal safetyQty) {
		this.safetyQty = safetyQty;
	}

	public void setSnapShotDate(Date snapShotDate) {
		this.snapShotDate = snapShotDate;
	}

	public void setSnapshotInstock(Integer snapshotInstock) {
		this.snapshotInstock = snapshotInstock;
	}

	public void setSnapshotOnorder(Integer snapshotOnorder) {
		this.snapshotOnorder = snapshotOnorder;
	}

	public void setTargetCycleDemandFactor(BigDecimal targetCycleDemandFactor) {
		this.targetCycleDemandFactor = targetCycleDemandFactor;
	}

	public void setTargetCycleEndDate(Date targetCycleEndDate) {
		this.targetCycleEndDate = targetCycleEndDate;
	}

	public void setTargetCycleMonth1GrowthRate(BigDecimal targetCycleMonth1GrowthRate) {
		this.targetCycleMonth1GrowthRate = targetCycleMonth1GrowthRate;
	}

	public void setTargetCycleMonth1Sale(BigDecimal targetCycleMonth1Sale) {
		this.targetCycleMonth1Sale = targetCycleMonth1Sale;
	}

	public void setTargetCycleMonth1TotalDays(Integer targetCycleMonth1TotalDays) {
		this.targetCycleMonth1TotalDays = targetCycleMonth1TotalDays;
	}

	public void setTargetCycleMonth2GrowthRate(BigDecimal targetCycleMonth2GrowthRate) {
		this.targetCycleMonth2GrowthRate = targetCycleMonth2GrowthRate;
	}

	public void setTargetCycleMonth2Sale(BigDecimal targetCycleMonth2Sale) {
		this.targetCycleMonth2Sale = targetCycleMonth2Sale;
	}

	public void setTargetCycleMonth2TotalDays(Integer targetCycleMonth2TotalDays) {
		this.targetCycleMonth2TotalDays = targetCycleMonth2TotalDays;
	}

	public void setTargetCycleReorder(BigDecimal targetCycleReorder) {
		this.targetCycleReorder = targetCycleReorder;
	}

	public void setTargetCycleSQMultiplier(BigDecimal targetCycleSQMultiplier) {
		this.targetCycleSQMultiplier = targetCycleSQMultiplier;
	}

	public void setTargetCycleStartDate(Date targetCycleStartDate) {
		this.targetCycleStartDate = targetCycleStartDate;
	}

}
