package com.bvas.insight.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

import com.bvas.insight.service.InitiateOrderService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InitiateOrdersUtils;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user" })
@RequestMapping("initiateorders")
public class InitiateOrderController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExistingOrdersController.class);

	@Autowired
	@Qualifier("initiateOrderService")
	protected InitiateOrderService initiateOrderService;

	protected Map<String, String> relsubcategorylist = new HashMap<String, String>();

	@RequestMapping("searchpartorder")
	public ModelAndView getPartsForOrder(@RequestParam("makeselected") String makeselected,
			HttpServletResponse response, HttpSession session, ModelAndView mav,
			@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate,
			@RequestParam("typeoforderrdio") String typeoforderrdio,
			@RequestParam("categoryselected") String categoryselected,
			@RequestParam("relsubcategoryselected") String relsubcategoryselected,
			@RequestParam("stocklimit") String stocklimit, @RequestParam("listlimit") String listlimit,
			@RequestParam("avoidcapa") String avoidcapa) {

		AppUser user = (AppUser) session.getAttribute("user");
		String sqlEngineString = InitiateOrdersUtils.IO_PARTSMAIN_START;
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			if (categorylistdd.size() == 0) {
				categorylistdd = mainService.getAllCategory();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			// LOGGER.info("typeoforderrdio:" + typeoforderrdio);
			// LOGGER.info("stocklimit:" + stocklimit);
			// LOGGER.info("listlimit:" + listlimit);
			// LOGGER.info("avoidcapa:" + avoidcapa);

			// remove from here
			// getPartsForOrder();
			if (typeoforderrdio != null) {
				if (typeoforderrdio.equalsIgnoreCase("eagleeyeorder")) {
					sqlEngineString = sqlEngineString + InitiateOrdersUtils.EAGLE_CRITERIA;
				} else if (typeoforderrdio.equalsIgnoreCase("localorder")) {
					sqlEngineString = sqlEngineString + InitiateOrdersUtils.LOCAL_CRITERIA;
				} else {
					sqlEngineString = sqlEngineString + InitiateOrdersUtils.TAIWAN_CRITERIA;
				}

			}

			if (categoryselected != null) {
				if (!(categoryselected.equalsIgnoreCase("ALL"))) {
					if (!(relsubcategoryselected.equalsIgnoreCase("ALL"))) {
						sqlEngineString = sqlEngineString + InitiateOrdersUtils.CATEGORY_CRITERIA;
					} else {
						sqlEngineString = sqlEngineString + InitiateOrdersUtils.SUBCATEGORY_CRITERIA;
					}
				}
			}

			if (makeselected != null) {
				if (!(makeselected.equalsIgnoreCase("ALL"))) {
					sqlEngineString = sqlEngineString + InitiateOrdersUtils.MAKE_CRITERIA;
				}
			}

			// LOGGER.info(sqlEngineString);

			// remove from here

			mav.clear();
			mav.setViewName("initiateorderpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
			mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
			mav.addObject("categorylistdd", categorylistdd);
			mav.addObject("categoryselected", categoryselected);
			mav.addObject("relsubcategorylist", relsubcategorylist);
			mav.addObject("relsubcategoryselected", relsubcategoryselected);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);

			mav.addObject("typeoforderrdio", typeoforderrdio);
			mav.addObject("stocklimit", stocklimit);
			mav.addObject("listlimit", listlimit);
			mav.addObject("avoidcapa", avoidcapa);

			return mav;
		}

	}

	@RequestMapping("getsubcategorylist")
	public ModelAndView getRelativeSubcategory(@RequestParam("makeselected") String makeselected,
			HttpServletResponse response, HttpSession session, ModelAndView mav,
			@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate,
			@RequestParam("typeoforderrdio") String typeoforderrdio,
			@RequestParam("categoryselected") String categoryselected,
			@RequestParam("relsubcategoryselected") String relsubcategoryselected,
			@RequestParam("stocklimit") String stocklimit, @RequestParam("listlimit") String listlimit,
			@RequestParam("avoidcapa") String avoidcapa) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			if (categorylistdd.size() == 0) {
				categorylistdd = mainService.getAllCategory();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			String categoryid = categorylistdd.get((categoryselected));
			// LOGGER.info(categoryid);

			relsubcategorylist = initiateOrderService.getRelatedSubcategory(categoryid);

			mav.clear();
			mav.setViewName("initiateorderpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", InsightUtils.getCurrentSQLDate());
			mav.addObject("analyticstodate", InsightUtils.getCurrentSQLDate());
			mav.addObject("categorylistdd", categorylistdd);
			mav.addObject("categoryselected", categoryselected);
			mav.addObject("relsubcategorylist", relsubcategorylist);
			mav.addObject("relsubcategoryselected", "");
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);

			mav.addObject("typeoforderrdio", typeoforderrdio);
			mav.addObject("stocklimit", stocklimit);
			mav.addObject("listlimit", listlimit);
			mav.addObject("avoidcapa", avoidcapa);

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

}
