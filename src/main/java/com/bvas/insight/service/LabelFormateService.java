package com.bvas.insight.service;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.inch;
import static net.sf.dynamicreports.report.builder.DynamicReports.margin;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bvas.insight.data.LabelFormate;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.MultiPageListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.Markup;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JREmptyDataSource;

@Repository
public class LabelFormateService {

	@Transactional
	public String formateLabelToWord(MultipartFile multipartFile, String appRepository) throws IOException {

		if (null != multipartFile && !multipartFile.isEmpty()) {
			List<LabelFormate> labelFormateLis = parseLabelFile(multipartFile);
			String fileName = appRepository + "//labelformate//" + new Date().getTime() + "_"
					+ multipartFile.getOriginalFilename().replace(".", "-") + ".docx";
			StyleBuilder leftStyle = stl.style().setMarkup(Markup.STYLED).setFontName("Times New Roman");// .setLeftPadding(inch(0.1)).setRightPadding(inch(0.15));//.setTopPadding(10);
			StyleBuilder rightStyle = stl.style().setMarkup(Markup.STYLED).setFontName("Times New Roman")
					.setLeftPadding(inch(0.15));// .setRightPadding(inch(0.1));//.setTopPadding(10);
			HorizontalListBuilder layout = cmp.horizontalList();
			MultiPageListBuilder multiPageList = cmp.multiPageList();
			int col = 1, row = 1;
			for (LabelFormate label : labelFormateLis) {
				if (col == 3) {
					// break;
					multiPageList.add(layout);
					row++;
					if (row == 6) {
						multiPageList.add(cmp.pageBreak());
						row = 1;
					}
					layout = cmp.horizontalList();
					col = 1;
				}
				if (col == 1) {
					layout.add(cmp.text(label.getReportContents()).setStyle(leftStyle).setWidth(inch(4))
							.setHeight(inch(2)));
				} else {
					layout.add(cmp.text(label.getReportContents()).setStyle(rightStyle).setWidth(inch(4))
							.setHeight(inch(2)));
				}
				col++;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			try {
				JasperReportBuilder report = report();

				report.detail(multiPageList).setDataSource(new JREmptyDataSource(1)).setPageFormat(PageType.LETTER)
						.setPageMargin(margin().setTop(inch(0.6)).setLeft(inch(0.2)));
				report.toDocx(fileOutputStream);
				return fileName;
			} catch (DRException e) {
				e.printStackTrace();
			} finally {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		return "";

	}

	private List<LabelFormate> parseLabelFile(MultipartFile multipartFile) throws IOException {

		List<LabelFormate> labelFormateList = new ArrayList<LabelFormate>();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
		String line;
		Workbook wb;
		if (multipartFile.getContentType().equals("text/plain")) {
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().length() == 0) {
					continue;
				}
				String[] values = line.split("\\t", -1);
				String alsoFit[] = null;
				LabelFormate label = new LabelFormate();
				if (values != null && values.length > 0) {
					int noOfVal = values.length;
					label.setLeftRight(noOfVal > 0 ? values[0] : "");
					label.setBvNo(noOfVal > 1 ? values[1] : "");
					// unknown1 = noOfVal > 2 ? values[2] : "";
					label.setPlNo(noOfVal > 3 ? values[3] : "");
					label.setYear(noOfVal > 4 ? values[4] : "");
					label.setModel(noOfVal > 5 ? values[5] : "");
					label.setPartDesc(noOfVal > 6 ? values[6] : "");

					// emptySpace = noOfVal > 12 ? values[noOfVal - 1] : "";
					label.setVdInvNo(noOfVal > 11 ? values[noOfVal - 2] : "");
					label.setConNo(noOfVal > 10 ? values[noOfVal - 3] : "");
					label.setLocation(noOfVal > 9 ? values[noOfVal - 4] : "");
					label.setReplacement(noOfVal > 8 ? values[noOfVal - 5] : "");
					label.setPartNo(noOfVal > 7 ? values[noOfVal - 6] : "");
					if (noOfVal > 13) {
						alsoFit = new String[noOfVal - 13];
						for (int i = 0; i < noOfVal - 13; i++) {
							alsoFit[i] = values[(7 + i)];
						}
					}
					label.setAlsoFit(alsoFit);
					labelFormateList.add(label);
				}
			}
		} else {
			if (multipartFile.getContentType().equals("application/vnd.ms-excel")) {
				wb = new HSSFWorkbook(multipartFile.getInputStream());
			} else if (multipartFile.getContentType()
					.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				wb = new XSSFWorkbook(multipartFile.getInputStream());
			} else {
				wb = null;
			}

			try {
				// POIFSFileSystem fs = new
				// POIFSFileSystem(multipartFile.getInputStream());
				Sheet sheet = wb.getSheetAt(0);
				Row row;

				int rows; // No of rows
				rows = sheet.getPhysicalNumberOfRows();

				int cols = 0; // No of columns
				int tmp = 0;

				for (int i = 0; i < 10 || i < rows; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						tmp = sheet.getRow(i).getPhysicalNumberOfCells();
						if (tmp > cols) {
							cols = tmp;
						}
					}
				}

				DataFormatter formatter = new DataFormatter();
				for (int r = 0; r < rows; r++) {
					row = sheet.getRow(r);
					LabelFormate label = new LabelFormate();
					String alsoFit[] = null;
					if (row != null) {
						label.setLeftRight(row.getCell(0) == null ? ""
								: formatter.formatCellValue(row.getCell(0, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setBvNo(row.getCell(1) == null ? ""
								: formatter.formatCellValue(row.getCell(1, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setPlNo(row.getCell(3) == null ? ""
								: formatter.formatCellValue(row.getCell(3, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setYear(row.getCell(4) == null ? ""
								: formatter.formatCellValue(row.getCell(4, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setModel(row.getCell(5) == null ? ""
								: formatter.formatCellValue(row.getCell(5, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setPartDesc(row.getCell(6) == null ? ""
								: formatter.formatCellValue(row.getCell(6, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());

						label.setVdInvNo(row.getCell(13) == null ? ""
								: formatter.formatCellValue(row.getCell(13, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setConNo(row.getCell(12) == null ? ""
								: formatter.formatCellValue(row.getCell(12, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setLocation(row.getCell(11) == null ? ""
								: formatter.formatCellValue(row.getCell(11, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setReplacement(row.getCell(10) == null ? ""
								: formatter.formatCellValue(row.getCell(10, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						label.setPartNo(row.getCell(9) == null ? ""
								: formatter.formatCellValue(row.getCell(9, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim());
						// if (cols > 13) {
						// alsoFit = new String[cols - 13];
						// for (int i = 0; i < cols - 13; i++) {
						// alsoFit[i] = row.getCell(7 + i).getStringCellValue();
						// }
						// }
						alsoFit = new String[2];
						alsoFit[0] = row.getCell(7) == null ? ""
								: formatter.formatCellValue(row.getCell(7, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim();
						alsoFit[1] = row.getCell(8) == null ? ""
								: formatter.formatCellValue(row.getCell(8, Row.RETURN_BLANK_AS_NULL))
										.replace("null", "").trim();
						label.setAlsoFit(alsoFit);
						labelFormateList.add(label);

					}
				}
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
		}
		///
		return labelFormateList;
	}

}
