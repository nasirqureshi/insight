package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "levelcalc")
public class LevelCalc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "level1")
	private BigDecimal level1;

	@Column(name = "level10")
	private BigDecimal level10;

	@Column(name = "level11")
	private BigDecimal level11;

	@Column(name = "level12")
	private BigDecimal level12;

	@Column(name = "level2")
	private BigDecimal level2;

	@Column(name = "level3")
	private BigDecimal level3;

	@Column(name = "level4")
	private BigDecimal level4;

	@Column(name = "level5")
	private BigDecimal level5;

	@Column(name = "level6")
	private BigDecimal level6;

	@Column(name = "level7")
	private BigDecimal level7;

	@Column(name = "level8")
	private BigDecimal level8;

	@Column(name = "level9")
	private BigDecimal level9;

	@Id
	@Column(name = "percentfrom")
	public BigDecimal percentfrom;

	@Id
	@Column(name = "percentto")
	public BigDecimal percentto;

	public LevelCalc() {
		super();
	}

	public LevelCalc(BigDecimal level1, BigDecimal level10, BigDecimal level11, BigDecimal level12, BigDecimal level2,
			BigDecimal level3, BigDecimal level4, BigDecimal level5, BigDecimal level6, BigDecimal level7,
			BigDecimal level8, BigDecimal level9, BigDecimal percentfrom, BigDecimal percentto) {
		super();
		this.level1 = level1;
		this.level10 = level10;
		this.level11 = level11;
		this.level12 = level12;
		this.level2 = level2;
		this.level3 = level3;
		this.level4 = level4;
		this.level5 = level5;
		this.level6 = level6;
		this.level7 = level7;
		this.level8 = level8;
		this.level9 = level9;
		this.percentfrom = percentfrom;
		this.percentto = percentto;
	}

	public BigDecimal getLevel1() {

		return level1;
	}

	public BigDecimal getLevel10() {

		return level10;
	}

	public BigDecimal getLevel11() {

		return level11;
	}

	public BigDecimal getLevel12() {

		return level12;
	}

	public BigDecimal getLevel2() {

		return level2;
	}

	public BigDecimal getLevel3() {

		return level3;
	}

	public BigDecimal getLevel4() {

		return level4;
	}

	public BigDecimal getLevel5() {

		return level5;
	}

	public BigDecimal getLevel6() {

		return level6;
	}

	public BigDecimal getLevel7() {

		return level7;
	}

	public BigDecimal getLevel8() {

		return level8;
	}

	public BigDecimal getLevel9() {

		return level9;
	}

	public BigDecimal getPercentfrom() {

		return percentfrom;
	}

	public BigDecimal getPercentto() {

		return percentto;
	}

	public void setLevel1(BigDecimal level1) {

		this.level1 = level1;
	}

	public void setLevel10(BigDecimal level10) {

		this.level10 = level10;
	}

	public void setLevel11(BigDecimal level11) {

		this.level11 = level11;
	}

	public void setLevel12(BigDecimal level12) {

		this.level12 = level12;
	}

	public void setLevel2(BigDecimal level2) {

		this.level2 = level2;
	}

	public void setLevel3(BigDecimal level3) {

		this.level3 = level3;
	}

	public void setLevel4(BigDecimal level4) {

		this.level4 = level4;
	}

	public void setLevel5(BigDecimal level5) {

		this.level5 = level5;
	}

	public void setLevel6(BigDecimal level6) {

		this.level6 = level6;
	}

	public void setLevel7(BigDecimal level7) {

		this.level7 = level7;
	}

	public void setLevel8(BigDecimal level8) {

		this.level8 = level8;
	}

	public void setLevel9(BigDecimal level9) {

		this.level9 = level9;
	}

	public void setPercentfrom(BigDecimal percentfrom) {

		this.percentfrom = percentfrom;
	}

	public void setPercentto(BigDecimal percentto) {

		this.percentto = percentto;
	}

}
