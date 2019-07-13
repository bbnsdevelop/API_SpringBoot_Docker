package br.com.restWithSpringBoot.utils;

public class UtilOperation {
	
	
	public static Double convertToDouble(String number) {
		if(number == null) {
			return 0D;
		}
		return Double.valueOf(replaceCommaToScore(number));
	}

	public static boolean isNumeric(String number) {
		if(number == null) {
			return false;
		}
		String replaceInNumber  = replaceCommaToScore(number);
		
		return replaceInNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	private static String replaceCommaToScore(String number) {
		return number.replace(",", ".");
	}

}
