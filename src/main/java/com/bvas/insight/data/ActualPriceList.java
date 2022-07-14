package com.bvas.insight.data;

import java.math.BigDecimal;
import java.util.Comparator;

public class ActualPriceList implements Comparable<ActualPriceList> {

	public static Comparator<ActualPriceList> ActualPriceListComparator = new Comparator<ActualPriceList>() {

		@Override
		public int compare(ActualPriceList pl1, ActualPriceList pl2) {

			Integer noorder1 = pl1.getNoorder();
			Integer noorder2 = pl2.getNoorder();

			// ascending
			// order
			return noorder1.compareTo(noorder2);
		}
	};

	public BigDecimal actualprice;

	public Integer noorder;

	public Integer orderno;

	public String partno;

	public Integer quantity;

	public BigDecimal total;

	@Override
	public int compareTo(ActualPriceList o) {

		int noorder = o.getNoorder();

		// ascending order
		return this.noorder - noorder;
	}

	public BigDecimal getActualprice() {

		return actualprice;
	}

	public Integer getNoorder() {

		return noorder;
	}

	public Integer getOrderno() {

		return orderno;
	}

	public String getPartno() {

		return partno;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public BigDecimal getTotal() {

		return total;
	}

	public void setActualprice(BigDecimal actualprice) {

		this.actualprice = actualprice;
	}

	public void setNoorder(Integer noorder) {

		this.noorder = noorder;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

	public void setTotal(BigDecimal total) {

		this.total = total;
	}

}
