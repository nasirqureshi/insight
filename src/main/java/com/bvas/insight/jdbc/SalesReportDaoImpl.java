package com.bvas.insight.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bvas.insight.data.SalesDetailReport;
import com.bvas.insight.data.SalesDetailSalesPersonReport;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation", "unused" })
public class SalesReportDaoImpl {

	@Autowired
	private DataSource chdatasourceref;

	@Autowired
	private DataSource grdatasourceref;

	@Autowired
	private DataSource localDataSource;

	@Autowired
	private DataSource mpdatasourceref;

	private final RowMapper<SalesDetailReport> regionReturnsMapper = new RowMapper<SalesDetailReport>() {

		@Override
		public SalesDetailReport mapRow(ResultSet rs, int rowNum) throws SQLException {

			SalesDetailReport sd = new SalesDetailReport();

			sd.setReturned(rs.getBigDecimal("OrderReturns"));
			sd.setRegion(rs.getString("region"));

			return sd;
		}
	};

	private final RowMapper<SalesDetailReport> regionSalesMapper = new RowMapper<SalesDetailReport>() {

		@Override
		public SalesDetailReport mapRow(ResultSet rs, int rowNum) throws SQLException {

			SalesDetailReport sd = new SalesDetailReport();

			sd.setSale(rs.getBigDecimal("totalSale"));
			sd.setRegion(rs.getString("region"));

			return sd;
		}
	};

	private final RowMapper<SalesDetailReport> salesDeatailsMapper = new RowMapper<SalesDetailReport>() {

		@Override
		public SalesDetailReport mapRow(ResultSet rs, int rowNum) throws SQLException {

			SalesDetailReport sd = new SalesDetailReport();

			sd.setCount(rs.getInt("OrderCount"));
			sd.setDiscount(rs.getBigDecimal("OrderDiscount"));
			sd.setSale(rs.getBigDecimal("OrderSales"));
			sd.setTax(rs.getBigDecimal("OrderTax"));

			// sd.setGross(rs.getBigDecimal("vendorpartno"));
			// sd.setNet(rs.getBigDecimal("vendorpartno"));
			// sd.setReturned(rs.getBigDecimal("vendorpartno"));
			// sd.setYear(rowNum);
			return sd;
		}
	};

	private final RowMapper<SalesDetailSalesPersonReport> salesPersonMapper = new RowMapper<SalesDetailSalesPersonReport>() {

		@Override
		public SalesDetailSalesPersonReport mapRow(ResultSet rs, int rowNum) throws SQLException {

			SalesDetailSalesPersonReport sd = new SalesDetailSalesPersonReport();
			sd.setSalesPerson(rs.getString("SalesPerson"));
			sd.setReturnInvCount(rs.getInt("ReturnInvoiceCount"));
			sd.setReturns(rs.getBigDecimal("ReturnSum"));
			sd.setSale(rs.getBigDecimal("InvoiceTotal"));
			return sd;
		}
	};

	private Map getRegionReturns(boolean onlyEndDate, Date dateStart, Date dateEnd) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(localDataSource);

