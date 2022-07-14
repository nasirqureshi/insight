package com.bvas.insight.utilities;

public class AnalyticsUtils {

	public static String COSTOFGOODS_SQL = "SELECT i.invoicenumber AS invoicenumber, i.invoicetotal AS invoicetotal, i.Discount AS discount, SUM(id.ActualPrice * id.quantity) AS ourprice, "
			+ " SUM(id.SoldPrice * id.quantity) AS soldprice, (SUM(id.SoldPrice * id.quantity) - SUM(id.ActualPrice * id.quantity) - i.Discount) AS margin , i.tax as tax"
			+ " FROM invoice i, invoicedetails id WHERE i.invoicenumber = id.invoicenumber"
			+ " AND i.orderdate BETWEEN :orderdatefrom AND :orderdateto"
			+ " GROUP BY i.invoicenumber ORDER BY i.invoicenumber";
	// change see above NNQ + " AND id.actualprice > 0 GROUP BY i.invoicenumber
	// ORDER BY i.invoicenumber";

	public static String COSTOFGOODS_SQL_EXTENDED = "SELECT i.invoicenumber AS invoicenumber, i.invoicetotal AS invoicetotal, i.Discount AS discount, SUM(id.ActualPrice * id.quantity) AS ourprice, "
			+ " SUM(id.SoldPrice * id.quantity) AS soldprice, (SUM(id.SoldPrice * id.quantity) - SUM(id.ActualPrice * id.quantity) - i.Discount) AS margin, i.tax as tax"
			+ " FROM invoicearch i, invoicedetailsarch id WHERE i.invoicenumber = id.invoicenumber"
			+ " AND i.orderdate BETWEEN :orderdatefrom AND :orderdateto"
			+ " GROUP BY i.invoicenumber ORDER BY i.invoicenumber";
	// nnq change 20210424 + " AND id.actualprice > 0 GROUP BY i.invoicenumber ORDER
	// BY i.invoicenumber";

	public static String LOCALORDER_LIGHTS = "SELECT  p.makemodelname AS makemodelname, p.manufacturername AS manufacturername,"
			+ " p.partno AS partno,  p.year AS YEAR, p.capa AS capa, vo.vendorpartno  AS vendorpartno,"
			+ " p.partdescription   AS partdescription,p.unitsinstock AS unitsinstock,"
			+ " p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,vo.sellingrate  AS price ,"
			+ " CAST( (CEIL((p.reorderlevel * :forecastdays) / 60)   -p.unitsinstock -p.unitsonorder) AS UNSIGNED INTEGER)   AS quantity,"
			+ " (( (p.reorderlevel -p.unitsinstock-p.unitsonorder)  ) * vo.sellingrate)   AS totalprice, p.ordertype as ordertype"
			+ " FROM Parts p, VendorItems vo , Vendors v"
			+ " WHERE p.partno = vo.partno and v.supplierid = vo.supplierid " + " AND p.interchangeno='' "
			+ " AND p.partdescription NOT LIKE 'Z%' " + " AND p.partdescription NOT LIKE '%*capa*%'"
			+ " AND p.reorderlevel>=1 " + " AND p.reorderlevel-p.unitsinstock-p.unitsonorder>0 "
			+ " AND p.partdescription NOT LIKE '%-recycle%' " + " AND p.partdescription NOT LIKE '%re-cycle%' "
			+ " AND p.partno NOT LIKE 'zz%' " + " AND p.partno !='a0000' AND p.partno NOT LIKE 'mec%' "
			+ " AND p.partno NOT LIKE 'ac0%' AND p.partno !='x0002' "
			+ " AND p.partno !='MEHCX'  AND p.subcategory!=69 " + " AND p.subcategory  IN (11,10,12,13,14,15,6)"
			+ " AND p.ordertype NOT IN ( 'D', 'X','S') "
			+ " AND v.companytype <> 'H' AND  ( CEIL((p.reorderlevel * :forecastdays) / 60)   -p.unitsinstock -p.unitsonorder ) > 0 ";

