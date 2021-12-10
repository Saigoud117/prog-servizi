package Calculators;

import java.util.*;

import org.openxava.calculators.*; // To use ICalculator

public class CurrentYearCalculatorAsString implements ICalculator {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }
}