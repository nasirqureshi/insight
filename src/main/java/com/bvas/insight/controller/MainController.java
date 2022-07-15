package com.bvas.insight.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.ButtonLogic;
import com.bvas.insight.data.CreateVendorOrder;
import com.bvas.insight.data.DisplayInvoice;
import com.bvas.insight.data.DisplayInvoiceDetail;
import com.bvas.insight.data.InvoiceStatus;
import com.bvas.insight.entity.Address;
import com.bvas.insight.entity.BouncedChecks;
import com.bvas.insight.entity.Customer;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.service.InvoiceService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user", "customerList" })
public class MainController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	protected DisplayInvoice displayinvoice = new DisplayInvoice("");

	protected List<DisplayInvoiceDetail> displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();

	@Autowired
	@Qualifier("invoiceService")
	protected InvoiceService invoiceService;

	@RequestMapping(value = "/nav", method = RequestMethod.GET)
	public ModelAndView landing(Locale locale, ModelAndView model, HttpServletResponse response) {

		// LOGGER.info("Welcome login! The client locale is {}", locale);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		model.setViewName("loginpage");
		model.addObject("branch", branch);
		model.addObject("appcss", appcss);
		return model;

	}

	@RequestMapping(value = "/main", method = RequestMethod.POST, params = { "mainpageselect" })
	public ModelAndView processMain(@RequestParam("mainpageselect") String mainpageselect, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if (yearslistdd.size() == 0) {
			yearslistdd = mainService.getYears();
		}

		if (makelistdd.size() == 0) {
			makelistdd = mainService.getAllManufacturersMap();
		}

		if (subcategorylistdd.size() == 0) {
			subcategorylistdd = mainService.getAllSubCategory();
		}

		if (categorylistdd.size() == 0) {
			categorylistdd = mainService.getAllCategory();
		}

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (mainpageselect.equalsIgnoreCase("reportpage")) {
				mav.setViewName(mainpageselect);
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("reportfromdate", InsightUtils.getNewUSDate());
				mav.addObject("reporttodate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("logout")) {
				mav.clear();
				mav.setViewName("loginpage");
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("existingorderpage")) {

				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}
				mav.clear();
				mav.setViewName("existingorderpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("addtoorderpage")) {

				mav.clear();
				mav.setViewName("addtoorderpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("fromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("todate", InsightUtils.getCurrentSQLDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("utilitiespage")) {
				mav.clear();
				mav.setViewName("utilitiespage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("stockcheckpartno", "");
				mav.addObject("locationpartno", "");
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("addtocontainerpage")) {
				mav.clear();
				mav.setViewName("addtocontainerpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("vendoritemsuploadpage")) {
				mav.clear();
				mav.setViewName("vendoritemsuploadpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("reportpage")) {
				mav.clear();
				mav.setViewName("reportpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("reportfromdate", InsightUtils.getNewUSDate());
				mav.addObject("reporttodate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("deadinventorypage")) {
				mav.clear();
				mav.setViewName("deadinventorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("salesanalysispage")) {
				mav.clear();
				mav.setViewName("salesanalysispage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("analyticspage")) {
				mav.clear();
				mav.setViewName("analyticspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("duration", "Yearly");
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("feedback", "");
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("vendorpricecomparisonpage")) {
				mav.clear();
				mav.setViewName("vendorpricecomparisonpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("stockvalue", "0");
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("factor", "1.00");
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("addvendorpartpage")) {

				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}
				mav.clear();
				mav.setViewName("addvendorpartpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("scanorderpage")) {

				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}
				mav.clear();
				mav.setViewName("scanorderpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("localorderspage")) {
				mav.clear();
				mav.setViewName("localorderspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("stockvalue", 0);
				mav.addObject("ordervalue", 0);
				mav.addObject("reordervalue", 1);
				mav.addObject("forecastdays", 15);
				mav.addObject("orderselect", "locallightradio");
				mav.addObject("feedback", "");
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("invoicenotdeliveredpage")) {

				if (driverlistdd.size() == 0) {
					driverlistdd = mainService.getAllDriver();
				}

				if (routelistdd.size() == 0) {
					routelistdd = mainService.getAllRoute();
				}

				mav.clear();
				mav.setViewName("invoicenotdeliveredpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("driverlistdd", driverlistdd);
				mav.addObject("routeselected", "");
				mav.addObject("routefilename", "");
				mav.addObject("driverselected", "");
				mav.addObject("wildtext", "");
				mav.addObject("routelistdd", routelistdd);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("redeliverypage")) {

				mav.clear();
				mav.setViewName(mainpageselect);
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("bouncecheckpage")) {
				mav.clear();
				mav.setViewName("bouncecheckpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("bouncedchecks", new BouncedChecks());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("writeoffpage")) {
				mav.clear();
				mav.setViewName("writeoffpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("closeinvoicespage")) {

				mav.clear();
				mav.setViewName("closeinvoicepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("accountingreports")) {
				mav.clear();
				mav.setViewName("accountingreports");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("custhistorypage")) {
				mav.clear();
				mav.setViewName("custhistorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("partlinkoemsearchpage")) {

				mav.clear();
				mav.setViewName(mainpageselect);
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("createordernopage")) {

				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}
				mav.clear();
				mav.setViewName("createordernopage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("partmakerpage")) {
				if (subcategorylistdd.size() == 0) {
					subcategorylistdd = mainService.getAllSubCategory();
				}
				if (makelistdd.size() == 0) {
					makelistdd = mainService.getAllManufacturersMap();
				}
				mav.clear();
				mav.setViewName("partmakerpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("makelistdd", makelistdd);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("purchasingfunctionpage")) {

				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}

				if (branchlistdd.size() == 0) {
					branchlistdd = mainService.getAllOtherBranches(branch);
				}

				mav.clear();
				mav.setViewName("purchasingfunctionpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("branchlistdd", branchlistdd);
				mav.addObject("orderno", 0);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("containerpage")) {

				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}
				mav.clear();
				mav.setViewName("containerpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("initiateorderpage")) {

				if (categorylistdd.size() == 0) {
					categorylistdd = mainService.getAllCategory();
				}

				mav.clear();
				mav.setViewName("initiateorderpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
				mav.addObject("categorylistdd", categorylistdd);
				mav.addObject("categoryselected", "ALL");
				mav.addObject("makelistdd", makelistdd);
				mav.addObject("makeselected", "ALL");
				mav.addObject("typeoforderrdio", "taiwanrdio");
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("inventorytransferpage")) {

				if (branchlistdd.size() == 0) {
					branchlistdd = mainService.getAllActiveBranches(branch);
					if (!branchlistdd.containsKey("ALL")) {
						branchlistdd.put("ALL", "ALL");
					}
				}
				if (subcategorylistdd.size() == 0) {
					subcategorylistdd = mainService.getAllSubCategory();
				}

				if (yearslistdd.size() == 0) {
					yearslistdd = mainService.getYears();
				}

				if (makelistdd.size() == 0) {
					makelistdd = mainService.getAllManufacturersMap();
				}

				mav.clear();
				mav.setViewName("inventorytransferpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("subcategoryselected", subcategorylistdd.get(0));
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("stocklimit", 5);
				mav.addObject("branchlistdd", branchlistdd);
				mav.addObject("branchselected", branchlistdd.get(0));
				mav.addObject("factor", 10);
				mav.addObject("yearslistdd", yearslistdd);
				mav.addObject("makelistdd", makelistdd);
				mav.addObject("makeselected", "ALL");
				mav.addObject("selectYear", "2000");
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("formatelabel")) {
				mav.clear();
				mav.setViewName("formatelabelpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("updatepartsprice")) {
				mav.clear();
				mav.setViewName("updatepartsprices");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("createnewpartspage")) {
				if (subcategorylistdd.size() == 0) {
					subcategorylistdd = mainService.getAllSubCategory();
				}
				if (makelistdd.size() == 0) {
					makelistdd = mainService.getAllManufacturersMap();
				}
				mav.clear();
				mav.setViewName("createnewpartspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("makelistdd", makelistdd);
				return mav;
			}

			else if (mainpageselect.equalsIgnoreCase("salesreportpage")) {
				mav.clear();
				mav.setViewName("salesreportpage");
				mav.addObject("tab", "salesdetails");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				String endDate = dateFormat.format(calendar.getTime());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				String startDate = dateFormat.format(calendar.getTime());
				mav.addObject("startDate", startDate);
				mav.addObject("endDate", endDate);
				// mav.addObject("salesDetails", reportService.getSaleDetails(startDate,
				// endDate));
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("salesdetailspage")) {
				mav.clear();
				mav.setViewName("salesdetailspage");
				mav.addObject("spList", mainService.getAllSalesPersons());
				mav.addObject("tab", "salesdetails");
				mav.addObject("startDate", InsightUtils.getCurrentSQLDate());
				mav.addObject("endDate", InsightUtils.getCurrentSQLDate());
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("comparepricepage")) {
				mav.clear();
				mav.setViewName("comparepricepage");

				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("stockauditpage")) {
				mav.clear();
				mav.setViewName("stockauditpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());

				return mav;
			} else if (mainpageselect.equalsIgnoreCase("invoicespage")) {

				Integer yearselectedint = Calendar.getInstance().get(Calendar.YEAR);

				if (shippinglistdd.size() == 0) {
					shippinglistdd = mainService.getShippingTypes();
				}
				if (makelistdd.size() == 0) {
					makelistdd = mainService.getAllManufacturersMap();
				}
				if (yearslistdd.size() == 0) {
					yearslistdd = mainService.getYears();
				}
				displayinvoice = new DisplayInvoice(user.getUsername());
				displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();
				displayinvoicedetails.clear();
				displayinvoice.setDisplayinvoicedetailslist(displayinvoicedetails);
				super.invoicestatus.setInvoicemode("proceed");
				mav.clear();
				mav.setViewName("invoicespage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("searchcustomernumber", "1111111111");
				mav.addObject("customerList", mainService.getAllCustomer());
				mav.addObject("billtoaddress", new Address("Bill"));
				mav.addObject("shiptoaddress", new Address("Ship"));
				mav.addObject("searchinvoicenumber", "0");
				mav.addObject("selectpartnumber", "");
				mav.addObject("makelistdd", makelistdd);
				mav.addObject("yearslistdd", yearslistdd);
				mav.addObject("yearselected", yearselectedint.toString());
				mav.addObject("displayinvoice", displayinvoice);
				mav.addObject("invoicestatus", new InvoiceStatus());
				mav.addObject("partnoinvoicedetails", "");
				mav.addObject("buttonlogic", new ButtonLogic());
				mav.addObject("shippinglistdd", shippinglistdd);
				// mav.addObject("shipvia", shippinglistdd.firstKey());
				mav.addObject("customer", null);

				// LOGGER.info("SHIPVIA:" + shippinglistdd.firstKey());

				return mav;
			}

			else if (mainpageselect.equalsIgnoreCase("saleshistorypage")) {
				mav.clear();
				mav.setViewName("saleshistorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("feedback", "");
				return mav;
			}

			else if (mainpageselect.equalsIgnoreCase("procurementpage")) {
				mav.clear();
				if (ordertypelistdd.isEmpty()) {
					if (ordertypelistdd.size() <= 0) {
						ordertypelistdd = mainService.getAllOrderType();
					}
				}
				if (branchlistdd.size() == 0) {
					branchlistdd = mainService.getAllOtherBranches(branch);
				}

				mav.setViewName("procurementpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("ordertypelistdd", ordertypelistdd);

				mav.addObject("ordertypeselected", "-");
				mav.addObject("stocklimit", "200");
				mav.addObject("onorderlimit", "200");
				mav.addObject("orderlimit", "10000");
				mav.addObject("branchlistdd", branchlistdd);
				mav.addObject("branchselected", "");
				mav.addObject("feedback", "");
				return mav;
			}

			else if (mainpageselect.equalsIgnoreCase("uploadnewpartspage")) {
				mav.clear();
				mav.setViewName("uploadnewpartspage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("partmaintenancepage")) {
				if (subcategorylistdd.size() == 0) {
					subcategorylistdd = mainService.getAllSubCategory();
				}
				if (makemodellistdd.size() == 0) {
					makemodellistdd = mainService.getAllMakeModelMap();
				}
				if (ordertypelistdd.size() == 0) {
					ordertypelistdd = mainService.getAllOrderType();
				}

				mav.clear();
				mav.setViewName("partmaintenancepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("makemodellistdd", makemodellistdd);
				mav.addObject("ordertypelistdd", ordertypelistdd);
				mav.addObject("searchpartno", "");
				mav.addObject("percent", "0.00");
				mav.addObject("parts", new Parts(""));
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("customermaintenancepage")) {

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				String taxexpiredate = dateFormat.format(calendar.getTime());

				if (paymentlistdd.size() == 0) {
					paymentlistdd = mainService.getAllPaymentPolicy();
				}
				mav.clear();
				mav.setViewName("customermaintenancepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("customermaintenancemode", "create");
				mav.addObject("searchcustomerid", "");
				mav.addObject("paymentlistdd", paymentlistdd);
				mav.addObject("taxexpiredate", taxexpiredate);
				mav.addObject("customer", new Customer());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("reorderlevelqtytoorderpage")) {
				mav.clear();
				mav.setViewName("reorderlevelqtytoorderpage");
				mav.addObject("reorderLevelData", reorderLevelService.getCycleInfo());
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("createvendororderpage")) {
				mav.clear();
				mav.setViewName("createvendororderpage");
				mav.addObject("vendorOrder", new CreateVendorOrder());
				mav.addObject("locationList", locationList);
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("routeSaleAnalysispage")) {
				if (routelistdd.size() == 0) {
					routelistdd = mainService.getAllRoute();
				}
				mav.clear();
				mav.setViewName("routeSaleAnalysisReportpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("routelistdd", routelistdd);
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("updatepartcostanddiscountpricepage")) {
				mav.clear();
				mav.setViewName("updatepartcostanddiscountpricepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			} else if (mainpageselect.equalsIgnoreCase("customerupdatepage")) {
				mav.clear();
				mav.setViewName("customerupdatepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("customerupdatemode", "search");
				mav.addObject("customersearchno", "");

				return mav;

			}

			else {
				mav.clear();
				mav.setViewName(mainpageselect);
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}
		}
	}
}
