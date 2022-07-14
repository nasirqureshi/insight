package com.bvas.insight.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bvas.insight.data.StockData;

public class StockDataMapper implements RowMapper<StockData> {

	@Override
	public StockData mapRow(ResultSet rs, int rowNum) throws SQLException {

		StockData stockcheck = new StockData();

		stockcheck.setPartno(rs.getString("Partno"));
		stockcheck.setUnitsinstock(rs.getInt("unitsinstock"));
		stockcheck.setUnitsonorder(rs.getInt("UnitsOnOrder"));
		stockcheck.setReorderlevel(rs.getInt("reorderlevel"));
		stockcheck.setSafetyquantity(rs.getInt("safetyquantity"));
		stockcheck.setReturncount(rs.getInt("returncount"));
		return stockcheck;
	}
}
