package com.bvas.insight.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.CategoryBySalesAnalysis;
import com.bvas.insight.data.CreateVendorOrder;
import com.bvas.insight.data.PartList;
import com.bvas.insight.data.PartnoQuantity;
import com.bvas.insight.data.PreOrderParts;
import com.bvas.insight.data.PreOrderPartsHelper;
import com.bvas.insight.data.ProcurementParts;
import com.bvas.insight.data.SalesAnalysisHistoryCategory;
import com.bvas.insight.data.ScanOrderDetails;
import com.bvas.insight.data.StockData;
import com.bvas.insight.data.TransferParts;
import com.bvas.insight.data.VendorOrderedItemsDetails;
import com.bvas.insight.data.YearSubCategoryReport;
import com.bvas.insight.entity.OrderCounter;
import com.bvas.insight.entity.PartsMonthlySales;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.jdbc.ChStocksDAOImpl;
import com.bvas.insight.jdbc.GrStocksDAOImpl;
import com.bvas.insight.jdbc.MpStocksDAOImpl;
import com.bvas.insight.jdbc.VendorOrderDaoImpl;
import com.bvas.insight.utilities.AnalyticsUtils;
import com.bvas.insight.utilities.InsightUtils;
import com.bvas.insight.utilities.OrdersUtils;

