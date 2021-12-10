package Calculators;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

public class TicketIdCalculator implements ICalculator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		
		EntityManager em = XPersistence.createManager();
    	long countTicket = (long) em.createQuery(
		        "select count(t) from TicketHelpDesk t")  // JPQL query (1)
		    .getSingleResult();  // To obtain one single entity (2)
    	em.close(); // You have to close the manager
    	
    	countTicket = countTicket + 1;
    	
    	String id = CodiceInterno(countTicket);
    	
    	return id;
	  }
	
	public String CodiceInterno(long codiceInterno) {
		int codiceInternoLenght = String.valueOf(codiceInterno).length();
		String id = "";
		for (int i = 0; i < 6-codiceInternoLenght; i++) {
			id = id.concat("0");
		}
		
		id = id.concat(String.valueOf(codiceInterno));
		
		return id;
	}
}