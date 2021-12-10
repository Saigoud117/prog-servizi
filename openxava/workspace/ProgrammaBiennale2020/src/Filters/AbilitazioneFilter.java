package Filters;

import java.util.*;

import org.openxava.filters.*;
import org.openxava.util.*;

import ProgBien.*;

@SuppressWarnings("serial")
public class AbilitazioneFilter extends BaseContextFilter implements IFilter {
	 
	 public Object filter(Object o) throws FilterException { // (2)		 
		 String username = Users.getCurrent();
		 
		 List<Inquadrato> i = util.ProgBienUtils.getUserInquadrato(username);
		 
		 String usr = "%%";
		 
		 int filterUsername = 0;
		 int filterServizio = 0;
		 String servizio = "";	
		 
		 String ruolo = "";
		 
		 List<String> listaServizi = new ArrayList<String>();
		 		 
		 if (!util.ProgBienUtils.checkAdmin())
		 {
			 usr = username;
		 }
		 
		 if (!i.isEmpty() && !util.ProgBienUtils.checkAdmin() && !util.ProgBienUtils.checkSelfSignUp())
		 {	
			 filterServizio = 1;
			 
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
		                     break;	 
			            default: 
		                     break;
			        }
					 
					listaServizi.add(servizio);
				 }
			 }
		 }
		 else
		 {
			 filterUsername = 1;
			 listaServizi.add(servizio);
		 }
		 
		 if (o == null) {
	            return new Object [] { filterServizio, listaServizi, filterUsername, usr };
	        }       
		 if (o instanceof Object []) {           
			 List<Object> c = new ArrayList<Object>(Arrays.asList((Object []) o));
			 c.add(0, filterServizio);
			 c.add(1, listaServizi);
			 c.add(2, filterUsername);
			 c.add(3, usr);
			 return c.toArray();
		 } 
		 else {
			 return new Object [] { filterServizio, listaServizi, filterUsername, usr, o   };
		 }
	 }	
}
