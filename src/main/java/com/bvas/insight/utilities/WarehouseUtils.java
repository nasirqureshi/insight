package com.bvas.insight.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarehouseUtils {

	public static String INVOICE_NOT_DELIVERED_ALL = "SELECT i.invoicenumber AS invoicenumber, CONCAT(i.invoicetime, '') AS invoicetime, c.customerId AS customerId, i.salesperson AS salesperson, i.isdelivered AS isdelivered, c.companyname AS companyname,i.notes AS notes,a.region AS region,c.paymentterms as paymentterms, i.invoicetotal as invoicetotal, i.tax as tax, i.discount as discount"
			+ " FROM Invoice i, Customer c , Address a"
			+ " WHERE i.customerId = c.customerId AND a.id  = c.customerId AND i.customerId = a.id AND a.type = 'Standard' AND i.orderdate >  DATE_SUB(NOW(), INTERVAL 3 MONTH)"
			+ " AND i.status!='C' AND i.status!='W'  AND i.shipvia='Deliver'  AND i.isdelivered IN ('N','R') AND i.balance!=0  AND i.returnedinvoice=0  ORDER BY c.customerid, i.isdelivered DESC, i.invoiceNumber";

	public static final String INVOICE_NOT_DELIVERED_ALL_WILD = "SELECT i.invoicenumber AS invoicenumber, CONCAT(i.invoicetime, '') AS invoicetime, c.customerId AS customerId, i.salesperson AS salesperson, i.isdelivered AS isdelivered, c.companyname AS companyname,i.notes AS notes,a.region AS region,c.paymentterms as paymentterms, i.invoicetotal as invoicetotal, i.tax as tax, i.discount as discount"
			+ " FROM Invoice i, Customer c , Address a"
			+ " WHERE i.customerId = c.customerId AND a.id  = c.customerId AND i.customerId = a.id AND a.type = 'Standard' AND i.orderdate >  DATE_SUB(NOW(), INTERVAL 3 MONTH)"
			+ " AND i.status!='C' AND i.status!='W'  AND i.shipvia='Deliver'  AND i.isdelivered IN ('N','R') AND i.balance!=0  AND i.returnedinvoice=0   AND (i.invoicenumber like :searchtext OR i.customerid like :searchtext OR i.notes like :searchtext )  ORDER BY c.customerid, i.isdelivered DESC, i.invoiceNumber";

	public static String INVOICE_NOT_DELIVERED_ROUTE = "SELECT i.invoicenumber AS invoicenumber, CONCAT(i.invoicetime, '') AS invoicetime, c.customerId AS customerId, i.salesperson AS salesperson, i.isdelivered AS isdelivered, c.companyname AS companyname,i.notes AS notes,a.region AS region,c.paymentterms as paymentterms, i.invoicetotal as invoicetotal, i.tax as tax, i.discount as discount"
			+ " FROM Invoice i, Customer c , Address a"
			+ " WHERE i.customerId = c.customerId AND a.id  = c.customerId AND i.customerId = a.id AND a.type = 'Standard' AND i.orderdate >  DATE_SUB(NOW(), INTERVAL 3 MONTH)"
			+ " AND i.status!='C' AND i.status!='W'  AND i.shipvia='Deliver'  AND i.isdelivered IN ('N','R') AND i.balance!=0  AND i.returnedinvoice=0  AND a.region=:region   ORDER BY c.customerid, i.isdelivered DESC, i.invoiceNumber";

	public static final String INVOICE_NOT_DELIVERED_ROUTE_WILD = "SELECT i.invoicenumber AS invoicenumber, CONCAT(i.invoicetime, '') AS invoicetime, c.customerId AS customerId, i.salesperson AS salesperson, i.isdelivered AS isdelivered, c.companyname AS companyname,i.notes AS notes,a.region AS region,c.paymentterms as paymentterms, i.invoicetotal as invoicetotal, i.tax as tax, i.discount as discount"
			+ " FROM Invoice i, Customer c , Address a"
			+ " WHERE i.customerId = c.customerId AND a.id  = c.customerId AND i.customerId = a.id AND a.type = 'Standard' AND i.orderdate >  DATE_SUB(NOW(), INTERVAL 3 MONTH)"
			+ " AND i.status!='C' AND i.status!='W'  AND i.shipvia='Deliver'  AND i.isdelivered  IN ('N','R') AND i.balance!=0  AND i.returnedinvoice=0  AND a.region=:region  AND (i.invoicenumber like :searchtext OR i.customerid like :searchtext OR i.notes like :searchtext ) ORDER BY c.customerid, i.isdelivered DESC, i.invoiceNumber";

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseUtils.class);

	public static String getTermDescription(Character character) {

		String characterStr = character.toString();
		switch (characterStr) {
		case "O":
			return "**CASH**";

		case "C":
			return "**COD**";

		case "W":
			return "**WKLY**";

		case "B":
			return "**BI-WK**";

		case "M":
			return "**MTHLY**";

		default:
			return "";
		}
	}
}
