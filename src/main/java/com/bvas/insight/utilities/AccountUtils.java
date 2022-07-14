package com.bvas.insight.utilities;

public class AccountUtils {

	public static String INVENTORYTRANSFER_REPORT = "FROM LocalOrders WHERE SupplierId =:localvendorid AND  QUANTITY < 0 AND vendorinvdate BETWEEN :datefrom AND :dateto";

	// public static String INVENTORYTRANSFER_SQL = "SELECT m.manufacturername as
	// manufacturername,m.MakeModelName as makemodelname, p.partno as PartNo,
	// p.UnitsInStock as unitsinstock, p.UnitsOnOrder as unitsonorder, p.year as
	// year, p.PartDescription as partdescription, p.location as location"
	public static String INVENTORYTRANSFER_SQL = "SELECT m.manufacturername as manufacturername,m.MakeModelName as makemodelname, p.partno as PartNo, p.UnitsInStock as unitsinstock, p.year as year, p.PartDescription as partdescription, p.location as location"
			+ " FROM  parts p, makemodel m " + "WHERE p.MakeModelCode = m.MakeModelCode " + "AND P.InterchangeNo = ''"
			+ "AND p.SubCategory = '6' " + "AND p.UnitsInStock > 5";

	public static String INVENTORYTRANSFERDEST_SQL = "SELECT m.manufacturername,m.MakeModelName, p.PartNo,        p.UnitsInStock,    P.UNITSONORDER,      p.year,     p.PartDescription , p.location"
			+ "FROM parts p, makemodel m" + " WHERE p.MakeModelCode = m.MakeModelCode" + " AND p.UnitsInStock < 5"
			+ " AND partno IN (";

	public static String LOCALVENDOR = "FROM LocalVendors WHERE shortcode=:vshortcode";

}
