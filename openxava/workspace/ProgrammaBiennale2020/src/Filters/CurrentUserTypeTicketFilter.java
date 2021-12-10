package Filters;

import java.util.*;

import org.openxava.filters.*;

@SuppressWarnings("serial")
public class CurrentUserTypeTicketFilter extends BaseContextFilter implements IFilter {
	 
	 public Object filter(Object o) throws FilterException { // (2)		 
		 
		 boolean admin = true;
		 boolean noAdmin = false;
		 		 
		 if (!util.ProgBienUtils.checkAdmin())
		 {
			 admin = false;
			 noAdmin = false;
		 }
		 
		 if (o == null) {
	            return new Object [] { admin, noAdmin };
	        }       
		 if (o instanceof Object []) {           
			 List<Object> c = new ArrayList<Object>(Arrays.asList((Object []) o));
			 c.add(0, admin);
			 c.add(1, noAdmin);
			 return c.toArray();
		 } 
		 else {
			 return new Object [] { admin, noAdmin, o   };
		 }
	 }	
}
