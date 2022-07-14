package com.bvas.insight.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Requiredquanity implements Serializable {

	public Integer chrequiredquanity;

	public Integer grrequiredquanity;

	public Integer mprequiredquanity;

	public Integer nyrequiredquanity;

	public Requiredquanity() {
		super();
	}

	public Requiredquanity(Integer mprequiredquanity, Integer nyrequiredquanity, Integer chrequiredquanity,
			Integer grrequiredquanity) {
		super();
		this.mprequiredquanity = mprequiredquanity;
		this.nyrequiredquanity = nyrequiredquanity;
		this.chrequiredquanity = chrequiredquanity;
		this.grrequiredquanity = grrequiredquanity;
	}

	public Integer getChrequiredquanity() {
		return chrequiredquanity;
	}

	public Integer getGrrequiredquanity() {
		return grrequiredquanity;
	}

	public Integer getMprequiredquanity() {
		return mprequiredquanity;
	}

	public Integer getNyrequiredquanity() {
		return nyrequiredquanity;
	}

	public void setChrequiredquanity(Integer chrequiredquanity) {
		this.chrequiredquanity = chrequiredquanity;
	}

	public void setGrrequiredquanity(Integer grrequiredquanity) {
		this.grrequiredquanity = grrequiredquanity;
	}

	public void setMprequiredquanity(Integer mprequiredquanity) {
		this.mprequiredquanity = mprequiredquanity;
	}

	public void setNyrequiredquanity(Integer nyrequiredquanity) {
		this.nyrequiredquanity = nyrequiredquanity;
	}
}
