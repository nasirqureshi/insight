/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.bvas.insight.data;

import java.math.BigDecimal;

/**
 *
 * @author khalid
 */
public class UpdatePartsCostPrice {
	private BigDecimal costPrice;
	private BigDecimal discount;
	private int excelRowNum;
	private BigDecimal landingCost;
	private Integer newQuantity;
	private String partNo;

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public int getExcelRowNum() {
		return excelRowNum;
	}

	public BigDecimal getLandingCost() {
		return landingCost;
	}

	public Integer getNewQuantity() {
		return newQuantity;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public void setExcelRowNum(int excelRowNum) {
		this.excelRowNum = excelRowNum;
	}

	public void setLandingCost(BigDecimal landingCost) {
		this.landingCost = landingCost;
	}

	public void setNewQuantity(Integer newQuantity) {
		this.newQuantity = newQuantity;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

}
