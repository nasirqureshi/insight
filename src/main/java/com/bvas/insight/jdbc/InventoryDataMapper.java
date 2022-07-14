package com.bvas.insight.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bvas.insight.data.InvCategoryBySalesAnalysis;

public class InventoryDataMapper implements RowMapper<InvCategoryBySalesAnalysis> {

	@Override
	public InvCategoryBySalesAnalysis mapRow(ResultSet rs, int rowNum) throws SQLException {

		InvCategoryBySalesAnalysis stockcheck = new InvCategoryBySalesAnalysis();

		stockcheck.setPartno(rs.getString("Partno"));
		stockcheck.setTotalsold(rs.getInt("totalsold"));

		return stockcheck;
	}
}