	public static String LOCALORDER_TRANSAMERICA = "SELECT p.makemodelname AS makemodelname, p.manufacturername AS manufacturername,"
			+ " p.partno AS partno,  p.year AS YEAR, p.capa AS capa, vo.vendorpartno  AS vendorpartno,"
			+ " p.partdescription   AS partdescription,p.unitsinstock AS unitsinstock,"
			+ "  p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,vo.sellingrate  AS price ,"
			+ "  ( (p.reorderlevel -p.unitsinstock-p.unitsonorder)  )  AS quantity,"
			+ "  (( (p.reorderlevel -p.unitsinstock-p.unitsonorder)  ) * vo.sellingrate)   AS totalprice, P.ordertype as ordertype"
			+ "  FROM Parts p, VendorItems vo " + "  WHERE p.partno = vo.partno " + "  AND  p.interchangeno='' "
			+ " AND p.partdescription NOT LIKE 'Z%' " + " AND p.reorderlevel  -p.unitsinstock-p.unitsonorder>0 "
			+ " AND p.partdescription NOT LIKE '%-recycle%' " + " AND p.partdescription NOT LIKE '%re-cycle%'  "
			+ " AND p.partdescription NOT LIKE '%CAPA%' " + " AND p.partno NOT LIKE 'zz%' "
			+ " AND p.partno !='a0000' AND p.partno NOT LIKE 'mec%' "
			+ " AND p.partno NOT LIKE 'ac0%' AND p.partno !='x0002' "
			+ " AND p.partno !='MEHCX'  AND p.subcategory!=69 " + " AND p.reorderlevel>=1" + " AND p.ordertype  ='R'"
			+ " AND vo.supplierid =30"
			+ " AND p.subcategory NOT IN (43,70,55,62,63,56,60,88,71,54,74,75,85,69,39,66,10,12,13,14,15,81,11,57,61,5,6,99,28)";

	public static String LOCALORDER_WESTIN = "SELECT p.makemodelname AS makemodelname, p.manufacturername AS manufacturername,"
			+ " p.partno AS partno,  p.year AS YEAR, p.capa AS capa, vo.vendorpartno  AS vendorpartno,"
			+ " p.partdescription   AS partdescription,p.unitsinstock AS unitsinstock,"
			+ "  p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,vo.sellingrate  AS price ,"
			+ "  ( (p.reorderlevel -p.unitsinstock-p.unitsonorder)  )  AS quantity,"
			+ "  (( (p.reorderlevel -p.unitsinstock-p.unitsonorder)  ) * vo.sellingrate)   AS totalprice, P.ordertype as ordertype"
			+ "  FROM Parts p, VendorItems vo " + "  WHERE p.partno = vo.partno " + "  AND  p.interchangeno='' "
			+ " AND p.partdescription NOT LIKE 'Z%' " + " AND p.reorderlevel  -p.unitsinstock-p.unitsonorder>0 "
			+ " AND p.partdescription NOT LIKE '%-recycle%' " + " AND p.partdescription NOT LIKE '%re-cycle%'  "
			+ " AND p.partdescription NOT LIKE '%CAPA%' " + " AND p.partno NOT LIKE 'zz%' "
			+ " AND p.partno !='a0000' AND p.partno NOT LIKE 'mec%' "
			+ " AND p.partno NOT LIKE 'ac0%' AND p.partno !='x0002' "
			+ " AND p.partno !='MEHCX'  AND p.subcategory!=69 " + " AND p.reorderlevel>=1" + " AND p.ordertype  ='W'"
			+ " AND p.subcategory =28";

	public static String MONTH_CUSTOMER = "SELECT YEAR(i.OrderDate) AS yr,MONTH(i.OrderDate) AS mnth, c.COMPANYNAME as companyname, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, CUSTOMER c"
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber  AND c.CUSTOMERID = I.CustomerID "
			+ " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 AND i.OrderDate BETWEEN :orderfrom AND :orderto "
			+ " GROUP BY YEAR(i.OrderDate) ,MONTH(i.OrderDate),  c.COMPANYNAME HAVING cnt > 0 ORDER BY YEAR(i.OrderDate) DESC, MONTH(i.OrderDate) DESC, cnt DESC";

	public static String MONTH_SUBCATEGORY = "SELECT YEAR(i.OrderDate) AS yr, MONTH(i.OrderDate) AS mnth, c.SubCategoryName as subcategory, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, subcategory c"
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND c.SubCategoryCode = p.SubCategory"
			+ " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 AND i.OrderDate BETWEEN :orderfrom AND :orderto "
			+ " GROUP BY YEAR(i.OrderDate) , MONTH(i.OrderDate),  P.SubCategory HAVING cnt > 0 ORDER BY YEAR(i.OrderDate) DESC, MONTH(i.OrderDate) DESC, cnt DESC";

	public static String SUBCAT_ALLBRANCH = "SELECT p.partno as partno, p.partdescription as partdescription, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , SUM(INVDTLS.Quantity) AS chicagoquantity "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i " + " WHERE P.PARTNO = invdtls.PARTNUMBER "
			+ " AND i.InvoiceNumber = invdtls.InvoiceNumber " + " AND p.SubCategory =:subcategorycode "
			+ " AND i.OrderDate BETWEEN :orderfrom AND :orderto " + " AND I.ReturnedInvoice = 0 "
			+ " AND p.interchangeno = '' " + " GROUP BY p.partno " + " HAVING chicagoquantity > 0 "
			+ " ORDER BY chicagoquantity DESC";

