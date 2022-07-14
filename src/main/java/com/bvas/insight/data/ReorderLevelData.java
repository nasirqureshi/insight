package com.bvas.insight.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author khalid
 */
public class ReorderLevelData {// implements Cloneable{

	private BigDecimal currentCycleDemandFactor; //
	private Date currentCycleEndDate;
	private BigDecimal currentCycleMonth1GrowthRate;
	private BigDecimal currentCycleMonth1Sale;
	private Integer currentCycleMonth1TotalDays;
	private BigDecimal currentCycleMonth2GrowthRate;
	private BigDecimal currentCycleMonth2Sale;
	private Integer currentCycleMonth2TotalDays;
	private BigDecimal currentCycleSQMultiplier; //
	private Date currentCycleStartDate;
	private Integer instockNow;
	private Integer inTransitNow;
	/*
	 * public void mergeData(ReorderLevelData data){
	 * //this.setCurrentCycleDemandFactor(this.getCurrentCycleDemandFactor().add(
	 * data.getCurrentCycleDemandFactor()));
	 * //this.setCurrentCycleEndDate(this.getCurrentCycleEndDate());
	 * this.setCurrentCycleMonth1GrowthRate(this.getCurrentCycleMonth1GrowthRate().
	 * add(data.getCurrentCycleMonth1GrowthRate()));
	 * this.setCurrentCycleMonth1Sale(this.getCurrentCycleMonth1Sale().add(data.
	 * getCurrentCycleMonth1Sale())); if(this.currentCycleMonth1TotalDays==0){
	 * this.setCurrentCycleMonth1TotalDays(data.getCurrentCycleMonth1TotalDays()); }
	 * this.setCurrentCycleMonth2GrowthRate(this.getCurrentCycleMonth2GrowthRate().
	 * add(data.getCurrentCycleMonth2GrowthRate()));
	 * this.setCurrentCycleMonth2Sale(this.getCurrentCycleMonth2Sale().add(data.
	 * getCurrentCycleMonth2Sale())); if(this.currentCycleMonth2TotalDays==0){
	 * this.setCurrentCycleMonth2TotalDays(data.getCurrentCycleMonth2TotalDays()); }
	 * //this.setCurrentCycleSQMultiplier(this.getCurrentCycleSQMultiplier());
	 * //this.setCurrentCycleStartDate(this.getCurrentCycleStartDate());
	 * if(this.getLastOrderDate()==null && data.getLastOrderDate()!=null){
	 * this.setLastOrderDate((Date)data.getLastOrderDate().clone()); }else if
	 * (this.getLastOrderDate()!=null && data.getLastOrderDate() !=null &&
	 * data.getLastOrderDate().after(this.getLastOrderDate())){
	 * this.setLastOrderDate(data.getLastOrderDate()); }
	 * //this.setLocation(this.getLocation());//
	 * //this.setPartNo(this.getPartNo());//
	 * this.setQtyOrderedAfterSnapshot(this.getQtyOrderedAfterSnapshot()+data.
	 * getQtyOrderedAfterSnapshot());
	 * this.setSafetyQty(this.getSafetyQty().add(data.getSafetyQty()));
	 * //this.setSnapShotDate(this.getSnapShotDate());
	 * this.setSnapshotInstock(this.getSnapshotInstock()+data.getSnapshotInstock());
	 * this.setSnapshotOnorder(this.getSnapshotOnorder()+data.getSnapshotOnorder());
	 * //this.setTargetCycleDemandFactor(targetCycleDemandFactor);
	 * //this.setTargetCycleEndDate(targetCycleEndDate);
	 * this.setTargetCycleMonth1GrowthRate(this.getTargetCycleMonth1GrowthRate().add
	 * (data.getTargetCycleMonth1GrowthRate()));
	 * this.setTargetCycleMonth1Sale(this.getTargetCycleMonth1Sale().add(data.
	 * getTargetCycleMonth1Sale())); if(this.targetCycleMonth1TotalDays==0){
	 * this.setTargetCycleMonth1TotalDays(data.getTargetCycleMonth1TotalDays()); }
	 * this.setTargetCycleMonth2GrowthRate(this.getTargetCycleMonth2GrowthRate().add
	 * (data.getTargetCycleMonth2GrowthRate()));
	 * this.setTargetCycleMonth2Sale(this.getTargetCycleMonth2Sale().add(data.
	 * getTargetCycleMonth2Sale())); if(this.targetCycleMonth2TotalDays==0){
	 * this.setTargetCycleMonth2TotalDays(data.getTargetCycleMonth2TotalDays()); }
	 * //this.setTargetCycleSQMultiplier(targetCycleSQMultiplier);
	 * //this.setTargetCycleStartDate(targetCycleStartDate);
	 * 
	 * }
	 * 
	 * @Override public Object clone() { try { return super.clone(); } catch
	 * (CloneNotSupportedException ex) { return null; } }
	 * 
	 */
	private Date lastOrderDate; //
	private String location;
	private Integer onorderNow;
	// private String orderType;
	private String partNo;
	private Integer qtyOrderedAfterSnapshot; //
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
	private BigDecimal targetCycleSQMultiplier; //
	private Date targetCycleStartDate;

