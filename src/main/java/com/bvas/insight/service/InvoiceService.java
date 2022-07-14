package com.bvas.insight.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.DisplayInvoice;
import com.bvas.insight.data.DisplayInvoiceDetail;
import com.bvas.insight.data.InvoiceStatus;
import com.bvas.insight.entity.Address;
import com.bvas.insight.entity.BouncedChecks;
import com.bvas.insight.entity.Customer;
import com.bvas.insight.entity.Invoice;
import com.bvas.insight.entity.InvoiceDetails;
import com.bvas.insight.entity.LevelCalc;
import com.bvas.insight.entity.MakeModel;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.utilities.AppUser;

@Repository
@SuppressWarnings("unchecked")
public class InvoiceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

	protected BigDecimal bigdecimalminusone = new BigDecimal("-1.00");

	protected BigDecimal bigdecimalzero = new BigDecimal("0.00");

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public synchronized String addInvoice(DisplayInvoice displayinvoice, Customer customer, Address billtoaddress,
			Address shiptoaddress) {

		String SQL = "SELECT max(invoicenumber) FROM invoice;";
		Session session = sessionFactory.getCurrentSession();

		Query query = (session.createSQLQuery(SQL));
		Integer max = ((Number) query.uniqueResult()).intValue();

		// LOGGER.info("max:" + max);
		Integer createdinvoice = max + 1;
		Invoice invoice = new Invoice();

		invoice.setInvoicenumber(max + 1);
		invoice.setAppliedamount(displayinvoice.getAppliedamount());
		invoice.setBalance(displayinvoice.getBalance());
		invoice.setBillattention(customer.getContactname());
		invoice.setCustomerid(customer.getCustomerid());
		invoice.setDatenewapplied(displayinvoice.getOrderdate());
		invoice.setDiscount(displayinvoice.getDiscount());
		invoice.setDiscounttype(displayinvoice.getDiscounttype());
		invoice.setHistory("N");
		invoice.setInvoicetime(displayinvoice.getInvoicetime());
		invoice.setInvoicetotal(displayinvoice.getInvoicetotal());
		invoice.setIsdelivered(displayinvoice.getIsdelivered());
		invoice.setIsprinted(displayinvoice.getIsprinted());
		invoice.setNewapplied(displayinvoice.getNewapplied());
		invoice.setNotes(displayinvoice.getNotes());
		invoice.setOrderdate(displayinvoice.getOrderdate());
		invoice.setPaymentterms(customer.getPaymentterms());
		invoice.setReturnedinvoice(0);
		invoice.setSalesperson(displayinvoice.getSalesperson());
		invoice.setShipattention(displayinvoice.getShipattention());
		invoice.setShipto(displayinvoice.getShipto());
		invoice.setStatus(displayinvoice.getStatus());
		invoice.setTax(displayinvoice.getTax());
		invoice.setShipvia(displayinvoice.getShipvia());

		session.save(invoice);
		shiptoaddress.setInvoicenumber(max + 1);
		shiptoaddress.setId(customer.getCustomerid());
		session.saveOrUpdate(shiptoaddress);

		List<DisplayInvoiceDetail> displayinvoicedetails = displayinvoice.getdisplayinvoicedetailslist();

		for (DisplayInvoiceDetail dpinvoicedetail : displayinvoicedetails) {
			InvoiceDetails invoicedetails = new InvoiceDetails();

			invoicedetails.setInvoicenumber(max + 1);
			invoicedetails.setPartnumber(dpinvoicedetail.getPartnumber());
			invoicedetails.setQuantity(dpinvoicedetail.getQuantity());
			invoicedetails.setActualprice(dpinvoicedetail.getActualprice());
			invoicedetails.setSoldprice(dpinvoicedetail.getSoldprice());

			session.saveOrUpdate(invoicedetails);

		}

		session.flush();
		session.clear();
		return createdinvoice.toString();

	}

	private void addInvoiceDetails(List<DisplayInvoiceDetail> displayinvoicedetailslist) {
		Session session = sessionFactory.getCurrentSession();
		// LOGGER.info("inv det in Serv 1=" + displayinvoicedetailslist.size());
		for (DisplayInvoiceDetail item : displayinvoicedetailslist) {
			// LOGGER.info("inv det in Serv partNo=" + item.getPartnumber());
			InvoiceDetails invItem = new InvoiceDetails();
			Parts part = getPartInfo(item.getPartnumber());

			if ((part == null) && item.getPartnumber().startsWith("XX")) {
				part = getPartInfo(item.getPartnumber().substring(2));
			}
			if (part.getUnitsinstock() > 0) {
				// LOGGER.info("inv det in Serv partNo 1=");
				if (!part.getActualprice().equals(BigDecimal.ZERO)) {
					// LOGGER.info("inv det in Serv partNo 2=");
					invItem.setActualprice(part.getActualprice());
				} else if ((part.getInterchangeno() != null) && !part.getInterchangeno().trim().equals("")
						&& (!getPartInfo(part.getInterchangeno()).getActualprice().equals(BigDecimal.ZERO))) {
					// LOGGER.info("inv det in Serv partNo 3=");
					invItem.setActualprice(getPartInfo(part.getInterchangeno()).getActualprice());
				} else {
					// LOGGER.info("inv det in Serv partNo 4=");
					List<Parts> v = getAllInterChangeParts(part.getPartno());

					if (v == null) {
						// LOGGER.info("inv det in Serv partNo 5=");
						invItem.setActualprice(BigDecimal.ZERO);
					} else {
						// LOGGER.info("inv det in Serv partNo 6=");
						for (Parts p : v) {
							// LOGGER.info("inv det in Serv partNo 7=");
							if (!p.getActualprice().equals(BigDecimal.ZERO)) {
								// LOGGER.info("inv det in Serv partNo 8=");
								invItem.setActualprice(p.getActualprice());
								break;
							}
							// LOGGER.info("inv det in Serv partNo 9=");
						}
					}
				}
			} else {
				// LOGGER.info("inv det in Serv partNo 10=");
				invItem.setActualprice(BigDecimal.ZERO);
			}
			// LOGGER.info("11-1=" + item.getActualprice());
			invItem.setInvoicenumber(item.getInvoicenumber());
			invItem.setPartnumber(item.getPartnumber());
			invItem.setActualprice(item.getActualprice());
			invItem.setQuantity(item.getQuantity());
			invItem.setSoldprice(item.getSoldprice());
			// LOGGER.info("inv det in Serv partNo 11=" + invItem.getActualprice() + "=" +
			// invItem.getInvoicenumber() + "=" + invItem.getPartnumber() + "=" +
			// invItem.getQuantity() + "=" + invItem.getSoldprice());
			session.save(invItem);
			int qty = getQuantity(invItem.getPartnumber());
			if (!invItem.getPartnumber().startsWith("XX")) {
				qty -= invItem.getQuantity();
				changeQuantity(invItem.getPartnumber(), qty);
			}
		}
	}

	private void addNewAddress(Address address) {
		Session session = sessionFactory.getCurrentSession();

		String insertSQL = "INSERT INTO Address (Id, Type, Who, DateCreated, Addr1, Addr2, City, State, Region, PostalCode,"
				+ " Country, Phone, Ext, Fax, InvoiceNumber)"
				+ " VALUES (:Id, :Type, :Who, :DateCreated, :Addr1, :Addr2, :City, :State, :Region, :PostalCode,"
				+ " :Country, :Phone, :Ext, :Fax, :InvoiceNumber)";

		boolean emptyAddress = false;
		if (address.getType().trim().equalsIgnoreCase("Ship") && address.getAddr1().trim().equals("")
				&& address.getAddr2().trim().equals("") && address.getCity().trim().equals("")
				&& address.getState().trim().equals("") && address.getRegion().trim().equals("")
				&& address.getPostalcode().trim().equals("") && address.getCountry().trim().equals("")
				&& address.getPhone().trim().equals("") && address.getExt().trim().equals("")
				&& address.getFax().trim().equals("")) {
			emptyAddress = true;
		}
		if (address.getType().trim().equalsIgnoreCase("Bill")) {
			emptyAddress = true;
		}
		if (!emptyAddress) {
			SQLQuery addressInsertQuery = session.createSQLQuery(insertSQL);
			addressInsertQuery.setParameter("Id", address.getId());
			addressInsertQuery.setParameter("Type", address.getType());
			addressInsertQuery.setParameter("Who", address.getWho());
			addressInsertQuery.setParameter("DateCreated", address.getDatecreated());
			addressInsertQuery.setParameter("Addr1", address.getAddr1());
			addressInsertQuery.setParameter("Addr2", address.getAddr2());
			addressInsertQuery.setParameter("City", address.getCity());
			addressInsertQuery.setParameter("State", address.getState());
			addressInsertQuery.setParameter("Region", address.getRegion());
			addressInsertQuery.setParameter("PostalCode", address.getPostalcode());
			addressInsertQuery.setParameter("Country", address.getCountry());
			addressInsertQuery.setParameter("Phone", address.getPhone());
			addressInsertQuery.setParameter("Ext", address.getExt());
			addressInsertQuery.setParameter("Fax", address.getFax());
			addressInsertQuery.setParameter("InvoiceNumber", address.getInvoicenumber());
			@SuppressWarnings("unused")
			int executeUpdate = addressInsertQuery.executeUpdate();
		}

	}

	private void changeAddress(Address address) {
		Session session = sessionFactory.getCurrentSession();
		if (!address.getType().trim().equalsIgnoreCase("Bill")) {

			String query = "Select count(*) from Address address where address.Id=:addId AND address.Who=:addWho AND address.Type=:addType";
			if (address.getInvoicenumber() != null && address.getInvoicenumber() != 0) {
				query += " AND InvoiceNumber=:addInvNo";
			}
			SQLQuery isAvailableQuery = session.createSQLQuery(query);
			isAvailableQuery.setParameter("addId", address.getId());
			isAvailableQuery.setParameter("addWho", address.getWho());
			isAvailableQuery.setParameter("addType", address.getType());
			if (address.getInvoicenumber() != null && address.getInvoicenumber() != 0) {
				isAvailableQuery.setParameter("addInvNo", address.getInvoicenumber());
			}

			Integer isAvailableCount = ((Number) isAvailableQuery.uniqueResult()) == null ? 0
					: ((Number) isAvailableQuery.uniqueResult()).intValue();
			if (isAvailableCount == 0) {
				addNewAddress(address);
			} else {
				String changeSQL = "UPDATE Address set DateCreated=:DateCreated,Addr1=:Addr1, Addr2=:Addr2, City=:City,"
						+ " State=:State, Region=:Region, PostalCode=:PostalCode, Country=:Country, Phone=:Phone,"
						+ " Ext=:Ext, Fax=:Fax WHERE Id=:Id AND Type=:Type AND Who=:Who ";
				if (address.getInvoicenumber() != null && address.getInvoicenumber() != 0) {
					changeSQL += " AND InvoiceNumber=:invNo";
				}
				SQLQuery addressUpdateQuery = session.createSQLQuery(changeSQL);
				addressUpdateQuery.setParameter("DateCreated", address.getDatecreated());
				addressUpdateQuery.setParameter("Addr1", address.getAddr1());
				addressUpdateQuery.setParameter("Addr2", address.getAddr2());
				addressUpdateQuery.setParameter("City", address.getCity());
				addressUpdateQuery.setParameter("State", address.getState());
				addressUpdateQuery.setParameter("Region", address.getRegion());
				addressUpdateQuery.setParameter("PostalCode", address.getPostalcode());
				addressUpdateQuery.setParameter("Country", address.getCountry());
				addressUpdateQuery.setParameter("Phone", address.getPhone());
				addressUpdateQuery.setParameter("Ext", address.getExt());
				addressUpdateQuery.setParameter("Fax", address.getFax());
				addressUpdateQuery.setParameter("Id", address.getId());
				addressUpdateQuery.setParameter("Type", address.getId());
				addressUpdateQuery.setParameter("Who", address.getWho());
				if (address.getInvoicenumber() != null && address.getInvoicenumber() != 0) {
					addressUpdateQuery.setParameter("invNo", address.getInvoicenumber());
				}
				@SuppressWarnings("unused")
				int executeUpdate = addressUpdateQuery.executeUpdate();
			}
		}
	}

	@Transactional
	public void changeInvoice(Invoice oldInvoice, DisplayInvoice newInvoice, List<InvoiceDetails> oldInvoiceDetails,
			Address billtoaddress, Address shiptoaddress) {
		Session session = sessionFactory.getCurrentSession();

		Query custQuery = session.createQuery("From Customer customer  where customer.customerid =:customerid");
		custQuery.setParameter("customerid", oldInvoice.getCustomerid());
		Customer cust = (Customer) custQuery.uniqueResult();
		// LOGGER.info("Insie credit balance" + cust.getCreditbalance() + "------" +
		// oldInvoice.getBalance() + "----"+
		// cust.getCreditbalance().subtract(oldInvoice.getBalance()));
		// cust.setCreditbalance(cust.getCreditbalance().subtract(oldInvoice.getBalance()));
		// java.util.Date ordDate = oldInvoice.getOrderdate();

		String query = "UPDATE Invoice set OrderDate=:OrderDate, ShipTo=:ShipTo, ShipAttention=:ShipAttention, ShipVia=:ShipVia,"
				+ "BillAttention=:BillAttention,"
				+ "InvoiceTotal=:InvoiceTotal, AppliedAmount=:AppliedAmount, NewApplied=:NewApplied, "
				// + "DateNewApplied=:DateNewApplied, "
				+ "Balance=:Balance, Tax=:Tax, Discount=:Discount, ReturnedInvoice=:ReturnedInvoice, Notes=:Notes,"
				+ "Status=:Status, History=:History, IsPrinted=:IsPrinted "
				+ "WHERE InvoiceNumber=:InvoiceNumber AND CustomerId=:CustomerId";
		SQLQuery invUpdateQuery = session.createSQLQuery(query);

		String query2 = "SELECT SUM(AppliedAmount) FROM AppliedAmounts where InvoiceNumber=:invNo";
		SQLQuery sumAppAmQuery = session.createSQLQuery(query2);
		sumAppAmQuery.setParameter("invNo", oldInvoice.getInvoicenumber());
		double applAmounts = ((Number) sumAppAmQuery.uniqueResult()) == null ? 0.0
				: ((Number) sumAppAmQuery.uniqueResult()).doubleValue();
		if (applAmounts != 0) {
			newInvoice.setAppliedamount(BigDecimal.valueOf(applAmounts));
			newInvoice.setBalance(newInvoice.getBalance().subtract(newInvoice.getAppliedamount()));
		}
		invUpdateQuery.setParameter("OrderDate", newInvoice.getOrderdate());
		invUpdateQuery.setParameter("ShipTo", newInvoice.getShipto());
		invUpdateQuery.setParameter("ShipAttention", newInvoice.getShipattention());
		invUpdateQuery.setParameter("ShipVia", newInvoice.getShipvia());
		invUpdateQuery.setParameter("BillAttention", newInvoice.getBillAttention());
		// pstmt.setString(5, getBillAttention());
		invUpdateQuery.setParameter("InvoiceTotal", newInvoice.getInvoicetotal());
		invUpdateQuery.setParameter("AppliedAmount", newInvoice.getAppliedamount());
		invUpdateQuery.setParameter("NewApplied", newInvoice.getNewapplied());
		// invUpdateQuery.setParameter("DateNewApplied", );
		invUpdateQuery.setParameter("Balance", newInvoice.getBalance());
		invUpdateQuery.setParameter("Tax", newInvoice.getTax());
		invUpdateQuery.setParameter("Discount", newInvoice.getDiscount());
		invUpdateQuery.setParameter("ReturnedInvoice", newInvoice.getReturnedinvoice());
		invUpdateQuery.setParameter("Notes", newInvoice.getNotes());
		invUpdateQuery.setParameter("Status", newInvoice.getStatus());
		invUpdateQuery.setParameter("History", 'Y');
		invUpdateQuery.setParameter("IsPrinted", 'N');
		invUpdateQuery.setParameter("InvoiceNumber", newInvoice.getInvoicenumber());
		invUpdateQuery.setParameter("CustomerId", cust.getCustomerid());
		@SuppressWarnings("unused")
		int executeUpdate = invUpdateQuery.executeUpdate();

		changeAddress(shiptoaddress);
		changeAddress(billtoaddress);

		for (InvoiceDetails iBean : oldInvoiceDetails) {
			if (!iBean.getPartnumber().startsWith("XX")) {
				int qty = getQuantity(iBean.getPartnumber()) + iBean.getQuantity();
				changeQuantity(iBean.getPartnumber(), qty);
			}
		}
		deleteInvoiceDetails(newInvoice.getInvoicenumber());
		// LOGGER.info("inv details size==" +
		// newInvoice.getDisplayinvoicedetailslist().size());
		addInvoiceDetails(newInvoice.getDisplayinvoicedetailslist());
		// LOGGER.info("Crdeit bal-----" + cust.getCreditbalance() + "--------" +
		// newInvoice.getBalance() + "--------"+
		// cust.getCreditbalance().add(newInvoice.getBalance()));
		// cust.setCreditbalance(cust.getCreditbalance().add(newInvoice.getBalance()));
		session.update(cust);
		session.flush();
		session.clear();
	}

	private void changeQuantity(String partNo, int qty) {
		Session session = sessionFactory.getCurrentSession();
		Parts p = getPartInfo(partNo);
		if (p.getInterchangeno() == null || p.getInterchangeno().trim().equals("")) {
			p.setUnitsinstock(qty);
			// LOGGER.info("Class:InvoiceService, method:changeQuantity(), partNo" +
			// p.getPartno() + ",set unitsinstock:" + p.getUnitsinstock());
			session.save(p);
		} else {
			changeQuantity(p.getInterchangeno(), qty);
		}
	}

	@Transactional
	public Boolean checkCredit(Customer customer) {

		Boolean creditverified = false;
		@SuppressWarnings("unused")
		BigDecimal zero = new BigDecimal("0");
		BigDecimal one = new BigDecimal("1");
		// LOGGER.info("creditlimit" + customer.getCreditlimit());
		// LOGGER.info("creditbalance" + customer.getCreditbalance());
		if ((customer.getCreditlimit().compareTo(one) > 0)
				&& (customer.getCreditbalance().compareTo(customer.getCreditlimit()) <= 0)) {
			@SuppressWarnings("unused")
			BigDecimal creditorbalance = getCreditorBalance(customer);
			// LOGGER.info("creditorbalance" + customer.getCreditlimit());

			creditverified = true;
		} else if (customer.getCreditlimit().compareTo(one) == 0) {
			creditverified = false;
		} else {
			creditverified = false;
		}

		return creditverified;

	}

	private double checkDetailedDiscount(int discPerc, List<DisplayInvoiceDetail> detailsList) {
		double newDisc = 0.0;
		double costPrice = 0.0;
		double actPrice = 0.0;
		for (DisplayInvoiceDetail bean : detailsList) {
			Parts part = null;
			if (!bean.getPartnumber().startsWith("XX")) {
				part = getPartInfo(bean.getPartnumber());
				if (part.getUnitsinstock() > 0 && bean.getQuantity() > 0) {
					int inStock = part.getUnitsinstock() - bean.getQuantity();
					if (inStock >= 0) {
						costPrice += bean.getSoldprice().doubleValue() * bean.getQuantity();
						actPrice += part.getActualprice().doubleValue() * bean.getQuantity();
					} else {
						costPrice += bean.getSoldprice().doubleValue() * inStock * -1;
						actPrice += part.getActualprice().doubleValue() * inStock * -1;
					}
				}
			} else {
				continue;
			}
		}
		double perc = ((costPrice - actPrice) / costPrice) * 100;
		if (perc > (discPerc + 10)) {
			newDisc = costPrice * discPerc / 100;
		}
		return newDisc;
	}

	private double checkPromoDiscount(List<DisplayInvoiceDetail> detailsList) {
		Session session = sessionFactory.getCurrentSession();
		double newDisc = 0.0;
		double totDiscount = 0.0;
		for (DisplayInvoiceDetail bean : detailsList) {
			Parts part = null;
			if (!bean.getPartnumber().startsWith("XX")) {
				part = getPartInfo(bean.getPartnumber());
				if (part.getUnitsinstock() > 0 && bean.getQuantity() > 0) {
					int inStock = part.getUnitsinstock() - bean.getQuantity();
					if (inStock >= 0) {
						double costPrice = bean.getSoldprice().doubleValue() * bean.getQuantity();
						String query = "Select SalePrice,FromDate,ToDate from SaleParts Where PartNo=:PartNo";
						SQLQuery salePartQuery = session.createSQLQuery(query);
						salePartQuery.setParameter("PartNo", bean.getPartnumber());
						salePartQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
						List<Object> data = salePartQuery.list();
						if (data != null && !data.isEmpty()) {
							Map<Object, Object> row = (Map<Object, Object>) data.get(0);
							double prce = (double) row.get("SalePrice");
							Date dd1 = (Date) row.get("FromDate");
							Date dd2 = (Date) row.get("ToDate");
							long fromDate = dd1.getTime();
							long toDate = dd2.getTime() + 86400000;
							long currTime = System.currentTimeMillis();
							if (currTime > fromDate && currTime < toDate) {
								totDiscount += (costPrice - (prce * bean.getQuantity()));
							}
						} else {
							continue;
						}
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}

		return totDiscount;
	}

	@Transactional
	public String createAsHtml(Invoice invoice, List<InvoiceDetails> invoiceDetails, Customer customer,
			Address billToAddress, Address shipToAddress, String repository) {
		// String fileName=invoice.getInvoicenumber()+ " - "+(new
		// java.util.Date()).getTime()+".html";
		String fileName = invoice.getInvoicenumber() + "";
		try {
			File fileHtml = new File(repository + "htmlinvoice/" + fileName + ".html");
			FileWriter ft = new FileWriter(fileHtml);
			ft.write("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
			ft.write("<HTML>");
			ft.write("<HEAD>");
			ft.write("<meta http-equiv='Content-type' content='text/html;charset=UTF-8'>");
			ft.write("<TITLE>invoiceprint.html</TITLE>");
			ft.write("</HEAD>");
			ft.write(
					"<script language='JavaScript'>	function PrintPage() {	window.print();/*	window.close();*/ }</script>");
			ft.write("<BODY onload='PrintPage()'>");
			ft.write("<P><BR>");
			ft.write("<BR>");
			ft.write("<BR>");
			ft.write("<BR>");
			ft.write("</P>");
			ft.write("<TABLE width='850' height='625'>");
			ft.write("<TBODY>");
			ft.write("<TR valign='bottom'>");
			ft.write("<TD width='787' valign='bottom'>");

			ft.write("<TABLE width='764' height='25'>");
			ft.write("<TBODY>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");

			String invoiceNoStr = invoice.getInvoicenumber() + "";
			if (invoice.getReturnedinvoice() != 0) {
				invoiceNoStr = "C" + invoiceNoStr;
			}
			String paymentTerms = "";

			// CustomerBean custBean =
			// CustomerBean.getCustomer(invoice.getCustomerId());
			if (customer.getPaymentterms().equals("O")) {
				paymentTerms = "* CASH ONLY *";
			} else if (customer.getPaymentterms().equals("C")) {
				paymentTerms = " ** COD ** ";
			} else if (customer.getPaymentterms().equals("B")) {
				paymentTerms = " * BI-WKLY * ";
			} else if (customer.getPaymentterms().equals("W")) {
				paymentTerms = " * WKLY * ";
			} else if (customer.getPaymentterms().equals("M")) {
				paymentTerms = " * MTHLY * ";
			}

			ft.write("<TR>");
			ft.write("<TD width='119' align='center'></TD>");
			ft.write("<TD width='142'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>");
			ft.write("<TD width='125'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>");
			ft.write("<TD width='120'></TD>");
			ft.write(
					"<TD style='font-size: 16pt;' width='145' align='left'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>");
			ft.write("</TR>");

			ft.write("<TR>");
			ft.write("<TD width='119' align='center'></TD>");
			ft.write("<TD width='142'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>");
			ft.write("<TD width='125'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>");
			ft.write("<TD width='120'></TD>");
			ft.write(
					"<TD style='font-size: 16pt;' width='145' align='left'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>");
			ft.write("</TR>");

			ft.write("<TR>");
			ft.write("<TD width='119' align='center'>" + invoice.getSalesperson().toUpperCase() + "</TD>");
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy h:mm a");
			ft.write("<TD width='142'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ sdf.format(new java.util.Date((Long) invoice.getInvoicetime())) + "</TD>");
			ft.write("<TD width='125'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + customer.getCustomerid() + "</TD>");
			ft.write("<TD width='120'>" + invoice.getShipvia() + "</TD>");
			ft.write(
					"<TD style='font-size: 16pt;' width='145' align='left'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ invoiceNoStr + "</TD>");
			ft.write("</TR>");
			ft.write("</TBODY>");
			ft.write("</TABLE>");

			ft.write("</TD>");
			ft.write("</TR>");
			ft.write("<TR valign='top' style='font-size: 11pt;' height='60'>");
			ft.write("<TD valign='top'>");

			ft.write("<TABLE>");
			ft.write("<TBODY>");
			ft.write("<TR>");
			ft.write("<TD valign='top'><BR>");
			ft.write("<BR>");
			ft.write("<TABLE cellspacing='0' cellpadding='0'>");
			ft.write("<TBODY>");
			ft.write("<TR>");
			ft.write("<TD width='10'></TD>");
			ft.write("<TD width='142'></TD>");
			ft.write("<TD width='35'></TD>");
			ft.write("<TD width='51'></TD>");
			ft.write("<TD width='60'></TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD width='10'></TD>");
			ft.write("<TD width='142'></TD>");
			ft.write("<TD width='35'></TD>");
			ft.write("<TD width='51'></TD>");
			ft.write("<TD width='60'></TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD width='10'></TD>");
			ft.write("<TD width='142'></TD>");
			ft.write("<TD width='35'></TD>");
			ft.write("<TD width='51'></TD>");
			ft.write("<TD width='60'></TD>");
			ft.write("</TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD colspan='4'>&nbsp;&nbsp;&nbsp;" + customer.getCompanyname() + "&nbsp;" + paymentTerms
					+ "</TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD colspan='4'>&nbsp;&nbsp;&nbsp;" + billToAddress.getAddr1() + "</TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD>&nbsp;&nbsp;&nbsp;" + billToAddress.getCity() + "</TD>");
			ft.write("<TD colspan='2'>" + billToAddress.getState() + "</TD>");
			ft.write("<TD>" + billToAddress.getPostalcode() + "</TD>");
			ft.write("</TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + billToAddress.getRegion() + "</TD>");
			ft.write("<TD></TD>");
			ft.write("<TD colspan='2'>" + invoice.getBillattention() + "</TD>");
			ft.write("</TR>");
			ft.write("</TBODY>");
			ft.write("</TABLE>");
			ft.write("</TD>");
			ft.write("<TD valign='top'><BR>");
			ft.write("<BR>");

			ft.write("<TABLE cellspacing='0' cellpadding='0'>");
			ft.write("<TBODY>");
			ft.write("<TR>");
			ft.write("<TD width='67'></TD>");
			ft.write("<TD width='146'></TD>");
			ft.write("<TD width='43'></TD>");
			ft.write("<TD width='45'></TD>");
			ft.write("<TD width='50'></TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write(
					"<TD colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + invoice.getShipto() + "</TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + shipToAddress.getAddr1()
					+ "</TD>");
			ft.write("</TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD>&nbsp;&nbsp;&nbsp;" + shipToAddress.getCity() + "</TD>");
			ft.write("<TD colspan='2'>&nbsp;&nbsp;" + shipToAddress.getState() + "</TD>");
			ft.write("<TD>&nbsp;&nbsp;" + shipToAddress.getPostalcode() + "</TD>");
			ft.write("</TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR>");
			ft.write("<TD></TD>");
			ft.write("<TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + shipToAddress.getRegion() + "</TD>");
			ft.write("<TD></TD>");
			ft.write("<TD colspan='2'>" + invoice.getShipattention() + "</TD>");
			ft.write("</TR>");
			ft.write("</TBODY>");
			ft.write("</TABLE>");
			ft.write("</TD>");
			ft.write("</TR>");
			ft.write("</TBODY>");
			ft.write("</TABLE>");
			ft.write("</TD>");
			ft.write("</TR>");
			ft.write("<TR height='445' valign='top'>");
			ft.write("<TD width='787' valign='top'><BR>");
			ft.write("<BR>");
			ft.write("<BR>");

			ft.write("<TABLE cellpadding='0' cellspacing='0'>");
			ft.write("<TBODY>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			// Vector<InvoiceDetailsBean> invoiceDetails =
			// invoice.getInvoiceDetails();
			// Enumeration<InvoiceDetailsBean> ennum =
			// invoiceDetails.elements();
			// int noElements = invoiceDetails.size();
			// while (ennum.hasMoreElements()) {
			// InvoiceDetailsBean iBean = ennum.nextElement();

			// }
			for (InvoiceDetails iBean : invoiceDetails) {
				String partNumber = iBean.getPartnumber();
				// Integer qty = iBean.getQuantity();
				String location = "";
				String model = "";
				String year = "";
				String desc = "";
				BigDecimal list = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
				BigDecimal cost = iBean.getSoldprice().setScale(2, RoundingMode.HALF_EVEN);
				BigDecimal totalCost = cost.multiply(BigDecimal.valueOf(iBean.getQuantity())).setScale(2,
						RoundingMode.HALF_EVEN);// qty
				// *
				// cost;

				if (partNumber.startsWith("XX")) {
					totalCost = totalCost.multiply(BigDecimal.valueOf(-1));
					desc = "Damaged Discount For " + partNumber.substring(2);
				} else {
					Parts part = getPartInfo(partNumber);
					location = part.getLocation();
					// model =
					// MakeModelBean.getMakeModelName(part.getMakeModelCode());
					model = part.getMakemodelname();
					String interModel = "";
					if (part.getInterchangeno() != null && !part.getInterchangeno().trim().equals("")) {
						// interModel =
						// MakeModelBean.getMakeModelName(PartsBean.getPart(part.getInterchangeNo(),
						// null).getMakeModelCode());
						interModel = getPartInfo(part.getInterchangeno()).getMakemodelname();
						interModel = cutModel(interModel);
					} else {
						List<Parts> allInterChangeParts = getAllInterChangeParts(part.getPartno());
						if (allInterChangeParts != null) {
							for (Parts p : allInterChangeParts) {
								String interModel1 = "";
								interModel1 = p.getMakemodelname();
								interModel1 = cutModel(interModel1);
								if (interModel.trim().equals("")) {
									interModel += interModel1;
								} else {
									interModel += "/ " + interModel1;
								}
							}
						}
					}
					model = cutModel(model);
					if (!interModel.trim().equals("")) {
						model += "/ " + interModel;
					}
					year = part.getYear();
					desc = part.getPartdescription();
					list = part.getListprice();// iBean.getListPrice();
				}

				// if (totalCost != 0) {
				// totalCost =
				// Double.parseDouble(NumberUtils.cutFractions(totalCost + ""));
				// }
				// String listStr = list + "";
				// String costStr = cost + "";
				DecimalFormat df = new DecimalFormat("#.##");
				String totalCostStr = df.format(totalCost);
				/*
				 * if (totalCost < 0) { totalCostStr = (totalCost * -1) + "C"; } else {
				 * totalCostStr = totalCost+""; }
				 */
				// if (listStr.indexOf(".") == listStr.length() - 2)
				// listStr += "0";
				// if (costStr.indexOf(".") == costStr.length() - 2)
				// costStr += "0";
				// if (totalCostStr.indexOf(".") == totalCostStr.length() - 2)
				// totalCostStr += "0";
				// if (totalCostStr.indexOf("-") != -1) {
				// totalCostStr = totalCostStr.substring(0,
				// totalCostStr.indexOf("-"))
				// + totalCostStr.substring(totalCostStr.indexOf("-") + 1) +
				// "C";
				// }
				if (totalCost.compareTo(BigDecimal.ZERO) == -1) {
					totalCostStr = df.format((totalCost.multiply(BigDecimal.valueOf(-1)))) + "C";
				}
				ft.write("<TR>");
				ft.write("<TD width='70'><B>" + partNumber + "</B></TD>");

				ft.write("<TD width='70' style='font-size: 10pt;'>&nbsp;<B>" + location + "&nbsp;</B></TD>");
				ft.write("<TD width='150' style='font-size: 9pt;'>&nbsp;" + model + "&nbsp;</TD>");
				ft.write("<TD width='50' style='font-size: 9pt;'>&nbsp;" + year + "&nbsp;</TD>");
				ft.write("<TD width='240' style='font-size: 9pt;'>&nbsp;" + desc + "&nbsp;</TD>");
				ft.write("<TD width='80'>&nbsp;"
						+ (iBean.getQuantity() < 0 ? iBean.getQuantity() * -1 : iBean.getQuantity()) + "&nbsp;</TD>");
				ft.write("<TD width='80'>&nbsp;" + df.format(list) + "&nbsp;</TD>");
				ft.write("<TD width='80'>&nbsp;" + df.format(cost) + "&nbsp;</TD>");
				ft.write("<TD width='80'>&nbsp;" + totalCostStr + "&nbsp;</TD>");
				ft.write("</TR>");
			}

			ft.write("</TBODY>");
			ft.write("</TABLE>");
			ft.write("</TD>");
			ft.write("</TR>");
			ft.write("</TBODY>");
			ft.write("</TABLE>");
			ft.write("<table>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR></TR>");
			ft.write("<TR>");
			String notes = "";
			if (invoice.getReturnedinvoice() != 0) {
				notes += "Returned Invoice # " + invoice.getReturnedinvoice() + " --- ";
			}
			if (invoice.getNotes() != null && !invoice.getNotes().trim().equals("")) {
				notes += invoice.getNotes();
			}
			String notes1 = "";
			String notes2 = "";
			if (notes.length() > 60) {
				notes1 = notes.substring(0, 60);
				if (notes.length() > 120)
					notes2 = notes.substring(61, 120);
				else
					notes2 = notes.substring(61);
			} else {
				notes1 = notes;
			}
			DecimalFormat df = new DecimalFormat("#.##");
			String tot = df.format(invoice.getInvoicetotal());
			if (invoice.getInvoicetotal().compareTo(BigDecimal.ZERO) == -1) {
				tot = df.format((invoice.getInvoicetotal().multiply(BigDecimal.valueOf(-1)))) + "C";
			}
			// if (tot.indexOf("-") != -1) {
			// tot = tot.substring(0, tot.indexOf("-")) +
			// tot.substring(tot.indexOf("-") + 1) + "C";
			// }
			ft.write("<TD align='left' width='600' style='font-size: 10pt;'>&nbsp;" + notes1 + "</TD>");
			ft.write("<TD align='right' width='60' style='font-size: 14pt;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tot
					+ "</TD>");
			ft.write("</TR>");
			ft.write("<TR></TR>");
			ft.write("<TR>");
			String tax = df.format(invoice.getTax());
			if (invoice.getTax().compareTo(BigDecimal.ZERO) == -1) {
				tax = df.format((invoice.getTax().multiply(BigDecimal.valueOf(-1)))) + "C";
			}
			// if (tax.indexOf("-") != -1) {
			// tax = tax.substring(0, tax.indexOf("-")) +
			// tax.substring(tax.indexOf("-") + 1) + "C";
			// }
			ft.write("<TD align='left' width='600' style='font-size: 10pt;'>&nbsp;" + notes2 + "</TD>");
			ft.write("<TD align='right' width='60' style='font-size: 14pt;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tax
					+ "</TD>");
			ft.write("</TR>");
			ft.write("<TR></TR>");
			ft.write("<TR>");
			BigDecimal total = (invoice.getInvoicetotal().add(invoice.getTax())).subtract(invoice.getDiscount());

			String totalStr = df.format(total);
			if (total.compareTo(BigDecimal.ZERO) == -1) {
				totalStr = df.format((total.multiply(BigDecimal.valueOf(-1)))) + "C";
			}

			ft.write("<TD align='left' width='600' style='font-size: 10pt;'></TD>");
			ft.write("<TD align='right' width='60' style='font-size: 14pt;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + totalStr
					+ "<BR/></TD>");
			ft.write("</TR>");
			ft.write("<TR></TR>");
			// if (invoice.getDiscount() != 0) {
			if (invoice.getDiscount().compareTo(BigDecimal.ZERO) != 0) {
				ft.write("<TR>");
				String disc = df.format(invoice.getDiscount());

				if (invoice.getDiscounttype().trim().equals("N")) {
					ft.write("<TD align='right' width='660' style='font-size: 14pt;'>New Customer Disc.:  " + disc
							+ "</TD>");
				} else if (invoice.getDiscounttype().trim().equals("M")) {
					ft.write("<TD align='right' width='660' style='font-size: 14pt;'>Month End Sale Disc.:  " + disc
							+ "</TD>");
				} else if (invoice.getDiscounttype().trim().equals("X")) {
					ft.write(
							"<TD align='right' width='660' style='font-size: 14pt;'>New Customer + Over $500.00 Disc.:  "
									+ disc + "</TD>");
				} else if (invoice.getDiscounttype().trim().equals("R")) {
					ft.write("<TD align='right' width='660' style='font-size: 14pt;'>Return Customer Disc.:  " + disc
							+ "</TD>");
				} else if (invoice.getDiscounttype().trim().equals("O")) {
					ft.write("<TD align='right' width='660' style='font-size: 14pt;'>Over $500.00 Disc.:  " + disc
							+ "</TD>");
				} else if (invoice.getDiscounttype().trim().equals("P")) {
					ft.write("<TD align='right' width='660' style='font-size: 14pt;'>Sale Items Disc.:  " + disc
							+ "</TD>");
				} else {
					ft.write("<TD align='right' width='660' style='font-size: 14pt;'>Special Discount:  " + disc
							+ "</TD>");
				}
				ft.write("</TR>");
				ft.write("<TR>");
			}
			ft.write("<TR>");
			ft.write("<TD align='left' width='550' style='font-size: 10pt;'></TD>");
			ft.write("<TD align='right' width='60' style='font-size: 14pt;'>" + paymentTerms + "<BR/></TD>");
			ft.write("</TR>");
			ft.write("</table>");

			ft.write("</BODY>");
			ft.write("</HTML>");

			ft.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			fileName = "";
		} catch (Exception e) {
			e.printStackTrace();
			fileName = "";
		}
		Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.clear();
		return fileName;
	}

	@Transactional
	public void createHistory(AppUser user, Invoice oldInvoice, DisplayInvoice newInvoice,
			List<InvoiceDetails> oldInvoiceDetails) {
		Session session = sessionFactory.getCurrentSession();
		int invoiceNo = 0;
		String modifiedBy = user.getUsername();
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String modifiedDate = df.format(new java.util.Date());
		int modifiedOrder = 0;
		String remarks = "";

		invoiceNo = oldInvoice.getInvoicenumber();

		String query = "Select max(ModifiedOrder) From InvoiceHistory Where InvoiceNumber=:invoiceNo";
		SQLQuery maxModifiedQuery = session.createSQLQuery(query);
		maxModifiedQuery.setParameter("invoiceNo", invoiceNo);
		Number maxModified = ((Number) maxModifiedQuery.uniqueResult());
		if (maxModified != null) {
			modifiedOrder = maxModified.intValue() + 1;
		} else {
			modifiedOrder = 1;
		}

		if (!df.format(oldInvoice.getOrderdate()).equals(df.format(new java.util.Date()))) {
			remarks += "Invoice Date Changed From " + df.format(oldInvoice.getOrderdate()) + " To "
					+ df.format(newInvoice.getOrderdate()) + "<br>";
		}

		if (oldInvoice.getReturnedinvoice() == 0 && newInvoice.getReturnedinvoice() != 0) {
			remarks += "Returned Invoice No Added: " + newInvoice.getReturnedinvoice() + "<BR>";
		} else if (oldInvoice.getReturnedinvoice() != 0 && newInvoice.getReturnedinvoice() == 0) {
			remarks += "Returned Invoice No " + oldInvoice.getReturnedinvoice() + " Removed " + "<BR>";
		} else if (!oldInvoice.getReturnedinvoice().equals(newInvoice.getReturnedinvoice())) {
			remarks += "Returned Invoice No Changed From " + oldInvoice.getReturnedinvoice() + " To "
					+ newInvoice.getReturnedinvoice() + "<BR>";
		}

		if (oldInvoice.getInvoicetotal().doubleValue() != newInvoice.getInvoicetotal().doubleValue()) {
			remarks += "Invoice Total Changed From " + oldInvoice.getInvoicetotal().doubleValue() + " To "
					+ newInvoice.getInvoicetotal().doubleValue() + "<BR>";
		}
		if (oldInvoice.getTax().doubleValue() != newInvoice.getTax().doubleValue()) {
			remarks += "Tax Changed From " + oldInvoice.getTax().doubleValue() + " To "
					+ newInvoice.getTax().doubleValue() + "<BR>";
		}
		if (oldInvoice.getDiscount().doubleValue() == 0 && newInvoice.getDiscount().doubleValue() != 0) {
			remarks += "Discount Of " + newInvoice.getDiscount().doubleValue() + " is Added " + "<BR>";
		} else if (oldInvoice.getDiscount().doubleValue() != newInvoice.getDiscount().doubleValue()) {
			remarks += "Discount Changed From " + oldInvoice.getDiscount().doubleValue() + " To "
					+ newInvoice.getDiscount().doubleValue() + "<BR>";
		}

		if (!oldInvoiceDetails.isEmpty() && newInvoice.getDisplayinvoicedetailslist().isEmpty()) {
			remarks += " *** Invoice Voided *** " + "<BR>";
		}

		for (InvoiceDetails invDet1 : oldInvoiceDetails) {
			boolean partFound = false;
			for (DisplayInvoiceDetail invDet2 : newInvoice.getDisplayinvoicedetailslist()) {
				if (invDet1.getPartnumber().trim().equals(invDet2.getPartnumber().trim())) {
					partFound = true;
					remarks += " Part Number " + invDet1.getPartnumber() + " ----- Exists " + "<BR>";
					if (!invDet2.getQuantity().equals(invDet1.getQuantity())) {
						remarks += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Quantity Changed From "
								+ invDet1.getQuantity() + " To " + invDet2.getQuantity() + "<BR>";
					}
					if (invDet1.getSoldprice().doubleValue() != invDet2.getSoldprice().doubleValue()) {
						remarks += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cost Price Changed From "
								+ invDet1.getSoldprice().doubleValue() + " To " + invDet2.getSoldprice().doubleValue()
								+ "<BR>";
					}
					break;
				}
			}
			if (!partFound) {
				remarks += " Part Number " + invDet1.getPartnumber() + " ----- Removed " + "<BR>";
			}
		}

		for (DisplayInvoiceDetail invDet3 : newInvoice.getDisplayinvoicedetailslist()) {
			boolean partFound = false;
			for (InvoiceDetails invDet4 : oldInvoiceDetails) {
				if (invDet3.getPartnumber().trim().equals(invDet4.getPartnumber().trim())) {
					partFound = true;
				}
			}
			if (!partFound) {
				remarks += " Part Number " + invDet3.getPartnumber() + " ----- Added " + "<BR>";
				remarks += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Quantity : "
						+ invDet3.getQuantity() + "<BR>";
				remarks += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cost Price : "
						+ invDet3.getSoldprice().doubleValue() + "<BR>";
			}
		}
		query = "INSERT INTO InvoiceHistory (InvoiceNumber, ModifiedBy, ModifiedDate, ModifiedOrder, Remarks1, Remarks2, Remarks3, Remarks4, Remarks5) "
				+ "values (:InvoiceNumber, :ModifiedBy, :ModifiedDate, :ModifiedOrder, :Remarks1, :Remarks2, :Remarks3, :Remarks4, :Remarks5) ";
		SQLQuery invHQueryInsert = session.createSQLQuery(query);
		invHQueryInsert.setParameter("InvoiceNumber", invoiceNo);
		invHQueryInsert.setParameter("ModifiedBy", modifiedBy);
		invHQueryInsert.setParameter("ModifiedDate", modifiedDate);
		invHQueryInsert.setParameter("ModifiedOrder", modifiedOrder);
		if (remarks.equals("")) {
			remarks += "<br>";
		}
		if (remarks.length() <= 200) {
			invHQueryInsert.setParameter("Remarks1", remarks);
		} else {
			invHQueryInsert.setParameter("Remarks1", remarks.substring(0, 200));
		}

		if (remarks.length() <= 200) {
			invHQueryInsert.setParameter("Remarks2", "");
		} else if (remarks.length() > 200 && remarks.length() <= 400) {
			invHQueryInsert.setParameter("Remarks2", remarks.substring(200));
		} else if (remarks.length() > 400) {
			invHQueryInsert.setParameter("Remarks2", remarks.substring(200, 400));
		}
		if (remarks.length() <= 400) {
			invHQueryInsert.setParameter("Remarks3", "");
		} else if (remarks.length() > 400 && remarks.length() <= 600) {
			invHQueryInsert.setParameter("Remarks3", remarks.substring(400));
		} else if (remarks.length() > 600) {
			invHQueryInsert.setParameter("Remarks3", remarks.substring(400, 600));
		}
		if (remarks.length() <= 600) {
			invHQueryInsert.setParameter("Remarks4", "");
		} else if (remarks.length() > 600 && remarks.length() <= 800) {
			invHQueryInsert.setParameter("Remarks4", remarks.substring(600));
		} else if (remarks.length() > 800) {
			invHQueryInsert.setParameter("Remarks4", remarks.substring(600, 800));
		}
		if (remarks.length() <= 800) {
			invHQueryInsert.setParameter("Remarks5", "");
		} else if (remarks.length() > 800 && remarks.length() <= 1000) {
			invHQueryInsert.setParameter("Remarks5", remarks.substring(800));
		} else if (remarks.length() > 1000) {
			invHQueryInsert.setParameter("Remarks5", remarks.substring(800, 1000));
		}
		@SuppressWarnings("unused")
		int executeUpdate = invHQueryInsert.executeUpdate();
		session.flush();
		session.clear();
	}

	@Transactional
	public String createInvoiceProcess(DisplayInvoice displayinvoice, AppUser user, String shipvia, Customer customer,
			String discount, String billsameship, Address billtoaddress, Address shiptoaddress) {

		displayinvoice.setDiscount(new BigDecimal(discount));
		// LOGGER.info("ENTERED DISCOUNT:" + discount);

		String stat = displayinvoice.getStatus();
		double d = 0.0;

		Double invoiceTotal = new Double(d);

		Double costPrice = new Double(d);
		Double listPrice = new Double(d);
		int quantity = 0;

		displayinvoice.setCustomerid(customer.getCustomerid());

		if ((stat.trim().equalsIgnoreCase("C") || stat.trim().equalsIgnoreCase("W")
				|| stat.trim().equalsIgnoreCase("P")) && !user.getActualrole().trim().equalsIgnoreCase("admin")) {
			displayinvoice.setStatus(stat.trim());
		} else {
			displayinvoice.setStatus("N");
		}
		if (displayinvoice.getDiscounttype().equals("")) {
			displayinvoice.setDiscounttype("G");
		}

		displayinvoice.setReturnedinvoice(0);
		displayinvoice.setShipvia(shipvia);

		List<DisplayInvoiceDetail> displayinvoicedetails = displayinvoice.getdisplayinvoicedetailslist();

		for (DisplayInvoiceDetail invoicedetail : displayinvoicedetails) {
			String partNo = invoicedetail.getPartnumber();
			quantity = invoicedetail.getQuantity();
			if (partNo != null && (!partNo.trim().equals(""))) {
				listPrice = invoicedetail.getListprice().doubleValue();
				costPrice = invoicedetail.getSoldprice().doubleValue();
				Parts searchpart = getParts(partNo);

				if (customer.getCustomerid().trim().equalsIgnoreCase("1111111111")) {

					if ((listPrice < searchpart.getListprice().doubleValue())
							&& (!(user.getActualrole().trim().equalsIgnoreCase("admin")))) {
						listPrice = searchpart.getListprice().doubleValue();
					}
					costPrice = listPrice * 0.8;
					invoicedetail.setSoldprice(
							new BigDecimal(costPrice, MathContext.DECIMAL64).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					invoicedetail.setListprice(
							new BigDecimal(listPrice, MathContext.DECIMAL64).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				}
				double tot = costPrice * quantity;
				invoiceTotal += tot;

			} // if partno null check

		} // for invoicedetails loop

		String message = addInvoice(displayinvoice, customer, billtoaddress, shiptoaddress);
		return message;

	}

	private String cutModel(String model) {
		int len = 0;
		if (model == null) {
			return "";
		}
		if (model.length() > 15) {
			model = model.substring(0, 15);
		}
		if ((len = model.indexOf("(")) != -1) {
			model = model.substring(0, len);
		}
		return model;
	}

	// THIS METHOD IS USED FOR MODIFYING THE INVOICE.
	// INVOICES CANNOT BE MODIFIED FROM THE NEXT DAY ONWARDS...
	public boolean cutOffGood(DisplayInvoice invoice) {
		long invTime = invoice.getInvoicetime();
		long timeToAdd = 86400000;
		Calendar cc = java.util.Calendar.getInstance();
		cc.setTime(new java.util.Date(invTime));
		if (cc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			timeToAdd *= 2;
		}

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
		if (invTime != 0 && (System.currentTimeMillis() < invTime + timeToAdd)) {
			return true;
		} else if (df.format(invoice.getOrderdate()).equals(df.format(new java.util.Date()))) {
			return true;
		} else {
			return false;
		}
	}

	private void deleteInvoiceDetails(int invNo) {
		Session session = sessionFactory.getCurrentSession();
		String query = "DELETE FROM InvoiceDetails WHERE InvoiceNumber=:invNo";
		SQLQuery deleteQuery = session.createSQLQuery(query);
		deleteQuery.setParameter("invNo", invNo);
		@SuppressWarnings("unused")
		int executeUpdate = deleteQuery.executeUpdate();

	}

	@Transactional
	public void deleteWriteOff(int invNo) {
		Session session = sessionFactory.getCurrentSession();
		String query = "DELETE FROM WriteOff WHERE InvoiceNo=:invNo";
		SQLQuery deleteQuery = session.createSQLQuery(query);
		deleteQuery.setParameter("invNo", invNo);
		@SuppressWarnings("unused")
		int executeUpdate = deleteQuery.executeUpdate();
		session.flush();
		session.clear();
	}

	@Transactional
	public Address getAddress(String customerid, String type, String who, Date datecreated, Integer invoicenumber) {

		Session session = sessionFactory.getCurrentSession();
		StringBuffer addressSQL = new StringBuffer("");

		addressSQL.append(
				"from Address address where address.id =:customerid and address.type =:type and address.who =:who ");
		if (datecreated != null) {
			addressSQL.append(" and address.datecreated =:datecreated");
			if (invoicenumber != 0) {
				addressSQL.append(" and address.invoicenumber =:invoicenumber");
			}
		} else {
			if (invoicenumber != 0) {
				addressSQL.append(" and  address.invoicenumber =:invoicenumber");
			}
		}

		Query query = session.createQuery(addressSQL.toString());

		if (type.trim().equalsIgnoreCase("Bill")) {
			query.setParameter("customerid", customerid);
			query.setParameter("type", "Standard");
			query.setParameter("who", who);
			if ((datecreated != null)) {
				query.setParameter("datecreated", datecreated);
				if (invoicenumber != 0) {
					query.setParameter("invoicenumber", 0);
				}
			} else {
				if (invoicenumber != 0) {
					query.setParameter("invoicenumber", 0);
				}
			}
		} else {
			query.setParameter("customerid", customerid);
			query.setParameter("type", type);
			query.setParameter("who", who);
			if ((datecreated != null)) {
				query.setParameter("datecreated", datecreated);
				if (invoicenumber != 0) {
					query.setParameter("invoicenumber", invoicenumber);
				}
			} else {
				if (invoicenumber != 0) {
					query.setParameter("invoicenumber", invoicenumber);
				}
			}
		}

		// querying for the address
		List<Address> addresses = query.list();

		if (addresses != null) {
			if (addresses.size() > 0) {
				session.flush();
				session.clear();
				return addresses.get(0);
			} else {
				session.flush();
				session.clear();
				Address address = new Address();
				address.setId(customerid);
				address.setType(type);
				address.setWho(who);
				address.setDatecreated(datecreated);
				address.setInvoicenumber(invoicenumber);
				address.setAddr1("");
				address.setAddr2("");
				address.setCity("");
				address.setState("");
				address.setRegion("");
				address.setPostalcode("");
				address.setCountry("");
				address.setPhone("");
				address.setExt("");
				address.setFax("");
				return address;
			}
		} else {
			session.flush();
			session.clear();
			Address address = new Address();
			address.setId(customerid);
			address.setType(type);
			address.setWho(who);
			address.setDatecreated(datecreated);
			address.setInvoicenumber(invoicenumber);
			address.setAddr1("");
			address.setAddr2("");
			address.setCity("");
			address.setState("");
			address.setRegion("");
			address.setPostalcode("");
			address.setCountry("");
			address.setPhone("");
			address.setExt("");
			address.setFax("");
			return address;
		}

	}

	@Transactional
	public List<Customer> getAllCustomer() {

		String hqlQuery = "From Customer customer  ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Customer> results = query.list();
		if (results != null) {
			session.flush();
			session.clear();
			return results;
		} else {
			results = new ArrayList<Customer>();
			results.add(getWalkinCustomer());
			session.flush();
			session.clear();
			return results;
		}

	}

	private List<Parts> getAllInterChangeParts(String partNo) {
		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts  WHERE parts.interchangeno =:partno";
		Query query = session.createQuery(hSql);
		query.setParameter("partno", partNo);
		return query.list();
	}

	@Transactional
	public Map<String, String> getAllMakeModelFromSelectedMake(Integer manufacturerid, String yearselected) {

		Session session = sessionFactory.getCurrentSession();

		String hSqlParts = "Select parts.makemodelcode from Parts parts where :yearselected between parts.yearfrom and parts.yearto";
		SQLQuery queryParts = session.createSQLQuery(hSqlParts);
		queryParts.setParameter("yearselected", Integer.parseInt(yearselected));
		List<String> partsmodel = queryParts.list();

		String hSqlModel = "FROM MakeModel makemodel where makemodel.manufacturerid =:manufacturerid and makemodel.makemodelcode in (:yearmodels) ORDER BY makemodel.makemodelname";
		Query queryModel = session.createQuery(hSqlModel);
		queryModel.setParameter("manufacturerid", manufacturerid);
		queryModel.setParameterList("yearmodels", partsmodel);
		Map<String, String> makemodellist = new LinkedHashMap<String, String>();
		// makemodellist.put("ALL", "-1");

		List<MakeModel> makemodels = queryModel.list();

		if (makemodels != null) {
			if (makemodels.size() > 0) {
				for (MakeModel makemodel : makemodels) {
					makemodellist.put(makemodel.getMakemodelname(), makemodel.getMakemodelcode());
				}
			}
		}

		session.flush();
		session.clear();

		return makemodellist;
	}

	@Transactional
	public Map<String, String> getAllPartsFromSelectedModel(String makemodelcode, String yearselected,
			Customer customerid) {

		Map<String, String> relpartslist = new LinkedHashMap<String, String>();

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts where parts.makemodelcode =:makemodelcode and :yearselected  BETWEEN  parts.yearfrom AND  parts.yearto  ORDER BY parts.partdescription";
		Query query = session.createQuery(hSql);
		query.setParameter("makemodelcode", makemodelcode);
		query.setParameter("yearselected", Integer.parseInt(yearselected));

		List<Parts> parts = query.list();

		if (parts != null) {
			if (parts.size() > 0) {
				for (Parts part : parts) {
					// part.setCalculatedprice(InsightUtils.getCalculatedSellingPrice(part,
					// customerid));

					relpartslist.put(part.getPartno(), part.getPartdescription());
				}
			}
		}

		session.flush();
		session.clear();

		return relpartslist;
	}

	public Address getBlankAddress(String customerid, String type, String who, Date datecreated,
			Integer invoicenumber) {
		Address address = new Address();
		address.setId(customerid);
		address.setType(type);
		address.setWho(who);
		address.setDatecreated(datecreated);
		address.setInvoicenumber(invoicenumber);
		address.setAddr1("");
		address.setAddr2("");
		address.setCity("");
		address.setState("");
		address.setRegion("");
		address.setPostalcode("");
		address.setCountry("");
		address.setPhone("");
		address.setExt("");
		address.setFax("");
		return address;
	}

	@Transactional
	public BigDecimal getCalculatedSellingPrice(Parts part, Customer customer) {

		BigDecimal zerocompare = new BigDecimal("0.00");
		BigDecimal hundred = new BigDecimal("100.00");
		BigDecimal calculatedprice = new BigDecimal("0.00");
		BigDecimal fifteenpercent = new BigDecimal("1.15");
		if (customer.getCustomerid().equalsIgnoreCase("1111111111")) {
			calculatedprice = part.getCostprice().multiply(fifteenpercent).setScale(2, RoundingMode.HALF_EVEN);
			return calculatedprice;
		} else if (customer.getCustomerid().equalsIgnoreCase("2222222222")) {
			return part.getCostprice().setScale(2, RoundingMode.HALF_EVEN);
		} else {
			if (customer.getCustomerlevel() != null) {
				if (customer.getCustomerlevel() <= 0) {
					return part.getCostprice().setScale(2, RoundingMode.HALF_EVEN);
				} else {
					if (part.getCostprice().compareTo(part.getActualprice()) == 1) {
						if (part.getCostprice().compareTo(zerocompare) == 1) {
							Integer customerlevel = customer.getCustomerlevel();
							BigDecimal percent = part.getCostprice().subtract(part.getActualprice()).multiply(hundred)
									.divide(part.getCostprice(), RoundingMode.HALF_UP).setScale(2, BigDecimal.ROUND_UP);
							BigDecimal factor = new BigDecimal("1.00");
							LevelCalc levelcalc = getLevelCalc(percent);
							switch (customerlevel) {
							case 1:
								factor = levelcalc.getLevel1();
								break;
							case 2:
								factor = levelcalc.getLevel2();
								break;
							case 3:
								factor = levelcalc.getLevel3();
								break;
							case 4:
								factor = levelcalc.getLevel4();
								break;
							case 5:
								factor = levelcalc.getLevel5();
								break;
							case 6:
								factor = levelcalc.getLevel6();
								break;
							case 7:
								factor = levelcalc.getLevel7();
								break;
							case 8:
								factor = levelcalc.getLevel8();
								break;
							case 9:
								factor = levelcalc.getLevel9();
								break;
							case 10:
								factor = levelcalc.getLevel10();
								break;
							case 11:
								factor = levelcalc.getLevel11();
								break;
							case 12:
								factor = levelcalc.getLevel12();
								break;
							default:

								break;
							}
							return part.getCostprice().multiply(factor).setScale(2, RoundingMode.HALF_EVEN);
						} else {
							return part.getCostprice().setScale(2, RoundingMode.HALF_EVEN);
						}
					} else {
						return part.getCostprice().setScale(2, RoundingMode.HALF_EVEN);
					}
				}
			} else {
				return part.getCostprice().setScale(2, RoundingMode.HALF_EVEN);
			}
		}

	}

	@Transactional
	private BigDecimal getCreditorBalance(Customer customer) {

		BigDecimal amountpending = new BigDecimal(0);
		Session session = sessionFactory.getCurrentSession();

		String hqlInvoice = "from Invoice invoice where invoice.balance != 0 and invoice.status != 'C' and invoice.status != 'W' and invoice.customerid=:customerId  order by invoicenumber";

		Query queryInvoice = session.createQuery(hqlInvoice);
		queryInvoice.setParameter("customerId", customer.getCustomerid());

		List<Invoice> pendinginvoices = queryInvoice.list();

		if (pendinginvoices != null) {
			if (pendinginvoices.size() > 0) {
				for (Invoice creditcheckinvoice : pendinginvoices) {
					amountpending.add(creditcheckinvoice.getBalance());
				}
			}
		}

		String hqlBouncedChecks = "from BouncedChecks bouncedchecks where bouncedchecks.customerid=:customer_Id and bouncedchecks.iscleared='N' ";

		Query queryBouncedChecks = session.createQuery(hqlBouncedChecks);
		queryBouncedChecks.setParameter("customer_Id", customer.getCustomerid());

		List<BouncedChecks> bouncedchecks = queryBouncedChecks.list();

		if (bouncedchecks != null) {
			if (bouncedchecks.size() > 0) {
				for (BouncedChecks bouncedcheck : bouncedchecks) {
					amountpending.add(bouncedcheck.getBalance());
				}
			}
		}

		if ((customer.getCreditbalance().compareTo(amountpending) != 0) && (!amountpending.equals(new BigDecimal(0)))) {
			String updateSql = "Update Customer Set CreditBalance=:amountpending Where CustomerId=:customerId";
			// LOGGER.info("SL---"+updateSql+"--"+customer.getCreditbalance().compareTo(amountpending)+"===="+amountpending+"==="+customer.getCreditbalance());
			Query query = session.createSQLQuery(updateSql);
			query.setParameter("amountpending", amountpending);
			query.setParameter("customerId", customer.getCustomerid());
			query.executeUpdate();

		}

		session.flush();
		session.clear();
		return amountpending;

	}

	@Transactional
	public Customer getCustomerById(String customerId) {

		String hqlQuery = "From Customer customer  where customer.customerid =:customerid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("customerid", customerId.trim());

		Customer results = (Customer) query.uniqueResult();

		if (results != null) {
			session.flush();
			session.clear();
			return results;
		} else {
			results = getWalkinCustomer();
			session.flush();
			session.clear();
			return results;
		}

	}

	@Transactional
	public List<DisplayInvoiceDetail> getDisplayInvoiceDetailsFromInvoiceDetails(
			List<InvoiceDetails> invoicedetailslist) {

		Session session = sessionFactory.getCurrentSession();
		List<DisplayInvoiceDetail> displayinvoicedetailslist = new ArrayList<DisplayInvoiceDetail>();
		for (InvoiceDetails invoicedetails : invoicedetailslist) {
			Parts partsdetails = getSelectedPartsDetailsVanilla(invoicedetails.getPartnumber().trim());

			DisplayInvoiceDetail displayinvoicedetails = new DisplayInvoiceDetail();

			displayinvoicedetails.setActualprice(invoicedetails.getActualprice());
			displayinvoicedetails.setInvoicenumber(invoicedetails.getInvoicenumber());
			displayinvoicedetails.setMakemodelname(partsdetails.getMakemodelname());
			displayinvoicedetails.setManufacturername(partsdetails.getManufacturername());
			displayinvoicedetails.setPartdescription(partsdetails.getPartdescription());
			displayinvoicedetails.setPartnumber(invoicedetails.getPartnumber());
			displayinvoicedetails.setQuantity(invoicedetails.getQuantity());
			displayinvoicedetails.setReorderlevel(partsdetails.getReorderlevel());
			displayinvoicedetails.setSoldprice(invoicedetails.getSoldprice());
			displayinvoicedetails.setUnitsinstock(partsdetails.getUnitsinstock());
			displayinvoicedetails.setUnitsonorder(partsdetails.getUnitsonorder());
			displayinvoicedetails.setYear(partsdetails.getYear());
			displayinvoicedetails.setListprice(partsdetails.getListprice());
			displayinvoicedetails.setLocation(partsdetails.getLocation());

			displayinvoicedetailslist.add(displayinvoicedetails);
		}
		session.flush();
		session.clear();

		if (displayinvoicedetailslist.size() > 0) {
			return displayinvoicedetailslist;
		} else {
			return null;
		}

	}

	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsFromInvoiceNumber(Integer invoicenumber) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM InvoiceDetails invoicedetails where invoicedetails.invoicenumber = :searchinvoicenumber";
		Query query = session.createQuery(hSql);
		query.setParameter("searchinvoicenumber", invoicenumber);

		List<InvoiceDetails> invoicedetails = query.list();

		session.flush();
		session.clear();

		if (invoicedetails != null) {
			if (invoicedetails.size() > 0) {
				return invoicedetails;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsOriginalForReturn(Integer orgInvoiceNo,
			List<DisplayInvoiceDetail> returnedInvItems) {
		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM InvoiceDetails invoicedetails where invoicedetails.invoicenumber = :searchinvoicenumber";
		if (returnedInvItems != null && !returnedInvItems.isEmpty()) {
			hSql += " and invoicedetails.partnumber not in (:returnedParts)";
		}
		Query query = session.createQuery(hSql);
		query.setParameter("searchinvoicenumber", orgInvoiceNo);
		if (returnedInvItems != null && !returnedInvItems.isEmpty()) {
			List<String> returnedParts = new ArrayList<String>();
			for (DisplayInvoiceDetail invDet : returnedInvItems) {
				returnedParts.add(invDet.getPartnumber());
			}
			query.setParameterList("returnedParts", returnedParts);
		}
		@SuppressWarnings("unchecked")
		List<InvoiceDetails> invoicedetails = query.list();
		session.flush();
		session.clear();
		return invoicedetails;
	}

	@Transactional
	private LevelCalc getLevelCalc(BigDecimal percent) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM LevelCalc levelcalc where :percent BETWEEN levelcalc.percentfrom AND levelcalc.percentto";
		Query query = session.createQuery(hSql);
		query.setParameter("percent", percent);

		List<LevelCalc> levelcalc = query.list();

		session.flush();
		session.clear();

		if (levelcalc != null) {
			if (levelcalc.size() > 0) {
				return levelcalc.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@SuppressWarnings("deprecation")
	private int getMonthEndDiscount(String custId) {
		Session session = sessionFactory.getCurrentSession();
		int disc = 5;
		String query = "Select sum(InvoiceTotal) from invoice where customerid=:customerId and OrderDate>=:date1 and OrderDate<=:date2";
		SQLQuery invTQuery = session.createSQLQuery(query);
		invTQuery.setParameter("customerId", custId);
		invTQuery.setParameter("date1", new java.util.Date(2007, 06, 01));
		invTQuery.setParameter("date2", new java.util.Date());
		double amt = ((Number) invTQuery.uniqueResult()) == null ? 0
				: ((Number) invTQuery.uniqueResult()).doubleValue();
		if (amt < 500) {
			disc = 5;
		} else if (amt < 1000) {
			disc = 6;
		} else if (amt < 2000) {
			disc = 7;
		} else if (amt < 3000) {
			disc = 8;
		} else if (amt < 4000) {
			disc = 9;
		} else if (amt < 6000) {
			disc = 10;
		} else if (amt < 8000) {
			disc = 11;
		} else if (amt < 10000) {
			disc = 12;
		} else if (amt < 13000) {
			disc = 13;
		} else if (amt < 17000) {
			disc = 14;
		} else {
			disc = 15;
		}
		return disc;
	}

	public int getNewInvoiceNo(String username) {
		Integer newInvNo = 0;
		Session session = sessionFactory.getCurrentSession();
		String query = "select InvNum from InvNo where Username=:username";
		SQLQuery invNoQuery = session.createSQLQuery(query);
		invNoQuery.setParameter("username", username);
		newInvNo = ((Number) invNoQuery.uniqueResult()) == null ? 0 : ((Number) invNoQuery.uniqueResult()).intValue();
		if (newInvNo == 0) {
			long newTimeLocked = System.currentTimeMillis();
			query = "select InvNum from InvNo where (TimeLocked + 1800000) < :newTimeLocked  order by InvNum ASC ";
			SQLQuery invNoQuery2 = session.createSQLQuery(query);
			invNoQuery2.setParameter("newTimeLocked", newTimeLocked);
			List<?> results = invNoQuery2.list();
			if (results != null && results.size() > 0) {
				newInvNo = ((Number) results.get(0)).intValue();
			}
			if (newInvNo != 0) {
				query = "update InvNo set Username=:username , TimeLocked=:newTimeLocked where InvNum=:newInvNo";
				SQLQuery invNoQueryUpdate = session.createSQLQuery(query);
				invNoQueryUpdate.setParameter("username", username);
				invNoQueryUpdate.setParameter("newTimeLocked", newTimeLocked);
				invNoQueryUpdate.setParameter("newInvNo", newInvNo);
				int executeUpdate = invNoQueryUpdate.executeUpdate();
				// LOGGER.info("update for query==" + executeUpdate);
			} else {
				int numFromInvoiceTable = 0;
				int numFromInvNoTable = 0;
				query = "select max(invoiceNumber) from Invoice";
				SQLQuery maxInvNoQuery = session.createSQLQuery(query);
				numFromInvoiceTable = ((Number) maxInvNoQuery.uniqueResult()) == null ? 0
						: ((Number) maxInvNoQuery.uniqueResult()).intValue();

				query = "select max(invNum) from InvNo";
				SQLQuery maxInvNoQuery2 = session.createSQLQuery(query);
				numFromInvNoTable = ((Number) maxInvNoQuery2.uniqueResult()) == null ? 0
						: ((Number) maxInvNoQuery2.uniqueResult()).intValue();
				if (numFromInvNoTable > numFromInvoiceTable) {
					newInvNo = numFromInvNoTable;
				} else {
					newInvNo = numFromInvoiceTable;
				}
				newInvNo++;
				// LOGGER.info("NEW INVOICE NUMBER FOR THE USER: " + username + " IS " +
				// newInvNo);
				query = "INSERT INTO InvNo (Username, InvNum, TimeLocked) VALUES (:username,:newInvNo,:newTimeLocked)";
				SQLQuery invNoQueryInsert = session.createSQLQuery(query);
				invNoQueryInsert.setParameter("username", username);
				invNoQueryInsert.setParameter("newInvNo", newInvNo);
				invNoQueryInsert.setParameter("newTimeLocked", newTimeLocked);
				int executeUpdate = invNoQueryInsert.executeUpdate();
				// LOGGER.info("update for invoice 2=" + executeUpdate);
			}
		}
		return newInvNo;
	}

	private Parts getPartInfo(String partno) {
		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts  WHERE parts.partno =:partno";
		Query query = session.createQuery(hSql);

		query.setParameter("partno", partno);

		List<Parts> parts = query.list();
		if (parts != null) {
			if (parts.size() > 0) {
				return parts.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Transactional
	public Parts getParts(String stockcheckpartno) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts  WHERE parts.partno =:partno";
		Query query = session.createQuery(hSql);

		query.setParameter("partno", stockcheckpartno);

		List<Parts> parts = query.list();

		session.flush();
		session.clear();
		if (parts != null) {
			if (parts.size() > 0) {
				return parts.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Transactional
	public List<String> getPrintRemarks(int invNo) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select Remarks1 from InvoiceHistory Where InvoiceNumber=:invNo And Remarks1 like :remarks Order By ModifiedOrder ";
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.setParameter("invNo", invNo);
		sqlQuery.setParameter("remarks", "Printed%");
		List<String> results = sqlQuery.list();

		session.flush();
		session.clear();
		return results;
	}

	private int getQuantity(String partNo) {
		int units = 0;
		Parts p = getPartInfo(partNo);
		if (p == null) {
			units = 0;
			return units;
		}

		if (p.getInterchangeno() == null || p.getInterchangeno().trim().equals("")) {
			units = p.getUnitsinstock();
		} else {
			// logger.error("Going to call the method for Interchange No");
			units = getQuantity(p.getInterchangeno());
		}
		return units;
	}

	@Transactional
	public Parts getSelectedPartsDetailsFromSelection(String selectpartnumber, Customer customer) {

		Parts selectedpart = null;
		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts where parts.partno =:selectpartnumber";
		Query query = session.createQuery(hSql);
		query.setParameter("selectpartnumber", selectpartnumber.trim());

		List<Parts> parts = query.list();

		if (parts != null) {
			if (parts.size() > 0) {
				for (Parts part : parts) {
					selectedpart = part;
					// LOGGER.info("calculatedprice" + getCalculatedSellingPrice(part, customer));
					part.setCalculatedprice(getCalculatedSellingPrice(part, customer));
					if (part.getInterchangeno() != null) {
						if (!(part.getInterchangeno().trim().equalsIgnoreCase(""))) {
							Parts mainpart = getParts(part.getInterchangeno().trim());
							selectedpart.setUnitsinstock(mainpart.getUnitsinstock());
							selectedpart.setUnitsonorder(mainpart.getUnitsonorder());
							selectedpart.setReorderlevel(mainpart.getReorderlevel());

						}
					}
				}
			}
		}

		session.flush();
		session.clear();

		return selectedpart;
	}

	@Transactional
	public Parts getSelectedPartsDetailsVanilla(String selectpartnumber) {

		if (selectpartnumber.toUpperCase().startsWith("XX")) {
			Parts part = new Parts();
			part.setPartno(selectpartnumber.toUpperCase());
			part.setMakemodelname("");
			part.setYear("");
			part.setUnitsinstock(null);
			// part.setQuantity(1);
			part.setCostprice(BigDecimal.ZERO);
			part.setListprice(BigDecimal.ZERO);
			part.setPartdescription("DAMAGED DISCOUNT FOR " + selectpartnumber.substring(2).toUpperCase());
			return part;
		}
		Parts selectedpart = null;
		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts where parts.partno =:selectpartnumber";
		Query query = session.createQuery(hSql);
		query.setParameter("selectpartnumber", selectpartnumber.trim());

		List<Parts> parts = query.list();

		if (parts != null) {
			if (parts.size() > 0) {
				for (Parts part : parts) {
					selectedpart = part;
					if (part.getInterchangeno() != null) {
						if (!(part.getInterchangeno().trim().equalsIgnoreCase(""))) {
							Parts mainpart = getParts(part.getInterchangeno().trim());
							selectedpart.setUnitsinstock(mainpart.getUnitsinstock());
							selectedpart.setUnitsonorder(mainpart.getUnitsonorder());
							selectedpart.setReorderlevel(mainpart.getReorderlevel());

						}
					}
				}
			}
		}

		session.flush();
		session.clear();

		return selectedpart;
	}

	@Transactional
	public Customer getWalkinCustomer() {

		String hqlQuery = "From Customer customer  where customer.customerid =:customerid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("customerid", "1111111111");

		Customer results = (Customer) query.uniqueResult();
		session.flush();
		session.clear();
		if (results != null) {
			return results;
		} else {
			return null;
		}

	}

	@Transactional
	public BigDecimal getWriteOffAmount(Customer customer) {

		BigDecimal writeoffamount = new BigDecimal("0.00");
		Session session = sessionFactory.getCurrentSession();
		String writeoffSql = "Select sum(invoice.balance) from Invoice invoice Where invoice.customerid=:customerid and (invoice.status='W' or invoice.status='C') and invoice.balance > 0 ";
		Query query = session.createQuery(writeoffSql);
		query.setParameter("customerid", customer.getCustomerid());
		query.setMaxResults(1);
		writeoffamount = (BigDecimal) query.uniqueResult();
		session.flush();
		session.clear();
		if (writeoffamount != null) {
			return writeoffamount;
		} else {

			return new BigDecimal("0.00");
		}
	}

	public boolean hasGoodCredit(Customer customer) {
		if (customer.getCreditlimit() != null && customer.getCreditlimit().doubleValue() > 0
				&& customer.getCreditbalance() != null
				&& customer.getCreditbalance().doubleValue() > customer.getCreditlimit().doubleValue()) {
			// double amt = cridetOrBalance.doubleValue();
			if (customer.getCreditlimit().doubleValue() > 0
					&& customer.getCreditbalance().doubleValue() > customer.getCreditlimit().doubleValue()) {
				return false;
			} else if (customer.getCreditlimit().doubleValue() == 1) {
				return false;
			} else {
				return true;
			}
		} else if (customer.getCreditlimit().doubleValue() == 1) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional
	public void invoicePrinted(int invNo, AppUser user) {
		Session session = sessionFactory.getCurrentSession();

		SQLQuery updateInvQuery = session.createSQLQuery("Update Invoice Set IsPrinted='Y' where InvoiceNumber=:invNo");
		updateInvQuery.setParameter("invNo", invNo);
		int count = updateInvQuery.executeUpdate();
		if (count != 0) {

			SQLQuery ordCntQuery = session
					.createSQLQuery("Select count(*) From InvoiceHistory Where InvoiceNumber=:invNo");
			ordCntQuery.setParameter("invNo", invNo);
			Integer ordCnt = ((Number) ordCntQuery.uniqueResult()) == null ? 1
					: ((Number) ordCntQuery.uniqueResult()).intValue() + 1;
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy h:mm a");
			String rmk1 = "Printed by " + user.getUsername() + " at "
					+ sdf.format(new java.util.Date(System.currentTimeMillis()));
			SQLQuery insertHistQuery = session.createSQLQuery(
					"Insert into InvoiceHistory (InvoiceNumber, ModifiedBy, ModifiedDate, ModifiedOrder, Remarks1) Values ("
							+ ":invNo ,:uName, :todayDate, :ordCnt, :rmk1)");
			insertHistQuery.setParameter("invNo", invNo);
			insertHistQuery.setParameter("uName", user.getUsername());
			insertHistQuery.setParameter("todayDate", new java.util.Date());
			insertHistQuery.setParameter("ordCnt", ordCnt);
			insertHistQuery.setParameter("rmk1", rmk1);
			insertHistQuery.executeUpdate();
		}

		session.flush();
		session.clear();
	}

	@Transactional
	public Boolean isAvailable(Integer invoicenumber) {

		Boolean isavailable = false;

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Invoice invoice where invoice.invoicenumber = :invoicenumber";
		Query query = session.createQuery(hSql);
		query.setParameter("searchinvoicenumber", invoicenumber);

		List<Invoice> invoices = query.list();

		if (invoices != null) {
			if (invoices.size() > 0) {
				isavailable = true;
			} else {
				isavailable = false;
			}
		} else {
			isavailable = false;
		}

		session.flush();
		session.clear();
		return isavailable;

	}

	@Transactional
	public Boolean isDoubleReturn(Integer invoicenumber, Integer returnedinvoice, String partnumber, Integer qty) {

		Boolean doubleReturn = false;
		Session session = sessionFactory.getCurrentSession();
		String query1SQL = "Select invoice.invoicenumber From Invoice invoice Where invoice.returnedinvoice=:returnedinvoice";
		String query2SQL = "Select sum(invoicedetails.quantity) from InvoiceDetails invoicedetails where (invoicedetails.invoicenumber=:returnedinvoice";

		Query query1 = session.createSQLQuery(query1SQL);
		query1.setParameter("returnedinvoice", returnedinvoice);

		query1.setMaxResults(1);
		Integer invoicenumberfound = (Integer) query1.uniqueResult();

		if (invoicenumberfound != null) {
			query2SQL = query2SQL
					+ " or invoicedetails.invoicenumber =:invoicenumberfound ) and invoicedetails.partnumber=:partnumber";
		}

		Query query2 = session.createSQLQuery(query2SQL);
		query2.setParameter("returnedinvoice", returnedinvoice);
		query2.setParameter("invoicenumberfound", invoicenumberfound);
		query2.setParameter("partnumber", partnumber);

		query2.setMaxResults(1);
		Integer totalquantity = (Integer) query2.uniqueResult();

		if (totalquantity != null) {
			if (((isAvailable(invoicenumber)) && (totalquantity < 0))
					|| (!(isAvailable(invoicenumber)) && ((totalquantity + qty) < 0))) {
				doubleReturn = true;
			}
		} else {
			doubleReturn = false;
		}

		session.flush();
		session.clear();
		return doubleReturn;

	}

	private boolean isFirstTimeBuying(String custId) {
		Session session = sessionFactory.getCurrentSession();
		boolean isFirstTime = false;
		String query = "Select count(*) from invoice where customerid=:custId";
		SQLQuery invCountQuery = session.createSQLQuery(query);
		invCountQuery.setParameter("custId", custId);
		Integer invCount = ((Number) invCountQuery.uniqueResult()).intValue();

		if (invCount == 0) {
			isFirstTime = true;
		}
		return isFirstTime;
	}

	@Transactional
	public Integer quantityInInvoice(Integer invoicenumber, String partnumber, Integer qty) {

		Integer availquantity = 0;
		Session session = sessionFactory.getCurrentSession();
		String ispartavaialableSQL = "select invoicedetails.quantity From InvoiceDetails invoicedetails where invoicedetails.invoicenumber =:invoicenumber and invoicedetails.partnumber=:partnumber";
		Query query = session.createSQLQuery(ispartavaialableSQL);
		query.setParameter("invoicenumber", invoicenumber);
		query.setParameter("partnumber", partnumber);
		query.setMaxResults(1);
		Integer originalquantity = (Integer) query.uniqueResult();
		session.flush();
		session.clear();

		if (originalquantity != null) {
			if (originalquantity != 0) {
				return originalquantity;
			} else {
				return availquantity;
			}

		} else {
			return availquantity;
		}

	}

	@Transactional
	public void saveReturnInvoice(DisplayInvoice displayInvoice, Customer customer, BigDecimal appliedtax) {

		Session session = sessionFactory.getCurrentSession();
		Invoice invoice = new Invoice();
		invoice.setInvoicenumber(getNewInvoiceNo(displayInvoice.getSalesperson()));
		invoice.setOrderdate(displayInvoice.getOrderdate());
		invoice.setCustomerid(displayInvoice.getCustomerid());
		String status = "N";
		if (displayInvoice.getInvoicenumber() != null) {
			Invoice orgInvoice = this.searchInvoice(displayInvoice.getInvoicenumber().toString());
			String stat = orgInvoice.getStatus();
			if ((stat.trim().equalsIgnoreCase("C") || stat.trim().equalsIgnoreCase("W")
					|| stat.trim().equalsIgnoreCase("P"))) {
				invoice.setStatus(stat.trim());
			} else {
				invoice.setStatus(status);
			}
			if (orgInvoice.getDiscounttype().trim().equals("")) {
				invoice.setDiscounttype("G");
			} else {
				invoice.setDiscounttype(orgInvoice.getDiscounttype());
			}
		} else {
			invoice.setDiscounttype("G");
			invoice.setStatus(status);
		}
		invoice.setReturnedinvoice(displayInvoice.getReturnedinvoice());
		invoice.setShipvia(displayInvoice.getShipvia());
		// if (status.trim().equalsIgnoreCase("M")) {
		// invoice.setSalesPerson(invForm.getSalesPerson());
		// } else {
		// invoice.setSalesPerson(user.getUsername());
		// }
		invoice.setSalesperson(displayInvoice.getSalesperson());
		// getAmounts(invoice, invForm, request, user, customerLevel,
		// customerSetFirstTime); Already done in controller
		invoice.setBillattention("");
		/*
		 * todo
		 */
		invoice.setInvoicetotal(displayInvoice.getInvoicetotal());
		invoice.setTax(displayInvoice.getTax());
		invoice.setDiscount(displayInvoice.getDiscount());
		invoice.setPaymentterms(customer.getPaymentterms());
		invoice.setBalance(displayInvoice.getBalance());
		// invoice.setAppliedamount(displayInvoice.getAppliedamount());
		// invoice.setDatenewapplied(null);
		// invoice.setHistory(status);
		invoice.setInvoicetime(System.currentTimeMillis());
		// invoice.setIsdelivered(status);
		// invoice.setIsprinted(status);
		// invoice.setNewapplied(BigDecimal.ZERO);
		// invoice.setNotes(status);

		Address shipAddress = new Address();
		Address billAddress = new Address();
		billAddress.setId(displayInvoice.getCustomerid());
		billAddress.setType("Bill");
		billAddress.setWho("Cust");
		billAddress.setDatecreated(displayInvoice.getOrderdate());
		billAddress.setInvoicenumber(invoice.getInvoicenumber());
		billAddress.setAddr1("");
		billAddress.setAddr2("");
		billAddress.setCity("");
		billAddress.setState("");
		billAddress.setRegion("");
		billAddress.setPostalcode("");
		billAddress.setCountry("");

		billAddress.setPhone("");
		billAddress.setExt("");
		billAddress.setFax("");

		invoice.setShipto(displayInvoice.getCustomerName());
		invoice.setShipattention("");
		shipAddress.setType("Ship");
		shipAddress.setWho("Cust");
		shipAddress.setId(displayInvoice.getCustomerid());
		shipAddress.setDatecreated(displayInvoice.getOrderdate());
		shipAddress.setInvoicenumber(invoice.getInvoicenumber());
		shipAddress.setAddr1("");
		shipAddress.setAddr2("");
		shipAddress.setCity("");
		shipAddress.setState("");
		shipAddress.setRegion("");
		shipAddress.setPostalcode("");
		shipAddress.setCountry("");
		shipAddress.setPhone("");
		shipAddress.setExt("");
		shipAddress.setFax("");
		// checkOtherDiscounts();
		if (invoice.getDiscount().equals(BigDecimal.ZERO)) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");
			java.util.Date dd1 = null;
			java.util.Date dd2 = null;
			try {
				dd1 = sdf.parse("06-28-2007");
				dd2 = sdf.parse("07-01-2007");
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			long fromDate = dd1.getTime();
			long toDate = dd2.getTime();
			long currTime = System.currentTimeMillis();
			double detailedDisc = 0;
			double discOnTotal = 0;

			String query = "Select max(OrderDate) from invoice where customerid=:customerId";
			SQLQuery maxOrderDateQuery = session.createSQLQuery(query);
			maxOrderDateQuery.setParameter("customerId", invoice.getCustomerid());
			long compareTime = 0L;
			long maxTime = 0L;
			Date ddX = (Date) maxOrderDateQuery.uniqueResult();
			if (ddX != null) {
				maxTime = ddX.getTime();
				compareTime = System.currentTimeMillis() - 7689600000L;
			}
			if (customer.getCustomerlevel() == 0) {
				if (currTime > fromDate && currTime < toDate) {
					int monthEndDiscount = getMonthEndDiscount(invoice.getCustomerid());
					double xy = invoice.getInvoicetotal().multiply(BigDecimal.valueOf(monthEndDiscount))
							.divide(BigDecimal.valueOf(100)).doubleValue();
					double xx = checkDetailedDiscount(monthEndDiscount, displayInvoice.getDisplayinvoicedetailslist());
					if (xx > 0) {
						discOnTotal = xy;
						detailedDisc = xx;
						invoice.setDiscounttype("M");
					}
				}
				if (invoice.getInvoicetotal().doubleValue() > 499 && isFirstTimeBuying(invoice.getCustomerid())) {
					// Gives $ 500 Discount And New Customer Discount
					double xy = invoice.getInvoicetotal().doubleValue() * .15;
					double xx = checkDetailedDiscount(15, displayInvoice.getDisplayinvoicedetailslist());
					if (xx > 0 && xx > detailedDisc) {
						discOnTotal = xy;
						detailedDisc = xx;
						invoice.setDiscounttype("X");
					}
				}
				if (isFirstTimeBuying(invoice.getCustomerid()) && !invoice.getDiscounttype().trim().equals("X")) {
					// Gives New Customer Discount
					double xy = invoice.getInvoicetotal().doubleValue() * .10;
					double xx = checkDetailedDiscount(10, displayInvoice.getDisplayinvoicedetailslist());
					if (xx > 0 && xx > detailedDisc) {
						discOnTotal = xy;
						detailedDisc = xx;
						invoice.setDiscounttype("N");
					}
				}
				if (maxTime < compareTime && !invoice.getDiscounttype().trim().equals("X")
						&& !invoice.getDiscounttype().trim().equals("N")) {
					// Gives Discount For Returning Customers
					double xy = invoice.getInvoicetotal().doubleValue() * .10;
					double xx = checkDetailedDiscount(10, displayInvoice.getDisplayinvoicedetailslist());
					if (xx > 0 && xx > detailedDisc) {
						discOnTotal = xy;
						detailedDisc = xx;
						invoice.setDiscounttype("R");
					}
				}
				if (invoice.getInvoicetotal().doubleValue() > 499 && !invoice.getDiscounttype().trim().equals("R")
						&& !invoice.getDiscounttype().trim().equals("X")
						&& !invoice.getDiscounttype().trim().equals("N")) {
					// Gives $ 500 Discount
					double xy = invoice.getInvoicetotal().doubleValue() * .05;
					double xx = checkDetailedDiscount(5, displayInvoice.getDisplayinvoicedetailslist());
					if (xx > 0 && xx > detailedDisc) {
						discOnTotal = xy;
						detailedDisc = xx;
						invoice.setDiscounttype("O");
					}
				}
				if (detailedDisc != 0 && discOnTotal != 0) {
					double newDisc = 0;
					if (detailedDisc < discOnTotal) {
						newDisc = detailedDisc;
					} else {
						newDisc = discOnTotal;
					}
					if (newDisc != 0 && invoice.getReturnedinvoice() == 0) {
						invoice.setDiscount(BigDecimal.valueOf(newDisc));
						invoice.setBalance(invoice.getBalance().subtract(BigDecimal.valueOf(newDisc)));
					}
				}
			}
			if (customer.getCustomerlevel() <= 8) {
				double xyz = checkPromoDiscount(displayInvoice.getDisplayinvoicedetailslist());
				if (xyz > invoice.getDiscount().doubleValue()) {
					invoice.setDiscount(BigDecimal.valueOf(xyz));
					invoice.setBalance(BigDecimal
							.valueOf(invoice.getInvoicetotal().doubleValue() + invoice.getTax().doubleValue() - xyz));
					invoice.setDiscounttype("P");
				}
			}
		}
		if (invoice.getDiscount().doubleValue() > 0 && invoice.getTax().doubleValue() > 0) {
			invoice.setTax((invoice.getInvoicetotal().subtract(invoice.getDiscount()))
					.multiply(appliedtax.divide(BigDecimal.valueOf(100))));
			invoice.setBalance(invoice.getInvoicetotal().add(invoice.getTax()).subtract(invoice.getDiscount()));
		}
		session.save(invoice);
		SQLQuery delQuery = session.createSQLQuery("DELETE FROM InvNo where InvNum=:invoiceNumber");
		delQuery.setParameter("invoiceNumber", invoice.getInvoicenumber());
		int executeUpdate = delQuery.executeUpdate();
		session.save(shipAddress);
		session.save(billAddress);
		List<DisplayInvoiceDetail> displayinvoicedetailslist = displayInvoice.getDisplayinvoicedetailslist();
		for (DisplayInvoiceDetail item : displayinvoicedetailslist) {
			InvoiceDetails invItem = new InvoiceDetails();
			Parts part = getPartInfo(item.getPartnumber());

			if ((part == null) && item.getPartnumber().startsWith("XX")) {
				part = getPartInfo(item.getPartnumber().substring(2));
			}
			if (part.getUnitsinstock() > 0) {
				if (!part.getActualprice().equals(BigDecimal.ZERO)) {
					invItem.setActualprice(part.getActualprice());
				} else if ((part.getInterchangeno() != null) && !part.getInterchangeno().trim().equals("")
						&& (!getPartInfo(part.getInterchangeno()).getActualprice().equals(BigDecimal.ZERO))) {
					invItem.setActualprice(getPartInfo(part.getInterchangeno()).getActualprice());
				} else {
					List<Parts> v = getAllInterChangeParts(part.getPartno());

					if (v == null) {
						invItem.setActualprice(BigDecimal.ZERO);
					} else {
						for (Parts p : v) {
							if (!p.getActualprice().equals(BigDecimal.ZERO)) {
								invItem.setActualprice(p.getActualprice());
								break;
							}
						}
					}
				}
			} else {
				invItem.setActualprice(BigDecimal.ZERO);
			}

			invItem.setInvoicenumber(invoice.getInvoicenumber());
			invItem.setPartnumber(item.getPartnumber());
			invItem.setActualprice(item.getActualprice());
			invItem.setQuantity(item.getQuantity());
			invItem.setSoldprice(item.getSoldprice());
			session.save(invItem);
			int qty = getQuantity(invItem.getPartnumber());
			if (!invItem.getPartnumber().startsWith("XX")) {
				qty -= invItem.getQuantity();
				changeQuantity(invItem.getPartnumber(), qty);
			}
		}
		displayInvoice.setInvoicenumber(invoice.getInvoicenumber());

	}

	@Transactional
	public Invoice searchInvoice(String searchinvoicenumber) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Invoice invoice where invoice.invoicenumber = :searchinvoicenumber";
		Query query = session.createQuery(hSql);
		query.setParameter("searchinvoicenumber", Integer.parseInt(searchinvoicenumber));

		List<Invoice> invoices = query.list();

		session.flush();
		session.clear();
		if (invoices != null) {
			if (invoices.size() > 0) {
				return invoices.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@SuppressWarnings("unused")
	@Transactional
	public InvoiceStatus verifyTaxAndOthers(DisplayInvoice displayinvoice, AppUser user, Customer customer,
			String appliedtax) {

		InvoiceStatus invoicestatus = new InvoiceStatus();
		invoicestatus.setInvoicemessage("");
		invoicestatus.setInvoicemode("proceed");

		List<DisplayInvoiceDetail> displayinvoicedetails = displayinvoice.getDisplayinvoicedetailslist();
		Double invoicetotaldouble = new Double("0.00");
		BigDecimal invoicetotal = new BigDecimal("0.00");
		BigDecimal invoicetax = new BigDecimal("0.00");
		BigDecimal discount = new BigDecimal("0.00");
		BigDecimal amountowed = new BigDecimal("0.00");
		Boolean istherereturnedparts = false;
		Integer returnedinvoicenumber = 0;

		if (displayinvoicedetails != null) {
			if (displayinvoicedetails.size() > 0) {
				// LOGGER.info("displayinvoicedetails.size() -> " +
				// displayinvoicedetails.size());
				if (!user.getActualrole().trim().equalsIgnoreCase("admin")
						&& !user.getActualrole().trim().equalsIgnoreCase("accounting")) {
					displayinvoice.setReturnedinvoice(0);
				} else {
					returnedinvoicenumber = displayinvoice.getReturnedinvoice();
					if (returnedinvoicenumber != null) {
						if (returnedinvoicenumber != 0) {
							Invoice returnedinvoice = searchInvoice(returnedinvoicenumber.toString());
							BigDecimal returndiscount = returnedinvoice.getDiscount();
							if (returndiscount != null) {
								if ((returndiscount.compareTo(bigdecimalzero) != 0)
										&& (displayinvoice.getDiscount().compareTo(bigdecimalzero) == 0)) {
									displayinvoice.setDiscount((returndiscount.multiply(bigdecimalminusone)).setScale(2,
											RoundingMode.HALF_EVEN));
								}
							}
						}
					}
				} // else userrolecheck

				for (DisplayInvoiceDetail displayinvoicedetail : displayinvoicedetails) {
					// LOGGER.info(displayinvoicedetail.getPartnumber());
					if (displayinvoicedetail.getPartnumber() != null) {
						if (!displayinvoicedetail.getPartnumber().equalsIgnoreCase("")) {
							if (!(displayinvoicedetail.getPartnumber().startsWith("XX"))) {
								Parts originalpart = getSelectedPartsDetailsFromSelection(
										displayinvoicedetail.getPartnumber(), customer);
								// LOGGER.info("" + displayinvoicedetail.getSoldprice()
								// .compareTo(originalpart.getCalculatedprice()));
								if ((displayinvoicedetail.getSoldprice()
										.compareTo(originalpart.getCalculatedprice()) < 0)) {
									if (!user.getActualrole().trim().equalsIgnoreCase("admin")
											&& !user.getActualrole().trim().equalsIgnoreCase("accounting")) {
										displayinvoicedetail.setSoldprice(originalpart.getCalculatedprice());
									}
								}

							}
						}
					}

					if ((displayinvoicedetail.getQuantity() < 0)) {
						if ((user.getActualrole().trim().equalsIgnoreCase("admin"))
								|| (user.getActualrole().trim().equalsIgnoreCase("accounting"))) {
							istherereturnedparts = true;
							if (displayinvoice.getReturnedinvoice() != 0) {

								if ((quantityInInvoice(displayinvoice.getReturnedinvoice(),
										displayinvoicedetail.getPartnumber(),
										displayinvoicedetail.getQuantity())) != 0) {
									if ((quantityInInvoice(displayinvoice.getReturnedinvoice(),
											displayinvoicedetail.getPartnumber(), displayinvoicedetail
													.getQuantity()) < (displayinvoicedetail.getQuantity() * -1))) {
										invoicestatus
												.setInvoicemessage("Quantity Not Matching with the original Invoice");
										invoicestatus.setInvoicemode("error");
									}
									invoicestatus.setInvoicemessage("*****  THE PART "
											+ displayinvoicedetail.getPartnumber() + " IS NOT AVAILABLE IN THE INVOICE "
											+ displayinvoice.getReturnedinvoice() + "  ***** ");
									invoicestatus.setInvoicemode("error");

								} else if (isDoubleReturn(displayinvoice.getInvoicenumber(),
										displayinvoice.getReturnedinvoice(), displayinvoicedetail.getPartnumber(),
										displayinvoicedetail.getQuantity())) {
									invoicestatus
											.setInvoicemessage("*****  THE PART " + displayinvoicedetail.getPartnumber()
													+ " ALREADY HAS A RETURNED INVOICE  ***** ");
									invoicestatus.setInvoicemode("error");
								} else {
									List<InvoiceDetails> invoicedetaillist = getInvoiceDetailsFromInvoiceNumber(
											displayinvoice.getReturnedinvoice());
									if (invoicedetaillist != null) {
										for (InvoiceDetails returninvoicedetails : invoicedetaillist) {
											if (returninvoicedetails.getPartnumber().trim()
													.equalsIgnoreCase(displayinvoicedetail.getPartnumber().trim())) {
												BigDecimal cst = returninvoicedetails.getSoldprice();
												if ((!(cst.compareTo(bigdecimalzero) == 0)) && (!(cst
														.compareTo(displayinvoicedetail.getSoldprice()) == 0))) {
													displayinvoicedetail.setSoldprice(cst);
												}
												break;
											}
										}

									}
								}

							}
						} else {
							displayinvoicedetail.setQuantity(-1);
							displayinvoice.setReturnedinvoice(0);
						}
					}
					BigDecimal tot = displayinvoicedetail.getSoldprice()
							.multiply(new BigDecimal(displayinvoicedetail.getQuantity()));
					if (displayinvoicedetail.getPartnumber().startsWith("XX")) {
						tot = tot.multiply(bigdecimalminusone);
					}
					// LOGGER.info("PRICE TO ADD: " + tot);
					invoicetotaldouble = invoicetotaldouble + tot.doubleValue();
					// LOGGER.info("INVOICE TOTAL: " + invoicetotaldouble);

				} // for loop for displayinvoicedetail
				if ((invoicetotaldouble != null) && (invoicetotaldouble != 0.00)) {
					invoicetotal = new BigDecimal(invoicetotaldouble.doubleValue());
					displayinvoice.setInvoicetotal(invoicetotal.setScale(2, RoundingMode.HALF_EVEN));
				} else {
					invoicetotal = new BigDecimal("0.00");
					displayinvoice.setInvoicetotal(invoicetotal.setScale(2, RoundingMode.HALF_EVEN));
				}

				if (!(invoicetotal.compareTo(bigdecimalzero) == 0)) {
					invoicetotal = invoicetotal.setScale(2, RoundingMode.HALF_EVEN);
				}

				if (displayinvoice.getDiscount() != null) {
					displayinvoice.setDiscount(displayinvoice.getDiscount().setScale(2, RoundingMode.HALF_EVEN));
				} else {
					displayinvoice.setDiscount(new BigDecimal("0.00"));
				}

				if ((customer.getTaxid() != null) && (!(customer.getTaxid().equals("")))
						&& (customer.getTaxid().equals("Y"))) {
					displayinvoice.setTax(new BigDecimal("0.00"));
				} else {
					// LOGGER.info("APPLIED TAX: " + new BigDecimal(appliedtax));
					invoicetax = (displayinvoice.getInvoicetotal().subtract(displayinvoice.getDiscount()))
							.multiply(new BigDecimal(appliedtax));
					// LOGGER.info("INVOICE TAX: " + invoicetax);
					displayinvoice.setTax(invoicetax.setScale(2, RoundingMode.HALF_EVEN));
				}

				BigDecimal amountvowed = invoicetax.add(invoicetax).subtract(displayinvoice.getDiscount());
				// LOGGER.info("AMOUNTVOWED: " + amountvowed);

			} // invoicedetails size check

			// This is for checking original invoice when there are returns
			if ((istherereturnedparts) && (displayinvoice.getReturnedinvoice() == 0)) {

				invoicestatus.setInvoicemessage(
						"You must enter the original Invoice Number, </br>when there are returned parts");
				invoicestatus.setInvoicemode("error");

			}

		} // invoicedetails null check
		return invoicestatus;
	}
}
