/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.bvas.insight.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Khalid
 */
public class SaleByRouteMonthly {
	private String month;

	private BigDecimal y1Discount;
	private boolean y1Exist = false;
	private BigDecimal y1Expenses;
	private BigDecimal y1Sale;
	private BigDecimal y1Tax;
	private int y1Year;

	private BigDecimal y2Discount;
	private boolean y2Exist = false;
	private BigDecimal y2Expenses;
	private BigDecimal y2Sale;
	private BigDecimal y2Tax;
	private int y2Year;

	private BigDecimal y3Discount;
	private boolean y3Exist = false;
	private BigDecimal y3Expenses;
	private BigDecimal y3Sale;
	private BigDecimal y3Tax;
	private int y3Year;

	private BigDecimal y4Discount;
	private boolean y4Exist = false;
	private BigDecimal y4Expenses;
	private BigDecimal y4Sale;
	private BigDecimal y4Tax;
	private int y4Year;

	private BigDecimal y5Discount;
	private boolean y5Exist = false;
	private BigDecimal y5Expenses;
	private BigDecimal y5Sale;
	private BigDecimal y5Tax;
	private int y5Year;

	public BigDecimal getAvgSale() {
		BigDecimal avgSale = BigDecimal.ZERO;
		int count = 0;
		if (isY1Exist()) {
			avgSale = avgSale.add(getY1Sale());
			count++;
		}
		if (isY2Exist()) {
			avgSale = avgSale.add(getY2Sale());
			count++;
		}
		if (isY3Exist()) {
			avgSale = avgSale.add(getY3Sale());
			count++;
		}
		if (isY4Exist()) {
			avgSale = avgSale.add(getY4Sale());
			count++;
		}
		if (isY5Exist()) {
			avgSale = avgSale.add(getY5Sale());
			count++;
		}
		if (count != 0) {
			avgSale = avgSale.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
		}
		return avgSale;
	}

	public String getMonth() {
		return month;
	}

	public BigDecimal getY1Discount() {
		return y1Discount;
	}

	public BigDecimal getY1Expenses() {
		return y1Expenses;
	}

	public BigDecimal getY1Net() {
		BigDecimal y1Net = BigDecimal.ZERO;
		if (isY1Exist()) {
			if (getY1Sale() != null) {
				y1Net = getY1Sale().subtract(getY1Tax());
			}
			if (getY1Discount() != null) {
				y1Net = y1Net.subtract(getY1Discount());
			}
		}
		return y1Net;
	}

	public BigDecimal getY1Sale() {
		return y1Sale;
	}

	public BigDecimal getY1Tax() {
		return y1Tax;
	}

	public int getY1Year() {
		return y1Year;
	}

	public BigDecimal getY2Discount() {
		return y2Discount;
	}

	public BigDecimal getY2Expenses() {
		return y2Expenses;
	}

	public BigDecimal getY2Net() {
		BigDecimal y2Net = BigDecimal.ZERO;
		if (isY2Exist()) {
			if (getY2Sale() != null) {
				y2Net = getY2Sale().subtract(getY2Tax());
			}
			if (getY2Discount() != null) {
				y2Net = y2Net.subtract(getY2Discount());
			}
		}
		return y2Net;
	}

	public BigDecimal getY2Sale() {
		return y2Sale;
	}

	public BigDecimal getY2Tax() {
		return y2Tax;
	}

	public int getY2Year() {
		return y2Year;
	}

	public BigDecimal getY3Discount() {
		return y3Discount;
	}

	public BigDecimal getY3Expenses() {
		return y3Expenses;
	}

	public BigDecimal getY3Net() {
		BigDecimal y3Net = BigDecimal.ZERO;
		if (isY3Exist()) {
			if (getY3Sale() != null) {
				y3Net = getY3Sale().subtract(getY3Tax());
			}
			if (getY3Discount() != null) {
				y3Net = y3Net.subtract(getY3Discount());
			}
		}
		return y3Net;
	}

	public BigDecimal getY3Sale() {
		return y3Sale;
	}

	public BigDecimal getY3Tax() {
		return y3Tax;
	}

	public int getY3Year() {
		return y3Year;
	}

	public BigDecimal getY4Discount() {
		return y4Discount;
	}

	public BigDecimal getY4Expenses() {
		return y4Expenses;
	}

	public BigDecimal getY4Net() {
		BigDecimal y4Net = BigDecimal.ZERO;
		if (isY4Exist()) {
			if (getY4Sale() != null) {
				y4Net = getY4Sale().subtract(getY4Tax());
			}
			if (getY4Discount() != null) {
				y4Net = y4Net.subtract(getY4Discount());
			}
		}
		return y4Net;
	}

