package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.bvas.insight.data.InvoicesNotDelivered;
import com.bvas.insight.entity.Invoice;
import com.bvas.insight.service.WarehouseService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user" })
@RequestMapping("warehouse")
public class WarehouseController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseController.class);

	Map<Integer, InvoicesNotDelivered> invoicenotdeliveredlist = new HashMap<Integer, InvoicesNotDelivered>();

	String routefilename = "";

	@Autowired
	@Qualifier("warehouseService")
	protected WarehouseService warehouseService;

	@RequestMapping(value = "createroute", method = RequestMethod.POST)
	public ModelAndView createRoute(@RequestParam("routeselected") String routeselected,
			@RequestParam("driverselected") String driverselected, @RequestParam("ampmselected") String ampmselected,
			@RequestParam("forroute") String forroute, @RequestParam("wildtext") String wildtext,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (routelistdd.size() == 0) {
				routelistdd = mainService.getAllRoute();
				if (!routelistdd.contains("ALL")) {
					routelistdd.add("ALL");
				}
			}
			if (driverlistdd.size() == 0) {
				driverlistdd = mainService.getAllDriver();
			}

			List<InvoicesNotDelivered> selectedinvoices = new LinkedList<InvoicesNotDelivered>();

			String selectedinvoicesRP[] = request.getParameterValues("invoiceselect");

			if (selectedinvoicesRP != null) {
				for (int i = 0; i < selectedinvoicesRP.length; i++) {
					if (!(selectedinvoicesRP[i].equalsIgnoreCase(""))) {
						InvoicesNotDelivered invoicenotdelivered = invoicenotdeliveredlist
								.get(Integer.parseInt(selectedinvoicesRP[i]));
						selectedinvoices.add(invoicenotdelivered);
					}
				}
			}

			if (selectedinvoices.size() > 0) {

				routefilename = warehouseService.createRoute(selectedinvoices, repository, driverselected, ampmselected,
						forroute, user.username, branch);
			}

			if (wildtext.equalsIgnoreCase("")) {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDelivered(routeselected);
			} else {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDeliveredWild(routeselected, wildtext);
			}

			Collections.sort(routelistdd);
			mav.setViewName("invoicenotdeliveredpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("driverlistdd", driverlistdd);
			mav.addObject("driverselected", driverselected);
			mav.addObject("routeselected", routeselected);
			mav.addObject("routelistdd", routelistdd);
			mav.addObject("routefilename", routefilename);
			mav.addObject("ampmselected", ampmselected);
			mav.addObject("wildtext", wildtext);
			mav.addObject("invoicenotdeliveredlist", invoicenotdeliveredlist);

			return mav;
		}
	}

	@RequestMapping("getinvoices")
	public ModelAndView getInvoices(@RequestParam("routeselected") String routeselected,
			@RequestParam("driverselected") String driverselected, @RequestParam("ampmselected") String ampmselected,
			@RequestParam("forroute") String forroute, @RequestParam("wildtext") String wildtext,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			// LOGGER.info("IMHERE");
			if (routelistdd.size() == 0) {
				routelistdd = mainService.getAllRoute();
				if (!routelistdd.contains("ALL")) {
					routelistdd.add("ALL");
				}
			}
			if (driverlistdd.size() == 0) {
				driverlistdd = mainService.getAllDriver();
			}

			if (wildtext.equalsIgnoreCase("")) {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDelivered(routeselected);
			} else {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDeliveredWild(routeselected, wildtext);
			}

			Collections.sort(routelistdd);
			mav.setViewName("invoicenotdeliveredpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("driverlistdd", driverlistdd);
			mav.addObject("driverselected", driverselected);
			mav.addObject("routeselected", routeselected);
			mav.addObject("routelistdd", routelistdd);
			mav.addObject("routefilename", "");
			mav.addObject("ampmselected", ampmselected);
			mav.addObject("wildtext", wildtext);
			mav.addObject("invoicenotdeliveredlist", invoicenotdeliveredlist);

			return mav;

		}
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

	@RequestMapping("printroute")
	public ResponseEntity<Object> printRoute(@RequestParam("routeselected") String routeselected,
			@RequestParam("driverselected") String driverselected, @RequestParam("ampmselected") String ampmselected,
			@RequestParam("forroute") String forroute, @RequestParam("wildtext") String wildtext,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		@SuppressWarnings("unused")
		AppUser user = (AppUser) session.getAttribute("user");
		if (routelistdd.size() == 0) {
			routelistdd = mainService.getAllRoute();
			if (!routelistdd.contains("ALL")) {
				routelistdd.add("ALL");
			}
		}
		if (driverlistdd.size() == 0) {
			driverlistdd = mainService.getAllDriver();
		}

		try {
			int length = 0;
			ServletOutputStream outStream;
			outStream = response.getOutputStream();

			File file = new File(routefilename);
			response.setContentType("application/pdf");
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

	@RequestMapping("redelivery")
	public ModelAndView processRedelivery(@RequestParam("redeliverymode") String redeliverymode,
			@RequestParam("redeliveryinvoicenumber") String redeliveryinvoicenumber,
			@RequestParam("invoicenumber") String invoicenumber, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			if (redeliverymode.equalsIgnoreCase("invoicesearch")) {
				Invoice invoice = warehouseService.getInvoice(Integer.parseInt(redeliveryinvoicenumber));
				mav.setViewName("redeliverypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				if (invoice != null) {
					mav.addObject("invoice", invoice);
				}
				return mav;
			} else if (redeliverymode.equalsIgnoreCase("redeliveryupdate")) {
				warehouseService.updateRedeliveryInvoice(Integer.parseInt(invoicenumber));
				mav.setViewName("redeliverypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}

			else {
				mav.clear();
				mav.setViewName("redeliverypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}
		}

	}

	@RequestMapping("getwildsearch")
	public ModelAndView processWildSearch(@RequestParam("routeselected") String routeselected,
			@RequestParam("driverselected") String driverselected, @RequestParam("ampmselected") String ampmselected,
			@RequestParam("forroute") String forroute, @RequestParam("wildtext") String wildtext,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			if (routelistdd.size() == 0) {
				routelistdd = mainService.getAllRoute();
				if (!routelistdd.contains("ALL")) {
					routelistdd.add("ALL");
				}
			}
			if (driverlistdd.size() == 0) {
				driverlistdd = mainService.getAllDriver();
			}

			if (wildtext.equalsIgnoreCase("")) {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDelivered(routeselected);
			} else {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDeliveredWild(routeselected, wildtext);
			}

			Collections.sort(routelistdd);
			mav.setViewName("invoicenotdeliveredpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("driverlistdd", driverlistdd);
			mav.addObject("driverselected", driverselected);
			mav.addObject("routeselected", routeselected);
			mav.addObject("routelistdd", routelistdd);
			mav.addObject("routefilename", "");
			mav.addObject("ampmselected", ampmselected);
			mav.addObject("wildtext", wildtext);
			mav.addObject("invoicenotdeliveredlist", invoicenotdeliveredlist);

			return mav;

		}
	}

	@RequestMapping(value = "saveinvoicenotes", method = RequestMethod.POST)
	public ModelAndView saveNotes(@RequestParam("routeselected") String routeselected,
			@RequestParam("driverselected") String driverselected, @RequestParam("ampmselected") String ampmselected,
			@RequestParam("forroute") String forroute, @RequestParam("wildtext") String wildtext,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (routelistdd.size() == 0) {
				routelistdd = mainService.getAllRoute();
				if (!routelistdd.contains("ALL")) {
					routelistdd.add("ALL");
				}
			}
			if (driverlistdd.size() == 0) {
				driverlistdd = mainService.getAllDriver();
			}

			int i = 0;
			String[] invoiceRP = null;
			String[] notesRP = null;

			Map<Integer, String> invoicenumbernotesMap = new HashMap<Integer, String>();

			invoiceRP = request.getParameterValues("invoicenumber");
			notesRP = request.getParameterValues("notes");

			for (i = 0; i < invoiceRP.length; i++) {
				if (!(notesRP[i].equalsIgnoreCase(""))) {
					invoicenumbernotesMap.put(Integer.parseInt(invoiceRP[i]), notesRP[i]);
				}
			}
			warehouseService.saveNotes(invoicenumbernotesMap);

			if (wildtext.equalsIgnoreCase("")) {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDelivered(routeselected);
			} else {
				invoicenotdeliveredlist = warehouseService.getInvoiceNotDeliveredWild(routeselected, wildtext);
			}

			Collections.sort(routelistdd);
			mav.setViewName("invoicenotdeliveredpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("driverlistdd", driverlistdd);
			mav.addObject("driverselected", driverselected);
			mav.addObject("routeselected", routeselected);
			mav.addObject("routelistdd", routelistdd);
			mav.addObject("routefilename", "");
			mav.addObject("ampmselected", ampmselected);
			mav.addObject("wildtext", wildtext);
			mav.addObject("invoicenotdeliveredlist", invoicenotdeliveredlist);

			return mav;

		}
	}

}
