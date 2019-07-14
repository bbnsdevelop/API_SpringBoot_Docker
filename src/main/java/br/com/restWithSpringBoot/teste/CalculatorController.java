package br.com.restWithSpringBoot.teste;

import static br.com.restWithSpringBoot.utils.UtilOperation.convertToDouble;
import static br.com.restWithSpringBoot.utils.UtilOperation.isNumeric;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.restWithSpringBoot.exception.ResourceOperationException;

@RestController
public class CalculatorController {


	@GetMapping("/sum/{number1}/{number2}")
	public Double sum(@PathVariable("number1") String number1,@PathVariable("number2") String number2) throws Exception {
		if(!isNumeric(number1) || !isNumeric(number2)) {
			throw new ResourceOperationException("Please set a numeric value");
		}
		Double result = convertToDouble(number1) + convertToDouble(number2);
		return result;
	}
	
	
	@GetMapping("/subtraction/{number1}/{number2}")
	public Double subtraction(@PathVariable("number1") String number1,@PathVariable("number2") String number2) throws Exception {
		if(!isNumeric(number1) || !isNumeric(number2)) {
			throw new ResourceOperationException("Please set a numeric value");
		}
		Double result = convertToDouble(number1) - convertToDouble(number2);
		return result;
	}
	
	@GetMapping("/multiplication/{number1}/{number2}")
	public Double multiplication(@PathVariable("number1") String number1,@PathVariable("number2") String number2) throws Exception {
		if(!isNumeric(number1) || !isNumeric(number2)) {
			throw new ResourceOperationException("Please set a numeric value");
		}
		Double result = convertToDouble(number1) * convertToDouble(number2);
		return result;
	}
	@GetMapping("/division/{number1}/{number2}")
	public Double division(@PathVariable("number1") String number1,@PathVariable("number2") String number2) throws Exception {
		if(!isNumeric(number1) || !isNumeric(number2)) {
			throw new ResourceOperationException("Please set a numeric value");
		}if(number2.equals("0")) {
			throw new ResourceOperationException("divisor can not be zero");
		}
		Double num1 = convertToDouble(number1);
		Double num2 = convertToDouble(number2);
		
		Double result = num1 / num2;
		return result;
	}
	
	@GetMapping("/average/{number1}/{number2}")
	public Double average(@PathVariable("number1") String number1,@PathVariable("number2") String number2) throws Exception {
		if(!isNumeric(number1) || !isNumeric(number2)) {
			throw new ResourceOperationException("Please set a numeric value");
		}
		Double result = convertToDouble(number1) + convertToDouble(number2) / 2;
		return result;
	}

	
}
