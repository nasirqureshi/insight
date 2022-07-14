package com.bvas.insight.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.ButtonLogic;
import com.bvas.insight.data.DisplayInvoice;
import com.bvas.insight.data.DisplayInvoiceDetail;
import com.bvas.insight.data.InvoiceStatus;
import com.bvas.insight.entity.Address;
import com.bvas.insight.entity.Customer;
import com.bvas.insight.entity.Invoice;
import com.bvas.insight.entity.InvoiceDetails;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.service.InvoiceService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;
import com.bvas.insight.utilities.InvoiceErrorException;
import com.bvas.insight.utilities.OrderNotFoundException;

@Scope("session")
@Controller
@SessionAttributes({ "user", "displayinvoice", "buttonlogic" })
@RequestMapping("invoice")
public class InvoiceController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

	protected static final Map<String, String> shipviadd = new HashMap<String, String>();

	static {
		shipviadd.put("Deliver", "Deliver");
		shipviadd.put("Pick-Up", "Pick-Up");
		shipviadd.put("UPS", "UPS");
	}

	protected BigDecimal bigdecimalminusone = new BigDecimal("-1.00");

	protected BigDecimal bigdecimalzero = new BigDecimal("0.00");

	protected Address billtoaddress = new Address();

	protected ButtonLogic buttonlogic = new ButtonLogic();

	protected Customer customer = null;

	protected DisplayInvoice displayinvoice = new DisplayInvoice("");

	protected List<DisplayInvoiceDetail> displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();

	protected Parts displaypart = null;

	protected Invoice invoice = null;

	@Autowired
	@Qualifier("invoiceService")
	protected InvoiceService invoiceService;

	protected Map<String, String> relmakemodellist = new HashMap<String, String>();

	protected Map<String, String> relpartslist = new LinkedHashMap<String, String>();
	protected Address shiptoaddress = new Address();

	@RequestMapping("addpartstoinvoice")
	public ModelAndView addPartToInvoice(@RequestParam("partselected") String partselected,
			@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("makeselected") String makeselected, @RequestParam("modelselected") String modelselected,
			@RequestParam("yearselected") String yearselected, @RequestParam("shipvia") String shipvia, Model map,
			HttpSession session, ModelAndView mav) {

		// LOGGER.info("/addpartstoinvoice");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {
			int index = 0;
			int selectedindex = 0;
			@SuppressWarnings("unused")
			int removeindex = 0;

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			displayinvoicedetails = displayinvoice.getDisplayinvoicedetailslist();

			Boolean partnumberispresent = false;

			if (displayinvoicedetails != null) {
				if (displayinvoicedetails.size() > 0) {
					for (DisplayInvoiceDetail displayinvoicedetail : displayinvoicedetails) {
						if (displayinvoicedetail.getPartnumber().trim().equalsIgnoreCase("")) {
							removeindex = index;
						}
						if (displayinvoicedetail.getPartnumber().trim().equalsIgnoreCase(displaypart.getPartno())) {
							partnumberispresent = true;
							selectedindex = index;
						}
						index++;
					}
					if (partnumberispresent) {

						DisplayInvoiceDetail duplicatepart = displayinvoicedetails.get(selectedindex);
						Integer quantity = duplicatepart.getQuantity();
						displayinvoicedetails.remove(selectedindex);
						duplicatepart.setQuantity(quantity + 1);
						displayinvoicedetails.add(duplicatepart);

					} else {
						DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();
						addedpart.setActualprice(displaypart.getActualprice());
						addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));
						addedpart.setListprice(displaypart.getListprice());
						addedpart.setLocation(displaypart.getLocation());
						addedpart.setMakemodelname(displaypart.getMakemodelname());
						addedpart.setManufacturername(displaypart.getManufacturername());
						addedpart.setPartdescription(displaypart.getPartdescription());
						addedpart.setPartnumber(displaypart.getPartno());
						addedpart.setQuantity(1);
						addedpart.setReorderlevel(displaypart.getReorderlevel());
						addedpart.setUnitsinstock(displaypart.getUnitsinstock());
						addedpart.setUnitsonorder(displaypart.getUnitsonorder());
						addedpart.setYear(displaypart.getYear());
						addedpart.setSoldprice(displaypart.getCalculatedprice());
						displayinvoicedetails.add(addedpart);
					}

				} else {
					displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();
					DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();
					addedpart.setActualprice(displaypart.getActualprice());
					addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));
					addedpart.setListprice(displaypart.getListprice());
					addedpart.setLocation(displaypart.getLocation());
					addedpart.setMakemodelname(displaypart.getMakemodelname());
					addedpart.setManufacturername(displaypart.getManufacturername());
					addedpart.setPartdescription(displaypart.getPartdescription());
					addedpart.setPartnumber(displaypart.getPartno());
					addedpart.setQuantity(1);
					addedpart.setReorderlevel(displaypart.getReorderlevel());
					addedpart.setUnitsinstock(displaypart.getUnitsinstock());
					addedpart.setUnitsonorder(displaypart.getUnitsonorder());
					addedpart.setYear(displaypart.getYear());
					addedpart.setSoldprice(displaypart.getCalculatedprice());
					displayinvoicedetails.add(addedpart);
				}
			} else {
				displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();
				DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();
				addedpart.setActualprice(displaypart.getActualprice());
				addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));
				addedpart.setListprice(displaypart.getListprice());
				addedpart.setLocation(displaypart.getLocation());
				addedpart.setMakemodelname(displaypart.getMakemodelname());
				addedpart.setManufacturername(displaypart.getManufacturername());
				addedpart.setPartdescription(displaypart.getPartdescription());
				addedpart.setPartnumber(displaypart.getPartno());
				addedpart.setQuantity(1);
				addedpart.setReorderlevel(displaypart.getReorderlevel());
				addedpart.setUnitsinstock(displaypart.getUnitsinstock());
				addedpart.setUnitsonorder(displaypart.getUnitsonorder());
				addedpart.setYear(displaypart.getYear());
				addedpart.setSoldprice(displaypart.getCalculatedprice());
				displayinvoicedetails.add(addedpart);
			}

			displayinvoice.setDisplayinvoicedetailslist(displayinvoicedetails);

			Address billtoaddress = new Address();
			Address shiptoaddress = new Address();
			if (customer != null) {
				billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust", null,
						displayinvoice.getInvoicenumber());
				shiptoaddress = invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust", null,
						displayinvoice.getInvoicenumber());
			}
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			mav.addObject("modelselected", modelselected);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselected);
			mav.addObject("partselected", partselected);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displaypart", displaypart);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			mav.addObject("relpartslist", relpartslist);
			mav.addObject("makeselected", makeselected);
			mav.addObject("relpartslist", relpartslist);
			if (relpartslist != null) {
				if (relpartslist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}

			return mav;
		}
	}// addpartstoinvoice

	private String applyTaxAndOthers(Invoice orgInvoice, DisplayInvoice returnInvoice, Customer customer) {
		String errMsg = "";
		if (!orgInvoice.getDiscount().equals(BigDecimal.ZERO) && returnInvoice.getDiscount().equals(BigDecimal.ZERO)) {
			returnInvoice.setDiscount(returnInvoice.getDiscount().multiply(BigDecimal.valueOf(-1)));
		}
		BigDecimal invoiceTotal = BigDecimal.ZERO;
		BigDecimal invoiceTax = BigDecimal.ZERO;
		for (DisplayInvoiceDetail form : returnInvoice.getDisplayinvoicedetailslist()) {
			BigDecimal costPrice = BigDecimal.ZERO;
			// BigDecimal listPrice = BigDecimal.ZERO;
			Integer quantity = 0;
			// boolean isThereReturnedParts = false;
			costPrice = form.getSoldprice();
			// listPrice = form.getListprice();
			if (!form.getPartnumber().startsWith("XX")) {
				// double costFromPart =
				// invoiceService.getCostPrice(invoiceService.getParts(form.getPartnumber()),
				// customerLevel);
			}
			quantity = form.getQuantity();
			if (quantity >= 0) {
				errMsg = "Part#" + form.getPartnumber() + ": Quantity should be negative for return items.";

			}
			// if (quantity < 0) {
			// isThereReturnedParts = true;
			if (invoiceService.isDoubleReturn(returnInvoice.getInvoicenumber(), returnInvoice.getReturnedinvoice(),
					form.getPartnumber(), quantity)) {
				errMsg = "The part " + form.getPartnumber() + " already has a returned invoice";
			} else {
				List<InvoiceDetails> invoicedetails = invoiceService
						.getInvoiceDetailsFromInvoiceNumber(returnInvoice.getReturnedinvoice());
				for (InvoiceDetails idt : invoicedetails) {
					if (idt.getPartnumber().trim().equalsIgnoreCase(form.getPartnumber())) {
						BigDecimal cst = idt.getSoldprice();
						if (!cst.equals(BigDecimal.ZERO) && !cst.equals(costPrice)) {
							costPrice = cst;
							form.setSoldprice(costPrice);
						}
						break;
					}
				}
			}
			// }
			BigDecimal tot = costPrice.multiply(BigDecimal.valueOf(quantity));
			/*
			 * What is this if(partNo.startsWith("XX")) { tot = tot * -1; }
			 */
			invoiceTotal = invoiceTotal.add(tot);
		}
		BigDecimal discount = returnInvoice.getDiscount();
		BigDecimal amountVowed = BigDecimal.ZERO;
		String taxId = customer.getTaxid();
		if (taxId != null && !taxId.equals("") && taxId.equals("Y")) {
			invoiceTax = BigDecimal.ZERO;
		} else {
			/*
			 * Is it right
			 */
			// invoiceTax = (invoiceTotal - discount) * 0.1025;
			invoiceTax = (invoiceTotal.subtract(discount))
					.multiply((new BigDecimal(appliedtax)).divide(BigDecimal.valueOf(100)));
		}
		amountVowed = invoiceTotal.add(invoiceTax).subtract(discount);
		returnInvoice.setInvoicetotal(invoiceTotal);
		returnInvoice.setTax(invoiceTax);
		returnInvoice.setDiscount(discount);
		returnInvoice.setBalance(amountVowed);
		return errMsg;
	}

	@RequestMapping("createnewinvoice")
	public ModelAndView createInvoice(// @RequestParam("partselected") String
			// partselected,
			@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("makeselected") String makeselected, @RequestParam("shipvia") String shipvia,
			@RequestParam("yearselected") String yearselected, Model map, HttpSession session, ModelAndView mav) {

		// LOGGER.info("/createinvoice");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			@SuppressWarnings("unused")
			int removeindex = 0;
			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			String discount = request.getParameter("displaydiscount");

			String billsameship = request.getParameter("billsameship");

			if (!(billsameship == null)) {
				displayinvoice.setShipto(customer.getContactname());
				displayinvoice.setShipattention(customer.getContactname());
				shiptoaddress.setType("Ship");
				shiptoaddress = billtoaddress;
			} else {
				shiptoaddress = new Address();
				shiptoaddress.setAddr1(request.getParameter("sa_addr1"));
				shiptoaddress.setAddr2(request.getParameter("sa_addr2"));
				shiptoaddress.setCity(request.getParameter("sa_city"));
				shiptoaddress.setState(request.getParameter("sa_state"));
				shiptoaddress.setCountry(request.getParameter("sa_country"));
				shiptoaddress.setPostalcode(request.getParameter("sa_zip"));
				shiptoaddress.setRegion(request.getParameter("sa_region"));

				displayinvoice.setShipto(customer.getContactname());
				displayinvoice.setShipattention(customer.getContactname());
				shiptoaddress.setType("Ship");
				shiptoaddress.setWho("Cust");
				shiptoaddress.setDatecreated(displayinvoice.getOrderdate());
				shiptoaddress.setPhone("");
				shiptoaddress.setExt("");
				shiptoaddress.setFax("");

			}

			invoicestatus = invoiceService.verifyTaxAndOthers(displayinvoice, user, customer, appliedtax);

			String createdinvoice = invoiceService.createInvoiceProcess(displayinvoice, user, shipvia, customer,
					discount, billsameship, billtoaddress, shiptoaddress);

			invoicestatus.setInvoicemessage("invoice number : " + createdinvoice);
			Integer invNo = Integer.parseInt(createdinvoice);
			Invoice invoice = invoiceService.searchInvoice(createdinvoice);
			List<InvoiceDetails> invoicedetailslist = invoiceService.getInvoiceDetailsFromInvoiceNumber(invNo);

			String fileName = invoiceService.createAsHtml(invoice, invoicedetailslist, customer, billtoaddress,
					shiptoaddress, repository);
			if (!fileName.equals("")) {
				mav.addObject("printFileName", fileName);
			}
			if (user.getActualrole().equalsIgnoreCase("admin")
					|| user.getActualrole().equalsIgnoreCase("branchmanager")) {
				buttonlogic = new ButtonLogic(0, 1, 1, 1, 1, 0);
			} else {
				buttonlogic = new ButtonLogic(0, 0, 1, 1, 1, 0);
			}

			mav.addObject("buttonlogic", buttonlogic);

			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			// mav.addObject("modelselected", modelselected);
			mav.addObject("relpartslist", relpartslist);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselected);
			// mav.addObject("partselected", partselected);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displaypart", displaypart);
			mav.addObject("displayinvoice", new DisplayInvoice(invoice,
					invoiceService.getDisplayInvoiceDetailsFromInvoiceDetails(invoicedetailslist)));
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			if (relpartslist != null) {
				if (relpartslist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}

			return mav;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.GET)
	public @ResponseBody List<Customer> getAllCustomer(@RequestParam String custSearch, HttpSession session,
			ModelAndView mav) {
		// LOGGER.info(session.getAttribute("customerList")+"mav.getModel().containsKey(\"customerList\")==="+mav.getModel().containsKey("customerList"));
		// LOGGER.info("/getAllCustomer");
		if (session.getAttribute("customerList") == null) {
			session.setAttribute("customerList", invoiceService.getAllCustomer());
		}
		List<Customer> custList = (List<Customer>) session.getAttribute("customerList");

		List<Customer> customerList = new ArrayList<Customer>();
		int i = 0;
		for (Customer tag : custList) {
			if (tag.getCustomerid().startsWith(custSearch)
					|| tag.getCompanyname().toUpperCase().contains(custSearch.toUpperCase())) {
				customerList.add(tag);
				i++;
			}
			if (i == 6) {
				break;
			}
		}
		return customerList;
	}

	@RequestMapping("getdetailsofselectedpart")
	public ModelAndView getSelectedPartsDetails(@RequestParam("partselected") String partselected,
			@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("makeselected") String makeselected, @RequestParam("modelselected") String modelselected,
			@RequestParam("yearselected") String yearselected, @RequestParam("shipvia") String shipvia, Model map,
			HttpSession session, ModelAndView mav) {

		// LOGGER.info("/getdetailsofselectedpart");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			displaypart = invoiceService.getSelectedPartsDetailsFromSelection(selectpartnumber, customer);

			Address billtoaddress = new Address();
			Address shiptoaddress = new Address();
			if (customer != null) {
				billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust", null,
						displayinvoice.getInvoicenumber());
				shiptoaddress = invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust", null,
						displayinvoice.getInvoicenumber());
			}
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			mav.addObject("modelselected", modelselected);
			mav.addObject("relpartslist", relpartslist);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselected);
			mav.addObject("partselected", partselected);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displaypart", displaypart);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			if (relpartslist != null) {
				if (relpartslist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}
			// LOGGER.info("invoicestatus= " +
			// invoicestatus.getInvoicemessage());
			return mav;
		}
	} // getdetailsofselectedpart

	@RequestMapping(value = { "/virtualization" }, method = RequestMethod.GET)
	public String index() {
		return "combobox/virtualization";
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

	@RequestMapping("modifyinvoice")
	public ModelAndView modifyInvoice(@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("displayorderdate") String displayorderdate, @RequestParam("shipvia") String shipvia,
			@RequestParam("billAttention") String billAttention, @RequestParam("ba_addr1") String ba_addr1,
			@RequestParam("ba_addr2") String ba_addr2, @RequestParam("ba_city") String ba_city,
			@RequestParam("ba_state") String ba_state, @RequestParam("ba_zip") String ba_zip,
			@RequestParam("ba_region") String ba_region, @RequestParam("ba_country") String ba_country,
			@RequestParam("sa_addr1") String sa_addr1, @RequestParam("sa_addr2") String sa_addr2,
			@RequestParam("sa_city") String sa_city, @RequestParam("sa_state") String sa_state,
			@RequestParam("sa_zip") String sa_zip, @RequestParam("sa_region") String sa_region,
			@RequestParam("sa_country") String sa_country,
			// Model map,
			HttpSession session, ModelAndView mav) {

		// Address billtoaddress =
		// invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust",
		// null, displayinvoice.getInvoicenumber());
		// Address shiptoaddress =
		// invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust",
		// null, displayinvoice.getInvoicenumber());
		Address billtoaddress = new Address();

		Address shiptoaddress = new Address();

		// LOGGER.info("/modifyinvoice");
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new InvoiceErrorException();
		} else {
			Integer yearselectedint = Calendar.getInstance().get(Calendar.YEAR);
			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}
			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}
			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			// LOGGER.info("getInvoicenumber======= " + displayinvoice.getInvoicenumber());
			if (displayinvoice != null && displayinvoice.getInvoicenumber() != null) {
				Invoice oldInvoice = invoiceService.searchInvoice(displayinvoice.getInvoicenumber() + "");
				List<InvoiceDetails> oldInvoiceDetails = invoiceService
						.getInvoiceDetailsFromInvoiceNumber(oldInvoice.getInvoicenumber());
				String status = oldInvoice.getStatus();
				String error = "";
				if (oldInvoice.getSalesperson() == null) {
					error = "Can Not Be Changed --- Original Sales Person Not Found";
				}
				if ((status.trim().equalsIgnoreCase("C") || status.trim().equalsIgnoreCase("W"))) {// &&
					// !user.getRole().trim().equalsIgnoreCase("high")
					error = "Can Not Be Changed --- This Invoice Is Already Closed ";
				}
				if (!displayinvoice.getSalesperson().trim().equalsIgnoreCase(oldInvoice.getSalesperson().trim())) {
					displayinvoice.setSalesperson(oldInvoice.getSalesperson().trim());
					error = "Can Not Be Changed --- Do Not Change The Sales Person's Name ";
				}
				searchcustomernumber = customer.getCustomerid();
				if (!customer.getCustomerid().equals(oldInvoice.getCustomerid().trim())) {
					error = "Can Not Be Changed --- Do Not Change The Customer Id ";
				}
				// LOGGER.info("user role==" + user.getActualrole());
				if (!invoiceService.cutOffGood(displayinvoice)
						&& !user.getActualrole().trim().equalsIgnoreCase("admin")) {
					error = " *****  THIS INVOICE CANNOT BE CHANGED  ***** ";
				}
				// createInvoice() method Start
				try {
					java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM-dd-yyyy");
					displayinvoice.setOrderdate(df.parse(displayorderdate));
				} catch (ParseException ex) {
					error = "Can't update invoice! Invalid order date";
				}
				if ((status.trim().equalsIgnoreCase("C") || status.trim().equalsIgnoreCase("W")
						|| status.trim().equalsIgnoreCase("P"))
						&& !user.getActualrole().trim().equalsIgnoreCase("admin")) {
					displayinvoice.setStatus(status.trim());
				} else {
					displayinvoice.setStatus("M");
				}
				if (oldInvoice.getDiscounttype().trim().equals("")) {
					displayinvoice.setDiscounttype("G");
				} else {
					displayinvoice.setDiscounttype(oldInvoice.getDiscounttype());
				}
				// set 601- 612
				/*
				 * if (user.getRole().trim().equalsIgnoreCase("High") ||
				 * user.getRole().trim().equalsIgnoreCase("Acct")) {
				 * invoice.setReturnedInvoice(Integer.parseInt(invForm. getReturnedInvoice()));
				 * } else { invoice.setReturnedInvoice(0); invForm.setReturnedInvoice(""); }
				 * invoice.setNotes(invForm.getNotes());
				 */
				displayinvoice.setShipvia(shipvia);
				// set 621-623
				/*
				 * if (status.trim().equalsIgnoreCase("M")) {
				 * invoice.setSalesPerson(invForm.getSalesPerson()); } else {
				 * invoice.setSalesPerson(user.getUsername()); } getAmounts(invoice, invForm,
				 * request, user, customerLevel, customerSetFirstTime);
				 */
				displayinvoice.setBillAttention(billAttention);
				billtoaddress.setId(customer.getCustomerid());
				billtoaddress.setType("Bill");
				billtoaddress.setWho("Cust");
				billtoaddress.setDatecreated(displayinvoice.getOrderdate());
				billtoaddress.setInvoicenumber(displayinvoice.getInvoicenumber());
				billtoaddress.setAddr1(ba_addr1);
				billtoaddress.setAddr2(ba_addr2);
				billtoaddress.setCity(ba_city);
				billtoaddress.setState(ba_state);
				billtoaddress.setRegion(ba_region);
				billtoaddress.setPostalcode(ba_zip);
				billtoaddress.setCountry(ba_country);
				billtoaddress.setPhone("");
				billtoaddress.setExt("");
				billtoaddress.setFax("");

				String billsameship = request.getParameter("billsameship");
				if (billsameship != null) {
					displayinvoice.setShipto(customer.getContactname());
					displayinvoice.setShipattention(customer.getContactname());
					shiptoaddress.setType("Ship");
					// shiptoaddress = billtoaddress;
					shiptoaddress.setId(billtoaddress.getId());
					shiptoaddress.setWho(billtoaddress.getWho());
					shiptoaddress.setDatecreated(billtoaddress.getDatecreated());
					shiptoaddress.setInvoicenumber(billtoaddress.getInvoicenumber());
					shiptoaddress.setAddr1(billtoaddress.getAddr1());
					shiptoaddress.setAddr2(billtoaddress.getAddr2());
					shiptoaddress.setCity(billtoaddress.getCity());
					shiptoaddress.setState(billtoaddress.getState());
					shiptoaddress.setRegion(billtoaddress.getRegion());
					shiptoaddress.setPostalcode(billtoaddress.getPostalcode());
					shiptoaddress.setCountry(billtoaddress.getCountry());
					shiptoaddress.setPhone(billtoaddress.getPhone());
					shiptoaddress.setExt(billtoaddress.getExt());
					shiptoaddress.setFax(billtoaddress.getFax());
				} else {
					displayinvoice.setShipto(customer.getContactname());
					displayinvoice.setShipattention(customer.getContactname());
					shiptoaddress.setId(customer.getCustomerid());
					shiptoaddress.setType("Ship");
					shiptoaddress.setWho("Cust");
					shiptoaddress.setDatecreated(displayinvoice.getOrderdate());
					shiptoaddress.setInvoicenumber(displayinvoice.getInvoicenumber());
					shiptoaddress.setAddr1(sa_addr1);
					shiptoaddress.setAddr2(sa_addr2);
					shiptoaddress.setCity(sa_city);
					shiptoaddress.setState(sa_state);
					shiptoaddress.setRegion(sa_region);
					shiptoaddress.setPostalcode(sa_zip);
					shiptoaddress.setCountry(sa_country);
					shiptoaddress.setPhone("");
					shiptoaddress.setExt("");
					shiptoaddress.setFax("");
				}

				boolean goodCredit = invoiceService.hasGoodCredit(customer);
				if (goodCredit && error.equals("")) {
					// changing Invoice
					// LOGGER.info("inv det in cont=" +
					// displayinvoice.getDisplayinvoicedetailslist().size());
					invoiceService.createHistory(user, oldInvoice, displayinvoice, oldInvoiceDetails);
					invoiceService.changeInvoice(oldInvoice, displayinvoice, oldInvoiceDetails, billtoaddress,
							shiptoaddress);
					if (status.trim().equalsIgnoreCase("W")) {
						invoiceService.deleteWriteOff(oldInvoice.getInvoicenumber());
					}
					String fileName = invoiceService.createAsHtml(
							invoiceService.searchInvoice(displayinvoice.getInvoicenumber() + ""),
							invoiceService.getInvoiceDetailsFromInvoiceNumber(oldInvoice.getInvoicenumber()), customer,
							billtoaddress, shiptoaddress, repository);
					if (!fileName.equals("")) {
						mav.addObject("printFileName", fileName);
					}
					buttonlogic = new ButtonLogic(0, 1, 1, 1, 1, 0);
				}
				if (!goodCredit) {
					invoicestatus.setInvoicemessage("Not Good Credit");
				}
				if (!error.equals("")) {
					invoicestatus.setInvoicemessage(error);
				}
			}

			mav.addObject("buttonlogic", buttonlogic);

			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			// mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", "");
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselectedint.toString());
			// mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			// mav.addObject("partselected", partselected);
			// for the sake of simplicity
			mav.addObject("displayinvoice", displayinvoice);

			return mav;
		}
	}

	@RequestMapping("printinvoice")
	public void printInvoice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("invPrintName") String invPringName) throws UnsupportedEncodingException, IOException {
		AppUser user = (AppUser) session.getAttribute("user");
		// LOGGER.info("/printinvoice");
		if (user == null) {
			throw new InvoiceErrorException();
		}
		File file = new File(repository + "htmlinvoice/", invPringName + ".html");
		// LOGGER.info(repository + "htmlinvoice/", invPringName + ".html");
		response.setHeader("Content-Type", "text/html");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		invoiceService.invoicePrinted(Integer.parseInt(invPringName), user);
		Files.copy(file.toPath(), response.getOutputStream());
		return;
	}

	@RequestMapping("removepartstoinvoice")
	public ModelAndView removePartToInvoice(@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber, HttpSession session, ModelAndView mav) {

		// LOGGER.info("/removepartstoinvoice");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {
			int index = 0;
			int selectedindex = 0;

			displayinvoicedetails = displayinvoice.getDisplayinvoicedetailslist();
			//
			if (displayinvoicedetails != null) {
				if (displayinvoicedetails.size() > 0) {
					for (DisplayInvoiceDetail displayinvoicedetail : displayinvoicedetails) {
						if (displayinvoicedetail.getPartnumber().trim().equalsIgnoreCase(selectpartnumber)) {
							displayinvoicedetails.remove(index);
							selectedindex = index;
							break;
						}
						index++;
					}
				}
			}
			// LOGGER.info("selectedindex" + selectedindex);
			displayinvoice.setDisplayinvoicedetailslist(displayinvoicedetails);
			invoicestatus = invoiceService.verifyTaxAndOthers(displayinvoice, user, customer, appliedtax);
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("relpartslist", relpartslist);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displaypart", displaypart);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("shippinglistdd", shippinglistdd);

			if (relpartslist != null) {
				if (relpartslist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}

			return mav;
		}
	}// remove partstoinvoice

	// resetyear
	@RequestMapping("resetyear")
	public ModelAndView resetFormYear(@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("yearselected") String yearselected, @RequestParam("shipvia") String shipvia, Model map,
			HttpSession session, ModelAndView mav) {

		// LOGGER.info("/resetyear");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			customer = invoiceService.getCustomerById(searchcustomernumber);

			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", "");
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselected);
			mav.addObject("customer", customer);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			return mav;
		}
	} // resetyear

	@RequestMapping("resetinvoice")
	public ModelAndView resetInvoice(HttpSession session, ModelAndView mav) {
		// LOGGER.info("/resetinvoice");
		Integer yearselectedint = Calendar.getInstance().get(Calendar.YEAR);
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new InvoiceErrorException();
		} else {

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
			mav.clear();
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", "1111111111");
			mav.addObject("billtoaddress", new Address("Bill"));
			mav.addObject("shiptoaddress", new Address("Ship"));
			mav.addObject("searchinvoicenumber", "0");
			mav.addObject("selectpartnumber", "");
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselectedint.toString());
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("invoicestatus", new InvoiceStatus());
			mav.addObject("customer", new Customer());
			mav.addObject("partnoinvoicedetails", "");
			mav.addObject("buttonlogic", new ButtonLogic());
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shippinglistdd.get(0));
		}
		return mav;
	}

	@RequestMapping("returninvoiceactions")
	public ModelAndView returnInvoiceActions(@ModelAttribute("invoice") DisplayInvoice returnInvoice,
			@RequestParam("partToAdd") String partNoToAdd, @RequestParam("partToRemove") String partNoToRemove,
			@RequestParam("operation") String operation, Model map, HttpSession session, ModelAndView mav) {
		// LOGGER.info("/returninvoiceactions");
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			if (operation.equals("back")) {
				mav.clear();
				mav.setViewName("invoicesmainpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}
			// LOGGER.info("returned Invoice no=" + returnInvoice.getReturnedinvoice());
			String errMsg = "";
			if (returnInvoice != null) {
				// if this is a new invoice that is not yet saved
				Invoice orgInvoice = invoiceService.searchInvoice(returnInvoice.getReturnedinvoice().toString());
				if (returnInvoice.getInvoicenumber() == null && returnInvoice.getReturnedinvoice() != null) {
					returnInvoice.setCustomerid(orgInvoice.getCustomerid());
					returnInvoice.setOrderdate(new Date());
					returnInvoice.setSalesperson(user.getUsername());
					if (returnInvoice.getDisplayinvoicedetailslist() == null) {
						returnInvoice.setdisplayinvoicedetailslist(new ArrayList<DisplayInvoiceDetail>());
					}
				} else {
					/**
					 * to do if the invoice is already saved get it from db
					 */

					if (returnInvoice.getDisplayinvoicedetailslist() == null) {
						returnInvoice.setdisplayinvoicedetailslist(new ArrayList<DisplayInvoiceDetail>());
					}
				}
				Customer customer = invoiceService.getCustomerById(returnInvoice.getCustomerid());
				returnInvoice.setCustomerName(customer.getCompanyname());
				// mav.addObject("customer", customer);

				if (operation.equalsIgnoreCase("addItem")) {
					if (partNoToAdd != null && !partNoToAdd.equals("")) {
						Parts parts = invoiceService.getParts(partNoToAdd);
						if (parts == null) {
							errMsg = "Part not found.";
						} else {
							DisplayInvoiceDetail itemToAdd = new DisplayInvoiceDetail(parts);
							itemToAdd.setQuantity(itemToAdd.getQuantity() * -1);
							returnInvoice.getDisplayinvoicedetailslist().add(itemToAdd);
						}
					}
				} else if (operation.equalsIgnoreCase("removeItem")) {
					if (partNoToRemove != null && !partNoToRemove.equals("")) {
						int removeIdx = 0;
						for (DisplayInvoiceDetail item : returnInvoice.getdisplayinvoicedetailslist()) {
							if (item.getPartnumber().equals(partNoToRemove)) {
								returnInvoice.getdisplayinvoicedetailslist().remove(removeIdx);
								break;
							}
							removeIdx++;
						}
					}
				} else if (operation.equalsIgnoreCase("saveInvoice")) {
					// LOGGER.info("save invoice");
					if (returnInvoice.getDisplayinvoicedetailslist() == null
							|| returnInvoice.getDisplayinvoicedetailslist().isEmpty()) {
						errMsg = "Invoice without items can not be saved";
					} else if (returnInvoice.getShipvia().equals("")) {
						errMsg = "Please select shipping method";
					} else {
						String applyTaxAndOthersMsg = applyTaxAndOthers(orgInvoice, returnInvoice, customer);
						if (applyTaxAndOthersMsg.equals("")) {
							// LOGGER.info("save invoice2");
							invoiceService.saveReturnInvoice(returnInvoice, customer, new BigDecimal(appliedtax));

							errMsg = "Invoice added successfully.";
						} else {
							// LOGGER.info("save invoice3");
							errMsg = applyTaxAndOthersMsg;
						}
					}
				}
				if (returnInvoice.getInvoicenumber() != null) {
					mav.addObject("removeSaveBtn", "remove");
				}
				mav.addObject("invoice", returnInvoice);

				List<InvoiceDetails> invoicedetails = invoiceService.getInvoiceDetailsOriginalForReturn(
						returnInvoice.getReturnedinvoice(), returnInvoice.getDisplayinvoicedetailslist());
				List<DisplayInvoiceDetail> originalinvoiceItems = invoiceService
						.getDisplayInvoiceDetailsFromInvoiceDetails(invoicedetails);

				String applyTaxAndOthersMsg = applyTaxAndOthers(orgInvoice, returnInvoice, customer);
				if (errMsg.equals("")) {
					errMsg = applyTaxAndOthersMsg;
				}
				mav.addObject("originalinvoiceItems", originalinvoiceItems);

			}
			// LOGGER.info("save invoice=err=" + errMsg + "=oper" + operation);
			mav.addObject("errMsg", errMsg);
		}
		mav.addObject("shipviadd", shipviadd);
		mav.setViewName("invoicesreturnpage");
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		return mav;
	}

	@RequestMapping("returninvoicemain")
	public ModelAndView returnInvoiceMain(@RequestParam("searchinvoicenumber") String searchinvoicenumber, Model map,
			HttpSession session, ModelAndView mav) {
		// LOGGER.info("/return invoice");
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			String errMsg = "";
			if (!searchinvoicenumber.equals("")) {
				try {
					Integer.parseInt(searchinvoicenumber);
				} catch (NumberFormatException ex) {
					errMsg = "Please enter a valid invoice no.";
				}
			} else {
				errMsg = "Please enter invoice no.";
			}

			if (errMsg.equals("")) {
				Invoice orgInvoice = invoiceService.searchInvoice(searchinvoicenumber);
				if (orgInvoice != null) {
					errMsg = validateInvoiceForReturn(orgInvoice);
					if (errMsg.equals("")) {
						DisplayInvoice returnInvoice = new DisplayInvoice(orgInvoice,
								new ArrayList<DisplayInvoiceDetail>());
						returnInvoice.setInvoicenumber(null);
						returnInvoice.setReturnedinvoice(orgInvoice.getInvoicenumber());
						returnInvoice.setCustomerid(orgInvoice.getCustomerid());
						returnInvoice.setOrderdate(new Date());
						returnInvoice.setSalesperson(user.getUsername());
						Customer customer = invoiceService.getCustomerById(returnInvoice.getCustomerid());
						returnInvoice.setCustomerName(customer.getCompanyname());
						mav.addObject("invoice", returnInvoice);

						// mav.addObject("customer", customer);

						List<InvoiceDetails> invoicedetails = invoiceService.getInvoiceDetailsOriginalForReturn(
								returnInvoice.getReturnedinvoice(), returnInvoice.getDisplayinvoicedetailslist());
						// List<InvoiceDetails> invoicedetails =
						// invoiceService.getInvoiceDetailsFromInvoiceNumber(orgInvoice.getInvoicenumber());
						List<DisplayInvoiceDetail> originalinvoiceItems = invoiceService
								.getDisplayInvoiceDetailsFromInvoiceDetails(invoicedetails);
						String applyTaxAndOthersMsg = applyTaxAndOthers(orgInvoice, returnInvoice, customer);
						if (errMsg.equals("")) {
							errMsg = applyTaxAndOthersMsg;
						}
						mav.addObject("originalinvoiceItems", originalinvoiceItems);
					}
				}
			}

			if (errMsg.equals("")) {
				mav.setViewName("invoicesreturnpage");
				mav.addObject("shipviadd", shipviadd);
			} else {
				mav.setViewName("invoicesmainpage");
				mav.addObject("searchinvoicenumber", searchinvoicenumber);
				mav.addObject("errMsg", errMsg);
			}

			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			return mav;
		}
	}

	@RequestMapping("getallmodels")
	public ModelAndView searchAllModels(@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("makeselected") String makeselected, @RequestParam("yearselected") String yearselected,
			@RequestParam("shipvia") String shipvia, Model map, HttpSession session, ModelAndView mav) {

		// LOGGER.info("/getallmodels");

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			customer = invoiceService.getCustomerById(searchcustomernumber);

			Integer manufacturerid = makelistdd.get((makeselected));

			relmakemodellist = invoiceService.getAllMakeModelFromSelectedMake(manufacturerid, yearselected);

			Address billtoaddress = new Address();
			Address shiptoaddress = new Address();
			if (customer != null) {
				billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust", null,
						displayinvoice.getInvoicenumber());
				shiptoaddress = invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust", null,
						displayinvoice.getInvoicenumber());
			}

			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselected);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			if (relmakemodellist != null) {
				if (relmakemodellist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}

			// LOGGER.info("invoicestatus= " +
			// invoicestatus.getInvoicemessage());
			return mav;
		}
	} // getallmodels

	@RequestMapping("getallpartsfrommodel")
	public ModelAndView searchAllParts(@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("makeselected") String makeselected, @RequestParam("modelselected") String modelselected,
			@RequestParam("yearselected") String yearselected, @RequestParam("shipvia") String shipvia, Model map,
			HttpSession session, ModelAndView mav) {

		// LOGGER.info("/getallpartsfrommodel");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			String makemodelcode = relmakemodellist.get((modelselected));

			relpartslist = invoiceService.getAllPartsFromSelectedModel(makemodelcode, yearselected, customer);

			Address billtoaddress = new Address();
			Address shiptoaddress = new Address();
			if (customer != null) {
				billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust", null,
						displayinvoice.getInvoicenumber());
				shiptoaddress = invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust", null,
						displayinvoice.getInvoicenumber());
			}
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			mav.addObject("modelselected", modelselected);
			mav.addObject("relpartslist", relpartslist);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselected);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			if (relpartslist != null) {
				if (relpartslist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}
			// LOGGER.info("invoicestatus= " +
			// invoicestatus.getInvoicemessage());
			return mav;
		}
	} // getallpartsfrommodel

	@RequestMapping("searchpartstoinvoicedetails")
	public ModelAndView searchandaddpart(@RequestParam("selectpartnumber") String selectpartnumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("partnoinvoicedetails") String partnoinvoicedetails, @RequestParam("shipvia") String shipvia,
			Model map, HttpSession session, ModelAndView mav) {

		// LOGGER.info("/searchpartstoinvoicedetails");

		Parts tempdisplaypart = null;

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			int index = 0;
			int selectedindex = 0;
			@SuppressWarnings("unused")
			int removeindex = 0;

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			displayinvoicedetails = displayinvoice.getDisplayinvoicedetailslist();

			Boolean partnumberispresent = false;

			if ((partnoinvoicedetails != null)) {

				partnoinvoicedetails = partnoinvoicedetails.trim().toUpperCase();

				if (!(partnoinvoicedetails.equalsIgnoreCase(""))) {

					if (!(partnoinvoicedetails.startsWith("XX"))) {

						tempdisplaypart = invoiceService.getSelectedPartsDetailsFromSelection(selectpartnumber,
								customer);
						if (tempdisplaypart != null) {
							if (displayinvoicedetails != null) {
								if (displayinvoicedetails.size() > 0) {
									for (DisplayInvoiceDetail displayinvoicedetail : displayinvoicedetails) {
										if (displayinvoicedetail.getPartnumber().trim().equalsIgnoreCase("")) {
											removeindex = index;
										}
										if (displayinvoicedetail.getPartnumber().trim()
												.equalsIgnoreCase(tempdisplaypart.getPartno())) {
											partnumberispresent = true;
											selectedindex = index;
										}
										index++;
									}
									if (partnumberispresent) {

										DisplayInvoiceDetail duplicatepart = displayinvoicedetails.get(selectedindex);
										Integer quantity = duplicatepart.getQuantity();
										displayinvoicedetails.remove(selectedindex);
										duplicatepart.setQuantity(quantity + 1);
										displayinvoicedetails.add(duplicatepart);

									} else {
										DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();
										addedpart.setActualprice(tempdisplaypart.getActualprice());
										addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));
										addedpart.setListprice(tempdisplaypart.getListprice());
										addedpart.setLocation(tempdisplaypart.getLocation());
										addedpart.setMakemodelname(tempdisplaypart.getMakemodelname());
										addedpart.setManufacturername(tempdisplaypart.getManufacturername());
										addedpart.setPartdescription(tempdisplaypart.getPartdescription());
										addedpart.setPartnumber(tempdisplaypart.getPartno());
										addedpart.setQuantity(1);
										addedpart.setReorderlevel(tempdisplaypart.getReorderlevel());
										addedpart.setUnitsinstock(tempdisplaypart.getUnitsinstock());
										addedpart.setUnitsonorder(tempdisplaypart.getUnitsonorder());
										addedpart.setYear(tempdisplaypart.getYear());
										addedpart.setSoldprice(tempdisplaypart.getCalculatedprice());
										displayinvoicedetails.add(addedpart);
									}

								} else {
									displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();
									DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();
									addedpart.setActualprice(tempdisplaypart.getActualprice());
									addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));
									addedpart.setListprice(tempdisplaypart.getListprice());
									addedpart.setLocation(tempdisplaypart.getLocation());
									addedpart.setMakemodelname(tempdisplaypart.getMakemodelname());
									addedpart.setManufacturername(tempdisplaypart.getManufacturername());
									addedpart.setPartdescription(tempdisplaypart.getPartdescription());
									addedpart.setPartnumber(tempdisplaypart.getPartno());
									addedpart.setQuantity(1);
									addedpart.setReorderlevel(tempdisplaypart.getReorderlevel());
									addedpart.setUnitsinstock(tempdisplaypart.getUnitsinstock());
									addedpart.setUnitsonorder(tempdisplaypart.getUnitsonorder());
									addedpart.setYear(tempdisplaypart.getYear());
									addedpart.setSoldprice(tempdisplaypart.getCalculatedprice());
									displayinvoicedetails.add(addedpart);
								}
							} else {
								displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();
								DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();
								addedpart.setActualprice(tempdisplaypart.getActualprice());
								addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));
								addedpart.setListprice(tempdisplaypart.getListprice());
								addedpart.setLocation(tempdisplaypart.getLocation());
								addedpart.setMakemodelname(tempdisplaypart.getMakemodelname());
								addedpart.setManufacturername(tempdisplaypart.getManufacturername());
								addedpart.setPartdescription(tempdisplaypart.getPartdescription());
								addedpart.setPartnumber(tempdisplaypart.getPartno());
								addedpart.setQuantity(1);
								addedpart.setReorderlevel(tempdisplaypart.getReorderlevel());
								addedpart.setUnitsinstock(tempdisplaypart.getUnitsinstock());
								addedpart.setUnitsonorder(tempdisplaypart.getUnitsonorder());
								addedpart.setYear(tempdisplaypart.getYear());
								addedpart.setSoldprice(tempdisplaypart.getCalculatedprice());
								displayinvoicedetails.add(addedpart);
							}
						}
					} else {
						DisplayInvoiceDetail addedpart = new DisplayInvoiceDetail();

						addedpart.setActualprice(bigdecimalzero);
						addedpart.setInvoicenumber(Integer.parseInt(searchinvoicenumber));

						addedpart.setListprice(bigdecimalzero);
						addedpart.setLocation("");
						addedpart.setMakemodelname("");
						addedpart.setManufacturername("");
						addedpart.setPartdescription(
								"DAMAGED DISCOUNT FOR " + partnoinvoicedetails.substring(2).toUpperCase());
						addedpart.setPartnumber(partnoinvoicedetails);
						addedpart.setQuantity(1);
						addedpart.setReorderlevel(0);
						addedpart.setUnitsinstock(0);
						addedpart.setUnitsonorder(0);
						addedpart.setYear("");
						addedpart.setSoldprice(bigdecimalzero);
						displayinvoicedetails.add(addedpart);
					} // XX condition
				}
			}

			displayinvoice.setDisplayinvoicedetailslist(displayinvoicedetails);

			Address billtoaddress = new Address();
			Address shiptoaddress = new Address();
			if (customer != null) {
				billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust", null,
						displayinvoice.getInvoicenumber());
				shiptoaddress = invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust", null,
						displayinvoice.getInvoicenumber());
			}
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("relpartslist", relpartslist);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			if (relpartslist != null) {
				if (relpartslist.size() > 0) {
					mav.addObject("relmakemodellist", relmakemodellist);
				}
			}

			return mav;

		}

	}

	// searchcustomernumber
	@RequestMapping("searchcustomernumber")
	public ModelAndView searchCustomer(@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("selectpartnumber") String selectpartnumber, Model map, HttpSession session,
			ModelAndView mav) {

		// LOGGER.info("/searchcustomernumber");
		BigDecimal writeoffamount = new BigDecimal("0.00");

		AppUser user = (AppUser) session.getAttribute("user");
		invoicestatus = new InvoiceStatus();
		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			Integer yearselectedint = Calendar.getInstance().get(Calendar.YEAR);
			displayinvoice = new DisplayInvoice(user.getUsername());

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			customer = invoiceService.getCustomerById(searchcustomernumber);

			if (customer != null) {
				if (invoiceService.checkCredit(customer)) {

					customer = invoiceService.getCustomerById(searchcustomernumber);

					billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Standard", "Cust", null, 0);

					shiptoaddress = invoiceService.getBlankAddress(customer.getCustomerid(), "Ship", "Cust", null, 0);

					writeoffamount = invoiceService.getWriteOffAmount(customer);

					// LOGGER.info("writeoffamount" + writeoffamount);

					invoicestatus.setInvoicemessage("proceed with further processing of invoice");
					invoicestatus.setInvoicemode("proceed");
				} else {
					invoicestatus.setInvoicemessage("customer doesnt have credit to proceed");
					invoicestatus.setInvoicemode("error");
				}

			} else {
				invoicestatus.setInvoicemessage("cannot find customer");
				invoicestatus.setInvoicemode("error");
			}

			displayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();
			displayinvoice.setDisplayinvoicedetailslist(displayinvoicedetails);

			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", "");
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselectedint.toString());
			mav.addObject("customer", customer);
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("invoicestatus", invoicestatus);
			buttonlogic = new ButtonLogic(1, 0, 1, 0, 1, 0);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			// LOGGER.info("invoicestatus= " +
			// invoicestatus.getInvoicemessage());
			mav.addObject("shippinglistdd", shippinglistdd);
			// mav.addObject("shipvia", shipvia);
			return mav;
		}
	} // searchcustomernumber

	@RequestMapping("searchinvoice")
	public ModelAndView searchInvoice(@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("selectpartnumber") String selectpartnumber, @RequestParam("shipvia") String shipvia,
			Model map, HttpSession session, ModelAndView mav) {

		// LOGGER.info("/searchinvoice");

		Address billtoaddress = new Address();
		Address shiptoaddress = new Address();
		InvoiceStatus invoicestatus = new InvoiceStatus("", "");

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {
			Integer yearselectedint = Calendar.getInstance().get(Calendar.YEAR);

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			searchcustomernumber = "0";

			Invoice invoice = invoiceService.searchInvoice(searchinvoicenumber);
			// BigDecimal writeoffamount = new BigDecimal("0.00");

			if (invoice != null) {
				if (invoice.getStatus().trim().equals("C") || invoice.getStatus().trim().equals("W")
						|| invoice.getStatus().trim().equals("P")) {
					invoicestatus.setInvoicemessage("THIS INVOICE CANNOT BE CHANGED");
					invoicestatus.setInvoicemode("error");
				}
				List<String> printRemarks = invoiceService.getPrintRemarks(invoice.getInvoicenumber());
				if (printRemarks != null && !printRemarks.isEmpty()) {
					for (String remarks : printRemarks) {
						if (invoicestatus.getInvoicemessage().equals("")) {
							invoicestatus.setInvoicemessage(remarks);
						} else {
							invoicestatus.setInvoicemessage(invoicestatus.getInvoicemessage() + "<br/>" + remarks);
						}
					}
				}

				List<InvoiceDetails> invoicedetails = invoiceService
						.getInvoiceDetailsFromInvoiceNumber(invoice.getInvoicenumber());
				customer = invoiceService.getCustomerById(invoice.getCustomerid());
				searchcustomernumber = invoice.getCustomerid();

				displayinvoicedetails = invoiceService.getDisplayInvoiceDetailsFromInvoiceDetails(invoicedetails);
				displayinvoice = new DisplayInvoice(invoice, displayinvoicedetails);

				billtoaddress = invoiceService.getAddress(customer.getCustomerid(), "Bill", "Cust", null,
						displayinvoice.getInvoicenumber());
				shiptoaddress = invoiceService.getAddress(customer.getCustomerid(), "Ship", "Cust", null,
						displayinvoice.getInvoicenumber());
				// writeoffamount = invoiceService.getWriteOffAmount(customer);
				String fileName = invoiceService.createAsHtml(invoice,
						invoiceService.getInvoiceDetailsFromInvoiceNumber(invoice.getInvoicenumber()), customer,
						billtoaddress, shiptoaddress, repository);
				if (!fileName.equals("")) {
					mav.addObject("printFileName", fileName);
				}

				if (invoice.getInvoicetime() == null) {
					// LOGGER.info("user.getActualrole():2");
					buttonlogic = new ButtonLogic(0, 0, 1, 0, 1, 0);
				} else {

					Number invTime = invoice.getInvoicetime();
					long timeToAdd = 86400000;
					Calendar cc = java.util.Calendar.getInstance();
					cc.setTime(new java.util.Date(invTime.longValue()));
					if (cc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
						timeToAdd *= 2;
					}
					if (invTime.longValue() != 0) {

						if (user.getActualrole().equalsIgnoreCase("admin")
								|| user.getActualrole().equalsIgnoreCase("branchmanager")) {
							if ((System.currentTimeMillis() < (invTime.longValue() + timeToAdd))
									&& user.getActualrole().equalsIgnoreCase("branchmanager")) {
								buttonlogic = new ButtonLogic(0, 1, 1, 1, 1, 0);
							}
						} else {
							buttonlogic = new ButtonLogic(0, 0, 1, 0, 1, 0);
						}
					}
				}
			} else {
				invoicestatus.setInvoicemessage("THIS INVOICE# NOT FOUND.");
				invoicestatus.setInvoicemode("error");
			}
			if (user.getActualrole().equalsIgnoreCase("superadmin") || user.getActualrole().equalsIgnoreCase("admin")) {
				buttonlogic = new ButtonLogic(0, 1, 1, 1, 1, 0);
			}
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", "");
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("yearselected", yearselectedint.toString());
			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			return mav;
		}
	}

	@RequestMapping("updateinvoice")
	public ModelAndView updateInvoice(// @RequestParam("partselected") String
										// partselected,
			@RequestParam("searchinvoicenumber") String searchinvoicenumber,
			@RequestParam("searchcustomernumber") String searchcustomernumber,
			@RequestParam("selectpartnumber") String selectpartnumber, @RequestParam("shipvia") String shipvia,
			Model map, HttpSession session, ModelAndView mav) {

		// LOGGER.info("/updateinvoice");

		int i = 0;
		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {
			Integer yearselectedint = Calendar.getInstance().get(Calendar.YEAR);
			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			if (shippinglistdd.size() == 0) {
				shippinglistdd = mainService.getShippingTypes();
			}

			displayinvoicedetails = displayinvoice.getDisplayinvoicedetailslist();

			if (invoicestatus.getInvoicemode().equalsIgnoreCase("modifyinvoice")) {

			} else {
				// updating the snapshot
				if (displayinvoice != null) {
					String discount = request.getParameter("displaydiscount");
					displayinvoice.setDiscount(new BigDecimal(discount));
					// LOGGER.info("ENTERED DISCOUNT:" + discount);
				}
				if (displayinvoicedetails != null) {
					if (displayinvoicedetails.size() > 0) {

						List<DisplayInvoiceDetail> newdisplayinvoicedetails = new LinkedList<DisplayInvoiceDetail>();

						String[] partnumberarray = null;
						String[] soldpricearray = null;
						String[] quantityarray = null;

						partnumberarray = request.getParameterValues("dplistpartnumber");
						soldpricearray = request.getParameterValues("dplistsoldprice");
						quantityarray = request.getParameterValues("dplistquantity");
						if ((partnumberarray != null) && (partnumberarray.length > 0)) {
							for (i = 0; i < partnumberarray.length; i++) {
								if (!(Integer.parseInt(quantityarray[i]) == 0)) {
									if ((partnumberarray[i]) != null && !((partnumberarray[i].equalsIgnoreCase("")))) {
										DisplayInvoiceDetail newinvoicedetail = new DisplayInvoiceDetail();
										newinvoicedetail = displayinvoicedetails.get(i);
										newinvoicedetail.setPartnumber(partnumberarray[i].trim());
										newinvoicedetail.setQuantity(Integer.parseInt(quantityarray[i]));
										newinvoicedetail.setSoldprice(new BigDecimal(soldpricearray[i]));
										newdisplayinvoicedetails.add(newinvoicedetail);
									}
								}
							}
						}

						displayinvoice.setDisplayinvoicedetailslist(newdisplayinvoicedetails);
						buttonlogic = new ButtonLogic(1, 0, 1, 0, 1, 0);
						mav.addObject("buttonlogic", buttonlogic);
					}
				} else {
					buttonlogic = new ButtonLogic();
					mav.addObject("buttonlogic", buttonlogic);
				}

			}

			invoicestatus = invoiceService.verifyTaxAndOthers(displayinvoice, user, customer, appliedtax);

			mav.setViewName("invoicespage");
			mav.setViewName("invoicespage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("searchcustomernumber", searchcustomernumber);
			mav.addObject("searchinvoicenumber", searchinvoicenumber);
			mav.addObject("customer", customer);
			mav.addObject("makelistdd", makelistdd);

			mav.addObject("yearslistdd", yearslistdd);

			mav.addObject("selectpartnumber", selectpartnumber);
			mav.addObject("displaypart", displaypart);
			mav.addObject("displayinvoice", displayinvoice);
			mav.addObject("invoicestatus", invoicestatus);
			mav.addObject("buttonlogic", buttonlogic);
			mav.addObject("billtoaddress", billtoaddress);
			mav.addObject("shiptoaddress", shiptoaddress);
			mav.addObject("shippinglistdd", shippinglistdd);
			mav.addObject("shipvia", shipvia);

			mav.addObject("relpartslist", relpartslist);

			mav.addObject("relpartslist", relpartslist);

			return mav;
		}
	}

	private String validateInvoiceForReturn(Invoice invoice) {

		return "";
	}

}
