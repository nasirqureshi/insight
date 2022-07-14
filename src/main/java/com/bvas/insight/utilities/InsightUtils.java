package com.bvas.insight.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bvas.insight.service.InvoiceService;

public class InsightUtils {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

	public static String allowOnlyNumbers(String numberstring) {

		String digits = numberstring.replaceAll("[^0-9]", "");
		return digits;
	}

	public static BigDecimal calculateSalesPercent(BigDecimal actualprice, BigDecimal costprice) {

		BigDecimal val100 = new BigDecimal("100.00");

		if ((costprice == null) || (costprice.compareTo(BigDecimal.ZERO) < 1) || (actualprice == null)
				|| (actualprice.compareTo(BigDecimal.ZERO) < 1)) {
			return BigDecimal.ZERO;
		} else {
			BigDecimal diff = costprice.subtract(actualprice);
			if ((diff.compareTo(BigDecimal.ZERO) < 1)) {
				return BigDecimal.ZERO;
			} else {
				return diff.multiply(val100).divide(costprice, 2, RoundingMode.HALF_UP);
			}
		}
	}

	public static String convertMySQLToUSFormat(String mysqlDate) {

		String usDate = "";
		java.text.SimpleDateFormat usSDF = new java.text.SimpleDateFormat("MM-dd-yyyy");
		java.text.SimpleDateFormat mysqlSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");

		try {
			if (mysqlDate == null) {
				return usDate;
			} else {
				java.util.Date dd = mysqlSDF.parse(mysqlDate);
				usDate = usSDF.format(dd);
			}

		} catch (ParseException e) {

		}

		return usDate;
	}

	public static String convertUSToMySQLFormat(String usDate) {

		String mysqlDate = "";
		java.text.SimpleDateFormat usSDF = new java.text.SimpleDateFormat("MM-dd-yyyy");
		java.text.SimpleDateFormat mysqlSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");

		try {

			java.util.Date dd = usSDF.parse(usDate);
			mysqlDate = mysqlSDF.format(dd);

		} catch (ParseException e) {

		}

		return mysqlDate;
	}

	public static String getCurrentMySQLDate() {

		java.util.Date today = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(today.toString()).toString();
		} catch (ParseException e) {
			return null;
		}

	}

	public static java.sql.Date getCurrentSQLDate() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	public static java.util.Date getDate(Calendar calender) {
		java.util.Date date = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int yr = calender.get(Calendar.YEAR);
			int mnt = calender.get(Calendar.MONTH) + 1;
			String mntStr = mnt < 10 ? "0" + mnt : "" + mnt;
			int day = calender.get(Calendar.DATE);
			String dayStr = day < 10 ? "0" + day : "" + day;
			date = df.parse(yr + "-" + mntStr + "-" + dayStr);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return date;
	}

	public static Date getFormatedDate(String date) {

		java.util.Date dd = new java.util.Date();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		try {
			dd = df.parse(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return dd;
	}

	public static Date getFormatedDate2(String date) {

		java.util.Date dd = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dd = df.parse(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return dd;
	}

	@SuppressWarnings("rawtypes")
	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	public static String getNewUSDate() {

		String usDate = "";
		java.util.Date dd = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");
		usDate = sdf.format(dd);
		return usDate;
	}

	public static int getPTermsDays(String ch) {

		if (ch.equalsIgnoreCase("M")) {
			return 33;
		} else if (ch.equalsIgnoreCase("W")) {
			return 9;
		}
		if (ch.equalsIgnoreCase("O")) {
			return 2;
		} else if (ch.equalsIgnoreCase("C")) {
			return 2;
		} else if (ch.equalsIgnoreCase("B")) {
			return 16;
		} else
			return 0;
	}

	public static String getPTermsDesc(String ch) {

		if (ch.equalsIgnoreCase("M")) {
			return "Monthly";
		} else if (ch.equalsIgnoreCase("W")) {
			return "Weekly";
		}
		if (ch.equalsIgnoreCase("O")) {
			return "CASH ONLY";
		} else if (ch.equalsIgnoreCase("C")) {
			return "C.O.D.";
		} else if (ch.equalsIgnoreCase("B")) {
			return "Bi-Weekly";
		} else
			return ch;
	}

}
