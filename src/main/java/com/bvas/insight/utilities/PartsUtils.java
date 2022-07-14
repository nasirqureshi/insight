package com.bvas.insight.utilities;

public class PartsUtils {

	public static final String FUTUREORDERS_SQL = "SELECT  a.PartNo AS partno,a.OrderNo AS orderno, B.EstimatedArrivalDate AS estimatedarrivaldate, a.Quantity AS quantity, c.CompanyName AS companyname, a.Price AS price "
			+ " FROM VendorOrderedItems a, VendorOrder b, Vendors c WHERE  a.OrderNo=b.OrderNo AND b.UpdatedInventory='N' AND b.IsFinal='Y' AND b.SupplierId=c.SupplierId AND a.PartNo=:partno ORDER BY B.EstimatedArrivalDate;";

	public static final String LOCALORDERSHISTORY_SQL = "SELECT a.InvoiceNo AS invoiceno, a.DateEntered AS dateentered, a.PartNo AS partno, a.Quantity AS quantity, a.Price AS price, b.CompanyName AS companyname "
			+ " FROM LocalOrders a, LocalVendors b WHERE  a.SupplierId=b.SupplierId AND a.PartNo=:partno ORDER BY DateEntered DESC;";

	public static final String PARTSPARTSHISTORY_INTERCHANGE_SQL = "SELECT p.PartNo AS partno, p.interchangeno AS interchangeno,  p.ManufacturerName AS make, p.MakeModelName AS model,	"
			+ " p.PartDescription AS partdescription, p.yearfrom AS yearfrom, p.yearto AS yearto,p.location AS location, "
			+ " p.unitsinstock AS unitsinstock,p.unitsonorder AS unitsonorder, p.reorderlevel AS reorderlevel, "
			+ " p.actualprice AS actualprice , p.costprice AS costprice , p.keystonenumber AS keystonenumber "
			+ " FROM parts p WHERE  p.interchangeno =:partno ";

	public static final String PARTSPARTSHISTORY_SQL = "SELECT p.PartNo AS partno, p.interchangeno AS interchangeno,  p.ManufacturerName AS make, p.MakeModelName AS model,	"
			+ " p.PartDescription AS partdescription, p.yearfrom AS yearfrom, p.yearto AS yearto,p.location AS location, "
			+ " p.unitsinstock AS unitsinstock,p.unitsonorder AS unitsonorder, p.reorderlevel AS reorderlevel, "
			+ " p.actualprice AS actualprice , p.costprice AS costprice , p.keystonenumber AS keystonenumber "
			+ " FROM parts p WHERE  p.PartNo =:partno ";

	public static final String PROCESSEDORDERS1_SQL = "SELECT a.OrderNo AS orderno, C.InventoryDoneDate  AS inventorydonedate, a.PartNo AS partno, a.Quantity AS quantity, b.ItemDesc1 AS itemdesc1, b.ItemDesc2  AS itemdesc2, "
			+ " d.CompanyName AS companyname,  a.Price AS price FROM VendorOrderedItems a, VendorItems b, VendorOrder c, Vendors d  "
			+ " WHERE a.OrderNo=c.OrderNo AND c.UpdatedInventory='Y' AND c.SupplierId=b.SupplierId  "
			+ " AND b.partNo=a.partNo AND c.supplierid=d.supplierid AND a.PartNo=:partno ORDER BY C.InventoryDoneDate DESC";

	public static final String PROCESSEDORDERS2_SQL = "SELECT a.OrderNo AS orderno, B.InventoryDoneDate  AS inventorydonedate, a.PartNo AS partno, a.Quantity AS quantity, "
			+ " a.Price AS price, '' AS itemdesc1, '' AS  itemdesc2, c.CompanyName  AS companyname "
			+ " FROM VendorOrderedItems a, VendorOrder b, Vendors c WHERE a.vendorpartno='' AND a.OrderNo=b.OrderNo AND b.UpdatedInventory='Y'  "
			+ " AND b.SupplierId=c.SupplierId  AND a.PartNo=:partno ORDER BY B.InventoryDoneDate DESC;";

}
