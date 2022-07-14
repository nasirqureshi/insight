package com.bvas.insight.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bvas.insight.data.ActualPriceList;
import com.bvas.insight.data.CategoryBySalesAnalysis;
import com.bvas.insight.data.PartList;
import com.bvas.insight.data.PreOrderParts;
import com.bvas.insight.data.ProcurementParts;
import com.bvas.insight.data.ScanOrderDetails;
import com.bvas.insight.data.SubCatAllBranch;
import com.bvas.insight.data.VendorOrderedItemsDetails;
import com.bvas.insight.data.VendorSalesHelper;

public class BVSExcel {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(BVSExcel.class);

	public File createActualPriceListExcelFile(List<ActualPriceList> parts, Integer orderno, String path)
			throws FileNotFoundException, IOException {

		File file = new File(path + "ActualPriceList_" + orderno + ".xls");

		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("ActualPriceList");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("ACTUALPRICE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("QUANTITY");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("ORDERNO");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("TOTAL");

		for (ActualPriceList actualprice : parts) {
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(actualprice.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(actualprice.getActualprice().doubleValue());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(actualprice.getQuantity().doubleValue());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(actualprice.getOrderno().doubleValue());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(actualprice.getTotal().doubleValue());

		}

		FileOutputStream out = new FileOutputStream(file);

		workbook.write(out);
		out.flush();
		out.close();
		return file;
	}

	public File createAddtoOrderFile(List<VendorOrderedItemsDetails> addtoordredetailslist, String filePath,
			String criteria) throws IOException {

		File file = new File(filePath + "addtoinvent_" + criteria + ".xls");
		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("addtoorder");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("YEAR");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("CAPA");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("ST");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("OR");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("RE");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("SFTY");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("TYPE");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("COSTPRICE");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("QTY");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("TOTPRICE");

		for (VendorOrderedItemsDetails allbranch : addtoordredetailslist) {
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getYear());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getCapa());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getUnitsinstock() == null ? 0 : allbranch.getUnitsinstock());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getUnitsonorder() == null ? 0 : allbranch.getUnitsonorder());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(allbranch.getReorderlevel() == null ? 0 : allbranch.getReorderlevel());

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(allbranch.getSafetyquantity() == null ? 0 : allbranch.getSafetyquantity());

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(allbranch.getOrdertype());

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(allbranch.getPrice() == null ? 0 : (allbranch.getPrice().doubleValue()));

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(allbranch.getQty() == null ? 0 : allbranch.getQty().doubleValue());

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(allbranch.getTotalprice() == null ? 0 : allbranch.getTotalprice().doubleValue());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