	public static String SUBCAT_DETAIL = "SELECT p.partno AS partno, p.ordertype AS ordertype, p.actualprice AS actualprice,p.costprice AS costprice, ( p.costprice - p.actualprice ) AS partmargin,"
			+ "     ROUND(((p.costprice - p.actualprice ) * 100  )/ (p.costprice), 2)  AS partprcnt,"
			+ "     SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ "     SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ "     ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i"
			+ "     WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 "
			+ "     AND i.OrderDate BETWEEN :orderfrom AND :orderto AND p.SubCategory =:subcategorycode GROUP BY p.partno   ORDER BY prcnt";

	public static String SUBCAT_DETAIL_ALL = "SELECT p.partno AS partno, p.ordertype AS ordertype, p.actualprice AS actualprice,p.costprice AS costprice, ( p.costprice - p.actualprice ) AS partmargin,"
			+ "     ROUND(((p.costprice - p.actualprice ) * 100  )/ (p.costprice), 2)  AS partprcnt,"
			+ "     SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ "     SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ "     ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i"
			+ "     WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 "
			+ "     AND i.OrderDate BETWEEN :orderfrom AND :orderto  GROUP BY p.partno   ORDER BY prcnt";

	public static String SUBCATEGORY_SALES_HISTORY =

			" SELECT p.SubCategory AS subcategorycode , c.SubCategoryName AS subcategory, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice, "
					+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin, "
					+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, subcategory c "
					+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND c.SubCategoryCode = p.SubCategory"
					// + " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 "
					+ " AND i.OrderDate >= :orderfrom AND i.OrderDate <= :orderto  AND p.SubCategory=:subcategorycode ";

	public static String SUBCATEGORY_SALES_HISTORY_MAIN =

			" SELECT p.SubCategory AS subcategorycode ,c.SubCategoryName AS subcategory, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice, "
					+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin, "
					+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, subcategory c "
					+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND c.SubCategoryCode = p.SubCategory"
					// + " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 "
					+ " AND i.OrderDate >= :orderfrom AND i.OrderDate <= :orderto " + " GROUP BY P.SubCategory; ";

	public static String SUBCATEGORY_SALES_HISTORY_RETURN_MAIN =

			" SELECT p.SubCategory AS subcategorycode ,c.SubCategoryName AS subcategory, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice, "
					+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin, "
					+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, subcategory c "
					+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND c.SubCategoryCode = p.SubCategory"
					// + " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice > 0 "
					+ " AND i.OrderDate >= :orderfrom AND i.OrderDate <= :orderto "
					+ " GROUP BY P.SubCategory ORDER BY SUM(INVDTLS.Quantity) DESC;";

	public static final String VENDORITEMS_CUBICFEET_LAST5_SQL = "SELECT CONCAT(V.companytype,'-', V.SHORTCODE ,'-', VI.SUPPLIERID ,'-',ROUND(CASE WHEN V.companytype IN ('T' , 'E') THEN ( VI.SELLINGRATE +  (:factor * VI.ItemSize) )   ELSE VI.SELLINGRATE END ,2)) as PASS "
			+ " FROM VENDORITEMS VI, VENDORS V WHERE VI.SupplierID = V.SupplierID AND VI.PARTNO=:partno AND VI.SELLINGRATE > 0.01 AND V.CompanyType NOT IN ( 'Z' ,'H') "
			+ " ORDER BY VI.SELLINGRATE LIMIT 3; ";

	public static final String VENDORITEMS_SURCHARGE_LAST5_SQL = " SELECT CONCAT(V.companytype,'-', V.SHORTCODE ,'-', VI.SUPPLIERID ,'-',ROUND(CASE WHEN V.companytype IN ('T' , 'E') THEN VI.SELLINGRATE * :factor  ELSE VI.SELLINGRATE END ,2)) as PASS"
			+ " FROM VENDORITEMS VI, VENDORS V WHERE VI.SupplierID = V.SupplierID AND VI.PARTNO=:partno AND VI.SELLINGRATE > 0.01 AND V.CompanyType <> 'Z' ORDER BY VI.SELLINGRATE LIMIT 3;";

	public static final String VENDORPRICECOMPARISON_ALL_SQL = "SELECT p.partno AS partno, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " SUM(INVDTLS.Quantity) AS totalsold , p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel  "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i   "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0  "
			+ " AND i.OrderDate BETWEEN :orderfrom AND  :orderto    AND unitsinstock<=:stockvalue"
			+ " AND p.interchangeno = '' GROUP BY p.partno HAVING totalsold > 0  " + " UNION ALL  "
			+ " SELECT p.interchangeno AS partno, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription,  "
			+ " SUM(INVDTLS.Quantity) AS totalsold , p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel  "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i   "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0  "
			+ " AND i.OrderDate BETWEEN :orderfrom AND :orderto   AND unitsinstock<=:stockvalue"
			+ " AND p.interchangeno <> '' GROUP BY  p.interchangeno  HAVING totalsold > 0 ORDER BY totalsold DESC;";

