package util;

import org.apache.commons.lang.*;
import org.openxava.jpa.*;

import com.openxava.naviox.model.*;
import com.openxava.naviox.model.Role;

public class UserUtils {
	
	public static void Signup(String userName, String name) {

		User user = new User();
		user.setName(userName);
		user.setPassword(name.concat("01").replaceAll("\\s","").toLowerCase());
		user.setRepeatPassword(name.concat("01").replaceAll("\\s","").toLowerCase());
		if (Configuration.getInstance().isUseEmailAsUserName()) {
			user.setEmail(user.getName());
			user.setName(StringUtils.abbreviate(user.getName(), 30));
		}	

		Role role = Role.findSelfSignUpRole();
		user.addRole(role);
		XPersistence.getManager().persist(user);
		XPersistence.commit(); 
	}
	
	public static User AddUserInfo(String username, String email, String name, String surname, String roleKey) {
		User u = User.find(username);
		
		u.setEmail(email);
		u.setGivenName(name);
		u.setFamilyName(surname);
		
		Role r = new Role();
		switch (roleKey) {
        case "A02":  
        	r = Role.find("Referente di servizio");
             break;
        case "A03":  
        	r = Role.find("Referente Struttura");
            break;
        case "A04":  
        	r = Role.find("Lettore Programma");
            break;
        case "A05":  
        	r = Role.find("Lettore servizio");
            break;
        case "A06":  
        	r = Role.find("Lettore struttura");
            break;
        default: 
             break;
		}

		if (r.getDescription() != null)
		{
			u.addRole(r);
			u.getRoles().remove(Role.findSelfSignUpRole());
		}
		
		return u;
	}
	
	public static User updateRole(User u, String roleKey) {
		Role r = new Role();
		switch (roleKey) {
        case "A02":  
        	r = Role.find("Referente di servizio");
             break;
        case "A03":  
        	r = Role.find("Referente Struttura");
            break;
        case "A04":  
        	r = Role.find("Lettore Programma");
            break;
        case "A05":  
        	r = Role.find("Lettore servizio");
            break;
        case "A06":  
        	r = Role.find("Lettore struttura");
            break;
        default: 
             break;
		}
		
		for (Role role: u.getRoles()) {
			if (!role.getDescription().equals(r.getDescription())){
				u.getRoles().remove(role);
			}
		}
		if (!u.hasRole(r.getDescription())) {
			u.addRole(r);
		}	
		
		return u;
	}
}
