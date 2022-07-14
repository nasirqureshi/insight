package com.bvas.insight.data;

import java.math.BigInteger;

public class TransferParts {

	public Integer chneed;

	public Integer chreorderlevel;

	public Integer chsafetyquantity;

	public Integer chshow;

	public Integer chunitsinstock;

	public Integer chunitsonorder;

	public Integer grneed;

	public Integer grreorderlevel;

	public Integer grsafetyquantity;

	public Integer grshow;

	public Integer grunitsinstock;

	public Integer grunitsonorder;

	public String location;

	public String makemodelname;

	public String manufacturername;

	public Integer mpneed;

	public Integer mpreorderlevel;

	public Integer mpsafetyquantity;

	public Integer mpshow;

	public Integer mpunitsinstock;

	public Integer mpunitsonorder;

	public Integer nyneed;

	public Integer nyreorderlevel;

	public Integer nysafetyquantity;

	public Integer nyshow;

	public Integer nyunitsinstock;

	public Integer nyunitsonorder;

	public String partdescription;

	public String partno;

	public Integer reorderlevel;

	public BigInteger safetyquantity;

	public Integer unitsinstock;

	public Integer unitsonorder;

	public Integer yearfrom;

	public Integer yearto;

	public TransferParts() {
		super();
		this.partno = "";
		this.makemodelname = "";
		this.partdescription = "";
		this.reorderlevel = 0;
		this.unitsinstock = 0;
		this.unitsonorder = 0;
		this.safetyquantity = new BigInteger("0");
		this.yearfrom = 0;
		this.yearto = 0;
		this.location = "";
		this.manufacturername = "";
		this.mpreorderlevel = 0;
		this.mpunitsinstock = 0;
		this.mpunitsonorder = 0;
		this.mpsafetyquantity = 0;
		this.nyreorderlevel = 0;
		this.nyunitsinstock = 0;
		this.nyunitsonorder = 0;
		this.nysafetyquantity = 0;
		this.chreorderlevel = 0;
		this.chsafetyquantity = 0;
		this.chunitsinstock = 0;
		this.chunitsonorder = 0;
		this.grreorderlevel = 0;
		this.grunitsinstock = 0;
		this.grunitsonorder = 0;
		this.grsafetyquantity = 0;
		this.mpshow = 0;
		this.nyshow = 0;
		this.grshow = 0;
		this.chshow = 0;
		this.mpneed = 0;
		this.nyneed = 0;
		this.grneed = 0;
		this.chneed = 0;
	}

	public TransferParts(String partno, String manufacturername, String makemodelname, String partdescription,
			Integer reorderlevel, Integer unitsinstock, Integer unitsonorder, BigInteger safetyquantity,
			Integer yearfrom, Integer yearto, String location, Integer mpreorderlevel, Integer mpunitsinstock,
			Integer mpunitsonorder, Integer mpsafetyquantity, Integer nyreorderlevel, Integer nyunitsinstock,
			Integer nyunitsonorder, Integer nysafetyquantity, Integer chreorderlevel, Integer chsafetyquantity,
			Integer chunitsinstock, Integer chunitsonorder, Integer grreorderlevel, Integer grunitsinstock,
			Integer grunitsonorder, Integer grsafetyquantity, Integer mpshow, Integer nyshow, Integer grshow,
			Integer chshow, Integer mpneed, Integer nyneed, Integer grneed, Integer chneed) {
		super();
		this.partno = partno;
		this.manufacturername = manufacturername;
		this.makemodelname = makemodelname;
		this.partdescription = partdescription;
		this.reorderlevel = reorderlevel;
		this.unitsinstock = unitsinstock;
		this.unitsonorder = unitsonorder;
		this.safetyquantity = safetyquantity;
		this.yearfrom = yearfrom;
		this.yearto = yearto;
		this.location = location;
		this.mpreorderlevel = mpreorderlevel;
		this.mpunitsinstock = mpunitsinstock;
		this.mpunitsonorder = mpunitsonorder;
		this.mpsafetyquantity = mpsafetyquantity;
		this.nyreorderlevel = nyreorderlevel;
		this.nyunitsinstock = nyunitsinstock;
		this.nyunitsonorder = nyunitsonorder;
		this.nysafetyquantity = nysafetyquantity;
		this.chreorderlevel = chreorderlevel;
		this.chsafetyquantity = chsafetyquantity;
		this.chunitsinstock = chunitsinstock;
		this.chunitsonorder = chunitsonorder;
		this.grreorderlevel = grreorderlevel;
		this.grunitsinstock = grunitsinstock;
		this.grunitsonorder = grunitsonorder;
		this.grsafetyquantity = grsafetyquantity;
		this.mpshow = mpshow;
		this.nyshow = nyshow;
		this.grshow = grshow;
		this.chshow = chshow;
		this.mpneed = mpneed;
		this.nyneed = nyneed;
		this.grneed = grneed;
		this.chneed = chneed;
	}

	public Integer getChneed() {
		return chneed;
	}

	public Integer getChreorderlevel() {
		return chreorderlevel;
	}

	public Integer getChsafetyquantity() {
		return chsafetyquantity;
	}

	public Integer getChshow() {
		return chshow;
	}

	public Integer getChunitsinstock() {
		return chunitsinstock;
	}

	public Integer getChunitsonorder() {
		return chunitsonorder;
	}

	public Integer getGrneed() {
		return grneed;
	}

