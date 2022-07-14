package com.bvas.insight.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bvas.insight.data.PartList;
import com.bvas.insight.data.PartsFix;
import com.bvas.insight.data.PreOrderParts;
import com.bvas.insight.data.VendorSalesHelper;
import com.bvas.insight.data.VendorSalesOrderDetails;
import com.bvas.insight.entity.MakeModel;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.jdbc.ChStocksDAOImpl;
import com.bvas.insight.jdbc.GrStocksDAOImpl;
import com.bvas.insight.jdbc.MpStocksDAOImpl;
import com.bvas.insight.utilities.FileUploadForm;

@Repository
public class ExcelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

	@Autowired
	@Qualifier("partsService")
	protected PartsService partsService;

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Boolean checkVendorOrdereditems(Integer orderno) {

		String hqlQuery = "From VendorOrderedItems vendorordereditems  where vendorordereditems.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);

		@SuppressWarnings("unchecked")
		List<VendorOrderedItems> norders = query.list();
		session.flush();
		session.clear();
		return norders.isEmpty();
	}

	public File createPartListExcelFile(List<PartList> partslist, Integer orderno, String path) throws IOException {

		File file = new File(path + "PartsList_" + orderno + ".xls");
		int rownum = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("PartsList_" + orderno);
		for (PartList key : partslist) {
			Row row = sheet.createRow(rownum++);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(key.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(key.getMakemodelname());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(key.getYear());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(key.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(key.getQuantity());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(key.getLocation());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

	public File createPostOrderExcelFile(List<PreOrderParts> parts, Integer orderno, String filePath)
			throws IOException {

		File file = new File(filePath + "PostOrder_" + orderno + ".xls");
		int rownum = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("PostOrder_" + orderno);
		for (PreOrderParts key : parts) {

			Row row = sheet.createRow(rownum++);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(key.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(key.getVendorpartno());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(key.getItemdesc1());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(key.getItemdesc2());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(key.getCapa());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(key.getPlno());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(key.getOemno());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(key.getPrice().doubleValue());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(key.getQuantity());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

	public File createPreOrderExcelFile(List<PreOrderParts> parts, Integer orderno, String filePath)
			throws IOException {

		File file = new File(filePath + "PreOrder_" + orderno + ".xls");
		int rownum = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("PreOrder_" + orderno);
		for (PreOrderParts key : parts) {
			Row row = sheet.createRow(rownum++);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(key.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(key.getVendorpartno());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(key.getItemdesc1());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(key.getItemdesc2());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(key.getCapa());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(key.getPlno());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(key.getOemno());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(key.getQuantity());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;
	}

	@Transactional
	public int getSalesOfPartForGivenDuration(String partno, String fromdate, String todate) {

		// LOGGER.info("partno:" + partno);

		Integer count1 = 0;
		Integer count2 = 0;

		String hqlQuery1 = "SELECT COUNT(1) AS count FROM  invoicedetails invdtls, INVOICE i  "
				+ " WHERE  i.InvoiceNumber = invdtls.InvoiceNumber AND i.ReturnedInvoice = 0 "
				+ " AND invdtls.PARTNUMBER =:partno AND i.OrderDate BETWEEN :fromdate AND :todate";

		Session session = sessionFactory.getCurrentSession();

		Query query1 = (session.createSQLQuery(hqlQuery1).setParameter("partno", partno)
				.setParameter("fromdate", fromdate).setParameter("todate", todate));

		// LOGGER.info("count1:" + ((Number) query1.uniqueResult()).intValue());

		count1 = ((Number) query1.uniqueResult()).intValue();

		if ((count1 <= 0) || (count1 == null)) {
			count1 = 0;
		}

		String hqlQuery2 = "SELECT COUNT(1) AS count FROM PARTS P, invoicedetails invdtls, INVOICE i  "
				+ " WHERE P.PARTNO = invdtls.PARTNUMBER  AND i.InvoiceNumber = invdtls.InvoiceNumber AND i.ReturnedInvoice = 0 "
				+ " AND p.INTERCHANGENO =:partno AND i.OrderDate BETWEEN :fromdate AND :todate";

		Query query2 = (session.createSQLQuery(hqlQuery2).setParameter("partno", partno)
				.setParameter("fromdate", fromdate).setParameter("todate", todate));

		// LOGGER.info("count2:" + ((Number) query2.uniqueResult()).intValue());

		count2 = ((Number) query2.uniqueResult()).intValue();

		if ((count2 <= 0) || (count2 == null)) {
			count2 = 0;
		}

		return count1 + count2;

	}

	public List<String> getUploadedExcel(FileUploadForm uploadForm) {

		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);

			}
		}
		return fileNames;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public VendorOrder getVendorOrder(Integer orderno) {

		String hqlQuery = "From VendorOrder vendororder  where vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);

		List<VendorOrder> vendororders = query.list();
		session.flush();
		session.clear();
		if (vendororders.size() <= 0) {
			VendorOrder vendororder2 = new VendorOrder();
			vendororder2.setOrderno(0);
			return vendororder2;

		} else {
			return vendororders.get(0);
		}
	}

	@Transactional
	public VendorSalesOrderDetails getVendorSalesOrderDetails(String partno) {

		VendorSalesOrderDetails vso = new VendorSalesOrderDetails(partno);

		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = " SELECT vo.supplierid, vo.orderno, vo.containerno, vo.orderdate  , v.price "
				+ " FROM vendororder vo, vendorordereditems v "
				+ " WHERE vo.OrderNo = v.OrderNo AND  vo.isfinal = 'Y' AND UpdatedInventory= 'Y' "
				+ " AND vo.InventoryDoneDate IS NOT NULL  AND vo.deliveredDate IS NOT NULL "
				+ " AND vo.InventoryDoneDate <> '0000-00-00'   AND vo.deliveredDate <> '0000-00-00' "
				+ " AND ( vo.ContainerNo IS NOT NULL OR vo.ContainerNo <> '') AND v.PartNo =:partno "
				+ " AND vo.supplierid NOT IN (SELECT SUPPLIERID FROM VENDORS WHERE COMPANYTYPE IN ('H','Z')) "
				+ " ORDER BY VO.OrderDate DESC LIMIT 1 ";

		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("partno", partno))
				.addScalar("supplierid").addScalar("orderno").addScalar("containerno").addScalar("orderdate")
				.addScalar("price").setResultTransformer(Transformers.aliasToBean(VendorSalesOrderDetails.class));
		List<VendorSalesOrderDetails> results = query.list();
		session.flush();
		session.clear();
		if (results == null) {
			return vso;
		} else {
			if (results.isEmpty()) {
				return vso;
			} else if (results.size() <= 0) {
				return vso;
			} else {

				return results.get(0);

			}
		}

	}

	@Transactional
	public VendorItems ifVendorItemExists(Integer cellSupplierID, String cellPartNo, String cellVendorPartNo) {

		String hqlQuery = "From VendorItems vendoritems  where vendoritems.supplierid=:supplierid and vendoritems.partno=:partno and vendoritems.vendorpartno=:vendorpartno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("supplierid", cellSupplierID);
		query.setParameter("partno", cellPartNo);
		query.setParameter("vendorpartno", cellVendorPartNo);

		@SuppressWarnings("unchecked")
		List<VendorItems> vendororders = query.list();
		session.flush();
		session.clear();
		if (vendororders.size() > 0) {
			return vendororders.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public String uploadExcelPartsFix(FileUploadForm uploadForm) {

		String message = "";
		String mmSql = "FROM MakeModel makemodel  WHERE makemodel.makemodelcode =:makemodelcode";
		String pSql = "FROM Parts parts  WHERE parts.partno =:partno";
		Session session = sessionFactory.getCurrentSession();
		List<PartsFix> partsfixlist = new ArrayList<PartsFix>();
		List<MultipartFile> files = uploadForm.getFiles();
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				if (StringUtils.containsIgnoreCase(multipartFile.getOriginalFilename(), ".xls")) {

					try {
						// LOGGER.info(multipartFile.getOriginalFilename());
						InputStream file;
						file = multipartFile.getInputStream();
						HSSFWorkbook workbook = new HSSFWorkbook(file);
						HSSFSheet sheet = workbook.getSheetAt(0);
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()) {
							PartsFix partsfix = new PartsFix();
							HSSFRow row = (HSSFRow) rowIterator.next();
							for (int i = 0; i <= 13; i++) {
								row.getCell(i, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
							}
							if (row.getRowNum() == 0) {
								continue;
							} else {

								if ((row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue() != null) && (row
										.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue().length() > 0)) {

									if (row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue()
											.equalsIgnoreCase("ZZZ")) {
										break;
									} else {
										partsfix.setMakemodelcode(row.getCell(0).getStringCellValue());
										partsfix.setPartslinknumber(row.getCell(1).getStringCellValue());
										partsfix.setOemnumber(row.getCell(2).getStringCellValue());
										partsfix.setMakemodelname(row.getCell(3).getStringCellValue());
										partsfix.setPartno(row.getCell(4).getStringCellValue());
										partsfix.setInterchangeno(row.getCell(5).getStringCellValue());
										partsfix.setOthersidepartno(row.getCell(6).getStringCellValue());
										partsfix.setPartdescription(row.getCell(7).getStringCellValue());
										partsfix.setYearfrom(row.getCell(8).getStringCellValue());
										partsfix.setYearto(row.getCell(9).getStringCellValue());
										partsfix.setYear(row.getCell(10).getStringCellValue());
										partsfix.setCategory(row.getCell(11).getStringCellValue());
										partsfix.setDpinumber(row.getCell(12).getStringCellValue());
										partsfix.setSubcategory((row.getCell(13).getStringCellValue()));

									}
								}

							}
							partsfixlist.add(partsfix);
						} // outer while
						for (PartsFix partsfix : partsfixlist) {
							if (!partsfix.getMakemodelname().equalsIgnoreCase("")) {
								Query queryModel = session.createQuery(mmSql);
								queryModel.setParameter("makemodelcode", partsfix.getMakemodelcode());
								MakeModel makemodel = (MakeModel) queryModel.list().get(0);
								if (makemodel != null) {
									makemodel.setMakemodelname(partsfix.getMakemodelname());
									session.saveOrUpdate(makemodel);
								}
							}
							if (!partsfix.getPartno().equalsIgnoreCase("")) {
								Parts parts = null;
								Query queryParts = session.createQuery(pSql);
								queryParts.setParameter("partno", partsfix.getPartno());
								if (queryParts.list().size() > 0) {
									parts = (Parts) queryParts.list().get(0);
								} else {
									message = message + "\n" + "Parts not there : " + partsfix.getPartno();
									parts = null;
								}
								if (parts != null) {
									if (!partsfix.getMakemodelcode().equalsIgnoreCase("")) {
										parts.setMakemodelcode(partsfix.getMakemodelcode());
									}
									if (!partsfix.getPartslinknumber().equalsIgnoreCase("")) {
										parts.setKeystonenumber(partsfix.getPartslinknumber());
									}
									if (!partsfix.getOemnumber().equalsIgnoreCase("")) {
										parts.setOemnumber(partsfix.getOemnumber());
									}
									if (!partsfix.getInterchangeno().equalsIgnoreCase("")) {
										parts.setInterchangeno(partsfix.getInterchangeno());
									}
									if (!partsfix.getPartdescription().equalsIgnoreCase("")) {
										parts.setPartdescription(partsfix.getPartdescription());
									}
									if (!partsfix.getYearfrom().equalsIgnoreCase("")) {
										parts.setYearfrom(Integer.parseInt(partsfix.getYearfrom()));
									}
									if (!partsfix.getYearto().equalsIgnoreCase("")) {
										parts.setYearto(Integer.parseInt(partsfix.getYearto()));
									}
									if (!partsfix.getYear().equalsIgnoreCase("")) {
										parts.setYear(partsfix.getYear());
									}
									if (!partsfix.getDpinumber().equalsIgnoreCase("")) {
										parts.setDpinumber(partsfix.getDpinumber());
									}
									if (!partsfix.getSubcategory().equalsIgnoreCase("")) {
										parts.setSubcategory(partsfix.getSubcategory());
									}
									session.saveOrUpdate(parts);
								}

							}
						}
						session.flush();
						session.clear();
						return message;
					} catch (IOException e) {
						session.flush();
						session.clear();
						message = message + "\n" + e.getMessage().toString();
						return message;
					}

				}
			}
		}
		return message;
	}

	@Transactional
	public String uploadExcelToContainer(FileUploadForm uploadForm, String addcontainernotxt) {

		String message = "";
		Integer i = 0;
		Integer orderno = Integer.parseInt(addcontainernotxt.trim());
		VendorOrder vendororder = getVendorOrder(orderno);
		if ((vendororder.getOrderno().equals(orderno)) && (checkVendorOrdereditems(orderno))) {
			Session session = sessionFactory.getCurrentSession();

			List<MultipartFile> files = uploadForm.getFiles();
			if (null != files && files.size() > 0) {
				for (MultipartFile multipartFile : files) {
					if (StringUtils.containsIgnoreCase(multipartFile.getOriginalFilename(), ".xls")) {
						try {
							InputStream file = multipartFile.getInputStream();
							HSSFWorkbook workbook = new HSSFWorkbook(file);
							HSSFSheet sheet = workbook.getSheetAt(0);
							Iterator<Row> rowIterator = sheet.iterator();

							while (rowIterator.hasNext()) {
								// LOGGER.info(i.toString());
								HSSFRow row = (HSSFRow) rowIterator.next();

								if (row.getRowNum() == 0) {
									continue;
								} else {

									row.getCell(0, Row.CREATE_NULL_AS_BLANK);
									row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);

									row.getCell(1, Row.CREATE_NULL_AS_BLANK);
									row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);

									row.getCell(2, Row.CREATE_NULL_AS_BLANK);
									row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);

									row.getCell(3, Row.CREATE_NULL_AS_BLANK);
									row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);

									if (row.getCell(0).getStringCellValue().trim().equalsIgnoreCase("END")) {
										break;
									}

									String cellPartno = row.getCell(0).getStringCellValue().trim();
									if (cellPartno.length() > 7) {
										// LOGGER.info(cellPartno + "is too big");
									}
									if (cellPartno.equalsIgnoreCase("")) {
										continue;
									}
									// String cellVendorpartno = row.getCell(1)
									// .getStringCellValue().trim();
									String cellQuantity = row.getCell(2).getStringCellValue();
									Double cellPrice = Double.parseDouble(row.getCell(3).getStringCellValue().trim());

									VendorOrderedItems vendorordereditems = new VendorOrderedItems();
									vendorordereditems.setNoorder(i++);
									vendorordereditems.setOrderno(orderno);
									vendorordereditems.setPartno(cellPartno.trim());
									vendorordereditems.setVendorpartno("");
									vendorordereditems.setPrice(new BigDecimal(cellPrice, MathContext.DECIMAL64));
									if (cellQuantity.isEmpty()) {
										vendorordereditems.setQuantity(0);
									} else {
										vendorordereditems.setQuantity(Integer.parseInt(cellQuantity.trim()));
									}

									session.save(vendorordereditems);

								}
							}

							message = "Following file was uploaded to the container # "
									+ multipartFile.getOriginalFilename();

						} catch (IOException e) {
							LOGGER.error("IOException: uploadExcelToContainer##" + e.getMessage().toString());
							message = "error processing this file";
						}

					} else {
						message = "Please check input file it should be  .xls file";
					}
				}

			} else {
				message = "No files uploaded";
			}

			session.flush();
			session.clear();
			return message;
		} // outer if
		else {
			message = "Container number does not exists";
			return message;
		}

	}

	@Transactional
	public String uploadExcelToVendorItems(FileUploadForm uploadForm) {

		String message = "";
		// Integer i = 0;
		List<MultipartFile> files = uploadForm.getFiles();
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				if (StringUtils.containsIgnoreCase(multipartFile.getOriginalFilename(), ".xls")) {

					LOGGER.info(multipartFile.getOriginalFilename());
					String xclLog = "";
					try {
						InputStream file = multipartFile.getInputStream();
						HSSFWorkbook workbook = new HSSFWorkbook(file);
						HSSFSheet sheet = workbook.getSheetAt(0);
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()) {

							Integer cellSupplierID = 0;
							String cellPartNo = "";
							String cellVendorPartNo = "";
							String cellItemDesc1 = "";
							String cellItemDesc2 = "";
							String cellPLNo = "";
							String cellOEMNo = "";
							Double cellSellingRate = new Double("0.00");
							Integer cellNoOfPieces = 0;
							Double cellItemSize = new Double("0.00");
							Double cellItemSizeUnits = new Double("0.00");

							HSSFRow row = (HSSFRow) rowIterator.next();
							if (row.getRowNum() == 0) {
								continue;
							} else {
								Cell cell0 = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
								cell0.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell1 = row.getCell(1, Row.CREATE_NULL_AS_BLANK);
								cell1.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell2 = row.getCell(2, Row.CREATE_NULL_AS_BLANK);
								cell2.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell3 = row.getCell(3, Row.CREATE_NULL_AS_BLANK);
								cell3.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell4 = row.getCell(4, Row.CREATE_NULL_AS_BLANK);
								cell4.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell5 = row.getCell(5, Row.CREATE_NULL_AS_BLANK);
								cell5.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell6 = row.getCell(6, Row.CREATE_NULL_AS_BLANK);
								cell6.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell7 = row.getCell(7, Row.CREATE_NULL_AS_BLANK);
								cell7.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell8 = row.getCell(8, Row.CREATE_NULL_AS_BLANK);
								cell8.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell9 = row.getCell(9, Row.CREATE_NULL_AS_BLANK);
								cell9.setCellType(Cell.CELL_TYPE_STRING);

								Cell cell10 = row.getCell(10, Row.CREATE_NULL_AS_BLANK);
								cell10.setCellType(Cell.CELL_TYPE_STRING);

								if ((cell0.getStringCellValue() != null) && (cell0.getStringCellValue().length() > 0)) {

									if (cell0.getStringCellValue().equalsIgnoreCase("ZZZ")) {
										break;
									} else {
										cellSupplierID = Integer.parseInt(cell0.getStringCellValue());
									}
								}

								if ((cell1.getStringCellValue() != null) && (cell1.getStringCellValue().length() > 0)) {
									cellPartNo = cell1.getStringCellValue().trim();
									LOGGER.info(cellPartNo);

								}
								if ((cell2.getStringCellValue() != null) && (cell2.getStringCellValue().length() > 0)) {
									cellVendorPartNo = cell2.getStringCellValue().trim();
								}
								if ((cell3.getStringCellValue() != null) && (cell3.getStringCellValue().length() > 0)) {
									cellItemDesc1 = cell3.getStringCellValue().trim();
								}

								if ((cell4.getStringCellValue() != null) && (cell4.getStringCellValue().length() > 0)) {
									cellItemDesc2 = cell4.getStringCellValue().trim();
								}

								if ((cell5.getStringCellValue() != null) && (cell5.getStringCellValue().length() > 0)) {
									cellPLNo = cell5.getStringCellValue().trim();
								}

								if ((cell6.getStringCellValue() != null) && (cell6.getStringCellValue().length() > 0)) {
									cellOEMNo = cell6.getStringCellValue().trim();
								}

								if ((cell7.getStringCellValue() != null) && (cell7.getStringCellValue().length() > 0)) {

									if (cell7.getStringCellValue().equalsIgnoreCase("")) {
										cellSellingRate = new Double("0.00");
									} else {
										cellSellingRate = Double.parseDouble(row.getCell(7).getStringCellValue());

									}
								}

								if ((cell8.getStringCellValue() != null) && (cell8.getStringCellValue().length() > 0)) {

									if (cell8.getStringCellValue().equalsIgnoreCase("")) {
										cellNoOfPieces = new Integer("0");
									} else {
										cellNoOfPieces = Integer.parseInt(cell8.getStringCellValue());
									}

								}

								if ((cell9.getStringCellValue() != null) && (cell9.getStringCellValue().length() > 0)) {
									if (cell9.getStringCellValue().equalsIgnoreCase("")) {
										cellItemSize = new Double("0.00");
									} else {
										cellItemSize = Double.parseDouble(cell9.getStringCellValue());
									}
								}

								if ((cell10.getStringCellValue() != null)
										&& (cell10.getStringCellValue().length() > 0)) {
									if (cell10.getStringCellValue().equalsIgnoreCase("")) {
										cellItemSizeUnits = new Double("0.00");
									} else {
										cellItemSizeUnits = Double.parseDouble(cell10.getStringCellValue());
									}

								}

							} // else
							Session session = sessionFactory.getCurrentSession();

							VendorItems vendoritems = ifVendorItemExists(cellSupplierID, cellPartNo, cellVendorPartNo);
							if ((vendoritems != null)) {
								cellItemDesc1 = vendoritems.getItemdesc1();
								cellItemDesc2 = vendoritems.getItemdesc2();
							} else {
								vendoritems = new VendorItems();
							}
							vendoritems.setSupplierid(cellSupplierID);
							vendoritems.setPartno(cellPartNo);
							vendoritems.setVendorpartno(cellVendorPartNo);
							if (cellItemDesc1 != null && !(cellItemDesc1.equalsIgnoreCase(""))) {
								vendoritems.setItemdesc1(cellItemDesc1);
							}
							if (cellItemDesc2 != null && !(cellItemDesc2.equalsIgnoreCase(""))) {

								vendoritems.setItemdesc2(cellItemDesc2);

							}
							if (cellPLNo != null && !(cellPLNo.equalsIgnoreCase(""))) {
								vendoritems.setPlno(cellPLNo);
							}
							if (cellOEMNo != null && !(cellOEMNo.equalsIgnoreCase(""))) {
								vendoritems.setOemno(cellOEMNo);
							}

							vendoritems.setSellingrate(new BigDecimal(cellSellingRate));
							vendoritems.setNoofpieces(cellNoOfPieces);
							vendoritems.setItemsize(new BigDecimal(cellItemSize));
							vendoritems.setItemsizeunits(cellItemSizeUnits.toString());
							LOGGER.info("Inventory uploaded through excel: Part No:" + cellPartNo + ",PL No: "
									+ cellPLNo + ",Item Desc:" + cellItemDesc1 + ", OEM NO:" + cellOEMNo);
							session.saveOrUpdate(vendoritems);
							session.flush();
							session.clear();
						} // while
					} // try
					catch (IOException e) {
						LOGGER.error("IOException: uploadExcelToVendorItems##" + e.getMessage().toString());
						message = "error processing this file";
					}
				}
			}
		} else {
			message = "No files uploaded";
		}

		return message;

	}

	@Transactional
	public List<VendorSalesHelper> uploadExcelToVendorSales(FileUploadForm uploadForm, ChStocksDAOImpl chpartsdao,
			GrStocksDAOImpl grpartsdao, MpStocksDAOImpl mppartsdao) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance(); // Get the current date
		String datetoday = sdf.format(cal.getTime());
		// LOGGER.info("datetoday:" + datetoday);

		cal.add(Calendar.MONTH, -12);
		String date12Mback = sdf.format(cal.getTime());
		// LOGGER.info("date12Mback:" + date12Mback);

		cal.add(Calendar.MONTH, 6);
		String date6Mback = sdf.format(cal.getTime());
		// LOGGER.info("date6Mback:" + date6Mback);

		cal.add(Calendar.MONTH, 3);
		String date3Mback = sdf.format(cal.getTime());
		// LOGGER.info("date3Mback:" + date3Mback);

		cal.add(Calendar.MONTH, 2);
		String date1Mback = sdf.format(cal.getTime());
		// LOGGER.info("date1Mback:" + date1Mback);

		cal.add(Calendar.MONTH, -1);
		String date2Mback = sdf.format(cal.getTime());
		// LOGGER.info("date2Mback:" + date2Mback);

		List<VendorSalesHelper> lvsh = new LinkedList<VendorSalesHelper>();
		int i = 0;
		List<MultipartFile> files = uploadForm.getFiles();
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				if (StringUtils.containsIgnoreCase(multipartFile.getOriginalFilename(), ".xls")) {

					LOGGER.info(multipartFile.getOriginalFilename());
					String xclLog = "";
					try {
						InputStream file = multipartFile.getInputStream();
						HSSFWorkbook workbook = new HSSFWorkbook(file);
						HSSFSheet sheet = workbook.getSheetAt(0);
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()) {

							String cellPartNo = "";

							HSSFRow row = (HSSFRow) rowIterator.next();
							if (row.getRowNum() == 0) {
								continue;
							} else {

								Cell cell0 = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
								cell0.setCellType(Cell.CELL_TYPE_STRING);

								if ((cell0.getStringCellValue() != null) && (cell0.getStringCellValue().length() > 0)) {

									if (cell0.getStringCellValue().equalsIgnoreCase("ZZZ")) {
										break;
									} else {

										cellPartNo = cell0.getStringCellValue().trim();
										VendorSalesHelper vsh = new VendorSalesHelper(cellPartNo);
										Parts part = partsService.getParts(cellPartNo);

										vsh.setPartno(part.getPartno());
										vsh.setManufacturername(part.getManufacturername());
										vsh.setMakemodelname(part.getMakemodelname());
										vsh.setPartdescription(part.getPartdescription());

										vsh.setCh1m(chpartsdao.getSalesOfPartForGivenBranchDuration(cellPartNo,
												date1Mback, datetoday));
										vsh.setCh2m(chpartsdao.getSalesOfPartForGivenBranchDuration(cellPartNo,
												date2Mback, datetoday));
										vsh.setCh3m(chpartsdao.getSalesOfPartForGivenBranchDuration(cellPartNo,
												date3Mback, datetoday));
										vsh.setCh12m(chpartsdao.getSalesOfPartForGivenBranchDuration(cellPartNo,
												date12Mback, datetoday));

										lvsh.add(vsh);

									}
								}

							} // else

						} // while
						return lvsh;
					} // try
					catch (IOException e) {
						LOGGER.error("IOException: uploadExcelToVendorItems##" + e.getMessage().toString());

					}
				}
			}
		} else {
			VendorSalesHelper vsh = new VendorSalesHelper("NONE");
			lvsh.add(vsh);
			return lvsh;
		}

		return lvsh;

	}

	@Transactional
	public List<VendorSalesHelper> uploadExcelToVendorSales2(FileUploadForm uploadForm) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance(); // Get the current date
		String datetoday = sdf.format(cal.getTime());
		// LOGGER.info("datetoday:" + datetoday);

		cal.add(Calendar.MONTH, -12);
		String date12Mback = sdf.format(cal.getTime());
		// LOGGER.info("date12Mback:" + date12Mback);

		cal.add(Calendar.MONTH, 6);
		String date6Mback = sdf.format(cal.getTime());
		// LOGGER.info("date6Mback:" + date6Mback);

		cal.add(Calendar.MONTH, 3);
		String date3Mback = sdf.format(cal.getTime());
		// LOGGER.info("date3Mback:" + date3Mback);

		cal.add(Calendar.MONTH, 2);
		String date1Mback = sdf.format(cal.getTime());
		// LOGGER.info("date1Mback:" + date1Mback);

		cal.add(Calendar.MONTH, -1);
		String date2Mback = sdf.format(cal.getTime());
		// LOGGER.info("date2Mback:" + date2Mback);

		List<VendorSalesHelper> lvsh = new LinkedList<VendorSalesHelper>();
		int i = 0;
		List<MultipartFile> files = uploadForm.getFiles();
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				if (StringUtils.containsIgnoreCase(multipartFile.getOriginalFilename(), ".xls")) {

					LOGGER.info(multipartFile.getOriginalFilename());
					String xclLog = "";
					try {
						InputStream file = multipartFile.getInputStream();
						HSSFWorkbook workbook = new HSSFWorkbook(file);
						HSSFSheet sheet = workbook.getSheetAt(0);
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()) {

							String cellPartNo = "";

							HSSFRow row = (HSSFRow) rowIterator.next();
							if (row.getRowNum() == 0) {
								continue;
							} else {

								Cell cell0 = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
								cell0.setCellType(Cell.CELL_TYPE_STRING);

								if ((cell0.getStringCellValue() != null) && (cell0.getStringCellValue().length() > 0)) {

									if (cell0.getStringCellValue().equalsIgnoreCase("ZZZ")) {
										break;
									} else {

										cellPartNo = cell0.getStringCellValue().trim();
										cellPartNo = cellPartNo.replaceAll("/[^A-Za-z0-9]/", "");
										VendorSalesHelper vsh = new VendorSalesHelper(cellPartNo);
										Parts part = partsService.getParts(cellPartNo);
										if (part == null) {
											continue;
										}
										// LOGGER.info(cellPartNo);
										VendorSalesOrderDetails vso = getVendorSalesOrderDetails(part.getPartno());

										vsh.setPartno(part.getPartno());
										vsh.setManufacturername(part.getManufacturername());
										vsh.setMakemodelname(part.getMakemodelname());
										vsh.setPartdescription(part.getPartdescription());

										vsh.setCh1m(getSalesOfPartForGivenDuration(cellPartNo, date1Mback, datetoday));
										vsh.setCh2m(getSalesOfPartForGivenDuration(cellPartNo, date2Mback, datetoday));
										vsh.setCh3m(getSalesOfPartForGivenDuration(cellPartNo, date3Mback, datetoday));

										vsh.setCh12m(
												getSalesOfPartForGivenDuration(cellPartNo, date12Mback, datetoday));

										vsh.setOrder(part.getUnitsonorder());
										vsh.setStock(part.getUnitsinstock());
										vsh.setSupplierid(vso.getSupplierid());
										vsh.setOrderno(vso.getOrderno());
										vsh.setContainerno(vso.getContainerno());
										vsh.setOrderdate(vso.getOrderdate());
										vsh.setPrice(vso.getPrice());

										lvsh.add(vsh);

									}
								}

							} // else

						} // while
						return lvsh;
					} // try
					catch (IOException e) {
						LOGGER.error("IOException: uploadExcelToVendorItems##" + e.getMessage().toString());

					}
				}
			}
		} else {
			VendorSalesHelper vsh = new VendorSalesHelper("NONE");
			lvsh.add(vsh);
			return lvsh;
		}

		return lvsh;

	}

}
