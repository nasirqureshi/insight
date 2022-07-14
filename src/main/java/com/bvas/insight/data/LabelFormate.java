package com.bvas.insight.data;

public class LabelFormate {
	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(LabelFormate.class);
	private String leftRight, bvNo, plNo, year, model, partDesc, alsoFit[] = null, partNo, replacement, location, conNo,
			vdInvNo;

	public String[] getAlsoFit() {

		return alsoFit;
	}

	public String getAlsoFitStr() {

		String alsoFitStr = "";
		if (getAlsoFit() != null) {
			for (String fit : getAlsoFit()) {
				if (alsoFitStr.isEmpty() && !fit.trim().isEmpty()) {
					// LOGGER.info("1-fit=" + fit + "=");
					alsoFitStr += ("       " + fit.trim());
				} else if (!fit.trim().isEmpty()) {
					// LOGGER.info("2-fit=" + fit + "=");
					alsoFitStr += ("<br/>       " + fit.trim());
				}
				// alsoFitStr += alsoFitStr.isEmpty() ? (" "+fit) : ("<br/> "+
				// fit);
			}
		}
		return alsoFitStr;
	}

	public String getBvNo() {

		return bvNo;
	}

	public String getConNo() {

		return conNo;
	}

	public String getLeftRight() {

		return leftRight;
	}

	public String getLine1() {

		return "<font size=\"24\"><b>" + getLeftRight() + "    " + getBvNo() + "   </b></font>" + "<font size=\"10\">"
				+ getPlNo() + "</font>";
		// return getLeftRight()+" "+getBvNo();
	}

	public String getLine2() {

		return "<font size=\"12\">" + getYear() + "     " + getModel() + "</font>";
		// return getYear()+" "+getModel();
	}

	public String getLine3() {

		return "<font size=\"12\"><b>" + getPartDesc() + "</b></font>";
		// return getYear()+" "+getModel();
	}

	public String getLine4() {

		String lin4 = "";
		if (!getAlsoFitStr().isEmpty()) {
			lin4 = getAlsoFitStr();
		}
		if (!getPartNo().isEmpty()) {
			lin4 += !lin4.isEmpty() ? "<br/>" : "";
			lin4 += "       " + getPartNo();
		}
		// String lin4=getAlsoFitStr().isEmpty()?"
		// "+getPartNo():getAlsoFitStr()+(getPartNo().isEmpty()?"":"<br/>
		// "+getPartNo());

		if (lin4.isEmpty())
			return "";

		return "<font size=\"10\">" + lin4 + "</font>";
		// return "<font size=\"10\">"+getAlsoFitStr()+"<br/>
		// "+getPartNo()+"</font>";
		// return getAlsoFitStr()+" "+getPartNo();
	}

	public String getLine5() {

		return "<font size=\"12\"><b>" + getReplacement() + "</b></font>";
		// return getReplacement();
	}

	public String getLine6() {

		// "<font size=\"" + getLabelSize(count) + "\"><b>" + " " + keyphrase +
		// "</b></font>"
		return "<font size=\"12\">" + getLocation() + "   <b>" + getConNo() + "</b>   " + getVdInvNo() + "</font>";
	}

	public String getLocation() {

		return location;
	}

	public String getModel() {

		return model;
	}

	public String getPartDesc() {

		return partDesc;
	}

	public String getPartNo() {

		return partNo;
	}

	public String getPlNo() {

		return plNo;
	}

	public String getReplacement() {

		return replacement;
	}

	public String getReportContents() {

		// "<font size=\"3\"> </font><br/>"+
		String contents = getLine1() + "<br/>" + getLine2() + "<br/>" + getLine3();
		if (!getLine4().isEmpty()) {
			contents += "<br/>" + getLine4();
		} else {
			contents += "<br/><br/><br/>";
		}
		contents += "<br/>" + getLine5() + "<br/>" + getLine6();
		return contents.replace("&", "&amp;");
	}

	public String getVdInvNo() {

		return vdInvNo;
	}

	public String getYear() {

		return year;
	}

	public void setAlsoFit(String[] alsoFit) {

		this.alsoFit = alsoFit;
	}

	public void setBvNo(String bvNo) {

		this.bvNo = bvNo;
	}

	public void setConNo(String conNo) {

		this.conNo = conNo;
	}

	public void setLeftRight(String leftRight) {

		this.leftRight = leftRight;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public void setModel(String model) {

		this.model = model;
	}

	public void setPartDesc(String partDesc) {

		this.partDesc = partDesc;
	}

	public void setPartNo(String partNo) {

		this.partNo = partNo;
	}

	public void setPlNo(String plNo) {

		this.plNo = plNo;
	}

	public void setReplacement(String replacement) {

		this.replacement = replacement;
	}

	public void setVdInvNo(String vdInvNo) {

		this.vdInvNo = vdInvNo;
	}

	public void setYear(String year) {

		this.year = year;
	}
}