	public File createDeadInventoryFile(List<CategoryBySalesAnalysis> categorysaleslist, String filePath,
			String subcategoryselected) throws IOException {

		File file = new File(filePath + "deadinventory_" + subcategoryselected + ".xls");
		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("deadinventory");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("TOTALSOLD");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("UNITSINSTOCK");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("UNITSONORDER");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("REORDER");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("BUY");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("SELL");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("PERCENT");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("TYPE");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("YEARFROM");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("YEARTO");

		for (CategoryBySalesAnalysis allbranch : categorysaleslist) {
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getTotalsold().doubleValue());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getUnitsinstock());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getUnitsonorder());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getReorderlevel());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(allbranch.getBuyingprice().doubleValue());

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(allbranch.getSellingprice().doubleValue());

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(allbranch.getPercent().doubleValue());

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(allbranch.getOrdertype());

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(allbranch.getYearfrom());

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(allbranch.getYearto());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

	public File createInventoryTransferFile(List<ScanOrderDetails> partstransferdetailslist, String filePath,
			String subcategoryselected, String RequestOrderGR, String RequestOrderMP) throws IOException {

		ArrayList<String> listMP = new ArrayList<String>(Arrays.asList(RequestOrderMP.trim().split(",")));
		ArrayList<String> listGR = new ArrayList<String>(Arrays.asList(RequestOrderGR.trim().split(",")));

		File file = new File(filePath + "inventoryTransfer_" + subcategoryselected + ".xls");
		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("inventoryTransfer");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("QUANTITYTOTAL");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("SAFETYTOTAL");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("CHSTOCK");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("CHSAFETY");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("CHRE");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("GRSTOCK");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("GRSAFETY");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("GRRE");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("GRR");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("MPSTOCK");

		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("MPSAFETY");

		Cell cellh15 = rowHeader.createCell(15);
		cellh15.setCellValue("MPRE");

		Cell cellh16 = rowHeader.createCell(16);
		cellh16.setCellValue("MPR");

		int i = 0;
		for (ScanOrderDetails scanorderitems : partstransferdetailslist) {
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(scanorderitems.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(scanorderitems.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(scanorderitems.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(scanorderitems.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(
					scanorderitems.chunitsinstock + scanorderitems.grunitsinstock + scanorderitems.mpunitsinstock);

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(scanorderitems.chsafetyquantity + scanorderitems.grsafetyquantity
					+ scanorderitems.mpsafetyquantity);

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(scanorderitems.chunitsinstock);

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(scanorderitems.chsafetyquantity);

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(scanorderitems.chreorderlevel);

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(scanorderitems.grunitsinstock);

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(scanorderitems.grsafetyquantity);

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(scanorderitems.grreorderlevel);

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(listGR.get(i));

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(scanorderitems.mpunitsinstock);

			Cell cell14 = row.createCell(14);
			cell14.setCellValue(scanorderitems.mpsafetyquantity);

			Cell cell15 = row.createCell(15);
			cell15.setCellValue(scanorderitems.mpreorderlevel);

			Cell cell16 = row.createCell(16);
			cell16.setCellValue(listMP.get(i));

			i++;
		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

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

			Cell cell6 = row.createCell(6);

			cell6.setCellValue(key.getUnitsinstock());

			Cell cell7 = row.createCell(7);

			cell7.setCellValue(key.getUnitsonorder());

			Cell cell8 = row.createCell(8);

			cell8.setCellValue(key.getReorderlevel());
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

	public File createProcurementFile(List<ProcurementParts> procurementpartslist, String filePath,
			String subcategoryselected) throws IOException {
		File file = new File(filePath + "procurement_" + subcategoryselected + ".xls");
		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("procurement");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("YEAR");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("CAPA");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("TYPE");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("UNITSINSTOCK");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("UNITSONORDER");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("REORDER");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("SAFETY");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("QUANTITY");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("DPI");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("PLINK");

		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("BUY");

		Cell cellh15 = rowHeader.createCell(15);
		cellh15.setCellValue("SELL");

		Cell cellh16 = rowHeader.createCell(16);
		cellh16.setCellValue("PERCENT");

		Cell cellh17 = rowHeader.createCell(17);
		cellh17.setCellValue("SALELASTYR");

		Cell cellh18 = rowHeader.createCell(18);
		cellh18.setCellValue("SALE2YR");

		Cell cellh119 = rowHeader.createCell(19);
		cellh119.setCellValue("SALE3YR");

		for (ProcurementParts allbranch : procurementpartslist) {

			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getYear());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getCapa());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getOrdertype());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getUnitsinstock());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(allbranch.getUnitsonorder());

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(allbranch.getReorderlevel());

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(allbranch.getSafetyquantity());

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(allbranch.getQuantitytoorder().doubleValue());

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(allbranch.getDpinumber());

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(allbranch.getKeystonenumber());

			Cell cell14 = row.createCell(14);
			cell14.setCellValue(allbranch.getActualprice().doubleValue());

			Cell cell15 = row.createCell(15);
			cell15.setCellValue(allbranch.getCostprice().doubleValue());

			Cell cell16 = row.createCell(16);
			cell16.setCellValue(allbranch.getPercent().doubleValue());

			Cell cell17 = row.createCell(17);
			cell17.setCellValue(allbranch.getSales1yearback().doubleValue());

			Cell cell18 = row.createCell(18);
			cell18.setCellValue(allbranch.getSales2yearback().doubleValue());

			Cell cell19 = row.createCell(19);
			cell19.setCellValue(allbranch.getSales3yearback().doubleValue());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;
	}

	public File createSalesAnalysisFile(List<CategoryBySalesAnalysis> categorysaleslist, String filePath,
			String subcategoryselected) throws IOException {

		File file = new File(filePath + "salesanalysis_" + subcategoryselected + ".xls");
		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("salesanalysis");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("TOTALSOLD");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("UNITSINSTOCK");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("UNITSONORDER");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("REORDER");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("BUY");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("SELL");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("PERCENT");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("TYPE");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("PLNO");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("YEARFROM");

		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("YEARTO");

		for (CategoryBySalesAnalysis allbranch : categorysaleslist) {
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getTotalsold().doubleValue());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getUnitsinstock());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getUnitsonorder());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getReorderlevel());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(allbranch.getBuyingprice().doubleValue());

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(allbranch.getSellingprice().doubleValue());

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(allbranch.getPercent().doubleValue());

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(allbranch.getOrdertype());

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(allbranch.getKeystonenumber());

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(allbranch.getYearfrom());

			Cell cell14 = row.createCell(14);
			cell14.setCellValue(allbranch.getYearto());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

	public File createSubCategoryAllBranchFile(List<SubCatAllBranch> subcatallbranchlist, String filePath,
			String subcategory) throws IOException {

		File file = new File(filePath + "SubcategoryBranch_" + subcategory + ".xls");
		int rownum = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("SubcategoryBranch");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("CHI");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("GRS");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("AM");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("TOTAL");

		for (SubCatAllBranch allbranch : subcatallbranchlist) {
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getChicagoquantity().doubleValue());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getGrandrapidsquantity().doubleValue());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getMelrosequantity().doubleValue());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getTotal().doubleValue());
		}

		FileOutputStream out = new FileOutputStream(file);

		workbook.write(out);
		out.flush();
		out.close();
		return file;
	}

	public File createVendorPriceExcelFile(List<CategoryBySalesAnalysis> categorysaleslist, String filePath)
			throws IOException {

		File file = new File(filePath + "vendorprice.xls");
		int rownum = 1;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("salesanalysis");
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("TOTALSOLD");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("UNITSINSTOCK");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("UNITSONORDER");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("REORDER");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("BUY");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("SELL");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("PERCENT");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("TYPE");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("1M");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("3M");

		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("6M");

		Cell cellh15 = rowHeader.createCell(15);
		cellh15.setCellValue("12M");

		for (CategoryBySalesAnalysis allbranch : categorysaleslist) {
			int vendorpriceindex = 16;
			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getTotalsold().doubleValue());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getUnitsinstock());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getUnitsonorder());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getReorderlevel());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(allbranch.getBuyingprice().doubleValue());

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(allbranch.getSellingprice().doubleValue());

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(allbranch.getPercent().doubleValue());

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(allbranch.getOrdertype());

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(allbranch.getM1());

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(allbranch.getM3());

			Cell cell14 = row.createCell(14);
			cell14.setCellValue(allbranch.getM6());

			Cell cell15 = row.createCell(15);
			cell15.setCellValue(allbranch.getM12());

			List<String> vendorpricelist = allbranch.getVendorpriceslist();
			if (vendorpricelist != null) {
				if (vendorpricelist.size() > 0) {
					for (String vendorstring : vendorpricelist) {

						String[] priceinformation = vendorstring.split("-");

						Cell cellA = row.createCell(vendorpriceindex++);
						cellA.setCellValue(priceinformation[0]);

						Cell cellB = row.createCell(vendorpriceindex++);
						cellB.setCellValue(priceinformation[1]);

						/*
						 * Cell cellC = row.createCell(vendorpriceindex++);
						 * cellC.setCellValue(priceinformation[2]);
						 */

						Cell cellD = row.createCell(vendorpriceindex++);
						cellD.setCellValue(priceinformation[3]);
					}
				}
			}

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

	public File createVendorSalesExcelFile(List<VendorSalesHelper> vendorsaleslist, String filePath)
			throws IOException {

		LOGGER.info("sixf:" + vendorsaleslist.size());

		File file = new File(filePath + "vendorsales.xls");
		int rownum = 1;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("vendorsales1");
		CreationHelper creationHelper = workbook.getCreationHelper();
		Row rowHeader = sheet.createRow(0);

		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("PARTNO");

		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("MAKE");

		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("MODEL");

		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("PARTDESCRIPTION");

		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("1M");

		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("2M");

		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("3M");

		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("12M");

		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("ON_ORDER");

		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("STOCK");

		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("VENDOR");

		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue("ORDER NO");

		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("CONTAINER");

		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("ORDER DATE");

		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("VENDOR PRICE");

		for (VendorSalesHelper allbranch : vendorsaleslist) {

			Row row = sheet.createRow(rownum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(allbranch.getPartno());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(allbranch.getManufacturername());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(allbranch.getMakemodelname());

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(allbranch.getPartdescription());

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(allbranch.getCh1m());

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(allbranch.getCh2m());

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(allbranch.getCh3m());

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(allbranch.getCh12m());

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(allbranch.getOrder());

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(allbranch.getStock());

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(allbranch.getSupplierid());

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(allbranch.getOrderno());

			Cell cell12 = row.createCell(12);
			cell12.setCellValue(allbranch.getContainerno());

			Cell cell13 = row.createCell(13);
			cell13.setCellValue(allbranch.getOrderdate());

			CellStyle style2 = workbook.createCellStyle();
			style2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));
			cell13.setCellStyle(style2);

			Cell cell14 = row.createCell(14);
			cell14.setCellValue(allbranch.getPrice().doubleValue());

		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
		return file;

	}

}
