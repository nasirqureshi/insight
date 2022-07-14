package com.bvas.insight.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
// import org.magicwerk.brownies.collections.GapList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.InvCategoryBySalesAnalysis;
import com.bvas.insight.data.InventoryPartsTransfer;
import com.bvas.insight.data.InventoryTransfer;
import com.bvas.insight.data.PaymentMaintenance;
import com.bvas.insight.data.Requiredquanity;
import com.bvas.insight.data.SaleByRouteData;
import com.bvas.insight.data.SaleByRouteMonthly;
import com.bvas.insight.data.ScanOrderDetails;
import com.bvas.insight.data.StockData;
import com.bvas.insight.entity.Address;
import com.bvas.insight.entity.AppliedAmounts;
import com.bvas.insight.entity.BouncedChecks;
import com.bvas.insight.entity.Customer;
import com.bvas.insight.entity.Invoice;
import com.bvas.insight.entity.LocalOrders;
import com.bvas.insight.entity.LocalVendors;
import com.bvas.insight.entity.Writeoff;
import com.bvas.insight.jdbc.ChStocksDAOImpl;
import com.bvas.insight.jdbc.GrStocksDAOImpl;
import com.bvas.insight.jdbc.MpStocksDAOImpl;
import com.bvas.insight.jdbc.StockDataMapper;
import com.bvas.insight.utilities.AccountUtils;
import com.bvas.insight.utilities.InsightUtils;

@Service
@Transactional
public class AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	public static int calculateTransferQty(int requiredQty, int availableQty) {
		int transferQty = 0;
		if (availableQty > 0) {
			if (requiredQty > availableQty)
				transferQty = availableQty;
			else
				transferQty = requiredQty;
		}

