package com.bvas.insight.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class AnalysisInterchangeHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal interchangetotalsold;

	public String partno;

	public AnalysisInterchangeHelper() {
		super();
	}

	public AnalysisInterchangeHelper(String partno, BigDecimal interchangetotalsold) {
		super();
		this.partno = partno;
		this.interchangetotalsold = interchangetotalsold;
	}

	public BigDecimal getInterchangetotalsold() {

		return interchangetotalsold;
	}

	public String getPartno() {

		return partno;
	}

	public void setInterchangetotalsold(BigDecimal interchangetotalsold) {

		this.interchangetotalsold = interchangetotalsold;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

}
