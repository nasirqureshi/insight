package com.bvas.insight.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.entity.Customer;
import com.bvas.insight.service.CustomerService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;
import com.bvas.insight.utilities.OrderNotFoundException;

@Scope("session")
@Controller
@SessionAttributes({ "user", "customer" })
@RequestMapping("customers")
public class CustomerController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	protected Customer customer = new Customer();

	@Autowired
	@Qualifier("customerService")
	protected CustomerService customerService;

	DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	protected Customer updatecustomer = new Customer();

	@RequestMapping("customermaintenancecreate")
	public ModelAndView customermaintenanceCreate(
			@RequestParam("customermaintenancemode") String customermaintenancemode,
			@RequestParam("searchcustomerid") String searchcustomerid,
			@RequestParam("taxexpiredate") String taxexpiredate, Model map, HttpSession session, ModelAndView mav)
			throws ParseException {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#customermaintenanceupdate");
			if (paymentlistdd.size() == 0) {
				paymentlistdd = mainService.getAllPaymentPolicy();
			}
			customermaintenancemode = "update";
			String payterms = paymentlistdd.get(request.getParameter("terms").trim());
			customer.setCustomerid(request.getParameter("customerid").trim());
			customer.setCompanyname(request.getParameter("companyname").trim());
			customer.setContactname(request.getParameter("contactname").trim());
			customer.setContacttitle(request.getParameter("contacttitle").trim());
			customer.setTerms(request.getParameter("terms").trim());
			customer.setTaxid(request.getParameter("taxid").trim());
			customer.setTaxidnumber(request.getParameter("taxidnumber").trim());
			customer.setNotes(request.getParameter("notes").trim());
			customer.setPaymentterms(payterms);
			customer.setCreditbalance(new BigDecimal(request.getParameter("creditbalance").trim()));
			customer.setCreditlimit(new BigDecimal(request.getParameter("creditlimit").trim()));
			customer.setCustomerlevel(Integer.parseInt(request.getParameter("customerlevel").trim()));
			customer.setAddress1(request.getParameter("address1").trim());
			customer.setAddress2(request.getParameter("address2").trim());
			customer.setTown(request.getParameter("town").trim());
			customer.setSt(request.getParameter("st").trim());
			customer.setRte(request.getParameter("rte").trim());
			customer.setPh(request.getParameter("ph").trim());
			customer.setZip(request.getParameter("zip").trim());
			customer.setEmailaddress(request.getParameter("emailaddress").trim());
			customer.setOpenaccountdate(formatter.parse(request.getParameter("openaccountdate").trim()));
			customer.setTaxidexpireon(formatter.parse(request.getParameter("taxidexpireon").trim()));
			customer.setAccountopenedby(user.getUsername());
			customer.setAccountreferredby(request.getParameter("accountreferredby").trim());
			customerService.updateCustomerMaintenance(customer);

			mav.clear();
			mav.setViewName("customermaintenancepage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("customermaintenancemode", customermaintenancemode);
			mav.addObject("searchcustomerid", searchcustomerid);
			mav.addObject("customer", customer);
			mav.addObject("paymentlistdd", paymentlistdd);
			return mav;

		}

	}

	@RequestMapping("customermaintenancesearch")
	public ModelAndView customermaintenanceSearch(
			@RequestParam("customermaintenancemode") String customermaintenancemode,
			@RequestParam("searchcustomerid") String searchcustomerid,
			@RequestParam("taxexpiredate") String taxexpiredate, Model map, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#customermaintenancesearch");
			if (paymentlistdd.size() == 0) {
				paymentlistdd = mainService.getAllPaymentPolicy();
			}
			if ((searchcustomerid != null) && (!searchcustomerid.equalsIgnoreCase(""))) {
				customer = customerService.getCustomerDetails(searchcustomerid.trim());
				if (customer != null) {
					customermaintenancemode = "update";
					String terms = customerService.getTermsFromPaymentTerms(customer.getPaymentterms());
					customer.setTerms(terms);
					mav.clear();
					mav.setViewName("customermaintenancepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("customermaintenancemode", customermaintenancemode);
					mav.addObject("searchcustomerid", searchcustomerid);
					mav.addObject("customer", customer);
					mav.addObject("paymentlistdd", paymentlistdd);
					return mav;
				} else {
					customer = customerService.getCustomerDetailsNoNull(searchcustomerid.trim());
					customermaintenancemode = "create";
					mav.clear();
					mav.setViewName("customermaintenancepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("customermaintenancemode", customermaintenancemode);
					mav.addObject("searchcustomerid", searchcustomerid);
					mav.addObject("customer", customer);
					mav.addObject("paymentlistdd", paymentlistdd);
					return mav;
				}

			} else {
				customer = customerService.getCustomerDetailsNoNull(searchcustomerid.trim());
				customermaintenancemode = "create";
				mav.clear();
				mav.setViewName("customermaintenancepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("customermaintenancemode", customermaintenancemode);
				mav.addObject("searchcustomerid", searchcustomerid);
				mav.addObject("customer", customer);
				mav.addObject("paymentlistdd", paymentlistdd);
				return mav;
			}
		}
	}

	@RequestMapping("customermaintenanceupdate")
	public ModelAndView customermaintenanceUpdate(
			@RequestParam("customermaintenancemode") String customermaintenancemode,
			@RequestParam("searchcustomerid") String searchcustomerid,
			@RequestParam("taxexpiredate") String taxexpiredate, Model map, HttpSession session, ModelAndView mav)
			throws ParseException {
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#customermaintenanceupdate");
			// LOGGER.info(customer.getCustomerid());
			if (paymentlistdd.size() == 0) {
				paymentlistdd = mainService.getAllPaymentPolicy();
			}
			customermaintenancemode = "update";
			String payterms = paymentlistdd.get(request.getParameter("terms").trim());
			customer.setCustomerid(request.getParameter("customerid").trim());
			customer.setCompanyname(request.getParameter("companyname").trim());
			customer.setContactname(request.getParameter("contactname").trim());
			customer.setContacttitle(request.getParameter("contacttitle").trim());
			customer.setTerms(request.getParameter("terms").trim());
			customer.setTaxid(request.getParameter("taxid").trim());
			customer.setTaxidnumber(request.getParameter("taxidnumber").trim());
			customer.setNotes(request.getParameter("notes").trim());
			customer.setPaymentterms(payterms);
			customer.setCreditbalance(new BigDecimal(request.getParameter("creditbalance").trim()));
			customer.setCreditlimit(new BigDecimal(request.getParameter("creditlimit").trim()));
			customer.setCustomerlevel(Integer.parseInt(request.getParameter("customerlevel").trim()));
			customer.setAddress1(request.getParameter("address1").trim());
			customer.setAddress2(request.getParameter("address2").trim());
			customer.setTown(request.getParameter("town").trim());
			customer.setSt(request.getParameter("st").trim());
			customer.setRte(request.getParameter("rte").trim());
			customer.setPh(request.getParameter("ph").trim());
			customer.setZip(request.getParameter("zip").trim());
			customer.setEmailaddress(request.getParameter("emailaddress").trim());
			customer.setOpenaccountdate(formatter.parse(request.getParameter("openaccountdate").trim()));
			customer.setTaxidexpireon(formatter.parse(request.getParameter("taxidexpireon").trim()));
			customerService.updateCustomerMaintenance(customer);

			mav.clear();
			mav.setViewName("customermaintenancepage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("customermaintenancemode", customermaintenancemode);
			mav.addObject("searchcustomerid", searchcustomerid);
			mav.addObject("customer", customer);
			mav.addObject("paymentlistdd", paymentlistdd);
			return mav;

		}
	}

	@RequestMapping("customerupdatesearch")
	public ModelAndView customerSearch(@RequestParam("customersearchno") String customersearchno,
			@RequestParam("customerupdatemode") String customerupdatemode, Model map, HttpSession session,
			ModelAndView mav) throws ParseException {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			LOGGER.info("#1");

			if ((customersearchno != null) && (!customersearchno.equalsIgnoreCase(""))) {

				Customer updatecustomer = customerService.getCustomerDetails(customersearchno.trim());

				if (updatecustomer != null) {
					mav.clear();
					if (updatecustomer.getCustomerid().equalsIgnoreCase("")) {
						customerupdatemode = "search";
					} else {
						customerupdatemode = "update";
					}

					LOGGER.info("#2");
					mav.setViewName("customerupdatepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("customerupdatemode", customerupdatemode);
					mav.addObject("customersearchno", customersearchno);
					mav.addObject("updatecustomer", updatecustomer);
					return mav;
				} else {
					mav.clear();
					customerupdatemode = "search";
					LOGGER.info("#3");
					mav.setViewName("customerupdatepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("customerupdatemode", customerupdatemode);
					mav.addObject("customersearchno", customersearchno);

					return mav;
				}

			} else {
				mav.clear();
				customerupdatemode = "search";
				mav.setViewName("customerupdatepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("customerupdatemode", customerupdatemode);
				mav.addObject("customersearchno", "");
				mav.addObject("customer", new Customer());
				return mav;
			}
		}
	}

	@RequestMapping("customerupdateupdate")
	public ModelAndView customerUpdate(@RequestParam("customersearchno") String customersearchno,
			@RequestParam("customerupdatemode") String customerupdatemode, Model map, HttpSession session,
			ModelAndView mav) throws ParseException {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			LOGGER.info("#a");

			if ((customersearchno != null) && (!customersearchno.equalsIgnoreCase(""))) {

				Customer updatecustomer = customerService.getCustomerDetails(customersearchno.trim());

				if (updatecustomer != null) {
					mav.clear();
					if (updatecustomer.getCustomerid().equalsIgnoreCase("")) {
						customerupdatemode = "search";
					} else {
						customerupdatemode = "update";
					}

					updatecustomer.setPh(request.getParameter("ph").trim());
					updatecustomer.setCell(request.getParameter("cell").trim());
					updatecustomer.setFax(request.getParameter("fax").trim());
					updatecustomer.setEmailaddress(request.getParameter("emailaddress").trim());
					customerService.updateCustomerOnly(updatecustomer);

					LOGGER.info("#b");
					mav.setViewName("customerupdatepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("customerupdatemode", customerupdatemode);
					mav.addObject("customersearchno", customersearchno);
					mav.addObject("updatecustomer", updatecustomer);
					return mav;
				} else {
					mav.clear();
					customerupdatemode = "search";
					LOGGER.info("#c");
					mav.setViewName("customerupdatepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("customerupdatemode", customerupdatemode);
					mav.addObject("customersearchno", customersearchno);

					return mav;
				}

			} else {
				mav.clear();
				customerupdatemode = "search";
				mav.setViewName("customerupdatepage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("customerupdatemode", customerupdatemode);
				mav.addObject("customersearchno", "");
				mav.addObject("customer", new Customer());
				return mav;
			}
		}
	}

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

}