@Repository
@SuppressWarnings("unchecked")
public class OrdersService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersService.class);

	@Autowired
	@Qualifier("chpartsdao")
	public ChStocksDAOImpl chpartsdao;

	@Autowired
	@Qualifier("grpartsdao")
	public GrStocksDAOImpl grpartsdao;

	@Autowired
	@Qualifier("mppartsdao")
	public MpStocksDAOImpl mppartsdao;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	VendorOrderDaoImpl vendorOrderDao;

	@Transactional
	public VendorOrder createNewOrder(String orderno, Integer supplierid) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Date javasqldate = new java.sql.Date(calendar.getTime().getTime());
		String hqlQuery = "From VendorOrder vendororder  where vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", Integer.parseInt(orderno));
		List<VendorOrder> vendors = query.list();
		if (vendors.size() > 0) {
			session.flush();
			session.clear();
			return vendors.get(0);
		} else {
			VendorOrder vendororder = new VendorOrder();
			vendororder.setOrderno(Integer.parseInt(orderno));
			vendororder.setSupplierid(supplierid);
			vendororder.setOrderdate(javasqldate);
			vendororder.setDelivereddate(javasqldate);
			vendororder.setContainerno("");
			vendororder.setSupplinvno("");
			vendororder.setOrderstatus("New");
			vendororder.setTotalitems(0);
			vendororder.setOrdertotal(new BigDecimal("0.00"));
			vendororder.setDiscount(new BigDecimal("0.00"));
			vendororder.setStickercharges(new BigDecimal("0.00"));
			vendororder.setOverheadamountstotal(new BigDecimal("0.00"));
			vendororder.setPaymentterms("");
			vendororder.setUpdatedinventory("N");
			vendororder.setUpdatedprices("N");
			vendororder.setIsfinal("N");
			session.save(vendororder);
			session.flush(); // all objects are comitted to the chache
			session.clear(); // update to database
			return vendororder;

		}

	}

	@Transactional
	public String createPartListForBranches(List<PartnoQuantity> localpartslist, String branchcode, String branch,
			String targetbranch, Integer ordernoseries) {

		// get previous order
		List<String> previousorderlist = getVendorOrderedItemsPartsOnly(ordernoseries - 1);

		// LOGGER.info("branchcode" + branchcode);
		// LOGGER.info("branch" + branch);
		// LOGGER.info("targetbranch" + targetbranch);
		Session session = null;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date javasqldate = new java.sql.Date(calendar.getTime().getTime());

		Integer counter = 0;

		StringBuffer bufferProcess = new StringBuffer();
		StringBuffer bufferError = new StringBuffer();

		session = sessionFactory.getCurrentSession();

		OrderCounter ordercounter = new OrderCounter();
		ordercounter.setBranch(targetbranch);
		ordercounter.setOrderno(ordernoseries);
		ordercounter.setOrdertype("B");
		session.saveOrUpdate(ordercounter);

		// OrderNo, SupplierId, OrderDate, DeliveredDate, OrderStatus,
		// OrderTotal, UpdatedInventory, UpdatedPrices

		VendorOrder vendororder = new VendorOrder();
		vendororder.setOrderno(ordernoseries);
		vendororder.setSupplierid(Integer.parseInt(branchcode));
		vendororder.setOrderdate(javasqldate);
		vendororder.setDelivereddate(javasqldate);
		// vendororder.setArriveddate();
		vendororder.setContainerno("");
		vendororder.setSupplinvno("");
		vendororder.setOrderstatus("New");
		vendororder.setTotalitems(0);
		vendororder.setOrdertotal(new BigDecimal("0.00"));
		vendororder.setDiscount(new BigDecimal("0.00"));
		vendororder.setStickercharges(new BigDecimal("0.00"));
		vendororder.setOverheadamountstotal(new BigDecimal("0.00"));
		// vendororder.setUnitsorderdonedate(null);
		// vendororder.setPricesdonedate(null);
		// vendororder.setInventorydonedate(null);
		vendororder.setPaymentterms("");
		// vendororder.setPaymentdate(null);
		vendororder.setUpdatedinventory("N");
		vendororder.setUpdatedprices("N");
		vendororder.setIsfinal("N");
		// vendororder.setEstimatedarrivaldate(null);
		vendororder.setUpdatedinventory("N");
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(vendororder);

		bufferProcess.append('\n');
		bufferProcess.append(targetbranch + " Order Created:" + ordernoseries);
		bufferProcess.append('\n');

		for (PartnoQuantity partquantity : localpartslist) {
			if (previousorderlist != null) {
				if (!previousorderlist.contains(partquantity.getPartno())) {
					VendorOrderedItems vendorordereditems = new VendorOrderedItems();
					vendorordereditems.setOrderno(ordernoseries);
					vendorordereditems.setPartno(partquantity.getPartno());
					vendorordereditems.setNoorder(counter);
					vendorordereditems.setQuantity(partquantity.getQuantity());
					vendorordereditems.setPrice(new BigDecimal("0.00"));
					vendorordereditems.setVendorpartno("");
					session = sessionFactory.getCurrentSession();
					session.save(vendorordereditems);
					counter++;
				}
			} else {
				VendorOrderedItems vendorordereditems = new VendorOrderedItems();
				vendorordereditems.setOrderno(ordernoseries);
				vendorordereditems.setPartno(partquantity.getPartno());
				vendorordereditems.setNoorder(counter);
				vendorordereditems.setQuantity(partquantity.getQuantity());
				vendorordereditems.setPrice(new BigDecimal("0.00"));
				vendorordereditems.setVendorpartno("");
				session = sessionFactory.getCurrentSession();
				session.save(vendorordereditems);
				counter++;
			}
		}

		session.flush();
		session.clear();
		return bufferProcess.toString() + bufferError.toString();

	}

	public String createVedorOrder(CreateVendorOrder vendorOrder) {
		try {
			// any checks/constraints
			if (vendorOrder.getFile() == null || vendorOrder.getFile().isEmpty()) {
				return "Please upload input file.";
			} else if (vendorOrder.getByWhosNoToCreateOrder() == null
					|| vendorOrder.getByWhosNoToCreateOrder().trim().equals("")) {
				return "Please select By whos No to create order.";
			} else if (vendorOrder.getDoesInputFileHasQty() == null
					|| vendorOrder.getDoesInputFileHasQty().trim().equals("")) {
				return "Please select whether input file has quantity.";
			} else if (vendorOrder.getLocation() == null || vendorOrder.getLocation().trim().equals("")) {
				return "Please select location";
			} else if (vendorOrder.getOrderNo() == null || vendorOrder.getOrderNo().trim().equals("")) {
				return "Please enter order no.";
			} else if (vendorOrder.getSupplierId() == null || vendorOrder.getSupplierId().trim().equals("")) {
				return "Please enter supplier Id.";
			}
			return vendorOrderDao.createVedorOrder(vendorOrder);
		} catch (IOException ex) {
			ex.printStackTrace();
			return "An internal error occur while processing the request.";
		}
	}

	@Transactional
	public void deleteVendorOrderedItems(Integer orderno) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete VendorOrderedItems where orderno = :orderno");
		query.setParameter("orderno", orderno);
		int result = query.executeUpdate();
		LOGGER.info("Info:" + "rows deleted" + result);
		session.flush();
		session.clear();

	}

	@Transactional
	public List<VendorOrderedItemsDetails> getAddToOrderDetails(String fromdate, String todate) {

		String hqlQuery = "SELECT  mk.makemodelname AS makemodelname, mk.manufacturername AS manufacturername, p.partno AS partno,  p.year AS year, p.capa AS capa,  p.partdescription   AS partdescription,p.unitsinstock AS unitsinstock,"
				+ " p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,p.returncount  AS returncount,p.safetyquantity  AS safetyquantity,sum(vo.Price)  AS price , sum(vo.quantity)  AS qty, sum(vo.quantity * vo.price)   AS totalprice, p.ordertype as ordertype"
				+ " FROM addtoorder ao,Parts p, VendorOrderedItems vo, makemodel mk"
				+ " WHERE p.partno=ao.partno and vo.partno = ao.partno AND p.makemodelcode = mk.makemodelcode"
				+ " AND ao.date >=:fromdate and ao.date <=:todate order by p.partno";
		// LOGGER.info(hqlQuery + "====" + fromdate + "=====" + todate);
		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("fromdate", fromdate)
				.setParameter("todate", todate)).addScalar("partno").addScalar("partdescription").addScalar("year")
						.addScalar("capa").addScalar("makemodelname").addScalar("manufacturername")
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("safetyquantity").addScalar("price").addScalar("qty").addScalar("totalprice")
						.addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(VendorOrderedItemsDetails.class));
		List<VendorOrderedItemsDetails> results = query.list();
		System.err.print("List length:" + results);
		session.flush();
		session.clear();
		return results;

	}

	@Transactional
	public List<CategoryBySalesAnalysis> getDeadInventoryBySubcategory(String analyticsfromdate, String analyticstodate,
			String subcategorycode) {

		List<CategoryBySalesAnalysis> categorycomparisonMap = new ArrayList<CategoryBySalesAnalysis>();

		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;

		String sqlinterchange = "";
		Query queryinterchange = null;
		sql = OrdersUtils.SALESBYDEADINVENTORY;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategorycode))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
						.addScalar("buyingprice").addScalar("percent").addScalar("ordertype").addScalar("yearfrom")
						.addScalar("yearto")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> subcategorysaleslist = query.list();
		Collections.sort(subcategorysaleslist);
		for (CategoryBySalesAnalysis categorycomparison : subcategorysaleslist) {

			// get the interchange stocks
			sqlinterchange = OrdersUtils.SALESBYDEADINVENTORY_ALL_INTERCHANGE;
			queryinterchange = (session.createSQLQuery(sqlinterchange).setParameter("orderfrom", analyticsfromdate)
					.setParameter("orderto", analyticstodate).setParameter("partno", categorycomparison.getPartno()));
			List<?> moresold = queryinterchange.list();
			if (moresold != null) {
				if (moresold.size() > 0) {

				} else {
					categorycomparisonMap.add(categorycomparison);
				}
			} else {
				categorycomparisonMap.add(categorycomparison);
			}
		}
		session.flush();
		session.clear();
		Collections.sort(categorycomparisonMap);
		return categorycomparisonMap;
	}

	@Transactional
	public List<CategoryBySalesAnalysis> getDeadInventoryBySubcategoryAll(String analyticsfromdate,
			String analyticstodate, String string) {

		List<CategoryBySalesAnalysis> categorycomparisonMap = new ArrayList<CategoryBySalesAnalysis>();

		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;
		sql = OrdersUtils.SALESBYDEADINVENTORY_ALL;
		String sqlinterchange = "";
		Query queryinterchange = null;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate)).addScalar("partno").addScalar("partdescription")
						.addScalar("manufacturername").addScalar("makemodelname").addScalar("totalsold")
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.addScalar("yearfrom").addScalar("yearto")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> subcategorysaleslist = query.list();
		Collections.sort(subcategorysaleslist);
		for (CategoryBySalesAnalysis categorycomparison : subcategorysaleslist) {

			// get the interchange stocks
			sqlinterchange = OrdersUtils.SALESBYDEADINVENTORY_ALL_INTERCHANGE;
			queryinterchange = (session.createSQLQuery(sqlinterchange).setParameter("orderfrom", analyticsfromdate)
					.setParameter("orderto", analyticstodate).setParameter("partno", categorycomparison.getPartno()));
			List<?> moresold = queryinterchange.list();
			if (moresold != null) {
				if (moresold.size() > 0) {

				} else {
					categorycomparisonMap.add(categorycomparison);
				}
			} else {
				categorycomparisonMap.add(categorycomparison);
			}
		}
		session.flush();
		session.clear();
		Collections.sort(categorycomparisonMap);
		return categorycomparisonMap;
	}

	@Transactional
	public List<ProcurementParts> getHistoricSales(List<ProcurementParts> procurementpartslist,
			String analyticsfromdate) throws ParseException {

		List<ProcurementParts> newprocurementpartslist = new LinkedList<ProcurementParts>();

		Session session = sessionFactory.getCurrentSession();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date date = formatter.parse(analyticsfromdate.trim());
		// Date date = formatter.parse("2016-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String currentMonthStr = "";
		Integer currentMonth = (cal.get(Calendar.MONTH) + 1);
		if (currentMonth.toString().length() <= 1) {
			currentMonthStr = "0" + currentMonth;
		} else {
			currentMonthStr = currentMonth.toString();
		}

		cal.add(Calendar.YEAR, -1);
		int oneyearback = cal.get(Calendar.YEAR);
		cal.add(Calendar.YEAR, -1);
		int twoyearback = cal.get(Calendar.YEAR);
		cal.add(Calendar.YEAR, -1);
		int threeyearback = cal.get(Calendar.YEAR);

		String oneyearsearch = oneyearback + currentMonthStr;
		String twoyearsearch = twoyearback + currentMonthStr;
		String threeyearsearch = threeyearback + currentMonthStr;

		String searchSQL = "SELECT IFNULL(partsmonthlysales.salescount,0) as count FROM PartsMonthlySales partsmonthlysales WHERE partsmonthlysales.partno=:partno and partsmonthlysales.yearmonth=:yearmonth  ";

		Query query = null;

		for (ProcurementParts procurement : procurementpartslist) {

			query = session.createSQLQuery(searchSQL);

			// step 1
			query.setParameter("partno", procurement.getPartno());
			query.setParameter("yearmonth", oneyearsearch);
			query.setMaxResults(1);
			List<BigInteger> result1 = query.list();
			if ((result1 != null)) {
				if (result1.size() > 0) {
					procurement.setSales1yearback(result1.get(0));
				}
			}

			// step 2
			query.setParameter("partno", procurement.getPartno());
			query.setParameter("yearmonth", twoyearsearch);
			query.setMaxResults(1);
			List<BigInteger> result2 = query.list();
			if ((result2 != null)) {
				if (result2.size() > 0) {
					procurement.setSales2yearback(result2.get(0));
				}
			}

			// step 3
			query.setParameter("partno", procurement.getPartno());
			query.setParameter("yearmonth", threeyearsearch);
			query.setMaxResults(1);
			List<BigInteger> result3 = query.list();
			if ((result3 != null)) {
				if (result3.size() > 0) {
					procurement.setSales3yearback(result3.get(0));
				}
			}

			newprocurementpartslist.add(procurement);

		}
		session.flush();
		session.clear();

		return newprocurementpartslist;

	}

	@Transactional
	public Integer getMaxPartListOrder(String branch) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "SELECT MAX(ordercounter.orderno) FROM OrderCounter ordercounter  WHERE ordercounter.ordertype = 'B'  AND ordercounter.branch =:branch";
		Query query = session.createQuery(hSql);
		query.setParameter("branch", branch);

		List<Integer> listofseries = query.list();
		Integer maxorder = listofseries.get(0);
		session.flush();
		session.clear();
		return maxorder;
	}

	@Transactional
	public List<PartList> getPartlist(Integer orderno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = "SELECT v.noorder AS noorder, p.partno AS partno, m.makemodelname  AS makemodelname, p.year  AS year, p.partdescription  AS partdescription, "
				+ " v.quantity  AS quantity, p.location  AS location, p.unitsinstock AS unitsinstock,  p.unitsonorder AS unitsonorder,  p.reorderlevel AS reorderlevel"
				+ " FROM parts p , vendorordereditems v, makemodel m " + " WHERE p.partno = v.partno "
				+ " AND p.makemodelcode = m.makemodelcode  " + " AND v.orderno =:orderno  " + " AND v.quantity > 0 "
				+ " ORDER BY  p.subcategory, m.manufacturerid, m.makemodelname";
		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("orderno", orderno))
				.addScalar("noorder").addScalar("partno").addScalar("makemodelname").addScalar("year")
				.addScalar("partdescription").addScalar("quantity").addScalar("location").addScalar("unitsinstock")
				.addScalar("unitsonorder").addScalar("reorderlevel")
				.setResultTransformer(Transformers.aliasToBean(PartList.class));

		List<PartList> results = query.list();
		session.flush();
		session.clear();
		return results;

	}

	@Transactional
	public List<TransferParts> getPartsForBranchTransfer(String subcategoryraw, String subcategoryselected,
			String stocklimit, String selectYear, String branchselected, String factor,
			Map<String, String> branchlistddSource, String makeselected, Map<String, Integer> makelistdd,
			String homebranch) {

		Set<String> loopBranch = new HashSet<String>();

		Integer manufacturerid = makelistdd.get(makeselected);

		if (!branchselected.equalsIgnoreCase("ALL")) {

			if (branchselected.contains(",")) {
				String[] bArr = branchselected.split(",");
				List<String> bList = Arrays.asList(bArr);
				for (String b : bList) {
					loopBranch.add(b);
				}
			} else {
				loopBranch.add(branchselected);
			}
			loopBranch.remove(homebranch);
		} else {
			branchlistddSource.remove("");
			branchlistddSource.remove(homebranch);
			loopBranch = branchlistddSource.keySet();
		}

		// //
		// LOGGER.info("______________________________________________________________________________");

		double factorvalue = Double.parseDouble(factor);

		double calcfactor = (100 + factorvalue) / 100;

		String INVENTORYTRANSFER_SQL_SUBCATEGORY = "";

		Session session = sessionFactory.getCurrentSession();

		Query query = null;

		if (subcategoryraw.equalsIgnoreCase("ALL")) {

			if (makeselected.equalsIgnoreCase("ALL")) {

				INVENTORYTRANSFER_SQL_SUBCATEGORY = "SELECT p.manufacturername AS manufacturername,p.MakeModelName AS makemodelname, p.partno AS partno,"
						+ " p.UnitsInStock AS unitsinstock, p.yearfrom AS yearfrom,  p.yearto AS yearto,"
						+ " p.PartDescription AS partdescription, p.location AS location, CEIL(p.safetyquantity * :calcfactor) AS safetyquantity, "
						+ " p.reorderlevel AS reorderlevel, p.unitsonorder AS unitsonorder "
						+ " FROM  parts p WHERE  P.InterchangeNo = ''  AND p.ordertype <> 'S'  AND p.UnitsInStock > :stocklimit "
						+ " AND ( (:selectYear BETWEEN yearfrom AND yearto ) OR ( yearto > :selectYear ) )  ORDER BY subcategory, location DESC;";

				query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY)
						.setParameter("stocklimit", stocklimit).setParameter("selectYear", selectYear)
						.setParameter("calcfactor", calcfactor)).addScalar("partno").addScalar("partdescription")
								.addScalar("makemodelname").addScalar("manufacturername").addScalar("yearfrom")
								.addScalar("yearto").addScalar("unitsinstock").addScalar("reorderlevel")
								.addScalar("unitsonorder").addScalar("safetyquantity").addScalar("location")
								.setResultTransformer(Transformers.aliasToBean(TransferParts.class));
			} else {

				INVENTORYTRANSFER_SQL_SUBCATEGORY = "SELECT p.manufacturername AS manufacturername,p.MakeModelName AS makemodelname, p.partno AS partno,"
						+ " p.UnitsInStock AS unitsinstock, p.yearfrom AS yearfrom,  p.yearto AS yearto,"
						+ " p.PartDescription AS partdescription, p.location AS location, CEIL(p.safetyquantity * :calcfactor) AS safetyquantity, "
						+ " p.reorderlevel AS reorderlevel, p.unitsonorder AS unitsonorder "
						+ " FROM  parts p, makemodel m WHERE p.makemodelcode = m.makemodelcode AND  P.InterchangeNo = ''   AND p.ordertype <> 'S' AND p.UnitsInStock > :stocklimit   AND ( (:selectYear BETWEEN yearfrom AND yearto ) OR ( yearto > :selectYear ) ) "
						+ "	AND p.makemodelcode in (select makemodelcode from makemodel where ManufacturerID =:manufacturerid)"
						+ " ORDER BY subcategory, location DESC;";

				query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY)
						.setParameter("stocklimit", stocklimit).setParameter("selectYear", selectYear)
						.setParameter("calcfactor", calcfactor).setParameter("manufacturerid", manufacturerid))
								.addScalar("partno").addScalar("partdescription").addScalar("makemodelname")
								.addScalar("manufacturername").addScalar("yearfrom").addScalar("yearto")
								.addScalar("unitsinstock").addScalar("reorderlevel").addScalar("unitsonorder")
								.addScalar("safetyquantity").addScalar("location")
								.setResultTransformer(Transformers.aliasToBean(TransferParts.class));
			}

		} else {
			if (makeselected.equalsIgnoreCase("ALL")) {
				INVENTORYTRANSFER_SQL_SUBCATEGORY = "SELECT p.manufacturername AS manufacturername,p.MakeModelName AS makemodelname, p.partno AS partno,"
						+ " p.UnitsInStock AS unitsinstock, p.yearfrom AS yearfrom,  p.yearto AS yearto,"
						+ " p.PartDescription AS partdescription, p.location AS location, CEIL(p.safetyquantity * :calcfactor) AS safetyquantity, "
						+ " p.reorderlevel AS reorderlevel, p.unitsonorder AS unitsonorder "
						+ " FROM  parts p WHERE  P.InterchangeNo = ''   AND p.ordertype <> 'S' AND p.subcategory = :subcategorycode AND p.UnitsInStock > :stocklimit  AND ( (:selectYear BETWEEN yearfrom AND yearto ) OR ( yearto > :selectYear ) ) "
						+ " ORDER BY subcategory, location DESC;";

				query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY)
						.setParameter("stocklimit", stocklimit).setParameter("selectYear", selectYear)
						.setParameter("calcfactor", calcfactor).setParameter("subcategorycode", subcategoryselected))
								.addScalar("partno").addScalar("partdescription").addScalar("makemodelname")
								.addScalar("manufacturername").addScalar("yearfrom").addScalar("yearto")
								.addScalar("unitsinstock").addScalar("reorderlevel").addScalar("unitsonorder")
								.addScalar("safetyquantity").addScalar("location")
								.setResultTransformer(Transformers.aliasToBean(TransferParts.class));
			} else {
				INVENTORYTRANSFER_SQL_SUBCATEGORY = "SELECT p.manufacturername AS manufacturername,p.MakeModelName AS makemodelname, p.partno AS partno,"
						+ " p.UnitsInStock AS unitsinstock, p.yearfrom AS yearfrom,  p.yearto AS yearto,"
						+ " p.PartDescription AS partdescription, p.location AS location, CEIL(p.safetyquantity * :calcfactor) AS safetyquantity, "
						+ " p.reorderlevel AS reorderlevel, p.unitsonorder AS unitsonorder "
						+ " FROM  parts p, makemodel m WHERE p.makemodelcode = m.makemodelcode AND  P.InterchangeNo = ''   AND p.ordertype <> 'S' AND p.subcategory = :subcategorycode AND p.UnitsInStock > :stocklimit  AND ( (:selectYear BETWEEN yearfrom AND yearto ) OR ( yearto > :selectYear ) ) "
						+ "	AND p.makemodelcode in (select makemodelcode from makemodel where ManufacturerID =:manufacturerid)"
						+ " ORDER BY subcategory, location DESC;";

				query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY)
						.setParameter("stocklimit", stocklimit).setParameter("selectYear", selectYear)
						.setParameter("calcfactor", calcfactor).setParameter("subcategorycode", subcategoryselected)
						.setParameter("manufacturerid", manufacturerid)).addScalar("partno")
								.addScalar("partdescription").addScalar("makemodelname").addScalar("manufacturername")
								.addScalar("yearfrom").addScalar("yearto").addScalar("unitsinstock")
								.addScalar("reorderlevel").addScalar("unitsonorder").addScalar("safetyquantity")
								.addScalar("location")
								.setResultTransformer(Transformers.aliasToBean(TransferParts.class));

			}

		}

		List<TransferParts> transferpartsList = new ArrayList<TransferParts>();
		transferpartsList = query.list();

		session.flush();
		session.clear();

		Map<String, StockData> chStockDataMap = new HashMap<String, StockData>();
		Map<String, StockData> grStockDataMap = new HashMap<String, StockData>();
		Map<String, StockData> mpStockDataMap = new HashMap<String, StockData>();

		// getting a list so that it will easier to call
		for (String branch : loopBranch) {
			// LOGGER.info("branch" + branch);
			switch (branch.toUpperCase()) {
			case "CHS":
				chStockDataMap = chpartsdao.getStockDataList();
				break;
			case "GRS":
				grStockDataMap = grpartsdao.getStockDataList();
				break;
			case "MPS":
				mpStockDataMap = mppartsdao.getStockDataList();
				break;
			case "":
				break;
			default:
				// LOGGER.info("no match");
				break;

			}
		}

		for (TransferParts transferparts : transferpartsList) {
			int totalAvailableQuantity = (int) Math.floor(transferparts.unitsinstock / 2);
			// LOGGER.info(transferparts.getPartno() + totalAvailableQuantity);
			if (totalAvailableQuantity > 0) {
				String partno = transferparts.getPartno().trim();
				StockData branchstockdata = new StockData();

				for (String branch : loopBranch) {
					// LOGGER.info("branch" + branch);
					switch (branch.toUpperCase()) {
					case "CHS":
						branchstockdata = chStockDataMap.get(partno);
						if (branchstockdata == null) {

							transferparts.setChunitsonorder(0);
							transferparts.setChunitsinstock(0);
							transferparts.setChsafetyquantity(0);
							transferparts.setChreorderlevel(0);
							transferparts.setChshow(1);
							transferparts.setChneed(0);
							break;
						} else {
							transferparts.setChunitsonorder(branchstockdata.getUnitsonorder());
							transferparts.setChunitsinstock(branchstockdata.getUnitsinstock());
							transferparts.setChsafetyquantity(branchstockdata.getSafetyquantity());
							transferparts.setChreorderlevel(branchstockdata.getReorderlevel());
							transferparts.setChshow(1);
						}
						if (totalAvailableQuantity > 0) {
							int chneed = 0;
							chneed = branchstockdata.getSafetyquantity() - branchstockdata.getUnitsinstock();
							if (chneed > 0) {
								if (totalAvailableQuantity > 3) {
									if (chneed > totalAvailableQuantity) {
										double chdouble = (totalAvailableQuantity / 2);
										chneed = (int) Math.ceil(chdouble);
									}
								} else {
									chneed = 0;
								}
							} else {
								chneed = 0;
							}
							if (chneed > totalAvailableQuantity) {
								chneed = (int) Math.ceil(totalAvailableQuantity * 0.3);
							}
							totalAvailableQuantity -= chneed;
							transferparts.setChneed(chneed);
						} else {
							transferparts.setChneed(0);
						}
						break;
					case "GRS":
						branchstockdata = grStockDataMap.get(partno);
						if (branchstockdata == null) {

							transferparts.setGrunitsonorder(0);
							transferparts.setGrunitsinstock(0);
							transferparts.setGrsafetyquantity(0);
							transferparts.setGrreorderlevel(0);
							transferparts.setGrshow(1);
							transferparts.setGrneed(0);
							break;
						} else {
							transferparts.setGrunitsonorder(branchstockdata.getUnitsonorder());
							transferparts.setGrunitsinstock(branchstockdata.getUnitsinstock());
							transferparts.setGrsafetyquantity(branchstockdata.getSafetyquantity());
							transferparts.setGrreorderlevel(branchstockdata.getReorderlevel());
							transferparts.setGrshow(1);
						}
						if (totalAvailableQuantity > 0) {
							int grneed = 0;
							grneed = branchstockdata.getSafetyquantity() - branchstockdata.getUnitsinstock();
							if (grneed > 0) {
								if (totalAvailableQuantity > 3) {
									if (grneed > totalAvailableQuantity) {
										double grdouble = (totalAvailableQuantity / 4);
										grneed = (int) Math.ceil(grdouble);
									}
								}
							} else {
								grneed = 0;
							}
							totalAvailableQuantity -= grneed;
							transferparts.setGrneed(grneed);
						} else {
							transferparts.setGrneed(0);
						}
						break;
					case "MPS":
						branchstockdata = mpStockDataMap.get(partno);
						if (branchstockdata == null) {

							transferparts.setMpunitsonorder(0);
							transferparts.setMpunitsinstock(0);
							transferparts.setMpsafetyquantity(0);
							transferparts.setMpreorderlevel(0);
							transferparts.setMpshow(1);
							transferparts.setMpneed(0);
							break;
						} else {
							transferparts.setMpunitsonorder(branchstockdata.getUnitsonorder());
							transferparts.setMpunitsinstock(branchstockdata.getUnitsinstock());
							transferparts.setMpsafetyquantity(branchstockdata.getSafetyquantity());
							transferparts.setMpreorderlevel(branchstockdata.getReorderlevel());
							transferparts.setMpshow(1);
						}
						if (totalAvailableQuantity > 0) {
							int mpneed = 0;
							mpneed = branchstockdata.getSafetyquantity() - branchstockdata.getUnitsinstock();
							if (mpneed > 0) {
								if (totalAvailableQuantity > 3) {
									if (mpneed > totalAvailableQuantity) {
										double mpdouble = (totalAvailableQuantity / 4);
										mpneed = (int) Math.ceil(mpdouble);
									}
								}
							} else {
								mpneed = 0;
							}
							totalAvailableQuantity -= mpneed;
							transferparts.setMpneed(mpneed);
						} else {
							transferparts.setMpneed(0);
						}
						break;

					case "":
						break;
					default:
						// LOGGER.info("no match");
						break;
					}
				}
			}

		} // for loop
		return transferpartsList;

	}

	@Transactional
	public List<PreOrderParts> getPreOrderParts(Integer orderno, Integer supplierid) {

		Session session = sessionFactory.getCurrentSession();
		List<PreOrderParts> preorderedparts = new ArrayList<PreOrderParts>();
		String hqlQuery = "From VendorOrderedItems vendorordereditems  where vendorordereditems.orderno=:orderno";
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);
		List<VendorOrderedItems> vendorordereditems = query.list();
		if (vendorordereditems.size() > 0) {
			Set<String> set = new HashSet<String>();
			for (VendorOrderedItems vendorordereditem : vendorordereditems) {
				if (set.add(vendorordereditem.getPartno())) {
					PreOrderParts preorderedpart = new PreOrderParts();
					PreOrderPartsHelper partshelper = getPreOrderPartsHelper(supplierid, vendorordereditem.getPartno());

					preorderedpart.setPartno(vendorordereditem.getPartno());
					preorderedpart.setPrice(vendorordereditem.getPrice());
					preorderedpart.setVendorpartno(vendorordereditem.getVendorpartno());
					preorderedpart.setQuantity(vendorordereditem.getQuantity());

					// from helper
					if (partshelper != null) {
						preorderedpart.setCapa(partshelper.getCapa());
						preorderedpart.setItemdesc1(partshelper.getItemdesc1());
						preorderedpart.setItemdesc2(partshelper.getItemdesc2());
						preorderedpart.setOemno(partshelper.getOemno());
						preorderedpart.setPlno(partshelper.plno);
					} else {
						PreOrderPartsHelper partshelper2 = getPreOrderParts2(vendorordereditem.getPartno());
						if (partshelper2 != null) {
							preorderedpart.setCapa(partshelper2.getCapa());
							preorderedpart.setItemdesc1(partshelper2.getItemdesc1());
							preorderedpart.setItemdesc2(partshelper2.getItemdesc2());
							preorderedpart.setOemno(partshelper2.getOemno());
							preorderedpart.setPlno(partshelper2.plno);
						}

					}
					preorderedparts.add(preorderedpart);
				}
			}
		} else {
			return null;
		}
		session.flush();
		session.clear();
		return preorderedparts;

	}

	@Transactional
	private PreOrderPartsHelper getPreOrderParts2(String partno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = "SELECT bva.partno AS partno, m.MakeModelName  AS itemdesc1,  bva.partdescription  AS itemdesc2, bva.capa  AS capa, "
				+ " bva.KeystoneNumber  AS plno, bva.OEMNumber  AS oemno FROM parts bva , makemodel m WHERE bva.MakeModelCode = m.MakeModelCode "
				+ " and bva.partno =:partno";

		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("partno", partno))
				.addScalar("itemdesc1").addScalar("itemdesc2").addScalar("capa").addScalar("plno").addScalar("oemno")
				.setResultTransformer(Transformers.aliasToBean(PreOrderPartsHelper.class));

		List<PreOrderPartsHelper> results = query.list();

		session.flush();
		session.clear();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	private PreOrderPartsHelper getPreOrderPartsHelper(Integer supplierid, String partno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = "SELECT p.partno AS partno, v.VendorPartNo AS vendorpartno, v.ItemDesc1  AS itemdesc1,  "
				+ " v.ItemDesc2  AS itemdesc2, p.capa  AS capa, v.PLNo  AS plno, v.OEMNo  AS oemno"
				+ " FROM parts p, vendoritems v" + " WHERE p.PartNo = v.PartNo" + " AND v.supplierid =:supplierid"
				+ " AND  p.partno =:partno";
		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("supplierid", supplierid)
				.setParameter("partno", partno)).addScalar("itemdesc1").addScalar("itemdesc2").addScalar("capa")
						.addScalar("plno").addScalar("oemno")
						.setResultTransformer(Transformers.aliasToBean(PreOrderPartsHelper.class));

		List<PreOrderPartsHelper> results = query.list();

		session.flush();
		session.clear();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
		// transaction.commit();

	}

	@Transactional
	public List<ProcurementParts> getProcurementParts(String analyticstodate, String subcategorycode,
			String ordertypeselected, String stocklimit, String onorderlimit, String orderlimit) {
		List<ProcurementParts> procurementpartslist = new LinkedList<ProcurementParts>();
		Session session = sessionFactory.getCurrentSession();
		Query query = null;

		Boolean hascategory = false;
		Boolean hasordertype = false;

		StringBuilder sb = new StringBuilder();
		sb.append(OrdersUtils.PROCUREMENT_BASE_SQL);

		if (subcategorycode != null) {
			if (!(subcategorycode.equalsIgnoreCase("ALL"))) {
				hascategory = true;
				sb.append(" AND parts.subcategory = :subcategorycode");
			}
		}

		if (ordertypeselected != null) {
			if (!(ordertypeselected.equalsIgnoreCase("-"))) {
				hasordertype = true;
				sb.append(" AND parts.ordertype = :ordertype");
			}
		}

		sb.append(" order by (parts.reorderlevel - parts.unitsinstock - parts.unitsonorder) desc");

		query = session.createSQLQuery(sb.toString());
		query.setParameter("unitsinstock", stocklimit);
		query.setParameter("unitsonorder", onorderlimit);

		if (hascategory) {
			query.setParameter("subcategorycode", subcategorycode);
		}

		if (hasordertype) {
			query.setParameter("ordertype", ordertypeselected);
		}

		((SQLQuery) query).addScalar("partno").addScalar("manufacturername").addScalar("makemodelname")
				.addScalar("year").addScalar("capa").addScalar("ordertype").addScalar("partdescription")
				.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
				.addScalar("safetyquantity").addScalar("quantitytoorder").addScalar("dpinumber")
				.addScalar("keystonenumber").addScalar("actualprice").addScalar("costprice").addScalar("percent")
				.setMaxResults(Integer.parseInt(orderlimit))
				.setResultTransformer(Transformers.aliasToBean(ProcurementParts.class));

		procurementpartslist = query.list();

		session.flush();
		session.clear();
		return procurementpartslist;
	}

	@Transactional
	public List<CategoryBySalesAnalysis> getSalesBySubcategory(String analyticsfromdate, String analyticstodate,
			String subcategorycode) {

		List<CategoryBySalesAnalysis> categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>();
		Map<String, CategoryBySalesAnalysis> categorycomparisonMap = new HashMap<String, CategoryBySalesAnalysis>();
		Set<String> dupelist = new HashSet<String>();

		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;

		sql = OrdersUtils.SALESBYCATEGORY;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategorycode))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("keystonenumber").addScalar("yearfrom").addScalar("yearto")
						.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
						.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> subcategorysaleslist = query.list();

		Collections.sort(subcategorysaleslist);

		for (CategoryBySalesAnalysis categorycomparison : subcategorysaleslist) {
			if (dupelist.add(categorycomparison.getPartno())) {
				categorycomparisonMap.put(categorycomparison.getPartno(), categorycomparison);
			}
		}

		// getting interchangeno figures
		String sqlinterchange = "";
		Query queryinterchange = null;

		String sqlMainNo = "";
		Query queryMainNo = null;

		sqlinterchange = OrdersUtils.SALESBYCATEGORY_INTERCHANGE;
		queryinterchange = ((SQLQuery) session.createSQLQuery(sqlinterchange)
				.setParameter("orderfrom", analyticsfromdate).setParameter("orderto", analyticstodate)
				.setParameter("subcategorycode", subcategorycode)).addScalar("partno").addScalar("partdescription")
						.addScalar("manufacturername").addScalar("makemodelname").addScalar("totalsold")
						.addScalar("keystonenumber").addScalar("yearfrom").addScalar("yearto").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
						.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> interchnagesoldlist = queryinterchange.list();

		for (CategoryBySalesAnalysis interchangehelper : interchnagesoldlist) {
			if (dupelist.add(interchangehelper.getPartno())) {
				String interchangepartno = interchangehelper.getPartno().trim();
				sqlMainNo = OrdersUtils.SALESBYCATEGORY_MAINNO;
				queryMainNo = ((SQLQuery) session.createSQLQuery(sqlMainNo).setParameter("partno", interchangepartno))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("keystonenumber").addScalar("yearfrom").addScalar("yearto")
						.addScalar("makemodelname").addScalar("totalsold", StandardBasicTypes.BIG_DECIMAL)
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));
				CategoryBySalesAnalysis mainpart = (CategoryBySalesAnalysis) queryMainNo.list().get(0);
				mainpart.setTotalsold(interchangehelper.getTotalsold());
				categorycomparisonMap.put(interchangepartno, mainpart);
			} else {
				BigDecimal interchangesold = new BigDecimal(interchangehelper.getTotalsold().intValue());
				String key = interchangehelper.getPartno();
				BigDecimal addtotalssold = categorycomparisonMap.get(key).getTotalsold().add(interchangesold);
				categorycomparisonMap.get(key).setTotalsold(addtotalssold);
			}
		}
		session.flush();
		session.clear();
		categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>(categorycomparisonMap.values());

		List<PartsMonthlySales> monthlysales = new ArrayList<PartsMonthlySales>();
		String hqlmonthlysalesQuery = "From PartsMonthlySales partsmonthlysales  where partsmonthlysales.partno=:partno ORDER BY  partsmonthlysales.yearmonth DESC";
		Query queryMonthlySales = null;
		queryMonthlySales = session.createQuery(hqlmonthlysalesQuery);

		// LOGGER.info("total items :" + categorycomparisonList.size());

		for (CategoryBySalesAnalysis categorybysalesanalysis : categorycomparisonList) {
			// LOGGER.info("now processing -> " + i++ + " of " +
			// categorycomparisonList.size());
			queryMonthlySales.setParameter("partno", categorybysalesanalysis.getPartno().trim());
			queryMonthlySales.setMaxResults(26);
			monthlysales = queryMonthlySales.list();
			categorybysalesanalysis.setPartsmonthlysaleslist(monthlysales);

		}

		Collections.sort(categorycomparisonList);
		return categorycomparisonList;
	}

	@Transactional
	public List<CategoryBySalesAnalysis> getSalesBySubcategoryAll(String analyticsfromdate, String analyticstodate,
			String string) {

		List<CategoryBySalesAnalysis> categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>();
		Map<String, CategoryBySalesAnalysis> categorycomparisonMap = new HashMap<String, CategoryBySalesAnalysis>();

		Session session = sessionFactory.getCurrentSession();
		Set<String> dupelist = new HashSet<String>();

		String sql = "";
		Query query = null;

		String sqlMainNo = "";
		Query queryMainNo = null;

		sql = OrdersUtils.SALESBYCATEGORY_ALL;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate)).addScalar("partno").addScalar("partdescription")
						.addScalar("keystonenumber").addScalar("yearfrom").addScalar("yearto")
						.addScalar("manufacturername").addScalar("makemodelname").addScalar("totalsold")
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> subcategorysaleslist = query.list();

		Collections.sort(subcategorysaleslist);

		for (CategoryBySalesAnalysis categorycomparison : subcategorysaleslist) {
			if (dupelist.add(categorycomparison.getPartno())) {
				categorycomparisonMap.put(categorycomparison.getPartno(), categorycomparison);
			}
		}

		// getting interchangeno figures
		String sqlinterchange = "";
		Query queryinterchange = null;

		sqlinterchange = OrdersUtils.SALESBYCATEGORY_ALL_INTERCHANGE;
		queryinterchange = ((SQLQuery) session.createSQLQuery(sqlinterchange)
				.setParameter("orderfrom", analyticsfromdate).setParameter("orderto", analyticstodate))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("keystonenumber").addScalar("yearfrom").addScalar("yearto")
						.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
						.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> interchnagesoldlist = queryinterchange.list();

		for (CategoryBySalesAnalysis interchangehelper : interchnagesoldlist) {
			if (dupelist.add(interchangehelper.getPartno())) {
				// LOGGER.info(interchangehelper.getPartno());
				String interchangepartno = interchangehelper.getPartno().trim();
				sqlMainNo = OrdersUtils.SALESBYCATEGORY_MAINNO;
				queryMainNo = ((SQLQuery) session.createSQLQuery(sqlMainNo).setParameter("partno", interchangepartno)

				).addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("keystonenumber").addScalar("yearfrom").addScalar("yearto")
						.addScalar("makemodelname").addScalar("totalsold", StandardBasicTypes.BIG_DECIMAL)
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class)

						);

				List<CategoryBySalesAnalysis> mainpartlist = queryMainNo.list();

				if (mainpartlist != null) {
					if (mainpartlist.size() > 0) {
						CategoryBySalesAnalysis mainpart = (CategoryBySalesAnalysis) queryMainNo.list().get(0);
						mainpart.setTotalsold(interchangehelper.getTotalsold());
						categorycomparisonMap.put(interchangepartno, mainpart);
					}
				}
			} else {
				BigDecimal interchangesold = new BigDecimal(interchangehelper.getTotalsold().intValue());
				String key = interchangehelper.getPartno();
				BigDecimal addtotalssold = categorycomparisonMap.get(key).getTotalsold().add(interchangesold);
				categorycomparisonMap.get(key).setTotalsold(addtotalssold);
			}
		}

		categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>(categorycomparisonMap.values());

		// NNQ
		/*
		 * List<PartsMonthlySales> monthlysales = new GapList<PartsMonthlySales>();
		 * String hqlmonthlysalesQuery =
		 * "From PartsMonthlySales partsmonthlysales  where partsmonthlysales.partno=:partno ORDER BY  partsmonthlysales.yearmonth DESC"
		 * ; Query queryMonthlySales = null; queryMonthlySales =
		 * session.createQuery(hqlmonthlysalesQuery);
		 * 
		 * LOGGER.info("total items :" + categorycomparisonList.size());
		 * 
		 * 
		 * for (CategoryBySalesAnalysis categorybysalesanalysis :
		 * categorycomparisonList) {
		 * 
		 * queryMonthlySales.setParameter("partno",
		 * categorybysalesanalysis.getPartno().trim());
		 * queryMonthlySales.setMaxResults(26); monthlysales = queryMonthlySales.list();
		 * categorybysalesanalysis.setPartsmonthlysaleslist(monthlysales);
		 * 
		 * }
		 */

		Collections.sort(categorycomparisonList);

		session.flush();
		session.clear();

		return categorycomparisonList;
	}

	@Transactional
	public List<SalesAnalysisHistoryCategory> getSalesHistoryByCategory(String analyticsfromdate,
			String analyticstodate) {

		Session session = sessionFactory.getCurrentSession();

		List<SalesAnalysisHistoryCategory> salesanalysishistorycategorylist = new LinkedList<SalesAnalysisHistoryCategory>();

		// setting datestring for query
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String durationfromperiod1 = analyticsfromdate.trim();
		String durationtoperiod1 = analyticstodate.trim();

		String durationfromperiod2 = analyticsfromdate.trim();
		String durationtoperiod2 = analyticstodate.trim();

		String durationfromperiod3 = analyticsfromdate.trim();
		String durationtoperiod3 = analyticstodate.trim();

		Calendar fromDateCalendar = Calendar.getInstance();
		Calendar toDateCalendar = Calendar.getInstance();

		try {

			fromDateCalendar.setTime(formatter.parse(durationfromperiod1));

			fromDateCalendar.add(Calendar.YEAR, -1);
			durationfromperiod2 = formatter.format(fromDateCalendar.getTime());

			fromDateCalendar.add(Calendar.YEAR, -1);
			durationfromperiod3 = formatter.format(fromDateCalendar.getTime());

			toDateCalendar.setTime(formatter.parse(durationtoperiod1));

			toDateCalendar.add(Calendar.YEAR, -1);
			durationtoperiod2 = formatter.format(toDateCalendar.getTime());

			toDateCalendar.add(Calendar.YEAR, -1);
			durationtoperiod3 = formatter.format(toDateCalendar.getTime());

		} catch (ParseException e) {
			durationfromperiod2 = InsightUtils.getCurrentMySQLDate();
			durationtoperiod2 = InsightUtils.getCurrentMySQLDate();

			durationfromperiod3 = InsightUtils.getCurrentMySQLDate();
			durationtoperiod3 = InsightUtils.getCurrentMySQLDate();
		}

		// step 1
		String sql1 = AnalyticsUtils.SUBCATEGORY_SALES_HISTORY_RETURN_MAIN;
		Query query1 = ((SQLQuery) session.createSQLQuery(sql1).setParameter("orderfrom", durationfromperiod1)
				.setParameter("orderto", durationtoperiod1)).addScalar("subcategorycode").addScalar("subcategory")
						.addScalar("cnt").addScalar("ourprice").addScalar("salesprice").addScalar("margin")
						.addScalar("prcnt")
						// .setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(YearSubCategoryReport.class));

		List<YearSubCategoryReport> salescategorylist1 = query1.list();
		if (salescategorylist1 != null) {
			if (salescategorylist1.size() > 0) {
				for (YearSubCategoryReport ysr : salescategorylist1) {

					SalesAnalysisHistoryCategory salesanalysishistorycategory = new SalesAnalysisHistoryCategory(1);

					salesanalysishistorycategory.setSubcategory(ysr.getSubcategory());
					salesanalysishistorycategory.setDurationfromperiod1(durationfromperiod1);
					salesanalysishistorycategory.setDurationfromperiod2(durationfromperiod2);
					salesanalysishistorycategory.setDurationfromperiod3(durationfromperiod3);
					salesanalysishistorycategory.setDurationtoperiod1(durationtoperiod1);
					salesanalysishistorycategory.setDurationtoperiod2(durationtoperiod2);
					salesanalysishistorycategory.setDurationtoperiod3(durationtoperiod3);

					salesanalysishistorycategory.setCntperiod1(ysr.getCnt());
					salesanalysishistorycategory.setOurpriceperiod1(ysr.getOurprice());
					salesanalysishistorycategory.setSalespriceperiod1(ysr.getSalesprice());
					salesanalysishistorycategory.setMarginperiod1(ysr.getMargin());
					salesanalysishistorycategory.setPrcntperiod1(ysr.getPrcnt());

					// step2
					String sql2 = AnalyticsUtils.SUBCATEGORY_SALES_HISTORY;
					Query query2 = ((SQLQuery) session.createSQLQuery(sql2)
							.setParameter("orderfrom", durationfromperiod2).setParameter("orderto", durationtoperiod2)
							.setParameter("subcategorycode", ysr.getSubcategorycode())).addScalar("subcategory")
									.addScalar("cnt").addScalar("ourprice").addScalar("salesprice").addScalar("margin")
									.addScalar("prcnt").setMaxResults(300000)
									.setResultTransformer(Transformers.aliasToBean(YearSubCategoryReport.class));
					List<YearSubCategoryReport> salescategorylist2 = query2.list();
					if (salescategorylist2 != null) {
						if (salescategorylist2.size() > 0) {
							salesanalysishistorycategory.setCntperiod2(salescategorylist2.get(0).getCnt());
							salesanalysishistorycategory.setOurpriceperiod2(salescategorylist2.get(0).getOurprice());
							salesanalysishistorycategory
									.setSalespriceperiod2(salescategorylist2.get(0).getSalesprice());
							salesanalysishistorycategory.setMarginperiod2(salescategorylist2.get(0).getMargin());
							salesanalysishistorycategory.setPrcntperiod2(salescategorylist2.get(0).getPrcnt());
						}
					}

					// step3
					String sql3 = AnalyticsUtils.SUBCATEGORY_SALES_HISTORY;
					Query query3 = ((SQLQuery) session.createSQLQuery(sql3)
							.setParameter("orderfrom", durationfromperiod3).setParameter("orderto", durationtoperiod3)
							.setParameter("subcategorycode", ysr.getSubcategorycode())).addScalar("subcategory")
									.addScalar("cnt").addScalar("ourprice").addScalar("salesprice").addScalar("margin")
									.addScalar("prcnt").setMaxResults(300000)
									.setResultTransformer(Transformers.aliasToBean(YearSubCategoryReport.class));
					List<YearSubCategoryReport> salescategorylist3 = query3.list();
					if (salescategorylist3 != null) {
						if (salescategorylist3.size() > 0) {
							salesanalysishistorycategory.setCntperiod3(salescategorylist3.get(0).getCnt());
							salesanalysishistorycategory.setOurpriceperiod3(salescategorylist3.get(0).getOurprice());
							salesanalysishistorycategory
									.setSalespriceperiod3(salescategorylist3.get(0).getSalesprice());
							salesanalysishistorycategory.setMarginperiod3(salescategorylist3.get(0).getMargin());
							salesanalysishistorycategory.setPrcntperiod3(salescategorylist3.get(0).getPrcnt());
						}
					}

					salesanalysishistorycategorylist.add(salesanalysishistorycategory);

				} // ysr loop
			}

		}

		session.flush();
		session.clear();
		return salesanalysishistorycategorylist;
	}

	@Transactional
	public List<ScanOrderDetails> getScanOrderDetails(Integer orderno) {

		String hqlQuery = "SELECT  mk.makemodelname AS makemodelname, mk.manufacturername AS manufacturername, p.partno AS partno, p.partdescription   AS partdescription"
				+ " , vo.quantity  AS quantity " + " FROM Parts p, VendorOrderedItems vo, makemodel mk"
				+ " WHERE p.partno = vo.partno AND p.makemodelcode = mk.makemodelcode"
				+ " AND vo.orderno =:orderno order by p.partno";
		List<ScanOrderDetails> scanorderdetailslist = new ArrayList<ScanOrderDetails>();
		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("orderno", orderno)).addScalar("partno")
				.addScalar("partdescription").addScalar("makemodelname").addScalar("manufacturername")
				.addScalar("quantity").setResultTransformer(Transformers.aliasToBean(ScanOrderDetails.class));
		List<ScanOrderDetails> results = query.list();
		session.flush();
		session.clear();

		for (ScanOrderDetails scanorderdetails : results) {

			StockData chdata = new StockData();

			StockData grdata = new StockData();

			StockData mpdata = new StockData();

			chdata = chpartsdao.getStockData(scanorderdetails.getPartno().trim());
			scanorderdetails.setChunitsinstock(chdata.getUnitsinstock());
			scanorderdetails.setChunitsonorder(chdata.getUnitsonorder());
			scanorderdetails.setChreorderlevel(chdata.getReorderlevel());
			scanorderdetails.setChsafetyquantity(chdata.getSafetyquantity());
			scanorderdetails.setChreturncount(chdata.getReturncount());

			grdata = grpartsdao.getStockData(scanorderdetails.getPartno().trim());
			scanorderdetails.setGrunitsinstock(grdata.getUnitsinstock());
			scanorderdetails.setGrunitsonorder(grdata.getUnitsonorder());
			scanorderdetails.setGrreorderlevel(grdata.getReorderlevel());
			scanorderdetails.setGrsafetyquantity(grdata.getSafetyquantity());
			scanorderdetails.setGrreturncount(grdata.getReturncount());

			mpdata = mppartsdao.getStockData(scanorderdetails.getPartno().trim());
			scanorderdetails.setMpunitsinstock(mpdata.getUnitsinstock());
			scanorderdetails.setMpunitsonorder(mpdata.getUnitsonorder());
			scanorderdetails.setMpreorderlevel(mpdata.getReorderlevel());
			scanorderdetails.setMpsafetyquantity(mpdata.getSafetyquantity());
			scanorderdetails.setMpreturncount(mpdata.getReturncount());

			scanorderdetailslist.add(scanorderdetails);
		}
		return results;
	}

	@Transactional
	public Integer getSupplierid(Integer orderno) {

		String hqlQuery = "From VendorOrder vendororder  where vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);
		List<VendorOrder> vendors = query.list();
		session.flush();
		session.clear();
		return vendors.get(0).getSupplierid();
	}

	@Transactional
	public VendorItems getVendorItem(Integer supplierid, String partno) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM VendorItems vendoritems  WHERE vendoritems.partno =:partno AND vendoritems.supplierid=:supplierid";
		Query query = session.createQuery(hSql);
		query.setParameter("partno", partno);
		query.setParameter("supplierid", supplierid);
		List<VendorItems> vendoritems = query.list();
		session.flush();
		session.clear();
		if (vendoritems.size() > 0) {
			return vendoritems.get(0);
		} else {
			VendorItems vendoritems2 = new VendorItems();
			vendoritems2.setPartno(partno);
			vendoritems2.setVendorpartno("");
			vendoritems2.setSupplierid(supplierid);
			return vendoritems2;

		}

	}

	@Transactional
	public VendorItems getVendorItemByVendorNO(Integer supplierid, String vendorpartno) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM VendorItems vendoritems  WHERE vendoritems.vendorpartno =:vendorpartno AND vendoritems.supplierid=:supplierid";
		Query query = session.createQuery(hSql);
		query.setParameter("vendorpartno", vendorpartno);
		query.setParameter("supplierid", supplierid);

		List<VendorItems> vendoritems = query.list();
		session.flush();
		session.clear();
		if (vendoritems.size() > 0) {
			return vendoritems.get(0);
		} else {
			VendorItems vendoritems2 = new VendorItems();
			vendoritems2.setPartno("");
			vendoritems2.setVendorpartno("");
			vendoritems2.setSupplierid(supplierid);
			return vendoritems2;

		}
	}

	@Transactional
	public List<VendorOrder> getVendorOrder(Integer supplierid, Integer orderno) {

		String hqlQuery = "From VendorOrder vendororder  where vendororder.supplierid=:supplierid and vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("supplierid", supplierid);
		query.setParameter("orderno", orderno);

		List<VendorOrder> vendororders = query.list();
		session.flush();
		session.clear();
		return vendororders;

	}

	@Transactional
	public List<VendorOrderedItemsDetails> getVendorOrderedItemsDetails(Integer orderno) {

		String hqlQuery = "SELECT  mk.makemodelname AS makemodelname, mk.manufacturername AS manufacturername, p.partno AS partno,  p.year AS year, p.capa AS capa, vo.vendorpartno  AS vendorpartno, p.partdescription   AS partdescription,p.unitsinstock AS unitsinstock,"
				+ " p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,p.returncount  AS returncount,p.safetyquantity  AS safetyquantity,vo.price  AS price , vo.quantity  AS quantity, (vo.quantity * vo.price)   AS totalprice, p.ordertype as ordertype"
				+ " FROM Parts p, VendorOrderedItems vo, makemodel mk"
				+ " WHERE p.partno = vo.partno AND p.makemodelcode = mk.makemodelcode"
				+ " AND vo.orderno =:orderno order by p.partno";
		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("orderno", orderno)).addScalar("partno")
				.addScalar("vendorpartno").addScalar("partdescription").addScalar("year").addScalar("capa")
				.addScalar("makemodelname").addScalar("manufacturername").addScalar("unitsinstock")
				.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("safetyquantity").addScalar("price")
				.addScalar("quantity").addScalar("totalprice").addScalar("ordertype").addScalar("returncount")
				.setResultTransformer(Transformers.aliasToBean(VendorOrderedItemsDetails.class));
		List<VendorOrderedItemsDetails> results = query.list();
		session.flush();
		session.clear();
		return results;

	}

	@Transactional
	public List<String> getVendorOrderedItemsPartsOnly(Integer orderno) {

		String hqlQuery = "Select partno FROM VendorOrderedItems vendorordereditems where  vendorordereditems.orderno =:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);
		List<String> results = query.list();
		session.flush();
		session.clear();
		return results;

	}

	@Transactional
	public List<CategoryBySalesAnalysis> getVendorPricesAllSubcategory(String analyticsfromdate, String analyticstodate,
			String subcategorycode, String factor, String stockvalue) {

		List<CategoryBySalesAnalysis> categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>();
		Map<String, CategoryBySalesAnalysis> categorycomparisonMap = new HashMap<String, CategoryBySalesAnalysis>();
		Set<String> dupelist = new HashSet<String>();

		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;

		sql = OrdersUtils.SALESBYCATEGORY;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategorycode))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
						.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> subcategorysaleslist = query.list();

		Collections.sort(subcategorysaleslist);

		for (CategoryBySalesAnalysis categorycomparison : subcategorysaleslist) {
			if (dupelist.add(categorycomparison.getPartno())) {
				categorycomparisonMap.put(categorycomparison.getPartno(), categorycomparison);
			}
		}

		// getting interchangeno figures
		String sqlinterchange = "";
		Query queryinterchange = null;

		String sqlMainNo = "";
		Query queryMainNo = null;

		sqlinterchange = OrdersUtils.SALESBYCATEGORY_INTERCHANGE;
		queryinterchange = ((SQLQuery) session.createSQLQuery(sqlinterchange)
				.setParameter("orderfrom", analyticsfromdate).setParameter("orderto", analyticstodate)
				.setParameter("subcategorycode", subcategorycode)).addScalar("partno").addScalar("partdescription")
						.addScalar("manufacturername").addScalar("makemodelname").addScalar("totalsold")
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		List<CategoryBySalesAnalysis> interchnagesoldlist = queryinterchange.list();

		for (CategoryBySalesAnalysis interchangehelper : interchnagesoldlist) {
			if (dupelist.add(interchangehelper.getPartno())) {
				// LOGGER.info(interchangehelper.getPartno());
				String interchangepartno = interchangehelper.getPartno().trim();
				sqlMainNo = OrdersUtils.SALESBYCATEGORY_MAINNO;
				queryMainNo = ((SQLQuery) session.createSQLQuery(sqlMainNo).setParameter("partno", interchangepartno))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("totalsold", StandardBasicTypes.BIG_DECIMAL)
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));
				CategoryBySalesAnalysis mainpart = (CategoryBySalesAnalysis) queryMainNo.list().get(0);
				mainpart.setTotalsold(interchangehelper.getTotalsold());
				categorycomparisonMap.put(interchangepartno, mainpart);
			} else {
				BigDecimal interchangesold = new BigDecimal(interchangehelper.getTotalsold().intValue());
				String key = interchangehelper.getPartno();
				BigDecimal addtotalssold = categorycomparisonMap.get(key).getTotalsold().add(interchangesold);
				categorycomparisonMap.get(key).setTotalsold(addtotalssold);
			}
		}
		session.flush();
		session.clear();
		categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>(categorycomparisonMap.values());

		List<PartsMonthlySales> monthlysales = new LinkedList<PartsMonthlySales>();
		String hqlmonthlysalesQuery = "From PartsMonthlySales partsmonthlysales  where partsmonthlysales.partno=:partno";
		Query queryMonthlySales = null;

		for (CategoryBySalesAnalysis categorybysalesanalysis : categorycomparisonList) {
			queryMonthlySales = session.createQuery(hqlmonthlysalesQuery);
			queryMonthlySales.setParameter("partno", categorybysalesanalysis.getPartno().trim());
			queryMonthlySales.setMaxResults(26);
			monthlysales = queryMonthlySales.list();
			categorybysalesanalysis.setPartsmonthlysaleslist(monthlysales);

		}

		Collections.sort(categorycomparisonList);
		return categorycomparisonList;
	}

	@Transactional
	public void removeYCCProfortune(Integer orderno) {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "delete FROM vendorordereditems  WHERE orderno =:orderno AND partno IN (SELECT partno FROM parts WHERE subcategory IN (9, 64, 27, 4 , 24,1, 2, 6,7,8,78))";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("orderno", orderno);
		query.executeUpdate();
		session.flush();
		session.clear();
	}

	@Transactional
	public void saveVendorItems(VendorItems vendoritems) {

		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(vendoritems);
		session.flush();
		session.clear();

	}

	@Transactional
	public void startResetUnitsOnOrderProcess() {

		Session session = sessionFactory.getCurrentSession();

		String updateSQL = "UPDATE parts set unitsonorder = 0;";
		Query query = session.createSQLQuery(updateSQL);

		query.executeUpdate();

		// qualifying orders

		String orderSQL = "SELECT distinct orderno from vendororder where isfinal = 'Y' AND UpdatedInventory= 'N'";

		List<Integer> listoforders = new ArrayList<Integer>();

		Query query2 = session.createSQLQuery(orderSQL);

		listoforders = query2.list();

		String vendorordereditemslistSQL = "from VendorOrderedItems vendorordereditems where vendorordereditems.orderno = :orderno";

		for (Integer orderno : listoforders) {

			LOGGER.info("processing order ....." + orderno);
			List<VendorOrderedItems> vendorordereditemslist = new ArrayList<VendorOrderedItems>();
			Query query3 = session.createQuery(vendorordereditemslistSQL);
			query3.setParameter("orderno", orderno);
			vendorordereditemslist = query3.list();

			for (VendorOrderedItems vo : vendorordereditemslist) {
				updateUnitsOnOrder(vo.getQuantity(), vo.getPartno());

			}

		}

		session.flush();
		session.clear();
		LOGGER.info("reset order done.....");
	}

	@Transactional
	public void updateUnitsOnOrder(Integer quanity, String partno) {
		Session session = sessionFactory.getCurrentSession();
		String updateUnitsOnOrderSQL = "update parts set unitsonorder = unitsonorder + :quantity  where partno = :partno";
		Query query4 = session.createSQLQuery(updateUnitsOnOrderSQL);
		query4.setParameter("quantity", quanity);
		query4.setParameter("partno", partno);
		query4.executeUpdate();
		session.flush();
		session.clear();

	}

	@Transactional
	public void updateVendorOrder(Integer orderno, Integer totalitems, BigDecimal ordertotal) {

		Session session = sessionFactory.getCurrentSession();
		VendorOrder vendororder = (VendorOrder) session.get(VendorOrder.class, orderno);
		vendororder.setTotalitems(totalitems);
		vendororder.setOrdertotal(ordertotal);
		session.flush();
		session.clear();
	}

	@Transactional
	public void updateVendorOrderedItems(List<VendorOrderedItems> vendororderitemslist) {

		Session session = sessionFactory.getCurrentSession();
		for (VendorOrderedItems vendororderitems : vendororderitemslist) {
			session.save(vendororderitems);
		}
		session.flush();
		session.clear();

	}

	@Transactional
	public void updateVendorParts(Integer orderno, Integer supplierid) {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "UPDATE vendorordereditems st INNER JOIN vendoritems sc ON st.partno = sc.partno "
				+ " SET st.VendorPartNo = sc.VendorPartNo WHERE st.orderno=:orderno "
				+ " AND st.PartNo = sc.PartNo AND sc.SupplierID =:supplierid";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("orderno", orderno);
		query.setParameter("supplierid", supplierid);
		query.executeUpdate();
		session.flush();
		session.clear();

	}

	@Transactional
	public List<CategoryBySalesAnalysis> wrapupwithsales(List<CategoryBySalesAnalysis> categorysaleslist) {
		Session session = sessionFactory.getCurrentSession();
		List<PartsMonthlySales> monthlysales = new LinkedList<PartsMonthlySales>();
		String hqlmonthlysalesQuery = "From PartsMonthlySales partsmonthlysales  where partsmonthlysales.partno=:partno order by partsmonthlysales.yearmonth desc";
		Query queryMonthlySales = null;

		for (CategoryBySalesAnalysis categorybysalesanalysis : categorysaleslist) {
			queryMonthlySales = session.createQuery(hqlmonthlysalesQuery);
			queryMonthlySales.setParameter("partno", categorybysalesanalysis.getPartno().trim());
			queryMonthlySales.setMaxResults(26);
			monthlysales = queryMonthlySales.list();
			categorybysalesanalysis.setPartsmonthlysaleslist(monthlysales);

		}

		session.flush();
		session.clear();
		return categorysaleslist;
	}

}
