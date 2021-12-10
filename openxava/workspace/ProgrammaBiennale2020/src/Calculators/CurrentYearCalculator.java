package Calculators;

import java.util.*;

import org.openxava.calculators.*; // To use ICalculator

public class CurrentYearCalculator implements ICalculator {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		if (util.ProgBienUtils.ControllaFase(Calendar.getInstance().get(Calendar.YEAR), new Date(), "A"))
			return Calendar.getInstance().get(Calendar.YEAR) + 1;
		else
			return Calendar.getInstance().get(Calendar.YEAR);
    }
}