package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.BranchTransferParts;
import com.bvas.insight.data.InventoryPartsTransfer;
import com.bvas.insight.data.PaymentMaintenance;
import com.bvas.insight.data.Requiredquanity;
import com.bvas.insight.data.ScanOrderDetails;
import com.bvas.insight.entity.AppliedAmounts;
import com.bvas.insight.entity.BouncedChecks;
import com.bvas.insight.entity.Writeoff;
import com.bvas.insight.service.AccountService;
import com.bvas.insight.service.OrdersService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.BVSExcel;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes("user")
@RequestMapping("accounting")
public class AccountingController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountingController.class);

	@Autowired
	@Qualifier("accountService")
	protected AccountService accountService;

	List<BranchTransferParts> branchtransferpartslist = new ArrayList<BranchTransferParts>();
	List<InventoryPartsTransfer> inventorypartstransferlist = new LinkedList<InventoryPartsTransfer>();

	protected ModelAndView mav = new ModelAndView();

	@Autowired
	@Qualifier("ordersService")
	protected OrdersService ordersService;

	private String sysdate = InsightUtils.getNewUSDate();

	AppUser user;

	@RequestMapping(value = "/closeInvoices", method = RequestMethod.POST)
	public ModelAndView closeInvoices(ModelMap model, @RequestParam("operation") String operation,
			@RequestParam("invoiceno") String invoiceno, HttpServletRequest request) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("closeinvoicepage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);

			int updatedCount = 0;

			if (operation.equals("showAll")) {
				mav.addObject("pendingInv", accountService.getPendingInvoices("showAll", 1, ""));
			} else if (operation.equals("showAll0")) {
				mav.addObject("pendingInv", accountService.getPendingInvoices("showAll0", 1, ""));
			} else if (operation.equals("invcheck") && !invoiceno.equals("")) {
				mav.addObject("pendingInv", accountService.getPendingInvoices("searchInv", 1, invoiceno));
			} else if (operation.equals("closeAll")) {
				updatedCount = accountService.closeAllInvoices();
				mav.addObject("updatedCount", updatedCount);
				// mav.addObject("pendingInv",
				// accountService.getPendingInvoices(""));
			} else if (operation.equals("closeInvoices")) {
				String invoices[] = request.getParameterValues("invoiceId");
				if (invoices != null && invoices.length > 0) {
					updatedCount = accountService.closeInvoices(invoices);
				}
				mav.addObject("updatedCount", updatedCount);
				// mav.addObject("pendingInv",
				// accountService.getPendingInvoices(""));
			}

			return mav;
		}
	}

	@RequestMapping(value = "/custhistory", method = RequestMethod.POST)
	public ModelAndView custhistory(ModelMap model, @RequestParam("customerId") String customerId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("custhistorypage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);

			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);
			if (customerId == null || customerId.equals("")) {
				mav.addObject("error", "Please enter Customer Id");
				return mav;
			}
			Map<String, Object> custList = accountService.getCustomerDetails(customerId, startDate, endDate);
			mav.addObject("custList", custList);
			return mav;
		}

	}

	@RequestMapping(value = "/bouncedchecks", method = RequestMethod.POST)
	public ModelAndView getBouncedChecks(ModelMap model, @Valid @ModelAttribute("bouncedchecks") BouncedChecks entity,
			BindingResult result, @RequestParam("operation") String operation) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("bouncecheckpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);

			BouncedChecks bouncedChecks = null;
			List<BouncedChecks> bcList = null;

			if (operation.equals("clear")) {
				bouncedChecks = new BouncedChecks();
			} else if (operation.equals("getBC")) {
				if (entity.getCheckid() != null && !entity.getCheckid().equals("")) {
					bouncedChecks = accountService.getBouncedChecksById(entity.getCheckid() + "");
					bcList = accountService.getBouncedChecksByCustomerId(bouncedChecks.getCustomerid());
				} else if (entity.getCustomerid() != null && !entity.getCustomerid().equals("")) {
					bcList = accountService.getBouncedChecksByCustomerId(entity.getCustomerid());
				}
				if (bouncedChecks == null || bouncedChecks.getCheckid() == null) {
					result.rejectValue("checkid", "Check Id not available.");
					bouncedChecks = new BouncedChecks();
				}
			} else if (operation.equals("save")) {
				if (result.hasErrors()) {
					bouncedChecks = entity;
				} else {
					String msg = accountService.addBouncedCheck(entity);
					if (msg.equals("")) {
						mav.addObject("error",
								"THIS CHECK ADDED SUCCESSFULLY --- New Check Id is : BC" + entity.getCheckid());
						bouncedChecks = new BouncedChecks();
					} else {
						mav.addObject("error", msg);
						bouncedChecks = entity;
					}
				}
				bcList = accountService.getBouncedChecksByCustomerId(entity.getCustomerid());
			} else if (operation.equals("update")) {
				if (result.hasErrors()) {
					bouncedChecks = entity;
				} else {
					String msg = accountService.updateBouncedCheck(entity);
					if (msg.equals("")) {
						mav.addObject("error", "Check updated Successfully.");
						bouncedChecks = accountService.getBouncedChecksById(entity.getCheckid() + "");
						// return mav;
					} else {
						mav.addObject("error", msg);
						bouncedChecks = entity;
					}
				}
				bcList = accountService.getBouncedChecksByCustomerId(entity.getCustomerid());
			}
			mav.addObject("bouncedchecks", bouncedChecks);
			mav.addObject("BCList", bcList);
			return mav;
		}
	}

	@RequestMapping("generateExcel")
	public ResponseEntity<?> getInventoryTransferForExcel(
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("stocklimit") String stocklimit, @RequestParam("RequestOrderGR") String RequestOrderGR,
			@RequestParam("RequestOrderMP") String RequestOrderMP, @RequestParam("selectYear") String selectYear,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		if (vendorlistdd.size() <= 0) {
			vendorlistdd = mainService.getAllVendors();
		}
		try {

			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();

			List<ScanOrderDetails> partstransferdetailslist = new ArrayList<ScanOrderDetails>();

			List<Requiredquanity> requiredquanititylist = new ArrayList<Requiredquanity>();

			partstransferdetailslist = accountService.getPartsTransferDetails(
					subcategorylistdd.get(subcategoryselected), Integer.parseInt(stocklimit),
					Integer.parseInt(selectYear), branch, requiredquanititylist);

			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;

			ServletOutputStream outStream = response.getOutputStream();
			File file;

			file = bvsexcel.createInventoryTransferFile(partstransferdetailslist, filePath, subcategoryselected,
					RequestOrderGR, RequestOrderMP);
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "writeOffDate", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "checkDate", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(BigDecimal.class, "bouncedAmount", new CustomNumberEditor(BigDecimal.class, true));
		binder.registerCustomEditor(BigDecimal.class, "payingAmount", new CustomNumberEditor(BigDecimal.class, true));

	}

	@RequestMapping(value = "/nav", method = RequestMethod.GET)
	public ModelAndView landing(Locale locale, ModelAndView model, HttpServletResponse response) {

		// LOGGER.info("Welcome login! The client locale is.. {}.", locale);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		model.setViewName("loginpage");
		model.addObject("branch", branch);
		model.addObject("appcss", appcss);
		return model;

	}

	@RequestMapping(value = "/updatebouncedchecks", method = RequestMethod.POST)
	public ModelAndView processBouncedChecks(ModelMap model,
			@Valid @ModelAttribute("bouncedchecks") BouncedChecks entity, BindingResult result) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("bouncecheckpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);

			if (result.hasErrors()) {
				return mav;
			}
			String msg = "";
			if (entity.getCheckid() == null) {
				msg = accountService.addBouncedCheck(entity);
				if (!msg.equals("")) {
					mav.addObject("error", msg);
					return mav;
				}
			} else {
				msg = accountService.updateBouncedCheck(entity);
			}
			if (msg.equals(""))
				mav.addObject("error", "Record");
			return mav;
		}
	}

	@RequestMapping(value = "/enteramount", method = RequestMethod.POST)
	public ModelAndView processEnterAmount(ModelMap model, @RequestParam("invoiceno") String invoiceno) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("enteramountpage");
			mav.addObject("user", user);
			int bCheckId = 0;

			PaymentMaintenance list = null;
			List<AppliedAmounts> appAmtList = null;

			if (invoiceno != null && !invoiceno.equals("")) {
				if (invoiceno.length() > 2 && invoiceno.substring(0, 1).equalsIgnoreCase("BC")) {
					try {
						bCheckId = Integer.parseInt(invoiceno.substring(2));
					} catch (Exception e) {

					}
				}
				if (bCheckId != 0) {
					list = accountService.getPaymentByBCCheckId(bCheckId + "");
				} else {
					list = accountService.getPaymentByInvNumber(invoiceno);
					appAmtList = accountService.getAppliedAmountsByInvNo(invoiceno);
				}
			}

			mav.addObject("invList", list);

			mav.addObject("appAmtList", appAmtList);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);
			return mav;
		}
	}

	@RequestMapping(value = "/addPayments", method = RequestMethod.POST)
	public ModelAndView processPayments(ModelMap model, @RequestParam("operation") String operation,
			@Valid @ModelAttribute("invList") PaymentMaintenance entity, BindingResult result) {

		user = (AppUser) model.get("user");
		String invNo = entity.getInvoiceNumber();
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("enteramountpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);

			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);

			if (result.hasErrors()) {
				List<AppliedAmounts> appAmtList = accountService.getAppliedAmountsByInvNo(entity.getInvoiceNumber());
				mav.addObject("appAmtList", appAmtList);
				return mav;
			}
			String err = "";
			entity.setUserName(user.getUsername());
			if (operation.equals("addpayments")) {
				err = accountService.addPayment(entity);
			} else if (operation.equals("delPayment")) {
				err = accountService.delPayment(entity);
			}
			mav.addObject("error", err);
			PaymentMaintenance list;
			if (invNo.startsWith("bc") || invNo.startsWith("BC")) {
				list = accountService.getPaymentByBCCheckId(invNo.substring(2));
			} else {
				list = accountService.getPaymentByInvNumber(invNo);
			}
			mav.addObject("invList", list);
			List<AppliedAmounts> appAmtList = accountService.getAppliedAmountsByInvNo(entity.getInvoiceNumber());
			mav.addObject("appAmtList", appAmtList);
			return mav;
		}
	}

	@RequestMapping(value = "/addwriteoff", method = RequestMethod.POST)
	public ModelAndView processWriteoff(@Valid @ModelAttribute("writeOff") Writeoff entity, BindingResult result,
			ModelMap model, @RequestParam("operation") String operation) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("writeoffpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);

			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);

			if (result.hasErrors()) {
				return mav;
			}
			if (operation.equals("addwriteoff")) {
				String errorMsg = accountService.addWriteoff(entity);
				if (!errorMsg.equals("")) {
					model.addAttribute("errorMessage", errorMsg);
					return mav;
				}
			} else if (operation.equals("delwriteoff")) {
				accountService.delWriteoff(entity);
			}
			model.addAttribute("writeOff", accountService.getWriteOffByInvNo(entity.getInvoiceNo() + ""));
		}
		return mav;
	}

	@RequestMapping(value = "/writeoffinv", method = RequestMethod.POST)
	public ModelAndView searchWriteoffinv(ModelMap model, @RequestParam("invoiceno") String invoiceno) {

		user = (AppUser) model.get("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("writeoffpage");
			mav.addObject("user", user);
			Writeoff wo = null;
			if (invoiceno != null && !invoiceno.equals("")) {
				try {
					Integer.parseInt(invoiceno);
					wo = accountService.getWriteOffByInvNo(invoiceno);
				} catch (NumberFormatException ex) {
					mav.addObject("errorMessage", "Please enter a valid invoice number.");
				}

			}
			mav.addObject("writeOff", wo);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", sysdate);
			return mav;
		}
	}
}
