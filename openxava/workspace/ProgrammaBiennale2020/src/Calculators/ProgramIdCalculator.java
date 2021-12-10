package Calculators;

import java.util.*;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

import util.*;

public class ProgramIdCalculator implements ICalculator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		
		String cfSA = ProgrammaBiennalePreferences.getDefaultCfSA();
		
		EntityManager em = XPersistence.createManager();
    	long countPubblicazioni = (long) em.createQuery(
		        "select count(p) from Pubblicazione p where p.anno = :anno")  // JPQL query (1)
    			.setParameter("anno", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
		    .getSingleResult();  // To obtain one single entity (2)
    	em.close(); // You have to close the manager
    	
    	countPubblicazioni = countPubblicazioni + 1;
    	
    	String id = "FS".concat(String.valueOf(cfSA)).concat(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))).concat(CodiceInterno(countPubblicazioni));
    	
    	return id;
	  }
	
	public String CodiceInterno(long codiceInterno) {
		int codiceInternoLenght = String.valueOf(codiceInterno).length();
		String id = "";
		for (int i = 0; i < 3-codiceInternoLenght; i++) {
			id = id.concat("0");
		}
		
		id = id.concat(String.valueOf(codiceInterno));
		
		return id;
	}
}