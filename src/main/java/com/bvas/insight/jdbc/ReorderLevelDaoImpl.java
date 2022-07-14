package com.bvas.insight.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bvas.insight.data.ReorderLevelData;
import com.bvas.insight.data.ReorderLevelFilter;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation", "unused" })
public class ReorderLevelDaoImpl {

	@Autowired
	private DataSource chdatasourceref;

	@Autowired
	private DataSource grdatasourceref;

	@Autowired
	private DataSource mpdatasourceref;

	private final RowMapper<ReorderLevelData> reorderLevelDataMapper = new RowMapper<ReorderLevelData>() {
		@Override
		public ReorderLevelData mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReorderLevelData data = new ReorderLevelData();

			data.setCurrentCycleStartDate(rs.getDate("md.fromDate"));
			data.setCurrentCycleEndDate(rs.getDate("md.toDate"));
			data.setTargetCycleStartDate(rs.getDate("md.targetFromDate"));
			data.setTargetCycleEndDate(rs.getDate("md.targetToDate"));
			data.setSnapShotDate(rs.getDate("md.snapshotDate"));
			data.setLocation(rs.getString("md.Location"));
			data.setPartNo(rs.getString("md.partNumber"));

			data.setSnapshotInstock(rs.getInt("md.instock"));
			data.setSnapshotOnorder(rs.getInt("md.onorder"));
			data.setCurrentCycleMonth1Sale(rs.getBigDecimal("md.currentCycleMonth1Sale"));
			data.setCurrentCycleMonth2Sale(rs.getBigDecimal("md.currentCycleMonth2Sale"));
			data.setTargetCycleMonth1Sale(rs.getBigDecimal("md.newCycleMonth1Sale"));
			data.setTargetCycleMonth2Sale(rs.getBigDecimal("md.newCycleMonth2Sale"));

			data.setCurrentCycleMonth1TotalDays(rs.getInt("md.currentCycleMonth1Days"));
			data.setCurrentCycleMonth2TotalDays(rs.getInt("md.currentCycleMonth2Days"));
			data.setTargetCycleMonth1TotalDays(rs.getInt("md.newCycleMonth1Days"));
			data.setTargetCycleMonth2TotalDays(rs.getInt("md.newCycleMonth2Days"));

			data.setCurrentCycleMonth1GrowthRate(rs.getBigDecimal("md.currentCycleMonth1GR"));
			data.setCurrentCycleMonth2GrowthRate(rs.getBigDecimal("md.currentCycleMonth2GR"));
			data.setTargetCycleMonth1GrowthRate(rs.getBigDecimal("md.newCycleMonth1GR"));
			data.setTargetCycleMonth2GrowthRate(rs.getBigDecimal("md.newCycleMonth2GR"));
			data.setSafetyQty(rs.getBigDecimal("safetyQty"));

			return data;
		}
	};

	private final RowMapper<ReorderLevelFilter> reorderLevelFilterMapper = new RowMapper<ReorderLevelFilter>() {
		@Override
		public ReorderLevelFilter mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReorderLevelFilter data = new ReorderLevelFilter();
			data.setCurrentCycleStartDate(rs.getDate("fromDate"));
			data.setCurrentCycleEndDate(rs.getDate("toDate"));
			data.setTargetCycleStartDate(rs.getDate("targetFromDate"));
			data.setTargetCycleEndDate(rs.getDate("targetToDate"));
			return data;
		}
	};

	public ReorderLevelFilter getCycleInfo() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(chdatasourceref);
		String SQL = "SELECT fromDate, toDate,targetFromDate,targetToDate FROM md_ordermonthrange WHERE "
				+ "fromDate<=CURDATE() and toDate>=CURDATE() limit 1";
		List<ReorderLevelFilter> data = jdbcTemplate.query(SQL, reorderLevelFilterMapper);
		if (data != null && !data.isEmpty()) {
			return data.get(0);
		} else {
			return null;
		}
	}

	/*
	 * consolidated query SELECT
	 * md.fromDate,md.toDate,md.targetFromDate,md.targetToDate,md.snapshotDate,md.
	 * partNumber,
	 * SUM(md.instock),sum(md.onorder),sum(md.currentCycleMonth1Sale),sum(md.
	 * currentCycleMonth2Sale),sum(md.newCycleMonth1Sale),sum(md.newCycleMonth2Sale)
	 * ,
	 * md.currentCycleMonth1Days,md.currentCycleMonth2Days,md.newCycleMonth1Days,md.
	 * newCycleMonth2Days,
	 * sum(md.currentCycleMonth1GR),sum(md.currentCycleMonth2GR),sum(md.
	 * newCycleMonth1GR),sum(md.newCycleMonth2GR),sum(md.safetyQty) FROM
	 * md_invensnapshot AS md ,parts AS p WHERE p.PartNo=md.partNumber AND
	 * md.fromDate=STR_TO_DATE('2017-04-01','%Y-%m-%d') AND
	 * md.toDate=STR_TO_DATE('2017-05-15','%Y-%m-%d') AND
	 * md.targetFromDate=STR_TO_DATE('2017-05-16','%Y-%m-%d') AND
	 * md.targetToDate=STR_TO_DATE('2017-06-30','%Y-%m-%d') AND p.ordertype='T'
	 * GROUP BY md.partNumber ORDER BY md.partNumber
	 */
	private List<ReorderLevelData> getLocationWiseSnapShotData(ReorderLevelFilter filterData,
			JdbcTemplate jdbcTemplate) {
		/*
		 * SELECT
		 * md.fromDate,md.toDate,md.targetFromDate,md.targetToDate,md.snapshotDate,md.
		 * Location,md.partNumber,
		 * md.instock,md.onorder,md.currentCycleMonth1Sale,md.currentCycleMonth2Sale,md.
		 * newCycleMonth1Sale,md.newCycleMonth2Sale,
		 * md.currentCycleMonth1Days,md.currentCycleMonth2Days,md.newCycleMonth1Days,md.
		 * newCycleMonth2Days,
		 * md.currentCycleMonth1GR,md.currentCycleMonth2GR,md.newCycleMonth1GR,md.
		 * newCycleMonth2GR FROM md_invensnapshot AS md ,parts AS p WHERE
		 * p.PartNo=md.partNumber AND p.PartDescription NOT LIKE '%CAPA%' AND
		 * p.ordertype='T/E' AND md.fromDate=? AND md.toDate=? AND md.targetFromDate=?
		 * AND md.targetToDate=?;
		 */

		String SQL = "SELECT "
				+ "md.fromDate,md.toDate,md.targetFromDate,md.targetToDate,md.snapshotDate,md.Location,md.partNumber,"
				+ "md.instock,md.onorder,md.currentCycleMonth1Sale,md.currentCycleMonth2Sale,md.newCycleMonth1Sale,md.newCycleMonth2Sale,"
				+ "md.currentCycleMonth1Days,md.currentCycleMonth2Days,md.newCycleMonth1Days,md.newCycleMonth2Days,"
				+ "md.currentCycleMonth1GR,md.currentCycleMonth2GR,md.newCycleMonth1GR,md.newCycleMonth2GR,md.safetyQty "
				+ "FROM md_invensnapshot AS md ,parts AS p "
				+ "WHERE p.PartNo=md.partNumber AND md.fromDate=? AND md.toDate=? AND md.targetFromDate=? AND md.targetToDate=? AND p.ordertype=?";
		if (!filterData.isCAPAIncluded()) {
			SQL += " AND p.PartDescription NOT LIKE '%CAPA%'";
		}
		SQL += " ORDER BY md.partNumber";
		return jdbcTemplate.query(SQL, reorderLevelDataMapper,
				new Object[] { filterData.getCurrentCycleStartDate(), filterData.getCurrentCycleEndDate(),
						filterData.getTargetCycleStartDate(), filterData.getTargetCycleEndDate(),
						filterData.getOrderType() });

	}

	private void getReorderLevelAfterSnapshotData(ReorderLevelData data, DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String SQL1 = "select max(o.OrderDate) as lastOrderDate "
				+ "from vendororder o join vendorordereditems d on o.OrderNo = d.OrderNo " + "where o.IsFinal = 'Y' "
				+ "AND d.PartNo=?";
		List<Date> lastOrderDateList = jdbcTemplate.queryForList(SQL1, new Object[] { data.getPartNo() }, Date.class);
		if (lastOrderDateList != null && !lastOrderDateList.isEmpty()) {
			data.setLastOrderDate(lastOrderDateList.get(0));
		}

		String SQL = "select sum(d.Quantity) AS Qty "
				+ "from vendororder o join vendorordereditems d on o.OrderNo = d.OrderNo " + "where o.IsFinal = 'Y' " // and
																														// o.UpdatedInventory
																														// =
																														// 'N'
				+ "AND o.OrderDate>=? and d.PartNo=? group by d.PartNo";

		List<Integer> orderedQtyList = jdbcTemplate.queryForList(SQL,
				new Object[] { data.getSnapShotDate(), data.getPartNo() }, Integer.class);
		if (orderedQtyList != null && !orderedQtyList.isEmpty()) {
			data.setQtyOrderedAfterSnapshot(orderedQtyList.get(0));
		} else {
			data.setQtyOrderedAfterSnapshot(0);
		}

		SQL = "select sum(d.Quantity) AS Qty "
				+ " from vendororder o join vendorordereditems d on o.OrderNo = d.OrderNo "
				+ " where o.IsFinal = 'Y' and o.UpdatedInventory = 'Y' "
				+ " AND o.OrderDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 MONTH) and CURDATE() "
				+ " and d.PartNo=? group by d.PartNo";

		List<Integer> instockQtyList = jdbcTemplate.queryForList(SQL, new Object[] { data.getPartNo() }, Integer.class);
		if (instockQtyList != null && !instockQtyList.isEmpty()) {
			data.setInstockNow(instockQtyList.get(0));
		} else {
			data.setInstockNow(0);
		}
		SQL = "select sum(d.Quantity) AS Qty "
				+ " from vendororder o join vendorordereditems d on o.OrderNo = d.OrderNo "
				+ " where o.IsFinal = 'Y' and o.UpdatedInventory = 'N' "
				+ " AND o.OrderDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 MONTH) and CURDATE() "
				+ " and d.PartNo=? group by d.PartNo";

		List<Integer> onOrderQtyList = jdbcTemplate.queryForList(SQL, new Object[] { data.getPartNo() }, Integer.class);
		if (onOrderQtyList != null && !onOrderQtyList.isEmpty()) {
			data.setOnorderNow(onOrderQtyList.get(0));
		} else {
			data.setOnorderNow(0);
		}

		SQL = "select sum(d.Quantity) AS Qty "
				+ " from vendororder o join vendorordereditems d on o.OrderNo = d.OrderNo "
				+ " where o.ContainerNo is Not NULL and o.ContainerNo!='' and o.IsFinal = 'Y' and o.UpdatedInventory = 'N' "
				+ " AND o.OrderDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 MONTH) and CURDATE() "
				+ " and d.PartNo=? group by d.PartNo";

		List<Integer> intTransitList = jdbcTemplate.queryForList(SQL, new Object[] { data.getPartNo() }, Integer.class);
		if (intTransitList != null && !intTransitList.isEmpty()) {
			data.setInTransitNow(intTransitList.get(0));
		} else {
			data.setInTransitNow(0);
		}
	}
	// private final RowMapper<Map> reorderLevelAfterSnapshotDataMapper = new
	// RowMapper<Map>() {
	// @Override
	// public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Map<Object, Object> map = new HashMap();
	// map.put("lastOrderDate", rs.getDate("lastOrderDate"));
	// map.put("qtyOrderedAfterSnapshot", rs.getInt("Qty"));
	// return map;
	// }
	// };

	public List<ReorderLevelData> getReorderLevelQtyToOrderData(ReorderLevelFilter filterData) {
		// SELECT * FROM `md_invensnapshot`
		JdbcTemplate jdbcTemplate = new JdbcTemplate(chdatasourceref);
		List<ReorderLevelData> data = getLocationWiseSnapShotData(filterData, jdbcTemplate);
		for (ReorderLevelData reorderData : data) {
			reorderData.setCurrentCycleSQMultiplier(filterData.getCurrentCycleSQMultiplier());
			reorderData.setTargetCycleSQMultiplier(filterData.getTargetCycleSQMultiplier());
			reorderData.setCurrentCycleDemandFactor(filterData.getCurrentCycleDemandFactor());
			reorderData.setTargetCycleDemandFactor(filterData.getTargetCycleDemandFactor());
			if (reorderData.getLocation().equalsIgnoreCase("CH")) {
				getReorderLevelAfterSnapshotData(reorderData, chdatasourceref);
				// populate ch lastOrderDate,qtyOrderedAfterSnapshot
			} else if (reorderData.getLocation().equalsIgnoreCase("MP")) {
				// populate am lastOrderDate,qtyOrderedAfterSnapshot
				getReorderLevelAfterSnapshotData(reorderData, mpdatasourceref);
			} else if (reorderData.getLocation().equalsIgnoreCase("GR")) {
				// populate gr lastOrderDate,qtyOrderedAfterSnapshot
				getReorderLevelAfterSnapshotData(reorderData, grdatasourceref);
			}

		}
		return data;
	}
}
