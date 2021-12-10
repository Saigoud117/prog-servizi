package Filters;

import java.util.*;

import org.openxava.filters.*;
import org.openxava.util.*;

@SuppressWarnings("serial")
public class CurrentUserTicketFilter extends BaseContextFilter implements IFilter {
	 
	 public Object filter(Object o) throws FilterException { // (2)		 
		 String username = Users.getCurrent();
		 
		 String usr = "%%";
		 		 
		 if (!util.ProgBienUtils.checkAdmin())
		 {
			 usr = username;
		 }
		 
		 if (o == null) {
	            return new Object [] { usr };
	        }       
		 if (o instanceof Object []) {           
			 List<Object> c = new ArrayList<Object>(Arrays.asList((Object []) o));
			 c.add(0, usr);
			 return c.toArray();
		 } 
		 else {
			 return new Object [] { usr, o   };
		 }
	 }	
}
