package com.bvas.insight.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.FutureOrderDetailsPartsHistory;
import com.bvas.insight.data.LocalDetailsPartsHistory;
import com.bvas.insight.data.PartsPartHistory;
import com.bvas.insight.data.PartsPartLink;
import com.bvas.insight.data.ProcessedOrdersPartsHistory;
import com.bvas.insight.data.SelectedOrderItems;
import com.bvas.insight.data.StockCheck;
import com.bvas.insight.data.StockCheckDetails;
import com.bvas.insight.entity.InvoiceDetails;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.entity.PartsLink;
import com.bvas.insight.service.PartsService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.FileUploadForm;
import com.bvas.insight.utilities.InsightUtils;
import com.bvas.insight.utilities.InvoiceErrorException;
import com.bvas.insight.utilities.OrderNotFoundException;

@Scope("session")
@Controller
@SessionAttributes({ "user", "parts" })
@RequestMapping("parts")
public class PartsController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartsController.class);

	protected Parts parts = new Parts();

	@Autowired
	@Qualifier("partsService")
	protected PartsService partsService;

	@RequestMapping("enhancePrice")
	public ModelAndView enhancePrice(@RequestParam("orders") String orders, @RequestParam("receiver") String receiver,
			Model map, HttpSession session, ModelAndView mav) throws MessagingException {

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			throw new InvoiceErrorException();
		} else {

			List<String> lqs = partsService.getPartsFromMainOrder(orders, receiver);
			List<SelectedOrderItems> lso = partsService.getSelectedOrderItems(lqs, orders, receiver);

			String[] on = orders.split(",");
			for (String orderNo : on) {

				partsService.makeFile(orderNo.trim(), receiver);
			}

			partsService.sendMail(on, receiver);

			mav.setViewName("comparepricepage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			return mav;
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

	@RequestMapping("newpartsexceluploadinitiate")
	public ModelAndView newpartsexceluploadInitiate(Model map, HttpSession session, ModelAndView mav) {
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#newpartsexceluploadinitiate");
			mav.clear();
			mav.setViewName("uploadnewpartspage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			return mav;
		}
	}

	@RequestMapping("partmaintenancecreate")
	public ModelAndView partmaintenanceCreate(@RequestParam("partmaintenancemode") String partmaintenancemode,
			@RequestParam("searchpartno") String searchpartno,
			@RequestParam("makemodelcodeselected") String makemodelcodeselected,
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("ordertypeselected") String ordertypeselected, Model map, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#partmaintenanceupdate");
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}
			if (makemodellistdd.size() == 0) {
				makemodellistdd = mainService.getAllMakeModelMap();
			}
			if (ordertypelistdd.size() == 0) {
				ordertypelistdd = mainService.getAllOrderType();
			}

			partmaintenancemode = "update";

			parts.setPartno(request.getParameter("partno").trim().toUpperCase());
			parts.setInterchangeno(request.getParameter("interchangeno").trim().toUpperCase());
			parts.setYear(request.getParameter("year").trim());
			parts.setYearfrom(Integer.parseInt(request.getParameter("yearfrom").trim()));
			parts.setYearto(Integer.parseInt(request.getParameter("yearto").trim()));
			parts.setPartdescription(request.getParameter("partdescription").trim());
			parts.setActualprice(new BigDecimal(request.getParameter("actualprice").trim()));
			parts.setCostprice(new BigDecimal(request.getParameter("costprice").trim()));
			parts.setListprice(new BigDecimal(request.getParameter("listprice").trim()));
			parts.setWholesaleprice(new BigDecimal(request.getParameter("wholesaleprice").trim()));
			parts.setUnitsinstock(Integer.parseInt(request.getParameter("unitsinstock").trim()));
			// LOGGER.info("Class:PartsController, method:partmaintenanceCreate(), partNo" +
			// parts.getPartno() + ",set unitsinstock:" + parts.getUnitsinstock());
			parts.setUnitsonorder(Integer.parseInt(request.getParameter("unitsonorder").trim()));
			parts.setReorderlevel(Integer.parseInt(request.getParameter("reorderlevel").trim()));
			parts.setSafetyquantity(Integer.parseInt(request.getParameter("safetyquantity").trim()));
			parts.setKeystonenumber(request.getParameter("keystonenumber").trim());
			parts.setOemnumber(request.getParameter("oemnumber").trim());
			parts.setDpinumber(request.getParameter("dpinumber").trim());
			parts.setLocation(request.getParameter("location").trim());
			parts.setCapa(request.getParameter("capa").trim());
			parts.setMakemodelcode(makemodellistdd.get(request.getParameter("makemodelcodeselected").trim()));
			parts.setSubcategory(subcategorylistdd.get(request.getParameter("subcategoryselected").trim()));
			parts.setOrdertype(request.getParameter("ordertypeselected").trim());
			parts.setSortcode(request.getParameter("sortcode").trim());
			partsService.createPartsMaintenance(parts);

			mav.clear();
			mav.setViewName("partmaintenancepage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("partmaintenancemode", partmaintenancemode);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("makemodellistdd", makemodellistdd);
			mav.addObject("ordertypelistdd", ordertypelistdd);
			mav.addObject("makemodelcodeselected", makemodelcodeselected);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("ordertypeselected", ordertypeselected);
			mav.addObject("searchpartno", searchpartno);
			mav.addObject("parts", parts);
			return mav;
		}

	}

	@RequestMapping("partmaintenancesearch")
	public ModelAndView partmaintenanceSearch(@RequestParam("partmaintenancemode") String partmaintenancemode,
			@RequestParam("searchpartno") String searchpartno,
			@RequestParam("makemodelcodeselected") String makemodelcodeselected,
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("ordertypeselected") String ordertypeselected, Model map, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#partmaintenancesearch");
			if ((searchpartno != null) && (!searchpartno.equalsIgnoreCase(""))) {
				if (subcategorylistdd.size() == 0) {
					subcategorylistdd = mainService.getAllSubCategory();
				}
				if (makemodellistdd.size() == 0) {
					makemodellistdd = mainService.getAllMakeModelMap();
				}
				if (ordertypelistdd.size() == 0) {
					ordertypelistdd = mainService.getAllOrderType();
				}

				parts = partsService.getParts(searchpartno.trim());

				if (parts != null) {
					partmaintenancemode = "update";
					makemodelcodeselected = (String) InsightUtils.getKeyFromValue(makemodellistdd,
							parts.getMakemodelcode());
					subcategoryselected = (String) InsightUtils.getKeyFromValue(subcategorylistdd,
							parts.getSubcategory());

					ordertypeselected = parts.getOrdertype();

					BigDecimal calculatedpercent = InsightUtils.calculateSalesPercent(parts.getActualprice(),
							parts.getCostprice());

					mav.clear();
					mav.setViewName("partmaintenancepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("partmaintenancemode", partmaintenancemode);
					mav.addObject("subcategorylistdd", subcategorylistdd);
					mav.addObject("makemodellistdd", makemodellistdd);
					mav.addObject("ordertypelistdd", ordertypelistdd);
					mav.addObject("makemodelcodeselected", makemodelcodeselected);
					mav.addObject("subcategoryselected", subcategoryselected);
					mav.addObject("ordertypeselected", ordertypeselected);
					mav.addObject("searchpartno", searchpartno);
					mav.addObject("percent", calculatedpercent.toString());
					mav.addObject("parts", parts);
					return mav;
				} else {
					parts = partsService.getPartsNoNull(searchpartno.trim());
					partmaintenancemode = "create";
					mav.clear();
					mav.setViewName("partmaintenancepage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("partmaintenancemode", partmaintenancemode);
					mav.addObject("subcategorylistdd", subcategorylistdd);
					mav.addObject("makemodellistdd", makemodellistdd);
					mav.addObject("ordertypelistdd", ordertypelistdd);
					mav.addObject("makemodelcodeselected", "");
					mav.addObject("subcategoryselected", "");
					mav.addObject("ordertypeselected", "");
					mav.addObject("searchpartno", searchpartno);
					mav.addObject("parts", parts);
					return mav;
				}

			} else {
				parts = partsService.getPartsNoNull(searchpartno.trim());
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
				partmaintenancemode = "create";
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("partmaintenancemode", partmaintenancemode);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("makemodellistdd", makemodellistdd);
				mav.addObject("ordertypelistdd", ordertypelistdd);
				mav.addObject("makemodelcodeselected", "");
				mav.addObject("subcategoryselected", "");
				mav.addObject("ordertypeselected", "");
				mav.addObject("searchpartno", searchpartno);
				mav.addObject("parts", parts);
				return mav;
			}
		}
	}

	@RequestMapping("partmaintenanceupdate")
	public ModelAndView partmaintenanceUpdate(@RequestParam("partmaintenancemode") String partmaintenancemode,
			@RequestParam("searchpartno") String searchpartno,
			@RequestParam("makemodelcodeselected") String makemodelcodeselected,
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("ordertypeselected") String ordertypeselected, Model map, HttpSession session,
			ModelAndView mav) {
		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			// LOGGER.info("#partmaintenanceupdate");
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}
			if (makemodellistdd.size() == 0) {
				makemodellistdd = mainService.getAllMakeModelMap();
			}
			if (ordertypelistdd.size() == 0) {
				ordertypelistdd = mainService.getAllOrderType();
			}

			partmaintenancemode = "update";

			parts.setPartno(request.getParameter("partno").trim().toUpperCase());
			parts.setInterchangeno(request.getParameter("interchangeno").trim().toUpperCase());
			parts.setYear(request.getParameter("year").trim());
			parts.setYearfrom(Integer.parseInt(request.getParameter("yearfrom").trim()));
			parts.setYearto(Integer.parseInt(request.getParameter("yearto").trim()));
			parts.setPartdescription(request.getParameter("partdescription").trim());
			parts.setActualprice(new BigDecimal(request.getParameter("actualprice").trim()));
			parts.setCostprice(new BigDecimal(request.getParameter("costprice").trim()));
			parts.setListprice(new BigDecimal(request.getParameter("listprice").trim()));
			parts.setWholesaleprice(new BigDecimal(request.getParameter("wholesaleprice").trim()));

			// LOGGER.info("Class:PartsController, method:partmaintenanceUpdate(), partNo" +
			// parts.getPartno() + ",set unitsinstock:" + parts.getUnitsinstock());
			parts.setUnitsonorder(Integer.parseInt(request.getParameter("unitsonorder").trim()));
			parts.setReorderlevel(Integer.parseInt(request.getParameter("reorderlevel").trim()));
			parts.setSafetyquantity(Integer.parseInt(request.getParameter("safetyquantity").trim()));
			parts.setKeystonenumber(request.getParameter("keystonenumber").trim());
			parts.setOemnumber(request.getParameter("oemnumber").trim());
			parts.setDpinumber(request.getParameter("dpinumber").trim());
			parts.setLocation(request.getParameter("location").trim());
			parts.setCapa(request.getParameter("capa").trim());
			parts.setMakemodelcode(makemodellistdd.get(request.getParameter("makemodelcodeselected").trim()));
			parts.setSubcategory(subcategorylistdd.get(request.getParameter("subcategoryselected").trim()));
			parts.setOrdertype(request.getParameter("ordertypeselected").trim());
			parts.setSortcode(request.getParameter("sortcode").trim());
			if (!parts.getInterchangeno().equalsIgnoreCase("")) {
				Parts mainpart = partsService.getParts(parts.getInterchangeno());
				if (mainpart != null) {
					parts.setUnitsinstock(mainpart.getUnitsinstock());

				} else {
					parts.setUnitsinstock(Integer.parseInt(request.getParameter("unitsinstock").trim()));
				}

			} else {
				parts.setUnitsinstock(Integer.parseInt(request.getParameter("unitsinstock").trim()));
			}
			partsService.updatePartsMaintenance(parts);
			partsService.updateInterchangeStock(parts.getUnitsinstock(), parts.getPartno());
			// partsService.deleteStockAuditEntry(parts.getPartno());
			mav.clear();
			mav.setViewName("partmaintenancepage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("partmaintenancemode", partmaintenancemode);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("makemodellistdd", makemodellistdd);
			mav.addObject("ordertypelistdd", ordertypelistdd);
			mav.addObject("makemodelcodeselected", makemodelcodeselected);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("ordertypeselected", ordertypeselected);
			mav.addObject("searchpartno", searchpartno);
			mav.addObject("parts", parts);
			return mav;
		}
	}

	@RequestMapping(value = "partlinkoemsearch", method = RequestMethod.POST)
	public ModelAndView processPartLinkOEMSearch(@RequestParam("partlinkoemsearchmode") String partlinkoemsearchmode,
			@RequestParam("partlinksearchtxt") String partlinksearchtxt, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (partlinkoemsearchmode.equalsIgnoreCase("partlinksearch")) {

				List<PartsLink> partslinklist = partsService.getPartsLinkSearchList(partlinksearchtxt);
				List<PartsPartLink> bvpartslinklist = partsService.getBVPartsLinkSearchList(partlinksearchtxt);

				mav.setViewName("partlinkoemsearchpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("partlinksearchtxt", partlinksearchtxt);
				mav.addObject("partslinklist", partslinklist);
				mav.addObject("bvpartslinklist", bvpartslinklist);
				return mav;
			} else {
				mav.clear();
				mav.setViewName("partlinkoemsearchpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}
		}
	}

	@RequestMapping("partsfix")
	public ModelAndView processPartsFix(@RequestParam("partsfixmode") String partsfixmode,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map, HttpSession session, ModelAndView mav) {

		String message = "";
		AppUser user = (AppUser) session.getAttribute("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (partsfixmode.equalsIgnoreCase("partsfix")) {
				message = excelService.uploadExcelPartsFix(uploadForm);
				mav.setViewName("partsfixpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("message", message);
				return mav;
			} else {
				mav.clear();
				mav.setViewName("partsfixpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}
		}
	}

	@RequestMapping(value = "partshistory", method = RequestMethod.POST)
	public ModelAndView processPartsHistory(@RequestParam("partshistorymode") String partshistorymode,
			@RequestParam("partshistorypartno") String partshistorypartno, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (partshistorymode.equalsIgnoreCase("partshistory")) {

				String searchpartno = "";

				// get parts details
				List<PartsPartHistory> partslistmain = new ArrayList<PartsPartHistory>();
				partslistmain.clear();
				List<PartsPartHistory> partslistothers = new ArrayList<PartsPartHistory>();
				partslistothers.clear();

				// future orders
				List<FutureOrderDetailsPartsHistory> futureorderslist = new ArrayList<FutureOrderDetailsPartsHistory>();
				futureorderslist.clear();

				// processed orders
				List<ProcessedOrdersPartsHistory> processedorderslist = new ArrayList<ProcessedOrdersPartsHistory>();
				processedorderslist.clear();

				// local orders
				List<LocalDetailsPartsHistory> localorderslist = new ArrayList<LocalDetailsPartsHistory>();
				localorderslist.clear();

				// local orders
				List<InvoiceDetails> invoicedetailslist = new ArrayList<InvoiceDetails>();
				invoicedetailslist.clear();

				PartsPartHistory partdetails = partsService.getPartsDetails(partshistorypartno);

				if (partdetails != null) {
					if (partdetails.getInterchangeno().equalsIgnoreCase("")) {
						searchpartno = partdetails.getPartno();
						partslistmain.add(partdetails);
						partslistothers = partsService.getPartsDetailforMain(partdetails.getPartno());
						for (PartsPartHistory ph : partslistothers) {
							ph.setUnitsinstock(partdetails.getUnitsinstock());
							partslistmain.add(ph);
						}

					} else {
						searchpartno = partdetails.getInterchangeno();
						PartsPartHistory partdetailsinterchange = partsService
								.getPartsDetails(partdetails.getInterchangeno());
						partslistothers = partsService.getPartsDetailforMain(partdetailsinterchange.getPartno());

						for (PartsPartHistory ph : partslistothers) {
							ph.setUnitsinstock(partdetailsinterchange.getUnitsinstock());
							partslistmain.add(ph);
						}

					}

					mav.addObject("partslist", partslistmain);

					// processing for future orders
					futureorderslist = partsService.getFutureOrders(searchpartno);
					mav.addObject("futureorderslist", futureorderslist);

					// processing for processed orders
					processedorderslist = partsService.getProcessedOrders(searchpartno);
					mav.addObject("processedorderslist", processedorderslist);

					// processing for local orders
					localorderslist = partsService.getLocalOrders(searchpartno);
					mav.addObject("localorderslist", localorderslist);

					// processing for invoice details
					invoicedetailslist = partsService.getInvoiceDetails(searchpartno);
					mav.addObject("invoicedetailslist", invoicedetailslist);

				}

				mav.setViewName("partshistorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());

				return mav;
			} else {
				mav.clear();
				mav.setViewName("partshistorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				return mav;
			}
		}
	}

	@RequestMapping("partmaker")
	public ModelAndView processPartsMaker(@RequestParam("partmakermode") String partmakermode,
			@RequestParam("makeselected") String makeselected,
			@RequestParam("subcategoryselected") String subcategoryselected, Model map, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		if (user == null) {
			throw new OrderNotFoundException();
		} else {
			String nextpartnumber = "";
			if (partmakermode.equalsIgnoreCase("partnocheck")) {
				if (subcategorylistdd.size() == 0) {
					subcategorylistdd = mainService.getAllSubCategory();
				}
				if (makelistdd.size() == 0) {
					makelistdd = mainService.getAllManufacturersMap();
				}
				nextpartnumber = partsService.processNextPartNumber(makelistdd.get(makeselected),
						subcategorylistdd.get(subcategoryselected));
				mav.setViewName("partmakerpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("makelistdd", makelistdd);
				mav.addObject("subcategoryselected", subcategoryselected);
				mav.addObject("makeselected", makeselected);
				mav.addObject("nextpartnumber", nextpartnumber);
				return mav;
			} else {
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
				mav.addObject("orderno", 0);
				return mav;
			}
		}
	}

	@RequestMapping(value = "utilities", method = RequestMethod.POST)
	public ModelAndView processUtilities(@RequestParam("utilitiesmode") String utilitiesmode,
			@RequestParam("stockcheckpartno") String stockcheckpartno,
			@RequestParam("locationpartno") String locationpartno, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (utilitiesmode.equalsIgnoreCase("stockcheck")) {

				List<StockCheck> stockchecks = new ArrayList<StockCheck>();
				stockchecks.clear();

				List<StockCheckDetails> stockchecksdetails = new ArrayList<StockCheckDetails>();
				stockchecksdetails.clear();

				List<PartsLink> partslinklist = new ArrayList<PartsLink>();
				partslinklist.clear();

				Parts part = partsService.getParts(stockcheckpartno);
				Parts nsfpart = partsService.getParts(stockcheckpartno + "N");

				if (part != null) {

					partslinklist = partsService.getPartsLinkList(part.getKeystonenumber());

					List<StockCheck> chstock = new ArrayList<StockCheck>();
					if (chpartsdao != null) {
						try {
							chstock = chpartsdao.getAllStock(stockcheckpartno);
						} catch (Exception e) {
							LOGGER.info("processUtilities:" + e.toString());
						}
					}

					List<StockCheck> grstock = new ArrayList<StockCheck>();
					if (grpartsdao != null) {
						try {
							grstock = grpartsdao.getAllStock(stockcheckpartno);
						} catch (Exception e) {
							LOGGER.info("processUtilities:" + e.toString());
						}
					}

					List<StockCheck> mpstock = new ArrayList<StockCheck>();
					if (mppartsdao != null) {
						try {
							mpstock = mppartsdao.getAllStock(stockcheckpartno);
						} catch (Exception e) {
							LOGGER.info("processUtilities:" + e.toString());
						}
					}

					StockCheck chstockinterchange = new StockCheck();
					StockCheck grstockinterchange = new StockCheck();
					StockCheck mpstockinterchange = new StockCheck();

					int i = 0;
					int j = 0;
					int k = 0;
					int m = 0;
					int n = 0;

					for (StockCheck chs : chstock) {

						chs.setBranch("CH");
						if (!(chs.getInterchangepartno().equalsIgnoreCase(""))
								|| (chs.getInterchangepartno() == null)) {
							if (i == 0) {
								chstockinterchange = chpartsdao.getStock(chs.getInterchangepartno());
								i++;
							}
							chs.setUnitsinstock(chstockinterchange.getUnitsinstock());
							chs.setUnitsonorder(chstockinterchange.getUnitsonorder());
						}

					}

					for (StockCheck grs : grstock) {
						grs.setBranch("GR");
						if (!(grs.getInterchangepartno().equalsIgnoreCase(""))
								|| (grs.getInterchangepartno() == null)) {
							if (k == 0) {
								grstockinterchange = grpartsdao.getStock(grs.getInterchangepartno());
								k++;
							}
							grs.setUnitsinstock(grstockinterchange.getUnitsinstock());
							grs.setUnitsonorder(grstockinterchange.getUnitsonorder());
						}
					}

					for (StockCheck mps : mpstock) {
						mps.setBranch("MP");
						if (!(mps.getInterchangepartno().equalsIgnoreCase(""))
								|| (mps.getInterchangepartno() == null)) {
							if (n == 0) {
								mpstockinterchange = mppartsdao.getStock(mps.getInterchangepartno());
								n++;
							}
							mps.setUnitsinstock(mpstockinterchange.getUnitsinstock());
							mps.setUnitsonorder(mpstockinterchange.getUnitsonorder());
						}
					}

					stockchecks.addAll(chstock);
					stockchecks.addAll(grstock);
					stockchecks.addAll(mpstock);

					LOGGER.info(stockchecks.toString());

					stockchecksdetails.addAll(partsService.getStockDetails(stockcheckpartno));
					mav.setViewName("utilitiespage");
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("utilitiespageselect", "stockcheck");
					mav.addObject("stockchecks", stockchecks);
					mav.addObject("stockchecksdetails", stockchecksdetails);
					mav.addObject("partsmonthlysales", partsService.getPartsMonthlySales(part.getPartno()));
					mav.addObject("partslinklist", partslinklist);
					mav.addObject("nsfpart", nsfpart);
					mav.addObject("stockcheckpartno", stockcheckpartno);
					mav.addObject("locationpartno", locationpartno);
					return mav;
				} else {

					mav.setViewName("utilitiespage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject("sysdate", InsightUtils.getNewUSDate());
					mav.addObject("stockcheckpartno", stockcheckpartno);
					mav.addObject("locationpartno", locationpartno);
					return mav;
				}
			} else if (utilitiesmode.equalsIgnoreCase("locationupdate")) {
				partsService.saveLocation(stockcheckpartno.trim(), locationpartno.trim());
				mav.setViewName("utilitiespage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("stockcheckpartno", stockcheckpartno);
				mav.addObject("locationpartno", locationpartno);
				return mav;
			}

			else {
				mav.clear();
				mav.setViewName("utilitiespage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("stockcheckpartno", stockcheckpartno);
				mav.addObject("locationpartno", locationpartno);
				return mav;
			}
		}
	}

	@RequestMapping("updatepartcostanddiscountprice")
	public void updatePartCostAndDiscountPrice(@ModelAttribute("partsfile") FileUploadForm uploadForm, Model map,
			HttpSession session, ModelAndView mav, HttpServletResponse response) throws IOException {

		AppUser user = (AppUser) session.getAttribute("user");
		List<String> messages = new ArrayList<String>();
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			messages.add("No User found, please relogin.");
			mav.addObject("messages", messages);
			return;
		}

		List<MultipartFile> labelfiles = uploadForm.getFiles();
		if (null != labelfiles && labelfiles.size() == 1) {
			if (null != labelfiles.get(0) && !labelfiles.get(0).isEmpty()) {
				OutputStream outStream = response.getOutputStream();
				response.setContentType(labelfiles.get(0).getContentType());
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + labelfiles.get(0).getOriginalFilename());
				messages = partsService.updatePartCostAndDiscountPrice(labelfiles.get(0), outStream);

				outStream.flush();
				outStream.close();
				response.flushBuffer();
			} else {
				messages.add("No file uploaded");
			}
		} else {
			messages.add("No file uploaded");
		}
		mav.addObject("messages", messages);
		mav.setViewName("updatepartcostanddiscountpricepage");
		mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);

	}

	@RequestMapping("updatepartsprice")
	public void updatePartsPrice(@ModelAttribute("partsfile") FileUploadForm uploadForm, Model map, HttpSession session,
			ModelAndView mav, HttpServletResponse response) throws IOException {

		AppUser user = (AppUser) session.getAttribute("user");
		List<String> messages = new ArrayList<String>();
		List<MultipartFile> labelfiles = uploadForm.getFiles();
		if (null != labelfiles && labelfiles.size() == 1) {
			if (null != labelfiles.get(0) && !labelfiles.get(0).isEmpty()) {
				OutputStream outStream = response.getOutputStream();
				response.setContentType(labelfiles.get(0).getContentType());
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + labelfiles.get(0).getOriginalFilename());
				messages = partsService.updatepartsprices(labelfiles.get(0), outStream);

				outStream.flush();
				outStream.close();
				response.flushBuffer();
			} else {
				messages.add("No file uploaded");
			}
		} else {
			messages.add("No file uploaded");
		}
		mav.addObject("messages", messages);
		mav.setViewName("updatepartsprices");
		mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);

	}
}
