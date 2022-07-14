package com.bvas.insight.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

import com.bvas.insight.data.CategoryBySalesAnalysis;
import com.bvas.insight.data.PartList;
import com.bvas.insight.data.PreOrderParts;
import com.bvas.insight.data.VendorOrderedItemsDetails;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.service.LabelService;
import com.bvas.insight.service.OrdersService;
import com.bvas.insight.utilities.AppUser;
import com.bvas.insight.utilities.BVSExcel;
import com.bvas.insight.utilities.InsightUtils;

@Scope("session")
@Controller
@SessionAttributes({ "user" })
@RequestMapping("existingorders")
public class ExistingOrdersController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExistingOrdersController.class);

	List<CategoryBySalesAnalysis> categorysaleslist = new LinkedList<CategoryBySalesAnalysis>();

	@Autowired
	@Qualifier("labelService")
	protected LabelService labelService;

	@Autowired
	@Qualifier("ordersService")
	protected OrdersService ordersService;

	protected VendorItems vendoritems = new VendorItems();

	@SuppressWarnings("null")
	private ModelAndView displayExistingOrder(Integer togglebuttons, String vendorselected,
			String existingordernosearchtxt,
			@RequestParam(value = "alllocations", required = false) String alllocations, ModelAndView mav,
			AppUser user) {

		List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
		List<VendorOrderedItemsDetails> vendororderitemsdetailslist = new ArrayList<VendorOrderedItemsDetails>();
		vendororderlist.clear();
		vendororderitemsdetailslist.clear();
		vendororderlist = ordersService.getVendorOrder(vendorlistdd.get(vendorselected),
				Integer.parseInt(existingordernosearchtxt));

		mav.clear();
		mav.setViewName("existingorderpage");
		mav.addObject("user", user);
		mav.addObject("branch", branch);
		mav.addObject("appcss", appcss);
		mav.addObject("sysdate", InsightUtils.getNewUSDate());
		mav.addObject("vendorlistdd", vendorlistdd);
		mav.addObject("vendorselected", vendorselected);
		mav.addObject("alllocations", alllocations);
		mav.addObject("orderno", Integer.parseInt(existingordernosearchtxt));

		if (alllocations == null) {
			alllocations = "";
		}

		if (vendororderlist != null) {
			if (vendororderlist.size() > 0) {
				mav.addObject("vendororder", vendororderlist.get(0));
				vendororderitemsdetailslist.clear();
				vendororderitemsdetailslist = ordersService
						.getVendorOrderedItemsDetails(Integer.parseInt(existingordernosearchtxt));
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

	@RequestMapping("getaddtoorder")
	public ModelAndView getAddToOrder(@RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate,
			@RequestParam(value = "alllocations", required = false) String alllocations,
			@RequestParam("btnval") String btnval, HttpServletResponse response, HttpSession session,
			ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		if ((user == null)) {
			mav.clear();
			mav.setViewName("loginpage");
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			return mav;
		} else {
			mav.clear();
			mav.setViewName("addtoorderpage");
			mav.addObject("user", user);
			mav.addObject("branch", branch);
			mav.addObject("appcss", appcss);
			mav.addObject("sysdate", InsightUtils.getNewUSDate());
			mav.addObject("fromdate", fromdate);
			mav.addObject("todate", todate);
			List<VendorOrderedItemsDetails> addtoordredetails = ordersService.getAddToOrderDetails(fromdate, todate);
			mav.addObject("alllocations", alllocations);
			mav.addObject("addtoorderlist", addtoordredetails);

			if (alllocations == null) {
				alllocations = "";
			}

			if (btnval.equals("saveexcel")) {
				try {
					int length = 0;
					BVSExcel bvsexcel = new BVSExcel();
					String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;

					ServletOutputStream outStream;
					outStream = response.getOutputStream();
					File file = bvsexcel.createAddtoOrderFile(addtoordredetails, filePath, (fromdate + "_" + todate));

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

					return mav;
				} catch (IOException e) {
					LOGGER.error("IOException: processAnalysis##" + e.getMessage().toString());
					mav.addObject("addtoorderlist", addtoordredetails);
					return mav;
				}
			}

			return mav;
		}

	}

	// resetunitonorder

	@RequestMapping("getexistingorder")
	public ModelAndView getExistingOrder(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");

		Integer togglebuttons = 0;
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
			return displayExistingOrder(togglebuttons, vendorselected, existingordernosearchtxt, alllocations, mav,
					user);
		}

	}

	@RequestMapping("getlabels")
	public ResponseEntity<?> getLabels(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		if (vendorlistdd.size() <= 0) {
			vendorlistdd = mainService.getAllVendors();
		}
		try {

			if (alllocations == null) {
				alllocations = "";
			}
			LOGGER.info("alllocations:" + alllocations);
			int length = 0;
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = labelService.processLabelFile(Integer.parseInt(existingordernosearchtxt), filePath,
					alllocations);
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

	@RequestMapping("postorder")
	public ResponseEntity<?> getPostorder(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		if (vendorlistdd.size() <= 0) {
			vendorlistdd = mainService.getAllVendors();
		}
		try {

			if (alllocations == null) {
				alllocations = "";
			}

			int length = 0;

			BVSExcel bvsexcel = new BVSExcel();

			Integer supplierid = ordersService.getSupplierid(Integer.parseInt(existingordernosearchtxt));
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
			List<PreOrderParts> parts = ordersService.getPreOrderParts(Integer.parseInt(existingordernosearchtxt),
					supplierid);
			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = bvsexcel.createPostOrderExcelFile(parts, Integer.parseInt(existingordernosearchtxt), filePath);
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

	@RequestMapping("preorder")
	public ResponseEntity<?> getPreorder(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		if (vendorlistdd.size() <= 0) {
			vendorlistdd = mainService.getAllVendors();
		}
		try {

			if (alllocations == null) {
				alllocations = "";
			}

			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();

			Integer supplierid = ordersService.getSupplierid(Integer.parseInt(existingordernosearchtxt));
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
			List<PreOrderParts> parts = ordersService.getPreOrderParts(Integer.parseInt(existingordernosearchtxt),
					supplierid);
			ServletOutputStream outStream = response.getOutputStream();
			File file;
			file = bvsexcel.createPreOrderExcelFile(parts, Integer.parseInt(existingordernosearchtxt), filePath);
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

	@RequestMapping("removeduplicates")
	public ModelAndView removeDuplicates(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		AppUser user = (AppUser) session.getAttribute("user");
		// LOGGER.info("removeduplicates");

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
			// initialization

			if (alllocations == null) {
				alllocations = "";
			}

			Set<String> set = new HashSet<String>();
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

			ordersService.deleteVendorOrderedItems(Integer.parseInt(existingordernosearchtxt));

			List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
			for (i = 0; i < partnoRP.length; i++) {
				if (Integer.parseInt(quantityRP[i]) > 0) {
					if (set.add(partnoRP[i])) {
						VendorOrderedItems vendororderitems = new VendorOrderedItems();
						vendororderitems.setOrderno(Integer.parseInt(existingordernosearchtxt));
						vendororderitems.setNoorder(totalitems++);
						vendororderitems.setPartno(partnoRP[i]);
						vendororderitems.setVendorpartno(vendorpartnoRP[i]);
						vendororderitems.setPrice(new BigDecimal(priceRP[i]));
						vendororderitems.setQuantity(Integer.parseInt(quantityRP[i]));
						vendororderitemslist.add(vendororderitems);
						ordertotalf = ordertotalf + (Float.parseFloat(priceRP[i])) * Integer.parseInt(quantityRP[i]);
					}
				}
			}

			BigDecimal ordertotal = new BigDecimal(ordertotalf);

			ordersService.updateVendorOrderedItems(vendororderitemslist);
			ordersService.updateVendorOrder(Integer.parseInt(existingordernosearchtxt), totalitems, ordertotal);

			return displayExistingOrder(1, vendorselected, existingordernosearchtxt, alllocations, mav, user);
		}

	}

	@RequestMapping("yccprofortuneremove")
	public ModelAndView removeYCCPROFOTUNE(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

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
			ordersService.removeYCCProfortune(Integer.parseInt(existingordernosearchtxt));

			return displayExistingOrder(1, vendorselected, existingordernosearchtxt, alllocations, mav, user);

		}

	}

	@RequestMapping("resetunitonorder")
	public ModelAndView reSetUnitsOnOrder(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		// LOGGER.info("resetunitonorder was called");
		AppUser user = (AppUser) session.getAttribute("user");

		Integer togglebuttons = 0;
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

			if (alllocations == null) {
				alllocations = "";
			}
			ordersService.startResetUnitsOnOrderProcess();
			return displayExistingOrder(togglebuttons, vendorselected, existingordernosearchtxt, alllocations, mav,
					user);
		}

	}

	@RequestMapping("savepartslist")
	public ResponseEntity<?> savePartList(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

		if (vendorlistdd.size() <= 0) {
			vendorlistdd = mainService.getAllVendors();
		}
		try {
			if (alllocations == null) {
				alllocations = "";
			}
			int length = 0;
			BVSExcel bvsexcel = new BVSExcel();
			String filePath = request.getSession().getServletContext().getRealPath("") + File.separator;
			List<PartList> parts = ordersService.getPartlist(Integer.parseInt(existingordernosearchtxt));
			ServletOutputStream outStream;
			outStream = response.getOutputStream();
			File file = bvsexcel.createPartListExcelFile(parts, Integer.parseInt(existingordernosearchtxt), filePath);
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

	@RequestMapping("updateorder")
	public ModelAndView updateOrder(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

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

			if (alllocations == null) {
				alllocations = "";
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

			ordersService.deleteVendorOrderedItems(Integer.parseInt(existingordernosearchtxt));
			ordersService.deleteVendorOrderedItems(Integer.parseInt(existingordernosearchtxt));

			List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
			for (i = 0; i < partnoRP.length; i++) {
				if (Integer.parseInt(quantityRP[i]) > 0) {

					VendorOrderedItems vendororderitems = new VendorOrderedItems();
					vendororderitems.setOrderno(Integer.parseInt(existingordernosearchtxt));
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

			ordersService.updateVendorOrderedItems(vendororderitemslist);
			ordersService.updateVendorOrder(Integer.parseInt(existingordernosearchtxt), totalitems, ordertotal);
			return displayExistingOrder(1, vendorselected, existingordernosearchtxt, alllocations, mav, user);
		}

	}

	@RequestMapping("updatevendorparts")
	public ModelAndView updateVendorParts(@RequestParam("existingordernosearchtxt") String existingordernosearchtxt,
			@RequestParam("vendorselected") String vendorselected,
			@RequestParam(value = "alllocations", required = false) String alllocations, HttpServletResponse response,
			HttpSession session, ModelAndView mav) {

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
			Integer supplierid = ordersService.getSupplierid(Integer.parseInt(existingordernosearchtxt));

			ordersService.updateVendorParts(Integer.parseInt(existingordernosearchtxt), supplierid);

			return displayExistingOrder(1, vendorselected, existingordernosearchtxt, alllocations, mav, user);
		}

	}

}
