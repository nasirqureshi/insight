package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bvas.insight.data.CategoryBySalesAnalysis;
import com.bvas.insight.data.CreateVendorOrder;
import com.bvas.insight.data.PartnoQuantity;
import com.bvas.insight.data.ProcurementParts;
import com.bvas.insight.data.SalesAnalysisHistoryCategory;
import com.bvas.insight.data.ScanOrderDetails;
import com.bvas.insight.data.TransferParts;
import com.bvas.insight.data.VendorSalesHelper;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.service.LabelFormateService;
import com.bvas.insight.service.LabelService;
import com.bvas.insight.service.OrdersService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.BVSExcel;
import com.bvas.insight.utilities.FileUploadForm;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user" })
@RequestMapping("orders")
public class OrdersController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

	List<SalesAnalysisHistoryCategory> categorysaleshistorylist = new LinkedList<SalesAnalysisHistoryCategory>();

	List<CategoryBySalesAnalysis> categorysaleslist = new LinkedList<CategoryBySalesAnalysis>();

	@Autowired
	@Qualifier("labelFormateService")
	protected LabelFormateService labelFormateService;

	@Autowired
	@Qualifier("labelService")
	protected LabelService labelService;

	@Autowired
	@Qualifier("ordersService")
	protected OrdersService ordersService;

	List<TransferParts> partstransferdetailslist = new ArrayList<TransferParts>();

	List<ProcurementParts> procurementpartslist = new LinkedList<ProcurementParts>();

	protected VendorItems vendoritems = new VendorItems();

	List<VendorSalesHelper> vendorsaleslist = new LinkedList<VendorSalesHelper>();

	@RequestMapping("createpartsfortransferbtn")
	public ModelAndView createPartsInventoryTransferToBranches(
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("stocklimit") String stocklimit, @RequestParam("selectYear") String selectYear,
			@RequestParam("branchselected") String branchselected, @RequestParam("factor") String factor,
			@RequestParam("makeselected") String makeselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		String feedback = "";

		// LOGGER.info("makeselected:" + makeselected);

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			// LOGGER.info("createpartsfortransferbtn");

			Set<String> checkdupes = new HashSet<String>();
			if (branchlistdd.size() == 0) {
				branchlistdd = mainService.getAllActiveBranches(branch);
				if (!branchlistdd.containsKey("ALL")) {
					branchlistdd.put("ALL", "ALL");
				}
			}
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			Set<String> loopBranch = new HashSet<String>();

			if (!branchselected.equalsIgnoreCase("ALL")) {
				loopBranch.add(branchselected);
			} else {
				branchlistdd.remove("");
				branchlistdd.remove(branch);
				loopBranch = branchlistdd.keySet();
			}

			String[] partnoRP = request.getParameterValues("partno");
			String[] quantityCH = request.getParameterValues("chneed");
			String[] quantityGR = request.getParameterValues("grneed");
			String[] quantityMP = request.getParameterValues("mpneed");

			List<PartnoQuantity> chpartslist = new ArrayList<PartnoQuantity>();
			List<PartnoQuantity> grpartslist = new ArrayList<PartnoQuantity>();
			List<PartnoQuantity> mppartslist = new ArrayList<PartnoQuantity>();
			List<PartnoQuantity> nypartslist = new ArrayList<PartnoQuantity>();

			for (String branchinstance : loopBranch) {
				// LOGGER.info("branchinstance" + branchinstance);
				switch (branchinstance.toUpperCase()) {
				case "CHS":
					Integer chordernoseries = ordersService.getMaxPartListOrder(branchinstance.toUpperCase()) + 1;
					// LOGGER.info("chordernoseries" + chordernoseries);
					for (int i = 0; i < partnoRP.length; i++) {
						if (Integer.parseInt(quantityCH[i]) > 0) {
							if (checkdupes.add(partnoRP[i])) {
								PartnoQuantity pq = new PartnoQuantity();
								pq.setPartno(partnoRP[i]);
								pq.setQuantity(Integer.parseInt(quantityCH[i]));
								chpartslist.add(pq);
							}
						}
					}
					if (chpartslist != null) {
						if (chpartslist.size() > 0) {
							feedback = feedback + " - " + ordersService.createPartListForBranches(chpartslist, "24",
									branch, branchinstance.toUpperCase(), chordernoseries);
						}
					}
					break;
				case "GRS":
					Integer grordernoseries = ordersService.getMaxPartListOrder(branchinstance.toUpperCase()) + 1;
					// LOGGER.info("grordernoseries" + grordernoseries);
					for (int i = 0; i < partnoRP.length; i++) {
						if (Integer.parseInt(quantityGR[i]) > 0) {
							if (checkdupes.add(partnoRP[i])) {
								PartnoQuantity pq = new PartnoQuantity();
								pq.setPartno(partnoRP[i]);
								pq.setQuantity(Integer.parseInt(quantityGR[i]));
								grpartslist.add(pq);
							}
						}
					}
					if (grpartslist != null) {
						if (grpartslist.size() > 0) {
							feedback = feedback + " - " + ordersService.createPartListForBranches(grpartslist, "25",
									branch, branchinstance.toUpperCase(), grordernoseries);
						}
					}
					break;
				case "MPS":
					Integer mpordernoseries = ordersService.getMaxPartListOrder(branchinstance.toUpperCase()) + 1;
					// LOGGER.info("mpordernoseries" + mpordernoseries);
					for (int i = 0; i < partnoRP.length; i++) {
						if (Integer.parseInt(quantityMP[i]) > 0) {
							if (checkdupes.add(partnoRP[i])) {
								PartnoQuantity pq = new PartnoQuantity();
								pq.setPartno(partnoRP[i]);
								pq.setQuantity(Integer.parseInt(quantityMP[i]));
								mppartslist.add(pq);
							}
						}
					}
					if (mppartslist != null) {
						if (mppartslist.size() > 0) {
							feedback = feedback + " - " + ordersService.createPartListForBranches(mppartslist, "41",
									branch, branchinstance.toUpperCase(), mpordernoseries);
						}
					}
					break;

				case "":
					break;
				default:
					break;

				}

			}

			// CREATING ORDERS

			mav.clear();
			mav.setViewName("inventorytransferpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("stocklimit", stocklimit);
			mav.addObject("branchlistdd", branchlistdd);
			mav.addObject("branchselected", branchselected);
			mav.addObject("factor", factor);
			mav.addObject("partstransferdetailslist", partstransferdetailslist);
			mav.addObject("selectYear", selectYear);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("feedback", feedback);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			mav.addObject("listsize", partstransferdetailslist.size());
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
			return mav;
		}

	}

	@RequestMapping(value = "/createvendororder", method = RequestMethod.POST)
	public ModelAndView createVendorOrder(@ModelAttribute("vendorOrder") CreateVendorOrder vendorOrder,
			HttpSession session, ModelAndView mav) throws IOException {
		AppUser user = (AppUser) session.getAttribute("user");
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		if (user == null) {
			mav.setViewName("loginpage");
			return mav;
		}

		String errMsg = ordersService.createVedorOrder(vendorOrder);
		mav.addObject("vendorOrder", vendorOrder);
		mav.addObject("locationList", locationList);
		mav.addObject("messages", errMsg);
		mav.setViewName("createvendororderpage");
		mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping("downloadLabelReport")
	public void downloadLabelReport(@RequestParam(value = "downloadFileName") String downloadFileName,
			HttpServletRequest request, HttpServletResponse response, ModelAndView mav, HttpSession session) {

		AppUser user = (AppUser) session.getAttribute("user");
		@SuppressWarnings("unused")
		String message = "";
		try {
			ServletOutputStream outStream = response.getOutputStream();
			File file = new File(downloadFileName);
			response.setContentType("application/docx");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			org.apache.commons.io.IOUtils.copy(in, response.getOutputStream());
			in.close();
			outStream.flush();
			outStream.close();
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		mav.addObject("downloadFileName", "");
		mav.setViewName("formatelabelpage");
		mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);

	}

	@RequestMapping("searchpartsfortransferbtn")
	public ModelAndView getPartsInventoryTransferToBranches(
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("stocklimit") String stocklimit, @RequestParam("selectYear") String selectYear,
			@RequestParam("branchselected") String branchselected, @RequestParam("factor") String factor,
			@RequestParam("makeselected") String makeselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		// LOGGER.info("makeselected:" + makeselected);

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			if (branchlistdd.size() == 0) {
				branchlistdd = mainService.getAllActiveBranches(branch);
				if (!branchlistdd.containsKey("ALL")) {
					branchlistdd.put("ALL", "ALL");
				}
			}
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}

			if (yearslistdd.size() == 0) {
				yearslistdd = mainService.getYears();
			}

			if (makelistdd.size() == 0) {
				makelistdd = mainService.getAllManufacturersMap();
			}

			partstransferdetailslist.clear();

			partstransferdetailslist = ordersService.getPartsForBranchTransfer(subcategoryselected,
					subcategorylistdd.get(subcategoryselected), stocklimit, selectYear, branchselected, factor,
					branchlistdd, makeselected, makelistdd, branch);

			mav.clear();
			mav.setViewName("inventorytransferpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("stocklimit", stocklimit);
			mav.addObject("branchlistdd", branchlistdd);
			mav.addObject("branchselected", branchselected);
			mav.addObject("factor", factor);
			mav.addObject("partstransferdetailslist", partstransferdetailslist);
			mav.addObject("selectYear", selectYear);
			mav.addObject("yearslistdd", yearslistdd);
			mav.addObject("user", user);
			mav.addObject("appcss", appcss);
			mav.addObject("makelistdd", makelistdd);
			mav.addObject("makeselected", makeselected);
			mav.addObject("listsize", partstransferdetailslist.size());
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
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

	@RequestMapping("addtocontainer")
	public ModelAndView processAddtoContainer(@RequestParam("addtocontainermode") String addtocontainermode,
			@RequestParam("addcontainernotxt") String addcontainernotxt,
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
			if (addtocontainermode.equalsIgnoreCase("addtocontainer")) {
				message = excelService.uploadExcelToContainer(uploadForm, addcontainernotxt);
				Integer supplierid = ordersService.getSupplierid(Integer.parseInt(addcontainernotxt));
				ordersService.updateVendorParts(Integer.parseInt(addcontainernotxt), supplierid);
				mav.setViewName("addtocontainerpage");
				mav.addObject("message", message);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);

				return mav;

			} else {

				mav.setViewName("addtocontainerpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				return mav;
			}

		}

	}

	@RequestMapping("addvendorpart")
	public ModelAndView processAddVendorpart(@RequestParam("addvendorpartbtn") String addvendorpartbtn,
			@RequestParam("addvendorpartsearchtxt") String addvendorpartsearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam("vendornosearchtxt") String vendornosearchtxt, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
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
			if (addvendorpartbtn.equalsIgnoreCase("partnosearch")) {
				if ((addvendorpartsearchtxt.equalsIgnoreCase("")) || (vendorselected.equalsIgnoreCase(""))) {

					mav.setViewName("addvendorpartpage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("vendorlistdd", vendorlistdd);
					mav.addObject("partno", addvendorpartsearchtxt);
					mav.addObject("vendorselected", vendorselected);
					mav.addObject("vendoritems", vendoritems);
					return mav;
				} else {
					vendoritems = ordersService.getVendorItem(vendorlistdd.get(vendorselected), addvendorpartsearchtxt);
					mav.setViewName("addvendorpartpage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("vendorlistdd", vendorlistdd);
					mav.addObject("partno", addvendorpartsearchtxt);
					mav.addObject("vendoritems", vendoritems);
					mav.addObject("vendorselected", vendorselected);
					return mav;
				}
			} else if (addvendorpartbtn.equalsIgnoreCase("vendorpartsearch")) {
				if ((vendornosearchtxt.equalsIgnoreCase("")) || (vendorselected.equalsIgnoreCase(""))) {
					if (vendorlistdd.size() == 0) {
						vendorlistdd = mainService.getAllVendors();
					}

					mav.setViewName("addvendorpartpage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("vendorlistdd", vendorlistdd);
					mav.addObject("partno", addvendorpartsearchtxt);
					mav.addObject("vendorselected", vendorselected);
					mav.addObject("vendoritems", vendoritems);
					return mav;

				} else {
					vendoritems = ordersService.getVendorItemByVendorNO(vendorlistdd.get(vendorselected),
							vendornosearchtxt);
					mav.setViewName("addvendorpartpage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("vendorlistdd", vendorlistdd);
					mav.addObject("partno", addvendorpartsearchtxt);
					mav.addObject("vendoritems", vendoritems);
					mav.addObject("vendorselected", vendorselected);
					return mav;

				}
			}

			else if (addvendorpartbtn.equalsIgnoreCase("savevendorpartno")) {
				vendoritems.setPartno(request.getParameter("partno").trim().toUpperCase());
				vendoritems.setVendorpartno(request.getParameter("vendorpartno").trim());
				vendoritems.setItemdesc1(request.getParameter("itemdesc1").trim());
				vendoritems.setItemdesc2(request.getParameter("itemdesc2").trim());
				vendoritems.setSellingrate(new BigDecimal(request.getParameter("sellingrate").trim()));
				vendoritems.setPlno(request.getParameter("plno").trim());
				vendoritems.setOemno(request.getParameter("oemno").trim());
				vendoritems.setNoofpieces(Integer.parseInt(request.getParameter("noofpieces").trim()));
				vendoritems.setSupplierid(vendorlistdd.get(vendorselected));
				vendoritems.setItemsize(new BigDecimal(request.getParameter("itemsize").trim()));
				vendoritems.setItemsizeunits(request.getParameter("itemsizeunits").trim());

				ordersService.saveVendorItems(vendoritems);

				mav.setViewName("addvendorpartpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("partno", addvendorpartsearchtxt);
				mav.addObject("vendorselected", vendorselected);
				return mav;
			}

			else {
				mav.setViewName("addvendorpartpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("partno", addvendorpartsearchtxt);
				mav.addObject("vendorselected", vendorselected);
				return mav;

			}
		}

	}

	@RequestMapping("createneworder")
	public ModelAndView processCreateNewOrder(@RequestParam("createneworderbtn") String createneworderbtn,
			@RequestParam("createnewordernosearchtxt") String createnewordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (vendorlistdd.size() <= 0) {
				vendorlistdd = mainService.getAllVendors();
			}

			if (createneworderbtn.equalsIgnoreCase("createneworder")) {
				VendorOrder vendororder = ordersService.createNewOrder(createnewordernosearchtxt,
						vendorlistdd.get(vendorselected));
				mav.setViewName("createordernopage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("vendororder", vendororder);
				mav.addObject("orderno", 0);
				return mav;

			} else {
				if (vendorlistdd.size() <= 0) {
					vendorlistdd = mainService.getAllVendors();
				}
				mav.clear();
				mav.setViewName("createordernopage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject("sysdate", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			}
		}
	}

	@RequestMapping("deadinventory")
	public ModelAndView processDeadInventory(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate,
			@RequestParam("deadinventoryBtn") String deadinventoryBtn,
			@RequestParam("subcategoryselected") String subcategoryselected, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}
			if (deadinventoryBtn.equalsIgnoreCase("categorysales")) {

				if (subcategoryselected.equalsIgnoreCase("ALL")) {
					categorysaleslist = ordersService.getDeadInventoryBySubcategoryAll(analyticsfromdate,
							analyticstodate, subcategorylistdd.get(subcategoryselected));
				} else {
					categorysaleslist = ordersService.getDeadInventoryBySubcategory(analyticsfromdate, analyticstodate,
							subcategorylistdd.get(subcategoryselected));

				}

				mav.setViewName("deadinventorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("subcategoryselected", subcategoryselected);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("categorysaleslist", categorysaleslist);
				return mav;
			} else if (deadinventoryBtn.equalsIgnoreCase("savesales")) {

				try {

					int length = 0;
					BVSExcel bvsexcel = new BVSExcel();
					String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;

					ServletOutputStream outStream;
					outStream = response.getOutputStream();
					File file = bvsexcel.createDeadInventoryFile(categorysaleslist, filePath, subcategoryselected);

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

				} catch (NumberFormatException e) {
					LOGGER.error("NumberFormatException: processAnalysis##" + e.getMessage().toString());
					mav.setViewName("deadinventorypage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("analyticsfromdate", analyticsfromdate);
					mav.addObject("analyticstodate", analyticstodate);
					mav.addObject("subcategoryselected", subcategoryselected);
					mav.addObject("subcategorylistdd", subcategorylistdd);
					mav.addObject("categorysaleslist", categorysaleslist);
					return mav;
				} catch (IOException e) {
					LOGGER.error("IOException: processAnalysis##" + e.getMessage().toString());
					mav.setViewName("deadinventorypage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("analyticsfromdate", analyticsfromdate);
					mav.addObject("analyticstodate", analyticstodate);
					mav.addObject("subcategoryselected", subcategoryselected);
					mav.addObject("subcategorylistdd", subcategorylistdd);
					mav.addObject("categorysaleslist", categorysaleslist);
					return mav;
				}
				mav.setViewName("deadinventorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("subcategoryselected", subcategoryselected);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				mav.addObject("categorysaleslist", categorysaleslist);
				return mav;
			}

			else {
				mav.clear();
				mav.setViewName("deadinventorypage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("analyticsfromdate", analyticsfromdate);
				mav.addObject("analyticstodate", analyticstodate);
				mav.addObject("subcategorylistdd", subcategorylistdd);
				return mav;
			}
		}
	}

	@RequestMapping("formatelabel")
	public ModelAndView processLabel(@ModelAttribute("labelfile") FileUploadForm uploadForm, Model map,
			HttpSession session, ModelAndView mav) throws IOException {

		AppUser user = (AppUser) session.getAttribute("user");
		String message = "";
		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			List<MultipartFile> labelfiles = uploadForm.getFiles();
			if (null != labelfiles && labelfiles.size() == 1) {
				if (null != labelfiles.get(0) && !labelfiles.get(0).isEmpty()) {
					String fileName = labelFormateService.formateLabelToWord(labelfiles.get(0), repository);
					mav.addObject("downloadFileName", fileName);
				} else {
					message = "No files uploaded";
				}
			} else {
				message = "No files uploaded";
			}
			mav.addObject("message", message);
			mav.setViewName("formatelabelpage");
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		}
	}

	@RequestMapping("procurement")
	public ModelAndView processProcurement(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("ordertypeselected") String ordertypeselected, @RequestParam("stocklimit") String stocklimit,
			@RequestParam("onorderlimit") String onorderlimit, @RequestParam("orderlimit") String orderlimit,
			@RequestParam("branchselected") String branchselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}
			if (ordertypelistdd.size() == 0) {
				ordertypelistdd = mainService.getAllOrderType();
			}
			if (branchlistdd.size() == 0) {
				branchlistdd = mainService.getAllOtherBranches(branch);
			}

			procurementpartslist = ordersService.getProcurementParts(analyticsfromdate,
					subcategorylistdd.get(subcategoryselected), ordertypeselected, stocklimit, onorderlimit,
					orderlimit);

			mav.setViewName("procurementpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", analyticsfromdate);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("ordertypeselected", ordertypeselected);
			mav.addObject("ordertypelistdd", ordertypelistdd);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("procurementpartslist", procurementpartslist);
			mav.addObject("listsize", procurementpartslist.size());
			mav.addObject("stocklimit", stocklimit);
			mav.addObject("onorderlimit", onorderlimit);
			mav.addObject("orderlimit", orderlimit);
			mav.addObject("branchlistdd", branchlistdd);
			mav.addObject("branchselected", branchselected);

			return mav;
		}

	}

	@RequestMapping("getprojection")
	public ModelAndView processProjection(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("ordertypeselected") String ordertypeselected, @RequestParam("stocklimit") String stocklimit,
			@RequestParam("onorderlimit") String onorderlimit, @RequestParam("orderlimit") String orderlimit,
			@RequestParam("branchselected") String branchselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) throws ParseException {

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}

			if (ordertypelistdd.size() == 0) {
				ordertypelistdd = mainService.getAllOrderType();
			}

			if (branchlistdd.size() == 0) {
				branchlistdd = mainService.getAllOtherBranches(branch);
			}

			procurementpartslist = ordersService.getHistoricSales(procurementpartslist, analyticsfromdate);

			mav.setViewName("procurementpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", analyticsfromdate);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("ordertypeselected", ordertypeselected);
			mav.addObject("ordertypelistdd", ordertypelistdd);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("procurementpartslist", procurementpartslist);
			mav.addObject("listsize", procurementpartslist.size());
			mav.addObject("stocklimit", stocklimit);
			mav.addObject("onorderlimit", onorderlimit);
			mav.addObject("orderlimit", orderlimit);
			mav.addObject("branchlistdd", branchlistdd);
			mav.addObject("branchselected", branchselected);

			return mav;
		}

	}

	@RequestMapping("salesanalysis")
	public ModelAndView processSalesAnalysis(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate,
			@RequestParam("subcategoryselected") String subcategoryselected, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (subcategorylistdd.size() == 0) {
				subcategorylistdd = mainService.getAllSubCategory();
			}

			if (subcategoryselected.equalsIgnoreCase("ALL")) {
				categorysaleslist = ordersService.getSalesBySubcategoryAll(analyticsfromdate, analyticstodate,
						subcategorylistdd.get(subcategoryselected));

			} else {
				categorysaleslist = ordersService.getSalesBySubcategory(analyticsfromdate, analyticstodate,
						subcategorylistdd.get(subcategoryselected));

			}

			mav.setViewName("salesanalysispage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", analyticsfromdate);
			mav.addObject("analyticstodate", analyticstodate);
			mav.addObject("subcategoryselected", subcategoryselected);
			mav.addObject("subcategorylistdd", subcategorylistdd);
			mav.addObject("categorysaleslist", categorysaleslist);
			return mav;
		}

	}

	@RequestMapping("salesanalysishistory")
	public ModelAndView processSalesAnalysisHistory(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if (user == null) {
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {

			categorysaleshistorylist = ordersService.getSalesHistoryByCategory(analyticsfromdate, analyticstodate);

			mav.setViewName("saleshistorypage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
			mav.addObject("analyticsfromdate", analyticsfromdate);
			mav.addObject("analyticstodate", analyticstodate);
			mav.addObject("categorysaleshistorylist", categorysaleshistorylist);
			return mav;
		}

	}

	@SuppressWarnings("null")
	@RequestMapping("scanorder")
	public ModelAndView processScanOrders(@RequestParam("scanorderbtn") String scanorderbtn,
			@RequestParam("scanordernosearchtxt") String scanordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

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
			if (scanorderbtn.equalsIgnoreCase("scanordersearch")) {
				if (scanordernosearchtxt.equalsIgnoreCase("")) {

					mav.setViewName("scanorderpage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("vendorlistdd", vendorlistdd);
					mav.addObject("orderno", 0);
					return mav;

				} else {
					List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
					List<ScanOrderDetails> scanorderdetailslist = new ArrayList<ScanOrderDetails>();
					vendororderlist.clear();
					scanorderdetailslist.clear();
					vendororderlist = ordersService.getVendorOrder(vendorlistdd.get(vendorselected),
							Integer.parseInt(scanordernosearchtxt));

					mav.setViewName("scanorderpage");
					mav.addObject("user", user);
					mav.addObject("branch", branch);
					mav.addObject("appcss", appcss);
					mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
					mav.addObject("vendorlistdd", vendorlistdd);
					mav.addObject("vendorselected", vendorselected);
					mav.addObject("orderno", Integer.parseInt(scanordernosearchtxt));

					if (vendororderlist != null) {
						if (vendororderlist.size() > 0) {
							mav.addObject("vendororder", vendororderlist.get(0));
							scanorderdetailslist.clear();
							scanorderdetailslist = ordersService
									.getScanOrderDetails(Integer.parseInt(scanordernosearchtxt));
							if (scanorderdetailslist != null) {
								if (scanorderdetailslist.size() > 0) {

									mav.addObject("scanorderdetailslist", scanorderdetailslist);
								} else {
									scanorderdetailslist.add(new ScanOrderDetails());
									mav.addObject("scanorderdetailslist", scanorderdetailslist);
								}
							} else {
								scanorderdetailslist.add(new ScanOrderDetails());
								mav.addObject("scanorderdetailslist", scanorderdetailslist);
							}
						} else {
							mav.addObject("vendororder", new VendorOrder());
							scanorderdetailslist.add(new ScanOrderDetails());
							mav.addObject("scanorderdetailslist", scanorderdetailslist);
						}
					}
					return mav;
				}

			} else if (scanorderbtn.equalsIgnoreCase("updateorder")) {

				Integer i = 0;
				Integer totalitems = 0;

				String[] partnoRP = request.getParameterValues("partno");
				String[] quantityRP = request.getParameterValues("quantity");

				List<VendorOrderedItems> vendororderdetailslist = new ArrayList<VendorOrderedItems>();
				for (i = 0; i < partnoRP.length; i++) {
					if (Integer.parseInt(quantityRP[i]) > 0) {

						VendorOrderedItems vendororderitems = new VendorOrderedItems();
						vendororderitems.setOrderno(Integer.parseInt(scanordernosearchtxt));
						vendororderitems.setNoorder(totalitems++);
						vendororderitems.setPartno(partnoRP[i]);
						vendororderitems.setVendorpartno("");
						vendororderitems.setPrice(new BigDecimal("0.00"));
						vendororderitems.setQuantity(Integer.parseInt(quantityRP[i]));
						vendororderdetailslist.add(vendororderitems);

					}
				}

				ordersService.deleteVendorOrderedItems(Integer.parseInt(scanordernosearchtxt));
				ordersService.updateVendorOrderedItems(vendororderdetailslist);

				List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
				List<ScanOrderDetails> scanorderdetailslist = new ArrayList<ScanOrderDetails>();
				vendororderlist.clear();
				scanorderdetailslist.clear();
				vendororderlist = ordersService.getVendorOrder(vendorlistdd.get(vendorselected),
						Integer.parseInt(scanordernosearchtxt));

				mav.setViewName("scanorderpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("vendorselected", vendorselected);
				mav.addObject("orderno", Integer.parseInt(scanordernosearchtxt));

				if (vendororderlist != null) {
					if (vendororderlist.size() > 0) {
						mav.addObject("vendororder", vendororderlist.get(0));
						scanorderdetailslist.clear();
						scanorderdetailslist = ordersService
								.getScanOrderDetails(Integer.parseInt(scanordernosearchtxt));
						if (scanorderdetailslist != null) {
							if (scanorderdetailslist.size() > 0) {

								mav.addObject("scanorderdetailslist", scanorderdetailslist);
							} else {
								scanorderdetailslist.add(new ScanOrderDetails());
								mav.addObject("scanorderdetailslist", scanorderdetailslist);
							}
						} else {
							scanorderdetailslist.add(new ScanOrderDetails());
							mav.addObject("scanorderdetailslist", scanorderdetailslist);
						}
					} else {
						mav.addObject("vendororder", new VendorOrder());
						scanorderdetailslist.add(new ScanOrderDetails());
						mav.addObject("scanorderdetailslist", scanorderdetailslist);
					}
				}
				return mav;

			} else {
				if (vendorlistdd.size() == 0) {
					vendorlistdd = mainService.getAllVendors();
				}

				mav.setViewName("scanorderpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("vendorlistdd", vendorlistdd);
				mav.addObject("orderno", 0);
				return mav;
			}
		}
	}

	@RequestMapping("vendoritemsupload")
	public ModelAndView processVendorItemsUpload(@RequestParam("vendoritemsuploadmode") String vendoritemsuploadmode,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		String message = "";

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (vendoritemsuploadmode.equalsIgnoreCase("vendoritemsupload")) {
				message = excelService.uploadExcelToVendorItems(uploadForm);
				mav.setViewName("vendoritemsuploadpage");
				mav.addObject("message", message);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);

				return mav;

			} else {
				mav.clear();
				mav.setViewName("vendoritemsuploadpage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				return mav;
			}
		}
	}

	// vendoritemsupload
	@RequestMapping("vendorsales")
	public ModelAndView processVendorSales(@RequestParam("vendorsalesmode") String vendorsalesmode,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map, HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		vendorsaleslist.clear();

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			if (vendorsalesmode.equalsIgnoreCase("vendorsales")) {
				vendorsaleslist = excelService.uploadExcelToVendorSales2(uploadForm);
				mav.setViewName("vendorsalespage");
				mav.addObject("vendorsaleslist", vendorsaleslist);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);

				return mav;

			} else {
				mav.clear();
				mav.setViewName("vendorsalespage");
				mav.addObject("user", user);
				mav.addObject("branch", branch);
				mav.addObject("appcss", appcss);
				mav.addObject(" InsightUtils.getNewUSDate()", InsightUtils.getNewUSDate());
				return mav;
			}
		}
	}

	@RequestMapping("saveanalysis")
	public ResponseEntity<Object> saveAnalysis(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("analyticstodate") String analyticstodate,
			@RequestParam("subcategoryselected") String subcategoryselected, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		if (subcategorylistdd.size() == 0) {
			subcategorylistdd = mainService.getAllSubCategory();
		}
		try {

			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;

			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = bvsexcel.createSalesAnalysisFile(categorysaleslist, filePath, subcategoryselected);

			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length()); // sets HTTP
															// header
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
			byte[] byteBuffer = new byte[BUFSIZE];
			DataInputStream in = new DataInputStream(new FileInputStream(file));

			// reads the file's bytes and writes them to the response //
			// stream
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
				outStream.write(byteBuffer, 0, length);
			}

			IOUtils.copy(in, outStream);
			IOUtils.closeQuietly(in);

			response.flushBuffer();
			return new ResponseEntity<Object>(HttpStatus.OK);

		} catch (NumberFormatException e) {
			LOGGER.error("NumberFormatException: processAnalysis##" + e.getMessage().toString());
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (IOException e) {
			LOGGER.error("IOException: processAnalysis##" + e.getMessage().toString());
			return new ResponseEntity<Object>(HttpStatus.OK);

		}
	}

	@RequestMapping("saveprocurement")
	public ResponseEntity<Object> saveProcurement(@RequestParam("analyticsfromdate") String analyticsfromdate,
			@RequestParam("subcategoryselected") String subcategoryselected,
			@RequestParam("ordertypeselected") String ordertypeselected, @RequestParam("stocklimit") String stocklimit,
			@RequestParam("onorderlimit") String onorderlimit, @RequestParam("orderlimit") String orderlimit,
			@RequestParam("branchselected") String branchselected, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		if (subcategorylistdd.size() == 0) {
			subcategorylistdd = mainService.getAllSubCategory();
		}

		if (ordertypelistdd.size() == 0) {
			ordertypelistdd = mainService.getAllOrderType();
		}

		if (branchlistdd.size() == 0) {
			branchlistdd = mainService.getAllOtherBranches(branch);
		}

		try {
			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;

			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = bvsexcel.createProcurementFile(procurementpartslist, filePath, subcategoryselected);

			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length()); // sets HTTP
															// header
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
			byte[] byteBuffer = new byte[BUFSIZE];
			DataInputStream in = new DataInputStream(new FileInputStream(file));

			// reads the file's bytes and writes them to the response //
			// stream
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
				outStream.write(byteBuffer, 0, length);
			}

			IOUtils.copy(in, outStream);
			IOUtils.closeQuietly(in);

			response.flushBuffer();
			return new ResponseEntity<Object>(HttpStatus.OK);

		} catch (IOException e) {
			LOGGER.error("IOException: saveProcurement##" + e.getMessage().toString());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

	}

	@RequestMapping("savevendorsales")
	public ResponseEntity<?> saveVendorSales(@RequestParam("vendorsalesmode") String vendorsalesmode,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		@SuppressWarnings("unused")
		AppUser user = (AppUser) session.getAttribute("user");

		try {

			// LOGGER.info("sixe:" + vendorsaleslist.size());
			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = bvsexcel.createVendorSalesExcelFile(vendorsaleslist, filePath);

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
}
