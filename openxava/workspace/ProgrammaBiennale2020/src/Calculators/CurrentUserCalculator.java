package Calculators;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;

public class CurrentUserCalculator implements ICalculator {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		String username = Users.getCurrent();
		if (!username.equals("admin"))
		{
			Query query = XPersistence.getManager().createQuery("from Dipendenti i where i.cf like :cf");			      
		    query.setParameter("cf", username);
			
		    Dipendenti d = null;
		    try {
		    	d = (Dipendenti)query.getSingleResult();
		    }
		    catch (NoResultException nre) {
		    	//Ignore this because as per your logic this is ok!
		    	}
		    return d;
		}
		else return "";	    
	  }
	 
	}