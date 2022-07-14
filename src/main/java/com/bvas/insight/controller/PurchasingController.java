package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
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

import com.bvas.insight.data.ActualPriceList;
import com.bvas.insight.data.VendorOrderedItemsDetails;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.jdbc.BaseDAOImpl;
import com.bvas.insight.service.OrdersService;
import com.bvas.insight.service.PurchasingService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.BVSExcel;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user", "message" })
@RequestMapping("purchasing")
public class PurchasingController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchasingController.class);

	String message = "";

	@Autowired
	@Qualifier("ordersService")
	protected OrdersService ordersService;

	@Autowired
	@Qualifier("purchasingService")
	protected PurchasingService purchasingService;

	@SuppressWarnings("null")
	private <purchasingService> ModelAndView displayOrder(String vendorselected, String branchselected,
			String ordernosearchtxt, ModelAndView mav, AppUser user, String message) {

		Integer togglebuttons = 1;
		List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
		List<VendorOrderedItemsDetails> vendororderitemsdetailslist = new ArrayList<VendorOrderedItemsDetails>();
		vendororderlist.clear();
		vendororderitemsdetailslist.clear();
		vendororderlist = purchasingService.getVendorOrder(vendorlistdd.get(vendorselected),
				Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

		mav.setViewName("purchasingfunctionpage");
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		mav.addObject("vendorlistdd", vendorlistdd);
		mav.addObject("branchlistdd", branchlistdd);
		mav.addObject("vendorselected", vendorselected);
		mav.addObject("branchselected", branchselected);

		mav.addObject("message", message);
		mav.addObject("orderno", Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

		if (vendororderlist != null) {
			if (vendororderlist.size() > 0) {
				mav.addObject("vendororder", vendororderlist.get(0));
				vendororderitemsdetailslist.clear();
				vendororderitemsdetailslist = purchasingService.getVendorOrderedItemsDetails(
						Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));
				if (vendororderitemsdetailslist != null) {
					if (vendororderitemsdetailslist.size() > 0) {
						togglebuttons = 1;
						mav.addObject("vendororderitemsdetailslist", vendororderitemsdetailslist);
					} else {
						vendororderitemsdetailslist.add(new VendorOrderedItemsDetails());
						mav.addObject("vendororderitemsdetailslist", vendororderitemsdetailslist);
					}
				} else {
					vendororderitemsdetailslist.add(new VendorOrderedItemsDetails());
					mav.addObject("vendororderitemsdetailslist", vendororderitemsdetailslist);
				}
			} else {
				mav.addObject("vendororder", new VendorOrder());
				vendororderitemsdetailslist.add(new VendorOrderedItemsDetails());
				mav.addObject("vendororderitemsdetailslist", vendororderitemsdetailslist);
			}
		}
		mav.addObject("togglebuttons", togglebuttons);
		return mav;

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

	@RequestMapping("order")
	public ModelAndView processPurchasingFunction(@RequestParam("purchasingfunctionbtn") String purchasingfunctionbtn,
			@RequestParam("ordernosearchtxt") String ordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam("branchselected") String branchselected, Model map, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		BaseDAOImpl basedao = null;
		Integer transfersupplierid = 0;
		String transfercode = "";

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (vendorlistdd.size() == 0) {
				vendorlistdd = mainService.getAllVendors();
			}
			if (branchlistdd.size() == 0) {
				branchlistdd = mainService.getAllOtherBranches(branch);
			}
			if (purchasingfunctionbtn.equalsIgnoreCase("getvendororder")) {
				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user,
						"Select operations to be done on the order");

			} else if (purchasingfunctionbtn.equalsIgnoreCase("makefinalonorder")) {

				message = purchasingService.makefinalOnOrder(vendorlistdd.get(vendorselected),
						Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);
			} else if (purchasingfunctionbtn.equalsIgnoreCase("removefinalonorder")) {

				message = purchasingService.removefinalOnOrder(vendorlistdd.get(vendorselected),
						Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			} else if (purchasingfunctionbtn.equalsIgnoreCase("addunitsonorder")) {
				message = purchasingService.addUnitsOnOrder(vendorlistdd.get(vendorselected),
						Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);
			} else if (purchasingfunctionbtn.equalsIgnoreCase("removeunitsonorder")) {

				message = purchasingService.removeUnitsOnOrder(vendorlistdd.get(vendorselected),
						Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			} else if (purchasingfunctionbtn.equalsIgnoreCase("updatepricesonorder")) {

				message = "prices updated on the order: "
						+ Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt));
				purchasingService.updatePricesOnOrder(vendorlistdd.get(vendorselected),
						Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			} else if (purchasingfunctionbtn.equalsIgnoreCase("removepartlist")) {

				if (branchselected.equalsIgnoreCase("")) {
					message = "please select a branch to remove";

				} else {
					// LOGGER.info(branchselected);
					transfercode = branch + branchselected;

					switch (branchselected) {

					case "AMS":
						transfersupplierid = 3;
						break;

					case "CHS":
						transfersupplierid = 37;
						break;

					case "GRS":
						transfersupplierid = 38;
						break;

					case "MRS":
						transfersupplierid = 59;
						break;

					case "MPS":
						transfersupplierid = 63;
						break;

					default:
						transfersupplierid = 0;
						break;
					}
					/*
					 * purchasingService.updatePricesOnOrder(vendorlistdd.get( vendorselected),
					 * Integer.parseInt(InsightUtils.allowOnlyNumbers( ordernosearchtxt)));
					 */
					message = purchasingService.removePartList(vendorlistdd.get(vendorselected),
							Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)), transfersupplierid,
							transfercode

					);
				}

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			}

			else if (purchasingfunctionbtn.equalsIgnoreCase("removefax")) {

				if (branchselected.equalsIgnoreCase("")) {
					message = "please select a branch to remove";

				} else {
					// LOGGER.info(branchselected);
					transfercode = branch + branchselected;

					switch (branchselected) {

					case "AMS":
						transfersupplierid = 3;
						break;

					case "CHS":
						transfersupplierid = 37;
						break;

					case "GRS":
						transfersupplierid = 38;
						break;

					case "MRS":
						transfersupplierid = 59;
						break;

					case "MPS":
						transfersupplierid = 63;
						break;

					default:
						transfersupplierid = 0;
						break;
					}
					/*
					 * purchasingService.updatePricesOnOrder(vendorlistdd.get( vendorselected),
					 * Integer.parseInt(InsightUtils.allowOnlyNumbers( ordernosearchtxt)));
					 */
					message = purchasingService.removeFax(vendorlistdd.get(vendorselected),
							Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)), transfersupplierid,
							transfercode

					);
				}

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			} else if (purchasingfunctionbtn.equalsIgnoreCase("copyorder")) {

				if (branchselected.equalsIgnoreCase("")) {
					message = "please select a branch to copy";

				} else {
					// LOGGER.info(branchselected);

					switch (branchselected) {

					case "CHS":
						basedao = chpartsdao;

						break;

					case "GRS":
						basedao = grpartsdao;
						break;

					case "MPS":
						basedao = mppartsdao;
						break;

					default:
						basedao = chpartsdao;
						break;
					}
					// purchasingService.updatePricesOnOrder(vendorlistdd.get(vendorselected),
					// Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

					message = purchasingService.copyOrderToOtherBranch(vendorlistdd.get(vendorselected),
							Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)), basedao

					);
				}

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			} else if (purchasingfunctionbtn.equalsIgnoreCase("printpricesonorder")) {

				try {
					message = "prices printed on the order: "
							+ Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt));
					purchasingService.updatePricesOnOrder(vendorlistdd.get(vendorselected),
							Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));

					int length = 0;
					BVSExcel bvsexcel = new BVSExcel();
					String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
					List<ActualPriceList> parts = purchasingService
							.getActualPriceList(Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)));
					ServletOutputStream outStream;
					outStream = response.getOutputStream();
					File file = bvsexcel.createActualPriceListExcelFile(parts,
							Integer.parseInt(InsightUtils.allowOnlyNumbers(ordernosearchtxt)), filePath);
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

				} catch (IOException e) {
					message = e.toString();
				}

				return displayOrder(vendorselected, branchselected, ordernosearchtxt, mav, user, message);

			} else {

				mav.clear();
				mav.setViewName("loginpage");
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				return mav;
			}
		}
	}
}
