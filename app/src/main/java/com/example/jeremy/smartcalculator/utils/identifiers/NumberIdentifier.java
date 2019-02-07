package com.example.jeremy.smartcalculator.utils.identifiers;

import com.example.jeremy.smartcalculator.constants.Symbols;

public final class NumberIdentifier {

	//global vars
	private static int countOfDot;
	private static boolean isNumber;
	private static boolean dotIsFirst;
	
	private static void init() {
		countOfDot = 0;
		dotIsFirst = false;
		isNumber = false;
	}
	
	public static boolean isNumber(String info) {
		init();
		return ((isDouble(info) || isInteger(info)) && !dotIsFirst);
	}
	
	private static boolean isDouble(String info) {
		convertToCharArray(info);
		return countOfDot == 1 && isNumber;
	}
	
	private static boolean isInteger(String info) {
		convertToCharArray(info);
		return countOfDot == 0 && isNumber;
	}

	private static void convertToCharArray(String info) {
		final char[] chars = info.toCharArray();
		for (char ch : chars) {
			if (chars[0] == Symbols.DOT) {
				dotIsFirst = true;
				break;
				//quit 'for-each' loop
			}
			else {
				 if (ch == Symbols.DOT) countOfDot++;
				 else isNumber = true;
			}
		}
	}
}
