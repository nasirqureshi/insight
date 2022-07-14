package com.bvas.insight.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.ReorderLevelFilter;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.InsightUtils;

// @Scope("session")
@Controller
@SessionAttributes({ "user" })
@RequestMapping("reorderLevel")
public class ReorderLevelController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReorderLevelController.class);

	@RequestMapping(value = "/getQtyToOrder", method = RequestMethod.POST)
	public void getQtyToOrderFile(// @RequestParam("c1SQ_Multiplier") String
									// c1SQ_Multiplier,
			@RequestParam("c2SQ_Multiplier") String c2SQ_Multiplier,
			// @RequestParam("c1DemandFactor") String c1DemandFactor,
			@RequestParam("c2DemandFactor") String c2DemandFactor, @RequestParam("qtyToOrder") String qtyToOrder,
			@RequestParam("orderType") String orderType,
			@RequestParam(name = "includCAPA", required = false) String includCAPA, ModelAndView mav,
			HttpServletResponse response, HttpSession session) throws IOException {

		AppUser user = (AppUser) session.getAttribute("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return;// mav;
		}
		BigDecimal c1SQ_Multiplier = BigDecimal.ZERO;
		BigDecimal c1DemandFactor = BigDecimal.ZERO;
		ReorderLevelFilter filterData = reorderLevelService.getCycleInfo();
		filterData.setCurrentCycleSQMultiplier(c1SQ_Multiplier);
		filterData.setTargetCycleSQMultiplier(
				(c2SQ_Multiplier.equals("") ? BigDecimal.ZERO : new BigDecimal(c2SQ_Multiplier.trim())));
		filterData.setCurrentCycleDemandFactor(c1DemandFactor);
		filterData.setTargetCycleDemandFactor(
				(c2DemandFactor.equals("") ? BigDecimal.ZERO : new BigDecimal(c2DemandFactor.trim())));
		filterData.setQtyToOrder((qtyToOrder.equals("") ? BigDecimal.ZERO : new BigDecimal(qtyToOrder)));
		filterData.setOrderType(orderType);
		filterData.setCAPAIncluded((includCAPA != null && includCAPA.trim().equalsIgnoreCase("Y")));

		OutputStream outStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=QtyToOrder.xls");
		// List<ReorderLevelData> dataList =
		reorderLevelService.getReorderLevelQtyToOrderData(filterData, outStream);
		// messages = partsService.updatepartsprices(labelfiles.get(0),
		// outStream);

		outStream.flush();
		outStream.close();
		response.flushBuffer();

		// List<ReorderLevelData> dataList =
		// reorderLevelService.getReorderLevelQtyToOrderData(filterData);
		// SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		// for (ReorderLevelData data : dataList) {
		// LOGGER.info("c1D1=" +
		// df.format(data.getCurrentCycleStartDate()) + ": c1D2=" +
		// df.format(data.getCurrentCycleEndDate())
		// + ": c2D1=" + df.format(data.getTargetCycleStartDate()) + ": c2D2=" +
		// df.format(data.getTargetCycleEndDate())
		// + ": location=" + data.getLocation() + ": partNo=" + data.getPartNo()
		// + ": lastOrderDate=" + (data.getLastOrderDate() != null ?
		// df.format(data.getLastOrderDate()) : "null")
		// + ": qtyOrdered=" + data.getQtyOrderedAfterSnapshot() + ": c1m1gr=" +
		// data.getCurrentCycleMonth1GrowthRate()
		// + ": c1m2gr=" + data.getCurrentCycleMonth2GrowthRate() + ": c2m1gr="
		// + data.getTargetCycleMonth1GrowthRate()
		// + ": c2m2gr=" + data.getTargetCycleMonth2GrowthRate() + ": c1Sq=" +
		// data.getCurrentCycleSQMultiplier()
		// + ": c2Sq=" + data.getTargetCycleSQMultiplier()
		// );
		// }

		// mav.setViewName("reorderlevelqtytoorderpage");
		// mav.addObject("reorderLevelData",
		// reorderLevelService.getCycleInfo());
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());

		// return mav;
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
