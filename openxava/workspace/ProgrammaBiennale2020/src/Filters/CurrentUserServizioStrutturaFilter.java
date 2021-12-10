package Filters;

import java.util.*;

import javax.persistence.*;

import org.openxava.filters.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;

@SuppressWarnings("serial")
public class CurrentUserServizioStrutturaFilter extends BaseContextFilter implements IFilter {
	 
	 public Object filter(Object o) throws FilterException { // (2)		 
		 String username = Users.getCurrent();
		 
		 List<Inquadrato> i = util.ProgBienUtils.getUserInquadrato(username);	
		 
		 Inquadrato sessionInquadrato = (Inquadrato) get("ProgBien_profile");
		 if (sessionInquadrato.getServizi() != null)
		 {
			 i.clear();
			 i.add(sessionInquadrato);
		 }
		 
		 String servizio = "";		 
		 String struttura = "";
		 int filterServizio = 0;
		 int filterStruttura = 0;
		 String ruolo = "";
		 
		 List<String> listaServizi = new ArrayList<String>();
		 List<String> listaStrutture = new ArrayList<String>();
		 
		 if (!util.ProgBienUtils.checkAdmin() && !util.ProgBienUtils.checkSelfSignUp() && i.isEmpty())
		 {
			 servizio = "error";		 
			 struttura = "error";
			 listaServizi.add(servizio);
			 listaStrutture.add(struttura);
		 }
		 else if (!i.isEmpty() && !util.ProgBienUtils.checkAdmin() && !util.ProgBienUtils.checkSelfSignUp())
		 {	
			 for(Inquadrato x: i)
			 {
				 if (x.getProfilo() == null) 
				 {
					 ruolo = "RUP";
				 }
				 else
				 {
					 ruolo = x.getProfilo().getKey();
				 }
				 if (!ruolo.isEmpty())
				 {
					 switch (ruolo) {
			            case "A01":
		                     break;
			            case "A02":  
			            	 servizio = x.getServizi().getServizio();		 
			   			 	 //struttura = i.getServizi().getStruttura();	
			            	 listaStrutture.addAll(addStruttureServizio(x.getServizi().getServizio()));
		                     break;
			            case "A03":  
			            	 servizio = x.getServizi().getServizio();		 
			            	 struttura = x.getServizi().getStruttura();	
		                     break;
			            case "A04":  
			                 break;
			            case "A05":  
			            	 servizio = x.getServizi().getServizio();		 
			   			     //struttura = i.getServizi().getStruttura();	
			            	 listaStrutture.addAll(addStruttureServizio(x.getServizi().getServizio()));
		                     break;
			            case "A06":  
			            	 servizio = x.getServizi().getServizio();		 
			            	 struttura = x.getServizi().getStruttura();	
		                     break;
			            case "RUP":
			            	 servizio = x.getServizi().getServizio();		 
			            	 struttura = x.getServizi().getStruttura();
			            default: 
		                     break;
			        }
					 
					listaServizi.add(servizio);
					listaStrutture.add(struttura);
				 }
			 }
		 }
		 else
		 {
			 filterServizio = 1;
			 filterStruttura = 1;
			 listaServizi.add(servizio);
			 listaStrutture.add(struttura);
		 }
		 
		 if (o == null) {
	            return new Object [] { filterServizio, listaServizi, filterStruttura, listaStrutture };
	        }       
		 if (o instanceof Object []) {           
			 List<Object> c = new ArrayList<Object>(Arrays.asList((Object []) o));
			 c.add(0, filterServizio);
			 c.add(1, listaServizi);
			 c.add(2, filterStruttura);
			 c.add(3, listaStrutture);
			 return c.toArray();
		 } 
		 else {
			 return new Object [] { filterServizio, listaServizi, filterStruttura, listaStrutture, o   };
		 }
	 }
	 
	 private List<String> addStruttureServizio(String servizio) {
		 	List<String> result = new ArrayList<String>();
			Date data = new Date();
			Query query = XPersistence.getManager().createQuery("from Servizi i where i.servizio = :servizio and i.datainizio <= :data and i.datafine >= :data");			      
		    query.setParameter("servizio", servizio);
		    query.setParameter("data", data);			    
		    @SuppressWarnings("unchecked")
			List<Servizi> servizi = (List<Servizi>)query.getResultList();
			for(Servizi s: servizi) {
				result.add(s.getStruttura());
			}
			return result;
	 }
}
