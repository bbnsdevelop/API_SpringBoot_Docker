package br.com.restWithSpringBoot.teste;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.restWithSpringBoot.exception.UnsuporteMathOperationException;

@RestController
public class CalculatorController {

	
	@GetMapping("/sum/{number1}/{number2}")
	public Double sum(@PathVariable("number1") String number1,@PathVariable("number2") String number2) throws Exception {
		if(!isNumeric(number1) || !isNumeric(number2)) {
			throw new UnsuporteMathOperationException("Please set a numeric value");
		}
		Double sum = convertToDouble(number1) + convertToDouble(number2);
		return sum;
	}

	private Double convertToDouble(String number) {
		if(number == null) {
			return 0D;
		}
		return Double.valueOf(replaceCommaToScore(number));
	}

	private boolean isNumeric(String number) {
		if(number == null) {
			return false;
		}
		String replaceInNumber  = replaceCommaToScore(number);
		
		return replaceInNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	private String replaceCommaToScore(String number) {
		return number.replace(",", ".");
	}
}
