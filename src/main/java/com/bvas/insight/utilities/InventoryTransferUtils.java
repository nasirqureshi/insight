package com.bvas.insight.utilities;

public class InventoryTransferUtils {

	public static final String PROCUREMENT_BASE_SQL = "SELECT parts.makemodelname AS makemodelname, parts.manufacturername AS manufacturername,	"
			+ "parts.partno AS partno,  parts.year AS YEAR, parts.capa AS capa,	"
			+ "parts.partdescription   AS partdescription,parts.unitsinstock AS unitsinstock, 	"
			+ "parts.unitsonorder  AS unitsonorder,parts.SAFETYQUANTITY  AS safetyquantity, 	"
			+ "CEIL ( (parts.reorderlevel - parts.unitsinstock - parts.unitsonorder) / 2)  AS quantitytoorder, 	"
			+ "parts.ordertype AS ordertype,  parts.ActualPrice as actualprice,parts.CostPrice as costprice, 	"
			+ "Coalesce(Round( ( ( parts.CostPrice - parts.actualprice ) * 100 ) / (parts.CostPrice), 0),0) as percent , 	"
			+ "parts.dpinumber as dpinumber, parts.reorderlevel as reorderlevel, parts.keystonenumber as keystonenumber "
			+ "FROM Parts parts WHERE parts.InterchangeNo = '' 	" + "AND parts.reorderlevel > 0 	"
			+ "AND ( (parts.reorderlevel - parts.unitsinstock - parts.unitsonorder)  ) > 1 and parts.ordertype <> 'D' AND parts.unitsinstock <=:unitsinstock and parts.unitsonorder <=:unitsonorder";

	public static final String SALESBYCATEGORY = "SELECT p.partno AS partno,p.keystonenumber, p.yearfrom, p.yearto,   p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " SUM(INVDTLS.Quantity) AS totalsold ,p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice as buyingprice, p.costprice AS sellingprice,Coalesce(Round( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) as percent "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i  "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0 "
			+ " AND p.SubCategory = ? "
			+ " AND i.OrderDate BETWEEN ? AND ? AND p.interchangeno =''  AND p.ordertype <> 'S'"
			+ " GROUP BY p.partno HAVING totalsold > 0    ORDER BY totalsold DESC";

	public static final String SALESBYCATEGORY_ALL = "SELECT p.partno AS partno,p.keystonenumber, p.yearfrom, p.yearto, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " SUM(INVDTLS.Quantity) AS totalsold ,p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice as buyingprice, p.costprice AS sellingprice,Coalesce(Round( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) as percent "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i  "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0 "
			+ " AND i.OrderDate BETWEEN ? AND ? AND p.interchangeno =''  AND p.ordertype <> 'S'"
			+ " GROUP BY p.partno HAVING totalsold > 0    ORDER BY totalsold  DESC";

	public static final String SALESBYCATEGORY_ALL_INTERCHANGE = "SELECT p.interchangeno AS partno,p.keystonenumber, p.yearfrom, p.yearto, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " SUM(INVDTLS.Quantity) AS totalsold ,p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice as buyingprice, p.costprice AS sellingprice,Coalesce(Round( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) as percent "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i  "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0 "
			+ " AND i.OrderDate BETWEEN :orderfrom AND :orderto AND p.interchangeno <> '' AND p.ordertype <> 'S'"
			+ " GROUP BY partno HAVING totalsold > 0  ORDER BY totalsold";

	public static final String SALESBYCATEGORY_INTERCHANGE = "SELECT p.interchangeno AS partno,p.keystonenumber, p.yearfrom, p.yearto,  p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " SUM(INVDTLS.Quantity) AS totalsold ,p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice as buyingprice, p.costprice AS sellingprice,Coalesce(Round( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) as percent "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i  "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0 "
			+ " AND p.SubCategory = ? "
			+ " AND i.OrderDate BETWEEN ? AND ? AND p.interchangeno <>''  AND p.ordertype <> 'S'"
			+ " GROUP BY partno HAVING totalsold > 0    ORDER BY totalsold  DESC";

	public static final String SALESBYCATEGORY_MAINNO = "SELECT p.partno AS partno,p.keystonenumber, p.yearfrom, p.yearto,  p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " 0 AS totalsold ,p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice AS buyingprice, p.costprice AS sellingprice, "
			+ " COALESCE(ROUND( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) AS percent "
			+ " FROM PARTS P WHERE P.PARTNO = ?  AND p.ordertype <> 'S'";

	public static final String SALESBYDEADINVENTORY = "SELECT p.partno AS partno,p.keystonenumber, p.yearfrom, p.yearto, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, 0.00 AS totalsold, "
			+ " p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice AS buyingprice, p.costprice "
			+ " AS sellingprice,COALESCE(ROUND( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) AS percent "
			+ " FROM PARTS P WHERE P.PARTNO NOT IN "
			+ " (SELECT DISTINCT id.partnumber FROM  invoice i , invoicedetails id WHERE i.invoicenumber = id.invoicenumber  AND i.OrderDate BETWEEN :orderfrom AND :orderto )"
			+ " AND p.SubCategory =:subcategorycode ";

	public static final String SALESBYDEADINVENTORY_ALL = "SELECT p.partno AS partno,p.keystonenumber, p.yearfrom, p.yearto, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, 0.00 AS totalsold, "
			+ " p.ordertype, p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel,p.actualprice AS buyingprice, p.costprice "
			+ " AS sellingprice,COALESCE(ROUND( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) AS percent "
			+ " FROM PARTS P WHERE P.PARTNO NOT IN "
			+ " (SELECT DISTINCT id.partnumber FROM  invoice i , invoicedetails id WHERE i.invoicenumber = id.invoicenumber  AND i.OrderDate BETWEEN :orderfrom AND :orderto ) ";

	public static final String SALESBYDEADINVENTORY_ALL_INTERCHANGE = "SELECT SUM(INVDTLS.Quantity) AS totalsold "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i  "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber  "
			+ " AND i.OrderDate BETWEEN :orderfrom AND :orderto  AND p.interchangeno =:partno"
			+ " AND p.interchangeno <> '' GROUP BY p.partno HAVING totalsold > 0 ORDER BY p.UnitsInStock DESC; ";

}
