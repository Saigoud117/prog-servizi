package Calculators;

import org.openxava.calculators.*;

import ProgBien.*;

public class DefaultStatoProgettoCalculator implements ICalculator {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
	    StatoProgetto key = new StatoProgetto();
	    key.setKey("N");
	    return key;
	  }
	 
	}
	 