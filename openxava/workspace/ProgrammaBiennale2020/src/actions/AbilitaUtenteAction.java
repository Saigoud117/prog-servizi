package actions;

import java.text.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;

public class AbilitaUtenteAction extends ViewBaseAction {
	public void execute() throws Exception {
		
		if ((!Users.getCurrent().equals("admin") || !util.ProgBienUtils.checkAdmin()) && !util.ProgBienUtils.checkReferenteServizio()) {
			throw new javax.validation.ValidationException(
				XavaResources.getString(
					"operation_not_allowed_abilita"
				)
			);
		}
		
		RichiestaAbilitazione r = (RichiestaAbilitazione)getView().getEntity();
		EntityManager em = XPersistence.getManager();   
        r = em.merge(r);
		
		util.UserUtils.AddUserInfo(r.getCf(), r.getEmail(), r.getNome(), r.getCognome(), r.getProfilo().getKey());
		
		Dipendenti u;
		u = util.ProgBienUtils.checkDipendenteExist(r.getCf());
		if (u == null) {
			u = new Dipendenti();
			u.setCf(r.getCf());
			u.setNome(r.getNome());
			u.setCognome(r.getCognome());
			u.setMatricola(r.getMatricola());
			u.setRup(r.isRup());
			em.persist(u);
		}
		
		Inquadrato i = new Inquadrato();
		i.setDatainizio(new Date());
		
		String d ="31/12/9999";  
	    Date f = new SimpleDateFormat("dd/MM/yyyy").parse(d);
	    
		i.setDatafine(f);
		i.setDipendenti(u);
		i.setProfilo(r.getProfilo());
		i.setServizi(r.getServizi());
		
		em.persist(i);
		
		r.setAbilitato(true);
		
		XPersistence.commit(); 
		getView().reset();
		getView().setEditable(true);    
		getView().setKeyEditable(false);
        addMessage("Abilitazione.Success");
     
        util.ProgBienUtils.sendNotifyEmailAbilitazione("Abilitazione", r.getCf(), r.getEmail(), r.getServizi().getServizio(), r.getServizi().getDescrizione());
	}
}