	public static final String VENDORPRICECOMPARISON_SQL = "SELECT p.partno AS partno, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription, "
			+ " SUM(INVDTLS.Quantity) AS totalsold , p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel  "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i   "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0  "
			+ " AND p.SubCategory =:subcategorycode "
			+ " AND i.OrderDate BETWEEN :orderfrom AND  :orderto  AND unitsinstock<=:stockvalue"
			+ " AND p.interchangeno = '' GROUP BY p.partno HAVING totalsold > 0  " + " UNION ALL  "
			+ " SELECT p.interchangeno AS partno, p.ManufacturerName AS manufacturername, p.MakeModelName AS makemodelname , p.partdescription AS partdescription,  "
			+ " SUM(INVDTLS.Quantity) AS totalsold , p.UnitsInStock,p.UnitsOnOrder,p.ReorderLevel  "
			+ " FROM PARTS P, invoicedetails invdtls, INVOICE i   "
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND I.ReturnedInvoice = 0  "
			+ " AND p.SubCategory =:subcategorycode "
			+ " AND i.OrderDate BETWEEN :orderfrom AND :orderto   AND unitsinstock<=:stockvalue"
			+ " AND p.interchangeno <> '' GROUP BY  p.interchangeno  HAVING totalsold > 0 ORDER BY totalsold DESC;";

	public static String WEEK_CUSTOMER = "SELECT YEAR(i.OrderDate) AS yr,MONTH(i.OrderDate) AS mnth,WEEK(i.OrderDate) AS wk, c.COMPANYNAME as companyname, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, CUSTOMER c"
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber  AND c.CUSTOMERID = I.CustomerID "
			+ " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 AND i.OrderDate BETWEEN :orderfrom AND :orderto "
			+ " GROUP BY YEAR(i.OrderDate) ,MONTH(i.OrderDate),WEEK(i.OrderDate),  c.COMPANYNAME HAVING cnt > 0 ORDER BY YEAR(i.OrderDate) DESC ,MONTH(i.OrderDate) DESC,WEEK(i.OrderDate) DESC, cnt DESC";

	public static String WEEK_SUBCATEGORY = "SELECT YEAR(i.OrderDate) AS yr, MONTH(i.OrderDate) AS mnth, WEEK(i.OrderDate) AS wk, c.SubCategoryName as subcategory, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, subcategory c"
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND c.SubCategoryCode = p.SubCategory"
			+ " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 AND i.OrderDate BETWEEN :orderfrom AND :orderto "
			+ " GROUP BY YEAR(i.OrderDate) , MONTH(i.OrderDate),WEEK(i.OrderDate),   P.SubCategory HAVING cnt > 0 ORDER BY YEAR(i.OrderDate) DESC, MONTH(i.OrderDate) DESC, WEEK(i.OrderDate) DESC, cnt DESC";

	public static String YEAR_CUSTOMER = "SELECT YEAR(i.OrderDate) AS yr, c.COMPANYNAME as companyname, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, CUSTOMER c"
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber  AND c.CUSTOMERID = I.CustomerID "
			+ " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 AND i.OrderDate BETWEEN :orderfrom AND :orderto "
			+ " GROUP BY YEAR(i.OrderDate) , c.COMPANYNAME HAVING cnt > 0 ORDER BY YEAR(i.OrderDate) DESC, cnt DESC";

	public static String YEAR_SUBCATEGORY = "SELECT YEAR(i.OrderDate) AS yr, c.SubCategoryName as subcategory, SUM(INVDTLS.Quantity) AS cnt,SUM(invdtls.ActualPrice * INVDTLS.Quantity) AS ourprice,"
			+ " SUM(invdtls.SoldPrice  * INVDTLS.Quantity) AS salesprice,( SUM(invdtls.SoldPrice  * INVDTLS.Quantity ) - SUM(invdtls.ActualPrice * INVDTLS.Quantity) ) AS margin,"
			+ " ROUND(( SUM(invdtls.SoldPrice)  - SUM(invdtls.ActualPrice) ) * 100  /  (SUM(invdtls.SoldPrice)), 2) AS prcnt   FROM PARTS P, invoicedetails invdtls, INVOICE i, subcategory c"
			+ " WHERE P.PARTNO = invdtls.PARTNUMBER AND i.InvoiceNumber = invdtls.InvoiceNumber AND c.SubCategoryCode = p.SubCategory"
			+ " AND INVDTLS.Quantity > 0 AND I.ReturnedInvoice = 0 AND i.OrderDate BETWEEN :orderfrom AND :orderto "
			+ " GROUP BY YEAR(i.OrderDate) , P.SubCategory HAVING cnt > 0 ORDER BY YEAR(i.OrderDate) DESC, cnt DESC";

}
