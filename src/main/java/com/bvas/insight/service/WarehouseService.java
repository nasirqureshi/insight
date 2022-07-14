package com.bvas.insight.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.InvoicesNotDelivered;
import com.bvas.insight.entity.Invoice;
import com.bvas.insight.entity.PartsDelivered;
import com.bvas.insight.utilities.ListOutFilesInDir;
import com.bvas.insight.utilities.WarehouseUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Repository
public class WarehouseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseService.class);

	@SuppressWarnings("unused")
	@Autowired
	private ServletContext context;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public String createRoute(List<InvoicesNotDelivered> selectedinvoices, String repository, String driverselected,
			String ampmselected, String forroute, String username, String branch) {

		ListOutFilesInDir lo = new ListOutFilesInDir(0);

		// special font sizes
		Font BFBOLD = new Font(FontFamily.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(0, 0, 0));
		Font BFBOLD12 = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		Font BFBOLDCMPNY = new Font(FontFamily.TIMES_ROMAN, 9, Font.NORMAL, new BaseColor(0, 0, 0));
		Font BF12 = new Font(FontFamily.TIMES_ROMAN, 9);
		Font BFEXTRA = new Font(FontFamily.TIMES_ROMAN, 9);
		DecimalFormat df = new DecimalFormat("0.00");
		Calendar calendar = Calendar.getInstance();
		Date todaysdate = new java.sql.Date(calendar.getTime().getTime());
		SimpleDateFormat simpleformat = new SimpleDateFormat("yyyyMMdd");
		String javasqldate = simpleformat.format(new java.sql.Date(calendar.getTime().getTime()));
		String routefilename = "";
		Document document = new Document();
		String filname = javasqldate.trim() + "_" + forroute.trim() + "_" + ampmselected.trim() + "_"
				+ driverselected.trim() + ".pdf";
		filname = lo.getNextFilename(repository + "/route", filname);
		// updating the invoice and parts delivered
		updateInvoiceDelivery(selectedinvoices, driverselected);

		// creating the file in the repository
		try {
			Paragraph paragraphMain = new Paragraph();
			Paragraph paragraphDetail = new Paragraph();
			String imagePath = request.getSession().getServletContext().getRealPath("") + File.separator
					+ "resources\\images\\main.jpg";
			if ((branch.equalsIgnoreCase("AMS")) || (branch.equalsIgnoreCase("MPS"))) {
				imagePath = request.getSession().getServletContext().getRealPath("") + File.separator
						+ "resources\\images\\mainAM.jpg";
			}
			routefilename = repository + File.separator + "route\\" + filname;
			PdfWriter.getInstance(document, new FileOutputStream(routefilename));
			document.addAuthor("Insight Application");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("Insight Application");
			document.addTitle("Route:" + filname);
			document.setPageSize(PageSize.LETTER);
			document.open();

			// image file
			Image image = Image.getInstance(imagePath);

			image.scaleToFit(180, 90);

			PdfPTable tableimage = new PdfPTable(2);
			// tableimage = 100f;
			tableimage.setWidthPercentage(95);
			paragraphMain.add("\n");
			paragraphMain.add("ROUTE SHEET: " + todaysdate + "\n" + "Route: " + forroute);

			PdfPCell cell1 = new PdfPCell(image, false);
			PdfPCell cell2 = new PdfPCell(paragraphMain);

			cell1.setBorder(Rectangle.NO_BORDER);
			cell2.setBorder(Rectangle.NO_BORDER);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);

			tableimage.addCell(cell1);
			tableimage.addCell(cell2);
			document.add(tableimage);

			// sub heading #1
			PdfPTable tablesubheading1 = new PdfPTable(3);

			tablesubheading1.setWidthPercentage(95);

			PdfPCell cellSH_11 = new PdfPCell(new Paragraph("Driver: " + driverselected));

			cellSH_11.setBorder(Rectangle.NO_BORDER);
			cellSH_11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSH_11.setColspan(1);

			PdfPCell cellSH_12 = new PdfPCell(new Paragraph("Print: " + username));

			cellSH_12.setBorder(Rectangle.NO_BORDER);
			cellSH_12.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSH_12.setColspan(1);

			PdfPCell cellSH_13 = new PdfPCell(new Paragraph("Total Invoices: " + selectedinvoices.size()));

			cellSH_13.setBorder(Rectangle.NO_BORDER);
			cellSH_13.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSH_13.setColspan(1);
			tablesubheading1.addCell(cellSH_11);
			tablesubheading1.addCell(cellSH_12);
			tablesubheading1.addCell(cellSH_13);
			document.add(tablesubheading1);
			document.add(Chunk.NEWLINE);

			// sub heading #2
			PdfPTable tablesubheading2 = new PdfPTable(3);

			tablesubheading2.setWidthPercentage(95);

			PdfPCell cellSH_21 = new PdfPCell(new Paragraph("Van Mileage:______________"));

			cellSH_21.setBorder(Rectangle.NO_BORDER);
			cellSH_21.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSH_21.setColspan(1);

			PdfPCell cellSH_22 = new PdfPCell(new Paragraph("OUT: " + "______________"));

			cellSH_22.setBorder(Rectangle.NO_BORDER);
			cellSH_22.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSH_22.setColspan(1);

			PdfPCell cellSH_23 = new PdfPCell(new Paragraph("IN: " + "______________"));

			cellSH_23.setBorder(Rectangle.NO_BORDER);
			cellSH_23.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSH_23.setColspan(1);
			tablesubheading2.addCell(cellSH_21);
			tablesubheading2.addCell(cellSH_22);
			tablesubheading2.addCell(cellSH_23);
			document.add(tablesubheading2);
			document.add(Chunk.NEWLINE);

			// specify column widths
			float[] columnWidths = { 0.5f, 0.5f, 2f, 7f, 1.5f, 1f, 1f, 3f };

			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);

			// set table width a percentage of the page width
			table.setWidthPercentage(95f);

			// insert column headings
			insertCell(table, "IN", Element.ALIGN_RIGHT, 1, BFBOLD12);
			insertCell(table, "OUT", Element.ALIGN_LEFT, 1, BFBOLD12);
			insertCell(table, "INVOICE", Element.ALIGN_LEFT, 1, BFBOLD12);
			insertCell(table, "TERMS + COMPANY", Element.ALIGN_LEFT, 1, BFBOLDCMPNY);
			insertCell(table, "AMT", Element.ALIGN_RIGHT, 1, BFBOLD12);
			insertCell(table, "PAID", Element.ALIGN_LEFT, 1, BFBOLD12);
			insertCell(table, "PAYTYPE", Element.ALIGN_LEFT, 1, BFBOLD12);
			insertCell(table, "REMARKS", Element.ALIGN_LEFT, 1, BFBOLD12);
			table.setHeaderRows(1);

			double orderTotal = 0;

			for (InvoicesNotDelivered invoicenotdelivered : selectedinvoices) {
				orderTotal = invoicenotdelivered.getInvoicetotal().doubleValue()
						+ invoicenotdelivered.getTax().doubleValue() - invoicenotdelivered.getDiscount().doubleValue();
				insertCell(table, "", Element.ALIGN_LEFT, 1, BF12);
				insertCell(table, "", Element.ALIGN_LEFT, 1, BF12);
				insertCell(table, invoicenotdelivered.getInvoicenumber().toString(), Element.ALIGN_LEFT, 1, BFBOLD);
				insertCell(table, WarehouseUtils.getTermDescription(invoicenotdelivered.getPaymentterms()) + " "
						+ invoicenotdelivered.getCompanyname(), Element.ALIGN_LEFT, 1, BF12);
				insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, BF12);
				insertCell(table, "", Element.ALIGN_LEFT, 1, BF12);
				insertCell(table, "", Element.ALIGN_LEFT, 1, BF12);
				insertCell(table, "", Element.ALIGN_LEFT, 1, BF12);
			}

			paragraphDetail.add(table);

			// create PDF table with the given widths
			PdfPTable tableBuffer = new PdfPTable(columnWidths);

			// set table width a percentage of the page width
			tableBuffer.setWidthPercentage(95f);

			for (int i = 0; i < 7; i++) {

				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
				insertCell(tableBuffer, "", Element.ALIGN_LEFT, 1, BFEXTRA);
			}

			paragraphDetail.add(tableBuffer);

			// add the paragraph to the document
			document.add(paragraphDetail);

			document.add(Chunk.NEWLINE);

			PdfPTable otherdetailstable1 = new PdfPTable(3);
			PdfPTable otherdetailstable2 = new PdfPTable(3);
			otherdetailstable1.setWidthPercentage(98);
			otherdetailstable1.setWidthPercentage(98);

			PdfPCell cellod11 = new PdfPCell(new Phrase("AM/PM		", BFBOLD12));
			cellod11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellod11.setBorder(Rectangle.NO_BORDER);
			cellod11.setColspan(1);

			PdfPCell cellod12 = new PdfPCell(new Phrase("TIME LEFT: ________", BFBOLD12));
			cellod12.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellod12.setBorder(Rectangle.NO_BORDER);
			cellod12.setColspan(1);

			PdfPCell cellod13 = new PdfPCell(new Phrase("ARRIVED: ________     ", BFBOLD12));
			cellod13.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellod13.setBorder(Rectangle.NO_BORDER);
			cellod13.setColspan(1);

			otherdetailstable1.addCell(cellod11);
			otherdetailstable1.addCell(cellod12);
			otherdetailstable1.addCell(cellod13);
			document.add(otherdetailstable1);

			document.add(Chunk.NEWLINE);

			PdfPCell cellod21 = new PdfPCell(new Phrase("CHKD BY: _________", BFBOLD12));
			cellod21.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellod21.setBorder(Rectangle.NO_BORDER);
			cellod21.setColspan(1);

			PdfPCell cellod22 = new PdfPCell(new Phrase("TIME: ________", BFBOLD12));
			cellod22.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellod22.setBorder(Rectangle.NO_BORDER);
			cellod22.setColspan(1);

			PdfPCell cellod23 = new PdfPCell(new Phrase("DRV. SIGN: _________", BFBOLD12));
			cellod23.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellod23.setBorder(Rectangle.NO_BORDER);
			cellod23.setColspan(1);

			otherdetailstable2.addCell(cellod21);
			otherdetailstable2.addCell(cellod22);
			otherdetailstable2.addCell(cellod23);
			document.add(otherdetailstable2);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (document != null) {
				document.close();
			}
		}

		return routefilename;
	}

	@Transactional
	public Invoice getInvoice(Integer invoicenumber) {

		String hqlQuery = "From Invoice invoice  where invoice.invoicenumber=:invoicenumber";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);

		query.setParameter("invoicenumber", invoicenumber);

		@SuppressWarnings("unchecked")
		List<Invoice> invoices = query.list();

		session.flush();
		session.clear();
		if (invoices.size() > 0) {
			return invoices.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Map<Integer, InvoicesNotDelivered> getInvoiceNotDelivered(String routeselected) {

		Map<Integer, InvoicesNotDelivered> notdelieveredMap = new LinkedHashMap<Integer, InvoicesNotDelivered>();
		String hqlQuery = "";
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<InvoicesNotDelivered> results = new LinkedList<InvoicesNotDelivered>();

		if (routeselected.equalsIgnoreCase("ALL")) {
			hqlQuery = WarehouseUtils.INVOICE_NOT_DELIVERED_ALL;
			query = session.createSQLQuery(hqlQuery).addScalar("invoicenumber").addScalar("invoicetime")
					.addScalar("customerid").addScalar("salesperson").addScalar("isdelivered").addScalar("companyname")
					.addScalar("notes").addScalar("region").addScalar("paymentterms").addScalar("invoicetotal")
					.addScalar("tax").addScalar("discount")
					.setResultTransformer(Transformers.aliasToBean(InvoicesNotDelivered.class));
		} else {
			hqlQuery = WarehouseUtils.INVOICE_NOT_DELIVERED_ROUTE;
			query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("region", routeselected))
					.addScalar("invoicenumber").addScalar("invoicetime").addScalar("customerid")
					.addScalar("salesperson").addScalar("isdelivered").addScalar("companyname").addScalar("notes")
					.addScalar("region").addScalar("paymentterms").addScalar("invoicetotal").addScalar("tax")
					.addScalar("discount").setResultTransformer(Transformers.aliasToBean(InvoicesNotDelivered.class));
		}

		results = query.list();

		for (InvoicesNotDelivered invoicenotdelieverd : results) {
			notdelieveredMap.put(invoicenotdelieverd.getInvoicenumber(), invoicenotdelieverd);
		}

		session.flush();
		session.clear();

		return notdelieveredMap;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Map<Integer, InvoicesNotDelivered> getInvoiceNotDeliveredWild(String routeselected, String searchtext) {

		Map<Integer, InvoicesNotDelivered> notdelieveredMap = new LinkedHashMap<Integer, InvoicesNotDelivered>();
		String hqlQuery = "";
		Session session = sessionFactory.getCurrentSession();
		List<InvoicesNotDelivered> results = new LinkedList<InvoicesNotDelivered>();
		Query query = null;

		if (routeselected.equalsIgnoreCase("ALL")) {
			hqlQuery = WarehouseUtils.INVOICE_NOT_DELIVERED_ALL_WILD;
			query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("searchtext", "%" + searchtext + "%"))
					.addScalar("invoicenumber").addScalar("invoicetime").addScalar("customerid")
					.addScalar("salesperson").addScalar("isdelivered").addScalar("companyname").addScalar("notes")
					.addScalar("region").addScalar("paymentterms").addScalar("invoicetotal").addScalar("tax")
					.addScalar("discount").setResultTransformer(Transformers.aliasToBean(InvoicesNotDelivered.class));
		} else {
			hqlQuery = WarehouseUtils.INVOICE_NOT_DELIVERED_ROUTE_WILD;
			query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("region", routeselected)
					.setParameter("searchtext", "%" + searchtext + "%")).addScalar("invoicenumber")
							.addScalar("invoicetime").addScalar("customerid").addScalar("salesperson")
							.addScalar("isdelivered").addScalar("companyname").addScalar("notes").addScalar("region")
							.addScalar("paymentterms").addScalar("invoicetotal").addScalar("tax").addScalar("discount")
							.setResultTransformer(Transformers.aliasToBean(InvoicesNotDelivered.class));
		}

		results = query.list();

		for (InvoicesNotDelivered invoicenotdelieverd : results) {
			notdelieveredMap.put(invoicenotdelieverd.getInvoicenumber(), invoicenotdelieverd);
		}

		session.flush();
		session.clear();

		return notdelieveredMap;
	}

	@Transactional
	public PartsDelivered getPartsDelivered(Integer invoicenumber) {

		String hqlQuery = "From PartsDelivered partsdelivered  where partsdelivered.invoicenumber=:invoicenumber";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);

		query.setParameter("invoicenumber", invoicenumber);

		@SuppressWarnings("unchecked")
		List<PartsDelivered> partsdelievered = query.list();

		session.flush();
		session.clear();

		if (partsdelievered.size() > 0) {
			return partsdelievered.get(0);
		} else {
			return null;
		}
	}

	private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));

		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);

		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(25f);
		}

		table.addCell(cell);
	}

	@Transactional
	public void saveNotes(Map<Integer, String> invoicenumbernotesMap) {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "UPDATE invoice set notes=:notes where invoicenumber=:invoicenumber";

		for (Integer key : invoicenumbernotesMap.keySet()) {
			// LOGGER.info(invoicenumbernotesMap.get(key));
			Query query = session.createSQLQuery(updateSql);
			query.setParameter("notes", invoicenumbernotesMap.get(key));
			query.setParameter("invoicenumber", key);
			query.executeUpdate();

		}

		session.flush();
		session.clear();
	}

	@Transactional
	public void updateInvoiceDelivery(List<InvoicesNotDelivered> selectedinvoices, String driverselected) {

		Session session = sessionFactory.getCurrentSession();
		// String hqlQuery = "From Invoice invoice where
		// invoice.invoicenumber=:invoicenumber";

		for (InvoicesNotDelivered invoicenotdelivered : selectedinvoices) {
			// Query query2 = session.createQuery(hqlQuery);
			// query2.setParameter("invoicenumber",
			// invoicenotdelivered.getInvoicenumber());
			Invoice invoice = (Invoice) session.get(Invoice.class, invoicenotdelivered.getInvoicenumber());
			// invoice = (Invoice) query2.list().get(0);

			if (invoice != null) {
				invoice.setIsdelivered("Y");
				session.saveOrUpdate(invoice);

				PartsDelivered partsdelivered = getPartsDelivered(invoicenotdelivered.getInvoicenumber());

				if (partsdelivered != null) {
					partsdelivered.setName(driverselected);
					session.saveOrUpdate(partsdelivered);
				} else {
					PartsDelivered partsdelivered_new = new PartsDelivered();

					partsdelivered_new.setInvoicenumber(invoicenotdelivered.getInvoicenumber());
					partsdelivered_new.setName(driverselected);
					session.saveOrUpdate(partsdelivered_new);
				}
			}
		}

		session.flush();
		session.clear();
	}

	@Transactional
	public void updateRedeliveryInvoice(Integer invoicenumber) {

		Session session = sessionFactory.getCurrentSession();
		Invoice invoice = getInvoice(invoicenumber);
		String newnote = "(REDELIVER)	" + invoice.getNotes();
		invoice.setIsdelivered("R");
		invoice.setNotes(newnote);
		session.saveOrUpdate(invoice);
		session.flush();
		session.clear();

	}
}
