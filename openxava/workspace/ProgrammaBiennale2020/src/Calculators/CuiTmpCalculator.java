package Calculators;

import java.util.*;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

public class CuiTmpCalculator implements ICalculator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		EntityManager em = XPersistence.createManager();
    	long countProcedureTmp = (long) em.createQuery(
		        "select count(p) from ProcedureProgramma p where p.cui like '%~TMP'")  // JPQL query (1)
		    .getSingleResult();  // To obtain one single entity (2)
    	em.close(); // You have to close the manager
    	
    	String cui = "~TMP".concat(String.valueOf(countProcedureTmp)).concat("-").concat(UUID.randomUUID().toString());
    	
    	return cui;
	  }
}
