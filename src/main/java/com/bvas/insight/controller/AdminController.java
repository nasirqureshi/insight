package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.CategoryBySalesAnalysis;
import com.bvas.insight.data.CostOfGoodsTotalReport;
import com.bvas.insight.data.LocalOrderItemsDetails;
import com.bvas.insight.data.MonthCustomerReport;
import com.bvas.insight.data.MonthSubCategoryReport;
import com.bvas.insight.data.PartnoQuantity;
import com.bvas.insight.data.SubCatAllBranch;
import com.bvas.insight.data.SubCatDrillDetails;
import com.bvas.insight.data.WeekCustomerReport;
import com.bvas.insight.data.WeekSubCategoryReport;
import com.bvas.insight.data.YearCustomerReport;
import com.bvas.insight.data.YearSubCategoryReport;
import com.bvas.insight.service.AdminService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.BVSExcel;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user" })
@RequestMapping("admin")
public class AdminController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	@Qualifier("adminService")
	protected AdminService adminService;

	List<CategoryBySalesAnalysis> categorysaleslist = new LinkedList<CategoryBySalesAnalysis>();

	List<LocalOrderItemsDetails> orderdeatilslist = new LinkedList<LocalOrderItemsDetails>();

	List<SubCatAllBranch> subcatallbranchlist = new LinkedList<SubCatAllBranch>();

	@RequestMapping(value = "/nav", method = RequestMethod.GET)
	public ModelAndView landing(Locale locale, ModelAndView model, HttpServletResponse response) {

		// // LOGGER.info("Welcome login! The client locale is {}.", locale);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		model.setViewName("loginpage");
		model.addObject("branch", branch);
		model.addObject("appcss", appcss);
		return model;

	}

	@RequestMapping(value = "getanalysis", method = RequestMethod.POST)
	public ModelAndView processAnalysis(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate,
			@RequestParam("analyticspageselect") String analyticspageselect, @RequestParam("duration") String duration,
			@RequestParam("subcategoryselected") String subcategoryselected, HttpServletResponse response,
			HttpSession session, ModelAndView mav

	) {

		AppUser user = (AppUser) session.getAttribute("user");
		String feedback = "";
		if (subcategorylistdd.size() == 0) {
			subcategorylistdd = mainService.getAllSubCategory();
		}
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (analyticspageselect.equalsIgnoreCase("costofgoodstotal")) {
				String extendedvalue = request.getParameter("extended");

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				// NQ 20210413 mav.addObject("feedback", adminService.runActualPriceUpdate());
				mav.addObject("feedback", adminService.runActualPriceUpdate());
				CostOfGoodsTotalReport costofgoodstotal = adminService.getCostOfGoodsTotal(analyticsfromdate,
						analyticstodate, extendedvalue);
				mav.addObject("costofgoodstotal", costofgoodstotal);
				return mav;

			} else if ((analyticspageselect.equalsIgnoreCase("salescustomer"))
					&& (duration.equalsIgnoreCase("Yearly"))) {

				List<YearCustomerReport> yearcustomerlist = adminService.getYearSalesCustomer(analyticsfromdate,
						analyticstodate);

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("yearcustomerlist", yearcustomerlist);
				mav.addObject("subcategorylistdd", subcategorylistdd);

				return mav;

			} else if ((analyticspageselect.equalsIgnoreCase("salescustomer"))
					&& (duration.equalsIgnoreCase("Monthly"))) {

				List<MonthCustomerReport> monthcustomerlist = adminService.getMonthSalesCustomer(analyticsfromdate,
						analyticstodate);

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("monthcustomerlist", monthcustomerlist);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;

			} else if ((analyticspageselect.equalsIgnoreCase("salescustomer"))
					&& (duration.equalsIgnoreCase("Weekly"))) {

				List<WeekCustomerReport> weekcustomerlist = adminService.getWeekSalesCustomer(analyticsfromdate,
						analyticstodate);

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("weekcustomerlist", weekcustomerlist);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;

			}
			// @worksite
			else if ((analyticspageselect.equalsIgnoreCase("salescategory")) && (duration.equalsIgnoreCase("Yearly"))) {

				List<YearSubCategoryReport> yearsubcategorylist = adminService
						.getYearSalesSubcategory(analyticsfromdate, analyticstodate);

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("yearsubcategorylist", yearsubcategorylist);
				mav.addObject("subcategorylistdd", subcategorylistdd);

				return mav;

			} else if ((analyticspageselect.equalsIgnoreCase("salescategory"))
					&& (duration.equalsIgnoreCase("Monthly"))) {

				List<MonthSubCategoryReport> monthsubcategorylist = adminService
						.getMonthlySalesSubcategory(analyticsfromdate, analyticstodate);

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("monthsubcategorylist", monthsubcategorylist);
				mav.addObject("subcategorylistdd", subcategorylistdd);

				return mav;

			} else if ((analyticspageselect.equalsIgnoreCase("salescategory"))
					&& (duration.equalsIgnoreCase("Weekly"))) {

				List<WeekSubCategoryReport> weeksubcategorylist = adminService
						.getWeeklySalesSubcategory(analyticsfromdate, analyticstodate);

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("weeksubcategorylist", weeksubcategorylist);
				mav.addObject("subcategorylistdd", subcategorylistdd);

				return mav;

			} else if (analyticspageselect.equalsIgnoreCase("subcategorydetails")) {
				List<SubCatDrillDetails> subcatdetailslist = adminService.getDrillDetailsSubcategory(analyticsfromdate,
						analyticstodate, subcategorylistdd.get(subcategoryselected));

				mav.addObject("duration", duration);
				mav.addObject("subcategoryselected", subcategoryselected);
				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("mode", "subcatdetails");
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("subcatdetailslist", subcatdetailslist);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;
			} else if (analyticspageselect.equalsIgnoreCase("saveallbranch")) {
				try {

					int length = 0;
					BVSExcel bvsexcel = new BVSExcel();
					String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;

					ServletOutputStream outStream;
					outStream = response.getOutputStream();
					File file = bvsexcel.createSubCategoryAllBranchFile(subcatallbranchlist, filePath,
							subcategoryselected);

					response.setContentType("application/octet-stream");
					response.setContentLength((int) file.length());
					// sets HTTP header
					response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
					byte[] byteBuffer = new byte[BUFSIZE];
					DataInputStream in = new DataInputStream(new FileInputStream(file));

					// reads the file's bytes and writes them to the response
					// stream
					while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
						outStream.write(byteBuffer, 0, length);
					}

					in.close();
					outStream.flush();
					outStream.close();

				} catch (NumberFormatException e) {
					LOGGER.error("NumberFormatException: processAnalysis##" + e.getMessage().toString());
					mav.addObject("duration", duration);
					mav.addObject("subcategoryselected", subcategoryselected);
					mav.setViewName("analyticspage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("mode", "subcatallbranch");
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("analyticsfromdate", analyticsfromdate);
					mav.addObject("analyticstodate", analyticstodate);
					mav.addObject("analyticspageselect", analyticspageselect);
					mav.addObject("subcatallbranchlist", subcatallbranchlist);
					mav.addObject("subcategorylistdd", subcategorylistdd);
					return mav;
				} catch (IOException e) {
					LOGGER.error("IOException: processAnalysis##" + e.getMessage().toString());
					mav.addObject("duration", duration);
					mav.addObject("subcategoryselected", subcategoryselected);
					mav.setViewName("analyticspage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("mode", "subcatallbranch");
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("analyticsfromdate", analyticsfromdate);
					mav.addObject("analyticstodate", analyticstodate);
					mav.addObject("analyticspageselect", analyticspageselect);
					mav.addObject("subcatallbranchlist", subcatallbranchlist);
					mav.addObject("subcategorylistdd", subcategorylistdd);
					return mav;
				}

				mav.addObject("duration", duration);
				mav.addObject("subcategoryselected", subcategoryselected);
				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("mode", "subcatallbranch");
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("subcatallbranchlist", subcatallbranchlist);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;

			}

			else if (analyticspageselect.equalsIgnoreCase("subcategoryallbranch")) {

				subcatallbranchlist = adminService.getSubcategoryAllBranch(analyticsfromdate, analyticstodate,
						subcategorylistdd.get(subcategoryselected), chpartsdao, grpartsdao, mppartsdao);

				mav.addObject("duration", duration);
				mav.addObject("subcategoryselected", subcategoryselected);
				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("mode", "subcatallbranch");
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("subcatallbranchlist", subcatallbranchlist);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;
			} else {

				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("analyticspageselect", analyticspageselect);
				mav.addObject("duration", duration);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("feedback", "");
				return mav;
			}

		}

	}// end of method

	@RequestMapping(value = "localorder", method = RequestMethod.POST)
	public ModelAndView processLocalOrder(@RequestParam("orderselect") String orderselect,
			@RequestParam("stockvalue") String stockvalue, @RequestParam("ordervalue") String ordervalue,
			@RequestParam("forecastdays") String forecastdays, @RequestParam("reordervalue") String reordervalue,
			@RequestParam(value = "nsf", required = false) String nsf,
			@RequestParam("localorderbtn") String localorderbtn, HttpSession session, ModelAndView mav

	) {

		AppUser user = (AppUser) session.getAttribute("user");

		String feedback = "";

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (nsf != null) {
				nsf = "nsf";
			} else {
				nsf = "all";
			}
			if (localorderbtn.equalsIgnoreCase("getlocalorder")) {
				if (orderselect.equalsIgnoreCase("locallightradio")) {
					orderdeatilslist = adminService.getLocalOrderDetails(orderselect, stockvalue, ordervalue,
							reordervalue, nsf, forecastdays);
				} else if (orderselect.equalsIgnoreCase("localtransradio")) {
					orderdeatilslist = adminService.getTransamericaOrderDetails(orderselect, stockvalue, ordervalue,
							reordervalue);
				} else if (orderselect.equalsIgnoreCase("localwestinradio")) {
					orderdeatilslist = adminService.getWestinOrderDetails(orderselect, stockvalue, ordervalue,
							reordervalue);
				}

				mav.setViewName("localorderspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("orderdeatilslist", orderdeatilslist);
				mav.addObject("stockvalue", stockvalue);
				mav.addObject("ordervalue", ordervalue);
				mav.addObject("reordervalue", reordervalue);
				mav.addObject("orderselect", orderselect);
				mav.addObject("forecastdays", forecastdays);
				mav.addObject("listsize", orderdeatilslist.size());
				return mav;
			} else if (localorderbtn.equalsIgnoreCase("createlocalorder")) {

				Set<String> checkdupes = new HashSet<String>();
				String[] partnoRP = request.getParameterValues("partno");
				String[] quantityRP = request.getParameterValues("quantity");
				String[] priceRP = request.getParameterValues("price");
				List<PartnoQuantity> localpartslist = new ArrayList<PartnoQuantity>();

				for (int i = 0; i < partnoRP.length; i++) {
					if (Integer.parseInt(quantityRP[i]) > 0) {
						if (checkdupes.add(partnoRP[i])) {
							PartnoQuantity pq = new PartnoQuantity();
							pq.setPartno(partnoRP[i]);
							pq.setQuantity(Integer.parseInt(quantityRP[i]));
							if (!priceRP[i].equalsIgnoreCase("")) {
								pq.setPrice(Double.valueOf(priceRP[i]));
							} else {
								pq.setPrice(Double.valueOf("0.00"));
							}
							localpartslist.add(pq);
						}
					}
				}
				if (orderselect.equalsIgnoreCase("locallightradio")) {
					feedback = adminService.createLocalLightsOrders(localpartslist, locallightvendors, branch,
							localseries, repository);
				} else if (orderselect.equalsIgnoreCase("localtransradio")) {
					feedback = adminService.createTransAmericaOrder(localpartslist, locallightvendors, branch,
							localseries);
				} else if (orderselect.equalsIgnoreCase("localwestinradio")) {
					feedback = adminService.createWestinOrder(localpartslist, locallightvendors, branch, localseries);
				}

				mav.setViewName("localorderspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("orderdeatilslist", orderdeatilslist);
				mav.addObject("stockvalue", stockvalue);
				mav.addObject("ordervalue", ordervalue);
				mav.addObject("listsize", orderdeatilslist.size());
				mav.addObject("orderselect", orderselect);
				mav.addObject("nsf", nsf);
				mav.addObject("feedback", feedback);
				mav.addObject("forecastdays", forecastdays);
				return mav;

			} else {

				mav.setViewName("localorderspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}

		}
	}

	@RequestMapping(value = "vendorcomparison", method = RequestMethod.POST)
	public ModelAndView processVendorPrice(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate, @RequestParam("stockvalue") String stockvalue,
			@RequestParam("subcategoryselected") String subcategoryselected, @RequestParam("factor") String factor,
			@RequestParam("pricemethod") String pricemethod, HttpServletResponse response, HttpSession session,
			ModelAndView mav

	) {

		AppUser user = (AppUser) session.getAttribute("user");

		if (subcategorylistdd.size() == 0) {
			subcategorylistdd = mainService.getAllSubCategory();
		}
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			categorysaleslist = adminService.getVendorPrices(analyticsfromdate, analyticstodate,
					subcategorylistdd.get(subcategoryselected), factor, stockvalue);
			mav.setViewName("vendorpricecomparisonpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", analyticsfromdate);
			mav.addObject("analyticstodate", analyticstodate);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("factor", factor);
			mav.addObject("stockvalue", stockvalue);
			mav.addObject("categorysaleslist", categorysaleslist);
			mav.addObject("pricemethod", pricemethod);

			return mav;

		}
	}

	@RequestMapping("savevendorcomparison")
	public ResponseEntity<?> saveVendorPrice(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate, @RequestParam("stockvalue") String stockvalue,

			@RequestParam("subcategoryselected") String subcategoryselected, @RequestParam("factor") String factor,
			@RequestParam("pricemethod") String pricemethod, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		@SuppressWarnings("unused")
		AppUser user = (AppUser) session.getAttribute("user");
		if (subcategorylistdd.size() == 0) {
			subcategorylistdd = mainService.getAllSubCategory();
		}

		try {

			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = bvsexcel.createVendorPriceExcelFile(categorysaleslist, filePath);

			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length());
			// sets HTTP header
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
			byte[] byteBuffer = new byte[BUFSIZE];
			DataInputStream in = new DataInputStream(new FileInputStream(file));

			// reads the file's bytes and writes them to the response
			// stream
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
				outStream.write(byteBuffer, 0, length);
			}

			IOUtils.copy(in, outStream);
			IOUtils.closeQuietly(in);

			response.flushBuffer();

			return new ResponseEntity<Object>(HttpStatus.OK);

		} catch (NumberFormatException e) {
			LOGGER.error("NumberFormatException: processExistingOrders##" + e.getMessage().toString());
			return new ResponseEntity<Object>(HttpStatus.OK);

		} catch (IOException e) {
			LOGGER.error("IOException: processExistingOrders##" + e.getMessage().toString());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

	}
}
