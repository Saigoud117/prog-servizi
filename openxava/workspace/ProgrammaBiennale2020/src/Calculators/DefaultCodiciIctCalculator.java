package Calculators;

import org.openxava.calculators.*;

import ProgBien.*;

public class DefaultCodiciIctCalculator implements ICalculator {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
	    CodiciIct key = new CodiciIct();
	    key.setKey("B26");
	    return key;
	  }
	 
	}
	 
