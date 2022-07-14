package com.bvas.insight.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {

	static List<String> arrdup = new ArrayList<String>();

	static Set<String> arrset = new HashSet<String>();

	private static final Logger LOGGER = LoggerFactory.getLogger(test.class);

	public static boolean DuplicateString(String str) {
		// LOGGER.info("string received = " + str);

		// if(arrdup.isEmpty())
		// {
		// arrdup.add(str);
		// return false;
		// }
		// for(String strdup:arrdup){
		//
		// if(strdup.equals(str))
		// return true;
		//
		// }

		// if(!arrdup.contains(str))
		// arrdup.add(str);
		// else
		// return true;

		if (arrset.add(str))
			return false;
		else
			return true;

		// for(String str1:arrset){
		// LOGGER.info("elements = " + str1);
		// }
		//

	}

	public static void main(String[] args) {
		//
		// String[] arraystring = {"Hello","Hi","Hello"};
		// for(int i=0;i<arraystring.length;i++)
		// {
		//
		// boolean returnFlag = DuplicateString(arraystring[i]);
		// LOGGER.info("returned flag " + returnFlag);
		// }
		// Function<Integer,Integer> add1 = x -> x + 1;
		//
		// Integer two = add1.apply(1);
		// LOGGER.info("two = " +two);

		String test = "webservicesoap";

		char[] testChar = test.toCharArray();
		char[] testChar_dup = new char[test.length()];

		for (int i = 0; i < testChar.length; i++) {
			for (int j = i + 1; j < testChar.length; j++) {

				if (testChar[i] == testChar[j]) {
					// LOGGER.info("Substring = " + test.substring(i, j));
					i = i + 1;
					break;
				}
				// i++;
			}
			// LOGGER.info(" i = " + i);

		}

	}

}
