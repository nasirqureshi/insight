package com.bvas.insight.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user" })
public class NavigationController extends BaseController {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(NavigationController.class);

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session, ModelAndView mav) {

		session.invalidate();
		mav.clear();
		return "redirect:/loginpage";
	}

	@RequestMapping(value = "/nav", method = RequestMethod.POST)
	public ModelAndView processNavigation(@RequestParam("navmode") String navmode, HttpSession session,
			ModelAndView mav) {

		if (navmode.equalsIgnoreCase("logout")) {
			session.invalidate();
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else if (navmode.equalsIgnoreCase("home")) {
			AppUser user = (AppUser) session.getAttribute("user");
			mav.setViewName("mainpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			return mav;
		} else {
			session.invalidate();
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		}

	}
}
