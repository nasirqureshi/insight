package com.bvas.insight.service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bvas.insight.data.ReorderLevelData;
import com.bvas.insight.data.ReorderLevelDataConsolidated;
import com.bvas.insight.data.ReorderLevelFilter;
import com.bvas.insight.jdbc.ReorderLevelDaoImpl;

@Service("reorderLevelService")
@SuppressWarnings("rawtypes")
public class ReorderLevelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReorderLevelService.class);

	@Autowired
	ReorderLevelDaoImpl reorderLevelDao;

	/**
	 * Return Consolidated sheet of location-wise sheet based on partNo
	 *
	 * @param dataList
	 * @param filterData
	 * @param workbook
	 * @throws IOException
	 */
	private void createConsolidatedSheet(List<ReorderLevelDataConsolidated> dataList, ReorderLevelFilter filterData,
			HSSFWorkbook workbook) throws IOException {

		HSSFSheet sheet = workbook.createSheet("Consolidated based on partNo");
		Row rowHeader = sheet.createRow(0);
		SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM");

		// <editor-fold defaultstate="collapsed" desc="Header Row Columns">
		// A
		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("From Date");
		// B
		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("To Date");
		// C
		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("Target From");
		// D
		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("Target To");
		// E
		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("Part No");
		// F
		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("Last Order Date");
		// G
		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("Qty Ordered After Snapshot");
		// H
		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("Growth Rate - " + dateFormatForMonth.format(filterData.getTargetCycleStartDate()));
		// I
		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("Growth Rate - " + dateFormatForMonth.format(filterData.getTargetCycleEndDate()));
		// cellh9.setCellValue("Growth Rate Month2");
		// J
		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("SQ (Avg Monthly Qty Last Year)");
		// K
		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue(
				"Reorderlevel " + dateFormatForMonth.format(filterData.getTargetCycleStartDate()) + "/TotalMonthDays");
		// L
		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue(
				"Reorderlevel " + dateFormatForMonth.format(filterData.getTargetCycleEndDate()) + "/TotalMonthDays");
		// M
		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue("Min. Qty On Needed  To Survive Next 45 Days");
		// N
		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("Target Cycle Reorder Level");
		// O
		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("Excess Qty");
		// P
		Cell cellh15 = rowHeader.createCell(15);
		cellh15.setCellValue("Adjusted Reorder");
		// Q
		Cell cellh16 = rowHeader.createCell(16);
		cellh16.setCellValue("Qty Instock");

		Cell cellh17 = rowHeader.createCell(17);
		cellh17.setCellValue("Qty Onorder");

		Cell cellh18 = rowHeader.createCell(18);
		cellh18.setCellValue("Qty In-Transit");

		Cell cellh19 = rowHeader.createCell(19);
		cellh19.setCellValue("Qty To Order");

		// </editor-fold>
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		int rownum = 1;
		for (ReorderLevelDataConsolidated data : dataList) {
			BigDecimal qtyToOrder = data.getQtyToOrder();
			if (qtyToOrder.compareTo(filterData.getQtyToOrder()) == -1) {
				continue;
			}
			Row row = sheet.createRow(rownum++);
			// From Date
			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(df.format(data.getCurrentCycleStartDate()));
			// To Date
			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(df.format(data.getCurrentCycleEndDate()));
			// Target From
			Cell cell_2 = row.createCell(2);
			cell_2.setCellValue(df.format(data.getTargetCycleStartDate()));
			// Target To
			Cell cell_3 = row.createCell(3);
			cell_3.setCellValue(df.format(data.getTargetCycleEndDate()));
			// Part No
			Cell cell_4 = row.createCell(4);
			cell_4.setCellValue(data.getPartNo());
			// Last Order Date
			Cell cell_5 = row.createCell(5);
			if (data.getLastOrderDate() == null) {
				cell_5.setCellValue("");
			} else {
				cell_5.setCellValue(df.format(data.getLastOrderDate()));
			}
			// Qty Ordered After Snapshot
			Cell cell_6 = row.createCell(6);
			cell_6.setCellValue(data.getQtyOrderedAfterSnapshot());
			// Growth Rate Month1
			Cell cell_7 = row.createCell(7);
			cell_7.setCellValue(
					(data.getTargetCycleMonth1GrowthRate() != null ? data.getTargetCycleMonth1GrowthRate().doubleValue()
							: 0));
			// Growth Rate Month2
			Cell cell_8 = row.createCell(8);
			cell_8.setCellValue(
					(data.getTargetCycleMonth2GrowthRate() != null ? data.getTargetCycleMonth2GrowthRate().doubleValue()
							: 0));
			// SQ (Avg Monthly Qty Last Year)
			Cell cell_9 = row.createCell(9);
			cell_9.setCellValue((data.getSafetyQty() != null ? data.getSafetyQty().doubleValue() : 0));
			// Reorderlevel Month1/TotalMonth1Days = daily Reorder M1
			Cell cell_10 = row.createCell(10);
			BigDecimal reorderTargetCycleMonth1Daily = data.getReorderTargetCycleMonth1Daily();
			cell_10.setCellValue(
					(reorderTargetCycleMonth1Daily != null ? reorderTargetCycleMonth1Daily.doubleValue() : 0));
			// Reorderlevel Month2/TotalMonthDays = daily Reorder M2
			Cell cell_11 = row.createCell(11);
			BigDecimal reorderTargetCycleMonth2Daily = data.getReorderTargetCycleMonth2Daily();
			cell_11.setCellValue(
					(reorderTargetCycleMonth2Daily != null ? reorderTargetCycleMonth2Daily.doubleValue() : 0));
			// Min. Qty On Needed To Survive Next 45 Days
			Cell cell_12 = row.createCell(12);//// ReOrdr1+(ReOrder2/31)*15
			BigDecimal minimumQtyToSurviveNext45Days = data.getMinimumQtyToSurviveNext45Days();
			cell_12.setCellValue(
					(minimumQtyToSurviveNext45Days != null ? minimumQtyToSurviveNext45Days.doubleValue() : 0));
			// Cycle Reorder Level
			Cell cell_13 = row.createCell(13);// (1 + DemandFactor/100) * MinQty45
			BigDecimal targetCycleReorder = data.getTargetCycleReorder();
			cell_13.setCellValue((targetCycleReorder != null ? targetCycleReorder.doubleValue() : 0));
			// Excess Qty
			Cell cell_14 = row.createCell(14);
			cell_14.setCellValue(decimalFormat.format(data.getExcessQty()));
			// Adjusted Reorder
			Cell cell_15 = row.createCell(15);
			BigDecimal adjustedReorder = data.getAdjustedReorder();
			cell_15.setCellValue((adjustedReorder != null ? adjustedReorder.doubleValue() : 0));
			// Qty Instock
			Cell cell_16 = row.createCell(16);
			cell_16.setCellValue((data.getInstockNow() != null ? data.getInstockNow() : 0));
			// Qty Onorder
			Cell cell_17 = row.createCell(17);
			cell_17.setCellValue((data.getOnorderNow() != null ? data.getOnorderNow() : 0));
			// Qty In-Transit
			Cell cell_18 = row.createCell(18);
			cell_18.setCellValue((data.getInTransitNow() != null ? data.getInTransitNow() : 0));
			// Qty To Order
			Cell cell_19 = row.createCell(19);
			cell_19.setCellValue(qtyToOrder.doubleValue());
		}
	}

	private List<ReorderLevelData> createLocationWiseSheet(List<ReorderLevelData> dataList,
			ReorderLevelFilter filterData, HSSFWorkbook workbook) throws IOException {

		HSSFSheet sheet = workbook.createSheet("Location-wise_Data");
		Row rowHeader = sheet.createRow(0);
		SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM");

		// <editor-fold defaultstate="collapsed" desc="Header Row Columns">
		// A
		Cell cellh0 = rowHeader.createCell(0);
		cellh0.setCellValue("From Date");
		// B
		Cell cellh1 = rowHeader.createCell(1);
		cellh1.setCellValue("To Date");
		// C
		Cell cellh2 = rowHeader.createCell(2);
		cellh2.setCellValue("Target From");
		// D
		Cell cellh3 = rowHeader.createCell(3);
		cellh3.setCellValue("Target To");
		// E
		Cell cellh4 = rowHeader.createCell(4);
		cellh4.setCellValue("Location");
		// F
		Cell cellh5 = rowHeader.createCell(5);
		cellh5.setCellValue("Part No");
		// G
		Cell cellh6 = rowHeader.createCell(6);
		cellh6.setCellValue("Last Order Date");
		// H
		Cell cellh7 = rowHeader.createCell(7);
		cellh7.setCellValue("Qty Ordered After Snapshot");
		// I
		Cell cellh8 = rowHeader.createCell(8);
		cellh8.setCellValue("Growth Rate - " + dateFormatForMonth.format(filterData.getTargetCycleStartDate()));
		// cellh8.setCellValue("Growth Rate Month1");
		// J
		Cell cellh9 = rowHeader.createCell(9);
		cellh9.setCellValue("Growth Rate - " + dateFormatForMonth.format(filterData.getTargetCycleEndDate()));
		// cellh9.setCellValue("Growth Rate Month2");
		// K
		Cell cellh10 = rowHeader.createCell(10);
		cellh10.setCellValue("SQ (Avg Monthly Qty Last Year)");
		// L
		Cell cellh11 = rowHeader.createCell(11);
		cellh11.setCellValue(
				"Reorderlevel " + dateFormatForMonth.format(filterData.getTargetCycleStartDate()) + "/TotalMonthDays");
		// M
		Cell cellh12 = rowHeader.createCell(12);
		cellh12.setCellValue(
				"Reorderlevel " + dateFormatForMonth.format(filterData.getTargetCycleEndDate()) + "/TotalMonthDays");
		// N
		Cell cellh13 = rowHeader.createCell(13);
		cellh13.setCellValue("Min. Qty On Needed  To Survive Next 45 Days");
		// O
		Cell cellh14 = rowHeader.createCell(14);
		cellh14.setCellValue("Target Cycle Reorder Level");
		// P
		Cell cellh15 = rowHeader.createCell(15);
		cellh15.setCellValue("Excess Qty");
		// Q
		Cell cellh16 = rowHeader.createCell(16);
		cellh16.setCellValue("Adjusted Reorder");
		// T
		Cell cellh17 = rowHeader.createCell(17);
		cellh17.setCellValue("Qty Instock");

		Cell cellh18 = rowHeader.createCell(18);
		cellh18.setCellValue("Qty Onorder");

		Cell cellh19 = rowHeader.createCell(19);
		cellh19.setCellValue("Qty In-Transit");

		Cell cellh20 = rowHeader.createCell(20);
		cellh20.setCellValue("Qty To Order");

		// </editor-fold>
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		int rownum = 1;
		List<ReorderLevelData> consideredList = new ArrayList<>();
		for (ReorderLevelData data : dataList) {
			BigDecimal qtyToOrder = data.getQtyToOrder();

			if (qtyToOrder.compareTo(BigDecimal.ZERO) != 1 || qtyToOrder.compareTo(filterData.getQtyToOrder()) == -1) {
				// LOGGER.info("skiped: "+data.getPartNo()+" :qty="+qtyToOrder);
				continue;
			} else {
				consideredList.add(data);
			}
			Row row = sheet.createRow(rownum++);
			// From Date
			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(df.format(data.getCurrentCycleStartDate()));
			// To Date
			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(df.format(data.getCurrentCycleEndDate()));
			// Target From
			Cell cell_2 = row.createCell(2);
			cell_2.setCellValue(df.format(data.getTargetCycleStartDate()));
			// Target To
			Cell cell_3 = row.createCell(3);
			cell_3.setCellValue(df.format(data.getTargetCycleEndDate()));
			// Location
			Cell cell_4 = row.createCell(4);
			cell_4.setCellValue(data.getLocation());
			// Part No
			Cell cell_5 = row.createCell(5);
			cell_5.setCellValue(data.getPartNo());
			// Last Order Date
			Cell cell_6 = row.createCell(6);
			if (data.getLastOrderDate() == null) {
				cell_6.setCellValue("");
			} else {
				cell_6.setCellValue(df.format(data.getLastOrderDate()));
			}

			// Qty Ordered After Snapshot
			Cell cell_7 = row.createCell(7);
			cell_7.setCellValue(data.getQtyOrderedAfterSnapshot());
			// Growth Rate Month1
			Cell cell_8 = row.createCell(8);
			cell_8.setCellValue(
					(data.getTargetCycleMonth1GrowthRate() != null ? data.getTargetCycleMonth1GrowthRate().doubleValue()
							: 0));
			// Growth Rate Month2
			Cell cell_9 = row.createCell(9);
			cell_9.setCellValue(
					(data.getTargetCycleMonth2GrowthRate() != null ? data.getTargetCycleMonth2GrowthRate().doubleValue()
							: 0));
			// SQ (Avg Monthly Qty Last Year)
			Cell cell_10 = row.createCell(10);
			cell_10.setCellValue((data.getSafetyQty() != null ? data.getSafetyQty().doubleValue() : 0));
			// Reorderlevel Month1/TotalMonth1Days = daily Reorder M1
			Cell cell_11 = row.createCell(11);
			BigDecimal reorderTargetCycleMonth1Daily = data.getReorderTargetCycleMonth1Daily();
			cell_11.setCellValue(
					(reorderTargetCycleMonth1Daily != null ? reorderTargetCycleMonth1Daily.doubleValue() : 0));
			// Reorderlevel Month2/TotalMonthDays = daily Reorder M2
			Cell cell_12 = row.createCell(12);
			BigDecimal reorderTargetCycleMonth2Daily = data.getReorderTargetCycleMonth2Daily();
			cell_12.setCellValue(
					(reorderTargetCycleMonth2Daily != null ? reorderTargetCycleMonth2Daily.doubleValue() : 0));
			// Min. Qty On Needed To Survive Next 45 Days
			Cell cell_13 = row.createCell(13);//// ReOrdr1+(ReOrder2/31)*15
			BigDecimal minimumQtyToSurviveNext45Days = data.getMinimumQtyToSurviveNext45Days();
			cell_13.setCellValue(
					(minimumQtyToSurviveNext45Days != null ? minimumQtyToSurviveNext45Days.doubleValue() : 0));
			// Cycle Reorder Level
			Cell cell_14 = row.createCell(14);// (1 + DemandFactor/100) * MinQty45
			BigDecimal targetCycleReorder = data.getTargetCycleReorder();
			cell_14.setCellValue((targetCycleReorder != null ? targetCycleReorder.doubleValue() : 0));
			// Excess Qty
			Cell cell_15 = row.createCell(15);
			cell_15.setCellValue(decimalFormat.format(data.getExcessQty()));
			// Adjusted Reorder
			Cell cell_16 = row.createCell(16);
			BigDecimal adjustedReorder = data.getAdjustedReorder();
			cell_16.setCellValue((adjustedReorder != null ? adjustedReorder.doubleValue() : 0));
			// Qty Instock
			Cell cell_17 = row.createCell(17);
			cell_17.setCellValue((data.getInstockNow() != null ? data.getInstockNow() : 0));
			// Qty Onorder
			Cell cell_18 = row.createCell(18);
			cell_18.setCellValue((data.getOnorderNow() != null ? data.getOnorderNow() : 0));
			// Qty in-Transit
			Cell cell_19 = row.createCell(19);
			cell_19.setCellValue((data.getInTransitNow() != null ? data.getInTransitNow() : 0));
			// Qty To Order
			Cell cell_20 = row.createCell(20);
			cell_20.setCellValue(qtyToOrder.doubleValue());
		}
		return consideredList;
	}

	private List<ReorderLevelDataConsolidated> getConsolidatedReorderLevelData(List<ReorderLevelData> dataList) {
		HashMap<String, ReorderLevelDataConsolidated> hashMap = new HashMap<>();
		for (ReorderLevelData data : dataList) {
			if (!hashMap.containsKey(data.getPartNo())) {
				ReorderLevelDataConsolidated newData = new ReorderLevelDataConsolidated();
				newData.mergeData(data);
				// ReorderLevelData newData = (ReorderLevelData) data.clone();
				hashMap.put(data.getPartNo(), newData);
			} else {
				hashMap.get(data.getPartNo()).mergeData(data);
			}
		}
		List<ReorderLevelDataConsolidated> list = new ArrayList<>(hashMap.values());
		return list;
	}

	public ReorderLevelFilter getCycleInfo() {
		return reorderLevelDao.getCycleInfo();
	}

	public void getReorderLevelQtyToOrderData(ReorderLevelFilter filterData, OutputStream outStream) {
		// SimpleDateFormat df=new SimpleDateFormat("h:m:s:S");
		List<ReorderLevelData> dataList = reorderLevelDao.getReorderLevelQtyToOrderData(filterData);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			List<ReorderLevelData> cosideredDataList = createLocationWiseSheet(dataList, filterData, workbook);
			List<ReorderLevelDataConsolidated> consolidatedDataList = getConsolidatedReorderLevelData(
					cosideredDataList);
			createConsolidatedSheet(consolidatedDataList, filterData, workbook);
			workbook.write(outStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// return data;
	}

	// <editor-fold defaultstate="collapsed" desc=" commented code -formula in
	// excel">
	/*
	 * 
	 * public void getReorderLevelQtyToOrderData(ReorderLevelFilter
	 * filterData,OutputStream outStream) { List<ReorderLevelData> data =
	 * reorderLevelDao.getReorderLevelQtyToOrderData(filterData); Calendar c1 =
	 * Calendar.getInstance(); c1.setTime(filterData.getTargetCycleStartDate()); int
	 * cycle2M1StartDay = c1.get(Calendar.DAY_OF_MONTH); int cycle2M1EndDay =
	 * c1.getActualMaximum(Calendar.DAY_OF_MONTH); int cycle2M1Days=
	 * (cycle2M1EndDay-cycle2M1StartDay)+1;
	 * 
	 * Calendar c2 = Calendar.getInstance();
	 * c2.setTime(filterData.getTargetCycleEndDate()); int cycle2M2Days =
	 * c2.get(Calendar.DAY_OF_MONTH);
	 * 
	 * //LOGGER.info("c2m1="+cycle2M1Days+" : c2m2="+cycle2M2Days);
	 * 
	 * try { createVendorPriceExcelFile(data,filterData,cycle2M1Days,cycle2M2Days,
	 * outStream); } catch (IOException ex) { ex.printStackTrace(); } //return data;
	 * }
	 * 
	 * private void createVendorPriceExcelFile(List<ReorderLevelData>
	 * dataList,ReorderLevelFilter filterData,int month1Days, int
	 * month2Days,OutputStream outStream) throws IOException {
	 * 
	 * HSSFWorkbook workbook = new HSSFWorkbook(); HSSFSheet sheet =
	 * workbook.createSheet("Location-wise_Data"); Row rowHeader =
	 * sheet.createRow(0); SimpleDateFormat dateFormatForMonth = new
	 * SimpleDateFormat("MMM");
	 * 
	 * // editor-fold defaultstate="collapsed" desc="Header Row Columns"> //A Cell
	 * cellh0 = rowHeader.createCell(0); cellh0.setCellValue("From Date"); //B Cell
	 * cellh1 = rowHeader.createCell(1); cellh1.setCellValue("To Date"); //C Cell
	 * cellh2 = rowHeader.createCell(2); cellh2.setCellValue("Target From"); //D
	 * Cell cellh3 = rowHeader.createCell(3); cellh3.setCellValue("Target To"); //E
	 * Cell cellh4 = rowHeader.createCell(4); cellh4.setCellValue("Location"); //F
	 * Cell cellh5 = rowHeader.createCell(5); cellh5.setCellValue("Part No"); //G
	 * Cell cellh6 = rowHeader.createCell(6);
	 * cellh6.setCellValue("Last Order Date"); //H Cell cellh7 =
	 * rowHeader.createCell(7); cellh7.setCellValue("Qty Ordered After Snapshot");
	 * //I //Cell cellh8 = rowHeader.createCell(8);
	 * //cellh8.setCellValue("Qty Ordered After Snapshot"); //I Cell cellh8 =
	 * rowHeader.createCell(8);
	 * cellh8.setCellValue("Growth Rate - "+dateFormatForMonth.format(filterData.
	 * getTargetCycleStartDate())); //cellh8.setCellValue("Growth Rate Month1"); //J
	 * Cell cellh9 = rowHeader.createCell(9);
	 * cellh9.setCellValue("Growth Rate - "+dateFormatForMonth.format(filterData.
	 * getTargetCycleEndDate())); //cellh9.setCellValue("Growth Rate Month2"); //K
	 * Cell cellh10 = rowHeader.createCell(10);
	 * cellh10.setCellValue("SQ Multiplier"); //L Cell cellh11 =
	 * rowHeader.createCell(11);
	 * cellh11.setCellValue("SQ (Avg Monthly Qty Last Year)"); //M Cell cellh12 =
	 * rowHeader.createCell(12);
	 * cellh12.setCellValue("Reorderlevel "+dateFormatForMonth.format(filterData.
	 * getTargetCycleStartDate())+"/TotalMonthDays"); //N Cell cellh13 =
	 * rowHeader.createCell(13);
	 * cellh13.setCellValue("Reorderlevel "+dateFormatForMonth.format(filterData.
	 * getTargetCycleEndDate())+"/TotalMonthDays"); //O Cell cellh14 =
	 * rowHeader.createCell(14);
	 * cellh14.setCellValue("Min. Qty On Needed  To Survive Next 45 Days"); //P Cell
	 * cellh15 = rowHeader.createCell(15); cellh15.setCellValue("Demand Factor");
	 * //Q Cell cellh16 = rowHeader.createCell(16);
	 * cellh16.setCellValue("Target Cycle Reorder Level"); //R Cell cellh17 =
	 * rowHeader.createCell(17); cellh17.setCellValue("Excess Qty"); //S Cell
	 * cellh18 = rowHeader.createCell(18); cellh18.setCellValue("Adjusted Reorder");
	 * //T Cell cellh19 = rowHeader.createCell(19);
	 * cellh19.setCellValue("Qty To Order");
	 * 
	 * //editor-fold> SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	 * DecimalFormat decimalFormat = new DecimalFormat("#.##"); int rownum = 1; for
	 * (ReorderLevelData data : dataList) {
	 * if(data.getQtyToOrder().compareTo(filterData.getQtyToOrder())==-1){ continue;
	 * } Row row = sheet.createRow(rownum++); //From Date Cell cell_0 =
	 * row.createCell(0);
	 * cell_0.setCellValue(df.format(data.getCurrentCycleStartDate())); //To Date
	 * Cell cell_1 = row.createCell(1);
	 * cell_1.setCellValue(df.format(data.getCurrentCycleEndDate())); //Target From
	 * Cell cell_2 = row.createCell(2);
	 * cell_2.setCellValue(df.format(data.getTargetCycleStartDate())); //Target To
	 * Cell cell_3 = row.createCell(3);
	 * cell_3.setCellValue(df.format(data.getTargetCycleEndDate())); //Location Cell
	 * cell_4 = row.createCell(4); cell_4.setCellValue(data.getLocation()); //Part
	 * No Cell cell_5 = row.createCell(5); cell_5.setCellValue(data.getPartNo());
	 * //Last Order Date Cell cell_6 = row.createCell(6);
	 * if(data.getLastOrderDate()==null){ cell_6.setCellValue(""); }else{
	 * cell_6.setCellValue(df.format(data.getLastOrderDate())); }
	 * 
	 * //Qty Ordered After Snapshot Cell cell_7 = row.createCell(7);
	 * cell_7.setCellValue(data.getQtyOrderedAfterSnapshot()); //Growth Rate Month1
	 * Cell cell_8 = row.createCell(8);
	 * cell_8.setCellValue((data.getTargetCycleMonth1GrowthRate()!=null?data.
	 * getTargetCycleMonth1GrowthRate().doubleValue():0)); //Growth Rate Month2 Cell
	 * cell_9 = row.createCell(9);
	 * cell_9.setCellValue((data.getTargetCycleMonth2GrowthRate()!=null?data.
	 * getTargetCycleMonth2GrowthRate().doubleValue():0)); //SQ Multiplier Cell
	 * cell_10 = row.createCell(10);
	 * cell_10.setCellValue((data.getTargetCycleSQMultiplier()!=null?data.
	 * getTargetCycleSQMultiplier().doubleValue():0)); //SQ (Avg Monthly Qty Last
	 * Year) Cell cell_11 = row.createCell(11);
	 * cell_11.setCellValue((data.getSafetyQty()!=null?data.getSafetyQty().
	 * doubleValue():0)); //Reorderlevel Month1/TotalMonth1Days = daily Reorder M1
	 * Cell cell_12 = row.createCell(12);
	 * //(IF(md1.GrowthRate<1,(md1.LastYearTargetMonthSale* (1+md1.GrowthRate)),
	 * (md1.LastYearTargetMonthSale+(SQ_Multiplier*md1.avgMonthlyQtyLastYear))))/md1
	 * .LastYearTargetMonthDays String lastYrTargetMonth1Sale =
	 * decimalFormat.format(data.getTargetCycleMonth1Sale().setScale(2,BigDecimal.
	 * ROUND_HALF_UP)); String lastYrTargetMonth1GR =
	 * decimalFormat.format(data.getTargetCycleMonth1GrowthRate().setScale(2,
	 * BigDecimal.ROUND_HALF_UP)); String strFormula=
	 * "(IF(I"+(rownum)+"<1,("+lastYrTargetMonth1Sale+"*(1+"+lastYrTargetMonth1GR+
	 * ")),"+lastYrTargetMonth1Sale+"+(K"+(rownum)+"*L"+(rownum)+")))/"+data.
	 * getTargetCycleMonth1TotalDays();
	 * cell_12.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	 * cell_12.setCellFormula(strFormula);
	 * 
	 * //Reorderlevel Month2/TotalMonthDays = daily Reorder M2 Cell cell_13 =
	 * row.createCell(13); String lastYrTargetMonth2Sale =
	 * decimalFormat.format(data.getTargetCycleMonth2Sale().setScale(2,BigDecimal.
	 * ROUND_HALF_UP)); String lastYrTargetMonth2GR =
	 * decimalFormat.format(data.getTargetCycleMonth2GrowthRate().setScale(2,
	 * BigDecimal.ROUND_HALF_UP)); strFormula=
	 * "(IF(J"+(rownum)+"<1,("+lastYrTargetMonth2Sale+"*(1+"+lastYrTargetMonth2GR+
	 * ")),"+lastYrTargetMonth2Sale+"+(K"+(rownum)+"*L"+(rownum)+")))/"+data.
	 * getTargetCycleMonth2TotalDays();
	 * cell_13.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	 * cell_13.setCellFormula(strFormula); //Min. Qty On Needed To Survive Next 45
	 * Days Cell cell_14 = row.createCell(14);////ReOrdr1+(ReOrder2/31)*15
	 * strFormula= "(M"+(rownum)+"*"+month1Days+")+(N"+(rownum)+"*"+month2Days+")";
	 * cell_14.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	 * cell_14.setCellFormula(strFormula);
	 * 
	 * //Demand Factor Cell cell_15 = row.createCell(15);
	 * cell_15.setCellValue((data.getTargetCycleDemandFactor()!=null?data.
	 * getTargetCycleDemandFactor().doubleValue():0)); //Cycle Reorder Level Cell
	 * cell_16 = row.createCell(16);//(1 + DemandFactor/100) * MinQty45 strFormula=
	 * "(1+P"+(rownum)+"/100)*O"+(rownum);
	 * cell_16.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	 * cell_16.setCellFormula(strFormula); //Excess Qty Cell cell_17 =
	 * row.createCell(17);
	 * cell_17.setCellValue(decimalFormat.format(data.getExcessQty())); //Adjusted
	 * Reorder Cell cell_18 = row.createCell(18); strFormula=
	 * "Q"+(rownum)+"-R"+(rownum); cell_18.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	 * cell_18.setCellFormula(strFormula); //Qty To Order Cell cell_19 =
	 * row.createCell(19); //If (Adj. Re Order Level - Qty Ordered >20) Then Order
	 * 25% of Adj. Reorder Level Else Adj. ReOrderLevel - QtyOrdered strFormula=
	 * "IF(S"+(rownum)+"-H"+rownum+">20,(0.25*S"+rownum+"),(S"+rownum+"-H"+(rownum)+
	 * "))"; cell_19.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	 * cell_19.setCellFormula(strFormula);
	 * 
	 * 
	 * }
	 * 
	 * //FileOutputStream out = new FileOutputStream(file);
	 * workbook.write(outStream); }
	 */
	// </editor-fold>
}
