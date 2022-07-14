package com.bvas.insight.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bvas.insight.data.FutureOrderDetailsPartsHistory;
import com.bvas.insight.data.LocalDetailsPartsHistory;
import com.bvas.insight.data.PartsPartHistory;
import com.bvas.insight.data.PartsPartLink;
import com.bvas.insight.data.ProcessedOrdersPartsHistory;
import com.bvas.insight.data.SelectedOrderItems;
import com.bvas.insight.data.StockCheckDetails;
import com.bvas.insight.data.UpdatePartsCostPrice;
import com.bvas.insight.data.UpdateVendorParts;
import com.bvas.insight.entity.InvoiceDetails;
import com.bvas.insight.entity.Manufacturer;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.entity.PartsLink;
import com.bvas.insight.entity.PartsMonthlySales;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.utilities.InsightUtils;
import com.bvas.insight.utilities.PartsUtils;

@Repository
@SuppressWarnings("unchecked")
public class PartsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartsService.class);

	private static void copyRow(Workbook destinationWb, Sheet destinationWSheet, Sheet sourceWSheet, int sourceRowNum,
			int destinationRowNum, String remarksColVal) {

		// Get the source / new row
		Row newRow = destinationWSheet.getRow(destinationRowNum);
		Row sourceRow = sourceWSheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create
		// a new row
		if (newRow != null) {
			destinationWSheet.shiftRows(destinationRowNum, destinationWSheet.getLastRowNum(), 1);
		} else {
			newRow = destinationWSheet.createRow(destinationRowNum);
		}

		CellStyle newCellStyle = destinationWb.createCellStyle();
		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			// CellStyle newCellStyle = destinationWb.createCellStyle();
			// newCellStyle.cloneStyleFrom(oldCell.getCellStyle());

			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			DataFormat df = destinationWb.createDataFormat();
			newCellStyle.setDataFormat(df.getFormat("General"));

			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}
		Cell remarksCell = newRow.createCell(sourceRow.getLastCellNum());
		remarksCell.setCellValue(remarksColVal);
		// If there are are any merged regions in the source row, copy to new
		// row
		// for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
		// CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
		// if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
		// CellRangeAddress newCellRangeAddress = new
		// CellRangeAddress(newRow.getRowNum(),
		// (newRow.getRowNum() +
		// (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
		// )),
		// cellRangeAddress.getFirstColumn(),
		// cellRangeAddress.getLastColumn());
		// worksheet.addMergedRegion(newCellRangeAddress);
		// }
		//
	}

	private DecimalFormat df2 = new DecimalFormat(".##");

	@Autowired
	private DataSource localDataSource;

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void createPartsMaintenance(Parts parts) {

		Session session = sessionFactory.getCurrentSession();
		parts.setSupplierid(0);
		parts.setOrderno(0);
		parts.setManufacturername("");
		parts.setMakemodelname("");

		session.saveOrUpdate(parts);
		updateSubcategoryOnParts();
		updateMakemodelOnParts();
		session.flush();
		session.clear();

	}

	@Transactional
	public void deleteStockAuditEntry(String partno) {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "delete from stockaudit where partno =:partno";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("partno", partno);
		query.executeUpdate();
		session.flush();
		session.clear();

	}

	@Transactional
	public List<PartsPartLink> getBVPartsLinkSearchList(String partlinksearchtxt) {

		Session session = sessionFactory.getCurrentSession();
		String SQL = "SELECT p.PartNo AS partno, p.InterchangeNo AS interchangeno, p.KeystoneNumber AS plink , p.manufacturername AS make, "
				+ " p.MakeModelName AS model, p.partdescription AS partdescription, p.unitsinstock AS unitsinstock,p.unitsonorder AS unitsonorder,  "
				+ " p.reorderlevel AS reorderlevel, p.ordertype AS ordertype, p.location AS location,p.yearfrom AS yearfrom,p.yearto AS yearto "
				+ " FROM parts p " + " WHERE  p.keystonenumber LIKE :plink  "
				+ " ORDER BY p.manufacturername, p.MakeModelName,  p.PartNo";
		Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("plink", partlinksearchtxt + "%"))
				.addScalar("partno").addScalar("interchangeno").addScalar("plink").addScalar("make").addScalar("model")
				.addScalar("partdescription").addScalar("yearfrom").addScalar("yearto").addScalar("location")
				.addScalar("reorderlevel").addScalar("unitsinstock").addScalar("unitsonorder").setMaxResults(300000)
				.setResultTransformer(Transformers.aliasToBean(PartsPartLink.class));

		List<PartsPartLink> bvpartslinklist = query.list();

		session.flush();
		session.clear();
		return bvpartslinklist;
	}

	@Transactional
	public List<FutureOrderDetailsPartsHistory> getFutureOrders(String searchpartno) {

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(PartsUtils.FUTUREORDERS_SQL).setParameter("partno",
				searchpartno)).addScalar("partno").addScalar("orderno").addScalar("estimatedarrivaldate")
						.addScalar("quantity").addScalar("companyname").addScalar("price")
						// .setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(FutureOrderDetailsPartsHistory.class));

		List<FutureOrderDetailsPartsHistory> futureorderslist = query.list();

		session.flush();
		session.clear();
		return futureorderslist;
	}

	@Transactional
	public List<InvoiceDetails> getInvoiceDetails(String searchpartno) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM InvoiceDetails invoicedetails  WHERE invoicedetails.partnumber =:partnumber ORDER BY invoicedetails.invoicenumber DESC";
		Query query = session.createQuery(hSql);

		query.setParameter("partnumber", searchpartno);

		List<InvoiceDetails> invoicedetailslist = query.list();

		session.flush();
		session.clear();

		return invoicedetailslist;
	}

	@Transactional
	public List<LocalDetailsPartsHistory> getLocalOrders(String searchpartno) {

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(PartsUtils.LOCALORDERSHISTORY_SQL).setParameter("partno",
				searchpartno)).addScalar("invoiceno").addScalar("dateentered").addScalar("partno").addScalar("price")
						.addScalar("quantity").addScalar("companyname")

						.setResultTransformer(Transformers.aliasToBean(LocalDetailsPartsHistory.class));

		List<LocalDetailsPartsHistory> localorderslist = query.list();

		session.flush();
		session.clear();
		return localorderslist;

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
		if (parts.size() > 0) {
			return parts.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<PartsPartHistory> getPartsDetailforMain(String interchangeno) {

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(PartsUtils.PARTSPARTSHISTORY_INTERCHANGE_SQL)
				.setParameter("partno", interchangeno)).addScalar("partno").addScalar("make").addScalar("model")
						.addScalar("actualprice").addScalar("costprice").addScalar("interchangeno")
						.addScalar("keystonenumber").addScalar("location").addScalar("partdescription")
						.addScalar("reorderlevel").addScalar("unitsinstock").addScalar("unitsonorder")
						.addScalar("yearfrom").addScalar("yearto").setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(PartsPartHistory.class));

		List<PartsPartHistory> partsdetailslist = query.list();

		session.flush();
		session.clear();
		return partsdetailslist;
	}

	@Transactional
	public PartsPartHistory getPartsDetails(String partshistorypartno) {

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(PartsUtils.PARTSPARTSHISTORY_SQL).setParameter("partno",
				partshistorypartno)).addScalar("partno").addScalar("make").addScalar("model").addScalar("actualprice")
						.addScalar("costprice").addScalar("interchangeno").addScalar("keystonenumber")
						.addScalar("location").addScalar("partdescription").addScalar("reorderlevel")
						.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("yearfrom").addScalar("yearto")
						.setMaxResults(300000).setResultTransformer(Transformers.aliasToBean(PartsPartHistory.class));

		List<PartsPartHistory> partsdetailslist = query.list();

		session.flush();
		session.clear();
		if (partsdetailslist.size() > 0) {
			return partsdetailslist.get(0);
		} else {
			return null;
		}
	}

	public List<String> getPartsFromMainOrder(String orders, String receiver) {
		List<String> partsList = new ArrayList<String>();
		String sql = "SELECT distinct partno FROM vendorordereditems  WHERE orderno in (" + orders + " )"
				+ " ORDER BY partno";

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(localDataSource);

			Connection conn = jdbcTemplate.getDataSource().getConnection();
			pstmt1 = conn.prepareStatement(sql);
			rs1 = pstmt1.executeQuery(sql);
			while (rs1.next()) {
				partsList.add(rs1.getString("PartNo"));
			}
			// // LOGGER.info(partsList.size());
			rs1.close();
			pstmt1.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.info(e.toString());
		}

		return partsList;
	}

	@Transactional
	public List<PartsLink> getPartsLinkList(String plink) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM PartsLink partslink  WHERE partslink.plink =:plink ORDER BY partslink.plink";
		Query query = session.createQuery(hSql);

		query.setParameter("plink", plink);

		List<PartsLink> partslinklist = query.list();

		session.flush();
		session.clear();

		return partslinklist;
	}

	@Transactional
	public List<PartsLink> getPartsLinkSearchList(String plink) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM PartsLink partslink  WHERE partslink.plink like :plink ORDER BY partslink.plink";
		Query query = session.createQuery(hSql);

		query.setParameter("plink", plink + "%");

		List<PartsLink> partslinklist = query.list();

		session.flush();
		session.clear();

		return partslinklist;
	}

	@Transactional
	public List<PartsMonthlySales> getPartsMonthlySales(String partnumber) {

		Session session = sessionFactory.getCurrentSession();
		List<PartsMonthlySales> monthlysales = new ArrayList<PartsMonthlySales>();
		String hqlmonthlysalesQuery = "From PartsMonthlySales partsmonthlysales  where partsmonthlysales.partno=:partno ORDER BY  partsmonthlysales.yearmonth DESC";
		Query queryMonthlySales = null;
		queryMonthlySales = session.createQuery(hqlmonthlysalesQuery);
		queryMonthlySales.setParameter("partno", partnumber.trim());
		queryMonthlySales.setMaxResults(24);
		monthlysales = queryMonthlySales.list();
		session.flush();
		session.clear();
		return monthlysales;
	}

	@Transactional
	public Parts getPartsNoNull(String stockcheckpartno) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Parts parts  WHERE parts.partno =:partno";
		Query query = session.createQuery(hSql);

		query.setParameter("partno", stockcheckpartno);

		List<Parts> parts = query.list();

		session.flush();
		session.clear();
		if (parts.size() > 0) {
			return parts.get(0);
		} else {
			return new Parts(stockcheckpartno);
		}
	}

	@Transactional
	public List<ProcessedOrdersPartsHistory> getProcessedOrders(String searchpartno) {

		List<ProcessedOrdersPartsHistory> processedorderslist = new ArrayList<ProcessedOrdersPartsHistory>();

		Session session = sessionFactory.getCurrentSession();
		Query query1 = ((SQLQuery) session.createSQLQuery(PartsUtils.PROCESSEDORDERS1_SQL).setParameter("partno",
				searchpartno)).addScalar("partno").addScalar("orderno").addScalar("inventorydonedate")
						.addScalar("quantity").addScalar("itemdesc1").addScalar("itemdesc2").addScalar("companyname")
						.addScalar("price")
						// .setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(ProcessedOrdersPartsHistory.class));

		if (query1.list().size() > 0) {
			processedorderslist.addAll(query1.list());
		}

		Query query2 = ((SQLQuery) session.createSQLQuery(PartsUtils.PROCESSEDORDERS2_SQL).setParameter("partno",
				searchpartno)).addScalar("partno").addScalar("orderno").addScalar("inventorydonedate")
						.addScalar("quantity").addScalar("itemdesc1").addScalar("itemdesc2").addScalar("companyname")
						.addScalar("price")
						// .setMaxResults(300000)
						.setResultTransformer(Transformers.aliasToBean(ProcessedOrdersPartsHistory.class));
		if (query2.list().size() > 0) {
			processedorderslist.addAll(query2.list());
		}

		session.flush();
		session.clear();
		return processedorderslist;
	}

	public List<SelectedOrderItems> getSelectedOrderItems(List<String> lqs, String orders, String receiver) {
		List<SelectedOrderItems> lso = new ArrayList<SelectedOrderItems>();
		MultiMap multiMap = new MultiValueMap();
		try {
			String[] ord = orders.split(",");

			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rs1 = null;

			JdbcTemplate jdbcTemplate = new JdbcTemplate(localDataSource);

			Connection conn = jdbcTemplate.getDataSource().getConnection();

			// LOGGER.info("___________________________________________________________________________");
			for (String partno : lqs) {
				String str1 = "SELECT price , orderno, quantity FROM vendorordereditems WHERE partno = '" + partno
						+ "' " + "AND orderno in (" + orders + ") " + " AND price > 0 "
						+ "ORDER BY price,orderno desc  LIMIT 1";
				pstmt1 = conn.prepareStatement(str1);
				rs1 = pstmt1.executeQuery(str1);
				while (rs1.next()) {
					SelectedOrderItems sOrderItems = new SelectedOrderItems();
					sOrderItems.setPartno(partno);
					sOrderItems.setSupplierid(rs1.getInt("orderno"));
					sOrderItems.setOrderno(rs1.getInt("orderno"));
					sOrderItems.setPrice(rs1.getFloat("price"));
					sOrderItems.setQuantity(rs1.getInt("quantity"));
					lso.add(sOrderItems);
					multiMap.put(rs1.getInt("orderno"), "'" + partno + "'");

					// get all the set of keys

					// LOGGER.info(partno + ", " + rs1.getString("orderno") + ", " +
					// rs1.getString("price") + ", " + rs1.getString("quantity"));
				}

			}
			for (String key : ord) {
				// LOGGER.info("Key = " + key);
				String deleteSql = "";
				if (null != multiMap.get(Integer.parseInt(key))) {
					String vals = (multiMap.get(Integer.parseInt(key))).toString();
					// LOGGER.info("Values = " + vals.substring(1, vals.length() - 1) + "\n");
					if (vals.substring(1, vals.length() - 1).equalsIgnoreCase("")) {
						deleteSql = "DELETE FROM VENDORORDEREDITEMS WHERE ORDERNO = " + Integer.parseInt(key);
					} else {
						deleteSql = "DELETE FROM VENDORORDEREDITEMS WHERE ORDERNO = " + Integer.parseInt(key)
								+ " AND PARTNO NOT IN (" + vals.substring(1, vals.length() - 1) + ")";
					}
					// LOGGER.info(vals.substring(1, vals.length() - 1));
					pstmt2 = conn.prepareStatement(deleteSql);
					pstmt2.executeUpdate();
				}
			}

			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
			if (conn != null) {
				conn.close();
			}

			String listof5 = "";
			String listof6 = "";
			String listof11 = "";
			String listof14 = "";
			String listof3 = "";
			String listof27 = "";
			String listof2 = "";

			for (SelectedOrderItems sOrderItems : lso) {
				switch (sOrderItems.getSupplierid()) {
				case 5:
					listof5 = listof5 + ", " + sOrderItems.getPartno();
					break;
				case 6:
					listof6 = listof6 + ", " + sOrderItems.getPartno();
					break;
				case 11:
					listof11 = listof11 + ", " + sOrderItems.getPartno();
					break;
				case 14:
					listof14 = listof14 + ", " + sOrderItems.getPartno();
					break;
				case 3:
					listof3 = listof3 + ", " + sOrderItems.getPartno();
					break;
				case 27:
					listof27 = listof27 + ", " + sOrderItems.getPartno();
					break;
				case 2:
					listof2 = listof2 + ", " + sOrderItems.getPartno();
					break;
				}
			}

			// LOGGER.info("end processing");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lso;
	}

	@Transactional
	public List<StockCheckDetails> getStockDetails(String partno) {

		try {
			Session session = sessionFactory.getCurrentSession();
			String SQL = "SELECT V.CompanyName, vi.vendorpartno, v.comments, vi.sellingrate FROM vendoritems vi, vendors v"
					+ " WHERE vi.SupplierID = v.SupplierID" + " AND vi.PartNo =:partno"
					+ " AND vi.vendorpartno <> '' AND vi.SupplierID <> 24" + " ORDER BY Vi.sellingrate";
			Query query = ((SQLQuery) session.createSQLQuery(SQL).setParameter("partno", partno))
					.addScalar("companyname").addScalar("vendorpartno").addScalar("comments").addScalar("sellingrate")
					.setMaxResults(300000).setResultTransformer(Transformers.aliasToBean(StockCheckDetails.class));

			List<StockCheckDetails> stockdetailslist = query.list();

			session.flush();
			session.clear();

			if (stockdetailslist.size() <= 0) {
				List<StockCheckDetails> stockdetailslistblank = new ArrayList<StockCheckDetails>();
				StockCheckDetails stockdetails = new StockCheckDetails();

				stockdetails.setCompanyname("");
				stockdetails.setVendorpartno("");
				stockdetailslistblank.add(stockdetails);

				return stockdetailslistblank;
			} else {
				return stockdetailslist;
			}
		} catch (HibernateException e) {
			LOGGER.error("HibernateException: getStockDetails##" + e.getMessage().toString());
			List<StockCheckDetails> stockdetailslist = new ArrayList<StockCheckDetails>();
			StockCheckDetails stockdetails = new StockCheckDetails();

			stockdetails.setCompanyname("");
			stockdetails.setVendorpartno("");
			stockdetailslist.add(stockdetails);

			return stockdetailslist;
		}
	}

	public void makeFile(String orderNo, String receiver) {

		try {
			File returns = new File("C:\\repository\\insight\\ordercompare\\" + orderNo + ".txt");
			FileWriter wrt = new FileWriter(returns);

			JdbcTemplate jdbcTemplate = new JdbcTemplate(localDataSource);

			Connection conn = jdbcTemplate.getDataSource().getConnection();

			Statement stmt = null;
			PreparedStatement pstmt1 = null;
			Statement pstmt2 = null;
			Statement stmt3 = null;
			ResultSet rs3 = null;
			Statement stmt4 = null;
			ResultSet rs4 = null;
			ResultSet rs0 = null;
			ResultSet rs1 = null;
			Statement stmtXX = null;
			ResultSet rsXX = null;
			ResultSet rs2 = null;
			Statement stmtA = null;
			ResultSet rsA = null;
			Statement stmtXXX = null;
			Statement stmt5 = null;
			Statement stmt6 = null;
			ResultSet rs6 = null;
			Statement stmt52 = null;
			ResultSet rs52 = null;
			Statement stmt51 = null;
			ResultSet rs51 = null;
			ResultSet rs5 = null;
			ResultSet rsXXX = null;

			stmt = conn.createStatement();
			pstmt1 = conn.prepareStatement(
					"Select a.PartNo, a.VendorPartNo, a.Price, a.Quantity, b.CostPrice, b.ActualPrice, b.UnitsInStock, b.ReorderLevel, b.InterchangeNo, b.Year, b.PartDescription, c.MakeModelName from VendorOrderedItems a, Parts b, MakeModel c where a.OrderNo="
							+ orderNo
							+ " and a.partno=b.partno and b.MakeModelCode=c.MakeModelCode Order By a.PartNo ");
			pstmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			rs3 = stmt3.executeQuery(
					"Select SupplierId, SUBSTRING(CompanyName, 1,3) from Vendors where CompanyName <> '' ");
			Hashtable<String, String> vendorTable = new Hashtable<String, String>();

			while (rs3.next()) {
				// LOGGER.info(rs3.getString(1));
				vendorTable.put(rs3.getString(1), rs3.getString(2));
			}
			stmt4 = conn.createStatement();
			rs4 = stmt4.executeQuery("Select SupplierId, CompanyName from LocalVendors ");
			Hashtable<String, String> localVendorTable = new Hashtable<String, String>();

			while (rs4.next()) {
				localVendorTable.put(rs4.getString(1), rs4.getString(2));
			}
			int supId = 0;
			rs0 = stmt.executeQuery("SELECT SupplierId FROM VendorOrder WHERE OrderNo=" + orderNo);
			if (rs0.next()) {
				supId = rs0.getInt("SupplierId");
			} else {
				// LOGGER.info("No SupplierId");
				System.exit(1);
			}
			pstmt1.clearParameters();
			rs1 = pstmt1.executeQuery();
			wrt.write("PRICE COMPARISON FOR THE ORDER : " + orderNo);
			wrt.write("\r\n");
			wrt.write("\r\n");
			while (rs1.next()) {
				// LOGGER.info(rs1.getString("PartNo"));
				String partNo = rs1.getString("PartNo");

				String vendPartNo = rs1.getString("VendorPartNo");
				String vendDesc = "";
				double price = rs1.getDouble("Price");
				int qty = rs1.getInt("Quantity");
				double costPrice = rs1.getDouble("CostPrice");
				double actualPrice = rs1.getDouble("ActualPrice");
				int units = rs1.getInt("UnitsInStock");
				int reorder = rs1.getInt("ReorderLevel");
				String descrip = rs1.getString("Year") + "  " + rs1.getString("PartDescription") + "  "
						+ rs1.getString("MakeModelName");
				String remarkStr = "";
				String interNo = rs1.getString("InterchangeNo");
				if (!interNo.trim().equals("")) {
					stmtXX = conn.createStatement();
					rsXX = stmtXX.executeQuery("Select UnitsInStock from parts where partno='" + interNo + "'");
					if (rsXX.next()) {
						units = rsXX.getInt(1);
					}
				}
				String p1 = "";
				String p2 = "";
				stmtA = conn.createStatement();
				String sqlA = "Select PartNo From Parts Where InterchangeNo='";
				if (interNo.trim().equals("")) {
					sqlA += partNo + "'";
				} else {
					sqlA += interNo + "' and partNo!='" + partNo + "'";
				}
				rsA = stmtA.executeQuery(sqlA);
				while (rsA.next()) {
					String xxx = rsA.getString(1);
					if (p1.trim().equals("")) {
						p1 = xxx;
					} else if (p2.trim().equals("")) {
						p2 = xxx;
					} else {
						break;
					}
				}
				if (vendPartNo != null && !vendPartNo.trim().equals("")) {
					String it1 = "";
					String it2 = "";
					stmtXXX = conn.createStatement();
					rsXXX = stmtXXX.executeQuery(
							"Select ItemDesc1, ItemDesc2 From VendorItems Where VendorPartNo = '" + vendPartNo + "' ");
					if (partNo.equalsIgnoreCase("T7107")) {
						// LOGGER.info("Select ItemDesc1, ItemDesc2 From VendorItems Where VendorPartNo
						// like '"+ vendPartNo + "%' ");
					}
					if (rsXXX.next()) {
						vendDesc = "";
						it1 = rsXXX.getString("ItemDesc1");
						it2 = rsXXX.getString("ItemDesc2");
						if (it1 != null && !it1.trim().equals("")) {
							vendDesc = it1.trim();
						}
						if (it2 != null && !it2.trim().equals("")) {
							vendDesc += it2.trim();
						}
					}
				}
				String printStr1X = "";
				if (costPrice != 0 && price != 0) {
					if ((((costPrice - price) / costPrice) * 100) > 40) {
						printStr1X += "COST :  GOOD";
					} else {
						printStr1X += "COST : CHECK";
					}
					if (printStr1X.trim().equals("")) {
						printStr1X += "            ";
					}
				}

				double lowestPrice = price;
				double price1 = 0;
				double price2 = 0;
				double price3 = 0;
				double price4 = 0;
				double price5 = 0;
				int supId1 = 0;
				int supId2 = 0;
				int supId3 = 0;
				int supId4 = 0;
				int supId5 = 0;
				String supName1 = "";
				String supName2 = "";
				String supName3 = "";
				String supName4 = "";
				String supName5 = "";

				String sqle = "Select SupplierId, SellingRate from VendorItems Where (PartNo like '%" + partNo.trim()
						+ "%'";
				if (!interNo.trim().equals("")) {
					sqle += " or PartNo like '%" + interNo + "%'";
				}
				if (!p1.trim().equals("")) {
					sqle += " or PartNo like '%" + p1 + "%'";
				}
				if (!p2.trim().equals("")) {
					sqle += " or PartNo like '%" + p2 + "%'";
				}
				sqle += ") and SupplierId != " + supId + " and SellingRate != 0 Order By SellingRate";
				rs2 = pstmt2.executeQuery(sqle);

				if (rs2.next()) {
					supId1 = rs2.getInt("SupplierId");
					price1 = rs2.getDouble("SellingRate");
				}
				if (rs2.next()) {
					supId2 = rs2.getInt("SupplierId");
					price2 = rs2.getDouble("SellingRate");
				}
				if (rs2.next()) {
					supId3 = rs2.getInt("SupplierId");
					price3 = rs2.getDouble("SellingRate");
				}
				if (rs2.next()) {
					supId4 = rs2.getInt("SupplierId");
					price4 = rs2.getDouble("SellingRate");
				}
				if (rs2.next()) {
					supId5 = rs2.getInt("SupplierId");
					price5 = rs2.getDouble("SellingRate");
				}

				String remarks = "";

				if (price5 != 0) {
					if (price >= price5) {
						remarks = "Hi";
					}
				} else if (price4 != 0) {
					if (price >= price4) {
						remarks = "Hi";
					}
				} else if (price3 != 0) {
					if (price >= price3) {
						remarks = "Hi";
					}
				} else if (price2 != 0) {
					if (price >= price2) {
						remarks = "Hi";
					}
				} else if (price1 != 0) {
					if (price >= price1) {
						remarks = "Hi";
					}
				}
				if (price1 != 0) {
					if (price <= price1) {
						remarks = "Lo";
					}

					// For Checking The Lowest Price
					if (price1 < lowestPrice) {
						lowestPrice = price1;
					}
				}
				if (remarks.trim().equals("")) {
					remarks = "Mid";
				}

				if (supId1 != 0) {
					supName1 = vendorTable.get(supId1 + "");
				}
				if (supId2 != 0) {
					supName2 = vendorTable.get(supId2 + "");
				}
				if (supId3 != 0) {
					supName3 = vendorTable.get(supId3 + "");
				}
				if (supId4 != 0) {
					supName4 = vendorTable.get(supId4 + "");
				}
				if (supId5 != 0) {
					supName5 = vendorTable.get(supId5 + "");
				}

				if (supId1 == 1 || supId2 == 1 || supId3 == 1 || supId4 == 1 || supId5 == 1) {
					remarkStr += "-EE-";
				}
				if (supId1 == 13 || supId2 == 13 || supId3 == 13 || supId4 == 13 || supId5 == 13) {
					remarkStr += "-AC-";
				}

				String printStr1 = "";
				String printStr2 = "";
				String printStr3 = "";
				String printStr31 = "";
				// String printStr32 = "";
				String printStr32X = "";
				String printStr32Y = "";
				String printStr32Z = "";
				String printStr4 = "";
				String printStrXX1 = "BV :: " + descrip;
				String printStrXX2 = "VD :: " + vendDesc.trim();

				String sqlxx = "Select b.SupplierId, b.OrderDate, a.OrderNo, a.Price, a.Quantity from VendorOrderedItems a, VendorOrder b Where (a.PartNo like '%"
						+ partNo.trim() + "%'";
				if (!interNo.trim().equals("")) {
					sqlxx += " or a.PartNo like '%" + interNo + "%'";
				}
				if (!p1.trim().equals("")) {
					sqlxx += " or a.PartNo like '%" + p1 + "%'";
				}
				if (!p2.trim().equals("")) {
					sqlxx += " or a.PartNo like '%" + p2 + "%'";
				}
				sqlxx += ") and a.OrderNo != " + orderNo
						+ " and a.Price != 0 and a.OrderNo=b.OrderNo and b.IsFinal='Y' and b.UpdatedInventory='N' Order By a.OrderNo ";
				stmt5 = conn.createStatement();
				rs5 = stmt5.executeQuery(sqlxx);

				int sum = 0;
				while (rs5.next()) {
					String xx = InsightUtils.convertMySQLToUSFormat(rs5.getString("OrderDate"));
					String mm = "";
					String yy = "";
					try {
						mm = xx.substring(0, 2);
						yy = xx.substring(8);
					} catch (Exception e) {
						// LOGGER.info(e.toString());
					}
					double prce = rs5.getDouble("Price");
					if (prce < lowestPrice) {
						lowestPrice = prce;
					}
					String ordNo = rs5.getString("OrderNo");
					int suppId = rs5.getInt("SupplierId");
					if (suppId == 1 && remarkStr.indexOf("-EE-") == -1) {
						remarkStr += "-EE-";
					}
					if (suppId == 13 && remarkStr.indexOf("-AC-") == -1) {
						remarkStr += "-AC-";
					}
					String supNm = vendorTable.get(suppId + "").substring(0, 3);
					int qt = rs5.getInt("Quantity");
					sum += qt;

				}
				printStr1 = partNo + ":" + price + " (" + remarks + ")" + " QTY:" + qty + " CST:" + costPrice + " ACT:"
						+ actualPrice + " ST:" + units + " RE:" + reorder + " Unit on Order :" + sum;

				// printStr1 = partNo + " : " + price + " (" + remarks + ")";
				if (price1 != 0) {
					printStr2 = "Prices:   " + price1 + " (" + supName1 + ") ";
				}
				if (price2 != 0) {

					printStr2 += "\t" + price2 + " (" + supName2 + ") ";
				}
				if (price3 != 0) {
					printStr2 += "\t" + price3 + " (" + supName3 + ") ";
				}
				if (price4 != 0) {
					printStr2 += "\t" + price4 + " (" + supName4 + ") ";
				}
				if (price5 != 0) {
					printStr2 += "\t" + price5 + " (" + supName5 + ") ";
				}

				String sqlx = "Select b.SupplierId, b.OrderDate, a.OrderNo, a.Price, a.Quantity from VendorOrderedItems a, VendorOrder b Where (a.PartNo like '%"
						+ partNo.trim() + "%'";
				if (!interNo.trim().equals("")) {
					sqlx += " or a.PartNo like '%" + interNo + "%'";
				}
				if (!p1.trim().equals("")) {
					sqlx += " or a.PartNo like '%" + p1 + "%'";
				}
				if (!p2.trim().equals("")) {
					sqlx += " or a.PartNo like '%" + p2 + "%'";
				}
				sqlx += ") and a.OrderNo != " + orderNo
						+ " and a.Price != 0 and a.OrderNo=b.OrderNo and b.IsFinal='Y' and b.UpdatedInventory='N' Order By a.OrderNo ";
				stmt5 = conn.createStatement();
				rs5 = stmt5.executeQuery(sqlx);
				printStr3 = "Committed :";
				while (rs5.next()) {
					String xx = InsightUtils.convertMySQLToUSFormat(rs5.getString("OrderDate"));
					String mm = "";
					String yy = "";
					try {
						mm = xx.substring(0, 2);
						yy = xx.substring(8);
					} catch (Exception e) {
						// LOGGER.info(e.toString());
					}
					double prce = rs5.getDouble("Price");
					if (prce < lowestPrice) {
						lowestPrice = prce;
					}
					String ordNo = rs5.getString("OrderNo");
					int suppId = rs5.getInt("SupplierId");
					if (suppId == 1 && remarkStr.indexOf("-EE-") == -1) {
						remarkStr += "-EE-";
					}
					if (suppId == 13 && remarkStr.indexOf("-AC-") == -1) {
						remarkStr += "-AC-";
					}
					String supNm = vendorTable.get(suppId + "").substring(0, 3);
					int qt = rs5.getInt("Quantity");
					printStr3 += prce + " (" + qt + "-" + supNm + "(" + ordNo + ")" + "-" + mm + "/" + yy + ") ";
				}

				// FOR OLD ORDERS
				// VenorOrder of last year
				Calendar currentDate = Calendar.getInstance();
				Date dateNow = currentDate.getTime();
				currentDate.add(Calendar.MONTH, -12);
				Date lastYear = currentDate.getTime();

				java.sql.Date sqlDateNow = new java.sql.Date(dateNow.getTime());
				java.sql.Date sqlDateLastYear = new java.sql.Date(lastYear.getTime());

				String sqlx1 = "Select b.SupplierId, b.OrderDate, a.OrderNo, a.Price from VendorOrderedItems a, VendorOrder b Where (b.OrderDate <= '"
						+ sqlDateNow + "' and b.OrderDate >= '" + sqlDateLastYear
						+ "') and a.OrderNo > 3000 and (a.PartNo like '%" + partNo.trim() + "%'";
				if (!interNo.trim().equals("")) {
					sqlx1 += " or a.PartNo like '%" + interNo + "%'";
				}
				if (!p1.trim().equals("")) {
					sqlx1 += " or a.PartNo like '%" + p1 + "%'";
				}
				if (!p2.trim().equals("")) {
					sqlx1 += " or a.PartNo like '%" + p2 + "%'";
				}
				sqlx1 += ") and a.OrderNo != " + orderNo
						+ " and a.Price != 0 and a.OrderNo=b.OrderNo and b.IsFinal='Y' and b.UpdatedInventory='Y' Order By a.OrderNo ";
				stmt51 = conn.createStatement();
				rs51 = stmt51.executeQuery(sqlx1);
				printStr31 = "Old :";
				while (rs51.next()) {
					String xx1 = InsightUtils.convertMySQLToUSFormat(rs51.getString("OrderDate"));
					String mm1 = "";
					String yy1 = "";
					try {
						mm1 = xx1.substring(0, 2);
						yy1 = xx1.substring(8);
					} catch (Exception e) {
						// LOGGER.info(e.toString());
					}
					double prce = rs51.getDouble("Price");
					if (prce < lowestPrice) {
						lowestPrice = prce;
					}
					int suppId = rs51.getInt("SupplierId");
					if (suppId == 1 && remarkStr.indexOf("-EE-") == -1) {
						remarkStr += "-EE-";
					}
					if (suppId == 13 && remarkStr.indexOf("-AC-") == -1) {
						remarkStr += "-AC-";
					}
					printStr31 += prce + " (" + rs51.getString("OrderNo") + "- "
							+ vendorTable.get(suppId + "").substring(0, 3) + "-" + mm1 + "/" + yy1 + ") ";
				}

				String sqlx2 = "Select b.SupplierId, b.OrderDate, a.OrderNo, a.Price from VendorOrderedItems a, VendorOrder b Where (b.OrderDate <= '"
						+ sqlDateNow + "' and b.OrderDate >= '" + sqlDateLastYear + "') and (a.PartNo like '%"
						+ partNo.trim() + "%'";
				if (!interNo.trim().equals("")) {
					sqlx2 += " or a.PartNo like '%" + interNo + "%'";
				}
				if (!p1.trim().equals("")) {
					sqlx2 += " or a.PartNo like '%" + p1 + "%'";
				}
				if (!p2.trim().equals("")) {
					sqlx2 += " or a.PartNo like '%" + p2 + "%'";
				}
				sqlx2 += ") and a.OrderNo != " + orderNo
						+ " and a.Price != 0 and a.OrderNo=b.OrderNo and b.IsFinal='N' Order By a.OrderNo ";
				stmt52 = conn.createStatement();
				rs52 = stmt52.executeQuery(sqlx2);
				// printStr32 = "New Orders :";
				printStr32X = "Back :";
				printStr32Y = "Remo :";
				printStr32Z = "Orde :";
				while (rs52.next()) {
					String xx2 = InsightUtils.convertMySQLToUSFormat(rs52.getString("OrderDate"));
					String mm2 = "";
					String yy2 = "";
					try {
						mm2 = xx2.substring(0, 2);
						yy2 = xx2.substring(8);
					} catch (Exception e) {
						// LOGGER.info(e.toString());
					}
					double prce = rs52.getDouble("Price");
					if (prce < lowestPrice) {
						lowestPrice = prce;
					}
					int suppId = rs52.getInt("SupplierId");
					if (suppId == 1 && remarkStr.indexOf("-EE-") == -1) {
						remarkStr += "-EE-";
					}
					if (suppId == 13 && remarkStr.indexOf("-AC-") == -1) {
						remarkStr += "-AC-";
					}
					int ordNo = Integer.parseInt(rs52.getString("OrderNo"));

					// FOR REMOVING GR ORDERS & OTHERS
					// if ((ordNo >= 526 && ordNo <= 600) || (ordNo >= 651 &&
					// ordNo <= 750)) {
					// continue;
					// FOR BACK ORDERS
					// } else if (ordNo >= 601 && ordNo <= 650) {
					if ((ordNo >= 4001 && ordNo <= 4400) || (ordNo >= 4401 && ordNo <= 4800)) {
						printStr32X += prce + " (" + ordNo + "-" + vendorTable.get(suppId + "").substring(0, 3) + "-"
								+ mm2 + "/" + yy2 + ") ";
						// REMOVED FOR HIGH PRICES
					} else if ((ordNo >= 3361 && ordNo <= 3800) || (ordNo >= 8251 && ordNo <= 9000)) {
						printStr32Y += prce + " (" + ordNo + "-" + vendorTable.get(suppId + "").substring(0, 3) + "-"
								+ mm2 + "/" + yy2 + ") ";
					} else {
						printStr32Z += prce + " (" + ordNo + "-" + vendorTable.get(suppId + "").substring(0, 3) + "-"
								+ mm2 + "/" + yy2 + ") ";
					}
				}

				String sqly = "Select SupplierId, DateEntered, Price from LocalOrders Where (DateEntered <= '"
						+ sqlDateNow + "' and DateEntered >= '" + sqlDateLastYear
						+ "') and supplierid!=38 and supplierid!=39 and (PartNo like '%" + partNo.trim() + "%'";
				if (!interNo.trim().equals("")) {
					sqly += " or PartNo like '%" + interNo + "%'";
				}
				if (!p1.trim().equals("")) {
					sqly += " or PartNo like '%" + p1 + "%'";
				}
				if (!p2.trim().equals("")) {
					sqly += " or PartNo like '%" + p2 + "%'";
				}
				sqly += ") and Price != 0 ";
				stmt6 = conn.createStatement();
				rs6 = stmt6.executeQuery(sqly);
				printStr4 = "Local :";
				while (rs6.next()) {
					String xx = InsightUtils.convertMySQLToUSFormat(rs6.getString("DateEntered"));
					String mm = "";
					String yy = "";
					try {
						mm = xx.substring(0, 2);
						yy = xx.substring(8);
					} catch (Exception e) {
						// LOGGER.info(e.toString());
					}
					int supIdd = 0;
					supIdd = rs6.getInt("SupplierId");
					if (supIdd == 4) {
						remarkStr += "-MX-";
					}
					if (supIdd == 14) {
						remarkStr += "-AC-";
					}
					String supStr = "";
					if (supIdd != 0) {
						supStr = localVendorTable.get(supIdd + "").substring(0, 3);
					}
					double prce = rs6.getDouble("Price");
					if (prce < lowestPrice) {
						lowestPrice = prce;
					}
					printStr4 += prce + " (" + supStr + "-" + mm + "/" + yy + ") ";
				}

				if (lowestPrice == price) {
					printStr1X += "      PRICE : LOWEST";
				} else if ((price - lowestPrice) < 0.50) {
					printStr1X += "      PRICE : OK";
				} else {
					printStr1X += "      PRICE : " + lowestPrice;
				}

				if (costPrice != 0) {
					double perc = ((costPrice - price) / costPrice) * 100;
					if (perc > 0) {
						perc = Double.parseDouble(df2.format(perc));
					}
					if (price < 2 && perc < 70) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 2 && price < 5 && perc < 60) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 5 && price < 8 && perc < 55) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 8 && price < 12 && perc < 50) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 12 && price < 22 && perc < 45) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 22 && price < 42 && perc < 40) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 42 && price < 72 && perc < 35) {
						printStr1X += "          LOW COST : " + perc;
					} else if (price >= 72 && perc < 30) {
						printStr1X += "          LOW COST : " + perc;
					}
				}

				if (!remarkStr.trim().equals("")) {
					printStr1X += remarkStr;
				}

				if (!printStr1.trim().equals("")) {
					wrt.write("________________________________________________________");
					wrt.write("\r\n");
					wrt.write(printStr1);
					wrt.write("\r\n");
					wrt.write(printStr1X);
					wrt.write("\r\n");
					if (!printStr2.trim().equals("")) {
						wrt.write(printStr2);
						wrt.write("\r\n");
					}
					if (printStr3.trim().length() > 11) {
						wrt.write(printStr3);
						wrt.write("\r\n");
					}
					if (printStr31.trim().length() > 5) {
						wrt.write(printStr31);
						wrt.write("\r\n");
					}
					if (printStr32X.trim().length() > 6) {
						wrt.write(printStr32X);
						wrt.write("\r\n");
					}
					if (printStr32Y.trim().length() > 6) {
						wrt.write(printStr32Y);
						wrt.write("\r\n");
					}
					if (printStr32Z.trim().length() > 6) {
						wrt.write(printStr32Z);
						wrt.write("\r\n");
					}
					if (printStr4.trim().length() > 7) {
						wrt.write(printStr4);
						wrt.write("\r\n");
					}

					wrt.write(printStrXX1);
					wrt.write("\r\n");

					if (printStrXX2.trim().length() > 6) {
						wrt.write(printStrXX2);
						wrt.write("\r\n");
					}
					wrt.write("\r\n");

					printStr1 = "";
					printStr1X = "";
					printStr2 = "";
					printStr3 = "";
					printStr31 = "";
					printStr32X = "";
					printStr32Y = "";
					printStr32Z = "";
					printStr4 = "";
					printStrXX1 = "";
					printStrXX2 = "";

					// wrt1.write(printStr1);
					// wrt1.write("\n");
					// wrt1.write(printStr1X);
					// wrt1.write("\n\n");
				}

			} // rs1

			wrt.write("_____________END OF COMPARE_________________");

			if (rs4 != null) {
				rs4.close();
			}
			if (rs0 != null) {
				rs0.close();
			}

			if (rs2 != null) {
				rs2.close();
			}
			if (stmtA != null) {
				stmtA.close();
			}
			if (rs4 != null) {
				rs4.close();
			}
			if (rsA != null) {
				rsA.close();
			}
			if (stmt5 != null) {
				stmt5.close();
			}
			if (stmt6 != null) {
				stmt6.close();
			}
			if (rs6 != null) {
				rs6.close();
			}
			if (stmt52 != null) {
				stmt52.close();
			}
			if (stmtXXX != null) {
				stmtXXX.close();
			}

			if (rs52 != null) {
				rs52.close();
			}
			if (stmt51 != null) {
				stmt51.close();
			}
			if (rs51 != null) {
				rs51.close();
			}
			if (rs5 != null) {
				rs5.close();
			}
			if (rsXXX != null) {
				rsXXX.close();
			}

			if (stmt != null) {
				stmt.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
			if (stmt3 != null) {
				stmt3.close();
			}
			if (rs3 != null) {
				rs3.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			if (stmt4 != null) {
				stmt4.close();
			}

			conn.close();
			wrt.close();

			// LOGGER.info("_____________END OF COMPARE_________________");

		} catch (IOException e) {
			LOGGER.info("IOException:" + e.toString());
		} catch (SQLException e) {
			LOGGER.info("SQLException:" + e.toString());
		}

	}

	@Transactional
	public String processNextPartNumber(Integer manufacturerid, String subcategoryselected) {

		Session session = sessionFactory.getCurrentSession();
		String mfcodeSql = "FROM Manufacturer manufacturer  WHERE manufacturer.manufacturerid =:manufacturerid";
		String maxSql = "select max(series)from PartMaker partmaker where partmaker.index=:index";
		String seq = "";
		String nextpartnumber = "";
		String manufacturershortcode = "";
		String indexsearch = "";

		if (!(subcategoryselected.isEmpty())) {
			if (subcategoryselected.length() == 1) {
				subcategoryselected = "0" + subcategoryselected;
			}
			Query querymfcode = session.createQuery(mfcodeSql);
			querymfcode.setParameter("manufacturerid", manufacturerid);

			List<Manufacturer> makelist = querymfcode.list();
			manufacturershortcode = makelist.get(0).getManufacturershortcode();
			indexsearch = manufacturershortcode + subcategoryselected;

			Query querymax = session.createQuery(maxSql);
			querymax.setParameter("index", indexsearch);
			@SuppressWarnings("rawtypes")
			List maxlist = querymax.list();
			if (maxlist.get(0) == null) {
				seq = "0001";
			} else {
				seq = pump(maxlist.get(0).toString());
			}
		}

		nextpartnumber = manufacturershortcode + subcategoryselected + seq;
		session.flush();
		session.clear();
		return nextpartnumber;
	}

	public String pump(String seq) {

		if (seq.length() == 4) {
			return seq;
		} else {
			seq = "0" + seq;
			seq = pump(seq);
		}
		return seq;

	}

	@Transactional
	public void saveLocation(String stockcheckpartno, String locationpartno) {
		Session session = sessionFactory.getCurrentSession();
		String updateSql = "Update Parts Set location=:locationpartno Where  partno=:stockcheckpartno ;";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("locationpartno", locationpartno);
		query.setParameter("stockcheckpartno", stockcheckpartno);
		query.executeUpdate();
		session.flush();
		session.clear();

	}

	public void sendMail(String[] orders, String receiver) throws MessagingException {

		final String username = "ordercompare@gmail.com";
		final String password = "bestvaluefiles";

		Properties props = new Properties();
		/*
		 * props.put("mail.smtp.host", "smtp.gmail.com");
		 * props.put("mail.smtp.socketFactory.port", "465");
		 * props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 * props.put("mail.smtp.auth", "true"); props.put("mail.smtp.port", "465");
		 */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // TLS

		javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ordercompare@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject("Compare Price Files");

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			for (String orderNo : orders) {

				String file = "C:\\repository\\insight\\ordercompare\\" + orderNo + ".txt";
				String fileName = orderNo;
				messageBodyPart = new MimeBodyPart();
				FileDataSource source = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);

			}

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Transactional
	public void updateInterchangeStock(Integer quantity, String partno) {

		Session session = sessionFactory.getCurrentSession();
		String updateUnitsInStockSQL = "update parts set unitsinstock = :quantity  where interchangeno = :partno";

		Query query = session.createSQLQuery(updateUnitsInStockSQL);
		query.setParameter("quantity", quantity);
		query.setParameter("partno", partno);
		query.executeUpdate();

		session.flush();
		session.clear();
	}

	@Transactional
	public void updateMakemodelOnParts() {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "UPDATE parts p INNER JOIN makemodel m " + "ON p.MakeModelCode = m.MakeModelCode "
				+ "SET p.manufacturername = upper(m.manufacturername), p.makemodelname = upper(m.makemodelname) "
				+ "WHERE  p.MakeModelCode = m.MakeModelCode;";
		Query query = session.createSQLQuery(updateSql);
		query.executeUpdate();
		session.flush();
		session.clear();

	}

	@Transactional
	public List<String> updatePartCostAndDiscountPrice(MultipartFile multipartFile, OutputStream outStream) {

		List<String> messages = new ArrayList<String>();
		// File file = new File(filePath + multipartFile.getOriginalFilename());
		try {
			Workbook wb;
			Workbook wbReturn;
			if (multipartFile.getContentType().equals("application/vnd.ms-excel")) {
				wb = new HSSFWorkbook(multipartFile.getInputStream());
				wbReturn = new HSSFWorkbook();
			} else if (multipartFile.getContentType()
					.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				wb = new XSSFWorkbook(multipartFile.getInputStream());
				wbReturn = new XSSFWorkbook();
			} else {
				messages.add("content type of uploaded file is not supported.");
				return messages;
			}
			Sheet sheet = wb.getSheetAt(0);
			Sheet sheetReturn = wbReturn.createSheet("Parts Not Updated");
			Row row;
			// row = sheet.getRow(0);
			int distinationRowNum = 0;
			copyRow(wbReturn, sheetReturn, sheet, 0, distinationRowNum, "Remarks");

			Cell partNoCell, costPriceCell, discountCell;// SellingRate
			// ->
			// price
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			List<UpdatePartsCostPrice> updateList = new ArrayList<UpdatePartsCostPrice>();
			DataFormatter formatter = new DataFormatter();
			for (int r = 1; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null) {
					partNoCell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
					costPriceCell = row.getCell(1, Row.RETURN_BLANK_AS_NULL);
					discountCell = row.getCell(2, Row.RETURN_BLANK_AS_NULL);

					UpdatePartsCostPrice updatePart = new UpdatePartsCostPrice();
					if (partNoCell == null || formatter.formatCellValue(partNoCell) == null) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "PartNo is empty");
						continue;
					} else {
						updatePart.setPartNo(formatter.formatCellValue(partNoCell).trim());
					}
					if (costPriceCell == null || formatter.formatCellValue(costPriceCell) == null) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "Cost price is empty");
						continue;
					} else {

						try {
							if (costPriceCell.getCellType() == Cell.CELL_TYPE_FORMULA
									&& costPriceCell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC) {
								updatePart.setCostPrice(BigDecimal.valueOf(costPriceCell.getNumericCellValue()));
							} else if (costPriceCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								updatePart.setCostPrice(BigDecimal.valueOf(costPriceCell.getNumericCellValue()));
							} else {
								updatePart
										.setCostPrice(new BigDecimal(formatter.formatCellValue(costPriceCell).trim()));
							}
						} catch (NumberFormatException ex) {
							distinationRowNum++;
							copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "Cost Price is invalid");
							continue;
						}
					}
					if (discountCell == null || formatter.formatCellValue(discountCell) == null) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "Discount is empty");
						continue;
					} else {
						try {
							updatePart.setDiscount(new BigDecimal(formatter.formatCellValue(discountCell).trim()));
						} catch (NumberFormatException ex) {
							distinationRowNum++;
							copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "Discount is invalid");
							continue;
						}

					}
					updatePart.setExcelRowNum(r);
					updateList.add(updatePart);
				}
			}

			Session session = sessionFactory.getCurrentSession();
			int updateCount = 0;
			for (UpdatePartsCostPrice updatePart : updateList) {
				String hSql = "FROM Parts p  WHERE p.partno =:partno";
				Query query = session.createQuery(hSql);
				query.setParameter("partno", updatePart.getPartNo());

				List<Parts> partsList = query.list();
				if (partsList.size() > 0 && partsList.get(0) != null) {
					Parts partSelected = partsList.get(0);

					double actPrice = partSelected.getActualprice().doubleValue();
					double newCost = updatePart.getCostPrice().doubleValue();
					double origCost = partSelected.getCostprice().doubleValue();
					BigDecimal resultCostPrice = BigDecimal.ZERO;
					// LOGGER.info("act="+actPrice+",newCost="+newCost+",origCost="+origCost);
					if (actPrice == 0) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, updatePart.getExcelRowNum(), distinationRowNum,
								"Actual Price of the part is 0");
						continue;
					} else {
						if (newCost < origCost) { // && !user.getRole().equalsIgnoreCase("High")
							double calCost = 0.0;
							if (actPrice < 5) {
								calCost = actPrice / 0.40;
							} else if (actPrice < 10) {
								calCost = actPrice / 0.45;
							} else if (actPrice < 15) {
								calCost = actPrice / 0.50;
							} else if (actPrice < 20) {
								calCost = actPrice / 0.55;
							} else if (actPrice < 50) {
								calCost = actPrice / 0.60;
							} else if (actPrice < 75) {
								calCost = actPrice / 0.65;
							} else if (actPrice < 125) {
								calCost = actPrice / 0.70;
							} else if (actPrice < 200) {
								calCost = actPrice / 0.75;
							} else {
								calCost = actPrice / 0.80;
							}
							// LOGGER.info("calcCost="+calCost);
							if (newCost < calCost) {
								distinationRowNum++;
								copyRow(wbReturn, sheetReturn, sheet, updatePart.getExcelRowNum(), distinationRowNum,
										"THE COST PRICE IS TOO LOW");
								continue;
							} else {
								resultCostPrice = updatePart.getCostPrice();
							}
						} else {
							resultCostPrice = updatePart.getCostPrice();
						}
					}

					// LOGGER.info("resultCost="+resultCostPrice.doubleValue());
					double discount = updatePart.getDiscount().doubleValue();
					BigDecimal listPr = BigDecimal.ZERO;
					boolean listPriceChanged = false;
					if (discount > 0) {
						// (1 - discount / 100)
						BigDecimal percn = BigDecimal.ONE;
						// LOGGER.info("lst (1)="+percn.doubleValue());
						// percn=percn.subtract(updatePart.getDiscount());
						percn = percn.subtract(
								updatePart.getDiscount().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
						// LOGGER.info("lst (1-discount/100)="+percn.doubleValue());
						listPr = resultCostPrice.divide(percn, 2, RoundingMode.HALF_UP);
						// LOGGER.info("lst ()="+listPr);
						listPriceChanged = true;
					}
					// LOGGER.info("resultCost2="+resultCostPrice.doubleValue());
					partSelected.setCostprice(resultCostPrice);
					if (listPriceChanged) {
						partSelected.setListprice(listPr);
					}
					// LOGGER.info("part="+partSelected.getPartno()+",cost="+partSelected.getCostprice().doubleValue()+",lst="+partSelected.getListprice().doubleValue());
					session.update(partSelected);
					updateCount++;
				} else {
					distinationRowNum++;
					copyRow(wbReturn, sheetReturn, sheet, updatePart.getExcelRowNum(), distinationRowNum,
							"PartNo does not exist");
				}

			}
			session.flush();
			session.clear();
			messages.add("No of records updated: " + updateCount);
			wbReturn.write(outStream);
		} catch (Exception ioe) {
			ioe.printStackTrace();
			messages.add(ioe.getMessage());
		}
		return messages;
	}

	@Transactional
	public void updatePartsMaintenance(Parts parts) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(parts);
		// updateSubcategoryOnParts();
		// updateMakemodelOnParts();
		session.flush();
		session.clear();

	}

	@SuppressWarnings("unused")
	@Transactional
	public List<String> updatepartsprices(MultipartFile multipartFile, OutputStream outStream) {

		List<String> messages = new ArrayList<String>();
		// File file = new File(filePath + multipartFile.getOriginalFilename());
		try {
			Workbook wb;
			Workbook wbReturn;
			if (multipartFile.getContentType().equals("application/vnd.ms-excel")) {
				wb = new HSSFWorkbook(multipartFile.getInputStream());
				wbReturn = new HSSFWorkbook();
			} else if (multipartFile.getContentType()
					.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				wb = new XSSFWorkbook(multipartFile.getInputStream());
				wbReturn = new XSSFWorkbook();
			} else {
				messages.add("content type of uploaded file is not supported.");
				return messages;
			}
			Sheet sheet = wb.getSheetAt(0);
			Sheet sheetReturn = wbReturn.createSheet("Vendor Parts Not Updated");
			Row row;
			// row = sheet.getRow(0);
			int distinationRowNum = 0;
			copyRow(wbReturn, sheetReturn, sheet, 0, distinationRowNum, "Remarks");

			Cell supplierIdCell, vendorPartNoCell, partNoCell, PLNoCell, OemNoCell, sellingRateCell;// SellingRate
																									// ->
																									// price
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			List<UpdateVendorParts> updateList = new ArrayList<UpdateVendorParts>();
			DataFormatter formatter = new DataFormatter();
			for (int r = 1; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null) {
					supplierIdCell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
					partNoCell = row.getCell(1, Row.RETURN_BLANK_AS_NULL);
					vendorPartNoCell = row.getCell(2, Row.RETURN_BLANK_AS_NULL);
					PLNoCell = row.getCell(5, Row.RETURN_BLANK_AS_NULL);
					OemNoCell = row.getCell(6, Row.RETURN_BLANK_AS_NULL);
					sellingRateCell = row.getCell(7);
					UpdateVendorParts vendorPart = new UpdateVendorParts();
					if (supplierIdCell == null || formatter.formatCellValue(supplierIdCell) == null) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "SupplierId is empty");
						// messages.add("SupplierId at row "+(r+1) +" is
						// empty.");
						continue;
					} else {
						try {
							Integer suppId = new Integer(formatter.formatCellValue(supplierIdCell).trim());
							vendorPart.setSupplierId(suppId);
						} catch (NumberFormatException ex) {
							distinationRowNum++;
							copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "SupplierId is invalid");
							// messages.add("SupplierId at row "+(r+1) +" is
							// invalid.");
							continue;
						}
					}

					// if (partNoCell == null ||
					// formatter.formatCellValue(partNoCell)==null) {
					// messages.add("partNo at row "+(r+1) +" is empty.");
					// continue;
					// }else{
					// vendorPart.setPartNo(formatter.formatCellValue(partNoCell));
					// }

					if (vendorPartNoCell == null || formatter.formatCellValue(vendorPartNoCell) == null) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum, "vendorPartNo is empty");
						// messages.add("vendorPartNo at row "+(r+1) +" is
						// empty.");
						continue;
					} else {
						vendorPart.setVendorPartNo(formatter.formatCellValue(vendorPartNoCell));
					}

					if (sellingRateCell != null && sellingRateCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						vendorPart.setSellingRate(BigDecimal.valueOf(sellingRateCell.getNumericCellValue()));
					}
					if (PLNoCell != null && formatter.formatCellValue(PLNoCell) != null) {
						vendorPart.setPlno(formatter.formatCellValue(PLNoCell));
					}
					if (OemNoCell != null && formatter.formatCellValue(OemNoCell) != null) {
						vendorPart.setOemNo(formatter.formatCellValue(OemNoCell));
					}
					if (vendorPart.getSellingRate() == null && vendorPart.getPlno() == null
							&& vendorPart.getOemNo() == null) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, r, distinationRowNum,
								"selling rate, PlNo and OemNo is empty");
						// messages.add("selling rate, PlNo and OemNo at row
						// "+(r+1) +" is empty.");
						continue;
					}
					vendorPart.setExcelRowNum(r);
					updateList.add(vendorPart);
				}
			}

			Session session = sessionFactory.getCurrentSession();
			int updateCount = 0;
			for (UpdateVendorParts vpart : updateList) {
				String hSql = "FROM VendorItems vendoritems  WHERE vendoritems.vendorpartno =:vendorpartno AND vendoritems.supplierid=:supplierid";
				Query query = session.createQuery(hSql);
				query.setParameter("vendorpartno", vpart.getVendorPartNo());
				query.setParameter("supplierid", vpart.getSupplierId());

				List<VendorItems> vendoritems = query.list();
				if (vendoritems.size() > 0 && vendoritems.get(0) != null) {
					VendorItems vendorItem = vendoritems.get(0);
					if (vpart.getPlno() != null && vendorItem.getPlno() != null
							&& !vendorItem.getPlno().equals(vpart.getPlno())) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, vpart.getExcelRowNum(), distinationRowNum,
								"Existing plNo and provided plNo are different");
						// messages.add("Vendor part with supplierid:
						// "+vpart.getSupplierId()+" and vendorpartno:
						// "+vpart.getVendorPartNo()+", existing plNo and
						// provided plNo are different.");
						continue;
					}
					if (vpart.getOemNo() != null && vendorItem.getOemno() != null
							&& !vendorItem.getOemno().equals(vpart.getOemNo())) {
						distinationRowNum++;
						copyRow(wbReturn, sheetReturn, sheet, vpart.getExcelRowNum(), distinationRowNum,
								"Existing OemNo and provided OemNo are different");
						// messages.add("Vendor part with supplierid:
						// "+vpart.getSupplierId()+" and vendorpartno:
						// "+vpart.getVendorPartNo()+", existing OemNo and
						// provided OemNo are different.");
						continue;
					}
					if (vpart.getSellingRate() != null) {
						vendorItem.setSellingrate(vpart.getSellingRate());
					}
					if (vpart.getPlno() != null) {
						vendorItem.setPlno(vpart.getPlno());
					}
					if (vpart.getOemNo() != null) {
						vendorItem.setOemno(vpart.getOemNo());
					}
					session.update(vendorItem);
					updateCount++;
				} else {
					distinationRowNum++;
					copyRow(wbReturn, sheetReturn, sheet, vpart.getExcelRowNum(), distinationRowNum,
							"Vendor Part does not exist");
					// messages.add("Vendor part with supplierid:
					// "+vpart.getSupplierId()+" and vendorpartno:
					// "+vpart.getVendorPartNo()+" does not exist.");
				}

			}
			session.flush();
			session.clear();
			messages.add("No of records updated: " + updateCount);
			// FileOutputStream fileOutputStream = new FileOutputStream(file);

			wbReturn.write(outStream);
			// fileOutputStream.flush();
			// fileOutputStream.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			messages.add(ioe.getMessage());
		}
		return messages;
	}

	@Transactional
	public void updateSubcategoryOnParts() {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "UPDATE parts p INNER JOIN subcategory m " + "ON p.SubCategory = m.SubCategoryCode "
				+ "SET p.Category = m.SubCategoryName " + "WHERE  p.SubCategory = m.SubCategoryCode;";
		Query query = session.createSQLQuery(updateSql);
		query.executeUpdate();
		session.flush();
		session.clear();

	}

}
