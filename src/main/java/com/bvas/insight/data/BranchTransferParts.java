package com.bvas.insight.data;

import java.io.Serializable;

public class BranchTransferParts implements Serializable, Comparable<Object> {

	private static final long serialVersionUID = 1L;

	public String makemodelname;

	public String manufacturername;

	public String partdescription;

	public String partno;

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
