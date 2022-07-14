package com.bvas.insight.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.CategoryBySalesAnalysis;
import com.bvas.insight.data.CostOfGoodsReport;
import com.bvas.insight.data.CostOfGoodsTotalReport;
import com.bvas.insight.data.LocalOrderItemsDetails;
import com.bvas.insight.data.MonthCustomerReport;
import com.bvas.insight.data.MonthSubCategoryReport;
import com.bvas.insight.data.PartnoQuantity;
import com.bvas.insight.data.SubCatAllBranch;
import com.bvas.insight.data.SubCatDrillDetails;
import com.bvas.insight.data.VendorPriceComparison;
import com.bvas.insight.data.WeekCustomerReport;
import com.bvas.insight.data.WeekSubCategoryReport;
import com.bvas.insight.data.YearCustomerReport;
import com.bvas.insight.data.YearSubCategoryReport;
import com.bvas.insight.entity.InvoiceDetails;
import com.bvas.insight.entity.OrderCounter;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.jdbc.ChStocksDAOImpl;
import com.bvas.insight.jdbc.GrStocksDAOImpl;
import com.bvas.insight.jdbc.MpStocksDAOImpl;
import com.bvas.insight.utilities.AnalyticsUtils;
import com.bvas.insight.utilities.OrdersUtils;

