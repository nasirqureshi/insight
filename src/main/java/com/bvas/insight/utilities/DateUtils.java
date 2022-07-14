package com.bvas.insight.utilities;

// ~--- JDK imports ------------------------------------------------------------

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

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
			LOGGER.info(e.toString());
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
			LOGGER.info(e.toString());
		}

		return mysqlDate;
	}

	public static java.sql.Date getCurrentSQLDate() {
		java.util.Date today = new java.util.Date();

		return new java.sql.Date(today.getTime());
	}

	public static long getLongFromUSDate(String usDate) {
		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");
			java.util.Date dd1 = sdf.parse(usDate);

			return dd1.getTime();
		} catch (Exception e) {
			return 0L;
		}
	}

	public static String getNewUSDate() {
		String usDate = "";
		java.util.Date dd = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");

		usDate = sdf.format(dd);

		return usDate;
	}

	public static String getNewUSDateForInvoice() {
		String usDate = "";

		usDate = getNewUSDate();

		return usDate;
	}

	public static String getUSDateFromLong(long usDate) {
		return "";
	}
}
