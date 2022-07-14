package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class ProcessedOrdersPartsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	public String companyname;

	public Date inventorydonedate;

	public String itemdesc1;

	public String itemdesc2;

	public Integer orderno;

	public String partno;

	public BigDecimal price;

	public Integer quantity;

	public ProcessedOrdersPartsHistory() {
		super();
	}

	public ProcessedOrdersPartsHistory(String partno, Integer orderno, Date inventorydonedate, Integer quantity,
			String itemdesc1, String itemdesc2, String companyname, BigDecimal price) {
		super();
		this.partno = partno;
		this.orderno = orderno;
		this.inventorydonedate = inventorydonedate;
		this.quantity = quantity;
		this.itemdesc1 = itemdesc1;
		this.itemdesc2 = itemdesc2;
		this.companyname = companyname;
		this.price = price;
	}

	public String getCompanyname() {

		return companyname;
	}

	public Date getInventorydonedate() {

		return inventorydonedate;
	}

	public String getItemdesc1() {

		return itemdesc1;
	}

	public String getItemdesc2() {

		return itemdesc2;
	}

	public Integer getorderno() {

		return orderno;
	}

	public String getPartno() {

		return partno;
	}

	public BigDecimal getPrice() {

		return price;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public void setInventorydonedate(Date inventorydonedate) {

		this.inventorydonedate = inventorydonedate;
	}

	public void setItemdesc1(String itemdesc1) {

		this.itemdesc1 = itemdesc1;
	}

	public void setItemdesc2(String itemdesc2) {

		this.itemdesc2 = itemdesc2;
	}

	public void setorderno(Integer orderno) {

		this.orderno = orderno;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

}
