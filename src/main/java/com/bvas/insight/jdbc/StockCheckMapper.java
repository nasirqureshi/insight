package com.bvas.insight.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bvas.insight.data.StockCheck;

public class StockCheckMapper implements RowMapper<StockCheck> {

	@Override
	public StockCheck mapRow(ResultSet rs, int rowNum) throws SQLException {

		StockCheck stockcheck = new StockCheck();

		stockcheck.setInterchangepartno(rs.getString("InterchangeNo"));
		stockcheck.setLocation(rs.getString("Location"));
		stockcheck.setPartdescription(rs.getString("Partdescription"));
		stockcheck.setPartno(rs.getString("Partno"));
		stockcheck.setUnitsinstock(rs.getInt("unitsinstock"));
		stockcheck.setUnitsonorder(rs.getInt("UnitsOnOrder"));
		stockcheck.setMakemodelname(rs.getString("makemodelname"));
		stockcheck.setManufacturername(rs.getString("manufacturername"));
		stockcheck.setReorderlevel(rs.getInt("reorderlevel"));
		stockcheck.setOrdertype(rs.getString("ordertype"));
		stockcheck.setSafetyquantity(rs.getInt("safetyquantity"));
		stockcheck.setReturncount(rs.getInt("returncount"));
		stockcheck.setPricelock(rs.getString("pricelock"));
		return stockcheck;
	}
}
