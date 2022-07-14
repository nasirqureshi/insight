package com.bvas.insight.utilities;

public class InitiateOrdersUtils {

	public static String CATEGORY_CRITERIA = " AND subcategory  IN ( SELECT SUBCATEGORYCODE FROM SUBCATEGORY WHERE CATEGORYCODE=:categorycode) ";

	public static String EAGLE_CRITERIA = " AND subcategory  IN (11,10,12,13,14,15,6) AND ordertype NOT IN ( 'D', 'X','S') AND ORDERTYPE = 'E' ";

	public static String IO_PARTSMAIN_START = " SELECT p.partno AS partno,p.keystonenumber, p.yearfrom, p.yearto, p.ManufacturerName AS manufacturername, "
			+ " p.MakeModelName AS makemodelname , p.partdescription AS partdescription,  "
			+ " SUM(INVDTLS.Quantity) AS totalsold ,p.ordertype, p.UnitsInStock,p.UnitsOnOrder, "
			+ " p.ReorderLevel,p.actualprice AS buyingprice, p.costprice AS sellingprice, "
			+ " COALESCE(ROUND( ( ( p.CostPrice - p.actualprice ) * 100 ) / (p.CostPrice), 0),0) AS percent , "
			+ " CASE  WHEN p.reorderlevel < 40 THEN (p.reorderlevel   -p.unitsinstock-p.unitsonorder)  "
			+ " ELSE CEIL( (p.reorderlevel   -p.unitsinstock-p.unitsonorder) / 2  ) END AS requiredquantity "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i   " + " WHERE P.PARTNO = invdtls.PARTNUMBER   "
			+ " AND i.InvoiceNumber = invdtls.InvoiceNumber  " + " AND I.ReturnedInvoice = 0  "
			+ " AND p.interchangeno ='' " + " AND partdescription NOT LIKE 'Z%'  "
			+ " AND partdescription NOT LIKE '%*capa*%' " + " AND reorderlevel>=1  "
			+ " AND reorderlevel-unitsinstock-unitsonorder>0  " + " AND partdescription NOT LIKE '%-recycle%'  "
			+ " AND partdescription NOT LIKE '%re-cycle%'  " + " AND partno NOT LIKE 'zz%'  "
			+ " AND partno !='a0000' AND partno NOT LIKE 'mec%'  "
			+ " AND partno NOT LIKE 'ac0%' AND partno !='x0002'  " + " AND partno !='MEHCX'  AND subcategory!=69  "
			+ " AND i.OrderDate BETWEEN :orderfrom  AND :orderto";

	public static String LOCAL_CRITERIA = " AND subcategory  IN (11,10,12,13,14,15,6) AND ordertype NOT IN ( 'D', 'X','S') ";

	public static String MAKE_CRITERIA = " AND makemodelcode  IN ( SELECT makemodelcode FROM MAKEMODEL WHERE manufacturerid=:manufacturerid) ";

	public static String SUBCATEGORY_CRITERIA = " AND subcategory  =:subcategorycode";

	public static String TAIWAN_CRITERIA = " AND ordertype NOT IN ( 'D', 'X','S','E','R','W','A','B') AND subcategory NOT IN (43,70,55,62,63,56,60,88,71,54,74,75,85,69,39,66,10,12,13,14,15,81,11,57,61,5,6,99) ";

}
