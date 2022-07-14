package com.bvas.insight.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScanOrderDetails implements Serializable {

	public Integer chreorderlevel;

	public Integer chreturncount;

	public Integer chsafetyquantity;

	public Integer chunitsinstock;

	public Integer chunitsonorder;

	public Integer grreorderlevel;

	public Integer grreturncount;

	public Integer grsafetyquantity;

	public Integer grunitsinstock;

	public Integer grunitsonorder;

	public String makemodelname;

	public String manufacturername;

	public Integer mpreorderlevel;

	public Integer mpreturncount;

	public Integer mpsafetyquantity;

	public Integer mpunitsinstock;

	public Integer mpunitsonorder;

	public String partdescription;

	public String partno;

	public Integer quantity;

	public ScanOrderDetails() {
		super();
	}

	public ScanOrderDetails(Integer mpreorderlevel, Integer mpunitsinstock, Integer mpunitsonorder,
			Integer mpsafetyquantity, Integer chreorderlevel, Integer chsafetyquantity, Integer chunitsinstock,
			Integer chunitsonorder, Integer grreorderlevel, Integer grunitsinstock, Integer grunitsonorder,
			Integer grsafetyquantity, String makemodelname, String manufacturername, String partdescription,
			String partno, Integer quantity, Integer chreturncount, Integer grreturncount, Integer mpreturncount) {
		super();
		this.mpreorderlevel = mpreorderlevel;
		this.mpunitsinstock = mpunitsinstock;
		this.mpunitsonorder = mpunitsonorder;
		this.mpsafetyquantity = mpsafetyquantity;
		this.chreorderlevel = chreorderlevel;
		this.chsafetyquantity = chsafetyquantity;
		this.chunitsinstock = chunitsinstock;
		this.chunitsonorder = chunitsonorder;
		this.grreorderlevel = grreorderlevel;
		this.grunitsinstock = grunitsinstock;
		this.grunitsonorder = grunitsonorder;
		this.grsafetyquantity = grsafetyquantity;
		this.makemodelname = makemodelname;
		this.manufacturername = manufacturername;
		this.partdescription = partdescription;
		this.partno = partno;
		this.quantity = quantity;
		this.chreturncount = chreturncount;
		this.grreturncount = grreturncount;
		this.mpreturncount = mpreturncount;
	}

	public Integer getChreorderlevel() {
		return chreorderlevel;
	}

	public Integer getChreturncount() {
		return chreturncount;
	}

	public Integer getChsafetyquantity() {
		return chsafetyquantity;
	}

	public Integer getChunitsinstock() {
		return chunitsinstock;
	}

	public Integer getChunitsonorder() {
		return chunitsonorder;
	}

	public Integer getGrreorderlevel() {
		return grreorderlevel;
	}

	public Integer getGrreturncount() {
		return grreturncount;
	}

	public Integer getGrsafetyquantity() {
		return grsafetyquantity;
	}

	public Integer getGrunitsinstock() {
		return grunitsinstock;
	}

	public Integer getGrunitsonorder() {
		return grunitsonorder;
	}

	public String getMakemodelname() {
		return makemodelname;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public Integer getMpreorderlevel() {
		return mpreorderlevel;
	}

	public Integer getMpreturncount() {
		return mpreturncount;
	}

	public Integer getMpsafetyquantity() {
		return mpsafetyquantity;
	}

	public Integer getMpunitsinstock() {
		return mpunitsinstock;
	}

	public Integer getMpunitsonorder() {
		return mpunitsonorder;
	}

	public String getPartdescription() {
		return partdescription;
	}

	public String getPartno() {
		return partno;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setChreorderlevel(Integer chreorderlevel) {
		this.chreorderlevel = chreorderlevel;
	}

	public void setChreturncount(Integer chreturncount) {
		this.chreturncount = chreturncount;
	}

	public void setChsafetyquantity(Integer chsafetyquantity) {
		this.chsafetyquantity = chsafetyquantity;
	}

	public void setChunitsinstock(Integer chunitsinstock) {
		this.chunitsinstock = chunitsinstock;
	}

	public void setChunitsonorder(Integer chunitsonorder) {
		this.chunitsonorder = chunitsonorder;
	}

	public void setGrreorderlevel(Integer grreorderlevel) {
		this.grreorderlevel = grreorderlevel;
	}

	public void setGrreturncount(Integer grreturncount) {
		this.grreturncount = grreturncount;
	}

	public void setGrsafetyquantity(Integer grsafetyquantity) {
		this.grsafetyquantity = grsafetyquantity;
	}

	public void setGrunitsinstock(Integer grunitsinstock) {
		this.grunitsinstock = grunitsinstock;
	}

	public void setGrunitsonorder(Integer grunitsonorder) {
		this.grunitsonorder = grunitsonorder;
	}

	public void setMakemodelname(String makemodelname) {
		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public void setMpreorderlevel(Integer mpreorderlevel) {
		this.mpreorderlevel = mpreorderlevel;
	}

	public void setMpreturncount(Integer mpreturncount) {
		this.mpreturncount = mpreturncount;
	}

	public void setMpsafetyquantity(Integer mpsafetyquantity) {
		this.mpsafetyquantity = mpsafetyquantity;
	}

	public void setMpunitsinstock(Integer mpunitsinstock) {
		this.mpunitsinstock = mpunitsinstock;
	}

	public void setMpunitsonorder(Integer mpunitsonorder) {
		this.mpunitsonorder = mpunitsonorder;
	}

	public void setPartdescription(String partdescription) {
		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