		String where;
		if (!onlyEndDate) {
			where = "i.orderdate >= ? AND i.orderdate <= ?";
		} else {
			where = "i.orderdate = ?";
		}
		String SQL2 = "SELECT SUM(i.InvoiceTotal) AS OrderReturns, a.region AS region FROM Invoice i, Customer c , Address a WHERE "
				+ "i.customerId = c.customerId AND a.id  = c.customerId AND i.customerId = a.id AND a.type = 'Standard' "
				+ "AND i.ReturnedInvoice <> 0 AND " + where + " GROUP BY a.region";
		if (!onlyEndDate) {
			List<SalesDetailReport> salesDetails0 = jdbcTemplate.query(SQL2, regionReturnsMapper,
					new Object[] { dateStart, dateEnd });
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
			Map map0 = new HashMap();
			map0.put("regionReturnList", salesDetails0);
			map0.put("header", df.format(dateStart) + " to " + df.format(dateEnd));
			return map0;
		} else {
			List<SalesDetailReport> salesDetails0 = jdbcTemplate.query(SQL2, regionReturnsMapper,
					new Object[] { dateEnd });
			SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
			Map map0 = new HashMap();
			map0.put("regionReturnList", salesDetails0);
			map0.put("header", df.format(dateEnd));
			return map0;
		}

	}

	public Map getRegionReturns(Date dateStart, Date dateEnd) {

		boolean onlyDateEnd;
		if (dateStart != null && dateEnd != null) {
			onlyDateEnd = false;
		} else {
			onlyDateEnd = true;
		}
		dateStart = getZeroTimeDate(dateStart);
		dateEnd = getZeroTimeDate(dateEnd);
		Map map = new HashMap();

		if (!onlyDateEnd) {
			Date date0_0 = dateStart, date0_1 = dateEnd, date1_0 = (Date) dateStart.clone(),
					date1_1 = (Date) dateEnd.clone(), date2_0 = (Date) dateStart.clone(),
					date2_1 = (Date) dateEnd.clone(), date3_0 = (Date) dateStart.clone(),
					date3_1 = (Date) dateEnd.clone(), date4_0 = (Date) dateStart.clone(),
					date4_1 = (Date) dateEnd.clone();
			date1_0.setYear(date1_0.getYear() - 1);
			date1_1.setYear(date1_1.getYear() - 1);
			date2_0.setYear(date2_0.getYear() - 2);
			date2_1.setYear(date2_1.getYear() - 2);
			date3_0.setYear(date3_0.getYear() - 3);
			date3_1.setYear(date3_1.getYear() - 3);
			date4_0.setYear(date4_0.getYear() - 4);
			date4_1.setYear(date4_1.getYear() - 4);

			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			map.put("header", "Returns by Region from " + formatter.format(date0_0) + "-... to "
					+ formatter.format(date0_1) + "-...");
			map.put("date0", getRegionReturns(onlyDateEnd, date0_0, date0_1));
			map.put("date1", getRegionReturns(onlyDateEnd, date1_0, date1_1));
			map.put("date2", getRegionReturns(onlyDateEnd, date2_0, date2_1));
			map.put("date3", getRegionReturns(onlyDateEnd, date3_0, date3_1));
			map.put("date4", getRegionReturns(onlyDateEnd, date4_0, date4_1));

		} else {
			Calendar cal = new GregorianCalendar();
			Date date0_1 = dateEnd, date1_1 = (Date) dateEnd.clone(), date2_1 = (Date) dateEnd.clone(),
					date3_1 = (Date) dateEnd.clone(), date4_1 = (Date) dateEnd.clone();
			cal.setTime(date1_1);
			cal.add(Calendar.WEEK_OF_YEAR, -52);
			date1_1 = cal.getTime();

			cal.setTime(date2_1);
			cal.add(Calendar.WEEK_OF_YEAR, -104);
			date2_1 = cal.getTime();

			cal.setTime(date3_1);
			cal.add(Calendar.WEEK_OF_YEAR, -156);
			date3_1 = cal.getTime();

			cal.setTime(date4_1);
			cal.add(Calendar.WEEK_OF_YEAR, -208);
			date4_1 = cal.getTime();

			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			if (df.format(date0_1).equals(df.format(new Date()))) {
				map.put("header", "Returns by Region for today, " + (new SimpleDateFormat("EEEE")).format(date0_1));
			} else {
				map.put("header", "Returns by Region for " + (new SimpleDateFormat("EEEE, MMM, d")).format(date0_1));// ,
																														// yyyy
			}
			map.put("date0", getRegionReturns(onlyDateEnd, null, date0_1));
			map.put("date1", getRegionReturns(onlyDateEnd, null, date1_1));
			map.put("date2", getRegionReturns(onlyDateEnd, null, date2_1));
			map.put("date3", getRegionReturns(onlyDateEnd, null, date3_1));
			map.put("date4", getRegionReturns(onlyDateEnd, null, date4_1));
		}
		return map;
	}

	private Map getRegionSale(boolean onlyEndDate, Date dateStart, Date dateEnd, DataSource dataSource) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String where;
		if (!onlyEndDate) {
			where = "i.orderdate >= ? AND i.orderdate <= ?";
		} else {
			where = "i.orderdate = ?";
		}

		String SQL2 = "SELECT (SUM(i.InvoiceTotal)-SUM(i.Discount)-SUM(i.Tax)) as totalSale,a.region AS region FROM Invoice i, Customer c , Address a WHERE "
				+ "i.customerId = c.customerId AND a.id  = c.customerId AND i.customerId = a.id AND a.type = 'Standard' "
				// + "AND i.ReturnedInvoice = 0"
				+ " AND " + where + " GROUP BY a.region";
		if (!onlyEndDate) {
			List<SalesDetailReport> salesDetails0 = jdbcTemplate.query(SQL2, regionSalesMapper,
					new Object[] { dateStart, dateEnd });
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
			Map map0 = new HashMap();
			map0.put("regionSalesList", salesDetails0);
			map0.put("header", df.format(dateStart) + " to " + df.format(dateEnd));
			return map0;
		} else {
			List<SalesDetailReport> salesDetails0 = jdbcTemplate.query(SQL2, regionSalesMapper,
					new Object[] { dateEnd });
			SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
			Map map0 = new HashMap();
			map0.put("regionSalesList", salesDetails0);
			map0.put("header", df.format(dateEnd));
			return map0;
		}

	}

	public Map getRegionSale(Date dateStart, Date dateEnd) {

		boolean onlyDateEnd;
		if (dateStart != null && dateEnd != null) {
			onlyDateEnd = false;
		} else {
			onlyDateEnd = true;
		}
		dateStart = getZeroTimeDate(dateStart);
		dateEnd = getZeroTimeDate(dateEnd);
		Map report = new HashMap();
		Date date0_0 = null, date0_1 = null, date1_0 = null, date1_1 = null, date2_0 = null, date2_1 = null,
				date3_0 = null, date3_1 = null, date4_0 = null, date4_1 = null;
		if (!onlyDateEnd) {
			date0_0 = dateStart;
			date0_1 = dateEnd;
			date1_0 = (Date) dateStart.clone();
			date1_1 = (Date) dateEnd.clone();
			date2_0 = (Date) dateStart.clone();
			date2_1 = (Date) dateEnd.clone();
			date3_0 = (Date) dateStart.clone();
			date3_1 = (Date) dateEnd.clone();
			date4_0 = (Date) dateStart.clone();
			date4_1 = (Date) dateEnd.clone();
			date1_0.setYear(date1_0.getYear() - 1);
			date1_1.setYear(date1_1.getYear() - 1);
			date2_0.setYear(date2_0.getYear() - 2);
			date2_1.setYear(date2_1.getYear() - 2);
			date3_0.setYear(date3_0.getYear() - 3);
			date3_1.setYear(date3_1.getYear() - 3);
			date4_0.setYear(date4_0.getYear() - 4);
			date4_1.setYear(date4_1.getYear() - 4);

			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			report.put("header", "Sales by Region from " + formatter.format(date0_0) + "-... to "
					+ formatter.format(date0_1) + "-...");

		} else {
			Calendar cal = new GregorianCalendar();
			date0_1 = dateEnd;
			date1_1 = (Date) dateEnd.clone();
			date2_1 = (Date) dateEnd.clone();
			date3_1 = (Date) dateEnd.clone();
			date4_1 = (Date) dateEnd.clone();
			cal.setTime(date1_1);
			cal.add(Calendar.WEEK_OF_YEAR, -52);
			date1_1 = cal.getTime();

			cal.setTime(date2_1);
			cal.add(Calendar.WEEK_OF_YEAR, -104);
			date2_1 = cal.getTime();

			cal.setTime(date3_1);
			cal.add(Calendar.WEEK_OF_YEAR, -156);
			date3_1 = cal.getTime();

			cal.setTime(date4_1);
			cal.add(Calendar.WEEK_OF_YEAR, -208);
			date4_1 = cal.getTime();

			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			if (df.format(date0_1).equals(df.format(new Date()))) {
				report.put("header", "Sales by Region for today, " + (new SimpleDateFormat("EEEE")).format(date0_1));
			} else {
				report.put("header", "Sales by Region for " + (new SimpleDateFormat("EEEE, MMM, d")).format(date0_1));// ,
																														// yyyy
			}

			// map.put("date0", getRegionSale(onlyDateEnd, null, date0_1));
			// map.put("date1", getRegionSale(onlyDateEnd, null, date1_1));
			// map.put("date2", getRegionSale(onlyDateEnd, null, date2_1));
			// map.put("date3", getRegionSale(onlyDateEnd, null, date3_1));
			// map.put("date4", getRegionSale(onlyDateEnd, null, date4_1));
		}
		Map mapCh = new HashMap();
		mapCh.put("date0", getRegionSale(onlyDateEnd, date0_0, date0_1, chdatasourceref));
		mapCh.put("date1", getRegionSale(onlyDateEnd, date1_0, date1_1, chdatasourceref));
		mapCh.put("date2", getRegionSale(onlyDateEnd, date2_0, date2_1, chdatasourceref));
		mapCh.put("date3", getRegionSale(onlyDateEnd, date3_0, date3_1, chdatasourceref));
		mapCh.put("date4", getRegionSale(onlyDateEnd, date4_0, date4_1, chdatasourceref));
		report.put("ch", mapCh);

		Map mapAm = new HashMap();
		// mapAm.put("date1", getRegionSale(onlyDateEnd, date1_0, date1_1,
		// amdatasourceref));
		mapAm.put("date0", getRegionSale(onlyDateEnd, date0_0, date0_1, mpdatasourceref));
		mapAm.put("date1", getRegionSale(onlyDateEnd, date1_0, date1_1, mpdatasourceref));
		mapAm.put("date2", getRegionSale(onlyDateEnd, date2_0, date2_1, mpdatasourceref));
		mapAm.put("date3", getRegionSale(onlyDateEnd, date3_0, date3_1, mpdatasourceref));
		mapAm.put("date4", getRegionSale(onlyDateEnd, date4_0, date4_1, mpdatasourceref));
		report.put("am", mapAm);
		//
		Map mapGr = new HashMap();
		// mapGr.put("date1", getRegionSale(onlyDateEnd, date1_0, date1_1,
		// grdatasourceref));
		mapGr.put("date0", getRegionSale(onlyDateEnd, date0_0, date0_1, grdatasourceref));
		mapGr.put("date1", getRegionSale(onlyDateEnd, date1_0, date1_1, grdatasourceref));
		mapGr.put("date2", getRegionSale(onlyDateEnd, date2_0, date2_1, grdatasourceref));
		mapGr.put("date3", getRegionSale(onlyDateEnd, date3_0, date3_1, grdatasourceref));
		mapGr.put("date4", getRegionSale(onlyDateEnd, date4_0, date4_1, grdatasourceref));
		report.put("gr", mapGr);
		//

		return report;
	}

	private Map getSaleDetails(DataSource dataSource, boolean onlyDateEnd, Date dateStart, Date dateEnd) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<SalesDetailReport> saleDetailsList = new ArrayList<SalesDetailReport>();
		String reportHeader = "";
		String reportFooter = "";
		// SimpleDateFormat dfHeader = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dfFooter = new SimpleDateFormat("EEEE, MMM d, yyyy");

		String where;
		if (!onlyDateEnd) {
			where = "orderdate >= ? AND orderdate <= ?";
		} else {
			where = "orderdate = ? ";
		}
		String SQL1 = "SELECT COUNT(*) AS OrderCount, SUM(InvoiceTotal) AS OrderSales, SUM(Discount) AS OrderDiscount, SUM(Tax) AS OrderTax FROM invoice WHERE "
				+ where;
		String SQL2 = "SELECT SUM(InvoiceTotal) AS OrderReturns FROM invoice WHERE ReturnedInvoice <> 0 AND " + where;

		if (!onlyDateEnd) {

			Date date0_0 = dateStart, date0_1 = dateEnd, date1_0 = (Date) dateStart.clone(),
					date1_1 = (Date) dateEnd.clone(), date2_0 = (Date) dateStart.clone(),
					date2_1 = (Date) dateEnd.clone(), date3_0 = (Date) dateStart.clone(),
					date3_1 = (Date) dateEnd.clone(), date4_0 = (Date) dateStart.clone(),
					date4_1 = (Date) dateEnd.clone();
			date1_0.setYear(date1_0.getYear() - 1);
			date1_1.setYear(date1_1.getYear() - 1);
			date2_0.setYear(date2_0.getYear() - 2);
			date2_1.setYear(date2_1.getYear() - 2);
			date3_0.setYear(date3_0.getYear() - 3);
			date3_1.setYear(date3_1.getYear() - 3);
			date4_0.setYear(date4_0.getYear() - 4);
			date4_1.setYear(date4_1.getYear() - 4);

			SalesDetailReport salesDetails0 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date0_0, date0_1 });
			SalesDetailReport salesDetails1 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date1_0, date1_1 });
			SalesDetailReport salesDetails2 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date2_0, date2_1 });
			SalesDetailReport salesDetails3 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date3_0, date3_1 });
			SalesDetailReport salesDetails4 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date4_0, date4_1 });
			if (date0_0.getYear() == date0_1.getYear()) {
				salesDetails0.setYear((1900 + date0_0.getYear()) + "");
			} else {
				salesDetails0.setYear((1900 + date0_0.getYear()) + " - " + (1900 + date0_1.getYear()));
			}
			if (date1_0.getYear() == date1_1.getYear()) {
				salesDetails1.setYear((1900 + date1_0.getYear()) + "");
			} else {
				salesDetails1.setYear((1900 + date1_0.getYear()) + " - " + (1900 + date1_1.getYear()));
			}
			if (date2_0.getYear() == date2_1.getYear()) {
				salesDetails2.setYear((1900 + date2_0.getYear()) + "");
			} else {
				salesDetails2.setYear((1900 + date2_0.getYear()) + " - " + (1900 + date2_1.getYear()));
			}
			if (date3_0.getYear() == date3_1.getYear()) {
				salesDetails3.setYear((1900 + date3_0.getYear()) + "");
			} else {
				salesDetails3.setYear((1900 + date3_0.getYear()) + " - " + (1900 + date3_1.getYear()));
			}
			if (date4_0.getYear() == date4_1.getYear()) {
				salesDetails4.setYear((1900 + date4_0.getYear()) + "");
			} else {
				salesDetails4.setYear((1900 + date4_0.getYear()) + " - " + (1900 + date4_1.getYear()));
			}
			salesDetails0.setReturned(
					jdbcTemplate.queryForObject(SQL2, new Object[] { date0_0, date0_1 }, BigDecimal.class));
			salesDetails1.setReturned(
					jdbcTemplate.queryForObject(SQL2, new Object[] { date1_0, date1_1 }, BigDecimal.class));
			salesDetails2.setReturned(
					jdbcTemplate.queryForObject(SQL2, new Object[] { date2_0, date2_1 }, BigDecimal.class));
			salesDetails3.setReturned(
					jdbcTemplate.queryForObject(SQL2, new Object[] { date3_0, date3_1 }, BigDecimal.class));
			salesDetails4.setReturned(
					jdbcTemplate.queryForObject(SQL2, new Object[] { date4_0, date4_1 }, BigDecimal.class));
			saleDetailsList.add(salesDetails0);
			saleDetailsList.add(salesDetails1);
			saleDetailsList.add(salesDetails2);
			saleDetailsList.add(salesDetails3);
			saleDetailsList.add(salesDetails4);
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			reportHeader = "Sales matrix from " + formatter.format(date0_0) + "... to " + formatter.format(date0_1)
					+ "...";
			reportFooter = "Previouse 1 Year(s): " + dfFooter.format(date1_0) + " to " + dfFooter.format(date1_1)
					+ "<br/>" + "Previouse 2 Year(s): " + dfFooter.format(date2_0) + " to " + dfFooter.format(date2_1)
					+ "<br/>" + "Previouse 3 Year(s): " + dfFooter.format(date3_0) + " to " + dfFooter.format(date3_1)
					+ "<br/>" + "Previouse 4 Year(s): " + dfFooter.format(date4_0) + " to " + dfFooter.format(date4_1);

		} else {
			Calendar cal = new GregorianCalendar();
			Date date0_1 = dateEnd, date1_1 = (Date) dateEnd.clone(), date2_1 = (Date) dateEnd.clone(),
					date3_1 = (Date) dateEnd.clone(), date4_1 = (Date) dateEnd.clone();
			cal.setTime(date1_1);
			cal.add(Calendar.WEEK_OF_YEAR, -52);
			date1_1 = cal.getTime();

			cal.setTime(date2_1);
			cal.add(Calendar.WEEK_OF_YEAR, -104);
			date2_1 = cal.getTime();

			cal.setTime(date3_1);
			cal.add(Calendar.WEEK_OF_YEAR, -156);
			date3_1 = cal.getTime();

			cal.setTime(date4_1);
			cal.add(Calendar.WEEK_OF_YEAR, -208);
			date4_1 = cal.getTime();

			SalesDetailReport salesDetails0 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date0_1 });
			SalesDetailReport salesDetails1 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date1_1 });
			SalesDetailReport salesDetails2 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date2_1 });
			SalesDetailReport salesDetails3 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date3_1 });
			SalesDetailReport salesDetails4 = jdbcTemplate.queryForObject(SQL1, salesDeatailsMapper,
					new Object[] { date4_1 });

			salesDetails0.setYear((1900 + date0_1.getYear()) + "");
			salesDetails1.setYear((1900 + date1_1.getYear()) + "");
			salesDetails2.setYear((1900 + date2_1.getYear()) + "");
			salesDetails3.setYear((1900 + date3_1.getYear()) + "");
			salesDetails4.setYear((1900 + date4_1.getYear()) + "");

			salesDetails0.setReturned(jdbcTemplate.queryForObject(SQL2, new Object[] { date0_1 }, BigDecimal.class));
			salesDetails1.setReturned(jdbcTemplate.queryForObject(SQL2, new Object[] { date1_1 }, BigDecimal.class));
			salesDetails2.setReturned(jdbcTemplate.queryForObject(SQL2, new Object[] { date2_1 }, BigDecimal.class));
			salesDetails3.setReturned(jdbcTemplate.queryForObject(SQL2, new Object[] { date3_1 }, BigDecimal.class));
			salesDetails4.setReturned(jdbcTemplate.queryForObject(SQL2, new Object[] { date4_1 }, BigDecimal.class));
			saleDetailsList.add(salesDetails0);
			saleDetailsList.add(salesDetails1);
			saleDetailsList.add(salesDetails2);
			saleDetailsList.add(salesDetails3);
			saleDetailsList.add(salesDetails4);

			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			if (df.format(date0_1).equals(df.format(new Date()))) {
				reportHeader = "Sales matrix for today, " + (new SimpleDateFormat("EEEE")).format(date0_1);
			} else {
				reportHeader = "Sales matrix for " + (new SimpleDateFormat("EEEE, MMM, d")).format(date0_1);// ,
																											// yyyy
			}

			// if(dfHeader.format(date0_1).equals(dfHeader.format(new Date()))){
			// reportHeader = "Sales matrix for today, "+(new
			// SimpleDateFormat("EEEE")).format(date0_1);
			// }else{
			// reportHeader = "Sales matrix for " + dfHeader.format(date0_1);
			// }
			reportFooter = "Previouse 1 Year(s): " + dfFooter.format(date1_1) + "<br/>" + "Previouse 2 Year(s): "
					+ dfFooter.format(date2_1) + "<br/>" + "Previouse 3 Year(s): " + dfFooter.format(date3_1) + "<br/>"
					+ "Previouse 4 Year(s): " + dfFooter.format(date4_1) + "<br/>";
		}

		Map map = new HashMap();
		map.put("salesDetailsList", saleDetailsList);
		map.put("reportHeader", reportHeader);
		map.put("reportFooter", reportFooter);
		return map;
	}

	public Map getSaleDetails(Date dateStart, Date dateEnd) {

		boolean onlyDateEnd;
		if (dateStart != null && dateEnd != null) {
			onlyDateEnd = false;
		} else {
			onlyDateEnd = true;
			// dateEnd = new Date();
		}
		dateStart = getZeroTimeDate(dateStart);
		dateEnd = getZeroTimeDate(dateEnd);
		Map map = new HashMap();

		Map chSalesRpt = getSaleDetails(chdatasourceref, onlyDateEnd, dateStart, dateEnd);
		map.put("chSalesDetails", chSalesRpt.get("salesDetailsList"));
		map.put("reportHeader", chSalesRpt.get("reportHeader"));
		map.put("reportFooter", chSalesRpt.get("reportFooter"));

		Map grdSalesRpt = getSaleDetails(grdatasourceref, onlyDateEnd, dateStart, dateEnd);
		map.put("grSalesDetails", grdSalesRpt.get("salesDetailsList"));

		Map mpdSalesRpt = getSaleDetails(mpdatasourceref, onlyDateEnd, dateStart, dateEnd);
		map.put("mpSalesDetails", mpdSalesRpt.get("salesDetailsList"));

		return map;

	}

	private List<SalesDetailSalesPersonReport> getSaleDetailsSalePerson(DataSource dataSource, Date dateStart,
			Date dateEnd) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String SQL3 = "SELECT SalesPerson, SUM((InvoiceTotal) - (Discount) - (Tax)) AS ReturnSum, InvoiceTotal, ReturnedInvoice, Count(*) AS ReturnInvoiceCount "
				+ "FROM invoice "
				+ "WHERE InvoiceNumber IN (SELECT ReturnedInvoice FROM invoice WHERE ReturnedInvoice!=0 AND orderdate >= ? AND orderdate <= ?)" // OrderDate
																																				// =
																																				// CURRENT_DATE()
				+ " GROUP BY SalesPerson";
		List<SalesDetailSalesPersonReport> salesDetailsSp = jdbcTemplate.query(SQL3, salesPersonMapper,
				new Object[] { dateStart, dateEnd });
		return salesDetailsSp;
	}

	public Map getSaleDetailsSalePerson(Date dateStart, Date dateEnd) {

		// List<SalesDetailSalesPersonReport> salesDetailsSalesPerson = new
		// ArrayList();
		Map map = new HashMap();

		List<SalesDetailSalesPersonReport> chSaleDetailsSp = getSaleDetailsSalePerson(chdatasourceref, dateStart,
				dateEnd);
		map.put("ch", chSaleDetailsSp);
		// salesDetailsSalesPerson.addAll(chSaleDetailsSp);

		List<SalesDetailSalesPersonReport> grSaleDetailsSp = getSaleDetailsSalePerson(grdatasourceref, dateStart,
				dateEnd);
		map.put("gr", grSaleDetailsSp);
		// salesDetailsSalesPerson = joinList(salesDetailsSalesPerson,
		// grSaleDetailsSp);

		List<SalesDetailSalesPersonReport> mpSaleDetailsSp = getSaleDetailsSalePerson(mpdatasourceref, dateStart,
				dateEnd);
		map.put("mp", mpSaleDetailsSp);
		// salesDetailsSalesPerson = joinList(salesDetailsSalesPerson,
		// amSaleDetailsSp);

		// map.put("spSalesDetails", salesDetailsSalesPerson);
		SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM d, yyyy");
		map.put("reportHeader",
				"Sales mean time between invoices from " + df.format(dateStart) + " to " + df.format(dateEnd));
		return map;
	}

	public Map<String, Object> getSalesRpt(String dateStartStr, String dateEndStr, String salesPerson) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(localDataSource);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("proc_dailySalesReport");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("start_date", dateStartStr);
		inParamMap.put("end_Date", dateEndStr);
		inParamMap.put("psalesPerson", salesPerson);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		// LOGGER.info("==============="+simpleJdbcCallResult);
		return simpleJdbcCallResult;// (ArrayList<Map<String,
									// Object>>)simpleJdbcCallResult.get("#result-set-1");
	}

	private Date getZeroTimeDate(Date date) {

		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		return date;
	}

	private List<SalesDetailSalesPersonReport> joinList(List<SalesDetailSalesPersonReport> parent,
			List<SalesDetailSalesPersonReport> child) {

		parent.addAll(child);
		return parent;
		// for(SalesDetailSalesPersonReport sp:child){
		// boolean found=false;
		// for(SalesDetailSalesPersonReport sp2:parent){
		// if(sp2.getSalesPerson().equals(sp.getSalesPerson())){
		// sp2.setReturnInvCount(sp2.getReturnInvCount()+sp.getReturnInvCount());
		// sp2.setReturns(sp2.getReturns().add(sp.getReturns()));
		// sp2.setSale(sp2.getSale().add(sp.getSale()));
		// found=true;
		// break;
		// }
		// }
		// if(!found){
		// parent.add(sp);
		// }
		// }
		// return parent;
	}
}
