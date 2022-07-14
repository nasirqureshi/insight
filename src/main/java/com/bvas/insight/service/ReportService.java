package com.bvas.insight.service;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.PaymentMaintenance;
import com.bvas.insight.data.ReportLayout;
import com.bvas.insight.data.StockAuditData;
import com.bvas.insight.entity.AppliedAmounts;
import com.bvas.insight.entity.BouncedChecks;
import com.bvas.insight.entity.Invoice;
import com.bvas.insight.entity.StockAudit;
import com.bvas.insight.entity.Writeoff;
import com.bvas.insight.jdbc.SalesReportDaoImpl;
import com.bvas.insight.utilities.InsightUtils;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service("reportService")
@SuppressWarnings("rawtypes")
public class ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	SalesReportDaoImpl saleRptDao;

	@Autowired
	private SessionFactory sessionFactory;

	private AbstractColumn createColumn(String property, Class type, String title, int width, Style headerStyle,
			Style detailStyle) throws ColumnBuilderException {

		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty(property, type.getName()).setTitle(title)
				.setWidth(Integer.valueOf(width)).setStyle(detailStyle).setHeaderStyle(headerStyle).build();
		return columnState;
	}

	private Style createDetailNumberStyle() {

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM);
		// sb.setBorder(Border.NO_BORDER());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.RIGHT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingRight(5);
		return sb.build();
	}

	private Style createDetailTextStyle() {

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM);
		// sb.setBorder(Border.NO_BORDER);
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.LEFT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingLeft(5);
		return sb.build();
	}

	private Style createHeaderStyle() {

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM_BOLD);
		// sb.setBorder(Border.THIN);
		// sb.setBorderBottom(Border.PEN_2_POINT);
		sb.setBorderColor(Color.BLACK);
		sb.setBackgroundColor(Color.gray);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.CENTER);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}

	public String EAChecks(HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Todays Checks report");

		JasperPrint jp = getChecksReport();
		String fileName = appRepository + "//finance//checks//f_checks_" + InsightUtils.getNewUSDate() + ".pdf";

		ReportLayout.exportToPdf(jp, response, fileName);
		return fileName;

	}

	public String FinStmtAdjustment(HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Enter Amount report");

		JasperPrint jp = getAdjReport();
		// Create the output byte stream where the data will be written
		// //ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fileName = appRepository + "//finance//adjustments//f_adj_" + InsightUtils.getNewUSDate() + ".pdf";
		ReportLayout.exportToPdf(jp, response, fileName);
		return fileName;

	}

	public String FinStmtEnterAmount(HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Enter Amount report");
		JasperPrint jp = getEAReport();

		// //ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fileName = appRepository + "//finance//stmt//f_ea_" + InsightUtils.getNewUSDate() + ".pdf";
		ReportLayout.exportToPdf(jp, response, fileName);
		return fileName;

	}

	public String getAcctRecievable(String payable, HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Account Recievable/payable report");

		String fileName = "";
		JasperPrint jp = getAcctRecievableReport(payable);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (payable.equals("Recievable")) {
			fileName = appRepository + "//finance//acctRecievable//f_acctrecievable_" + InsightUtils.getNewUSDate()
					+ ".pdf";
			ReportLayout.exportToPdf(jp, response, fileName);
		} else if (payable.equals("Payable")) {
			fileName = appRepository + "//finance//acctrecievable//f_acctpayable_" + InsightUtils.getNewUSDate()
					+ ".pdf";
			ReportLayout.exportToPdf(jp, response, fileName);
		}
		return fileName;
	}

	public JasperPrint getAcctRecievableReport(String payable)
			throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		HashMap<String, Object> paramMap = null;
		if (payable.equals("Recievable")) {
			paramMap = accountService.getAcctRecievable();
		} else if (payable.equals("Payable")) {
			paramMap = accountService.getAcctPayable();
		}

		DynamicReport dynaReport = getAcctRecievableReport(payable, headerStyle, detailTextStyle, detailNumberStyle,
				paramMap);
		@SuppressWarnings("unchecked")
		List<BouncedChecks> nbclist = (List<BouncedChecks>) paramMap.get("ds");

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(nbclist));
		return jp;
	}

	private DynamicReport getAcctRecievableReport(String payable, Style headerStyle, Style detailTextStyle,
			Style detailNumStyle, HashMap params) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columnChkid = createColumn("customerId", String.class, "Customer Id", 15, headerStyle,
				detailTextStyle);
		AbstractColumn columnCust = createColumn("companyName", String.class, "Customer", 35, headerStyle,
				detailTextStyle);
		AbstractColumn columBal = createColumn("balance", BigDecimal.class, payable + " Amount", 10, headerStyle,
				detailNumStyle);

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailTextStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columnChkid).addColumn(columnCust).addColumn(columBal);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));
		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));

		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columBal, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Payments Receivable:");

		report.setTitle("Best Value Auto Body Supply Inc.");
		report.setTitleStyle(titleStyle.build());

		report.setSubtitleHeight(20);
		report.setSubtitle("ACCOUNTS " + payable.toUpperCase() + " REPORT AS ON " + InsightUtils.getNewUSDate());
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public JasperPrint getAdjReport() throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		DynamicReport dynaReport = getAdjReport(headerStyle, detailTextStyle, detailNumberStyle);
		List<PaymentMaintenance> nbcAdjlist = accountService.getNonBCAdjList();
		List<PaymentMaintenance> bcAdjlist = accountService.getBCAdjList();
		nbcAdjlist.addAll(bcAdjlist);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(nbcAdjlist));
		return jp;
	}

	private DynamicReport getAdjReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn columnInvNo = createColumn("invoiceNumber", String.class, "Invoice No", 10, headerStyle,
				detailNumStyle);
		AbstractColumn columnCust = createColumn("companyName", String.class, "Customer", 50, headerStyle,
				detailTextStyle);
		AbstractColumn columnChkAmt = createColumn("appliedAmount", BigDecimal.class, "Amount", 10, headerStyle,
				detailNumStyle);
		AbstractColumn columnPmtType = createColumn("paymentType", String.class, "Check No", 15, headerStyle,
				detailTextStyle);

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailNumStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columnInvNo).addColumn(columnCust).addColumn(columnPmtType)
				.addColumn(columnChkAmt);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));
		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true)).setTextColor(Color.red);
		// report.addGlobalFooterVariable(columnAplAmt,DJCalculation.SUM).setTitle("Total
		// Cash Amount");
		report.addGlobalFooterVariable(columnChkAmt, DJCalculation.COUNT, stStyle.build())
				.setTitle("Check Transaction");
		// report.addGlobalFooterVariable(columnChkAmt,DJCalculation.SUM).setTitle("Total
		// Cash Amount");

		report.setTitle("BEST VALUE Auto Body Supply, Inc.");
		report.setTitleStyle(titleStyle.build());

		report.setSubtitle("4425 W. 16Th St, CHICAGO, IL-60623 " + "\\n Phone: (773) 762-1000 Fax: (773) 542-5854"
				+ "\\nAdjustments For - " + InsightUtils.getNewUSDate()).setSubtitleHeight(20);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public String getAgeingInvoice(HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Ageng invoice report");

		JasperPrint jp = getAgeingInvoiceReport();
		// //ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fileName = appRepository + "//finance//ageing_" + InsightUtils.getNewUSDate() + "_.pdf";
		ReportLayout.exportToPdf(jp, response, fileName);

		return fileName;
	}

	@SuppressWarnings("unchecked")
	public JasperPrint getAgeingInvoiceReport() throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Map<String, Object> map = accountService.getAgeingInvoiceList();
		List<Map<String, Object>> ds = (List<Map<String, Object>>) map.get("ds");
		double totbalance = (Double) map.get("totbalance");
		double totCurr = (Double) map.get("totCurr");
		double tot30 = (Double) map.get("tot30");
		double tot60 = (Double) map.get("tot60");
		double tot90 = (Double) map.get("tot90");
		double totBC = (Double) map.get("totBC");
		DynamicReport dynaReport = getAgeingInvoiceReport(headerStyle, totbalance, totCurr, tot30, tot60, tot90, totBC);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(ds));
		return jp;
	}

	private DynamicReport getAgeingInvoiceReport(Style headerStyle, double totBalance, double totCurr, double tot30,
			double tot60, double tot90, double totBC) throws ColumnBuilderException, ClassNotFoundException {

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_SMALL);
		// sb.setBorder(Border.NO_BORDER);
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.RIGHT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingRight(5);

		StyleBuilder sbt = new StyleBuilder(true);
		sbt.setFont(Font.VERDANA_SMALL);
		// sbt.setBorder(Border.NO_BORDER);
		sbt.setBorderColor(Color.BLACK);
		sbt.setTextColor(Color.BLACK);
		sbt.setHorizontalAlign(HorizontalAlign.LEFT);
		sbt.setVerticalAlign(VerticalAlign.MIDDLE);
		sbt.setPaddingLeft(5);

		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columninvNo = createColumn("customerid", String.class, "Customer Id", 12, headerStyle,
				sbt.build());
		AbstractColumn columnCompanyName = createColumn("companyName", String.class, "Customer", 25, headerStyle,
				sbt.build());
		AbstractColumn columnbal = createColumn("balance", Double.class, "Total", 10, headerStyle, sb.build());
		AbstractColumn pterm = createColumn("paymentterms", String.class, "Terms", 8, headerStyle, sbt.build());
		AbstractColumn columncurr = createColumn("curr", Number.class, "current", 12, headerStyle, sb.build());
		AbstractColumn col30days = createColumn("30days", Number.class, "30Days", 12, headerStyle, sb.build());
		AbstractColumn col60days = createColumn("60days", Number.class, "60Days", 12, headerStyle, sb.build());
		AbstractColumn col90days = createColumn("90days", Number.class, "90Days", 12, headerStyle, sb.build());
		AbstractColumn colbc = createColumn("bc", Number.class, "BC Chks", 12, headerStyle, sb.build());

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(5).setStyle(sbt.build())
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columninvNo).addColumn(columnCompanyName).addColumn(columnbal).addColumn(pterm)
				.addColumn(columncurr).addColumn(col30days).addColumn(col60days).addColumn(col90days).addColumn(colbc);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));
		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));

		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(10, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columnbal, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Amount");

		report.setTitle("BEST VALUE Auto Body Supply, Inc.");
		report.setTitleStyle(titleStyle.build());
		DecimalFormat df = new DecimalFormat("#.##");
		report.setSubtitleHeight(40);
		report.setSubtitle("Total Payments Receivable: " + df.format(totBalance) + "\\n Current Payments:"
				+ df.format(totCurr) + "\\n % of Current:" + df.format(((totCurr * 100) / totBalance))
				+ "\\n Over 30 Days Payments:" + df.format(tot30) + "\\n % of 30 Days:"
				+ df.format(((tot30 * 100) / totBalance)) + "\\n Over 60 Days Payments:" + df.format(tot60)
				+ "\\n % of 60 Days:" + df.format(((tot60 * 100) / totBalance)) + "\\n Over 90 Days Payments:"
				+ df.format(tot90) + "\\n % of 90 Days:" + df.format(((tot90 * 100) / totBalance))
				+ "\\n Total Bounced Checks Amount:" + df.format(totBC));
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public JasperPrint getBCAcctStmt(String customerId)
			throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		HashMap<String, Object> paramMap = accountService.getAcctStmtDS(customerId);
		DynamicReport dynaReport = getBCAcctStmtReport(headerStyle, detailTextStyle, detailNumberStyle, paramMap);
		@SuppressWarnings("unchecked")
		List<Invoice> nbclist = (List<Invoice>) paramMap.get("ds");

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(nbclist));
		return jp;
	}

	public String getBCAcctStmt(String customerId, HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Account Statement report");

		JasperPrint jp = getBCAcctStmt(customerId);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fileName = appRepository + "//finance//bcacctstmt//f_bc_" + InsightUtils.getNewUSDate() + "_"
				+ customerId + ".pdf";
		ReportLayout.exportToPdf(jp, response, fileName);
		return fileName;
	}

	private DynamicReport getBCAcctStmtReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle,
			HashMap params) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columnChkid = createColumn("invoicenumber", Integer.class, "Invoice Number", 15, headerStyle,
				detailNumStyle);
		AbstractColumn columnCust = createColumn("orderdate", Date.class, "Order Date", 35, headerStyle,
				detailTextStyle);
		AbstractColumn columtotal = createColumn("invoiceTotal", BigDecimal.class, "Invoice Total", 10, headerStyle,
				detailNumStyle);
		AbstractColumn columBal = createColumn("balance", BigDecimal.class, "Amount Pending", 10, headerStyle,
				detailNumStyle);
		columnCust.setPattern("yyyy-MM-dd");
		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailTextStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columnChkid).addColumn(columnCust).addColumn(columtotal).addColumn(columBal);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));

		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));

		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columBal, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Pending Amount:");

		report.setTitle("BEST VALUE Auto Body Supply, Inc.\\n" + "4425 W. 16Th St, CHICAGO, IL-60623\\n"
				+ "Phone: (773) 762-1000 Fax: (773) 542-5854\\n" + "Statement of Account As of "
				+ InsightUtils.getNewUSDate());
		report.setTitleStyle(titleStyle.build());
		report.setTitleHeight(30);
		report.setSubtitleHeight(30);
		String address = params.get("customerAddres").toString();
		report.setSubtitle(address);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public JasperPrint getChecksReport() throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		HashMap<String, Object> paramMap = accountService.getTodaysChecks();
		DynamicReport dynaReport = getChecksReport(headerStyle, detailTextStyle, detailNumberStyle, paramMap);
		@SuppressWarnings("unchecked")
		List<AppliedAmounts> nbclist = (List<AppliedAmounts>) paramMap.get("ds");

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(nbclist));
		return jp;
	}

	private DynamicReport getChecksReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle,
			HashMap params) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columnChkAmt = createColumn("appliedAmount", BigDecimal.class, "Amount", 10, headerStyle,
				detailTextStyle);
		AbstractColumn columnPmtType = createColumn("paymentType", String.class, "Check No", 15, headerStyle,
				detailTextStyle);

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailTextStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columnPmtType).addColumn(columnChkAmt);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));
		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));

		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columnChkAmt, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Check Amount");

		report.setTitle("BEST VALUE Auto Body Supply, Inc.\\n Todays Checks");
		report.setTitleStyle(titleStyle.build());

		report.setSubtitleHeight(100);
		report.setSubtitle("Total No of Checks: " + params.get("totalCount") + "\\n" + "Total  Amount:"
				+ params.get("totAmount") + "\\n" + "Total Check Amount:" + params.get("totChkAmount") + "\\n"
				+ "Total Credit Card Amount" + params.get("totCCAmount") + "");
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public JasperPrint getEAReport() throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		DynamicReport dynaReport = getEAReport(headerStyle, detailTextStyle, detailNumberStyle);
		List<PaymentMaintenance> nbclist = accountService.getNonBCList();
		List<PaymentMaintenance> bclist = accountService.getBCList();
		for (PaymentMaintenance pm : bclist) {
			nbclist.add(pm);
		}
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(nbclist));
		return jp;
	}

	private DynamicReport getEAReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));

		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		AbstractColumn columnInvNo = createColumn("invoiceNumber", String.class, "Invoice No", 10, headerStyle,
				detailNumStyle);
		AbstractColumn columnCust = createColumn("companyName", String.class, "Customer", 50, headerStyle,
				detailTextStyle);
		AbstractColumn columnChkAmt = createColumn("appliedAmount", BigDecimal.class, "Amount", 10, headerStyle,
				detailNumStyle);
		AbstractColumn columnPmtType = createColumn("paymentType", String.class, "Check No", 15, headerStyle,
				detailTextStyle);
		AbstractColumn columnAplAmt = createColumn("payingAmount", BigDecimal.class, "Cash", 10, headerStyle,
				detailNumStyle);

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailNumStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columnInvNo).addColumn(columnCust).addColumn(columnAplAmt)
				.addColumn(columnPmtType).addColumn(columnChkAmt);

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));
		//
		// CustomExpression subtotals=new CustomExpression() {
		//
		// @Override
		// public String getClassName() {
		// return String.class.getName();
		// }
		//
		// @Override
		// public Object evaluate(Map fields, Map variables, Map parameters) {
		// BigDecimal totals= columnAplAmt
		// return ;
		// }
		// };
		// ///////

		// ///////
		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columnAplAmt, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Amount");

		// report.addGlobalFooterVariable(columnAplAmt,DJCalculation.SUM).setTitle("Total
		// Cash Amount");
		report.addGlobalFooterVariable(columnChkAmt, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Amount");
		// report.addGlobalFooterVariable(columnChkAmt,DJCalculation.SUM).setTitle("Total
		// Cash Amount");

		report.setTitle("BEST VALUE Auto Body Supply, Inc.");
		report.setTitleStyle(titleStyle.build());

		report.setSubtitle("4425 W. 16Th St, CHICAGO, IL-60623 " + "\\n Phone: (773) 762-1000 Fax: (773) 542-5854"
				+ "\\nFinance Statement - " + InsightUtils.getNewUSDate()).setSubtitleHeight(20);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public JasperPrint getPendingBCReport() throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		HashMap<String, Object> paramMap = accountService.getPendingBC();
		DynamicReport dynaReport = getPendingBCReport(headerStyle, detailTextStyle, detailNumberStyle, paramMap);
		@SuppressWarnings("unchecked")
		List<BouncedChecks> nbclist = (List<BouncedChecks>) paramMap.get("ds");

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(nbclist));
		return jp;
	}

	private DynamicReport getPendingBCReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle,
			HashMap params) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columnChkid = createColumn("checkId", Integer.class, "check Id", 15, headerStyle,
				detailNumStyle);
		AbstractColumn columnCust = createColumn("companyName", String.class, "Customer", 35, headerStyle,
				detailTextStyle);
		AbstractColumn columBal = createColumn("balance", BigDecimal.class, "Balance", 10, headerStyle, detailNumStyle);

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailTextStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columnChkid).addColumn(columnCust).addColumn(columBal);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));
		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));

		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columBal, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Balance Amount:");

		report.setTitle("General Report Viewer");
		report.setTitleStyle(titleStyle.build());

		report.setSubtitleHeight(20);
		report.setSubtitle("PENDING BOUNCED CHECKS AS ON " + InsightUtils.getNewUSDate());
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

	public String getPendingBouncedChecks(HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Pending Bounced Check report");

		JasperPrint jp = getPendingBCReport();
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fileName = appRepository + "//finance//pendingbc//f_pending_bc_" + InsightUtils.getNewUSDate() + ".pdf";
		ReportLayout.exportToPdf(jp, response, fileName);
		return fileName;
	}

	@SuppressWarnings("unchecked")
	public Map getRegionReturns(String dateStartStr, String dateEndStr) {

		if (dateStartStr == null || dateStartStr.equals("") || dateEndStr == null || dateEndStr.equals("")) {
			dateStartStr = null;
			dateEndStr = null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		Date dateEnd;
		Date dateStart;
		if (dateEndStr != null) {
			try {
				dateEnd = dateFormat.parse(dateEndStr);
				dateStart = dateFormat.parse(dateStartStr);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			dateEnd = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			dateStart = calendar.getTime();
		}

		Map regionReturn1 = saleRptDao.getRegionReturns(null, dateEnd);
		Map regionReturn2 = saleRptDao.getRegionReturns(dateStart, dateEnd);
		Map result = new HashMap();
		result.put("regionReturnReport1", regionReturn1);
		result.put("regionReturnReport2", regionReturn2);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map getRegionSales(String dateStartStr, String dateEndStr) {

		if (dateStartStr == null || dateStartStr.equals("") || dateEndStr == null || dateEndStr.equals("")) {
			dateStartStr = null;
			dateEndStr = null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		Date dateEnd;
		Date dateStart;
		if (dateEndStr != null) {
			try {
				dateEnd = dateFormat.parse(dateEndStr);
				dateStart = dateFormat.parse(dateStartStr);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			dateEnd = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			dateStart = calendar.getTime();
		}

		Map regionReturn1 = saleRptDao.getRegionSale(null, dateEnd);
		Map regionReturn2 = saleRptDao.getRegionSale(dateStart, dateEnd);

		Map result = new HashMap();
		result.put("regionSalesReport1", regionReturn1);
		result.put("regionSalesReport2", regionReturn2);
		return result;
	}

	// public Map getSalesPersonSales() {
	// return saleRptDao.getSaleDetailsSalePerson();
	// }
	@SuppressWarnings("unchecked")
	public Map getSaleDetails(String dateStartStr, String dateEndStr) {

		if (dateStartStr == null || dateStartStr.equals("") || dateEndStr == null || dateEndStr.equals("")) {
			dateStartStr = null;
			dateEndStr = null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		Date dateEnd;
		Date dateStart;
		if (dateEndStr != null) {
			try {
				dateEnd = dateFormat.parse(dateEndStr);
				dateStart = dateFormat.parse(dateStartStr);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			dateEnd = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			dateStart = calendar.getTime();
		}
		Map saleReport1 = saleRptDao.getSaleDetails(null, dateEnd);
		Map saleReport2 = saleRptDao.getSaleDetails(dateStart, dateEnd);
		Map result = new HashMap();
		result.put("saleReport1", saleReport1);
		result.put("saleReport2", saleReport2);
		result.put("saleReport3", saleRptDao.getSaleDetailsSalePerson(dateStart, dateEnd));
		return result;
	}

	public Map<String, Object> getSalesRpt(String dateStartStr, String dateEndStr, String salesPerson) {
		if (dateStartStr == null || dateStartStr.equals("") || dateEndStr == null || dateEndStr.equals("")) {
			dateStartStr = null;
			dateEndStr = null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		java.util.Date dateEnd;
		java.util.Date dateStart;
		if (dateEndStr != null) {
			try {
				dateEnd = dateFormat.parse(dateEndStr);
				dateStart = dateFormat.parse(dateStartStr);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			dateEnd = calendar.getTime();
			dateStart = calendar.getTime();
		}
		// List map=new ArrayList<>();
		// Session session=sessionFactory.getCurrentSession();
		// LOGGER.info("getsalesrpt---");
		// Query query = session.createSQLQuery(
		// "CALL proc_dailySalesReport(:start_date,:end_date,:salesperson)")
		// .setParameter("start_date", dateStart)
		// .setParameter("end_date", dateEnd)
		// .setParameter("salesperson", salesPerson);
		// map=query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		//
		// return map;
		return saleRptDao.getSalesRpt(dateStartStr, dateEndStr, salesPerson);
	}

	@Transactional
	public List<StockAuditData> getStockAudit(String analyticsfromdate, String analyticstodate) {
		if (analyticsfromdate == null || analyticsfromdate.equals("") || analyticstodate == null
				|| analyticstodate.equals("")) {
			analyticsfromdate = null;
			analyticstodate = null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		Date dateEnd;
		Date dateStart;
		if (analyticstodate != null) {
			try {
				dateEnd = dateFormat.parse(analyticstodate);
				dateStart = dateFormat.parse(analyticsfromdate);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			dateEnd = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			dateStart = calendar.getTime();
		}

		List<StockAuditData> stockAuditDatas = new ArrayList<StockAuditData>();

		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "From StockAudit stockAudit  where stockAudit.dateenter between :analyticsfromdate and :analyticstodate ";
		Query query = session.createQuery(hqlQuery);
		query.setParameter("analyticsfromdate", dateStart);
		query.setParameter("analyticstodate", dateEnd);

		List<StockAudit> stockAuditQueryResult = query.list();
		// LOGGER.info("Length:" + stockAuditQueryResult.size());
		if (stockAuditQueryResult.size() > 0) {
			for (StockAudit stockAudit : stockAuditQueryResult) {
				StockAuditData createdStockAudit = new StockAuditData();
				createdStockAudit.setSerial(stockAudit.getSerial());
				createdStockAudit.setDateenter(stockAudit.getDateenter());
				createdStockAudit.setChangeDesc(stockAudit.getChangeDesc());
				createdStockAudit.setPartNo(stockAudit.getPartNo());
				createdStockAudit.setChnge(stockAudit.getChnge());
				createdStockAudit.setOlde(stockAudit.getOlde());

				stockAuditDatas.add(createdStockAudit);
			}
		}

		session.flush();
		session.clear();
		return stockAuditDatas;
	}

	public String getWriteoff(String startDate, String endDate, HttpServletResponse response, String appRepository)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Account Recievable/payable report");

		JasperPrint jp = getWriteoffReport(startDate, endDate);
		// //ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fileName = appRepository + "//finance//writeOff//f_wo_" + InsightUtils.getNewUSDate() + "_" + startDate
				+ "_" + endDate + ".pdf";
		ReportLayout.exportToPdf(jp, response, fileName);
		return fileName;

	}

	public JasperPrint getWriteoffReport(String startDate, String endDate)
			throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		List<Writeoff> ds = accountService.getWriteOffInvoices(startDate, endDate);
		DynamicReport dynaReport = getWriteoffReport(startDate, endDate, headerStyle, detailTextStyle,
				detailNumberStyle);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(ds));
		return jp;
	}

	private DynamicReport getWriteoffReport(String startDate, String endDate, Style headerStyle, Style detailTextStyle,
			Style detailNumStyle) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columninvNo = createColumn("invoiceNo", Integer.class, "Invoice No", 10, headerStyle,
				detailNumStyle);
		AbstractColumn columnOrderDate = createColumn("orderDate", Date.class, "order Date", 15, headerStyle,
				detailTextStyle);
		AbstractColumn columnwoDt = createColumn("writeOffDate", Date.class, "Write Off On", 15, headerStyle,
				detailTextStyle);
		AbstractColumn columnCompanyName = createColumn("companyName", String.class, "Company Name", 15, headerStyle,
				detailTextStyle);
		AbstractColumn columnbal = createColumn("amount", BigDecimal.class, "Balance", 10, headerStyle, detailNumStyle);
		AbstractColumn columnNotes = createColumn("notes", String.class, "Notes", 15, headerStyle, detailTextStyle);

		@SuppressWarnings("serial")
		AbstractColumn sno = ColumnBuilder.getNew().setTitle("SL NO").setWidth(8).setStyle(detailTextStyle)
				.setHeaderStyle(headerStyle).setCustomExpression(new CustomExpression() {

					@Override
					public Object evaluate(Map fields, Map variables, Map parameters) {

						Integer count = (Integer) variables.get("REPORT_COUNT");
						return count;
					}

					@Override
					public String getClassName() {

						return Integer.class.getName();
					}
				}).build();
		report.addColumn(sno).addColumn(columninvNo).addColumn(columnOrderDate).addColumn(columnCompanyName)
				.addColumn(columnbal).addColumn(columnwoDt).addColumn(columnNotes);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(16, Font._FONT_ARIAL, true));
		report.setWhenNoData("No Data Found for this Report.", titleStyle.build());
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(12, Font._FONT_GEORGIA, true));

		StyleBuilder stStyle = new StyleBuilder(true);
		stStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		stStyle.setFont(new Font(12, Font._FONT_GEORGIA, true, false, true)).setTextColor(Color.black);

		report.addGlobalFooterVariable(columnbal, DJCalculation.SUM, stStyle.build())
				.setGrandTotalLegend("Total Amount");

		report.setTitle("BEST VALUE Auto Body Supply, Inc.");
		report.setTitleStyle(titleStyle.build());

		report.setSubtitleHeight(40);
		report.setSubtitle("Write Off Invoices From " + startDate + " To " + endDate);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}
}
