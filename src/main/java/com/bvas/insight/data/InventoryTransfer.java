package com.bvas.insight.data;

import java.math.BigDecimal;

public class InventoryTransfer {

	public String branchfrom;

	public String branchto;

	public BigDecimal totalAmount = BigDecimal.ZERO;

	public Integer totalItems = 0;

	public Integer totalQuantity = 0;

	public InventoryTransfer() {
		super();
	}

	public InventoryTransfer(String branch, String shortcode) {
		this.branchfrom = branch;
		this.branchto = shortcode;
		this.totalItems = 0;
		this.totalQuantity = 0;
		this.totalAmount = new BigDecimal("0.00");
	}

	public InventoryTransfer(String branchfrom, String branchto, Integer totalItems, Integer totalQuantity,
			BigDecimal totalAmount) {
		super();
		this.branchfrom = branchfrom;
		this.branchto = branchto;
		this.totalItems = totalItems;
		this.totalQuantity = totalQuantity;
		this.totalAmount = totalAmount;
	}

	public String getBranchfrom() {

		return branchfrom;
	}

	public String getBranchto() {

		return branchto;
	}

	public BigDecimal getTotalAmount() {

		return totalAmount;
	}

	public Integer getTotalItems() {

		return totalItems;
	}

	public Integer getTotalQuantity() {

		return totalQuantity;
	}

	public void setBranchfrom(String branchfrom) {

		this.branchfrom = branchfrom;
	}

	public void setBranchto(String branchto) {

		this.branchto = branchto;
	}

	public void setTotalAmount(BigDecimal totalAmount) {

		this.totalAmount = totalAmount;
	}

	public void setTotalItems(Integer totalItems) {

		this.totalItems = totalItems;
	}

	public void setTotalQuantity(Integer totalQuantity) {

		this.totalQuantity = totalQuantity;
	}
}