		return transferQty;
	}

	public static boolean checkForAllZeors(ArrayList<String> qtyList) {
		for (String num : qtyList) {

			if (!num.equals("0"))
				return true;
		}
		return false;
	}

	public static int checkIfQtyIsBelowAvailableStock(String partNum, List<ScanOrderDetails> partstransferdetailslist) {
		int qty = 0;
		for (ScanOrderDetails scanOrderDetails : partstransferdetailslist) {
			if (scanOrderDetails.getPartno().equals(partNum))
				qty = scanOrderDetails.chunitsinstock;
		}
		return qty;
	}

	public static String[] createOrders(String RequestOrderMP, String RequestOrderGR, String Base) {
		String[] orderNumbers = new String[3];

		ArrayList<String> listMP = new ArrayList<String>(Arrays.asList(RequestOrderMP.trim().split(",")));
		ArrayList<String> listGR = new ArrayList<String>(Arrays.asList(RequestOrderGR.trim().split(",")));

		if (listMP.size() > 0 && checkForAllZeors(listMP)) {
			orderNumbers[1] = getOrderNumber(Base, "MP");
		} else {
			orderNumbers[1] = "0";
		}

		if (listGR.size() > 0 && checkForAllZeors(listGR)) {
			orderNumbers[2] = getOrderNumber(Base, "GR");
		} else {
			orderNumbers[2] = "0";
		}

		return orderNumbers;
	}

	public static int DisplayDefaultValueForRequestOrder(int safetyQty, int inStockQty, int baseAvailable,
			String branch) {

		int orderQty = 0;
		if (inStockQty < 0)
			inStockQty = 0;

		int diffSafteyAndinStock = safetyQty - inStockQty;
		int twentyFivePercent = (int) (0.25 * baseAvailable);

		if (diffSafteyAndinStock > 0) {

			if (diffSafteyAndinStock < twentyFivePercent)
				orderQty = diffSafteyAndinStock;
			else {

				orderQty = twentyFivePercent;
			}
		}

		return orderQty;
	}

	public static int getNextInteger(String inputfile) {
		int k = 0;
		try {
			Scanner scanner = new Scanner(new File(inputfile));

			if (scanner.hasNextInt()) {
				k = scanner.nextInt();
			}
			k++;

			Writer wr = new FileWriter(inputfile);
			wr.write(new Integer(k).toString());
			wr.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	public static String getOrderNumber(String source, String destination) {
		String[] orderValues = { "", "CH", "GR", "MP", "" };
		String orderNumber = "";
		for (int i = 0; i < orderValues.length; i++) {
			if (source.equals(orderValues[i])) {
				orderNumber = "8" + i;
			}
		}
		for (int i = 0; i < orderValues.length; i++) {
			if (destination.equals(orderValues[i])) {
				orderNumber = orderNumber + i;
			}
		}
		int getUniqueInt = getNextInteger("C:\\repository\\insight\\UniqueInteger.txt");
		orderNumber = orderNumber + getUniqueInt;
		return orderNumber;
	}

	public static Map getPartTransferMap(String partno, String RequestOrderMP, String RequestOrderGR,
			List<ScanOrderDetails> partstransferdetailslist) {

		List<String> listMP = new ArrayList<String>(Arrays.asList(RequestOrderMP.split(",")));
		List<String> listGR = new ArrayList<String>(Arrays.asList(RequestOrderGR.split(",")));
		List<String> listPartno = new ArrayList<String>(Arrays.asList(partno.split(",")));

		List<String> listForMap = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		int i = 0;

		int listMPSize = listMP.size();
		int listGRSize = listGR.size();

		for (String parts : listPartno) {

			if (listGRSize-- > 0)
				listForMap.add(Integer.parseInt(listGR.get(i)) < checkIfQtyIsBelowAvailableStock(parts,
						partstransferdetailslist) ? listGR.get(i)
								: String.valueOf(checkIfQtyIsBelowAvailableStock(parts, partstransferdetailslist)));
			else
				listForMap.add("");

			if (listMPSize-- > 0)
				listForMap.add(Integer.parseInt(listMP.get(i)) < checkIfQtyIsBelowAvailableStock(parts,
						partstransferdetailslist) ? listMP.get(i)
								: String.valueOf(checkIfQtyIsBelowAvailableStock(parts, partstransferdetailslist)));
			// listForMap.add(listMP.get(i));
			else
				listForMap.add("");

			map.put(parts, listForMap);
			i++;

			listForMap = new ArrayList<String>();

		}
		return map;
	}

	public static String getTodayorbeforeDateinFormat(String day) {
		Date today = Calendar.getInstance().getTime();

		Date currentDate = new Date();

		long milliseconds = (long) 365 * 24 * 60 * 60 * 1000;
		Date oneYearBefore = new Date(currentDate.getTime() - milliseconds);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		String todayORbefore = "";
		if (day.equals("today"))
			todayORbefore = formatter.format(today);
		else
			todayORbefore = formatter.format(oneYearBefore);

		return todayORbefore;
	}

	/*
	 * public static boolean insertInVendorOrder(int supplierId, int orderNo, String
	 * db) {
	 * 
	 * boolean vendorInsert = false; try {
	 * 
	 * BvasConFatory bvasConFactory = new BvasConFatory(db); java.sql.Connection
	 * conn = bvasConFactory.getConnection(); Statement stmtX =
	 * conn.createStatement();
	 * 
	 * vendorInsert = stmtX.execute(
	 * "Insert Into VendorOrder (OrderNo, SupplierId, OrderDate, DeliveredDate, OrderStatus, OrderTotal, UpdatedInventory, UpdatedPrices) values ("
	 * + orderNo + ", " + supplierId + ", '" +
	 * DateUtils.convertUSToMySQLFormat(DateUtils.getNewUSDate()) + "', '" +
	 * DateUtils.convertUSToMySQLFormat(DateUtils.getNewUSDate()) +
	 * "', 'New', 0.0, 'N', 'N' ) "); } catch (SQLException e) { //
	 * LOGGER.info("Exception---" + e);
	 * 
	 * return false; }
	 * 
	 * return true; }
	 * 
	 * public static boolean insertInVendorOrderedItems(Map<String, List<String>>
	 * map, int supplierId, int orderNo, String db) { int supplierIdInsert =
	 * supplierId; Set<String> keys = map.keySet(); String vendPartNo = ""; // order
	 * number has 3rd digit for transfer location String orderNumLocation =
	 * String.valueOf(orderNo).substring(2, 3); int orderIndex =
	 * Integer.valueOf(orderNumLocation);
	 * 
	 * // Order number has these num values. CH -1, GR -2, MP - 3, // map idex has 3
	 * values 0 - GR, 1 - MP it is orderIndex - // 1 which is used later. if
	 * (orderIndex == 2) orderIndex = 1; // GR if (orderIndex == 3) orderIndex = 2;
	 * // MP
	 * 
	 * int noOrder = 0; try { BvasConFatory bvasConFactory = new BvasConFatory(db);
	 * java.sql.Connection conn = bvasConFactory.getConnection(); ResultSet
	 * rsVendorItems = null; boolean vendorInsert; Statement stmtX =
	 * conn.createStatement(); Statement stmtY = conn.createStatement(); for (String
	 * key : keys) { List<String> mapValues = map.get(key); rsVendorItems =
	 * stmtY.executeQuery("Select * from VendorItems Where SupplierId=" +
	 * supplierIdInsert + " and PartNo = '" + key + "'");
	 * 
	 * if (rsVendorItems.next()) { vendPartNo =
	 * rsVendorItems.getString("VendorPartNo"); }
	 * 
	 * if (vendPartNo == null) { vendPartNo = ""; }
	 * 
	 * String qtyValue = mapValues.get(orderIndex - 1).toString();
	 * 
	 * int qty = 0;
	 * 
	 * if (qtyValue.trim().length() > 0) { qty = Integer.valueOf(qtyValue); }
	 * 
	 * if (qty > 0) { noOrder++; float price = 0;
	 * 
	 * vendorInsert = stmtY.execute(
	 * "INSERT INTO VendorOrderedItems (OrderNo, PartNo, VendorPartNo, Quantity, Price, NoOrder) VALUES ('"
	 * + orderNo + "', '" + key + "', '" + vendPartNo + "', " + qty + ", " + price +
	 * ", " + noOrder + ")"); } } stmtX.close(); stmtY.close(); conn.close(); }
	 * 
	 * catch (Exception e) { // LOGGER.info("Exception---" + e); } return true; }
	 * 
	 */

	public static void resetRequiredquantity(String location, List<Requiredquanity> requiredquanititylist) {

		if (location.equals("CHS")) {
			for (Requiredquanity requiredquantity : requiredquanititylist) {
				requiredquantity.setGrrequiredquanity(0);
				requiredquantity.setMprequiredquanity(0);
				requiredquantity.setNyrequiredquanity(0);
			}
		}

		else if (location.equals("MPS")) {
			for (Requiredquanity requiredquantity : requiredquanititylist) {
				requiredquantity.setGrrequiredquanity(0);
				requiredquantity.setChrequiredquanity(0);
				requiredquantity.setNyrequiredquanity(0);
			}
		}

		else if (location.equals("GRS")) {
			for (Requiredquanity requiredquantity : requiredquanititylist) {
				requiredquantity.setChrequiredquanity(0);
				requiredquantity.setMprequiredquanity(0);
				requiredquantity.setNyrequiredquanity(0);
			}
		}

	}

	private DataSource chdataSource;

	@Autowired
	@Qualifier("chpartsdao")
	public ChStocksDAOImpl chpartsdao;

	private DataSource grdataSource;

	@Autowired
	@Qualifier("grpartsdao")
	public GrStocksDAOImpl grpartsdao;

	private DataSource mpdataSource;

	@Autowired
	@Qualifier("mppartsdao")
	public MpStocksDAOImpl mppartsdao;

	@Autowired
	private SessionFactory sessionFactory;

	public String addBCPayment(PaymentMaintenance entity) {

		String err = "";
		Session session = sessionFactory.getCurrentSession();
		AppliedAmounts appAmount = new AppliedAmounts();
		appAmount.setAppliedAmount(entity.getPayingAmount());
		appAmount.setInvoiceNumber(entity.getInvoiceNumber());
		appAmount.setAppliedDate(InsightUtils.getCurrentSQLDate());
		appAmount.setPaymentType(entity.getPaymentType());
		appAmount.setUserName(entity.getUserName());
		appAmount.setPaymentTime((new java.util.Date(System.currentTimeMillis()).toString()).substring(11, 16));
		session.save(appAmount);

		BouncedChecks bcheck = getBouncedChecksById(entity.getInvoiceNumber().substring(2));
		bcheck.setPaidamount(bcheck.getPaidamount().add(entity.getPayingAmount()));
		bcheck.setBalance(bcheck.getBalance().subtract(entity.getPayingAmount()));
		bcheck.setCheckdate(InsightUtils.getCurrentSQLDate());
		bcheck.setEntereddate(InsightUtils.getCurrentSQLDate());
		session.update(bcheck);
		session.flush();
		session.clear();
		err = "Invoice updated successfully.";
		return err;
	}

	public String addBouncedCheck(BouncedChecks entity) {

		if (entity.getCustomerid() == null || entity.getCustomerid().equals("")) {
			return "Please enter Customer Id.";
		}
		if (entity.getCheckno() == null || entity.getCheckno().equals("")) {
			return "Please enter Check No.";
		}
		if (availableBC(entity) > 0) {
			return "Please enter valid record,(Record Already exists)";
		}
		if (entity.getCheckdate() == null) {
			return "Please enter check date.";
		}
		BouncedChecks bc = new BouncedChecks();
		bc.setCheckid(getMaxBCCheckId() + 1);
		bc.setCustomerid(entity.getCustomerid());
		bc.setBouncedamount(entity.getBouncedamount());
		bc.setCheckno(entity.getCheckno() == null ? "" : entity.getCheckno());
		bc.setCheckdate(entity.getCheckdate());
		bc.setIscleared(entity.getIscleared() == null ? "N" : entity.getIscleared());
		bc.setEntereddate(InsightUtils.getCurrentSQLDate());
		bc.setPaidamount(entity.getPaidamount());
		bc.setBalance(entity.getBalance() == null ? (new BigDecimal(0)) : entity.getBalance());
		if (bc.getPaidamount().compareTo(BigDecimal.ZERO) != 0) {
			bc.setBalance(bc.getBouncedamount().add(new BigDecimal(25.0)).subtract(bc.getPaidamount()));
		} else {
			bc.setBalance(bc.getBouncedamount().add(new BigDecimal(25.0)));
		}
		Customer cust = getCustomerById(entity.getCustomerid());
		Session session = sessionFactory.getCurrentSession();
		session.save(bc);
		cust.setCreditbalance(cust.getCreditbalance().add(bc.getBalance()));
		session.update(cust);
		session.flush();
		session.clear();
		entity.setCheckid(bc.getCheckid());
		return "THIS CHECK ADDED SUCCESSFULLY --- New Check Id is : BC" + bc.getCheckid();
	}

	public String addInvPayment(PaymentMaintenance entity) {

		String err = "";
		Integer invno = Integer.parseInt(entity.getInvoiceNumber());

		Invoice inv = getInvoiceById(invno);
		String status = inv.getStatus().toString();
		if (!status.trim().equals("C") && !status.trim().equals("W")) {

			Session session = sessionFactory.getCurrentSession();

			AppliedAmounts appAmount = new AppliedAmounts();
			appAmount.setAppliedAmount(entity.getPayingAmount());
			appAmount.setInvoiceNumber(inv.getInvoicenumber() + "");
			appAmount.setAppliedDate(InsightUtils.getCurrentSQLDate());
			appAmount.setPaymentType(entity.getPaymentType());
			appAmount.setUserName(entity.getUserName());
			appAmount.setPaymentTime((new java.util.Date(System.currentTimeMillis()).toString()).substring(11, 16));
			session.save(appAmount);

			inv.setAppliedamount(inv.getAppliedamount().add(entity.getPayingAmount()));
			inv.setBalance(inv.getBalance().subtract(entity.getPayingAmount()));
			inv.setStatus("P");

			session.update(inv);

			Customer customer = getCustomerById(inv.getCustomerid());
			customer.setCreditbalance(customer.getCreditbalance().subtract(entity.getPayingAmount()));
			session.update(customer);
			err = "Invoice updated successfully.";
			session.flush();
			session.clear();
		} else {
			err = "This Invoice cannot be changed - Status is Closed";
		}
		return err;
	}

	public String addPayment(PaymentMaintenance entity) {

		String err = "";

		if (!isPaymentExist(entity)) {
			if (entity.getInvoiceNumber().startsWith("bc") || entity.getInvoiceNumber().startsWith("BC")) {
				err = addBCPayment(entity);
			} else {
				err = addInvPayment(entity);
			}
		}
		return err;
	}

	public String addWriteoff(Writeoff entity) {

		if (entity != null && entity.getInvoiceNo() != null) {
			Session session = sessionFactory.getCurrentSession();
			Invoice invoice = getInvoiceById(entity.getInvoiceNo());
			if (invoice == null) {
				return "This Invoice # is Not Valid";
			}
			if (invoice.getBalance().compareTo(new BigDecimal(0)) == 0) {
				return "ALREADY PAID INVOICE";
			}

			Criteria cr = session.createCriteria(Writeoff.class);
			Criterion invoiceCond = Restrictions.eq("invoiceNo", entity.getInvoiceNo());
			cr.add(invoiceCond);
			if (!cr.list().isEmpty()) {
				return "ALREADY WRITTEN OFF";
			}
			Writeoff wo = new Writeoff();
			wo.setInvoiceNo(entity.getInvoiceNo());
			wo.setNotes(entity.getNotes());
			if (entity.getWriteOffDate() == null) {
				wo.setWriteOffDate(InsightUtils.getCurrentSQLDate());
			} else {
				wo.setWriteOffDate(entity.getWriteOffDate());
			}
			session.save(wo);
			invoice.setStatus("W");
			session.update(invoice);
			session.flush();
			session.clear();
			return "Invoice No " + entity.getInvoiceNo() + " successfully write off.";
		}
		return "";
	}

	public int availableBC(BouncedChecks entity) {

		String SQL = "SELECT count(1) as count FROM bouncedchecks WHERE CustomerId=:CustomerId and (CheckNo=:CheckNo OR CheckDate=:CheckDate);  ";
		Session session = sessionFactory.getCurrentSession();

		Query query = (session.createSQLQuery(SQL).setParameter("CustomerId", entity.getCustomerid())
				.setParameter("CheckNo", entity.getCheckno()).setParameter("CheckDate", entity.getCheckdate()));
		Integer count = ((Number) query.uniqueResult()).intValue();
		session.flush();
		session.clear();
		return count;
	}

	@Transactional
	public int closeAllInvoices() {

		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY = "Update Invoice Set Status='C' where Balance=0 and Status!='C' and Status!='W' ";
		int updatedEntities = session.createSQLQuery(SQL_QUERY).executeUpdate();
		session.flush();
		session.clear();
		return updatedEntities;
	}

	@Transactional
	public int closeInvoices(String[] invoices) {

		int updatedEntities = 0;
		Session session = sessionFactory.getCurrentSession();
		for (String invoice : invoices) {
			String hqlUpdate = "update Invoice set status = :newStatus where invoicenumber = :invoicenumber";
			updatedEntities += session.createQuery(hqlUpdate).setString("newStatus", "C")
					.setString("invoicenumber", invoice).executeUpdate();
		}
		session.flush();
		session.clear();
		return updatedEntities;
	}

	public String delBCPayment(PaymentMaintenance entity) {

		String err = "";
		Session session = sessionFactory.getCurrentSession();
		AppliedAmounts appAmount = new AppliedAmounts();
		appAmount.setAppliedAmount(entity.getPayingAmount());
		appAmount.setInvoiceNumber(entity.getInvoiceNumber());
		appAmount.setAppliedDate(new java.sql.Date(InsightUtils.getFormatedDate(entity.getAppliedDate()).getTime()));
		appAmount.setPaymentType(entity.getPaymentType());

		session.delete(appAmount);

		BouncedChecks bcheck = getBouncedChecksById(entity.getInvoiceNumber().substring(2));
		bcheck.setPaidamount(bcheck.getPaidamount().subtract(entity.getPayingAmount()));
		bcheck.setBalance(bcheck.getBalance().add(entity.getPayingAmount()));
		bcheck.setCheckdate(InsightUtils.getCurrentSQLDate());
		bcheck.setEntereddate(InsightUtils.getCurrentSQLDate());
		session.update(bcheck);
		session.flush();
		session.clear();
		err = "Invoice deleted successfully.";
		return err;
	}

	public String delInvPayment(PaymentMaintenance entity) {

		String err = "";
		Integer invno = Integer.parseInt(entity.getInvoiceNumber());

		Invoice inv = getInvoiceById(invno);
		String status = inv.getStatus().toString();
		if (!status.trim().equals("C") && !status.trim().equals("W")) {
			Session session = sessionFactory.getCurrentSession();

			AppliedAmounts appAmount = new AppliedAmounts();
			appAmount.setAppliedAmount(entity.getPayingAmount());
			appAmount.setInvoiceNumber(inv.getInvoicenumber() + "");
			appAmount
					.setAppliedDate(new java.sql.Date(InsightUtils.getFormatedDate(entity.getAppliedDate()).getTime()));
			appAmount.setPaymentType(entity.getPaymentType());

			session.delete(appAmount);

			inv.setAppliedamount(inv.getAppliedamount().subtract(entity.getPayingAmount()));
			inv.setBalance(inv.getBalance().add(entity.getPayingAmount()));
			inv.setStatus("P");

			session.update(inv);

			Customer customer = getCustomerById(inv.getCustomerid());
			customer.setCreditbalance(customer.getCreditbalance().add(entity.getPayingAmount()));
			session.update(customer);
			session.flush();
			session.clear();
		}
		return err;
	}

	public String delPayment(PaymentMaintenance entity) {

		String err = "";
		if (isPaymentExist(entity)) {
			if (entity.getInvoiceNumber().startsWith("bc") || entity.getInvoiceNumber().startsWith("BC")) {
				err = delBCPayment(entity);
			} else {
				err = delInvPayment(entity);
			}
		}
		return err;
	}

	public String delWriteoff(Writeoff entity) {

		String err = "";
		if (entity != null && entity.getInvoiceNo() != null) {
			Session session = sessionFactory.getCurrentSession();
			if (entity.getWriteOffDate() == null || entity.getWriteOffDate().equals("")) {
				return "Write off invoice does not exists.";
			} else {
				session.delete(entity);
			}
			Invoice invoice = getInvoiceById(entity.getInvoiceNo());
			invoice.setStatus("M");
			session.update(invoice);
			session.flush();
			session.clear();
			return "Invoice No " + entity.getInvoiceNo() + " successfully deleted.";
		}
		return err;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAcctPayable() {

		HashMap<String, Object> pbcMap = new HashMap<String, Object>();
		Session session = sessionFactory.getCurrentSession();
		/*
		 * String SQL =
		 * "select a.customerid as customerId, b.companyname as companyName, " +
		 * "( (SELECT IFNULL(0,Sum(Balance))  FROM BouncedChecks WHERE customerid=a.customerid) + sum(a.balance) ) as balance "
		 * +
		 * " from invoice a, customer b where a.balance!=0 and a.Status!='C' and a.Status!='W' and a.customerid=b.customerid"
		 * + " group by a.customerid HAVING balance < 0  order by 3 desc";
		 */
		String SQL = "SELECT a.customerid as customerId, a.balance as balance from invoice a where  a.balance!=0 and a.Status!='C' and a.Status!='W'  Order BY  a.customerid";
		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		List<BouncedChecks> result = query.list();
		List<BouncedChecks> newResult = processPayable(result);

		String bSQL = "Select a.CustomerId as customerId,b.companyname as companyName, Sum(a.Balance) as balance  From BouncedChecks a, customer b  Where a.Balance!=0  "
				+ " and a.CustomerId=b.CustomerId Group By CustomerId HAVING balance < 0 Order By 1";

		Query bquery = session.createSQLQuery(bSQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		List<BouncedChecks> resultb = bquery.list();

		List<BouncedChecks> removedLst = new ArrayList<BouncedChecks>();
		for (BouncedChecks itembc : resultb) {
			for (BouncedChecks item : newResult) {
				if (itembc.getCustomerid().equals(item.getCustomerid())) {
					item.setBalance(item.getBalance().add(itembc.getBalance()));
					removedLst.add(itembc);
				}
			}
		}
		resultb.removeAll(removedLst);
		newResult.addAll(resultb);
		pbcMap.put("ds", newResult);
		session.flush();
		session.clear();
		return pbcMap;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAcctRecievable() {

		HashMap<String, Object> pbcMap = new HashMap<String, Object>();
		Session session = sessionFactory.getCurrentSession();
		/*
		 * String SQL =
		 * "select a.customerid as customerId, b.companyname as companyName, " +
		 * "( (SELECT IFNULL(0,Sum(Balance))  FROM BouncedChecks WHERE customerid=a.customerid) + sum(a.balance) ) as balance "
		 * +
		 * " from invoice a, customer b where a.balance!=0 and a.Status!='C' and a.Status!='W' and a.customerid=b.customerid"
		 * + " group by a.customerid HAVING balance >0  order by 3 desc";
		 */
		String SQL = "SELECT a.customerid as customerId, a.balance as balance from invoice a where  a.balance!=0 and a.Status!='C' and a.Status!='W'  Order BY  a.customerid";
		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		List<BouncedChecks> result = query.list();
		List<BouncedChecks> newResult = processRecievable(result);

		String bSQL = "Select a.CustomerId as customerId,b.companyname as companyName, Sum(a.Balance) as balance  From BouncedChecks a, customer b  Where a.Balance!=0  "
				+ " and a.CustomerId=b.CustomerId Group By CustomerId HAVING balance > 0 Order By 1";

		Query bquery = session.createSQLQuery(bSQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		List<BouncedChecks> resultb = bquery.list();
		List<BouncedChecks> removedLst = new ArrayList<BouncedChecks>();
		for (BouncedChecks itembc : resultb) {
			for (BouncedChecks item : newResult) {
				if (itembc.getCustomerid().equals(item.getCustomerid())) {
					item.setBalance(item.getBalance().add(itembc.getBalance()));
					removedLst.add(itembc);
				}
			}
		}
		resultb.removeAll(removedLst);
		newResult.addAll(resultb);
		pbcMap.put("ds", newResult);
		session.flush();
		session.clear();
		return pbcMap;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAcctStmtDS(String customerid) {

		HashMap<String, Object> pbcMap = new HashMap<String, Object>();
		Session session = sessionFactory.getCurrentSession();

		Customer result = getCustomerById(customerid);
		String address = "ATTN :" + (result.getContactname() == null ? "" : result.getContactname()).toUpperCase()
				+ "\\n" + (result.getCompanyname() == null ? "" : result.getCompanyname()).toUpperCase();
		Address result1 = getCustomerAddress(customerid);
		address += "\\n" + (result1.getAddr1() == null ? "" : result1.getAddr1()) + ", "
				+ (result1.getAddr2() == null ? "" : result1.getAddr2()) + "\\n"
				+ (result1.getCity() == null ? "" : result1.getCity()) + ", "
				+ (result1.getState() == null ? "" : result1.getState()) + "."
				+ (result1.getPostalcode() == null ? "" : result1.getPostalcode());
		address += "\\n PHONE: " + (result1.getPhone() == null ? "" : result1.getPhone()) + ",  FAX: "
				+ (result1.getFax() == null ? "" : result1.getFax());
		address += "\\n ROUTE #: " + (result1.getRegion() == null ? "" : result1.getRegion());

		String SQL = "SELECT CustomerId as customerid,InvoiceNumber as invoicenumber, OrderDate as orderdate,"
				+ " (InvoiceTotal - Discount + Tax) as invoiceTotal, Balance as balance FROM Invoice "
				+ " WHERE CustomerId=:customerid AND Balance!=0 Order by 1";
		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("customerid", customerid))
				.setResultTransformer(Transformers.aliasToBean(Invoice.class));
		List<Invoice> result2 = query.list();
		// //
		SQL = "Select CheckId as invoicenumber,CustomerId as customerid,CheckDate as orderdate,BouncedAmount as invoiceTotal,"
				+ "Balance as balance from BouncedChecks Where CustomerId=:customerid and IsCleared='N'";
		Query query1 = ((SQLQuery) session.createSQLQuery(SQL).setParameter("customerid", customerid))
				.setResultTransformer(Transformers.aliasToBean(Invoice.class));
		List<Invoice> result3 = query1.list();

		result2.addAll(result3);

		pbcMap.put("ds", result2);
		pbcMap.put("customerAddres", address);
		session.flush();
		session.clear();
		return pbcMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAgeingInvoiceList() {

		Map<String, Object> agemap = new HashMap<String, Object>();
		Session session = sessionFactory.getCurrentSession();

		String SQL = "select a.CustomerId as customerid, a.OrderDate as orderdate, DATEDIFF(NOW(),a.OrderDate) as invoicetime, a.Balance from invoice a "
				+ "where a.balance!=0 and a.Status!='C' and a.Status!='W' ORDER BY customerid";

		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(Invoice.class));

		List<Invoice> result = query.list();

		HashMap<String, BigDecimal> bcmap = getPendingBCMap();
		String currCustId = "";
		double amnt = 0;
		double totalBalance = 0.0;
		double invtime = 0;
		double totBalance = 0.0, tot90 = 0.0, totCurr = 0.0, tot30 = 0.0, tot60 = 0.0, totBC = 0.0;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("#.##");

		for (Invoice inv : result) {
			if (!currCustId.equals(inv.getCustomerid())) {
				amnt = 0;
				if (bcmap != null && bcmap.get(currCustId) != null) {
					amnt = bcmap.get(currCustId).doubleValue();
					totalBalance += amnt;
					totBC += amnt;
					bcmap.remove(currCustId);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				Customer cust = getCustomerById(currCustId);
				map.put("customerid", currCustId);
				map.put("companyName", (cust == null || cust.getCompanyname() == null) ? "" : cust.getCompanyname());
				map.put("paymentterms", (cust == null || cust.getPaymentterms() == null) ? ""
						: InsightUtils.getPTermsDesc(cust.getPaymentterms().toString()));
				if (totalBalance > 0) {

					if (inv.getInvoicetime().doubleValue() < 30) {
						totCurr += totalBalance;
						map.put("curr", Double.valueOf(df.format(totalBalance)));
					} else if (inv.getInvoicetime().doubleValue() < 60) {
						tot30 += totalBalance;
						map.put("30days", Double.valueOf(df.format(totalBalance)));
					} else if (inv.getInvoicetime().doubleValue() < 90) {
						tot60 += totalBalance;
						map.put("60days", Double.valueOf(df.format(totalBalance)));
					} else {
						tot90 += (totalBalance - amnt);
						map.put("90days", Double.valueOf(df.format(totalBalance)));
					}
					totBalance += totalBalance;
					map.put("balance", Double.valueOf(df.format(totalBalance)));

				} else {
					if (inv.getInvoicetime().doubleValue() < 30) {
						totCurr += totalBalance;
						map.put("curr", Double.valueOf(df.format(totalBalance)));
					} else if (inv.getInvoicetime().doubleValue() < 60) {
						tot30 += totalBalance;
						map.put("30days", Double.valueOf(df.format(totalBalance)));
					} else if (inv.getInvoicetime().doubleValue() < 90) {
						tot60 += totalBalance;
						map.put("60days", Double.valueOf(df.format(totalBalance)));
					} else {
						tot90 += (totalBalance - amnt);
						map.put("90days", Double.valueOf(df.format(totalBalance)));
					}
					totBalance += totalBalance;
					map.put("balance", Double.valueOf(df.format(totalBalance)));

				}
				list.add(map);

				totalBalance = inv.getBalance().doubleValue();
				currCustId = inv.getCustomerid();
				invtime = inv.getInvoicetime().doubleValue();
			} else if (currCustId.trim().equals("") || currCustId.trim().equals(inv.getCustomerid())) {
				totalBalance += inv.getBalance().doubleValue();

			}
		}
		if (totalBalance > 0) {
			if (bcmap != null && bcmap.get(currCustId) != null) {
				amnt = bcmap.get(currCustId).doubleValue();
				totalBalance += amnt;
				totBC += amnt;
				bcmap.remove(currCustId);
			}
			Customer cust = getCustomerById(currCustId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerid", currCustId);
			map.put("companyName", (cust == null || cust.getCompanyname() == null) ? "" : cust.getCompanyname());
			map.put("paymentterms", (cust == null || cust.getPaymentterms() == null) ? ""
					: InsightUtils.getPTermsDesc(cust.getPaymentterms().toString()));
			totBalance += totalBalance;
			map.put("balance", Double.valueOf(df.format(totalBalance)));
			if (invtime < 30) {
				totCurr += totalBalance;
				map.put("curr", Double.valueOf(df.format(totalBalance)));
			} else if (invtime < 60) {
				tot30 += totalBalance;
				map.put("30days", Double.valueOf(df.format(totalBalance)));
			} else if (invtime < 90) {
				tot60 += totalBalance;
				map.put("60days", Double.valueOf(df.format(totalBalance)));
			} else {
				tot90 += (totalBalance - amnt);
				map.put("90days", Double.valueOf(df.format(totalBalance)));
			}

			list.add(map);
		}
		if (bcmap != null && bcmap.size() > 0) {
			for (String custid : bcmap.keySet()) {
				Customer cust = getCustomerById(custid);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("customerid", custid);
				map.put("companyName", (cust == null || cust.getCompanyname() == null) ? "" : cust.getCompanyname());
				map.put("paymentterms", (cust == null || cust.getPaymentterms() == null) ? ""
						: InsightUtils.getPTermsDesc(cust.getPaymentterms().toString()));
				totalBalance = bcmap.get(custid).doubleValue();
				totBalance += totalBalance;
				map.put("balance", Double.valueOf(df.format(totalBalance)));
				totBC += totalBalance;
				map.put("bc", Double.valueOf(df.format(totalBalance)));
				list.add(map);
			}
		}
		Collections.sort(list, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(final Map<String, Object> o1, final Map<String, Object> o2) {

				return ((Comparable<Double>) o2.get("balance")).compareTo((Double) o1.get("balance"));
			}
		});
		agemap.put("ds", list);
		agemap.put("totbalance", totBalance);
		agemap.put("totCurr", totCurr);
		agemap.put("tot30", tot30);
		agemap.put("tot60", tot60);
		agemap.put("tot90", tot90);
		agemap.put("totBC", totBC);
		session.flush();
		session.clear();
		return agemap;
	}

	@Transactional
	public List<AppliedAmounts> getAppliedAmountsByInvNo(String invoiceNo) {

		String SQL = "select AppliedDate ,InvoiceNumber ," + "AppliedAmount ,PaymentType,UserName," + "PaymentTime "
				+ " from AppliedAmounts Where InvoiceNumber =:invoiceNo ; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("invoiceNo", invoiceNo))
				.setResultTransformer(Transformers.aliasToBean(AppliedAmounts.class));

		@SuppressWarnings("unchecked")
		List<AppliedAmounts> results = query.list();
		session.flush();
		session.clear();
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentMaintenance> getBCAdjList() {

		Session session = sessionFactory.getCurrentSession();
		String SQL = "SELECT a.PaymentType , a.InvoiceNumber as invoiceNumber,"
				+ " a.AppliedAmount as payingAmount, a.AppliedAmount as appliedAmount,"
				+ " c.CompanyName  FROM AppliedAmounts a, bouncedchecks b,customer c WHERE " + " AppliedDate='"
				+ InsightUtils.getCurrentSQLDate()
				+ "' and (a.PaymentType like 'apto%' or a.PaymentType like 'ap to%' or a.PaymentType like 'apcr%' or a.PaymentType like 'ap cr%')"
				+ " and a.InvoiceNumber like 'BC%' and SUBSTR(a.InvoiceNumber ,3) =b.CheckId "
				+ " and b.CustomerId=c.CustomerId ";

		Query query = session.createSQLQuery(SQL)
				.setResultTransformer(Transformers.aliasToBean(PaymentMaintenance.class));

		List<PaymentMaintenance> result = query.list();
		session.flush();
		session.clear();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentMaintenance> getBCList() {

		Session session = sessionFactory.getCurrentSession();
		String SQL = "SELECT a.PaymentType , a.InvoiceNumber as invoiceNumber,"
				+ " a.AppliedAmount as payingAmount, a.AppliedAmount as appliedAmount,"
				+ " c.CompanyName  FROM AppliedAmounts a, bouncedchecks b,customer c WHERE " + " AppliedDate='"
				+ InsightUtils.getCurrentSQLDate()
				+ "' and a.InvoiceNumber like 'BC%' and SUBSTR(a.InvoiceNumber ,3) =b.CheckId "
				+ " and b.CustomerId=c.CustomerId ";

		Query query = session.createSQLQuery(SQL)
				.setResultTransformer(Transformers.aliasToBean(PaymentMaintenance.class));

		List<PaymentMaintenance> result = query.list();
		session.flush();
		session.clear();
		return result;
	}

	@Transactional
	public List<BouncedChecks> getBouncedChecksByCustomerId(String customerId) {

		String SQL = "SELECT b.checkid,b.customerid,c.companyname,c.creditbalance,b.entereddate, b.checkno, b.checkdate,b.iscleared,b.bouncedamount,"
				+ " b.paidamount, b.balance FROM bouncedchecks b,customer c where  b.customerid=:customerId and b.customerid=c.CustomerId  ORDER BY checkid desc; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("customerId", customerId))
				.setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		@SuppressWarnings("unchecked")
		List<BouncedChecks> results = query.list();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional
	public BouncedChecks getBouncedChecksById(String bouncedCheckId) {

		String SQL = "SELECT checkid ,b.customerid,c.companyname ,c.creditbalance ,entereddate, checkno, checkdate,iscleared,bouncedamount,"
				+ " paidamount, balance FROM bouncedchecks b,customer c where b.checkid =:bouncedCheckId  and b.customerid=c.customerid  ;";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("bouncedCheckId", bouncedCheckId))
				.setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		BouncedChecks results = (BouncedChecks) query.uniqueResult();

		session.flush();
		session.clear();
		return results;
	}

	public Address getCustomerAddress(String customerid) {

		Session session = sessionFactory.getCurrentSession();
		String SQL = "SELECT ID as id,Type,Who,addr1,addr2,city,state,postalcode,country,phone,fax,region"
				+ " FROM Address WHERE ID=:customerid AND Type='Standard' AND Who='Cust'";

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("customerid", customerid))
				.setResultTransformer(Transformers.aliasToBean(Address.class));
		Address results = (Address) query.uniqueResult();

		session.flush();
		session.clear();
		return results;
	}

	@Transactional
	public Customer getCustomerById(String customerId) {

		String SQL = "SELECT customerid,companyname,contactname,contacttitle,taxid,taxidnumber,terms, notes, paymentterms,creditbalance,creditlimit,"
				+ "customerlevel, address1, address2,town, st, rte, ph, zip FROM customer where  CustomerId =:custId ; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("custId", customerId))
				.setResultTransformer(Transformers.aliasToBean(Customer.class));

		Customer results = (Customer) query.uniqueResult();
		session.flush();
		session.clear();
		return results;
	}

	public Map<String, Object> getCustomerDetails(String customerId, String startDate, String endDate) {

		Session session = sessionFactory.getCurrentSession();
		String SQL = "Select sum(invoicetotal) as totalPurchase From Invoice Where Customerid=:customerId ";
		if (!(startDate.equals("") || endDate.equals("")))
			SQL += " And Orderdate>=:startDate  and OrderDate<= :endDate ";

		Query query = (session.createSQLQuery(SQL).setParameter("customerId", customerId));
		if (!(startDate.equals("") || endDate.equals(""))) {
			query.setParameter("startDate", startDate).setParameter("endDate", endDate);
		}

		double totalPurchase = ((Number) query.uniqueResult()).doubleValue();

		SQL = "Select sum(Balance) as currBalance From Invoice Where Customerid=:customerId "
				+ " and Status!='C' and Status!='W' and Balance>0 ";

		Query bquery = (session.createSQLQuery(SQL).setParameter("customerId", customerId));

		double currBalance = ((Number) (bquery.uniqueResult() == null ? 0.0 : bquery.uniqueResult())).doubleValue();

		SQL = "Select sum(Balance) as writeBalance From Invoice Where Customerid=:customerId "
				+ " and Status='W' and Balance>0 ";

		Query wquery = (session.createSQLQuery(SQL).setParameter("customerId", customerId));
		double writeBalance = ((Number) (wquery.uniqueResult() == null ? 0.0 : bquery.uniqueResult())).doubleValue();

		SQL = "Select CheckId, EnteredDate, CheckNo, CheckDate, BouncedAmount, Balance from BouncedChecks"
				+ " Where Customerid=:customerId order by EnteredDate";

		Query bcquery = ((SQLQuery) session.createSQLQuery(SQL).setParameter("customerId", customerId))
				.setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));
		@SuppressWarnings("unchecked")
		List<BouncedChecks> bclist = bcquery.list();
		if (bclist != null && bclist.size() > 0) {
			String bcinvno = "", paymentDetails;
			for (BouncedChecks bc : bclist) {
				bcinvno = "BC" + bc.getCheckid();
				paymentDetails = "**NOT PAID**";
				//
				List<AppliedAmounts> bcamtlist = getAppliedAmountsByInvNo(bcinvno);
				if (bcamtlist != null && bcamtlist.size() > 0) {
					paymentDetails = "";
					for (AppliedAmounts amt : bcamtlist) {
						if (paymentDetails.equals("")) {
							paymentDetails = amt.getAppliedDate() + "/" + amt.getAppliedAmount();
						} else {
							paymentDetails += "---" + amt.getAppliedDate() + "/" + amt.getAppliedAmount();
						}
					}
				}
				bc.setPaymentdetails(paymentDetails);
			}
		}

		SQL = "Select InvoiceNumber as invoicenumber, (InvoiceTotal+Tax-Discount) as invoicetotal,"
				+ " Balance, OrderDate as orderdate, Status as status from Invoice"
				+ " Where CustomerId=:customerId  And Orderdate>=:startDate  and OrderDate<= :endDate ";
		Query invquery = ((SQLQuery) session.createSQLQuery(SQL).setParameter("customerId", customerId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate))
						.setResultTransformer(Transformers.aliasToBean(Invoice.class));
		@SuppressWarnings("unchecked")
		List<Invoice> invlist = invquery.list();
		Customer cust = getCustomerById(customerId);
		int termDays = InsightUtils.getPTermsDays(cust.getPaymentterms().toString());
		for (Invoice inv : invlist) {
			if (inv.getStatus().toString().equalsIgnoreCase("w")) {
				inv.setNotes("*** WRITE OFF ***");
			}
			if (inv.getBalance().doubleValue() != 0) {
				inv.setNotes("**PENDING AMOUNT - " + inv.getBalance().doubleValue() + "**");
			}
			inv = setInvPaymentDetails(inv, termDays);
		}
		Map<String, Object> list = new HashMap<String, Object>();
		// LOGGER.info("---" + customerId);

		list.put("customerId", customerId);
		list.put("companyName", cust.getCompanyname());
		list.put("terms", InsightUtils.getPTermsDesc(cust.getPaymentterms().toString()));
		list.put("totalPurchase", totalPurchase);
		list.put("currBalance", currBalance);
		list.put("writeBalance", writeBalance);
		list.put("bclist", bclist);
		list.put("invlist", invlist);

		session.flush();
		session.clear();
		return list;
	}

	@Transactional
	public List<InventoryPartsTransfer> getInventoryParts(String subcategorycode, String ordertypeselected,
			String stocklimit, String orderlimit) {
		List<InventoryPartsTransfer> inventorypartslist = new LinkedList<InventoryPartsTransfer>();

		Session session = sessionFactory.getCurrentSession();
		Query query = null;

		Boolean hascategory = false;
		Boolean hasordertype = false;

		StringBuilder sb = new StringBuilder();

		sb.append(AccountUtils.INVENTORYTRANSFER_SQL);

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

		query = session.createSQLQuery(sb.toString());

		if (hascategory) {
			query.setParameter("subcategorycode", subcategorycode);
		}

		if (hasordertype) {
			query.setParameter("ordertype", ordertypeselected);
		}

		((SQLQuery) query).addScalar("partno").addScalar("manufacturername").addScalar("makemodelname")
				.addScalar("year").addScalar("partdescription").addScalar("unitsinstock")

				.setMaxResults(Integer.parseInt(orderlimit))
				.setResultTransformer(Transformers.aliasToBean(InventoryPartsTransfer.class));

		inventorypartslist = query.list();

		session.flush();
		session.clear();
		return inventorypartslist;

	}

	public InventoryTransfer getInventoryTransferReport(String branch, String analyticsfromdate, String analyticstodate,
			LocalVendors localvendor) {

		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(AccountUtils.INVENTORYTRANSFER_REPORT);
		query.setParameter("localvendorid", localvendor.getSupplierid());
		query.setParameter("datefrom", InsightUtils.getFormatedDate2(analyticsfromdate));
		query.setParameter("dateto", InsightUtils.getFormatedDate2(analyticstodate));

		@SuppressWarnings("unchecked")
		List<LocalOrders> results = query.list();

		Integer totalItems = 0;
		Integer totalQuantity = 0;
		BigDecimal amount = BigDecimal.ZERO;
		Double totalamount = 0.00;

		if (results != null) {
			if (results.size() > 0) {
				session.flush();
				session.clear();
				InventoryTransfer inventorytransfer = new InventoryTransfer();

				for (LocalOrders localorders : results) {
					totalItems++;
					totalQuantity = localorders.getQuantity() + totalQuantity;
					amount = localorders.getPrice().multiply(new BigDecimal(localorders.getQuantity()));
					totalamount = totalamount + amount.doubleValue();

				}
				inventorytransfer.setBranchfrom(branch);
				inventorytransfer.setBranchto(localvendor.getCompanyname());
				inventorytransfer.setTotalAmount(new BigDecimal(totalamount).setScale(2, BigDecimal.ROUND_HALF_UP));
				inventorytransfer.setTotalItems(totalItems);
				inventorytransfer.setTotalQuantity(totalQuantity);
				return inventorytransfer;
			} else {
				session.flush();
				session.clear();
				InventoryTransfer inventorytransfer = new InventoryTransfer(branch, localvendor.getShortcode());
				return inventorytransfer;
			}

		} else {
			session.flush();
			session.clear();
			InventoryTransfer inventorytransfer = new InventoryTransfer(branch, localvendor.getShortcode());
			return inventorytransfer;
		}

	}

	@Transactional
	public Invoice getInvoiceById(int invoiceNo) {

		String SQL = "select invoicenumber, orderdate,  customerid, "
				+ "returnedinvoice,shipto,  shipattention, shipvia, billattention, invoicetotal, appliedamount,"
				+ " newapplied, datenewapplied,  discount, tax, balance, salesperson, notes,status"
				+ " from Invoice  where  InvoiceNumber =:invoiceNo ; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("invoiceNo", invoiceNo))
				.setResultTransformer(Transformers.aliasToBean(Invoice.class));

		Invoice results = (Invoice) query.uniqueResult();
		session.flush();
		session.clear();
		return results;
	}

	public LocalVendors getLocalVendorByShortCode(String branchselected) {

		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(AccountUtils.LOCALVENDOR);
		query.setParameter("vshortcode", branchselected);

		@SuppressWarnings("unchecked")
		List<LocalVendors> results = query.list();

		if (results != null) {
			if (results.size() > 0) {
				session.flush();
				session.clear();
				return results.get(0);

			} else {
				session.flush();
				session.clear();
				return null;
			}

		} else {
			session.flush();
			session.clear();
			return null;
		}

	}

	public Integer getMaxBCCheckId() {

		String SQL = "SELECT max(CheckId) as CheckId FROM bouncedchecks  ";
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		BouncedChecks results = (BouncedChecks) query.uniqueResult();
		Integer chkId = results.getCheckid() == null ? 0 : results.getCheckid();
		session.flush();
		session.clear();
		return chkId;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentMaintenance> getNonBCAdjList() {

		Session session = sessionFactory.getCurrentSession();

		String SQL = "SELECT  a.PaymentType ,a.InvoiceNumber ," + " a.appliedAmount ,"
				+ " c.CompanyName  FROM AppliedAmounts a, Invoice b, Customer c " + " WHERE AppliedDate='"
				// + "2011-03-01"
				+ InsightUtils.getCurrentSQLDate()
				+ "' and (a.PaymentType like 'apto%' or a.PaymentType like 'ap to%' or a.PaymentType like 'apcr%' or a.PaymentType like 'ap cr%') "
				+ " and a.InvoiceNumber Not Like 'BC%' and a.InvoiceNumber=b.InvoiceNumber "
				+ " and b.CustomerId=c.CustomerId Order By a.InvoiceNumber";

		Query query = session.createSQLQuery(SQL)
				.setResultTransformer(Transformers.aliasToBean(PaymentMaintenance.class));

		List<PaymentMaintenance> result = query.list();
		session.flush();
		session.clear();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentMaintenance> getNonBCList() {

		Session session = sessionFactory.getCurrentSession();

		String SQL = "SELECT  a.PaymentType ,a.InvoiceNumber ,"
				+ "case  WHEN a.PaymentType ='' THEN a.appliedAmount end as payingAmount,"
				+ "case  WHEN a.PaymentType <>'' THEN a.appliedAmount end as appliedAmount,"
				+ " c.CompanyName  FROM AppliedAmounts a, Invoice b, Customer c " + " WHERE AppliedDate='"
				+ InsightUtils.convertUSToMySQLFormat(InsightUtils.getNewUSDate())
				+ "' and a.InvoiceNumber Not Like 'BC%' and a.InvoiceNumber=b.InvoiceNumber "
				+ " and b.CustomerId=c.CustomerId Order By a.InvoiceNumber";

		Query query = session.createSQLQuery(SQL)
				.setResultTransformer(Transformers.aliasToBean(PaymentMaintenance.class));

		List<PaymentMaintenance> result = query.list();
		session.flush();
		session.clear();
		return result;
	}

	@Transactional
	public List<ScanOrderDetails> getPartsTransferDetails(String subCategory, int unitsInStock, int selectYear,
			String branch, List<Requiredquanity> requiredquanititylist) {

		Boolean isGRDBLive = true;

		String orderfrom = getTodayorbeforeDateinFormat("yearbefore");
		String orderto = getTodayorbeforeDateinFormat("today");
		String YEARLYSAFTETYQTY = "SELECT p.partno AS partno, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
				+ " SUM(INVDTLS.Quantity) AS totalsold ,p.ordertype, p.actualprice as buyingprice, p.costprice AS sellingprice,Coalesce(Round( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) as percent "
				+ " FROM PARTS P, invoicedetails invdtls, INVOICE i  "
				+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0 "
				+ " AND p.SubCategory =:subcategorycode "
				+ " AND i.OrderDate BETWEEN :orderfrom AND :orderto AND p.interchangeno =''  AND p.ordertype <> 'S'"
				+ " GROUP BY p.partno HAVING totalsold > 0    ORDER BY totalsold";

		try {
			isGRDBLive = grpartsdao.checkStockData("HD109");
		} catch (Exception e) {
			isGRDBLive = false;
		}

		String INVENTORYTRANSFER_SQL = "SELECT m.manufacturername as manufacturername,m.MakeModelName as makemodelname, p.partno as PartNo, p.UnitsInStock as unitsinstock, p.year as year, p.PartDescription as partdescription, p.location as location, p.safetyquantity as safetyquantity , p.returncount as returncount"
				+ " FROM  parts p, makemodel m " + "WHERE p.MakeModelCode = m.MakeModelCode "
				+ "AND P.InterchangeNo = ''" + " AND p.SubCategory = :subCategory  "
				// + "AND p.UnitsInStock > 3 "
				+ "AND p.UnitsInStock < :unitsInStock  " + "AND :selectYear between yearfrom and yearto "
				+ "AND p.safetyquantity  > p.UnitsInStock   ";

		String INVENTORYTRANSFER_SQL_SUBCATEGORY_ALL = "SELECT m.manufacturername as manufacturername,m.MakeModelName as makemodelname, p.partno as PartNo, p.UnitsInStock as unitsinstock, p.year as year, p.PartDescription as partdescription, p.location as location, p.safetyquantity as safetyquantity , p.returncount as returncount"
				+ " FROM  parts p, makemodel m " + "WHERE p.MakeModelCode = m.MakeModelCode "
				+ "AND P.InterchangeNo = '' "
				// + "AND p.UnitsInStock > 3 AND "
				+ "AND p.UnitsInStock < :unitsInStock " + "AND :selectYear between yearfrom and yearto "
				+ "AND p.safetyquantity >   p.UnitsInStock   ";

		String hqlQuery = "SELECT  mk.makemodelname AS makemodelname, mk.manufacturername AS manufacturername, p.partno AS partno, p.partdescription   AS partdescription"
				+ " , vo.quantity  AS quantity " + " FROM Parts p, VendorOrderedItems vo, makemodel mk"
				+ " WHERE p.partno = vo.partno AND p.makemodelcode = mk.makemodelcode"
				+ " AND vo.orderno =:orderno order by p.partno";

		List<ScanOrderDetails> scanorderdetailslist = new ArrayList<ScanOrderDetails>();
		Session session = sessionFactory.getCurrentSession();
		List<ScanOrderDetails> results;

		if (subCategory.equals("ALL")) {
			Query query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY_ALL)
					.setParameter("unitsInStock", unitsInStock).setParameter("selectYear", selectYear))
							.addScalar("partno").addScalar("partdescription").addScalar("makemodelname")
							.addScalar("manufacturername")
							.setResultTransformer(Transformers.aliasToBean(ScanOrderDetails.class));
			results = query.list();
		} else {
			Query query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL)
					.setParameter("subCategory", subCategory).setParameter("unitsInStock", unitsInStock)
					.setParameter("selectYear", selectYear)).addScalar("partno").addScalar("partdescription")
							.addScalar("makemodelname").addScalar("manufacturername")
							.setResultTransformer(Transformers.aliasToBean(ScanOrderDetails.class));
			results = query.list();
		}

		session.flush();
		session.clear();
		int safetyQty = 0;
		int transferQty = 0;
		int requiredQty = 0;
		String today = getTodayorbeforeDateinFormat("today");

		String oneYearBack = getTodayorbeforeDateinFormat("oneYearBack");

		List<InvCategoryBySalesAnalysis> InvcategoryBySalesAnalysis_ch = new ArrayList<InvCategoryBySalesAnalysis>();
		List<InvCategoryBySalesAnalysis> InvcategoryBySalesAnalysis_gr = new ArrayList<InvCategoryBySalesAnalysis>();
		List<InvCategoryBySalesAnalysis> InvcategoryBySalesAnalysis_ny = new ArrayList<InvCategoryBySalesAnalysis>();
		List<InvCategoryBySalesAnalysis> InvcategoryBySalesAnalysis_mp = new ArrayList<InvCategoryBySalesAnalysis>();

		InvcategoryBySalesAnalysis_ch = chpartsdao.getInventoryBySubcategory(oneYearBack, today, subCategory, "CH");

		InvcategoryBySalesAnalysis_gr = grpartsdao.getInventoryBySubcategory(oneYearBack, today, subCategory, "GR");

		InvcategoryBySalesAnalysis_mp = mppartsdao.getInventoryBySubcategory(oneYearBack, today, subCategory, "MP");

		for (ScanOrderDetails scanorderdetails : results) {

			Requiredquanity requiredquantity = new Requiredquanity();

			StockData chdata = new StockData();

			StockData grdata = new StockData();

			StockData mpdata = new StockData();

			StockData nydata = new StockData();

			StockData tmpdata = new StockData();

			chdata = chpartsdao.getStockData(scanorderdetails.getPartno().trim());
			grdata = grpartsdao.getStockData(scanorderdetails.getPartno().trim());
			mpdata = mppartsdao.getStockData(scanorderdetails.getPartno().trim());

			tmpdata = branch.equals("CHS") ? chdata
					: branch.equals("MPS") ? mpdata : branch.equals("GRS") ? grdata : null;

			int AvailableQty = tmpdata.getUnitsinstock() - tmpdata.getSafetyquantity();
			// if (AvailableQty < 0)
			// AvailableQty = 0;
			scanorderdetails.setChunitsinstock(chdata.getUnitsinstock());
			scanorderdetails.setChunitsonorder(chdata.getUnitsonorder());
			scanorderdetails.setChreorderlevel(chdata.getReorderlevel());
			scanorderdetails.setChsafetyquantity(chdata.getSafetyquantity());
			// for (InvCategoryBySalesAnalysis str :
			// InvcategoryBySalesAnalysis_ch) {
			// if (str.getPartno().equals(chdata.partno)) {
			// scanorderdetails.setChsafetyquantity(str.getTotalsold().intValue()
			// / 12);
			// }
			// }
			safetyQty = scanorderdetails.getChsafetyquantity() != null ? scanorderdetails.getChsafetyquantity() : 0;

			if (scanorderdetails.getChsafetyquantity() == null)
				scanorderdetails.setChsafetyquantity(0);
			// requiredquantity.setChrequiredquanity(
			// DisplayDefaultValueForRequestOrder(safetyQty,
			// chdata.getUnitsinstock(), AvailableQty, "CH"));

			if (!branch.equals("CHS")) {
				transferQty = calculateTransferQty(requiredQty,
						(chdata.getUnitsinstock() - chdata.getSafetyquantity()));

				requiredquantity.setChrequiredquanity(transferQty);

				requiredQty = requiredQty - transferQty;

			} else {
				requiredQty = safetyQty - chdata.getUnitsinstock();
				requiredquantity.setChrequiredquanity(requiredQty);
			}

			if (isGRDBLive) {

				scanorderdetails.setGrunitsinstock(grdata.getUnitsinstock());
				scanorderdetails.setGrunitsonorder(grdata.getUnitsonorder());
				scanorderdetails.setGrreorderlevel(grdata.getReorderlevel());
				scanorderdetails.setGrsafetyquantity(grdata.getSafetyquantity());
				// for (InvCategoryBySalesAnalysis str :
				// InvcategoryBySalesAnalysis_gr) {
				// if (str.getPartno().equals(grdata.partno)) {
				// scanorderdetails.setGrsafetyquantity(str.getTotalsold().intValue()
				// / 12);
				// }
				// }
				safetyQty = scanorderdetails.getGrsafetyquantity() != null ? scanorderdetails.getGrsafetyquantity() : 0;
				if (scanorderdetails.getGrsafetyquantity() == null)
					scanorderdetails.setGrsafetyquantity(0);

				if (!branch.equals("GRS")) {
					transferQty = calculateTransferQty(requiredQty,
							(grdata.getUnitsinstock() - grdata.getSafetyquantity()));

					requiredquantity.setGrrequiredquanity(transferQty);

					requiredQty = requiredQty - transferQty;
				} else {

					requiredQty = safetyQty - grdata.getUnitsinstock();
					requiredquantity.setGrrequiredquanity(requiredQty);
				}
				// DisplayDefaultValueForRequestOrder(safetyQty,
				// grdata.getUnitsinstock(), AvailableQty, "GR"));

			}

			transferQty = calculateTransferQty(requiredQty, nydata.getUnitsinstock());

			requiredquantity.setNyrequiredquanity(transferQty);

			requiredQty = requiredQty - transferQty;

			scanorderdetails.setMpunitsinstock(mpdata.getUnitsinstock());
			scanorderdetails.setMpunitsonorder(mpdata.getUnitsonorder());
			scanorderdetails.setMpreorderlevel(mpdata.getReorderlevel());

			for (InvCategoryBySalesAnalysis str : InvcategoryBySalesAnalysis_mp) {
				if (str.getPartno().equals(mpdata.partno)) {
					scanorderdetails.setMpsafetyquantity(str.getTotalsold().intValue() / 12);
				}
			}
			safetyQty = scanorderdetails.getMpsafetyquantity() != null ? scanorderdetails.getMpsafetyquantity() : 0;
			if (scanorderdetails.getMpsafetyquantity() == null)
				scanorderdetails.setMpsafetyquantity(0);

			if (!branch.equals("MPS")) {
				transferQty = calculateTransferQty(requiredQty, mpdata.getUnitsinstock());

				requiredquantity.setMprequiredquanity(transferQty);

				// requiredquantity.setMprequiredquanity(
				// DisplayDefaultValueForRequestOrder(safetyQty,
				// mpdata.getUnitsinstock(), AvailableQty, "MP"));

				requiredQty = requiredQty - transferQty;
			} else {
				requiredQty = safetyQty - mpdata.getUnitsinstock();
				requiredquantity.setMprequiredquanity(requiredQty);
			}
			scanorderdetailslist.add(scanorderdetails);
			requiredquanititylist.add(requiredquantity);
			requiredQty = 0;
		}

		return results;
	}

	// nnq
	@Transactional
	public List<ScanOrderDetails> getPartsTransferToBranchesDetails(String subCategory, int unitsInStock,
			int selectYear, String branch, List<Requiredquanity> requiredquanititylist) {

		String INVENTORYTRANSFER_SQL_SUBCATEGORY = "SELECT p.manufacturername AS manufacturername,p.MakeModelName AS makemodelname, p.partno AS PartNo, p.UnitsInStock AS unitsinstock, p.year AS YEAR, "
				+ " p.PartDescription AS partdescription, p.location AS location, p.safetyquantity as safetyquantity , p.returncount as returncount "
				+ " FROM  parts p WHERE  P.InterchangeNo = '' AND p.SubCategory = :subcategorycode  AND p.UnitsInStock > :unitsInStock AND (:selectYear BETWEEN yearfrom AND yearto);";

		String INVENTORYTRANSFER_SQL_SUBCATEGORY_ALL = "SELECT p.manufacturername AS manufacturername,p.MakeModelName AS makemodelname, p.partno AS PartNo, p.UnitsInStock AS unitsinstock, p.year AS YEAR, "
				+ " p.PartDescription AS partdescription, p.location AS location, p.safetyquantity as safetyquantity , p.returncount as returncount "
				+ " FROM  parts p WHERE  P.InterchangeNo = ''  AND p.UnitsInStock > :unitsInStock AND (:selectYear BETWEEN yearfrom AND yearto);";

		List<ScanOrderDetails> scanorderdetailslist = new ArrayList<ScanOrderDetails>();
		Session session = sessionFactory.getCurrentSession();
		List<ScanOrderDetails> results;

		if (subCategory.equals("ALL")) {
			Query query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY_ALL)
					.setParameter("unitsInStock", unitsInStock).setParameter("selectYear", selectYear))
							.addScalar("partno").addScalar("partdescription").addScalar("makemodelname")
							.addScalar("manufacturername")
							.setResultTransformer(Transformers.aliasToBean(ScanOrderDetails.class));
			results = query.list();
		} else {
			Query query = ((SQLQuery) session.createSQLQuery(INVENTORYTRANSFER_SQL_SUBCATEGORY)
					.setParameter("unitsInStock", unitsInStock).setParameter("selectYear", selectYear)
					.setParameter("subcategorycode", subCategory)).addScalar("partno").addScalar("partdescription")
							.addScalar("makemodelname").addScalar("manufacturername")
							.setResultTransformer(Transformers.aliasToBean(ScanOrderDetails.class));
			results = query.list();
		}

		int safetyQty = 0;
		int transferQty = 0;
		int requiredQty = 0;
		String today = getTodayorbeforeDateinFormat("today");
		String oneYearBack = getTodayorbeforeDateinFormat("oneYearBack");

		for (ScanOrderDetails scanorderdetails : results) {
			Requiredquanity requiredquantity = new Requiredquanity();

			StockData chdata = new StockData();

			StockData grdata = new StockData();

			StockData mpdata = new StockData();

			StockData nydata = new StockData();

			StockData tmpdata = new StockData();

			chdata = chpartsdao.getStockData(scanorderdetails.getPartno().trim());
			grdata = grpartsdao.getStockData(scanorderdetails.getPartno().trim());
			mpdata = mppartsdao.getStockData(scanorderdetails.getPartno().trim());

			tmpdata = branch.equals("CHS") ? chdata
					: branch.equals("MPS") ? mpdata : branch.equals("GRS") ? grdata : null;

			int AvailableQty = tmpdata.getUnitsinstock() - tmpdata.getSafetyquantity();

			scanorderdetails.setChunitsinstock(chdata.getUnitsinstock());
			scanorderdetails.setChunitsonorder(chdata.getUnitsonorder());
			scanorderdetails.setChreorderlevel(chdata.getReorderlevel());
			scanorderdetails.setChsafetyquantity(chdata.getSafetyquantity());

			safetyQty = scanorderdetails.getChsafetyquantity() != null ? scanorderdetails.getChsafetyquantity() : 0;

			if (scanorderdetails.getChsafetyquantity() == null)
				scanorderdetails.setChsafetyquantity(0);

			if (!branch.equals("CHS")) {
				transferQty = calculateTransferQty(requiredQty,
						(chdata.getUnitsinstock() - chdata.getSafetyquantity()));

				requiredquantity.setChrequiredquanity(transferQty);

				requiredQty = requiredQty - transferQty;

			} else {
				requiredQty = safetyQty - chdata.getUnitsinstock();
				requiredquantity.setChrequiredquanity(requiredQty);
			}

			scanorderdetails.setGrunitsinstock(grdata.getUnitsinstock());
			scanorderdetails.setGrunitsonorder(grdata.getUnitsonorder());
			scanorderdetails.setGrreorderlevel(grdata.getReorderlevel());
			scanorderdetails.setGrsafetyquantity(grdata.getSafetyquantity());

			safetyQty = scanorderdetails.getGrsafetyquantity() != null ? scanorderdetails.getGrsafetyquantity() : 0;
			if (scanorderdetails.getGrsafetyquantity() == null)
				scanorderdetails.setGrsafetyquantity(0);

			if (!branch.equals("GRS")) {
				transferQty = calculateTransferQty(requiredQty,
						(grdata.getUnitsinstock() - grdata.getSafetyquantity()));

				requiredquantity.setGrrequiredquanity(transferQty);

				requiredQty = requiredQty - transferQty;
			} else {

				requiredQty = safetyQty - grdata.getUnitsinstock();
				requiredquantity.setGrrequiredquanity(requiredQty);
			}

			transferQty = calculateTransferQty(requiredQty, nydata.getUnitsinstock());

			requiredquantity.setNyrequiredquanity(transferQty);

			requiredQty = requiredQty - transferQty;

			scanorderdetails.setMpunitsinstock(mpdata.getUnitsinstock());
			scanorderdetails.setMpunitsonorder(mpdata.getUnitsonorder());
			scanorderdetails.setMpreorderlevel(mpdata.getReorderlevel());
			scanorderdetails.setMpsafetyquantity(mpdata.getSafetyquantity());

			safetyQty = scanorderdetails.getMpsafetyquantity() != null ? scanorderdetails.getMpsafetyquantity() : 0;
			if (scanorderdetails.getMpsafetyquantity() == null)
				scanorderdetails.setMpsafetyquantity(0);

			if (!branch.equals("MPS")) {
				transferQty = calculateTransferQty(requiredQty, mpdata.getUnitsinstock());

				requiredquantity.setMprequiredquanity(transferQty);

				requiredQty = requiredQty - transferQty;
			} else {
				requiredQty = safetyQty - mpdata.getUnitsinstock();
				requiredquantity.setMprequiredquanity(requiredQty);
			}
			scanorderdetailslist.add(scanorderdetails);
			requiredquanititylist.add(requiredquantity);
			requiredQty = 0;

		}

		session.flush();
		session.clear();
		return results;

	}

	public PaymentMaintenance getPaymentByBCCheckId(String invoiceNo) {

		int invNo = Integer.parseInt(invoiceNo);
		String SQL = "select concat( bc.CheckId,'') as invoiceNumber ,cust.CompanyName ,"
				+ "cust.CreditBalance , bc.BouncedAmount as appliedAmount , bc.balance from "
				+ " bouncedchecks bc, customer cust where bc.CheckId =:invoiceNo and bc.CustomerID=cust.CustomerId; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("invoiceNo", invNo))
				.setResultTransformer(Transformers.aliasToBean(PaymentMaintenance.class));

		PaymentMaintenance results = (PaymentMaintenance) query.uniqueResult();
		results.setInvoiceNumber("BC" + results.getInvoiceNumber());
		session.flush();
		session.clear();
		return results;
	}

	@Transactional
	public PaymentMaintenance getPaymentByInvNumber(String invoiceNo) {

		int invNo = 0;
		try {
			invNo = Integer.parseInt(invoiceNo);
		} catch (NumberFormatException ex) {
			return null;
		}

		String SQL = "select concat( inv.InvoiceNumber,'') as invoiceNumber ,cust.CompanyName ,"
				+ "cust.CreditBalance , inv.appliedAmount , inv.balance from "
				+ " Invoice inv, customer cust where InvoiceNumber =:invoiceNo and inv.CustomerID=cust.CustomerId; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("invoiceNo", invNo))
				.setResultTransformer(Transformers.aliasToBean(PaymentMaintenance.class));

		PaymentMaintenance results = (PaymentMaintenance) query.uniqueResult();
		session.flush();
		session.clear();
		return results;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getPendingBC() {

		HashMap<String, Object> pbcMap = new HashMap<String, Object>();
		Session session = sessionFactory.getCurrentSession();

		String SQL = "Select a.CheckId, b.CompanyName, a.Balance From BouncedChecks a, Customer b Where a.Balance!=0 and a.customerId=b.customerId";

		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		List<BouncedChecks> result = query.list();

		BigDecimal totBalance = new BigDecimal(0);

		for (BouncedChecks apl : result) {

			totBalance.add(apl.getBalance());
		}

		pbcMap.put("ds", result);
		pbcMap.put("totBalance", totBalance);
		session.flush();
		session.clear();
		return pbcMap;
	}

	public HashMap<String, BigDecimal> getPendingBCMap() {

		Session session = sessionFactory.getCurrentSession();

		String SQL = "Select CustomerId, Sum(Balance) as balance From BouncedChecks Where Balance!=0 Group By CustomerId Order By 1";

		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(BouncedChecks.class));

		@SuppressWarnings("unchecked")
		List<BouncedChecks> result = query.list();
		HashMap<String, BigDecimal> bcmap = new HashMap<String, BigDecimal>();
		for (BouncedChecks bc : result) {
			bcmap.put(bc.getCustomerid(), bc.getBalance());
		}
		return bcmap;
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> getPendingInvoices(String op, int pageNo, String invoiceNo) {

		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Invoice.class);

		cr.setProjection(Projections.projectionList().add(Projections.property("invoicenumber"), "invoicenumber")
				.add(Projections.property("invoicetotal"), "invoicetotal")
				.add(Projections.property("history"), "history").add(Projections.property("orderdate"), "orderdate"))
				.setResultTransformer(Transformers.aliasToBean(Invoice.class));

		Criterion notC = Restrictions.ne("status", 'C');
		Criterion notW = Restrictions.ne("status", 'W');
		Criterion invgt = Restrictions.ne("invoicenumber", 100);

		if (op.equals("searchInv") && !invoiceNo.equals("")) {
			Criterion invCheck = Restrictions.eq("invoicenumber", Integer.parseInt(invoiceNo));
			Conjunction invExp = Restrictions.and(invCheck, notC, notW, invgt);
			cr.add(invExp);
		} else if (op.equals("showAll")) {
			Conjunction andExp = Restrictions.and(notC, notW, invgt);
			cr.add(andExp);
		} else if (op.equals("showAll0")) {
			Criterion balCheck = Restrictions.eq("balance", new BigDecimal(0));
			Conjunction invExp = Restrictions.and(notC, notW, balCheck, invgt);
			cr.add(invExp);
		}

		// cr.setMaxResults(100);
		session.flush();
		session.clear();
		return cr.list();
	}

	@SuppressWarnings("unused")
	public Map getRouteSale(String route) {
		Session session = sessionFactory.getCurrentSession();
		//
		String sql = "SELECT MONTH(a.OrderDate) as saleMonth,YEAR(a.OrderDate) as saleYear, Sum(a.InvoiceTotal) as sale,Sum(a.tax) as tax,Sum(a.Discount) as discount FROM Invoice a, Address b WHERE "
				+ "a.OrderDate>=:startDate AND a.OrderDate<=:endDate and b.type='Standard' and b.Region=:route and a.CustomerId=b.Id "
				+ "GROUP BY MONTH(a.OrderDate) ,YEAR(a.OrderDate);";
		Calendar start = new GregorianCalendar();
		int currentYear = start.get(Calendar.YEAR);
		start.set(Calendar.DATE, 1);
		start.set(Calendar.MONTH, 0);
		start.set(Calendar.YEAR, currentYear - 4);

		Calendar end = new GregorianCalendar();
		// end.add(Calendar.MONTH, currentYear);
		// end.set(Calendar.MONTH,mnthCnt);
		// end.set(Calendar.DATE, start.getMaximum(Calendar.DATE));
		Date startDate = InsightUtils.getDate(start);
		Date endDate = InsightUtils.getDate(end);
		Query query = ((SQLQuery) session.createSQLQuery(sql).setParameter("route", route)
				.setParameter("startDate", startDate).setParameter("endDate", endDate)).addScalar("sale")
						.addScalar("tax").addScalar("discount").addScalar("saleMonth").addScalar("saleYear")
						.setResultTransformer(Transformers.aliasToBean(SaleByRouteData.class));

		List<SaleByRouteData> saleData = query.list();
		ArrayList<String> yearsList = new ArrayList<>();
		yearsList.add((currentYear - 4) + "");
		yearsList.add((currentYear - 3) + "");
		yearsList.add((currentYear - 2) + "");
		yearsList.add((currentYear - 1) + "");
		yearsList.add((currentYear) + "");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("yearsList", yearsList);
		// List<SaleByRouteMonthly> dataList=new ArrayList<>();
		Map<String, SaleByRouteMonthly> dataMap = new HashMap<String, SaleByRouteMonthly>();
		for (int mnthCnt = 1; mnthCnt <= 12; mnthCnt++) {
			SaleByRouteMonthly monthly = new SaleByRouteMonthly();
			if (mnthCnt == 1) {
				monthly.setMonth("Jan");
			} else if (mnthCnt == 2) {
				monthly.setMonth("Feb");
			} else if (mnthCnt == 3) {
				monthly.setMonth("Mar");
			} else if (mnthCnt == 4) {
				monthly.setMonth("Apr");
			} else if (mnthCnt == 5) {
				monthly.setMonth("May");
			} else if (mnthCnt == 6) {
				monthly.setMonth("Jun");
			} else if (mnthCnt == 7) {
				monthly.setMonth("Jul");
			} else if (mnthCnt == 8) {
				monthly.setMonth("Aug");
			} else if (mnthCnt == 9) {
				monthly.setMonth("Sep");
			} else if (mnthCnt == 10) {
				monthly.setMonth("Oct");
			} else if (mnthCnt == 11) {
				monthly.setMonth("Nov");
			} else if (mnthCnt == 12) {
				monthly.setMonth("Dec");
			}

			dataMap.put("m-" + mnthCnt, monthly);
			// SaleByRouteMonthly monthly=new SaleByRouteMonthly();

		}
		SaleByRouteMonthly summary = new SaleByRouteMonthly();
		for (SaleByRouteData data : saleData) {
			SaleByRouteMonthly monthly = dataMap.get("m-" + data.getSaleMonth());

			if (data.getSaleYear() == (currentYear - 4)) {
				monthly.setY1Discount(data.getDiscount());
				monthly.setY1Expenses(data.getExpenses());
				monthly.setY1Sale(data.getSale());
				monthly.setY1Tax(data.getTax());
				monthly.setY1Year(data.getSaleYear());
				monthly.setY1Exist(true);

				if (summary.getY1Sale() != null) {
					summary.setY1Sale(summary.getY1Sale().add(data.getSale()));
				} else {
					summary.setY1Sale(data.getSale());
				}
				if (summary.getY1Discount() != null) {
					summary.setY1Discount(summary.getY1Discount().add(data.getDiscount()));
				} else {
					summary.setY1Discount(data.getDiscount());
				}
				if (summary.getY1Expenses() != null) {
					summary.setY1Expenses(summary.getY1Expenses().add(data.getExpenses()));
				} else {
					summary.setY1Expenses(data.getExpenses());
				}

				if (summary.getY1Tax() != null) {
					summary.setY1Tax(summary.getY1Tax().add(data.getTax()));
				} else {
					summary.setY1Tax(data.getTax());
				}
				summary.setY1Year(data.getSaleYear());
				summary.setY1Exist(true);
			} else if (data.getSaleYear() == (currentYear - 3)) {
				monthly.setY2Discount(data.getDiscount());
				monthly.setY2Expenses(data.getExpenses());
				monthly.setY2Sale(data.getSale());
				monthly.setY2Tax(data.getTax());
				monthly.setY2Year(data.getSaleYear());
				monthly.setY2Exist(true);
				if (summary.getY2Sale() != null) {
					summary.setY2Sale(summary.getY2Sale().add(data.getSale()));
				} else {
					summary.setY2Sale(data.getSale());
				}
				if (summary.getY2Discount() != null) {
					summary.setY2Discount(summary.getY2Discount().add(data.getDiscount()));
				} else {
					summary.setY2Discount(data.getDiscount());
				}
				if (summary.getY2Expenses() != null) {
					summary.setY2Expenses(summary.getY2Expenses().add(data.getExpenses()));
				} else {
					summary.setY2Expenses(data.getExpenses());
				}

				if (summary.getY2Tax() != null) {
					summary.setY2Tax(summary.getY2Tax().add(data.getTax()));
				} else {
					summary.setY2Tax(data.getTax());
				}
				summary.setY2Year(data.getSaleYear());
				summary.setY2Exist(true);
			} else if (data.getSaleYear() == (currentYear - 2)) {
				monthly.setY3Discount(data.getDiscount());
				monthly.setY3Expenses(data.getExpenses());
				monthly.setY3Sale(data.getSale());
				monthly.setY3Tax(data.getTax());
				monthly.setY3Year(data.getSaleYear());
				monthly.setY3Exist(true);

				if (summary.getY3Sale() != null) {
					summary.setY3Sale(summary.getY3Sale().add(data.getSale()));
				} else {
					summary.setY3Sale(data.getSale());
				}
				if (summary.getY3Discount() != null) {
					summary.setY3Discount(summary.getY3Discount().add(data.getDiscount()));
				} else {
					summary.setY3Discount(data.getDiscount());
				}
				if (summary.getY3Expenses() != null) {
					summary.setY3Expenses(summary.getY3Expenses().add(data.getExpenses()));
				} else {
					summary.setY3Expenses(data.getExpenses());
				}

				if (summary.getY3Tax() != null) {
					summary.setY3Tax(summary.getY3Tax().add(data.getTax()));
				} else {
					summary.setY3Tax(data.getTax());
				}
				summary.setY3Year(data.getSaleYear());
				summary.setY3Exist(true);
			} else if (data.getSaleYear() == (currentYear - 1)) {
				monthly.setY4Discount(data.getDiscount());
				monthly.setY4Expenses(data.getExpenses());
				monthly.setY4Sale(data.getSale());
				monthly.setY4Tax(data.getTax());
				monthly.setY4Year(data.getSaleYear());
				monthly.setY4Exist(true);

				if (summary.getY4Sale() != null) {
					summary.setY4Sale(summary.getY4Sale().add(data.getSale()));
				} else {
					summary.setY4Sale(data.getSale());
				}
				if (summary.getY4Discount() != null) {
					summary.setY4Discount(summary.getY4Discount().add(data.getDiscount()));
				} else {
					summary.setY4Discount(data.getDiscount());
				}
				if (summary.getY4Expenses() != null) {
					summary.setY4Expenses(summary.getY4Expenses().add(data.getExpenses()));
				} else {
					summary.setY4Expenses(data.getExpenses());
				}

				if (summary.getY4Tax() != null) {
					summary.setY4Tax(summary.getY4Tax().add(data.getTax()));
				} else {
					summary.setY4Tax(data.getTax());
				}
				summary.setY4Year(data.getSaleYear());
				summary.setY4Exist(true);
			} else if (data.getSaleYear() == (currentYear)) {
				monthly.setY5Discount(data.getDiscount());
				monthly.setY5Expenses(data.getExpenses());
				monthly.setY5Sale(data.getSale());
				monthly.setY5Tax(data.getTax());
				monthly.setY5Year(data.getSaleYear());
				monthly.setY5Exist(true);

				if (summary.getY5Sale() != null) {
					summary.setY5Sale(summary.getY5Sale().add(data.getSale()));
				} else {
					summary.setY5Sale(data.getSale());
				}
				if (summary.getY5Discount() != null) {
					summary.setY5Discount(summary.getY5Discount().add(data.getDiscount()));
				} else {
					summary.setY5Discount(data.getDiscount());
				}
				if (summary.getY5Expenses() != null) {
					summary.setY5Expenses(summary.getY5Expenses().add(data.getExpenses()));
				} else {
					summary.setY5Expenses(data.getExpenses());
				}

				if (summary.getY5Tax() != null) {
					summary.setY5Tax(summary.getY5Tax().add(data.getTax()));
				} else {
					summary.setY5Tax(data.getTax());
				}
				summary.setY5Year(data.getSaleYear());
				summary.setY5Exist(true);
			}

		}
		List<SaleByRouteMonthly> dataList = new ArrayList<>();

		for (int mnthCnt = 1; mnthCnt <= 12; mnthCnt++) {
			dataList.add(dataMap.get("m-" + mnthCnt));
		}
		resultMap.put("dataList", dataList);
		resultMap.put("summary", summary);

		session.flush();
		session.clear();
		return resultMap;
	}

	public StockData getStockData(String partno) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(chdataSource);
			String SQL = "SELECT p.PartNo, p.UnitsInStock, p.UnitsOnOrder,p.reorderlevel, p.safetyquantity FROM parts p  WHERE p.PartNo = ?  ";
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

			return stock;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getTodaysChecks() {

		HashMap<String, Object> checkMap = new HashMap<String, Object>();
		Session session = sessionFactory.getCurrentSession();

		String SQL = "Select PaymentType, sum(AppliedAmount) as AppliedAmount From AppliedAmounts Where AppliedDate='"
				+ InsightUtils.getCurrentSQLDate() + "' Group By 1 Order by 1";

		Query query = session.createSQLQuery(SQL).setResultTransformer(Transformers.aliasToBean(AppliedAmounts.class));

		List<AppliedAmounts> result = query.list();
		int totalCount = 0;
		double totChkAmount = 0;
		double totCCAmount = 0;
		double totAmount = 0;
		for (AppliedAmounts apl : result) {
			if (apl.getPaymentType() == null || apl.getPaymentType().equals("")) {
				apl.setPaymentType("Cash");
			} else if (apl.getPaymentType().trim().indexOf("ap") == -1
					&& apl.getPaymentType().trim().indexOf("AP") == -1
					&& apl.getPaymentType().trim().indexOf("apr") == -1
					&& apl.getPaymentType().trim().indexOf("APR") == -1
					&& apl.getPaymentType().trim().indexOf("cr") == -1
					&& apl.getPaymentType().trim().indexOf("CR") == -1
					&& apl.getPaymentType().trim().indexOf("adj") == -1
					&& apl.getPaymentType().trim().indexOf("ADJ") == -1
					&& apl.getPaymentType().trim().indexOf("to") == -1
					&& apl.getPaymentType().trim().indexOf("TO") == -1) {
				totChkAmount += apl.getAppliedAmount().doubleValue();
			} else if (apl.getPaymentType().trim().startsWith("APR")) {
				totCCAmount += apl.getAppliedAmount().doubleValue();
			}
			totAmount += apl.getAppliedAmount().doubleValue();
			totalCount++;
		}
		checkMap.put("ds", result);
		checkMap.put("totalCount", totalCount);
		checkMap.put("totAmount", totAmount);
		checkMap.put("totChkAmount", totChkAmount);
		checkMap.put("totCCAmount", totCCAmount);
		session.flush();
		session.clear();
		return checkMap;
	}

	public Writeoff getWriteOffByInvNo(String invoiceNo) {

		int invNo = Integer.parseInt(invoiceNo);
		Session session = sessionFactory.getCurrentSession();
		Writeoff results = null;
		// LOGGER.info(invNo + "__________");

		String SQLinv = "select  inv.InvoiceNumber as invoiceNo ,cust.CompanyName as companyName,"
				+ " inv.balance as amount from "
				+ " Invoice inv, customer cust where InvoiceNumber =:invoiceNo and inv.CustomerID=cust.CustomerId; ";

		Query queryinv = ((SQLQuery) session.createSQLQuery(SQLinv).setParameter("invoiceNo", invNo))
				.setResultTransformer(Transformers.aliasToBean(Writeoff.class));

		results = (Writeoff) queryinv.uniqueResult();

		String SQL = "Select invoiceNo as invoiceNo,writeOffDate as writeOffDate, notes as notes "
				+ "From writeoff Where invoiceNo =?; ";
		Query query = ((SQLQuery) session.createSQLQuery(SQL)// .setParameter("invoNo",
																// invNo))
				.setInteger(0, invNo)).setResultTransformer(Transformers.aliasToBean(Writeoff.class));
		Writeoff resultsb = (Writeoff) query.uniqueResult();
		if (resultsb != null) {
			results.setWriteOffDate(resultsb.getWriteOffDate());
			results.setNotes(resultsb.getNotes());
		}
		if (results.getWriteOffDate() == null || results.getWriteOffDate().equals("")) {
			results.setWriteOffDate(InsightUtils.getCurrentSQLDate());
		}

		session.flush();
		session.clear();
		return results;
	}

	public List<Writeoff> getWriteOffInvoices(String startDate, String endDate) {

		String SQL = "Select a.InvoiceNo, a.WriteOffDate, a.Notes, b.OrderDate, b.Balance as Amount, c.CompanyName "
				+ " From WriteOff a, Invoice b, Customer c  Where ";
		if (!startDate.equals("") && !endDate.equals("")) {
			SQL += " a.WriteOffDate >=:startDate And a.WriteOffDate <=:endDate  AND ";
		}
		SQL += " a.InvoiceNo=b.InvoiceNumber and b.CustomerId=c.CustomerId Order By 1 ; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createSQLQuery(SQL);
		if (!startDate.equals("") && !endDate.equals("")) {
			query.setParameter("startDate", startDate).setParameter("endDate", endDate);
		}

		query.setResultTransformer(Transformers.aliasToBean(Writeoff.class));

		@SuppressWarnings("unchecked")
		List<Writeoff> results = query.list();
		// LOGGER.info("results--" + results.size());
		session.flush();
		session.clear();
		return results;
	}

	public boolean isPaymentExist(PaymentMaintenance entity) {

		String aplDt = entity.getAppliedDate();
		if (entity.getAppliedDate() == null || entity.getAppliedDate().equals("")) {
			aplDt = InsightUtils.getCurrentSQLDate().toString();
		}
		String SQL = "select count(1) as count from appliedamounts Where InvoiceNumber =:invoiceNo "
				+ " And AppliedDate =:appDate" + " And PaymentType =:pType And AppliedAmount =:appAmt  ;";
		Session session = sessionFactory.getCurrentSession();

		Query query = (session.createSQLQuery(SQL).setParameter("invoiceNo", entity.getInvoiceNumber())
				.setParameter("appDate", aplDt).setParameter("pType", entity.getPaymentType())
				.setParameter("appAmt", entity.getPayingAmount()));
		Integer results = ((Number) query.uniqueResult()).intValue();
		session.flush();
		session.clear();
		return (results == 1) ? true : false;
	}

	private List<BouncedChecks> processPayable(List<BouncedChecks> result) {

		List<BouncedChecks> lst = new ArrayList<BouncedChecks>();
		String cust = "";
		BigDecimal balance = new BigDecimal(0);
		BigDecimal sumBalance = new BigDecimal(0);
		for (BouncedChecks list : result) {
			if (cust.equals(list.getCustomerid())) {
				sumBalance = sumBalance.add(list.getBalance());
			} else {
				balance = list.getBalance();
				if (!cust.equals("") && sumBalance.doubleValue() < 0) {
					BouncedChecks bc = new BouncedChecks();
					bc.setCustomerid(cust);
					bc.setCompanyname(getCustomerById(cust).getCompanyname());
					bc.setBalance(sumBalance);
					lst.add(bc);
				}
				cust = list.getCustomerid();
				sumBalance = balance;
			}
		}
		if (result.size() > 0 && sumBalance.doubleValue() < 0) {

			BouncedChecks bc = new BouncedChecks();
			bc.setCustomerid(cust);
			bc.setCompanyname(getCustomerById(cust).getCompanyname());
			bc.setBalance(sumBalance);
			lst.add(bc);

		}
		return lst;
	}

	private List<BouncedChecks> processRecievable(List<BouncedChecks> result) {

		List<BouncedChecks> lst = new ArrayList<BouncedChecks>();
		String cust = "";
		BigDecimal balance = new BigDecimal(0);
		BigDecimal sumBalance = new BigDecimal(0);
		for (BouncedChecks list : result) {
			if (cust.equals(list.getCustomerid())) {
				sumBalance = sumBalance.add(list.getBalance());
			} else {
				balance = list.getBalance();
				if (!cust.equals("") && sumBalance.doubleValue() > 0) {
					BouncedChecks bc = new BouncedChecks();
					bc.setCustomerid(cust);
					bc.setCompanyname(getCustomerById(cust).getCompanyname());
					bc.setBalance(sumBalance);
					lst.add(bc);
				}
				cust = list.getCustomerid();
				sumBalance = balance;
			}
		}
		if (result.size() > 0 && sumBalance.doubleValue() > 0) {
			BouncedChecks bc = new BouncedChecks();
			bc.setCustomerid(cust);
			bc.setCompanyname(getCustomerById(cust).getCompanyname());
			bc.setBalance(sumBalance);
			lst.add(bc);
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	public Invoice setInvPaymentDetails(Invoice inv, int termDays) {

		String SQL = "SELECT  concat('',DATEDIFF( AppliedDate,:orderDate) ) as paymentTime,appliedDate,appliedAmount,paymentType from AppliedAmounts "
				+ " Where InvoiceNumber =:invoiceNo ; ";
		Session session = sessionFactory.getCurrentSession();

		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("orderDate", inv.getOrderdate())
				.setParameter("invoiceNo", inv.getInvoicenumber()))
						.setResultTransformer(Transformers.aliasToBean(AppliedAmounts.class));

		List<AppliedAmounts> amtlist = query.list();
		@SuppressWarnings("unused")
		String remarks = "", paydetails = "";
		int termdiff = 0;
		for (AppliedAmounts amt : amtlist) {
			if (!amt.getPaymentTime().equals("") && Integer.parseInt(amt.getPaymentTime()) > 0) {
				termdiff = Integer.parseInt(amt.getPaymentTime());
				if ((termdiff - termDays) > 0)
					remarks += "PAID LATE BY " + (termdiff - termDays) + " days,";
			}
			if (amt.getPaymentType().equals("")) {
				paydetails += "Cash Amt - " + amt.getAppliedAmount() + " On "
						+ InsightUtils.convertMySQLToUSFormat(amt.getAppliedDate().toString()) + "----";
			} else if (amt.getPaymentType().startsWith("apto") || amt.getPaymentType().startsWith("ap to")
					|| amt.getPaymentType().startsWith("apcr") || amt.getPaymentType().startsWith("ap cr")) {
				paydetails += "Adjustment - " + amt.getPaymentType() + " -- Amt   " + amt.getAppliedAmount() + " On "
						+ InsightUtils.convertMySQLToUSFormat(amt.getAppliedDate().toString()) + ",";
			} else {
				paydetails += "Check # " + amt.getPaymentType() + " -- Amt - " + amt.getAppliedAmount() + " On "
						+ InsightUtils.convertMySQLToUSFormat(amt.getAppliedDate().toString()) + ",";
			}
		}
		inv.setNotes(remarks);

		session.flush();
		session.clear();
		return inv;
	}

	@SuppressWarnings("unused")
	public String updateBouncedCheck(BouncedChecks entity) {

		String msg = "";
		if (entity.getCheckid() == null || entity.getCheckid().equals("")) {
			return "Please enter Check Id.";
		}
		if (entity.getCustomerid() == null || entity.getCustomerid().equals("")) {
			return "Please enter Customer Id.";
		}
		if (entity.getCheckno() == null || entity.getCheckno().equals("")) {
			return "Please enter Check No.";
		}
		if (entity.getCheckdate() == null) {
			return "Please enter check date.";
		}
		BouncedChecks bc = getBouncedChecksById(entity.getCheckid() + "");
		if (bc == null || !bc.getCustomerid().equals(entity.getCustomerid())) {
			return "Check id:" + entity.getCheckid() + " not found";
		}
		bc.setBouncedamount(entity.getBouncedamount());
		bc.setCheckno(entity.getCheckno());
		bc.setCheckdate(entity.getCheckdate());
		bc.setIscleared(entity.getIscleared() == null ? "N" : entity.getIscleared());
		bc.setEntereddate(InsightUtils.getCurrentSQLDate());
		bc.setPaidamount(entity.getPaidamount());
		bc.setBalance(entity.getBalance() == null ? (new BigDecimal(0)) : entity.getBalance());
		if (bc.getPaidamount().compareTo(BigDecimal.ZERO) != 0) {
			bc.setBalance(bc.getBouncedamount().add(new BigDecimal(25.0)).subtract(bc.getPaidamount()));
		} else {
			bc.setBalance(bc.getBouncedamount().add(new BigDecimal(25.0)));
		}
		Customer cust = getCustomerById(entity.getCustomerid());
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(bc);
		cust.setCreditbalance(cust.getCreditbalance().add(bc.getBalance()));
		session.update(cust);
		session.flush();
		session.clear();
		return "THIS CHECK CHANGED SUCCESSFULLY";
	}

}
