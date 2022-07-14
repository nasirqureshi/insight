package com.bvas.insight.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bvas.insight.data.InventoryTransferData;

public class InventoryTransferDataMapper implements RowMapper<InventoryTransferData> {

	@Override
	public InventoryTransferData mapRow(ResultSet rs, int rowNum) throws SQLException {

		InventoryTransferData inventoryTransferData = new InventoryTransferData();

		inventoryTransferData.setManufacturername(rs.getString("manufacturername"));
		inventoryTransferData.setMakeModelName(rs.getString("MakeModelName"));
		inventoryTransferData.setPartNo(rs.getString("PartNo"));

		inventoryTransferData.setUnitsinstock(rs.getInt("unitsinstock"));
		inventoryTransferData.setUnitsonorder(rs.getInt("UnitsOnOrder"));
		inventoryTransferData.setYear(rs.getString("year"));
		inventoryTransferData.setPartDescription(rs.getString("PartDescription"));
		inventoryTransferData.setLocation(rs.getString("location"));

		return inventoryTransferData;
	}
}
