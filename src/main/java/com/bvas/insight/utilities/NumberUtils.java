package com.bvas.insight.utilities;

// ~--- JDK imports ------------------------------------------------------------

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtils.class);

	public static double cutFractions(double orig) {
		double removed = 0.0;
		NumberFormat nf = NumberFormat.getInstance();

		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);

		try {
			removed = Double.parseDouble(nf.format(orig));
		} catch (Exception e) {
			removed = 0;
			LOGGER.info("Error When Parsing the number in NumberUtils" + e);
		}

		return removed;
	}

	public static double cutFractions(double orig, int rnd) {
		double removed = 0.0;
		NumberFormat nf = NumberFormat.getInstance();

		nf.setMinimumFractionDigits(rnd);
		nf.setMaximumFractionDigits(rnd);

		try {
			removed = Double.parseDouble(nf.format(orig));
		} catch (Exception e) {
			removed = 0;
			LOGGER.info("Error When Parsing the number in NumberUtils" + e);
		}

		return removed;
	}

	public static String cutFractions(String orig) {
		String removed = "";

		if ((orig.indexOf(".") != -1) && ((orig.indexOf(".") + 2) < orig.length() - 1)) {
			removed = orig.substring(0, orig.indexOf(".") + 3);
		} else {
			removed = orig;
		}

		return removed;
	}

	public static String cutFractions(String orig, int rnd) {
		String removed = "";
		NumberFormat nf = NumberFormat.getInstance();

		nf.setMinimumFractionDigits(rnd);
		nf.setMaximumFractionDigits(rnd);

		try {
			removed = nf.format(Double.parseDouble(orig));
		} catch (Exception e) {
			removed = "";
			LOGGER.info("Error When Parsing the number in NumberUtils" + e);
		}

		return removed;
	}

	public static Double getBobCostprice(Float actualprice) {
		double costprice = 0.00d;

		if ((actualprice > 0) && (actualprice <= 0.99)) {
			costprice = actualprice / 0.21;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 0.99) && (actualprice <= 1.99)) {
			costprice = actualprice / 0.21;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 1.99) && (actualprice <= 2.99)) {
			costprice = actualprice / 0.26;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 2.99) && (actualprice <= 4.99)) {
			costprice = actualprice / 0.36;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 4.99) && (actualprice <= 9.99)) {
			costprice = actualprice / 0.46;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 9.99) && (actualprice <= 19.99)) {
			costprice = actualprice / 0.51;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 19.99) && (actualprice <= 39.99)) {
			costprice = actualprice / 0.56;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 39.99) && (actualprice <= 59.99)) {
			costprice = actualprice / 0.61;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 59.99) && (actualprice <= 79.99)) {
			costprice = actualprice / 0.66;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 79.99) && (actualprice <= 99.99)) {
			costprice = actualprice / 0.71;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else if ((actualprice > 99.99)) {
			costprice = actualprice / 0.76;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		} else {
			costprice = actualprice / 0.50;

			BigDecimal bd = new BigDecimal(costprice).setScale(0, RoundingMode.HALF_EVEN);

			return bd.doubleValue();
		}
	}

	public static String getYearFromString(String orig) {
		if (orig.length() != 2) {
			return "NA";
		} else {
			if ((orig.equalsIgnoreCase("00")) || (orig.equalsIgnoreCase("01")) || (orig.equalsIgnoreCase("02"))
					|| (orig.equalsIgnoreCase("03")) || (orig.equalsIgnoreCase("04")) || (orig.equalsIgnoreCase("05"))
					|| (orig.equalsIgnoreCase("06")) || (orig.equalsIgnoreCase("07")) || (orig.equalsIgnoreCase("08"))
					|| (orig.equalsIgnoreCase("09")) || (orig.equalsIgnoreCase("10")) || (orig.equalsIgnoreCase("11"))
					|| (orig.equalsIgnoreCase("12")) || (orig.equalsIgnoreCase("13")) || (orig.equalsIgnoreCase("14"))
					|| (orig.equalsIgnoreCase("15")) || (orig.equalsIgnoreCase("15")) || (orig.equalsIgnoreCase("16"))
					|| (orig.equalsIgnoreCase("17")) || (orig.equalsIgnoreCase("18"))) {
				return "20" + orig;
			} else {
				return "19" + orig;
			}
		}
	}

	public static void main(String[] args) {
		double o = 1559.129;

		o = cutFractions(o, 2);
	}

	public static String removeChar(String orig, String ch) {
		String removed = "";

		if (orig.indexOf(ch) != -1) {
			removed = orig.substring(0, orig.indexOf(ch)) + removeChar(orig.substring(orig.indexOf(ch) + 1), ch);
		} else {
			removed = orig;
		}

		return removed;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
