package com.bvas.insight.data;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

public class PartnoMakemodelname implements Serializable {

	private static final long serialVersionUID = 1L;

	public String makemodelname;

	public String partno;

	public PartnoMakemodelname() {
		super();
	}

	public PartnoMakemodelname(String partno, String makemodelname) {
		super();
		this.partno = partno;
		this.makemodelname = makemodelname;
	}

	public String getMakemodelname() {

		return makemodelname;
	}

	public String getPartno() {

		return partno;
	}

	public void setMakemodelname(String makemodelname) {

		this.makemodelname = makemodelname;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}
}
