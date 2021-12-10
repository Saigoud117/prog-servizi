package Calculators;

import org.openxava.calculators.*;

import ProgBien.*;

public class DefaultStatoTicketCalculator implements ICalculator {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
	    StatoTicket key = new StatoTicket();
	    key.setKey("N");
	    return key;
	  }
	 
	}
	 