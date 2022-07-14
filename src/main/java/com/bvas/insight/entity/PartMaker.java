package com.bvas.insight.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partmaker")
public class PartMaker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "index")
	public String index;

	@Id
	@Column(name = "series")
	public Integer series;

	public PartMaker() {
		super();
	}

	public PartMaker(Integer series, String index) {
		super();
		this.series = series;
		this.index = index;
	}

	public String getIndex() {

		return this.index;
	}

	public Integer getSeries() {

		return this.series;
	}

	public void setIndex(String index) {

		this.index = index;
	}

	public void setSeries(Integer series) {

		this.series = series;
	}
}
