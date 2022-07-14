package com.bvas.insight.jdbc;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.Query;
// import org.magicwerk.brownies.collections.GapList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bvas.insight.data.InvCategoryBySalesAnalysis;
import com.bvas.insight.data.StockCheck;
import com.bvas.insight.data.StockCheckDetails;
import com.bvas.insight.data.StockData;
import com.bvas.insight.entity.PartsMonthlySales;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.utilities.InventoryTransferUtils;

public class MpStocksDAOImpl implements BaseDAOImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(MpStocksDAOImpl.class);

	private DataSource mpdataSource;

	public List<StockCheck> getAllStock(String partno) throws ConnectException {

		List<StockCheck> stocklist = new ArrayList<StockCheck>();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
			String SQL = "SELECT  p.PartNo,p.InterchangeNo, p.manufacturername, p.makemodelname, p.PartDescription, p.Location, p.returncount, p.UnitsInStock, p.UnitsOnOrder,p.reorderlevel,p.safetyquantity,p.ordertype,p.pricelock FROM parts p  WHERE ( p.PartNo = ?  or interchangeno = ? )  ";

			stocklist = jdbcTemplate.query(SQL, new StockCheckMapper(), new Object[] { partno, partno });

			return stocklist;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException: getAllStock##" + e.getMessage().toString());

			StockCheck stock = new StockCheck();

			stock.setBranch("MP");
			stock.setPartno(partno);
			stock.setInterchangepartno("NA");
			stock.setManufacturername("NA");
			stock.setMakemodelname("NA");
			stock.setPartdescription("NA");
			stock.setUnitsinstock(0);
			stock.setUnitsonorder(0);
			stock.setLocation("NA");
			stock.setOrdertype("NA");
			stock.setPricelock("N");
			stocklist.add(stock);

			return stocklist;
		}
	}

	public List<InvCategoryBySalesAnalysis> getInventoryBySubcategory(String analyticsfromdate, String analyticstodate,
			String subcategorycode, String dataSource) {

		List<InvCategoryBySalesAnalysis> categorycomparisonList = new ArrayList<InvCategoryBySalesAnalysis>();
		Map<String, InvCategoryBySalesAnalysis> categorycomparisonMap = new HashMap<String, InvCategoryBySalesAnalysis>();

		Set<String> dupelist = new HashSet<String>();
		String sql = "";
		if (subcategorycode.equals("ALL")) {
			sql = InventoryTransferUtils.SALESBYCATEGORY_ALL;
		} else {
			sql = InventoryTransferUtils.SALESBYCATEGORY;
		}
		// LOGGER.info("subcategorycode = " +subcategorycode);
		// LOGGER.info("sql1 = " +
		// InventoryTransferUtils.SALESBYCATEGORY);
		try {
			JdbcTemplate jdbcTemplate = null;
			// LOGGER.info(" Datasource =" +chdataSource);
			switch (dataSource) {
			case "MP":
				jdbcTemplate = new JdbcTemplate(mpdataSource);
				break;
			}

			List<InvCategoryBySalesAnalysis> categoryBySalesAnalysis = null;
			if (subcategorycode.equals("ALL")) {
				categoryBySalesAnalysis = jdbcTemplate.query(sql, new InventoryDataMapper(),
						new Object[] { analyticsfromdate, analyticstodate });

			} else {
				categoryBySalesAnalysis = jdbcTemplate.query(sql, new InventoryDataMapper(),
						new Object[] { subcategorycode, analyticsfromdate, analyticstodate });

			}
			if (categoryBySalesAnalysis.size() <= 0) {
				List<InvCategoryBySalesAnalysis> categoryBySalesAnalysisblank = new ArrayList<InvCategoryBySalesAnalysis>();
				InvCategoryBySalesAnalysis categoryBySalesAnalysisblankObject = new InvCategoryBySalesAnalysis();

				categoryBySalesAnalysisblankObject.setOrdertype("");
				categoryBySalesAnalysisblankObject.setPartno("");
				categoryBySalesAnalysisblank.add(categoryBySalesAnalysisblankObject);
				return categoryBySalesAnalysisblank;
			}

			Collections.sort(categoryBySalesAnalysis, new Comparator<InvCategoryBySalesAnalysis>() {
				@Override
				public int compare(InvCategoryBySalesAnalysis s1, InvCategoryBySalesAnalysis s2) {
					int comparesold = s2.getTotalsold().intValue();
					return comparesold - s1.totalsold.intValue();
				}
			});
			// categoryBySalesAnalysis.sort(new
			// Comparator<InvCategoryBySalesAnalysis>(){
			// public int compare(InvCategoryBySalesAnalysis
			// s1,InvCategoryBySalesAnalysis s2){
			// int comparesold = ((InvCategoryBySalesAnalysis)
			// s2).getTotalsold()
			// .intValue();
			// return comparesold - s1.totalsold.intValue();
			// }});

			for (InvCategoryBySalesAnalysis categorycomparison : categoryBySalesAnalysis) {
				if (dupelist.add(categorycomparison.getPartno())) {
					categorycomparisonMap.put(categorycomparison.getPartno(), categorycomparison);
				}
			}

			String sqlinterchange = "";
			Query queryinterchange = null;

			String sqlMainNo = "";
			Query queryMainNo = null;

			sqlinterchange = InventoryTransferUtils.SALESBYCATEGORY_INTERCHANGE;
			List<InvCategoryBySalesAnalysis> interchangesoldlist = null;
			if (subcategorycode.equals("ALL")) {
				interchangesoldlist = jdbcTemplate.query(sql, new Object[] { analyticsfromdate, analyticstodate },
						new InventoryDataMapper());
			} else {
				interchangesoldlist = jdbcTemplate.query(sql,
						new Object[] { subcategorycode, analyticsfromdate, analyticstodate },
						new InventoryDataMapper());
			}
			for (InvCategoryBySalesAnalysis interchangehelper : interchangesoldlist) {
				if (dupelist.add(interchangehelper.getPartno())) {
					String interchangepartno = interchangehelper.getPartno().trim();
					sqlMainNo = InventoryTransferUtils.SALESBYCATEGORY_MAINNO;
					InvCategoryBySalesAnalysis interchnagemainlist = jdbcTemplate.queryForObject(sqlMainNo,
							new Object[] { interchangepartno }, new InventoryDataMapper());

					interchnagemainlist.setTotalsold(interchangehelper.getTotalsold());

					categorycomparisonMap.put(interchangepartno, interchnagemainlist);
				} else {
					Integer interchangesold = interchangehelper.getTotalsold().intValue();
					String key = interchangehelper.getPartno();
					Integer addtotalssold = categorycomparisonMap.get(key).getTotalsold();
					categorycomparisonMap.get(key).setTotalsold(addtotalssold);

				}
			}

			categorycomparisonList = new ArrayList<InvCategoryBySalesAnalysis>(categorycomparisonMap.values());

			List<PartsMonthlySales> monthlysales = new ArrayList<PartsMonthlySales>();
			String hqlmonthlysalesQuery = "From PartsMonthlySales partsmonthlysales  where partsmonthlysales.partno=:partno ORDER BY  partsmonthlysales.yearmonth DESC";
			Query queryMonthlySales = null;
			//// queryMonthlySales = session.createQuery(hqlmonthlysalesQuery);
			////
			//// LOGGER.info("total items :" + categorycomparisonList.size());
			////
			//// for (CategoryBySalesAnalysis categorybysalesanalysis :
			//// categorycomparisonList) {
			//// // LOGGER.info("now processing -> " + i++ + " of " +
			//// // categorycomparisonList.size());
			//// queryMonthlySales.setParameter("partno",
			//// categorybysalesanalysis.getPartno().trim());
			//// queryMonthlySales.setMaxResults(26);
			//// monthlysales = queryMonthlySales.list();
			//// categorybysalesanalysis.setPartsmonthlysaleslist(monthlysales);
			////
			//// }
			//
			//
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException: getStockDetails##" + e.getMessage().toString());
			List<StockCheckDetails> stockdetailslist = new ArrayList<StockCheckDetails>();

		}

		// Collections.sort(categorycomparisonList);
		return categorycomparisonList;

	}

	@Override
	public String getLocation(String partno) {

		String location2 = "";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
		String hqlQuery1 = "SELECT location FROM parts WHERE partno =?";

		try {

			location2 = jdbcTemplate.queryForObject(hqlQuery1, new Object[] { partno }, String.class);

			if ((location2.equalsIgnoreCase("")) || (location2 == null)) {
				location2 = "#";
				return location2;
			} else {

				return location2;
			}
		} catch (EmptyResultDataAccessException e) {
			location2 = "#";
			return location2;
		}

	}

	@Override
	public int getSalesOfPartForGivenBranchDuration(String partno, String fromdate, String todate) {

		// LOGGER.info("partno:" + partno);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);

		Integer count1 = 0;
		Integer count2 = 0;

		String hqlQuery1 = "SELECT COUNT(1) AS count FROM PARTS P, invoicedetails invdtls, INVOICE i  "
				+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND i.ReturnedInvoice = 0 "
				+ " AND p.PARTNO =? AND i.OrderDate BETWEEN ? AND ?";

		count1 = jdbcTemplate.queryForObject(hqlQuery1, new Object[] { partno, fromdate, todate }, Integer.class);

		if ((count1 <= 0) || (count1 == null)) {
			count1 = 0;
		}

		String hqlQuery2 = "SELECT COUNT(1) AS count FROM PARTS P, invoicedetails invdtls, INVOICE i  "
				+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND i.ReturnedInvoice = 0 "
				+ " AND p.INTERCHANGENO =? AND i.OrderDate BETWEEN ? AND ?";

		count2 = jdbcTemplate.queryForObject(hqlQuery2, new Object[] { partno, fromdate, todate }, Integer.class);

		if ((count2 <= 0) || (count2 == null)) {
			count2 = 0;
		}

		return count1 + count2;

	}

	public BigDecimal getSalesQuantity(String analyticsfromdate, String analyticstodate, String partno) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
			String SQL = "SELECT COALESCE(SUM(INVDTLS.Quantity), 0) AS cnt  FROM invoicedetails invdtls, INVOICE i WHERE i.InvoiceNumber = invdtls.InvoiceNumber "
					+ " AND invdtls.PartNumber = ? AND i.OrderDate BETWEEN ? AND ? AND I.ReturnedInvoice = 0 ORDER BY cnt DESC; ";

			BigDecimal value = jdbcTemplate.queryForObject(SQL,
					new Object[] { partno, analyticsfromdate, analyticstodate }, BigDecimal.class);

			return value;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException: getSalesQuantity##" + e.getMessage().toString());
			return BigDecimal.ZERO;
		}
	}

	public StockCheck getStock(String partno) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
			String SQL = "SELECT p.PartNo,p.InterchangeNo, p.manufacturername, p.makemodelname, p.PartDescription, p.Location, p.UnitsInStock, p.UnitsOnOrder,p.returncount, p.reorderlevel,p.safetyquantity,p.ordertype,p.pricelock FROM parts p  WHERE p.PartNo = ?   ";
			StockCheck stock = jdbcTemplate.queryForObject(SQL, new Object[] { partno }, new StockCheckMapper());

			stock.setBranch("MP");

			return stock;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException: getStock##" + e.getMessage().toString());
			StockCheck stock = new StockCheck();

			stock.setBranch("MP");
			stock.setPartno(partno);
			stock.setInterchangepartno("NA");
			stock.setManufacturername("NA");
			stock.setMakemodelname("NA");
			stock.setPartdescription("NA");
			stock.setUnitsinstock(0);
			stock.setUnitsonorder(0);
			stock.setLocation("NA");
			stock.setOrdertype("NA");

			return stock;
		}
	}

	public StockData getStockData(String partno) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
			String SQL = "SELECT p.PartNo, p.UnitsInStock, p.UnitsOnOrder,p.reorderlevel,p.safetyquantity,p.returncount FROM parts p  WHERE p.PartNo = ?  ";
			StockData stockdata = jdbcTemplate.queryForObject(SQL, new Object[] { partno }, new StockDataMapper());

			return stockdata;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException: getStockData##" + partno + e.getMessage().toString());
			StockData stock = new StockData();

			stock.setPartno(partno);
			stock.setUnitsinstock(0);
			stock.setUnitsonorder(0);
			stock.setReorderlevel(0);
			stock.setSafetyquantity(0);
			stock.setReturncount(0);

			return stock;
		}
	}

	public Map<String, StockData> getStockDataList() {

		Map<String, StockData> stockDataMap = new HashMap<String, StockData>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
		String STOCK_SQL = "SELECT * FROM  parts p WHERE  P.InterchangeNo = ''  AND p.ordertype <> 'S' ";
		List<StockData> stocklist = jdbcTemplate.query(STOCK_SQL, new StockDataMapper());

		for (StockData sd : stocklist) {
			stockDataMap.put(sd.getPartno(), sd);

		}

		return stockDataMap;

	}

	public List<StockCheckDetails> getStockDetails(String partno) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
			String SQL = "SELECT V.CompanyName, vi.vendorpartno FROM vendoritems vi, vendors v"
					+ " WHERE vi.SupplierID = v.SupplierID" + " AND vi.PartNo = ?" + " AND vi.vendorpartno <> '' "
					+ " ORDER BY V.CompanyName";
			List<StockCheckDetails> stockdetailslist = jdbcTemplate.query(SQL, new StockCheckDetailsMapper(),
					new Object[] { partno });

			if (stockdetailslist.size() <= 0) {
				List<StockCheckDetails> stockdetailslistblank = new ArrayList<StockCheckDetails>();
				StockCheckDetails stockdetails = new StockCheckDetails();

				stockdetails.setCompanyname("");
				stockdetails.setVendorpartno("");
				stockdetailslistblank.add(stockdetails);

				return stockdetailslistblank;
			} else {
				return stockdetailslist;
			}
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException: getStockDetails##" + e.getMessage().toString());
			List<StockCheckDetails> stockdetailslist = new ArrayList<StockCheckDetails>();
			StockCheckDetails stockdetails = new StockCheckDetails();

			stockdetails.setCompanyname("");
			stockdetails.setVendorpartno("");
			stockdetailslist.add(stockdetails);

			return stockdetailslist;
		}
	}

	@Override
	public void insertVendorOrderedItemsJDBC(VendorOrderedItems voi) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
		String insertSQL = "insert into vendorordereditems (OrderNo, PartNo, VendorPartNo, Quantity, NoOrder, Price) values(?,?,?,?,?,?)";

		jdbcTemplate.update(insertSQL, new Object[] { voi.getOrderno(), voi.getPartno(), voi.getVendorpartno(),
				voi.getQuantity(), voi.getNoorder(), voi.getPrice() });

	}

	@Override
	public void insertVendorOrderJDBC(VendorOrder vendorOrder) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mpdataSource);
		String insertSQL = "insert into vendororder (OrderNo, SupplierID, OrderDate, deliveredDate,"
				+ " ArrivedDate, ContainerNo, SupplInvNo, OrderStatus, TotalItems, OrderTotal, Discount,"
				+ " StickerCharges, OverheadAmountsTotal, UnitsOrderDoneDate, PricesDoneDate, InventoryDoneDate, "
				+ " PaymentTerms, PaymentDate, UpdatedInventory, UpdatedPrices, IsFinal, EstimatedArrivalDate, UpdateUnitsOnOrder) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(insertSQL,
				new Object[] { vendorOrder.getOrderno(), vendorOrder.getSupplierid(), vendorOrder.getOrderdate(),
						vendorOrder.getDelivereddate(), vendorOrder.getArriveddate(), vendorOrder.getContainerno(),
						vendorOrder.getSupplinvno(), vendorOrder.getOrderstatus(), vendorOrder.getTotalitems(),
						vendorOrder.getOrdertotal(), vendorOrder.getDiscount(), vendorOrder.getStickercharges(),
						vendorOrder.getOverheadamountstotal(), vendorOrder.getUnitsorderdonedate(),
						vendorOrder.getPricesdonedate(), vendorOrder.getInventorydonedate(),
						vendorOrder.getPaymentterms(), vendorOrder.getPaymentdate(), vendorOrder.getUpdatedinventory(),
						vendorOrder.getUpdatedprices(), vendorOrder.getIsfinal(), vendorOrder.getEstimatedarrivaldate(),
						"N" });

	}

	public void setMpdataSource(DataSource mpdataSource) {

		this.mpdataSource = mpdataSource;
	}

}