@Repository
@SuppressWarnings("unchecked")
public class AdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersService.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public String createLocalLightsOrders(List<PartnoQuantity> localpartslist, String vendors, String branch,
			String orderseries, String repositorypath) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Date javasqldate = new java.sql.Date(calendar.getTime().getTime());

		Integer usedorder = 0;

		StringBuffer bufferProcess = new StringBuffer();
		StringBuffer bufferError = new StringBuffer();

		Integer ordernoseries = getMaxOrder(branch, orderseries);
		Integer ordernoseries4 = ordernoseries + 1;
		Integer ordernoseries10 = ordernoseries + 2;
		Integer ordernoseries34 = ordernoseries + 3;

		bufferError.append('\n');
		bufferError.append("PRICE NOT FIND ON THESE ITEMS:");
		bufferError.append('\n');

		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		int i = 0;
		int counter4 = 0;
		int counter10 = 0;
		int counter34 = 0;
		String[] vendorsarray = vendors.split(",");
		Integer[] myIntArray = new Integer[vendorsarray.length];
		for (String id : vendorsarray) {

			myIntArray[i] = Integer.parseInt(id);
			switch (myIntArray[i]) {
			case 4:
				usedorder = ordernoseries4;
				bufferProcess.append('\n');
				bufferProcess.append("Maxzone Order Created:" + ordernoseries4);
				bufferProcess.append('\n');
				break;
			case 10:
				usedorder = ordernoseries10;
				bufferProcess.append('\n');
				bufferProcess.append("Genera Order Created:" + ordernoseries10);
				bufferProcess.append('\n');
				break;
			case 34:
				usedorder = ordernoseries34;
				bufferProcess.append('\n');
				bufferProcess.append("Vision Order Created:" + ordernoseries34);
				bufferProcess.append('\n');
				break;
			default:
				usedorder = ordernoseries4;
				bufferProcess.append('\n');
				bufferProcess.append("Maxzone Order Created:" + ordernoseries4);
				bufferProcess.append('\n');
				break;
			}

			OrderCounter ordercounter = new OrderCounter();
			ordercounter.setBranch(branch);
			ordercounter.setOrderno(usedorder);
			ordercounter.setOrdertype("L");
			session.save(ordercounter);

			// OrderNo, SupplierId, OrderDate, DeliveredDate, OrderStatus,
			// OrderTotal, UpdatedInventory, UpdatedPrices

			VendorOrder vendororder = new VendorOrder();
			vendororder.setOrderno(usedorder);
			vendororder.setSupplierid(myIntArray[i]);
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
			session.save(vendororder);

			i++;
		}
		String hqlQuery = "from VendorItems vendoritems where vendoritems.partno = :partno and vendoritems.supplierid IN(:vendors) and vendoritems.sellingrate > 0.01 order by vendoritems.sellingrate";
		for (PartnoQuantity partquantity : localpartslist) {

			query = session.createQuery(hqlQuery);
			query.setParameter("partno", partquantity.getPartno());
			query.setParameterList("vendors", Arrays.asList(myIntArray));
			query.setMaxResults(1);
			VendorOrderedItems vendorordereditems = new VendorOrderedItems();
			List<VendorItems> results = query.list();
			if (results.size() > 0) {
				VendorItems vendoritems = results.get(0);
				switch (vendoritems.getSupplierid()) {
				case 4:
					vendorordereditems.setOrderno(ordernoseries4);
					vendorordereditems.setPartno(partquantity.getPartno());
					vendorordereditems.setNoorder(counter4);
					vendorordereditems.setQuantity(partquantity.getQuantity());
					vendorordereditems.setPrice(vendoritems.getSellingrate());
					vendorordereditems.setVendorpartno(vendoritems.getVendorpartno());
					session.save(vendorordereditems);
					counter4++;
					break;
				case 10:
					vendorordereditems.setOrderno(ordernoseries10);
					vendorordereditems.setPartno(partquantity.getPartno());
					vendorordereditems.setNoorder(counter10);
					vendorordereditems.setQuantity(partquantity.getQuantity());
					vendorordereditems.setPrice(vendoritems.getSellingrate());
					vendorordereditems.setVendorpartno(vendoritems.getVendorpartno());
					session.save(vendorordereditems);
					counter10++;
					break;
				case 34:
					vendorordereditems.setOrderno(ordernoseries34);
					vendorordereditems.setPartno(partquantity.getPartno());
					vendorordereditems.setNoorder(counter34);
					vendorordereditems.setQuantity(partquantity.getQuantity());
					vendorordereditems.setPrice(vendoritems.getSellingrate());
					vendorordereditems.setVendorpartno(vendoritems.getVendorpartno());
					session.save(vendorordereditems);
					counter34++;
					break;
				default:
					vendorordereditems.setOrderno(ordernoseries4);
					vendorordereditems.setPartno(partquantity.getPartno());
					vendorordereditems.setNoorder(counter4);
					vendorordereditems.setQuantity(partquantity.getQuantity());
					vendorordereditems.setPrice(vendoritems.getSellingrate());
					vendorordereditems.setVendorpartno(vendoritems.getVendorpartno());
					session.save(vendorordereditems);
					counter4++;
					break;
				}

			} else {
				bufferError.append("'" + partquantity.getPartno() + "'," + "\n");
			}
		}

		try {
			File file = new File(
					repositorypath + File.separator + "localorder" + File.separator + "LO" + javasqldate + ".txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(bufferError.toString());
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		session.flush();
		session.clear();
		return bufferProcess.toString();
	}

	@Transactional
	public String createTransAmericaOrder(List<PartnoQuantity> localpartslist, String locallightvendors, String branch,
			String localseries) {

		Session session = null;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date javasqldate = new java.sql.Date(calendar.getTime().getTime());

		Integer counter = 0;

		StringBuffer bufferProcess = new StringBuffer();
		StringBuffer bufferError = new StringBuffer();

		Integer ordernoseries30 = getMaxOrder(branch, localseries) + 1;

		bufferError.append('\n');
		bufferError.append("price not found:");
		bufferError.append('\n');

		session = sessionFactory.getCurrentSession();

		OrderCounter ordercounter = new OrderCounter();
		ordercounter.setBranch(branch);
		ordercounter.setOrderno(ordernoseries30);
		ordercounter.setOrdertype("L");
		session.saveOrUpdate(ordercounter);

		// OrderNo, SupplierId, OrderDate, DeliveredDate, OrderStatus,
		// OrderTotal, UpdatedInventory, UpdatedPrices

		VendorOrder vendororder = new VendorOrder();
		vendororder.setOrderno(ordernoseries30);
		vendororder.setSupplierid(30);
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
		bufferProcess.append("Trans America Order Created:" + ordernoseries30);
		bufferProcess.append('\n');

		for (PartnoQuantity partquantity : localpartslist) {
			VendorOrderedItems vendorordereditems = new VendorOrderedItems();
			vendorordereditems.setOrderno(ordernoseries30);
			vendorordereditems.setPartno(partquantity.getPartno());
			vendorordereditems.setNoorder(counter);
			vendorordereditems.setQuantity(partquantity.getQuantity());
			vendorordereditems.setPrice(new BigDecimal(partquantity.getPrice()));
			vendorordereditems.setVendorpartno("");
			session = sessionFactory.getCurrentSession();
			session.save(vendorordereditems);
			counter++;
		}

		session.flush();
		session.clear();
		return bufferProcess.toString() + bufferError.toString();

	}

	@Transactional
	public String createWestinOrder(List<PartnoQuantity> localpartslist, String locallightvendors, String branch,
			String localseries) {

		Session session = null;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date javasqldate = new java.sql.Date(calendar.getTime().getTime());

		Integer counter = 0;

		StringBuffer bufferProcess = new StringBuffer();
		StringBuffer bufferError = new StringBuffer();

		Integer ordernoseries32 = getMaxOrder(branch, localseries) + 1;

		bufferError.append('\n');
		bufferError.append("price not found:");
		bufferError.append('\n');

		session = sessionFactory.getCurrentSession();

		OrderCounter ordercounter = new OrderCounter();
		ordercounter.setBranch(branch);
		ordercounter.setOrderno(ordernoseries32);
		ordercounter.setOrdertype("L");
		session.saveOrUpdate(ordercounter);

		// OrderNo, SupplierId, OrderDate, DeliveredDate, OrderStatus,
		// OrderTotal, UpdatedInventory, UpdatedPrices

		VendorOrder vendororder = new VendorOrder();
		vendororder.setOrderno(ordernoseries32);
		vendororder.setSupplierid(32);
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
		bufferProcess.append("Westin Order Created:" + ordernoseries32);
		bufferProcess.append('\n');

		for (PartnoQuantity partquantity : localpartslist) {
			VendorOrderedItems vendorordereditems = new VendorOrderedItems();
			vendorordereditems.setOrderno(ordernoseries32);
			vendorordereditems.setPartno(partquantity.getPartno());
			vendorordereditems.setNoorder(counter);
			vendorordereditems.setQuantity(partquantity.getQuantity());
			vendorordereditems.setPrice(new BigDecimal(partquantity.getPrice()));
			vendorordereditems.setVendorpartno("");
			session = sessionFactory.getCurrentSession();
			session.save(vendorordereditems);
			counter++;
		}

		session.flush();
		session.clear();
		return bufferProcess.toString() + bufferError.toString();

	}

	@Transactional
	private Integer getAbsoluteMax(Integer latestOrderNumber) {
		Integer loopOrder = latestOrderNumber;
		return orderExists(loopOrder) ? getAbsoluteMax(loopOrder + 1) : loopOrder;

	}

	@Transactional
	public CostOfGoodsTotalReport getCostOfGoodsTotal(String orderdatefrom, String orderdateto, String extendedvalue)
			throws NumberFormatException {

		Double ourpriceiter = 0.00d;
		Double soldpriceiter = 0.00d;
		Double marginiter = 0.00d;
		Double discountier = 0.00d;
		Double percent = 0.00d;
		Double taxier = 0.00d;
		Double ZERO_ = new Double("0.00");
		CostOfGoodsTotalReport costofgoodstotal = new CostOfGoodsTotalReport();
		Integer totalitems = 0;
		String effectiveSql = "";

		if (extendedvalue != null) {
			if (extendedvalue.equalsIgnoreCase("extended")) {
				effectiveSql = AnalyticsUtils.COSTOFGOODS_SQL_EXTENDED;
			} else {
				effectiveSql = AnalyticsUtils.COSTOFGOODS_SQL;
			}
		} else {
			effectiveSql = AnalyticsUtils.COSTOFGOODS_SQL;
		}

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(effectiveSql).setParameter("orderdatefrom", orderdatefrom)
				.setParameter("orderdateto", orderdateto)).addScalar("invoicenumber").addScalar("invoicetotal")
						.addScalar("discount").addScalar("ourprice").addScalar("soldprice").addScalar("margin")
						.addScalar("tax").setResultTransformer(Transformers.aliasToBean(CostOfGoodsReport.class));

		List<CostOfGoodsReport> results = query.list();

		totalitems = results.size();

		for (CostOfGoodsReport costofgoods : results) {
			ourpriceiter = ourpriceiter + costofgoods.getOurprice().doubleValue();
			soldpriceiter = soldpriceiter + costofgoods.getSoldprice().doubleValue();
			marginiter = marginiter + costofgoods.getMargin().doubleValue();
			discountier = discountier + costofgoods.getDiscount().doubleValue();
			taxier = taxier + costofgoods.getTax().doubleValue();
		}
		if (soldpriceiter.compareTo(ZERO_) > 0) {
			percent = (marginiter * 100) / soldpriceiter;
		}

		BigDecimal totalourprice = new BigDecimal(Double.valueOf(ourpriceiter));
		BigDecimal totalsoldprice = new BigDecimal(Double.valueOf(soldpriceiter));
		BigDecimal totaldiscount = new BigDecimal(Double.valueOf(discountier));
		BigDecimal totalmargin = new BigDecimal(Double.valueOf(marginiter));
		BigDecimal totalpercent = new BigDecimal(Double.valueOf(percent));
		BigDecimal totaltax = new BigDecimal(Double.valueOf(taxier));
		BigDecimal totalgross = totalsoldprice.add(totaltax);

		costofgoodstotal.setTotalitems(totalitems);
		costofgoodstotal.setTotalourprice(totalourprice.setScale(2, RoundingMode.HALF_UP));
		costofgoodstotal.setTotalsoldprice(totalsoldprice.setScale(2, RoundingMode.HALF_UP));
		costofgoodstotal.setTotaldiscount(totaldiscount.setScale(2, RoundingMode.HALF_UP));
		costofgoodstotal.setTotalmargin(totalmargin.setScale(2, RoundingMode.HALF_UP));
		costofgoodstotal.setTotalpercent(totalpercent.setScale(2, RoundingMode.HALF_UP));
		costofgoodstotal.setTotalgross(totalgross.setScale(2, RoundingMode.HALF_UP));

		session.flush();
		session.clear();

		return costofgoodstotal;

	}

	@Transactional
	public List<SubCatDrillDetails> getDrillDetailsSubcategory(String fromdate, String todate, String subcategorycode) {

		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;

		if (subcategorycode == null) {
			subcategorycode = "ALL";
		}

		if (subcategorycode.equalsIgnoreCase("ALL")) {
			sql = AnalyticsUtils.SUBCAT_DETAIL_ALL;
			query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate).setParameter("orderto",
					todate)).addScalar("partno").addScalar("ordertype").addScalar("cnt").addScalar("actualprice")
							.addScalar("costprice").addScalar("partmargin").addScalar("partprcnt").addScalar("ourprice")
							.addScalar("salesprice").addScalar("margin").addScalar("prcnt").setMaxResults(300000)
							.setResultTransformer(Transformers.aliasToBean(SubCatDrillDetails.class));
		} else {
			sql = AnalyticsUtils.SUBCAT_DETAIL;
			query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
					.setParameter("orderto", todate).setParameter("subcategorycode", subcategorycode))
							.addScalar("partno").addScalar("ordertype").addScalar("cnt").addScalar("actualprice")
							.addScalar("costprice").addScalar("partmargin").addScalar("partprcnt").addScalar("ourprice")
							.addScalar("salesprice").addScalar("margin").addScalar("prcnt").setMaxResults(300000)
							.setResultTransformer(Transformers.aliasToBean(SubCatDrillDetails.class));
		}

		List<SubCatDrillDetails> salescategorydrilldetailslist = query.list();

		session.flush();
		session.clear();

		return salescategorydrilldetailslist;
	}

	@Transactional
	public List<LocalOrderItemsDetails> getLocalOrderDetails(String selection, String stockvalue, String ordervalue,
			String reordervalue, String nsfvalue, String forecastdays) {

		// LOGGER.info("nsfvalue:" + nsfvalue);
		Set<String> set = new HashSet<String>();
		String hqlQuery = AnalyticsUtils.LOCALORDER_LIGHTS + " AND UNITSINSTOCK <= " + Integer.parseInt(stockvalue)
				+ " AND UNITSONORDER <= " + Integer.parseInt(ordervalue) + " AND REORDERLEVEL >= "
				+ Integer.parseInt(reordervalue) + "   order by p.partno , v.companytype, VO.SELLINGRATE";
		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("forecastdays", forecastdays))
				.addScalar("partno").addScalar("vendorpartno").addScalar("partdescription").addScalar("year")
				.addScalar("capa").addScalar("makemodelname").addScalar("manufacturername").addScalar("unitsinstock")
				.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("price").addScalar("quantity")
				.addScalar("totalprice").addScalar("ordertype")
				.setResultTransformer(Transformers.aliasToBean(LocalOrderItemsDetails.class));

		List<LocalOrderItemsDetails> results = query.list();

		if (nsfvalue != null) {
			if (nsfvalue.equalsIgnoreCase("nsf")) {

				// checking for nsf
				List<String> valuesToRemove = new ArrayList<String>();
				Map<String, LocalOrderItemsDetails> filterNSF = new LinkedHashMap<String, LocalOrderItemsDetails>();
				for (LocalOrderItemsDetails vo : results) {
					String partno = vo.getPartno();
					filterNSF.put(vo.getPartno(), vo);
					String lastStr = partno.substring(partno.length() - 1);

					if (lastStr.equalsIgnoreCase("N")) {
						valuesToRemove.add(partno.substring(0, partno.length() - 1));
					}
				}
				// using iterators
				Iterator<Map.Entry<String, LocalOrderItemsDetails>> itr = filterNSF.entrySet().iterator();
				while (itr.hasNext()) {
					Map.Entry<String, LocalOrderItemsDetails> entry = itr.next();
					String partno = entry.getKey();

					if (valuesToRemove.contains(partno)) {

						itr.remove();

					}
				}
				results = new ArrayList<LocalOrderItemsDetails>(filterNSF.values());
			}
		}

		List<LocalOrderItemsDetails> newresults = new LinkedList<LocalOrderItemsDetails>();

		for (LocalOrderItemsDetails vo : results) {

			Boolean havestock = false;

			if (set.add(vo.getPartno())) {

				if ((!(vo.getPartno() == null)) && (vo.partdescription.length() > 0)) {
					String lastStr = vo.getPartno().substring(vo.getPartno().length() - 1);
					// // LOGGER.info(vo.getPartno() + ":" + lastStr);
					if (lastStr.equalsIgnoreCase("N")) {
						havestock = haveStock(vo.getPartno().substring(0, vo.getPartno().length() - 1),
								Integer.parseInt(stockvalue));
					} else {
						havestock = haveStock(vo.getPartno() + "N", Integer.parseInt(stockvalue));
					}
					if (!(havestock)) {
						newresults.add(vo);
					}
				}
				//

			}
		}

		session.flush();
		session.clear();
		return newresults;

	}

	@Transactional
	private Integer getMaxLocalNumber(String branch, String orderseries) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "SELECT MAX(ordercounter.orderno) FROM OrderCounter ordercounter  WHERE ordercounter.ordertype = 'L'   AND ordercounter.branch =:branch";
		Query query = session.createQuery(hSql);
		// query.setParameter("orderseries", orderseries + "%");
		query.setParameter("branch", branch);

		List<Integer> listofseries = query.list();
		Integer maxorder = listofseries.get(0);
		session.flush();
		session.clear();
		return maxorder;
	}

	@Transactional
	private Integer getMaxOrder(String branch, String orderseries) {
		Integer latestOrderNumber = getMaxLocalNumber(branch, orderseries);
		return getAbsoluteMax(latestOrderNumber);

	}

	@Transactional
	public List<MonthSubCategoryReport> getMonthlySalesSubcategory(String fromdate, String todate) {

		Session session = sessionFactory.getCurrentSession();
		String sql = AnalyticsUtils.MONTH_SUBCATEGORY;
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
				.setParameter("orderto", todate)).addScalar("yr").addScalar("mnth").addScalar("subcategory")
						.addScalar("cnt").addScalar("ourprice").addScalar("salesprice").addScalar("margin")
						.addScalar("prcnt").setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(MonthSubCategoryReport.class));

		List<MonthSubCategoryReport> salescategorylist = query.list();

		session.flush();
		session.clear();

		return salescategorylist;
	}

	@Transactional
	public List<MonthCustomerReport> getMonthSalesCustomer(String fromdate, String todate) {

		Session session = sessionFactory.getCurrentSession();
		String sql = AnalyticsUtils.MONTH_CUSTOMER;
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
				.setParameter("orderto", todate)).addScalar("yr").addScalar("mnth").addScalar("companyname")
						.addScalar("cnt").addScalar("ourprice").addScalar("salesprice").addScalar("margin")
						.addScalar("prcnt").setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(MonthCustomerReport.class));

		List<MonthCustomerReport> salescustomerlist = query.list();

		session.flush();
		session.clear();

		return salescustomerlist;
	}

	@Transactional
	public int getSalesOfPartForGivenDuration(String partno, String fromdate, String todate) {

		// // LOGGER.info("partno:" + partno);

		Integer count1 = 0;
		Integer count2 = 0;

		String hqlQuery1 = "SELECT COUNT(1) AS count FROM PARTS P, invoicedetails invdtls, INVOICE i  "
				+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND i.ReturnedInvoice = 0 "
				+ " AND p.PARTNO =:partno AND i.OrderDate BETWEEN :fromdate AND :todate";

		Session session = sessionFactory.getCurrentSession();

		Query query1 = (session.createSQLQuery(hqlQuery1).setParameter("partno", partno)
				.setParameter("fromdate", fromdate).setParameter("todate", todate));

		// // LOGGER.info("count1:" + ((Number) query1.uniqueResult()).intValue());

		count1 = ((Number) query1.uniqueResult()).intValue();

		if (count1 <= 0) {
			count1 = 0;
		}

		String hqlQuery2 = "SELECT COUNT(1) AS count FROM PARTS P, invoicedetails invdtls, INVOICE i  "
				+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND i.ReturnedInvoice = 0 "
				+ " AND p.INTERCHANGENO =:partno AND i.OrderDate BETWEEN :fromdate AND :todate";

		Query query2 = (session.createSQLQuery(hqlQuery2).setParameter("partno", partno)
				.setParameter("fromdate", fromdate).setParameter("todate", todate));

		// // LOGGER.info("count2:" + ((Number) query2.uniqueResult()).intValue());

		count2 = ((Number) query2.uniqueResult()).intValue();

		if (count2 <= 0) {
			count2 = 0;
		}

		return count1 + count2;

	}

	@Transactional
	public List<SubCatAllBranch> getSubcategoryAllBranch(String analyticsfromdate, String analyticstodate,
			String subcategorycode, ChStocksDAOImpl chstocksdao, GrStocksDAOImpl grstocksdao,
			MpStocksDAOImpl mpstocksdao) {

		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;
		sql = AnalyticsUtils.SUBCAT_ALLBRANCH;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategorycode))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("chicagoquantity").setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(SubCatAllBranch.class));

		List<SubCatAllBranch> subcategoryallbranchlist = query.list();
		for (SubCatAllBranch subcatallbranch : subcategoryallbranchlist) {
			String partno = subcatallbranch.getPartno();
			subcatallbranch
					.setMelrosequantity(mpstocksdao.getSalesQuantity(analyticsfromdate, analyticstodate, partno));
			subcatallbranch
					.setGrandrapidsquantity(grstocksdao.getSalesQuantity(analyticsfromdate, analyticstodate, partno));

			BigDecimal total = subcatallbranch.getChicagoquantity().add(subcatallbranch.getGrandrapidsquantity());
			subcatallbranch.setTotal(total);

		}

		session.flush();
		session.clear();

		return subcategoryallbranchlist;

	}

	@Transactional
	public List<LocalOrderItemsDetails> getTransamericaOrderDetails(String orderselect, String stockvalue,
			String ordervalue, String reordervalue) {

		HashSet<String> set = new HashSet<String>();
		String hqlQuery = AnalyticsUtils.LOCALORDER_TRANSAMERICA + " AND UNITSINSTOCK <= "
				+ Integer.parseInt(stockvalue) + " AND UNITSONORDER <= " + Integer.parseInt(ordervalue)
				+ " AND REORDERLEVEL >= " + Integer.parseInt(reordervalue) + "   order by p.partno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(hqlQuery).addScalar("partno").addScalar("vendorpartno")
				.addScalar("partdescription").addScalar("year").addScalar("capa").addScalar("makemodelname")
				.addScalar("manufacturername").addScalar("unitsinstock").addScalar("unitsonorder")
				.addScalar("reorderlevel").addScalar("price").addScalar("quantity").addScalar("totalprice")
				.setResultTransformer(Transformers.aliasToBean(LocalOrderItemsDetails.class));

		List<LocalOrderItemsDetails> results = query.list();
		List<LocalOrderItemsDetails> newresults = new LinkedList<LocalOrderItemsDetails>();
		for (LocalOrderItemsDetails vo : results) {
			if (set.add(vo.getPartno())) {
				newresults.add(vo);

			}
		}
		session.flush();
		session.clear();
		return newresults;

	}

	@Transactional
	public List<CategoryBySalesAnalysis> getVendorPrices(String analyticsfromdate, String analyticstodate,
			String subcategoryselectedcode, String factor, String stockvalue) {

		long start = System.currentTimeMillis();

		// LOGGER.info("start: " + start);

		Boolean processIntervals = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance(); // Get the current date
		String datetoday = sdf.format(cal.getTime());
		// // LOGGER.info("datetoday:" + datetoday);

		cal.add(Calendar.MONTH, -12);
		String date12Mback = sdf.format(cal.getTime());
		// // LOGGER.info("date12Mback:" + date12Mback);

		cal.add(Calendar.MONTH, 6);
		String date6Mback = sdf.format(cal.getTime());
		// // LOGGER.info("date6Mback:" + date6Mback);

		cal.add(Calendar.MONTH, 3);
		String date3Mback = sdf.format(cal.getTime());
		// // LOGGER.info("date3Mback:" + date3Mback);

		cal.add(Calendar.MONTH, 2);
		String date1Mback = sdf.format(cal.getTime());
		// // LOGGER.info("date1Mback:" + date1Mback);

		List<CategoryBySalesAnalysis> categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>();
		Map<String, CategoryBySalesAnalysis> categorycomparisonMap = new HashMap<String, CategoryBySalesAnalysis>();

		Session session = sessionFactory.getCurrentSession();
		Set<String> dupelist = new HashSet<String>();

		String sql = "";
		Query query = null;

		Query query2 = null;
		String sql2 = "";
		if (factor.equalsIgnoreCase("surcharge")) {
			sql2 = AnalyticsUtils.VENDORITEMS_SURCHARGE_LAST5_SQL;
		} else {
			sql2 = AnalyticsUtils.VENDORITEMS_CUBICFEET_LAST5_SQL;
		}

		if (subcategoryselectedcode.equalsIgnoreCase("ALL")) {

			sql = OrdersUtils.SALESBYCATEGORY_ALL;
			query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
					.setParameter("orderto", analyticstodate)).addScalar("partno").addScalar("partdescription")
							.addScalar("manufacturername").addScalar("makemodelname").addScalar("totalsold")
							.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
							.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent")
							.addScalar("ordertype")
							.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));
		} else {

			sql = OrdersUtils.SALESBYCATEGORY;
			query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
					.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategoryselectedcode))
							.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
							.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
							.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
							.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
							.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

		}

		long second = System.currentTimeMillis();

		// LOGGER.info("second: " + (second - start));

		List<CategoryBySalesAnalysis> subcategorysaleslistorig = query.list();

		List<CategoryBySalesAnalysis> subcategorysaleslist = new ArrayList<CategoryBySalesAnalysis>();

		for (CategoryBySalesAnalysis cs : subcategorysaleslistorig) {

			cs.setM1(0);
			cs.setM3(0);
			cs.setM6(0);
			cs.setM12(0);

			if (processIntervals) {

				int m1 = getSalesOfPartForGivenDuration(cs.getPartno(), date1Mback, datetoday);
				int m3 = getSalesOfPartForGivenDuration(cs.getPartno(), date3Mback, datetoday);
				int m6 = getSalesOfPartForGivenDuration(cs.getPartno(), date6Mback, datetoday);
				int m12 = getSalesOfPartForGivenDuration(cs.getPartno(), date12Mback, datetoday);
				cs.setM1(m1);
				cs.setM3(m3);
				cs.setM6(m6);
				cs.setM12(m12);
			}

			subcategorysaleslist.add(cs);

		}

		// Collections.sort(subcategorysaleslist);

		for (CategoryBySalesAnalysis categorycomparison : subcategorysaleslistorig) {

			if (dupelist.add(categorycomparison.getPartno())) {
				int j = 0;
				List<String> vendorpricelist = new LinkedList<String>();

				query2 = (session.createSQLQuery(sql2).setParameter("factor", factor.trim()).setParameter("partno",
						categorycomparison.getPartno()));

				List<String> vendorlist = new ArrayList<String>();

				vendorlist = query2.list();
				if ((vendorlist.size() > 0) && (j < 3)) {
					for (String vc : vendorlist) {
						if (vc != null) {
							vendorpricelist.add(j, vc.trim());
							j++;
						}
					}
					categorycomparison.setVendorpriceslist(vendorpricelist);
				}
				categorycomparisonMap.put(categorycomparison.getPartno(), categorycomparison);
			}
		}

		long thrid = System.currentTimeMillis();

		// LOGGER.info("thrid: " + (thrid - second));

		// getting interchangeno figures
		String sqlinterchange = "";
		Query queryinterchange = null;

		// getting interchangeno figures
		String sqlMainNo = "";
		Query queryMainNo = null;
		if (subcategoryselectedcode.equalsIgnoreCase("ALL")) {
			sqlinterchange = OrdersUtils.SALESBYCATEGORY_ALL_INTERCHANGE;

			queryinterchange = ((SQLQuery) session.createSQLQuery(sqlinterchange)
					.setParameter("orderfrom", analyticsfromdate).setParameter("orderto", analyticstodate))
							.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
							.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
							.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
							.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
							.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));
		} else {
			sqlinterchange = OrdersUtils.SALESBYCATEGORY_INTERCHANGE;
			queryinterchange = ((SQLQuery) session.createSQLQuery(sqlinterchange)
					.setParameter("subcategorycode", subcategoryselectedcode)
					.setParameter("orderfrom", analyticsfromdate).setParameter("orderto", analyticstodate))
							.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
							.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
							.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("sellingprice")
							.addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
							.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));
		}

		long fourth = System.currentTimeMillis();

		// LOGGER.info("fourth: " + (fourth - thrid));

		List<CategoryBySalesAnalysis> interchnagesoldlist = queryinterchange.list();

		for (CategoryBySalesAnalysis interchangehelper : interchnagesoldlist) {
			if (dupelist.add(interchangehelper.getPartno())) {

				// // LOGGER.info(interchangehelper.getPartno());
				String interchangepartno = interchangehelper.getPartno().trim();
				sqlMainNo = OrdersUtils.SALESBYCATEGORY_MAINNO;
				queryMainNo = ((SQLQuery) session.createSQLQuery(sqlMainNo).setParameter("partno", interchangepartno))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("totalsold", StandardBasicTypes.BIG_DECIMAL)
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel")
						.addScalar("sellingprice").addScalar("buyingprice").addScalar("percent").addScalar("ordertype")
						.setResultTransformer(Transformers.aliasToBean(CategoryBySalesAnalysis.class));

				LOGGER.info(interchangepartno);
				CategoryBySalesAnalysis mainpart = (CategoryBySalesAnalysis) queryMainNo.list().get(0);
				mainpart.setTotalsold(interchangehelper.getTotalsold());
				mainpart.setM1(0);
				mainpart.setM3(0);
				mainpart.setM6(0);
				mainpart.setM12(0);

				if (processIntervals) {
					int m1 = getSalesOfPartForGivenDuration(mainpart.getPartno(), date1Mback, datetoday);
					int m3 = getSalesOfPartForGivenDuration(mainpart.getPartno(), date3Mback, datetoday);
					int m6 = getSalesOfPartForGivenDuration(mainpart.getPartno(), date6Mback, datetoday);
					int m12 = getSalesOfPartForGivenDuration(mainpart.getPartno(), date12Mback, datetoday);

					mainpart.setM1(m1);
					mainpart.setM3(m3);
					mainpart.setM6(m6);
					mainpart.setM12(m12);

				}
				int j = 0;
				List<String> vendorpricelist = new LinkedList<String>();

				query2 = (session.createSQLQuery(sql2).setParameter("factor", factor.trim()).setParameter("partno",
						interchangepartno));

				List<String> vendorlist = query2.list();

				if (vendorlist.size() > 0) {
					for (String vc : vendorlist) {

						if (vc != null) {
							vendorpricelist.add(j, vc.trim());
							j++;
						}
					}
					mainpart.setVendorpriceslist(vendorpricelist);
				}
				categorycomparisonMap.put(interchangepartno, mainpart);
			} else {
				BigDecimal interchangesold = new BigDecimal(interchangehelper.getTotalsold().intValue());
				String key = interchangehelper.getPartno();
				BigDecimal addtotalssold = categorycomparisonMap.get(key).getTotalsold().add(interchangesold);
				categorycomparisonMap.get(key).setTotalsold(addtotalssold);
			}
		}

		long fifth = System.currentTimeMillis();

		// LOGGER.info("fifth: " + (fifth - fourth));

		session.flush();
		session.clear();
		categorycomparisonList = new ArrayList<CategoryBySalesAnalysis>(categorycomparisonMap.values());
		// Collections.sort(categorycomparisonList);
		return categorycomparisonList;
	}

	@Transactional
	public Map<String, VendorPriceComparison> getVendorpricesBySubcategoryCubicFeet(String analyticsfromdate,
			String analyticstodate, String subcategorycode, String factor, String stockvalue) {

		Map<String, VendorPriceComparison> vendorcomparisonMap = new LinkedHashMap<String, VendorPriceComparison>();
		Set<String> dupelist = new HashSet<String>();
		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;
		sql = AnalyticsUtils.VENDORPRICECOMPARISON_SQL;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategorycode)
				.setParameter("stockvalue", Integer.parseInt(stockvalue))).addScalar("partno")
						.addScalar("partdescription").addScalar("manufacturername").addScalar("makemodelname")
						.addScalar("totalsold").addScalar("unitsinstock").addScalar("unitsonorder")
						.addScalar("reorderlevel")
						.setResultTransformer(Transformers.aliasToBean(VendorPriceComparison.class));

		List<VendorPriceComparison> vendorcomparisonlist = query.list();

		for (VendorPriceComparison vendorcomparison : vendorcomparisonlist) {
			int j = 0;
			List<String> vendorpricelist = new LinkedList<String>();
			if (dupelist.add(vendorcomparison.getPartno())) {

			} else {
				VendorPriceComparison oldvendorcomparison = vendorcomparisonMap.get(vendorcomparison.getPartno());
				vendorcomparisonMap.remove(oldvendorcomparison.getPartno());

				BigDecimal totalsoldsum = vendorcomparison.getTotalsold().add(oldvendorcomparison.getTotalsold());
				vendorcomparison.setTotalsold(totalsoldsum);

			}

			Query query2 = null;
			String sql2 = AnalyticsUtils.VENDORITEMS_CUBICFEET_LAST5_SQL;
			query2 = query = (session.createSQLQuery(sql2).setParameter("factor", factor.trim()).setParameter("partno",
					vendorcomparison.getPartno()));

			List<String> vendorlist = query2.list();
			if (vendorlist.size() > 0) {
				for (String vc : vendorlist) {
					vendorpricelist.add(j, vc.trim());
					j++;
				}
				vendorcomparison.setVendorpriceslist(vendorpricelist);
			}
			vendorcomparisonMap.put(vendorcomparison.getPartno(), vendorcomparison);
		}

		session.flush();
		session.clear();

		return vendorcomparisonMap;

	}

	@Transactional
	public Map<String, VendorPriceComparison> getVendorpricesBySubcategoryCubicFeetALL(String analyticsfromdate,
			String analyticstodate, String factor, String stockvalue) {

		Map<String, VendorPriceComparison> vendorcomparisonMap = new LinkedHashMap<String, VendorPriceComparison>();
		Set<String> dupelist = new HashSet<String>();
		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;
		sql = AnalyticsUtils.VENDORPRICECOMPARISON_SQL;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("stockvalue", Integer.parseInt(stockvalue)))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel")
						.setResultTransformer(Transformers.aliasToBean(VendorPriceComparison.class));

		List<VendorPriceComparison> vendorcomparisonlist = query.list();

		for (VendorPriceComparison vendorcomparison : vendorcomparisonlist) {
			int j = 0;
			List<String> vendorpricelist = new LinkedList<String>();
			if (dupelist.add(vendorcomparison.getPartno())) {

			} else {
				VendorPriceComparison oldvendorcomparison = vendorcomparisonMap.get(vendorcomparison.getPartno());
				vendorcomparisonMap.remove(oldvendorcomparison.getPartno());

				BigDecimal totalsoldsum = vendorcomparison.getTotalsold().add(oldvendorcomparison.getTotalsold());
				vendorcomparison.setTotalsold(totalsoldsum);

			}

			Query query2 = null;
			String sql2 = AnalyticsUtils.VENDORITEMS_CUBICFEET_LAST5_SQL;
			query2 = query = (session.createSQLQuery(sql2).setParameter("factor", factor.trim()).setParameter("partno",
					vendorcomparison.getPartno()));

			List<String> vendorlist = query2.list();
			if (vendorlist.size() > 0) {
				for (String vc : vendorlist) {
					vendorpricelist.add(j, vc.trim());
					j++;
				}
				vendorcomparison.setVendorpriceslist(vendorpricelist);
			}
			vendorcomparisonMap.put(vendorcomparison.getPartno(), vendorcomparison);
		}

		session.flush();
		session.clear();

		return vendorcomparisonMap;

	}

	@Transactional
	public Map<String, VendorPriceComparison> getVendorpricesBySubcategorySurcharge(String analyticsfromdate,
			String analyticstodate, String subcategorycode, String factor, String stockvalue) {

		Map<String, VendorPriceComparison> vendorcomparisonMap = new LinkedHashMap<String, VendorPriceComparison>();
		Set<String> dupelist = new HashSet<String>();
		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;
		sql = AnalyticsUtils.VENDORPRICECOMPARISON_SQL;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("subcategorycode", subcategorycode)
				.setParameter("stockvalue", Integer.parseInt(stockvalue))).addScalar("partno")
						.addScalar("partdescription").addScalar("manufacturername").addScalar("makemodelname")
						.addScalar("totalsold").addScalar("unitsinstock").addScalar("unitsonorder")
						.addScalar("reorderlevel")
						.setResultTransformer(Transformers.aliasToBean(VendorPriceComparison.class));

		List<VendorPriceComparison> vendorcomparisonlist = query.list();

		for (VendorPriceComparison vendorcomparison : vendorcomparisonlist) {
			int j = 0;
			List<String> vendorpricelist = new LinkedList<String>();
			if (dupelist.add(vendorcomparison.getPartno())) {

			} else {
				VendorPriceComparison oldvendorcomparison = vendorcomparisonMap.get(vendorcomparison.getPartno());
				vendorcomparisonMap.remove(oldvendorcomparison.getPartno());

				BigDecimal totalsoldsum = vendorcomparison.getTotalsold().add(oldvendorcomparison.getTotalsold());
				vendorcomparison.setTotalsold(totalsoldsum);

			}

			Query query2 = null;
			String sql2 = AnalyticsUtils.VENDORITEMS_SURCHARGE_LAST5_SQL;
			query2 = query = (session.createSQLQuery(sql2).setParameter("factor", factor.trim()).setParameter("partno",
					vendorcomparison.getPartno()));

			List<String> vendorlist = query2.list();
			if (vendorlist.size() > 0) {
				for (String vc : vendorlist) {
					vendorpricelist.add(j, vc.trim());
					j++;
				}
				vendorcomparison.setVendorpriceslist(vendorpricelist);
			}
			vendorcomparisonMap.put(vendorcomparison.getPartno(), vendorcomparison);
		}

		session.flush();
		session.clear();

		return vendorcomparisonMap;

	}

	@Transactional
	public Map<String, VendorPriceComparison> getVendorpricesBySubcategorySurchargeALL(String analyticsfromdate,
			String analyticstodate, String factor, String stockvalue) {

		Map<String, VendorPriceComparison> vendorcomparisonMap = new LinkedHashMap<String, VendorPriceComparison>();
		Set<String> dupelist = new HashSet<String>();
		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = null;
		sql = AnalyticsUtils.VENDORPRICECOMPARISON_ALL_SQL;
		query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", analyticsfromdate)
				.setParameter("orderto", analyticstodate).setParameter("stockvalue", Integer.parseInt(stockvalue)))
						.addScalar("partno").addScalar("partdescription").addScalar("manufacturername")
						.addScalar("makemodelname").addScalar("totalsold").addScalar("unitsinstock")
						.addScalar("unitsonorder").addScalar("reorderlevel")
						.setResultTransformer(Transformers.aliasToBean(VendorPriceComparison.class));

		List<VendorPriceComparison> vendorcomparisonlist = query.list();

		for (VendorPriceComparison vendorcomparison : vendorcomparisonlist) {
			int j = 0;
			List<String> vendorpricelist = new LinkedList<String>();
			if (dupelist.add(vendorcomparison.getPartno())) {

			} else {
				VendorPriceComparison oldvendorcomparison = vendorcomparisonMap.get(vendorcomparison.getPartno());
				vendorcomparisonMap.remove(oldvendorcomparison.getPartno());

				BigDecimal totalsoldsum = vendorcomparison.getTotalsold().add(oldvendorcomparison.getTotalsold());
				vendorcomparison.setTotalsold(totalsoldsum);

			}

			Query query2 = null;
			String sql2 = AnalyticsUtils.VENDORITEMS_SURCHARGE_LAST5_SQL;
			query2 = query = (session.createSQLQuery(sql2).setParameter("factor", factor.trim()).setParameter("partno",
					vendorcomparison.getPartno()));

			List<String> vendorlist = query2.list();
			if (vendorlist.size() > 0) {
				for (String vc : vendorlist) {
					vendorpricelist.add(j, vc.trim());
					j++;
				}
				vendorcomparison.setVendorpriceslist(vendorpricelist);
			}
			vendorcomparisonMap.put(vendorcomparison.getPartno(), vendorcomparison);
		}

		session.flush();
		session.clear();

		return vendorcomparisonMap;

	}

	@Transactional
	public List<WeekSubCategoryReport> getWeeklySalesSubcategory(String fromdate, String todate) {

		Session session = sessionFactory.getCurrentSession();
		String sql = AnalyticsUtils.WEEK_SUBCATEGORY;
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
				.setParameter("orderto", todate)).addScalar("yr").addScalar("mnth").addScalar("wk")
						.addScalar("subcategory").addScalar("cnt").addScalar("ourprice").addScalar("salesprice")
						.addScalar("margin").addScalar("prcnt").setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(WeekSubCategoryReport.class));

		List<WeekSubCategoryReport> salescategorylist = query.list();

		session.flush();
		session.clear();

		return salescategorylist;
	}

	@Transactional
	public List<WeekCustomerReport> getWeekSalesCustomer(String fromdate, String todate) {

		Session session = sessionFactory.getCurrentSession();
		String sql = AnalyticsUtils.WEEK_CUSTOMER;
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
				.setParameter("orderto", todate)).addScalar("yr").addScalar("mnth").addScalar("wk")
						.addScalar("companyname").addScalar("cnt").addScalar("ourprice").addScalar("salesprice")
						.addScalar("margin").addScalar("prcnt").setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(WeekCustomerReport.class));

		List<WeekCustomerReport> salescustomerlist = query.list();

		session.flush();
		session.clear();

		return salescustomerlist;
	}

	@Transactional
	public List<LocalOrderItemsDetails> getWestinOrderDetails(String orderselect, String stockvalue, String ordervalue,
			String reordervalue) {

		HashSet<String> set = new HashSet<String>();
		String hqlQuery = AnalyticsUtils.LOCALORDER_WESTIN + " AND UNITSINSTOCK <= " + Integer.parseInt(stockvalue)
				+ " AND UNITSONORDER <= " + Integer.parseInt(ordervalue) + " AND REORDERLEVEL >= "
				+ Integer.parseInt(reordervalue) + "   order by p.partno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(hqlQuery).addScalar("partno").addScalar("vendorpartno")
				.addScalar("partdescription").addScalar("year").addScalar("capa").addScalar("makemodelname")
				.addScalar("manufacturername").addScalar("unitsinstock").addScalar("unitsonorder")
				.addScalar("reorderlevel").addScalar("price").addScalar("quantity").addScalar("totalprice")
				.setResultTransformer(Transformers.aliasToBean(LocalOrderItemsDetails.class));

		List<LocalOrderItemsDetails> results = query.list();
		List<LocalOrderItemsDetails> newresults = new LinkedList<LocalOrderItemsDetails>();
		for (LocalOrderItemsDetails vo : results) {
			if (set.add(vo.getPartno())) {
				newresults.add(vo);

			}
		}
		session.flush();
		session.clear();
		return newresults;

	}

	@Transactional
	public List<YearCustomerReport> getYearSalesCustomer(String fromdate, String todate) {

		Session session = sessionFactory.getCurrentSession();
		String sql = AnalyticsUtils.YEAR_CUSTOMER;
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
				.setParameter("orderto", todate)).addScalar("yr").addScalar("companyname").addScalar("cnt")
						.addScalar("ourprice").addScalar("salesprice").addScalar("margin").addScalar("prcnt")
						.setMaxResults(300000).setResultTransformer(Transformers.aliasToBean(YearCustomerReport.class));

		List<YearCustomerReport> salescustomerlist = query.list();

		session.flush();
		session.clear();

		return salescustomerlist;
	}

	@Transactional
	public List<YearSubCategoryReport> getYearSalesSubcategory(String fromdate, String todate) {

		Session session = sessionFactory.getCurrentSession();
		String sql = AnalyticsUtils.YEAR_SUBCATEGORY;
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("orderfrom", fromdate)
				.setParameter("orderto", todate)).addScalar("yr").addScalar("subcategory").addScalar("cnt")
						.addScalar("ourprice").addScalar("salesprice").addScalar("margin").addScalar("prcnt")
						.setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(YearSubCategoryReport.class));

		List<YearSubCategoryReport> salescategorylist = query.list();

		session.flush();
		session.clear();

		return salescategorylist;
	}

	@Transactional
	public Boolean haveStock(String partno, Integer checkval) {

		Boolean haveStock = false;

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts  WHERE parts.partno =:partno";
		Query query = session.createQuery(hSql);

		query.setParameter("partno", partno);

		List<Parts> parts = query.list();

		session.flush();
		session.clear();

		if (parts != null) {
			if (parts.size() > 0) {
				if (parts.get(0).getUnitsinstock() > checkval) {
					haveStock = true;
				}
			} else {
				haveStock = false;
			}
		} else {
			haveStock = false;
		}

		return haveStock;
	}

	@Transactional
	private boolean orderExists(Integer orderno) {

		String hqlQuery = "From VendorOrder vendororder  where vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);
		List<VendorOrder> vendororders = query.list();
		session.flush();
		session.clear();

		if (vendororders != null) {
			if (vendororders.size() > 0) {
				return true;
			}
		}

		return false;

	}

	@Transactional
	public String runActualPriceUpdate() {

		String feedback = "\n";
		Session session = sessionFactory.getCurrentSession();
		String missingSQL = "FROM InvoiceDetails invoicedetails where invoicedetails.actualprice <= 0  and invoicedetails.partnumber not like 'ZZ%' and invoicedetails.partnumber not like 'XX%' order by invoicedetails.partnumber";
		String findpriceSql1 = "select actualprice FROM InvoiceDetails  where partnumber=:partnumber and actualprice > 0 order by invoicenumber desc";
		String findpriceSql2 = "select actualprice FROM InvoiceDetailsArch  where partnumber=:partnumber and actualprice > 0  order by invoicenumber desc";
		String findpriceSql3 = "select actualprice from Parts where partno =:partnumber";
		String findpriceSql4 = "select sellingrate from VendorItems where partno =:partnumber and sellingrate > 0";
		String findpriceSql5 = "Select actualprice From Parts where interchangeno =:partnumber";
		Query query = session.createQuery(missingSQL);

		List<InvoiceDetails> invoicedetailslist = query.list();

		for (InvoiceDetails invoicedetails : invoicedetailslist) {
			BigDecimal actualprice = new BigDecimal("0.00");

			query = session.createQuery(findpriceSql1);
			query.setParameter("partnumber", invoicedetails.getPartnumber());
			query.setMaxResults(1);
			actualprice = (BigDecimal) query.uniqueResult();

			if ((actualprice != null) && (actualprice.compareTo(BigDecimal.ZERO) == 1)) {
				invoicedetails.setActualprice(actualprice);
				session.save(invoicedetails);
			} else {
				query = session.createQuery(findpriceSql2);
				query.setParameter("partnumber", invoicedetails.getPartnumber());
				query.setMaxResults(1);
				actualprice = (BigDecimal) query.uniqueResult();

				if ((actualprice != null) && (actualprice.compareTo(BigDecimal.ZERO) == 1)) {
					invoicedetails.setActualprice(actualprice);
					session.save(invoicedetails);
				} else {
					query = session.createQuery(findpriceSql3);
					query.setParameter("partnumber", invoicedetails.getPartnumber());
					query.setMaxResults(1);
					actualprice = (BigDecimal) query.uniqueResult();

					if ((actualprice != null) && (actualprice.compareTo(BigDecimal.ZERO) == 1)) {
						invoicedetails.setActualprice(actualprice);
						session.save(invoicedetails);
					} else {
						query = session.createQuery(findpriceSql4);
						query.setParameter("partnumber", invoicedetails.getPartnumber());
						query.setMaxResults(1);
						actualprice = (BigDecimal) query.uniqueResult();

						if ((actualprice != null) && (actualprice.compareTo(BigDecimal.ZERO) == 1)) {
							invoicedetails.setActualprice(actualprice);
							session.save(invoicedetails);
						} else {
							query = session.createQuery(findpriceSql5);
							query.setParameter("partnumber", invoicedetails.getPartnumber());
							query.setMaxResults(1);
							actualprice = (BigDecimal) query.uniqueResult();

							if ((actualprice != null) && (actualprice.compareTo(BigDecimal.ZERO) == 1)) {
								invoicedetails.setActualprice(actualprice);
								session.save(invoicedetails);
							} else {

								feedback = feedback + invoicedetails.getPartnumber() + ": 0.00 ,";
							}
						}
					}
				}
			}
		}

		session.flush();
		session.clear();

		return feedback;
	}

}
