package com.bvas.insight.data;

public class PreOrderPartsHelper {

	public String capa;

	public String itemdesc1;

	public String itemdesc2;

	public String oemno;

	public String plno;

	public PreOrderPartsHelper() {
		super();
	}

	public PreOrderPartsHelper(String itemdesc1, String itemdesc2, String capa, String plno, String oemno) {
		super();
		this.itemdesc1 = itemdesc1;
		this.itemdesc2 = itemdesc2;
		this.capa = capa;
		this.plno = plno;
		this.oemno = oemno;
	}

	public String getCapa() {

		return capa;
	}

	public String getItemdesc1() {

		return itemdesc1;
	}

	public String getItemdesc2() {

		return itemdesc2;
	}

	public String getOemno() {

		return oemno;
	}

	public String getPlno() {

		return plno;
	}

	public void setCapa(String capa) {

		this.capa = capa;
	}

	public void setItemdesc1(String itemdesc1) {

		this.itemdesc1 = itemdesc1;
	}

	public void setItemdesc2(String itemdesc2) {

		this.itemdesc2 = itemdesc2;
	}

	public void setOemno(String oemno) {

		this.oemno = oemno;
	}

	public void setPlno(String plno) {

		this.plno = plno;
	}
}
