package com.bvas.insight.data;

public class ButtonLogic {

	public Integer archiveinvoice = 1;

	public Integer createinvoice = 0;

	public Integer modifyinvoice = 0;

	public Integer printinvoice = 0;

	public Integer resetinvoice = 1;

	public Integer returninvoice = 1;

	public ButtonLogic() {
		super();
		this.createinvoice = 0;
		this.modifyinvoice = 0;
		this.archiveinvoice = 1;
		this.printinvoice = 0;
		this.resetinvoice = 1;
		this.returninvoice = 1;
	}

	public ButtonLogic(Integer createinvoice, Integer modifyinvoice, Integer archiveinvoice, Integer printinvoice,
			Integer resetinvoice, Integer returninvoice) {
		super();
		this.createinvoice = createinvoice;
		this.modifyinvoice = modifyinvoice;
		this.archiveinvoice = archiveinvoice;
		this.printinvoice = printinvoice;
		this.resetinvoice = resetinvoice;
		this.returninvoice = returninvoice;
	}

	public Integer getArchiveinvoice() {

		return archiveinvoice;
	}

	public Integer getCreateinvoice() {

		return createinvoice;
	}

	public Integer getModifyinvoice() {

		return modifyinvoice;
	}

	public Integer getPrintinvoice() {

		return printinvoice;
	}

	public Integer getResetinvoice() {

		return resetinvoice;
	}

	public Integer getReturninvoice() {

		return returninvoice;
	}

	public void setArchiveinvoice(Integer archiveinvoice) {

		this.archiveinvoice = archiveinvoice;
	}

	public void setCreateinvoice(Integer createinvoice) {

		this.createinvoice = createinvoice;
	}

	public void setModifyinvoice(Integer modifyinvoice) {

		this.modifyinvoice = modifyinvoice;
	}

	public void setPrintinvoice(Integer printinvoice) {

		this.printinvoice = printinvoice;
	}

	public void setResetinvoice(Integer resetinvoice) {

		this.resetinvoice = resetinvoice;
	}

	public void setReturninvoice(Integer returninvoice) {

		this.returninvoice = returninvoice;
	}

}