	public Integer getGrreorderlevel() {
		return grreorderlevel;
	}

	public Integer getGrsafetyquantity() {
		return grsafetyquantity;
	}

	public Integer getGrshow() {
		return grshow;
	}

	public Integer getGrunitsinstock() {
		return grunitsinstock;
	}

	public Integer getGrunitsonorder() {
		return grunitsonorder;
	}

	public String getLocation() {
		return location;
	}

	public String getMakemodelname() {
		return makemodelname;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public Integer getMpneed() {
		return mpneed;
	}

	public Integer getMpreorderlevel() {
		return mpreorderlevel;
	}

	public Integer getMpsafetyquantity() {
		return mpsafetyquantity;
	}

	public Integer getMpshow() {
		return mpshow;
	}

	public Integer getMpunitsinstock() {
		return mpunitsinstock;
	}

	public Integer getMpunitsonorder() {
		return mpunitsonorder;
	}

	public Integer getNyneed() {
		return nyneed;
	}

	public Integer getNyreorderlevel() {
		return nyreorderlevel;
	}

	public Integer getNysafetyquantity() {
		return nysafetyquantity;
	}

	public Integer getNyshow() {
		return nyshow;
	}

	public Integer getNyunitsinstock() {
		return nyunitsinstock;
	}

	public Integer getNyunitsonorder() {
		return nyunitsonorder;
	}

	public String getPartdescription() {
		return partdescription;
	}

	public String getPartno() {
		return partno;
	}

	public Integer getReorderlevel() {
		return reorderlevel;
	}

	public BigInteger getSafetyquantity() {
		return safetyquantity;
	}

	public Integer getUnitsinstock() {
		return unitsinstock;
	}

	public Integer getUnitsonorder() {
		return unitsonorder;
	}

	public Integer getYearfrom() {
		return yearfrom;
	}

	public Integer getYearto() {
		return yearto;
	}

	public void setChneed(Integer chneed) {
		this.chneed = chneed;
	}

	public void setChreorderlevel(Integer chreorderlevel) {
		this.chreorderlevel = chreorderlevel;
	}

	public void setChsafetyquantity(Integer chsafetyquantity) {
		this.chsafetyquantity = chsafetyquantity;
	}

	public void setChshow(Integer chshow) {
		this.chshow = chshow;
	}

	public void setChunitsinstock(Integer chunitsinstock) {
		this.chunitsinstock = chunitsinstock;
	}

	public void setChunitsonorder(Integer chunitsonorder) {
		this.chunitsonorder = chunitsonorder;
	}

	public void setGrneed(Integer grneed) {
		this.grneed = grneed;
	}

	public void setGrreorderlevel(Integer grreorderlevel) {
		this.grreorderlevel = grreorderlevel;
	}

	public void setGrsafetyquantity(Integer grsafetyquantity) {
		this.grsafetyquantity = grsafetyquantity;
	}

	public void setGrshow(Integer grshow) {
		this.grshow = grshow;
	}

	public void setGrunitsinstock(Integer grunitsinstock) {
		this.grunitsinstock = grunitsinstock;
	}

	public void setGrunitsonorder(Integer grunitsonorder) {
		this.grunitsonorder = grunitsonorder;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMakemodelname(String makemodelname) {
		this.makemodelname = makemodelname;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public void setMpneed(Integer mpneed) {
		this.mpneed = mpneed;
	}

	public void setMpreorderlevel(Integer mpreorderlevel) {
		this.mpreorderlevel = mpreorderlevel;
	}

	public void setMpsafetyquantity(Integer mpsafetyquantity) {
		this.mpsafetyquantity = mpsafetyquantity;
	}

	public void setMpshow(Integer mpshow) {
		this.mpshow = mpshow;
	}

	public void setMpunitsinstock(Integer mpunitsinstock) {
		this.mpunitsinstock = mpunitsinstock;
	}

	public void setMpunitsonorder(Integer mpunitsonorder) {
		this.mpunitsonorder = mpunitsonorder;
	}

	public void setNyneed(Integer nyneed) {
		this.nyneed = nyneed;
	}

	public void setNyreorderlevel(Integer nyreorderlevel) {
		this.nyreorderlevel = nyreorderlevel;
	}

	public void setNysafetyquantity(Integer nysafetyquantity) {
		this.nysafetyquantity = nysafetyquantity;
	}

	public void setNyshow(Integer nyshow) {
		this.nyshow = nyshow;
	}

	public void setNyunitsinstock(Integer nyunitsinstock) {
		this.nyunitsinstock = nyunitsinstock;
	}

	public void setNyunitsonorder(Integer nyunitsonorder) {
		this.nyunitsonorder = nyunitsonorder;
	}

	public void setPartdescription(String partdescription) {
		this.partdescription = partdescription;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public void setReorderlevel(Integer reorderlevel) {
		this.reorderlevel = reorderlevel;
	}

	public void setSafetyquantity(BigInteger safetyquantity) {
		this.safetyquantity = safetyquantity;
	}

	public void setUnitsinstock(Integer unitsinstock) {
		this.unitsinstock = unitsinstock;
	}

	public void setUnitsonorder(Integer unitsonorder) {
		this.unitsonorder = unitsonorder;
	}

	public void setYearfrom(Integer yearfrom) {
		this.yearfrom = yearfrom;
	}

	public void setYearto(Integer yearto) {
		this.yearto = yearto;
	}

}
