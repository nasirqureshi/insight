package com.bvas.insight.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.bvas.insight.data.ContainerOrderDetails;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.service.ContainerService;
import com.bvas.insight.service.OrdersService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes("user")
@RequestMapping("container")
public class ContainerController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContainerController.class);

	@Autowired
	@Qualifier("containerService")
	protected ContainerService containerService;

	// private String sysdate = InsightUtils.getNewUSDate();

	@Autowired
	@Qualifier("ordersService")
	protected OrdersService ordersService;

	AppUser user;

	@SuppressWarnings("null")
	private ModelAndView displayContainerOrder(String vendorselected, String containerordernosearchtxt,
			ModelAndView mav, AppUser user, String containermode) {

		List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
		List<ContainerOrderDetails> containerorderdetailslist = new ArrayList<ContainerOrderDetails>();
		vendororderlist.clear();
		containerorderdetailslist.clear();
		vendororderlist = containerService.getVendorOrder(vendorlistdd.get(vendorselected),
				Integer.parseInt(containerordernosearchtxt));

		mav.clear();
		mav.setViewName("containerpage");
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		mav.addObject("vendorlistdd", vendorlistdd);
		mav.addObject("vendorselected", vendorselected);
		mav.addObject("containermode", containermode);
		mav.addObject("orderno", Integer.parseInt(containerordernosearchtxt));

		if (vendororderlist != null) {
			if (vendororderlist.size() > 0) {
				mav.addObject("vendororder", vendororderlist.get(0));
				containerorderdetailslist.clear();
				containerorderdetailslist = containerService.getContainerOrderDetails(vendorlistdd.get(vendorselected),
						Integer.parseInt(containerordernosearchtxt));
				if (containerorderdetailslist != null) {
					if (containerorderdetailslist.size() > 0) {
						mav.addObject("containerorderdetailslist", containerorderdetailslist);
					} else {
						containerorderdetailslist.add(new ContainerOrderDetails());
						mav.addObject("containerorderdetailslist", containerorderdetailslist);
					}
				} else {
					containerorderdetailslist.add(new ContainerOrderDetails());
					mav.addObject("containerorderdetailslist", containerorderdetailslist);
				}
			} else {
				mav.addObject("vendororder", new VendorOrder());
				containerorderdetailslist.add(new ContainerOrderDetails());
				mav.addObject("containerorderdetailslist", containerorderdetailslist);
			}
		}

		return mav;

	}

	@RequestMapping("getorder")
	public ModelAndView getExistingOrder(@RequestParam("containerordernosearchtxt") String containerordernosearchtxt,
			@RequestParam("containermode") String containermode, @RequestParam("vendorselected") String vendorselected,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			// LOGGER.info(containermode);
			if (vendorlistdd.size() <= 0) {
				vendorlistdd = mainService.getAllVendors();
			}

			return displayContainerOrder(vendorselected, containerordernosearchtxt, mav, user, containermode);
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

	@RequestMapping("dofinal")
	public ModelAndView processDoFinal(@RequestParam("containerordernosearchtxt") String containerordernosearchtxt,
			@RequestParam("containermode") String containermode, @RequestParam("vendorselected") String vendorselected,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			// LOGGER.info(containermode);
			if (vendorlistdd.size() <= 0) {
				vendorlistdd = mainService.getAllVendors();
			}
			return displayContainerOrder(vendorselected, containerordernosearchtxt, mav, user, containermode);
		}

	}

	@RequestMapping("updatevendorprices")
	public ModelAndView updateVendorPrices(@RequestParam("containerordernosearchtxt") String containerordernosearchtxt,
			@RequestParam("containermode") String containermode, @RequestParam("vendorselected") String vendorselected,
			HttpServletResponse response, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			// LOGGER.info(containermode);
			if (vendorlistdd.size() <= 0) {
				vendorlistdd = mainService.getAllVendors();
			}
			Integer i = 0;
			Integer totalitems = 0;
			Float ordertotalf = 0.0f;

			String[] partnoRP = null;
			String[] quantityRP = null;
			String[] priceRP = null;
			String[] vendorpartnoRP = null;

			partnoRP = request.getParameterValues("partno");
			quantityRP = request.getParameterValues("quantity");
			priceRP = request.getParameterValues("price");
			vendorpartnoRP = request.getParameterValues("vendorpartno");

			ordersService.deleteVendorOrderedItems(Integer.parseInt(containerordernosearchtxt));

			List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
			for (i = 0; i < partnoRP.length; i++) {
				if (Integer.parseInt(quantityRP[i]) > 0) {

					VendorOrderedItems vendororderitems = new VendorOrderedItems();
					vendororderitems.setOrderno(Integer.parseInt(containerordernosearchtxt));
					vendororderitems.setNoorder(totalitems++);
					vendororderitems.setPartno(partnoRP[i]);
					vendororderitems.setVendorpartno(vendorpartnoRP[i]);
					vendororderitems.setPrice(new BigDecimal(priceRP[i]));
					vendororderitems.setQuantity(Integer.parseInt(quantityRP[i]));
					vendororderitemslist.add(vendororderitems);
					ordertotalf = ordertotalf + (Float.parseFloat(priceRP[i])) * Integer.parseInt(quantityRP[i]);

				}
			}

			BigDecimal ordertotal = new BigDecimal(ordertotalf);

			containerService.updateVendorItemPrices(vendorlistdd.get(vendorselected), vendororderitemslist);
			ordersService.updateVendorOrderedItems(vendororderitemslist);
			ordersService.updateVendorOrder(Integer.parseInt(containerordernosearchtxt), totalitems, ordertotal);

			return displayContainerOrder(vendorselected, containerordernosearchtxt, mav, user, containermode);
		}

	}

}