	public BigDecimal getAdjustedReorder() {
		return getTargetCycleReorder().subtract(getExcessQty());
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
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getCurrentCycleStartDate());
		int cycle1M1StartDay = c1.get(Calendar.DAY_OF_MONTH);
		int cycle1M1EndDay = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		int cycle1M1Days = (cycle1M1EndDay - cycle1M1StartDay) + 1;

		Calendar c2 = Calendar.getInstance();
		c2.setTime(getCurrentCycleEndDate());
		int cycle1M2Days = c2.get(Calendar.DAY_OF_MONTH);

		BigDecimal p1 = BigDecimal.ONE
				.add(getCurrentCycleDemandFactor().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
		BigDecimal p2_1 = getReorderCurrentCycleMonth1Daily().multiply(BigDecimal.valueOf(cycle1M1Days));
		BigDecimal p2_2 = getReorderCurrentCycleMonth2Daily().multiply(BigDecimal.valueOf(cycle1M2Days));
		BigDecimal p2 = p2_1.add(p2_2);
		return p1.multiply(p2);

	}

	public BigDecimal getCurrentCycleSQMultiplier() {
		return currentCycleSQMultiplier;
	}

	public Date getCurrentCycleStartDate() {
		return currentCycleStartDate;
	}

	public BigDecimal getExcessQty() {
		return BigDecimal.valueOf(getSnapshotInstock() + getSnapshotOnorder()).subtract(getCurrentCycleReorder());
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

	public String getLocation() {
		return location;
	}

	public BigDecimal getMinimumQtyToSurviveNext45Days() {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getTargetCycleStartDate());
		int cycle2M1StartDay = c1.get(Calendar.DAY_OF_MONTH);
		int cycle2M1EndDay = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		int cycle2M1Days = (cycle2M1EndDay - cycle2M1StartDay) + 1;

		Calendar c2 = Calendar.getInstance();
		c2.setTime(getCurrentCycleEndDate());
		int cycle2M2Days = c2.get(Calendar.DAY_OF_MONTH);

		BigDecimal p1 = getReorderTargetCycleMonth1Daily().multiply(BigDecimal.valueOf(cycle2M1Days));
		BigDecimal p2 = getReorderTargetCycleMonth2Daily().multiply(BigDecimal.valueOf(cycle2M2Days));
		return p1.add(p2);

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
		/*
		 * If (Adj. Re Order Level - Qty Ordered >20) Then Order 25% of Adj. Reorder
		 * Level Else Adj. ReOrderLevel - QtyOrdered
		 */
		BigDecimal adjustedReorder = getAdjustedReorder();

		BigDecimal adjReOrder_QtyOrdered = adjustedReorder.subtract(BigDecimal.valueOf(getQtyOrderedAfterSnapshot()));
		BigDecimal qtyToOrder;
		if (adjReOrder_QtyOrdered.compareTo(BigDecimal.valueOf(20)) == 1) {

			qtyToOrder = BigDecimal.valueOf(0.25).multiply(adjustedReorder);
			// LOGGER.info("25%= partNo="+getPartNo()+"
			// :Cycle2Reorder"+getCycle2Reorder()+" :adjusteR:"+adjustedReorder+"
			// :QtyorderedAfterSnapshot="+getQtyOrderedAfterSnapshot()+"
			// :adjReOrder-QtyOrdered="+adjReOrder_QtyOrdered+" : qtyToOrder="+qtyToOrder);

		} else {
			qtyToOrder = adjustedReorder.subtract(BigDecimal.valueOf(getQtyOrderedAfterSnapshot()));
			// LOGGER.info("not 25%= partNo="+getPartNo()+"
			// :Cycle2Reorder"+getCycle2Reorder()+" :adjusteR:"+adjustedReorder+"
			// :QtyorderedAfterSnapshot="+getQtyOrderedAfterSnapshot()+"
			// :adjReOrder-QtyOrdered="+adjReOrder_QtyOrdered+" : qtyToOrder="+qtyToOrder);
		}
		return qtyToOrder;
	}

	public BigDecimal getReorderCurrentCycleMonth1Daily() {
		if (getCurrentCycleMonth1TotalDays() == 0) {
			return BigDecimal.ZERO;
		} else if (getCurrentCycleMonth1GrowthRate().compareTo(BigDecimal.ONE) == -1) {
			BigDecimal p1 = getCurrentCycleMonth1Sale().multiply(BigDecimal.ONE.add(getCurrentCycleMonth1GrowthRate()));
			return p1.divide(BigDecimal.valueOf(getCurrentCycleMonth1TotalDays()), 2, RoundingMode.HALF_UP);
		} else {
			BigDecimal p1 = getCurrentCycleMonth1Sale().add(getCurrentCycleSQMultiplier().multiply(getSafetyQty()));
			return p1.divide(BigDecimal.valueOf(getCurrentCycleMonth1TotalDays()), 2, RoundingMode.HALF_UP);
		}
	}

