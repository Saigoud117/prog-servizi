package Calculators;

import org.openxava.calculators.*;
import org.openxava.util.*;

public class CurrentUserCfCalculator implements ICalculator {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		String username = Users.getCurrent();
		if (!username.equals("admin"))
		{
			return username;
		}
		else return "";	    
	  }
	 
	}