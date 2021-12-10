package Calculators;

import java.util.*;

import org.openxava.calculators.*;

public class UUIDCalculator implements ICalculator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {    	
    	String cui = UUID.randomUUID().toString().substring(0, 32);
    	return cui;
	  }
}
