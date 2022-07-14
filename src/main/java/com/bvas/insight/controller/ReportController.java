package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.StockAuditData;
import com.bvas.insight.entity.BouncedChecks;
import com.bvas.insight.service.AccountService;
import com.bvas.insight.service.ReportService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;

import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("/report/")
public class ReportController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	static {
		System.setProperty("java.awt.headless", "true");
	}

	@Autowired
	@Qualifier("accountService")
	protected AccountService accountService;

	protected ModelAndView mav = new ModelAndView();

	@Resource(name = "reportService")
	private ReportService reportService;

	AppUser user;

	@RequestMapping(value = "/downloadReport", method = RequestMethod.POST)
	public void downloadReport(HttpServletResponse response, HttpServletRequest request, ModelAndView model,
			@RequestParam("reportfilename") String reportfilename)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Received request to download report as an pdf");
		try {

			ServletOutputStream outStream;
			outStream = response.getOutputStream();

			File file = new File(reportfilename);
			response.setContentType("application/pdf");
			response.setContentLength((int) file.length());
			// sets HTTP header
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			org.apache.commons.io.IOUtils.copy(in, response.getOutputStream());
			/*
			 * byte[] byteBuffer = new byte[BUFSIZE]; DataInputStream in = new
			 * DataInputStream( new FileInputStream(file)); while ((in != null) && ((length
			 * = in.read(byteBuffer)) != -1)) { outStream.write(byteBuffer, 0, length); }
			 */
			in.close();
			outStream.flush();
			outStream.close();
			response.flushBuffer();

		} catch (IOException e) {
			e.printStackTrace();
			// LOGGER.info("IOException: " + e.getMessage().toString());
		} catch (IllegalStateException e) {
			// LOGGER.info("IllegalStateException: ");
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.setViewName("accountingreports");
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());

		mav.addObject("reportfilename", "");
		mav.addObject("reportname", "");
		// return mav;

	}

	@RequestMapping(value = "downloadRpt", method = RequestMethod.POST)
	private ModelAndView downloadReports(@RequestParam("rptPath") String rptPath, ModelAndView mav,
			HttpServletResponse response) {
		mav.setViewName("salesdetailspage");
		File savePath = new File(repository + rptPath);
		String path = savePath.toString();
		try {
			if (path != null && (savePath.exists() && !savePath.isDirectory())) {
				response.setContentType("application/pdf");
				response.setHeader("content-dispostion", "attachment;");
				InputStream is = new FileInputStream(savePath);

				int read = 0;
				byte[] bytes = new byte[1024];

				OutputStream os = response.getOutputStream();
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				mav.addObject("salesList", null);
				mav.addObject("fileName", "");
				is.close();
				os.flush();
				os.close();
				savePath.delete();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/AcctPayable", method = RequestMethod.GET)
	public ModelAndView getAcctPayable(HttpServletResponse response, HttpServletRequest request, ModelAndView model)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Acct Recievable Report");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.getAcctRecievable("Payable", response, repository);
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "AcctPayable");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/AcctRecievable", method = RequestMethod.GET)
	public ModelAndView getAcctRecievable(HttpServletResponse response, HttpServletRequest request, ModelAndView model)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Acct Recievable Report");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.getAcctRecievable("Recievable", response, repository);
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "AcctRecievable");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/BCAcctStmt", method = RequestMethod.POST)
	public ModelAndView getAcctStmt(HttpServletResponse response, HttpServletRequest request, ModelAndView model,
			@RequestParam("customerId") String customerId)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Account Statement of Customer");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);
		String fileName = "";
		if (customerId != null && !customerId.equals("")) {
			List<BouncedChecks> bcList = accountService.getBouncedChecksByCustomerId(customerId);
			if (!bcList.isEmpty()) {
				fileName = reportService.getBCAcctStmt(customerId, response, repository);
			} else {
				mav.addObject("BCAcctStmtError", "No bounce check found.");
			}
		} else {
			mav.addObject("BCAcctStmtError", "Please enter cutomer id.");
		}
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "BCAcctStmt");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;

	}

	@RequestMapping(value = "/ageingInv", method = RequestMethod.GET)
	public ModelAndView getAgeingInv(HttpServletResponse response, HttpServletRequest request, ModelAndView model)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Acct Recievable Report");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.getAgeingInvoice(response, repository);
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "ageingInv");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/EAChecks", method = RequestMethod.GET)
	public ModelAndView getEAChecks(HttpServletResponse response, HttpServletRequest request, ModelAndView model)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Received request to download report as an pdf");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.EAChecks(response, repository);
		;
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "EAChecks");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	// ****************ReportController.jav

	@RequestMapping(value = "/finAdjrpt", method = RequestMethod.GET)
	public ModelAndView getFinAdjReport(HttpServletResponse response, HttpServletRequest request, ModelAndView model)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Received request to download report as an pdf");
		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.FinStmtAdjustment(response, repository);
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "finAdjrpt");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/finEArpt", method = RequestMethod.GET)
	public ModelAndView getFinEAReport(HttpServletResponse response, HttpServletRequest request, ModelMap model)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Received request to download report as an pdf");
		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.FinStmtEnterAmount(response, repository);
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "finEArpt");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/PendingBC", method = RequestMethod.GET)
	public ModelAndView getPendingBouncedChecks(HttpServletResponse response, HttpServletRequest request,
			ModelAndView model) throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Pending Bounced Checks");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		String fileName = reportService.getPendingBouncedChecks(response, repository);
		mav.addObject("reportfilename", fileName);
		mav.addObject("reportname", "PendingBC");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "salesDetailsRpt", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getRpt1(HttpServletResponse response, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("salesperson") String salesperson, ModelAndView mav,
			HttpSession session) throws JRException, IOException {
		mav.clear();
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		AppUser user = (AppUser) session.getAttribute("user");
		if (startDate.equals("") || endDate.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			endDate = dateFormat.format(calendar.getTime());
			startDate = dateFormat.format(calendar.getTime());
		}
		Map<String, Object> map = reportService.getSalesRpt(startDate, endDate, salesperson);
		if (user == null) {
			mav.setViewName("loginpage");
			return mav;
		} else {
			// LOGGER.info(map);
			mav.addObject("startDate", startDate);
			mav.addObject("endDate", endDate);
			mav.addObject("spList", mainService.getAllSalesPersons());
			mav.addObject("salesList", map.get("#result-set-1"));
			mav.addObject("salesGrpList", map.get("#result-set-2"));
		}
		mav.setViewName("salesdetailspage");

		if (map.size() > 0) {
			// InputStream jasperStream =
			// this.getClass().getResourceAsStream("com/bvas/insight/controller/report3.jasper");
			Map<String, Object> params = new HashMap<>();
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(new File(repository + "//sales//report3.jasper"));
			// JasperReport jasperReport1 = (JasperReport) JRLoader.loadObject(new
			// File(repository + "//sales//report3-1.jasper"));

			@SuppressWarnings("unchecked")
			JRDataSource ds = new JRBeanCollectionDataSource((ArrayList<Map<String, Object>>) map.get("#result-set-1"));
			// JRDataSource ds1 = new JRBeanCollectionDataSource((ArrayList<Map<String,
			// Object>>)map.get("#result-set-2"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
			// JasperPrint jasperPrint1 = JasperFillManager.fillReport(jasperReport1,
			// params, ds3);

			// response.setContentType("application/x-pdf");
			// response.setHeader("Content-disposition", "inline; filename=report3.pdf");
			// final OutputStream outStream = response.getOutputStream();
			// JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			String fileName = repository + "//sales//salesDetails_" + InsightUtils.getNewUSDate() + ".pdf";
			// String fileName1 = repository + "//sales//salesSummary_" +
			// InsightUtils.getNewUSDate() + ".pdf";
			mav.addObject("fileName", fileName);
			JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			// JasperExportManager.exportReportToPdfFile(jasperPrint1, fileName1);
		}
		return mav;
	}

	@RequestMapping(value = "/saleReportRegionReturn")
	public ModelAndView getSaleReportRegionReturn(ModelAndView mav, HttpSession session,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {

		mav.clear();
		mav.addObject("tab", "regionreturn");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		AppUser user = (AppUser) session.getAttribute("user");
		if (startDate.equals("") || endDate.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			endDate = dateFormat.format(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			startDate = dateFormat.format(calendar.getTime());
		}
		if (user == null) {
			mav.setViewName("loginpage");
			return mav;
		} else {
			mav.addObject("regionReturn", reportService.getRegionReturns(startDate, endDate));
		}
		mav.setViewName("salesreportpage");
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		return mav;
	}

	@RequestMapping(value = "/saleReportRegionSale")
	public ModelAndView getSaleReportRegionSale(ModelAndView mav, HttpSession session,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {

		mav.clear();
		mav.addObject("tab", "regionsale");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		AppUser user = (AppUser) session.getAttribute("user");
		if (startDate.equals("") || endDate.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			endDate = dateFormat.format(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			startDate = dateFormat.format(calendar.getTime());
		}
		if (user == null) {
			mav.setViewName("loginpage");
			return mav;
		} else {
			mav.addObject("regionSales", reportService.getRegionSales(startDate, endDate));
		}
		mav.setViewName("salesreportpage");
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		return mav;
	}

	@RequestMapping(value = "/saleReportSaleDetails")
	public ModelAndView getSaleReportSaleDetails(ModelAndView mav, HttpSession session,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {

		mav.clear();
		mav.addObject("tab", "salesdetails");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		AppUser user = (AppUser) session.getAttribute("user");
		if (startDate.equals("") || endDate.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			endDate = dateFormat.format(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			startDate = dateFormat.format(calendar.getTime());
		}
		if (user == null) {
			mav.setViewName("loginpage");
			return mav;
		} else {
			mav.addObject("salesDetails", reportService.getSaleDetails(startDate, endDate));
		}
		mav.setViewName("salesreportpage");
		// LOGGER.info("start="+startDate+"=end="+endDate);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/stockaudit", method = RequestMethod.GET)
	public ModelAndView getStockAudit(HttpServletResponse response, HttpServletRequest request, ModelAndView model,
			@RequestParam(value = "analyticsfromdate", required = true) String analyticsfromdate,
			@RequestParam(value = "analyticstodate", required = true) String analyticstodate) {

		mav.setViewName("stockauditpage");
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
		mav.addObject("analyticsfromdate", analyticsfromdate);
		mav.addObject("analyticstodate", analyticstodate);

		List<StockAuditData> stockauditList = reportService.getStockAudit(analyticsfromdate, analyticstodate);
		mav.addObject("stockauditlist", stockauditList);

		return mav;
	}

	@RequestMapping(value = "/writeoff", method = RequestMethod.POST)
	public ModelAndView getWriteoff(HttpServletResponse response, HttpServletRequest request, ModelAndView model,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate)
			throws ColumnBuilderException, ClassNotFoundException, JRException {

		LOGGER.debug("Acct Recievable Report");

		mav.clear();
		mav.setViewName("accountingreports");
		mav.addObject("user", user);

		if (startDate != null && endDate != null) {
			String fileName = reportService.getWriteoff(startDate, endDate, response, repository);
			mav.addObject("reportfilename", fileName);
			mav.addObject("reportname", "writeoff");
		}

		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping(value = "/nav", method = RequestMethod.GET)
	public ModelAndView landing(Locale locale, ModelAndView model, HttpServletResponse response) {

		// LOGGER.info("Welcome login! The client locale is {}.", locale);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		model.setViewName("loginpage");
		model.addObject("branch", branch);
		model.addObject("appcss", appcss);
		return model;

	}

	@RequestMapping(value = "/routeSaleAnalysis", method = RequestMethod.POST)
	public ModelAndView routeSaleAnalysis(HttpServletResponse response, HttpServletRequest request, ModelAndView model,
			@RequestParam("routselected") String routselected) {

		LOGGER.debug("Rout Sale Analysis");
		mav.clear();
		mav.setViewName("routeSaleAnalysisReportpage");
		mav.addObject("user", user);

		if (routelistdd.isEmpty()) {
			routelistdd = mainService.getAllRoute();
		}

		Map<String, Object> routeSale = accountService.getRouteSale(routselected);
		mav.addObject("routeSale", routeSale);
		mav.addObject("routelistdd", routelistdd);
		mav.addObject("routselected", routselected);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}
}
