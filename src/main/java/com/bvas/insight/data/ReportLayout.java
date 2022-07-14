package com.bvas.insight.data;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings("deprecation")
public class ReportLayout {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportLayout.class);

	public static DynamicReport buildReportLayout() throws ColumnBuilderException, ClassNotFoundException {

		// Create an instance of FastReportBuilder
		FastReportBuilder drb = new FastReportBuilder();

		// Create columns
		// The column fields must match the name and type of the properties in
		// your datasource
		drb.addColumn("InvoiceNumber", "InvoiceNumber", String.class.getName(), 50)
				.addColumn("AppliedAmount", "AppliedAmount", BigDecimal.class.getName(), 50)
				.addColumn("PaymentType", "PaymentType", String.class.getName(), 50).setPrintColumnNames(true)

				// Disables pagination
				.setIgnorePagination(true)

				// Experiment with this numbers to see the effect
				.setMargins(0, 0, 0, 0)

				// Set the title shown inside the Excel file
				.setTitle("Sales Report")

				// Set the subtitle shown inside the Excel file
				.setSubtitle("This report was generated at ")

				// Set to true if you want to constrain your report within the
				// page boundaries
				// For longer reports, set it to false
				.setUseFullPageWidth(true);

		// Set the name of the file
		// drb.setReportName("Sales Report");

		// Build the report layout. It doesn't have any data yet!
		DynamicReport dr = drb.build();

		// Return the layout
		return dr;
	}

	/*
	 * public static void exportToHtml(JasperPrint jasperPrint,
	 * ByteArrayOutputStream baos) throws JRException {
	 * 
	 * JRHtmlExporter exporter = new JRHtmlExporter();
	 * 
	 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
	 * exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
	 * true);
	 * 
	 * exporter.exportReport(); }
	 */

	public static void exportToPdf(JasperPrint jasperPrint, HttpServletResponse response, String fileName)
			throws JRException {

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		// if(baos!=null && fileName.equals(""));{
		// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		// response.addHeader( "Content-Disposition", "attachment; filename=" +
		// fileName );
		// response.setContentType("application/pdf");
		// response.setContentLength(baos.size());
		//
		// // 7. Write to response stream
		// ReportLayout.write(response, baos);
		// }

		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, fileName);
		exporter.setParameter(JRPdfExporterParameter.IS_TAGGED, Boolean.TRUE);
		exporter.exportReport();
	}

	public static void exportToXls(JasperPrint jasperPrint, ByteArrayOutputStream baos) throws JRException {

		JRXlsExporter exporter = new JRXlsExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);

		exporter.exportReport();
	}

	public static void write(HttpServletResponse response, ByteArrayOutputStream baos) {

		try {
			// Retrieve the output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to the output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();

		} catch (Exception e) {
			LOGGER.error("Unable to write report to the output stream");
		}
	}

}