	public BigDecimal getY4Sale() {
		return y4Sale;
	}

	public BigDecimal getY4Tax() {
		return y4Tax;
	}

	public int getY4Year() {
		return y4Year;
	}

	public BigDecimal getY5Discount() {
		return y5Discount;
	}

	public BigDecimal getY5Expenses() {
		return y5Expenses;
	}

	public BigDecimal getY5Net() {
		BigDecimal y5Net = BigDecimal.ZERO;
		if (isY5Exist()) {
			if (getY5Sale() != null) {
				y5Net = getY5Sale().subtract(getY5Tax());
			}
			if (getY5Discount() != null) {
				y5Net = y5Net.subtract(getY5Discount());
			}
		}
		return y5Net;
	}

	public BigDecimal getY5Sale() {
		return y5Sale;
	}

	public BigDecimal getY5Tax() {
		return y5Tax;
	}

	public int getY5Year() {
		return y5Year;
	}

	public boolean isY1Exist() {
		return y1Exist;
	}

	public boolean isY2Exist() {
		return y2Exist;
	}

	public boolean isY3Exist() {
		return y3Exist;
	}

	public boolean isY4Exist() {
		return y4Exist;
	}

	public boolean isY5Exist() {
		return y5Exist;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setY1Discount(BigDecimal y1Discount) {
		this.y1Discount = y1Discount;
	}

	public void setY1Exist(boolean y1Exist) {
		this.y1Exist = y1Exist;
	}

	public void setY1Expenses(BigDecimal y1Expenses) {
		this.y1Expenses = y1Expenses;
	}

	public void setY1Sale(BigDecimal y1Sale) {
		this.y1Sale = y1Sale;
	}

	public void setY1Tax(BigDecimal y1Tax) {
		this.y1Tax = y1Tax;
	}

	public void setY1Year(int y1Year) {
		this.y1Year = y1Year;
	}

	public void setY2Discount(BigDecimal y2Discount) {
		this.y2Discount = y2Discount;
	}

	public void setY2Exist(boolean y2Exist) {
		this.y2Exist = y2Exist;
	}

	public void setY2Expenses(BigDecimal y2Expenses) {
		this.y2Expenses = y2Expenses;
	}

	public void setY2Sale(BigDecimal y2Sale) {
		this.y2Sale = y2Sale;
	}

	public void setY2Tax(BigDecimal y2Tax) {
		this.y2Tax = y2Tax;
	}

	public void setY2Year(int y2Year) {
		this.y2Year = y2Year;
	}

	public void setY3Discount(BigDecimal y3Discount) {
		this.y3Discount = y3Discount;
	}

	public void setY3Exist(boolean y3Exist) {
		this.y3Exist = y3Exist;
	}

	public void setY3Expenses(BigDecimal y3Expenses) {
		this.y3Expenses = y3Expenses;
	}

	public void setY3Sale(BigDecimal y3Sale) {
		this.y3Sale = y3Sale;
	}

	public void setY3Tax(BigDecimal y3Tax) {
		this.y3Tax = y3Tax;
	}

	public void setY3Year(int y3Year) {
		this.y3Year = y3Year;
	}

	public void setY4Discount(BigDecimal y4Discount) {
		this.y4Discount = y4Discount;
	}

	public void setY4Exist(boolean y4Exist) {
		this.y4Exist = y4Exist;
	}

	public void setY4Expenses(BigDecimal y4Expenses) {
		this.y4Expenses = y4Expenses;
	}

	public void setY4Sale(BigDecimal y4Sale) {
		this.y4Sale = y4Sale;
	}

	public void setY4Tax(BigDecimal y4Tax) {
		this.y4Tax = y4Tax;
	}

	public void setY4Year(int y4Year) {
		this.y4Year = y4Year;
	}

	public void setY5Discount(BigDecimal y5Discount) {
		this.y5Discount = y5Discount;
	}

	public void setY5Exist(boolean y5Exist) {
		this.y5Exist = y5Exist;
	}

	public void setY5Expenses(BigDecimal y5Expenses) {
		this.y5Expenses = y5Expenses;
	}

	public void setY5Sale(BigDecimal y5Sale) {
		this.y5Sale = y5Sale;
	}

	public void setY5Tax(BigDecimal y5Tax) {
		this.y5Tax = y5Tax;
	}

	public void setY5Year(int y5Year) {
		this.y5Year = y5Year;
	}

}
