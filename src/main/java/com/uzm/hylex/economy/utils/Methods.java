package com.uzm.hylex.economy.utils;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class Methods {

	public static NumberFormat numberFormat;

	public static String modifyBalance(double value) {

		numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("en-US"));
		if (value <= 1.0D) {
			if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.").length > 1) {
				if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.")[1].length() == 3) {
					return numberFormat.format(value).replace(" ", "").substring(0,
							numberFormat.format(value).replace(" ", "").length() - 1);
				}
			} else {
				return numberFormat.format(value).concat(" ").concat("").replace(" ", "");
			}

		} else if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.").length > 1) {
			if (numberFormat.format(value).concat(" ").concat("").replace(" ", "").split("\\.")[1].length() == 3) {
				return numberFormat.format(value).replace(" ", "").substring(0,
						numberFormat.format(value).replace(" ", "").length() - 1);
			}
		} else {
			return numberFormat.format(value).concat(" ").concat("").replace(" ", "");
		}
		return numberFormat.format(value).concat(" ").concat("").replace(" ", "");

	}

	public static boolean isNumber(String strNum) {
		return strNum.matches("-?\\d+(\\.\\d+)?");
	}

	public static boolean deleteWorld(File file) {

		File f = file;
		return f.delete();
	}
}
