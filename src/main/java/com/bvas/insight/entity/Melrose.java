package com.bvas.insight.entity;

// ~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "melrose")
public class Melrose implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "orderno")
	private Integer orderno;

	@Column(name = "partno")
	private String partno;

	@Column(name = "quantity")
	private Integer quantity;

	public Melrose() {
		super();
	}

	public Melrose(Integer id, Integer orderno, String partno, Integer quantity) {
		super();
		this.id = id;
		this.orderno = orderno;
		this.partno = partno;
		this.quantity = quantity;
	}

	public Integer getId() {

		return id;
	}

	public Integer getOrderno() {

		return orderno;
	}

	public String getPartno() {

		return partno;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public void setOrderno(Integer orderno) {

		this.orderno = orderno;
	}

	public void setPartno(String partno) {

		this.partno = partno;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}
}
