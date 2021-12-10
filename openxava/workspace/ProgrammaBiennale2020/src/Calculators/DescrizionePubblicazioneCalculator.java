package Calculators;

import java.util.*;

import org.openxava.calculators.*; // To use ICalculator

import util.*;

public class DescrizionePubblicazioneCalculator implements ICalculator {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		String descr = ProgrammaBiennalePreferences.getDefaultDescrizionePubblicazione();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String date = String.valueOf(year).concat("/").concat(String.valueOf(year + 1));
		return descr.replace("~REPLACE~", date);
    }
}