	public BigDecimal getReorderCurrentCycleMonth2Daily() {
		if (getCurrentCycleMonth2TotalDays() == 0) {
			return BigDecimal.ZERO;
		} else if (getCurrentCycleMonth2GrowthRate().compareTo(BigDecimal.ONE) == -1) {
			return (getCurrentCycleMonth2Sale().multiply(BigDecimal.ONE.add(getCurrentCycleMonth2GrowthRate())))
					.divide(BigDecimal.valueOf(getCurrentCycleMonth2TotalDays()), 2, RoundingMode.HALF_UP);
		} else {
			return (getCurrentCycleMonth2Sale().add(getCurrentCycleSQMultiplier().multiply(getSafetyQty())))
					.divide(BigDecimal.valueOf(getCurrentCycleMonth2TotalDays()), 2, RoundingMode.HALF_UP);
		}
	}

	public BigDecimal getReorderTargetCycleMonth1Daily() {
		// LOGGER.info("c2m1gr="+getTargetCycleMonth1GrowthRate());
		if (getTargetCycleMonth1TotalDays() == 0) {
			return BigDecimal.ZERO;
		} else if (getTargetCycleMonth1GrowthRate().compareTo(BigDecimal.ONE) == -1) {
			return (getTargetCycleMonth1Sale().multiply(BigDecimal.ONE.add(getTargetCycleMonth1GrowthRate())))
					.divide(BigDecimal.valueOf(getTargetCycleMonth1TotalDays()), 2, RoundingMode.HALF_UP);
		} else {
			return (getTargetCycleMonth1Sale().add(getTargetCycleSQMultiplier().multiply(getSafetyQty())))
					.divide(BigDecimal.valueOf(getTargetCycleMonth1TotalDays()), 2, RoundingMode.HALF_UP);
		}
	}

	public BigDecimal getReorderTargetCycleMonth2Daily() {
		if (getTargetCycleMonth2TotalDays() == 0) {
			return BigDecimal.ZERO;
		} else if (getTargetCycleMonth2GrowthRate().compareTo(BigDecimal.ONE) == -1) {
			return (getTargetCycleMonth2Sale().multiply(BigDecimal.ONE.add(getTargetCycleMonth2GrowthRate())))
					.divide(BigDecimal.valueOf(getTargetCycleMonth2TotalDays()), 2, RoundingMode.HALF_UP);
		} else {
			return (getTargetCycleMonth2Sale().add(getTargetCycleSQMultiplier().multiply(getSafetyQty())))
					.divide(BigDecimal.valueOf(getTargetCycleMonth2TotalDays()), 2, RoundingMode.HALF_UP);
		}
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
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getTargetCycleStartDate());
		int cycle2M1StartDay = c1.get(Calendar.DAY_OF_MONTH);
		int cycle2M1EndDay = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		int cycle2M1Days = (cycle2M1EndDay - cycle2M1StartDay) + 1;

		Calendar c2 = Calendar.getInstance();
		c2.setTime(getTargetCycleEndDate());
		int cycle2M2Days = c2.get(Calendar.DAY_OF_MONTH);

		BigDecimal p1 = BigDecimal.ONE
				.add(getTargetCycleDemandFactor().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
		BigDecimal p2_1 = getReorderTargetCycleMonth1Daily().multiply(BigDecimal.valueOf(cycle2M1Days));
		BigDecimal p2_2 = getReorderTargetCycleMonth2Daily().multiply(BigDecimal.valueOf(cycle2M2Days));
		BigDecimal p2 = p2_1.add(p2_2);
		// LOGGER.info("c2M1days="+cycle2M1Days+" :c2M2Days="+cycle2M2Days+"
		// :reorderC2M1Daily="+getReorderC2M1Daily()+"
		// :reorderC2M2Daily="+getReorderC2M2Daily()+
		// " :p1="+p1+" :p2_1="+p2_1+" :p2_2="+p2_2+" :p2="+p2+"
		// :p1*p2="+p1.multiply(p2));
		return p1.multiply(p2);
	}

	public BigDecimal getTargetCycleSQMultiplier() {
		return targetCycleSQMultiplier;
	}

	public Date getTargetCycleStartDate() {
		return targetCycleStartDate;
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

	public void setCurrentCycleSQMultiplier(BigDecimal currentCycleSQMultiplier) {
		this.currentCycleSQMultiplier = currentCycleSQMultiplier;
	}

	public void setCurrentCycleStartDate(Date currentCycleStartDate) {
		this.currentCycleStartDate = currentCycleStartDate;
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

	public void setLocation(String location) {
		this.location = location;
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

	public void setTargetCycleSQMultiplier(BigDecimal targetCycleSQMultiplier) {
		this.targetCycleSQMultiplier = targetCycleSQMultiplier;
	}

	public void setTargetCycleStartDate(Date targetCycleStartDate) {
		this.targetCycleStartDate = targetCycleStartDate;
	}

}
