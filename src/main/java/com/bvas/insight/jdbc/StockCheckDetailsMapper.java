package com.bvas.insight.jdbc;

// ~--- non-JDK imports --------------------------------------------------------

// ~--- JDK imports ------------------------------------------------------------
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bvas.insight.data.StockCheckDetails;

public class StockCheckDetailsMapper implements RowMapper<StockCheckDetails> {

	@Override
	public StockCheckDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

		StockCheckDetails stockcheckdetails = new StockCheckDetails();

		stockcheckdetails.setCompanyname(rs.getString("CompanyName"));
		stockcheckdetails.setVendorpartno(rs.getString("vendorpartno"));

		return stockcheckdetails;
	}
}
