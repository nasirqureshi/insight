package com.bvas.insight.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.bvas.insight.data.InvoiceStatus;
import com.bvas.insight.jdbc.ChStocksDAOImpl;
import com.bvas.insight.jdbc.GrStocksDAOImpl;
import com.bvas.insight.jdbc.MpStocksDAOImpl;
import com.bvas.insight.service.ExcelService;
import com.bvas.insight.service.MainService;
import com.bvas.insight.service.ReorderLevelService;
import com.bvas.insight.service.ReportService;

public class BaseController {

	protected static final int BUFSIZE = 4096;

	protected static final Map<String, String> locationList = new HashMap<String, String>();

	static {

		locationList.put("CH", "CH");
		locationList.put("MP", "MP");
		locationList.put("GR", "GR");
	}

	@Value("${app.css}")
	protected String appcss;

	@Value("${app.appliedtax}")
	protected String appliedtax;

	@Value("${app.branch}")
	protected String branch;

	@Value("${app.branchcode}")
	protected String branchcode;

	protected Map<String, String> branchlistdd = new HashMap<String, String>();

	protected Map<String, String> categorylistdd = new HashMap<String, String>();

	@Autowired
	@Qualifier("chpartsdao")
	public ChStocksDAOImpl chpartsdao;

	protected Map<String, Integer> driverlistdd = new HashMap<String, Integer>();

	@Autowired
	@Qualifier("excelService")
	protected ExcelService excelService;

	@Autowired
	@Qualifier("grpartsdao")
	public GrStocksDAOImpl grpartsdao;

	protected InvoiceStatus invoicestatus = new InvoiceStatus();

	@Value("${app.locallightvendors}")
	protected String locallightvendors;

	@Value("${app.localseries}")
	protected String localseries;

	@Autowired
	@Qualifier("mainService")
	protected MainService mainService;

	protected Map<String, Integer> makelistdd = new LinkedHashMap<String, Integer>();

	protected Map<String, String> makemodellistdd = new HashMap<String, String>();

	@Autowired
	@Qualifier("mppartsdao")
	public MpStocksDAOImpl mppartsdao;

	protected List<String> ordertypelistdd = new ArrayList<String>();

	protected Map<String, String> paymentlistdd = new HashMap<String, String>();

	@Autowired
	@Qualifier("reorderLevelService")
	protected ReorderLevelService reorderLevelService;

	@Autowired
	@Qualifier("reportService")
	protected ReportService reportService;

	@Value("${app.repository}")
	protected String repository;

	@Autowired
	protected HttpServletRequest request;

	protected List<String> routelistdd = new LinkedList<String>();

	protected TreeMap<String, Integer> shippinglistdd = new TreeMap<String, Integer>();

	protected Map<String, String> subcategorylistdd = new HashMap<String, String>();

	protected Map<String, Integer> vendorlistdd = new HashMap<String, Integer>();
	protected List<Integer> yearslistdd = new ArrayList<Integer>();
}
